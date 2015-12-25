package info.fulloo.trygve.run_time;

/*
 * Trygve IDE 1.1
 *   Copyright (c)2015 James O. Coplien
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


import java.util.Map;

import info.fulloo.trygve.declarations.ActualOrFormalParameterList;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Type.ArrayType;
import info.fulloo.trygve.run_time.RTObjectCommon.RTIntegerObject;
import info.fulloo.trygve.run_time.RTObjectCommon.RTNullObject;
import info.fulloo.trygve.semantic_analysis.StaticScope;

public class RTArrayType implements RTType {
	public RTArrayType(final Type baseType, final ArrayType arrayType) {
		super();
		baseType_ = baseType;
		arrayType_ = arrayType;
		rTSizeMethod_ = new RTSizeMethod(arrayType.sizeMethodDeclaration(StaticScope.globalScope()));
	}

	@Override public void addClass(final String typeName, final RTClass classDecl) {
		assert false;
	}

	@Override public void addContext(final String typeName, final RTContext classDecl) {
		assert false;
	}

	@Override public RTType typeNamed(final String typeName) {
		assert false;
		return null;
	}

	@Override public void addMethod(final String methodName, final RTMethod method) {
		assert false;
	}

	@Override public void addObjectDeclaration(final String objectName, final RTType objectType) {
		assert false;
	}

	@Override public void addStageProp(final String objectName, final RTStageProp stagePropType) {
		assert false;
	}

	@Override public void addRole(final String objectName, final RTRole roleType) {
		assert false;
	}

	@Override public RTMethod lookupMethod(final String methodName, final ActualOrFormalParameterList pl) {
		assert false;
		return null;
	}

	@Override public RTMethod lookupMethodIgnoringParameterInSignatureNamed(final String methodName,
			final ActualOrFormalParameterList pl, final String paramToIgnore) {
		RTMethod retval = null;
		if (methodName.equals("size") && pl.count() == 1) {
			// This is part of the kludge to give naked array appearances
			// their own methods
			retval = rTSizeMethod_;
		} else {
			retval = null;
		}
		return retval;
	}
	
	@Override public RTMethod lookupMethodIgnoringParameterAtPosition(final String methodName,
			final ActualOrFormalParameterList pl, final int paramToIgnore) {
		return lookupMethodIgnoringParameterInSignatureNamed(methodName, pl, null);
	}
	
	@Override public RTMethod lookupMethodIgnoringParameterInSignatureWithConversionNamed(final String methodName,
			final ActualOrFormalParameterList pl, final String paramToIgnore) {
		assert false;
		return null;
	}
	
	@Override public RTMethod lookupMethodIgnoringParameterInSignatureWithConversionAtPosition(final String methodName,
			final ActualOrFormalParameterList pl, final int paramToIgnore) {
		assert false;
		return null;
	}

	@Override public Map<String, RTType> objectDeclarations() {
		assert false;
		return null;
	}

	@Override public void setObject(final String objectName, final RTObject object) {
		assert false;
	}

	@Override public RTObject getObject(final String objectName) {
		assert false;
		return null;
	}

	@Override public RTObject defaultObject() {
		assert false;
		return null;
	}

	@Override public Map<String, RTRole> nameToRoleDeclMap() {
		assert false;
		return null;
	}

	@Override public Map<String, RTStageProp> nameToStagePropDeclMap() {
		assert false;
		return null;
	}

	@Override public String name() {
		return null;
	}
	
	private static class RTSizeMethod extends RTMethod {
		public RTSizeMethod(final MethodDeclaration methodDecl) {
			super("size", methodDecl);
		}
		@Override public RTCode run() {
			final RTDynamicScope activationRecord = RunTimeEnvironment.runTimeEnvironment_.currentDynamicScope();
			final RTStackable theArrayObject = activationRecord.getObject("this");
			int size = 0;
			if (theArrayObject instanceof RTNullObject) {
				size = 0;
			} else if (theArrayObject instanceof RTArrayObject) {
				size = ((RTArrayObject)theArrayObject).size();
			}
			final RTObject result = new RTIntegerObject(size);
			
			// This is a VERY tightly integrated function. It
			// does not use an RTReturn functor but handles
			// return processing riiiiight here. The only thing
			// that follows is RTPostReturnProcessing, which
			// is where the return address takes us.
			
			// This does nothing but pop the frame pointer stack, really
			RunTimeEnvironment.runTimeEnvironment_.popDownToFramePointer();	// is this right?
			
			final RTStackable rawReturnAddress = RunTimeEnvironment.runTimeEnvironment_.popStack();
			assert rawReturnAddress instanceof RTCode;
			RTCode returnAddress = (RTCode) rawReturnAddress;
			
			final RTDynamicScope lastPoppedScope = RunTimeEnvironment.runTimeEnvironment_.popDynamicScope();
			lastPoppedScope.decrementReferenceCount();
			
			// No one else should have a handle to the activation record
			assert lastPoppedScope.referenceCount() == 0;
			
			lastPoppedScope.closeScope();

			RunTimeEnvironment.runTimeEnvironment_.pushStack(result);
			return returnAddress;
			
			// ignore returnInstruction_;
		}
	}
	
	public Type baseType() {
		return baseType_;
	}
	
	public ArrayType arrayType() {
		return arrayType_;
	}
	
	private final RTMethod rTSizeMethod_;
	private final Type baseType_;
	private final ArrayType arrayType_;
}
