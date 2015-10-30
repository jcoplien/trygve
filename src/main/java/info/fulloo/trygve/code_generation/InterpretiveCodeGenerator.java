package info.fulloo.trygve.code_generation;

/*
 * Trygve IDE
 *   Copyright ©2015 James O. Coplien
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

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import info.fulloo.trygve.add_ons.ListClass;
import info.fulloo.trygve.add_ons.MathClass;
import info.fulloo.trygve.add_ons.SystemClass;
import info.fulloo.trygve.declarations.ActualOrFormalParameterList;
import info.fulloo.trygve.declarations.BodyPart;
import info.fulloo.trygve.declarations.Declaration;
import info.fulloo.trygve.declarations.Declaration.InterfaceDeclaration;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.TemplateInstantiationInfo;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.TypeDeclaration;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.ContextDeclaration;
import info.fulloo.trygve.declarations.Declaration.DeclarationList;
import info.fulloo.trygve.declarations.Declaration.ExprAndDeclList;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectDeclaration;
import info.fulloo.trygve.declarations.Declaration.RoleDeclaration;
import info.fulloo.trygve.declarations.Declaration.StagePropDeclaration;
import info.fulloo.trygve.declarations.Declaration.TemplateDeclaration;
import info.fulloo.trygve.declarations.Declaration.TypeDeclarationList;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorType;
import info.fulloo.trygve.expressions.Constant;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.expressions.Expression.ArrayExpression;
import info.fulloo.trygve.expressions.Expression.ArrayIndexExpression;
import info.fulloo.trygve.expressions.Expression.ArrayIndexExpressionUnaryOp;
import info.fulloo.trygve.expressions.Expression.AssignmentExpression;
import info.fulloo.trygve.expressions.Expression.BinopExpression;
import info.fulloo.trygve.expressions.Expression.BlockExpression;
import info.fulloo.trygve.expressions.Expression.BooleanExpression;
import info.fulloo.trygve.expressions.Expression.BreakExpression;
import info.fulloo.trygve.expressions.Expression.ContinueExpression;
import info.fulloo.trygve.expressions.Expression.DoWhileExpression;
import info.fulloo.trygve.expressions.Expression.DupMessageExpression;
import info.fulloo.trygve.expressions.Expression.ExpressionList;
import info.fulloo.trygve.expressions.Expression.ForExpression;
import info.fulloo.trygve.expressions.Expression.IdentifierExpression;
import info.fulloo.trygve.expressions.Expression.IfExpression;
import info.fulloo.trygve.expressions.Expression.IndexExpression;
import info.fulloo.trygve.expressions.Expression.MessageExpression;
import info.fulloo.trygve.expressions.Expression.NewArrayExpression;
import info.fulloo.trygve.expressions.Expression.NewExpression;
import info.fulloo.trygve.expressions.Expression.PowerExpression;
import info.fulloo.trygve.expressions.Expression.ProductExpression;
import info.fulloo.trygve.expressions.Expression.PromoteToDoubleExpr;
import info.fulloo.trygve.expressions.Expression.QualifiedClassMemberExpression;
import info.fulloo.trygve.expressions.Expression.QualifiedClassMemberExpressionUnaryOp;
import info.fulloo.trygve.expressions.Expression.QualifiedIdentifierExpression;
import info.fulloo.trygve.expressions.Expression.QualifiedIdentifierExpressionUnaryOp;
import info.fulloo.trygve.expressions.Expression.RelopExpression;
import info.fulloo.trygve.expressions.Expression.ReturnExpression;
import info.fulloo.trygve.expressions.Expression.RoleArrayIndexExpression;
import info.fulloo.trygve.expressions.Expression.SumExpression;
import info.fulloo.trygve.expressions.Expression.SwitchExpression;
import info.fulloo.trygve.expressions.Expression.UnaryAbelianopExpression;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect;
import info.fulloo.trygve.expressions.Expression.WhileExpression;
import info.fulloo.trygve.parser.ParsingData;
import info.fulloo.trygve.run_time.RTClass;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTContext;
import info.fulloo.trygve.run_time.RTExpression;
import info.fulloo.trygve.run_time.RTMethod;
import info.fulloo.trygve.run_time.RTRole;
import info.fulloo.trygve.run_time.RTStageProp;
import info.fulloo.trygve.run_time.RTType;
import info.fulloo.trygve.run_time.RunTimeEnvironment;
import info.fulloo.trygve.run_time.RTExpression.*;
import info.fulloo.trygve.semantic_analysis.Program;
import info.fulloo.trygve.semantic_analysis.StaticScope;

public class InterpretiveCodeGenerator implements CodeGenerator {
	public static InterpretiveCodeGenerator interpretiveCodeGenerator = null;
	public InterpretiveCodeGenerator(Program program, ParsingData parsingData) {
		super();
		program_ = program;
		parsingData_ = parsingData;
		virtualMachine_ = new RunTimeEnvironment();
		interpretiveCodeGenerator = this;
	}
	@Override public RunTimeEnvironment virtualMachine() {
		return virtualMachine_;
	}
	@Override public RTExpression mainExpr() {
		return rTMainExpr_;
	}
	@Override public void compile() {
		List<TypeDeclaration> typeDeclarationList = SystemClass.typeDeclarationList();
		compileDeclarations(typeDeclarationList);
		
		typeDeclarationList = ListClass.typeDeclarationList();
		compileDeclarations(typeDeclarationList);
		
		typeDeclarationList = MathClass.typeDeclarationList();
		compileDeclarations(typeDeclarationList);
				
		TypeDeclarationList typeDeclarationListWrapper = program_.theRest();
		typeDeclarationList = typeDeclarationListWrapper.declarations();
		compileDeclarations(typeDeclarationList);
		
		typeDeclarationListWrapper = program_.templateInstantiations();
		typeDeclarationList = typeDeclarationListWrapper.declarations();
		compileDeclarations(typeDeclarationList);
		
		compileMain();
	}
	private void compileDeclarations(final List<TypeDeclaration> typeDeclarationList) {
		for (final TypeDeclaration a : typeDeclarationList) {
			if (a instanceof ContextDeclaration) {
				this.compileContext((ContextDeclaration)a);
			} else if (a instanceof ClassDeclaration) {
				this.compileClass((ClassDeclaration)a);
			} else if (a instanceof StagePropDeclaration) {
				assert false;	// ever get here?
				this.compileStageProp((StagePropDeclaration)a);
			} else if (a instanceof RoleDeclaration) {
				assert false;	// ever get here?
				this.compileRole((RoleDeclaration)a);
			} else if (a instanceof TemplateDeclaration) {
				this.compileTemplate((TemplateDeclaration)a);
			} else if (a instanceof InterfaceDeclaration) {
				this.compileInterface((InterfaceDeclaration)a);
			} else {
				System.err.print("Unexpected type in TypeDeclarationList: ");
				System.err.println(a.getClass().getSimpleName());
				assert false;
			}
		}
	}
	private void compileMain() {
		final Expression mainExpr = program_.main();
		rTMainExpr_ = RTExpression.makeExpressionFrom(mainExpr, null);
	}
	private void compileContext(final ContextDeclaration contextDeclaration) {
		final StaticScope myScope = contextDeclaration.enclosedScope();
		this.compileScope(myScope);
	}
	private void compileClass(final ClassDeclaration classDeclaration) {
		if (null == RunTimeEnvironment.runTimeEnvironment_.topLevelTypeNamed(classDeclaration.name())) {
			if (classDeclaration.enclosingScope() == StaticScope.globalScope()) {
				// Kludge. But it's direct, and effective.
				final RTClass rTClassDeclaration = new RTClass(classDeclaration);
				RunTimeEnvironment.runTimeEnvironment_.addTopLevelClass(classDeclaration.name(), rTClassDeclaration);
			}
		}
		final StaticScope myScope = classDeclaration.enclosedScope();
		this.compileScope(myScope);
	}
	private void compileInterface(final InterfaceDeclaration interfaceDeclaration) {
		// Really nothing to compile Ñ all interface logic should
		// be absorbed by semantic analysis
	}
	private void compileStageProp(final StagePropDeclaration stagePropDeclaration) {
		final StaticScope myScope = stagePropDeclaration.enclosedScope();
		this.compileScope(myScope);
	}
	private void compileRole(final RoleDeclaration roleDeclaration) {
		final StaticScope myScope = roleDeclaration.enclosedScope();
		this.compileScope(myScope);
	}
	private void compileTemplate(final TemplateDeclaration roleDeclaration) {
		// We compile instantiations (classes), not the templates themselves.
	}
	private void processListCall(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		final RTType rtListTypeDeclaration = TypeDeclarationToRTTypeDeclaration(typeDeclaration);
		assert null != rtListTypeDeclaration;
		final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
		rtListTypeDeclaration.addMethod(rtMethod.name(), rtMethod);
		final List<RTCode> listCode = new ArrayList<RTCode>();
		if (methodDeclaration.name().equals("List")) {
			listCode.add(new ListClass.RTListCtorCode(methodDeclaration.enclosedScope()));
		} else if (methodDeclaration.name().equals("size")) {
			listCode.add(new ListClass.RTSizeCode(methodDeclaration.enclosedScope()));
		} else if (methodDeclaration.name().equals("add")) {
			listCode.add(new ListClass.RTAddCode(methodDeclaration.enclosedScope()));
		} else if (methodDeclaration.name().equals("get")) {
			listCode.add(new ListClass.RTGetCode(methodDeclaration.enclosedScope()));
		} else if (methodDeclaration.name().equals("indexOf")) {
			listCode.add(new ListClass.RTIndexOfCode(methodDeclaration.enclosedScope()));
		} else if (methodDeclaration.name().equals("contains")) {
			listCode.add(new ListClass.RTContainsCode(methodDeclaration.enclosedScope()));
		} else if (methodDeclaration.name().equals("isEmpty")) {
			listCode.add(new ListClass.RTIsEmptyCode(methodDeclaration.enclosedScope()));
		} else {
			assert false;	// error message instead? Should be caught earlier
		}
		rtMethod.addCode(listCode);
	}
	private void processMathCall(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		final RTType rtMathTypeDeclaration = TypeDeclarationToRTTypeDeclaration(typeDeclaration);
		assert null != rtMathTypeDeclaration;
		final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
		rtMathTypeDeclaration.addMethod(rtMethod.name(), rtMethod);
		final List<RTCode> mathCode = new ArrayList<RTCode>();
		if (methodDeclaration.name().equals("Math")) {
			ErrorLogger.error(ErrorType.Fatal, "Cannot instantiate class Math", "", "", "");
		} else if (methodDeclaration.name().equals("random")) {
			mathCode.add(new MathClass.RTRandomCode(methodDeclaration.enclosedScope()));
		} else if (methodDeclaration.name().equals("sqrt")) {
			mathCode.add(new MathClass.RTSqrtCode(methodDeclaration.enclosedScope()));
		} else {
			assert false;	// error message instead? Should be caught earlier
		}
		rtMethod.addCode(mathCode);
	}
	private void processPrintStreamCall(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		final FormalParameterList formalParameterList = methodDeclaration.formalParameterList();
		if (formalParameterList.count() == 2) {
			final ObjectDeclaration printableArgumentDeclaration = formalParameterList.parameterAtPosition(1);
			final Type printableArgumentType = printableArgumentDeclaration.type();
			final RTType rtTypeDeclaration = TypeDeclarationToRTTypeDeclaration(typeDeclaration);
			assert null != rtTypeDeclaration;
			final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
			List<RTCode> printlnCode = new ArrayList<RTCode>();
			if (printableArgumentType.name().equals("String")) {
				if (methodDeclaration.name().equals("println")) {
					printlnCode.add(new SystemClass.RTPrintlnStringCode(methodDeclaration.enclosedScope()));
				} else if (methodDeclaration.name().equals("print")) {
					printlnCode.add(new SystemClass.RTPrintStringCode(methodDeclaration.enclosedScope()));
				} else {
					assert false;
				}
			} else if (printableArgumentType.name().equals("int")) {
				if (methodDeclaration.name().equals("println")) {
					printlnCode.add(new SystemClass.RTPrintlnIntegerCode(methodDeclaration.enclosedScope()));
				} else if (methodDeclaration.name().equals("print")) {
					printlnCode.add(new SystemClass.RTPrintIntegerCode(methodDeclaration.enclosedScope()));
				} else {
					assert false;
				}
			} else if (printableArgumentType.name().equals("Integer")) {
				if (methodDeclaration.name().equals("println")) {
					printlnCode.add(new SystemClass.RTPrintlnBigIntegerCode(methodDeclaration.enclosedScope()));
				} else if (methodDeclaration.name().equals("print")) {
					printlnCode.add(new SystemClass.RTPrintBigIntegerCode(methodDeclaration.enclosedScope()));
				} else {
					assert false;
				}
			} else if (printableArgumentType.name().equals("boolean")) {
				if (methodDeclaration.name().equals("println")) {
					printlnCode.add(new SystemClass.RTPrintlnBooleanCode(methodDeclaration.enclosedScope()));
				} else if (methodDeclaration.name().equals("print")) {
					printlnCode.add(new SystemClass.RTPrintBooleanCode(methodDeclaration.enclosedScope()));
				} else {
					assert false;
				}
			} else if (printableArgumentType.name().equals("double")) {
				if (methodDeclaration.name().equals("println")) {
					printlnCode.add(new SystemClass.RTPrintlnDoubleCode(methodDeclaration.enclosedScope()));
				} else if (methodDeclaration.name().equals("print")) {
					printlnCode.add(new SystemClass.RTPrintDoubleCode(methodDeclaration.enclosedScope()));
				} else {
					assert false;
				}
			} else {
				assert false;
			}
			
			assert printlnCode.size() > 0;
			
			rtMethod.addCode(printlnCode);
		} else {
			assert false;
		}
	}
	private void compileMethodInScope(MethodDeclaration methodDeclaration, StaticScope scope) {
		final List<BodyPart> bodyParts = methodDeclaration.bodyParts();
		// Duck: Null body parts here for "add" declaration in simpletemplate.k
		// Called by compileScope for List<int,String> scope, from compileClass,
		// from compileDeclarations, from original compile
		
		TypeDeclaration typeDeclaration = null;
		final StaticScope myScope = scope; // methodDeclaration.enclosingScope();
		final StaticScope rightEnclosingScope = Expression.nearestEnclosingMegaTypeOf(myScope).enclosedScope();
		final Declaration roleOrContextOrClass = rightEnclosingScope.associatedDeclaration();
		if (roleOrContextOrClass instanceof StagePropDeclaration) {
			// FYI: StagePropDeclaration is a subclass of RoleDeclaration
			typeDeclaration = (StagePropDeclaration)roleOrContextOrClass;
		} else if (roleOrContextOrClass instanceof RoleDeclaration) {
			typeDeclaration = (RoleDeclaration)roleOrContextOrClass;
		} else if (roleOrContextOrClass instanceof ClassDeclaration) {
			typeDeclaration = (ClassDeclaration)roleOrContextOrClass;
			if (typeDeclaration.name().equals("PrintStream")) {
				processPrintStreamCall(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().startsWith("List<")) {
				processListCall(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().equals("Math")) {
				processMathCall(methodDeclaration, typeDeclaration);
				return;
			}
		} else if (roleOrContextOrClass instanceof ContextDeclaration) {
			typeDeclaration = (ContextDeclaration)roleOrContextOrClass;
		} else {
			assert false;	// unanticipated...
		}
		
		// As a side-effect, TypeDeclarationToRTTypeDeclaration will add the
		// necessary types to the run-time environment
		final RTType rtTypeDeclaration = TypeDeclarationToRTTypeDeclaration(typeDeclaration);
		final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
		rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
		this.compileBodyPartsForMethodOfTypeInScope(bodyParts, rtMethod, rtTypeDeclaration, scope);
	}
	
	private List<RTCode> compileDeclarationForMethodOfTypeInScope(Declaration declaration, MethodDeclaration methodDeclaration,
			RTType runtimeType, StaticScope scope) {
		List<RTCode> retval = null;
		if (declaration instanceof DeclarationList) {
			// This code right here is the reason for all the returns
			// or List<RTCode> instead of just RTCode...
			retval = new ArrayList<RTCode>();
			final DeclarationList declarationList = (DeclarationList)declaration;
			ActualOrFormalParameterList parameterList = methodDeclaration.formalParameterList();
			TemplateInstantiationInfo templateInstantiationInfo = null;
			if (runtimeType instanceof RTClass) {
				templateInstantiationInfo = ((RTClass)runtimeType).templateInstantiationInfo();
				parameterList = parameterList.mapTemplateParameters(templateInstantiationInfo);
			}
			final RTMethod rTMethodDecl = runtimeType.lookupMethod(methodDeclaration.name(), parameterList);
			final RTType rTType = rTMethodDecl.rTEnclosingType();
			assert null != rTType;
			for (final BodyPart bodyPart : declarationList.bodyParts()) {
				for (final RTCode code : this.compileBodyPartForMethodOfTypeInScope(bodyPart, rTMethodDecl, rTType, scope)) {
					retval.add(code);
				}
			}
		} else {
			retval = this.compileDeclarationForMethodOfTypeHelper(declaration, methodDeclaration, runtimeType, scope);
		}
		return retval;
	}
	private List<RTCode> compileDeclarationForMethodOfTypeHelper(Declaration declaration, MethodDeclaration methodDeclaration,
			RTType runtimeType, StaticScope scope) {
		List<RTCode> retval = new ArrayList<RTCode>();
	
		final RTType declarationType = scopeToRTTypeDeclaration(declaration.type().enclosedScope());
		final RTMethod runTimeMethodDecl = runtimeType.lookupMethod(methodDeclaration.name(), methodDeclaration.formalParameterList());
		if (declaration instanceof ObjectDeclaration) {
			retval = this.compileObjectDecl((ObjectDeclaration)declaration); 
			runtimeType.addObjectDeclaration(declaration.name(), declarationType);
		} else if (declaration instanceof ContextDeclaration) {
			this.compileContext((ContextDeclaration)declaration);
			assert declarationType instanceof RTContext;
			runtimeType.addContext(declaration.name(), (RTContext)declarationType);
			retval.add(new RTNullExpression());
		} else if (declaration instanceof ClassDeclaration) {
			this.compileClass((ClassDeclaration)declaration);
			assert declarationType instanceof RTClass;
			runtimeType.addClass(declaration.name(), (RTClass)declarationType);
			retval.add(new RTNullExpression());
		} else if (declaration instanceof StagePropDeclaration) {
			final RTStageProp stageProp = new RTStageProp((StagePropDeclaration)declaration);
			this.compileStageProp((StagePropDeclaration)declaration);
			runtimeType.addStageProp(declaration.name(), stageProp);
			retval.add(new RTNullExpression());
		} else if (declaration instanceof RoleDeclaration) {
			final RTRole role = new RTRole((RoleDeclaration)declaration);
			this.compileRole((RoleDeclaration)declaration);
			runtimeType.addRole(declaration.name(), role);
			retval.add(new RTNullExpression());
		} else if (declaration instanceof MethodDeclaration) {
			this.compileMethodInScope((MethodDeclaration)declaration, scope);
			runtimeType.addMethod(declaration.name(), new RTMethod(declaration.name(), (MethodDeclaration)declaration));
			retval.add(new RTNullExpression());
		} else if (declaration instanceof ExprAndDeclList) {
			compileExprAndDeclListForMethodOfTypeInScope((ExprAndDeclList)declaration, runTimeMethodDecl, declarationType, scope);
			retval.add(new RTNullExpression());
		} else if (declaration instanceof DeclarationList) {
			assert false;
		} else if (declaration instanceof TypeDeclarationList) {
			assert false;
		} else {
			retval.add(new RTNullExpression());
		}
		return retval;
	}
	private void compileExprAndDeclListForMethodOfTypeInScope(ExprAndDeclList declaration, RTMethod runTimeMethodDecl,
			RTType declarationType, StaticScope scope) {
		final List<BodyPart> bodyParts = declaration.bodyParts();
		this.compileBodyPartsForMethodOfTypeInScope(bodyParts, runTimeMethodDecl, declarationType, scope);
	}
	private void compileBodyPartsForMethodOfTypeInScope(List<BodyPart> bodyParts, RTMethod rtMethod, RTType rtTypeDeclaration, StaticScope scope) {
		for (BodyPart bodyPart : bodyParts) {
			final List<RTCode> code = this.compileBodyPartForMethodOfTypeInScope(bodyPart, rtMethod, rtTypeDeclaration, scope);
			rtMethod.addCode(code);
		}
	}
	public List<RTCode> compileExpressionForMethodOfTypeInScope(Expression expression, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		// This boomerangs and comes back to the methods defined below
		// We use Expression as the dispatcher
		return expression.compileCodeForInScope(this, methodDeclaration, rtTypeDeclaration, scope);
	}
	public List<RTCode> compileQualifiedIdentifierExpression(QualifiedIdentifierExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTQualifiedIdentifier(expr.name(), expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileQualifiedIdentifierExpressionUnaryOp(QualifiedIdentifierExpressionUnaryOp expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTQualifiedIdentifierUnaryOp(expr.name(), expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileQualifiedClassMemberExpression(QualifiedClassMemberExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		assert false; 	// unreachable?
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTClassMemberIdentifier(expr.name(), expr));
		return retval;
	}
	public List<RTCode> compileQualifiedClassMemberExpressionUnaryOp(QualifiedClassMemberExpressionUnaryOp expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		assert false; 	// never executed?
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTClassMemberIdentifierUnaryOp(expr.name(), expr));
		return retval;
	}
	public List<RTCode> compileMessageExpression(MessageExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTMessage(expr.name(), expr, rtTypeDeclaration, scope, expr.isStatic()));
		return retval;
	}
	public List<RTCode> compileDupMessageExpression(DupMessageExpression expr, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTDupMessage(expr.name(), expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileIdentifierExpression(IdentifierExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTIdentifier(expr.name(), expr));
		return retval;
	}
	public List<RTCode> compileRelopExpression(RelopExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTRelop(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileBooleanExpression(BooleanExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTBoolean(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileBinopExpression(BinopExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTBinop(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileUnaryopExpressionWithSideEffect(UnaryopExpressionWithSideEffect expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTUnaryopWithSideEffect(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileUnaryAbelianopExpression(UnaryAbelianopExpression expr, String operation, StaticScope scope, RTType rtTypeDeclaration) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTUnaryAbelianop(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileAssignmentExpression(AssignmentExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTAssignment(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileNewExpression(NewExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		assert false;
		// final List<RTCode> retval = new ArrayList<RTCode>();
		// retval.add(new RTNew(expr));
		// return retval;
		return null;
	}
	public List<RTCode> compileNewArrayExpression(NewArrayExpression expr, MethodDeclaration methodDeclaration,
			RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTNewArray(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileArrayExpression(ArrayExpression expr, StaticScope scope) {
		assert false;
		final List<RTCode> retval = new ArrayList<RTCode>();
		// retval.add(new RTArrayExpression(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileArrayIndexExpression(ArrayIndexExpression expr, StaticScope scope, RTType rtTypeDeclaration) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTArrayIndexExpression(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileArrayIndexExpressionUnaryOp(ArrayIndexExpressionUnaryOp expr, StaticScope scope, RTType rtTypeDeclaration)
	{
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTArrayIndexExpressionUnaryOp(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileRoleArrayIndexExpression(RoleArrayIndexExpression expr, RTType nearestEnclosingType, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTRoleArrayIndexExpression(expr, nearestEnclosingType));
		return retval;
	}
	public List<RTCode> compileIfExpression(IfExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTIf(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileForExpression(ForExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		final RTFor newFor = new RTFor(expr, rtTypeDeclaration);
		retval.add(newFor);
		assert null == expr.thingToIterateOver();
		return retval;
	}
	public List<RTCode> compileForIterationExpression(ForExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		final RTForIteration newFor = new RTForIteration(expr, rtTypeDeclaration);
		retval.add(newFor);
		assert null != expr.thingToIterateOver();
		return retval;
	}
	public List<RTCode> compileWhileExpression(WhileExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		final RTWhile newWhile = new RTWhile(expr, rtTypeDeclaration);
		retval.add(newWhile);
		return retval;
	}
	public List<RTCode> compileDoWhileExpression(DoWhileExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		final RTDoWhile newDoWhile = new RTDoWhile(expr, rtTypeDeclaration);
		retval.add(newDoWhile);
		return retval;
	}
	public List<RTCode> compileSwitchExpression(SwitchExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		final RTSwitch newSwitch = new RTSwitch(expr, rtTypeDeclaration);
		retval.add(newSwitch);
		return retval;
	}
	public List<RTCode> compileBreakExpression(BreakExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		assert false;	// ever reached? just curious
		retval.add(new RTBreak(expr));
		return retval;
	}
	public List<RTCode> compileContinueExpression(ContinueExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		assert false;	// ever reached? just curious
		retval.add(new RTContinue(expr));
		return retval;
	}
	public List<RTCode> compileExpressionList(ExpressionList expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTExpressionList(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileSumExpression(SumExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTSum(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileProductExpression(ProductExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTProduct(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compilePowerExpression(PowerExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTPower(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileReturnExpression(ReturnExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		List<RTCode> rTExpr = null;
		if (null != expr) {
			rTExpr = this.compileExpressionForMethodOfTypeInScope(expr, methodDeclaration, rtTypeDeclaration, scope);
			assert null != rTExpr;
		}
		retval.add(new RTReturn(methodDeclaration.name(), rTExpr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileBlockExpression(BlockExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTBlock(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileConstant(Constant expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTConstant(expr));
		return retval;
	}
	public List<RTCode> compilePromoteToDoubleExpression(PromoteToDoubleExpr expr, StaticScope scope, RTType t) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTPromoteToDoubleExpr(expr, t));
		return retval;
	}
	public List<RTCode> compileIndexExpression(IndexExpression indexExpression) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTIndexExpression(indexExpression));
		return retval;
	}
	public List<RTCode> compileBodyPartForMethodOfTypeInScope(BodyPart bodyPart, RTMethod rtMethod, RTType rtTypeDeclaration, StaticScope scope) {
		List<RTCode> retval = new ArrayList<RTCode>();
		if (bodyPart instanceof Declaration) {
			retval = this.compileDeclarationForMethodOfTypeInScope((Declaration) bodyPart, rtMethod.methodDeclaration(),
					rtTypeDeclaration, scope);
		} else if (bodyPart instanceof Expression) {
			retval = this.compileExpressionForMethodOfTypeInScope((Expression) bodyPart, rtMethod.methodDeclaration(),
					rtTypeDeclaration, scope);
		} else {
			retval.add(new RTNullExpression());
		}
		return retval;
	}
	private List<RTCode> compileObjectDecl(ObjectDeclaration objectDeclaration) {
		// Declarations are a side effect on setting up ...
		return new ArrayList<RTCode>();
	}
	private void compileScope(StaticScope scope) {
		for (ClassDeclaration cd : scope.classDeclarations()) {
			this.compileClass(cd);
		}
		for (MethodDeclaration md : scope.methodDeclarations()) {
			this.compileMethodInScope(md, scope);
		}
		for (ObjectDeclaration od : scope.objectDeclarations()) {
			this.compileObjectDecl(od);
		}
		
		// There's some kind of problem that we need to do this.
		// FIXME.
		for (RoleDeclaration rd : scope.roleDeclarations()) {
			if (rd instanceof StagePropDeclaration == false) {
				this.compileRole(rd);
			}
		}
		for (StagePropDeclaration spd : scope.stagePropDeclarations()) {
			if (spd instanceof StagePropDeclaration == true) {
				this.compileStageProp(spd);
			}
		}
		for (ContextDeclaration cd : scope.contextDeclarations()) {
			this.compileContext(cd);
		}
	}
	
	public static RTType TypeDeclarationToRTTypeDeclaration(TypeDeclaration typeDeclaration) {
		final StaticScope enclosedScope = typeDeclaration.enclosedScope();
		return InterpretiveCodeGenerator.scopeToRTTypeDeclaration(enclosedScope);
	}
	private static RTType lookInGlobalScopeForRTTypeDeclaration(StaticScope enclosedScope) {
		RTType retval = null;
		final Stack<StaticScope> scopeStack = new Stack<StaticScope>();
		StaticScope scope = enclosedScope;
		do {
			scopeStack.push(scope);
			scope = scope.parentScope();
		} while (scope != StaticScope.globalScope());
		
		// Pop the top one back off (the one at the top of the declaration
		// tree). 
		scope = scopeStack.pop();
		Declaration associatedDeclaration = scope.associatedDeclaration();
		final String associatedDeclarationName = associatedDeclaration.name();
		
		// ... and see if it's already in the top-level
		// type declarations for the run-time environment
		if (null == (retval = RunTimeEnvironment.runTimeEnvironment_.topLevelTypeNamed(associatedDeclarationName))) {
			if (associatedDeclaration instanceof ClassDeclaration) {
				final RTClass classDeclaration = new RTClass((ClassDeclaration)associatedDeclaration);
				RunTimeEnvironment.runTimeEnvironment_.addTopLevelClass(associatedDeclaration.name(), classDeclaration);
			} else if (associatedDeclaration instanceof ContextDeclaration) {
				final RTContext contextDeclaration = new RTContext((ContextDeclaration)associatedDeclaration);
				RunTimeEnvironment.runTimeEnvironment_.addTopLevelContext(associatedDeclaration.name(), contextDeclaration);
			} else {
				// No Roles, StageProps at top level
				assert false;
			}
			retval = RunTimeEnvironment.runTimeEnvironment_.topLevelTypeNamed(associatedDeclaration.name());
		}
		
		// For a simple top-level type, this loop fails to execute
		while (scopeStack.size() > 0) {
			scope = scopeStack.pop();
			associatedDeclaration = scope.associatedDeclaration();
			RTType tempRetval = retval.typeNamed(associatedDeclaration.name());
			if (tempRetval == null) {
				if (associatedDeclaration instanceof ClassDeclaration) {
					tempRetval = new RTClass((ClassDeclaration)associatedDeclaration);
					retval.addClass(associatedDeclaration.name(), (RTClass)tempRetval);
				} else if (associatedDeclaration instanceof ContextDeclaration) {
					tempRetval = new RTContext((ContextDeclaration)associatedDeclaration);
					retval.addContext(associatedDeclaration.name(), (RTContext)tempRetval);
				} else if (associatedDeclaration instanceof StagePropDeclaration) {
					tempRetval = new RTStageProp((StagePropDeclaration)associatedDeclaration);
					retval.addStageProp(associatedDeclaration.name(), (RTStageProp)tempRetval);
				} else if (associatedDeclaration instanceof RoleDeclaration) {
					tempRetval = new RTRole((RoleDeclaration)associatedDeclaration);
					retval.addRole(associatedDeclaration.name(), (RTRole)tempRetval);
				} else {
					assert false;
				}
				assert null != tempRetval;
				retval = tempRetval;
			}
			
			assert null != retval;
		}
		
		return retval;
	}
	private static RTType lookInTopLevelTypeForRTTypeDeclaration(StaticScope enclosedScope) {
		RTType retval = null;

		final TypeDeclaration typeDeclaration = (TypeDeclaration)enclosedScope.associatedDeclaration();
		assert typeDeclaration instanceof TypeDeclaration;
		
		retval = RunTimeEnvironment.runTimeEnvironment_.topLevelTypeNamed(typeDeclaration.name());
		if (null == retval) {
			if (typeDeclaration instanceof ClassDeclaration) {
				final RTClass classDeclaration = new RTClass(typeDeclaration);
				RunTimeEnvironment.runTimeEnvironment_.addTopLevelClass(typeDeclaration.name(), classDeclaration);
			} else if (typeDeclaration instanceof ContextDeclaration) {
				final RTContext contextDeclaration = new RTContext(typeDeclaration);
				RunTimeEnvironment.runTimeEnvironment_.addTopLevelContext(typeDeclaration.name(), contextDeclaration);
			} else {
				assert false;
			}
			
			retval = InterpretiveCodeGenerator.TypeDeclarationToRTTypeDeclaration(typeDeclaration);
			
			assert null != retval;
		}
		return retval;
	}
	public static RTType scopeToRTTypeDeclaration(StaticScope enclosedScope) {
		RTType retval = null;
		if (null == enclosedScope) {
			assert null != enclosedScope;
		}
		final StaticScope enclosingScope = enclosedScope.parentScope();
		
		final String scopePathName = enclosedScope.pathName();
		RTType aType = null;
		if (null != (aType = RunTimeEnvironment.runTimeEnvironment_.typeFromPath(scopePathName))) {
			retval = aType;
		} else if (enclosingScope != StaticScope.globalScope()) {
			retval = InterpretiveCodeGenerator.lookInGlobalScopeForRTTypeDeclaration(enclosedScope);
			RunTimeEnvironment.runTimeEnvironment_.registerTypeByPath(scopePathName, retval);
			assert null != retval;
		} else {
			// Top-level
			assert enclosingScope == StaticScope.globalScope();
			retval = InterpretiveCodeGenerator.lookInTopLevelTypeForRTTypeDeclaration(enclosedScope);
			assert null != retval;
		}
		
		assert retval != null;
		return retval;
	}
	public ParsingData parsingData() {
		return parsingData_;
	}
	
	private Program program_;
	private RunTimeEnvironment virtualMachine_;
	private RTExpression rTMainExpr_;
	private final ParsingData parsingData_;
}
