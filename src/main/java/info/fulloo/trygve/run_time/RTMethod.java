package info.fulloo.trygve.run_time;

/*
 * Trygve IDE
 *   Copyright �2015 James O. Coplien
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 *  For further information about the trygve project, please contact
 *  Jim Coplien at jcoplien@gmail.com
 * 
 */

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import info.fulloo.trygve.code_generation.InterpretiveCodeGenerator;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.TemplateInstantiationInfo;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Type.ClassType;
import info.fulloo.trygve.declarations.Type.TemplateType;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.expressions.Expression.ReturnExpression;
import info.fulloo.trygve.expressions.Expression.TopOfStackExpression;
import info.fulloo.trygve.run_time.RTExpression.RTReturn;
import info.fulloo.trygve.run_time.RTObjectCommon.RTNullObject;
import info.fulloo.trygve.semantic_analysis.StaticScope;

public class RTMethod extends RTCode {
	public RTMethod(final String name, final MethodDeclaration methodDeclaration) {
		super();
		
		// Get ClassType as a handle to template information
		final StaticScope methodScope = methodDeclaration.enclosedScope();
		Type rawClassType = Expression.nearestEnclosingMegaTypeOf(methodScope);
		if (false == rawClassType instanceof ClassType) {
			rawClassType = null;
		}
		
		ReturnExpression returnExpression = null;
		name_ = name;
		codeSize_ = 10;
		nextCodeIndex_ = 0;
		code_ = new RTCode[codeSize_];
		returnType_ = methodDeclaration.returnType();
		if (null != returnType_ && returnType_ instanceof TemplateType) {
			final ClassType classType = (null == rawClassType)? null: (ClassType)rawClassType;
			final TemplateInstantiationInfo templateInstantiationInfo = null == classType? null: classType.enclosedScope().templateInstantiationInfo();
			assert null != templateInstantiationInfo;
			returnType_ = templateInstantiationInfo.classSubstitionForTemplateTypeNamed(returnType_.name());
		} else if (null != returnType_
				&& returnType_ != StaticScope.globalScope().lookupTypeDeclaration("void")) {
			final Expression dummyReturnExpression = new TopOfStackExpression();
			returnExpression = new ReturnExpression(dummyReturnExpression,
					methodDeclaration.lineNumber(), rawClassType);
			returnExpression.setResultIsConsumed(true);
		} else {
			// Put one in anyhow, even though there is no return value...
			final Expression dummyReturnExpression = null;
			returnExpression = new ReturnExpression(dummyReturnExpression,
					methodDeclaration.lineNumber(), rawClassType);
			returnExpression.setResultIsConsumed(false);
		}

		methodDeclaration_ = methodDeclaration;

		returnInstruction_ = new RTReturn(methodDeclaration_.name(), returnExpression, null);
		this.addCode(returnInstruction_);
		
		// All dogs go to heaven and all procedures that have something to return
		// will return it � we deal with consumption in the message. (However,
		// some are typed void and we should not signal that those will be
		// consumed.)
		assert null != returnInstruction_ && returnInstruction_ instanceof RTExpression;
		final RTExpression returnInstruction = (RTExpression)returnInstruction_;
		returnInstruction.setResultIsConsumed(returnExpression.resultIsConsumed());
		
		// WARNING: Fake-out. We continue to insert at the beginning
		// even though the return statement is at the end. It's a bit
		// unnerving but we can keep it all within this class.
		nextCodeIndex_ = 0;

		initializationList_ = new LinkedHashMap<String, RTExpression>();
	}

	public void addCode(final List<RTCode> code) {
		for (RTCode aCode : code) {
			this.addCode(aCode);
		}
	}

	private void growListIfNecessary() {
		if (nextCodeIndex_ + 1 >= codeSize_) {
			final RTCode[] saveCode = code_;
			final int saveCodeSize = codeSize_;
			codeSize_ *= 2;
			code_ = new RTCode[codeSize_];
			for (int i = 0; i < saveCodeSize; i++) {
				code_[i] = saveCode[i];
			}
		}
	}

	public void addCode(final RTCode code) {
		this.growListIfNecessary();
		// We're adding code into the middle of a list.
		// The constructor added the return statement as the
		// first code. All new code is inserted before the
		// return statement

		// Move the return statement
		code_[nextCodeIndex_ + 1] = code_[nextCodeIndex_];

		if (nextCodeIndex_ > 0) {
			code_[nextCodeIndex_ - 1].setNextCode(code);
		}
		code_[nextCodeIndex_++] = code;
		code.setNextCode(code_[nextCodeIndex_]);
	}

	private void populateActivationRecord() {
		final StaticScope methodScope = methodDeclaration_.enclosedScope();
		final List<ObjectDeclaration> objectDeclarations = methodScope.objectDeclarations();
		final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
		for (final ObjectDeclaration od : objectDeclarations) {
			// RTType runTimeDeclaration =
			// InterpretiveCodeGenerator.convertTypeDeclarationToRTTypeDeclaration(od);

			// We need to be a bit more disciplined about this. The lookup check
			// is a kludge, but we can separate non-argument locals from parameters
			// to be able to do this more efficiently. Right now the formal
			// parameters are in this loop along with the other local
			// variables in the method � we just snake around the former parameters
			// by seeing that they previously have been added.
			if (null == activationRecord.getObject(od.name())) {
				activationRecord.addObjectDeclaration(od.name(), null);
				activationRecord.setObject(od.name(), new RTNullObject());
			}
		}
	}

	private void initializeLocals() {
		// NOTE: initializationList_ is not linked in with code_
		
		final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_
				.currentDynamicScope();
		for (Map.Entry<String, RTExpression> iterator : initializationList_.entrySet()) {
			// Go into the initializer, and run it to completion
			final RTExpression initializer = iterator.getValue();
			for (RTCode pc = initializer.run(); pc != null;) {
				pc = pc.run();
			}
			// We're back from initializer. Is the stack clean?

			// Get the name of the identifier that is to be initialized
			final String name = iterator.getKey();

			// Pop off the expression that was previously evaluated
			// as the initialization value
			final RTObject value = (RTObject) RunTimeEnvironment.runTimeEnvironment_.popStack();
			assert null != value;
			activationRecord.setObject(name, value);

			value.decrementReferenceCount();
		}

		// This is a run-to-completion method. It does not return
		// a reference to any next code value. The caller must
		// resume execution autonomously.
	}

	@Override public RTCode run() {
		// A new activation record has just been pushed
		// by the caller. Parameters are already set up.
		// Populate it with local variables.
		populateActivationRecord();
		
		// NOTE: initializationList_ is not linked in with code_
		initializeLocals();	// deprecated?

		// Now run the body
		RTCode retval = null;
		if (0 < nextCodeIndex_) {	// i.e., if there is anything to execute
			// The RTMethod node is just a shell, and all it can be
			// said to handle is a bit of the stack protocol. The
			// real meat is in the code_ array. So if someone has a
			// handle to an RTMethod node and asks to run it, we set
			// up to execute the actual body.
			retval = code_[0];
		} else {
			retval = returnInstruction_;
		}

		return retval;
	}

	@Override public void setNextCode(final RTCode next) {
		if (0 < nextCodeIndex_) {
			code_[nextCodeIndex_].setNextCode(next);
		}
		super.setNextCode(next);
	}

	public String name() {
		return name_;
	}

	public MethodDeclaration methodDeclaration() {
		return methodDeclaration_;
	}

	public FormalParameterList formalParameters() {
		// Demeter win
		return methodDeclaration_.formalParameterList();
	}

	public RTType rTEnclosingType() {
		final StaticScope classOrRoleOrContextScope = methodDeclaration_.enclosingScope();
		final RTType rTType = InterpretiveCodeGenerator.scopeToRTTypeDeclaration(classOrRoleOrContextScope);
		return rTType;
	}

	private String name_;
	private RTCode[] code_;
	private int codeSize_, nextCodeIndex_;
	private MethodDeclaration methodDeclaration_;
	private Map<String, RTExpression> initializationList_;
	private RTCode returnInstruction_;
	private Type returnType_;
}
