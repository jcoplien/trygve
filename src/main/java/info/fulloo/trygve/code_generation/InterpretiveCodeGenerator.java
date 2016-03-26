package info.fulloo.trygve.code_generation;

/*
 * Trygve IDE 1.6
 *   Copyright (c)2016 James O. Coplien, jcoplien@gmail.com
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

import info.fulloo.trygve.add_ons.ColorClass;
import info.fulloo.trygve.add_ons.DateClass;
import info.fulloo.trygve.add_ons.ListClass;
import info.fulloo.trygve.add_ons.MapClass;
import info.fulloo.trygve.add_ons.MathClass;
import info.fulloo.trygve.add_ons.FrameClass;
import info.fulloo.trygve.add_ons.PanelClass;
import info.fulloo.trygve.add_ons.ScannerClass;
import info.fulloo.trygve.add_ons.SetClass;
import info.fulloo.trygve.add_ons.SystemClass;
import info.fulloo.trygve.configuration.ConfigurationOptions;
import info.fulloo.trygve.declarations.ActualOrFormalParameterList;
import info.fulloo.trygve.declarations.BodyPart;
import info.fulloo.trygve.declarations.Declaration;
import info.fulloo.trygve.declarations.Declaration.InterfaceDeclaration;
import info.fulloo.trygve.declarations.Declaration.ObjectSubclassDeclaration;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.TemplateInstantiationInfo;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Type.ArrayType;
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
import info.fulloo.trygve.editor.InputStreamClass;
import info.fulloo.trygve.editor.TextEditorGUI;
import info.fulloo.trygve.error.ErrorLogger;
import info.fulloo.trygve.error.ErrorLogger.ErrorIncidenceType;
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
import info.fulloo.trygve.expressions.Expression.DoubleCasterExpression;
import info.fulloo.trygve.expressions.Expression.DupMessageExpression;
import info.fulloo.trygve.expressions.Expression.ErrorExpression;
import info.fulloo.trygve.expressions.Expression.ExpressionList;
import info.fulloo.trygve.expressions.Expression.ForExpression;
import info.fulloo.trygve.expressions.Expression.IdentifierExpression;
import info.fulloo.trygve.expressions.Expression.IdentityBooleanExpression;
import info.fulloo.trygve.expressions.Expression.InternalAssignmentExpression;
import info.fulloo.trygve.expressions.Expression.IfExpression;
import info.fulloo.trygve.expressions.Expression.IndexExpression;
import info.fulloo.trygve.expressions.Expression.LastIndexExpression;
import info.fulloo.trygve.expressions.Expression.MessageExpression;
import info.fulloo.trygve.expressions.Expression.NewArrayExpression;
import info.fulloo.trygve.expressions.Expression.NewExpression;
import info.fulloo.trygve.expressions.Expression.NullExpression;
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
import info.fulloo.trygve.expressions.Expression.TopOfStackExpression;
import info.fulloo.trygve.expressions.Expression.UnaryAbelianopExpression;
import info.fulloo.trygve.expressions.Expression.UnaryopExpressionWithSideEffect;
import info.fulloo.trygve.expressions.Expression.WhileExpression;
import info.fulloo.trygve.parser.ParsingData;
import info.fulloo.trygve.run_time.RTClass;
import info.fulloo.trygve.run_time.RTClass.*;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTContext;
import info.fulloo.trygve.run_time.RTExpression;
import info.fulloo.trygve.run_time.RTInterface;
import info.fulloo.trygve.run_time.RTMethod;
import info.fulloo.trygve.run_time.RTRole;
import info.fulloo.trygve.run_time.RTStageProp;
import info.fulloo.trygve.run_time.RTType;
import info.fulloo.trygve.run_time.RunTimeEnvironment;
import info.fulloo.trygve.run_time.RTClass.RTObjectClass;
import info.fulloo.trygve.run_time.RTExpression.*;
import info.fulloo.trygve.semantic_analysis.Program;
import info.fulloo.trygve.semantic_analysis.StaticScope;

public class InterpretiveCodeGenerator implements CodeGenerator {
	private enum RetvalTypes { usingInt, usingBool, usingDouble, usingTemplate,
							   usingString, usingColor, none, undefined };
	
	public static InterpretiveCodeGenerator interpretiveCodeGenerator = null;
	private static void setStaticHandle(final InterpretiveCodeGenerator justThis) {
		interpretiveCodeGenerator = justThis;
	}
	public InterpretiveCodeGenerator(final Program program, final ParsingData parsingData, final TextEditorGUI gui) {
		super();
		program_ = program;
		parsingData_ = parsingData;
		virtualMachine_ = new RunTimeEnvironment(gui);
		setStaticHandle(this);
	}
	@Override public RunTimeEnvironment virtualMachine() {
		return virtualMachine_;
	}
	@Override public RTExpression mainExpr() {
		return rTMainExpr_;
	}
	@Override public void compile() {
		// WARNING: Order of compilations may be important!
		
		List<TypeDeclaration> typeDeclarationList = InputStreamClass.typeDeclarationList();	// "InputStream". Must come before System
		compileDeclarations(typeDeclarationList);
		
		typeDeclarationList = SystemClass.typeDeclarationList();
		compileDeclarations(typeDeclarationList);
		
		typeDeclarationList = StaticScope.typeDeclarationList();	// "String", others
		compileDeclarations(typeDeclarationList);
		
		typeDeclarationList = ListClass.typeDeclarationList();	// "List"
		compileDeclarations(typeDeclarationList);

		typeDeclarationList = SetClass.typeDeclarationList();	// "Set"
		compileDeclarations(typeDeclarationList);
		
		typeDeclarationList = MathClass.typeDeclarationList();	// "Math"
		compileDeclarations(typeDeclarationList);
		
		typeDeclarationList = DateClass.typeDeclarationList();	// "Date"
		compileDeclarations(typeDeclarationList);
		
		typeDeclarationList = ScannerClass.typeDeclarationList();	// "Scanner"
		compileDeclarations(typeDeclarationList);
		
		typeDeclarationList = ColorClass.typeDeclarationList();	// "Color"
		compileDeclarations(typeDeclarationList);
		
		typeDeclarationList = PanelClass.typeDeclarationList();	// "Panel"
		compileDeclarations(typeDeclarationList);
		
		typeDeclarationList = FrameClass.typeDeclarationList();	// "Frame"
		compileDeclarations(typeDeclarationList);
				
		TypeDeclarationList typeDeclarationListWrapper = program_.theRest();
		typeDeclarationList = typeDeclarationListWrapper.declarations();
		compileDeclarations(typeDeclarationList);
		
		typeDeclarationListWrapper = program_.templateInstantiations();
		typeDeclarationList = typeDeclarationListWrapper.declarations();
		compileDeclarations(typeDeclarationList);
		
		compileMain();
		
		if (ConfigurationOptions.printClassMethodDecls()) {
			printClassMethodDecls();
		}
		if (ConfigurationOptions.printContextMethodDecls()) {
			printContextMethodDecls();
		}
	}
	private void printClassMethodDecls() {
		final StaticScope globalScope = StaticScope.globalScope();
		globalScope.printClassMethodDecls();
	}
	private void printContextMethodDecls() {
		final StaticScope globalScope = StaticScope.globalScope();
		globalScope.printContextMethodDecls();
	}
	private void compileDeclarations(final List<TypeDeclaration> typeDeclarationList) {
		if (null == typeDeclarationList) {
			assert null != typeDeclarationList;
		}
		for (final TypeDeclaration a : typeDeclarationList) {
			if (a instanceof ContextDeclaration) {
				this.compileContext((ContextDeclaration)a);
			} else if (a instanceof ClassDeclaration) {
				this.compileClass((ClassDeclaration)a);
			} else if (a instanceof StagePropDeclaration) {
				assert false;	// ever get here?
				// this.compileStageProp((StagePropDeclaration)a);
			} else if (a instanceof RoleDeclaration) {
				assert false;	// ever get here?
				// this.compileRole((RoleDeclaration)a);
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
				RTClass rTClassDeclaration = null;
				
				// Some classes have special hooks. For example, SystemClass has two statics
				// (System.err and System.out) that need to be initialized. We use the
				// postSetupInitialization method to do that, and it is overridden
				// in RTSystemClass
				if (classDeclaration.type().pathName().equals("System.")) {
					rTClassDeclaration = new RTSystemClass(classDeclaration);
				} else if (classDeclaration.type().pathName().equals("Event.")) {
					rTClassDeclaration = new PanelClass.RTEventClass(classDeclaration);
				} else if (classDeclaration.type().pathName().equals("Color.")) {
					rTClassDeclaration = new ColorClass.RTColorClass(classDeclaration);
				} else {
					// Kludge. But it's direct, and effective.
					rTClassDeclaration = new RTClass(classDeclaration);
				}
				RunTimeEnvironment.runTimeEnvironment_.addTopLevelClass(classDeclaration.name(), rTClassDeclaration);
			}
		}
		final StaticScope myScope = classDeclaration.enclosedScope();
		this.compileScope(myScope);
	}
	private void compileInterface(final InterfaceDeclaration interfaceDeclaration) {
		// Really nothing to compile - all interface logic should
		// be absorbed by semantic analysis
		if (null == RunTimeEnvironment.runTimeEnvironment_.topLevelTypeNamed(interfaceDeclaration.name())) {
			if (interfaceDeclaration.enclosingScope() == StaticScope.globalScope()) {
				// Kludge. But it's direct, and effective.
				final RTInterface rTInterfaceDeclaration = new RTInterface(interfaceDeclaration);
				RunTimeEnvironment.runTimeEnvironment_.addTopLevelInterface(interfaceDeclaration.name(), rTInterfaceDeclaration);
			}
		}
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
	

	private void addReturn(final MethodDeclaration methodDeclaration,
			final RetvalTypes retvalType, final List<RTCode> codeVector) {
		final int sizeOfCodeArray = codeVector.size();
		assert (sizeOfCodeArray > 0);
		RTCode last = codeVector.get(sizeOfCodeArray - 1);
		final StaticScope myScope = methodDeclaration.enclosedScope();
		final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(myScope);
		final RTType rTEnclosingMegaType = scopeToRTTypeDeclaration(enclosingMegaType.enclosedScope());
		
		ReturnExpression returnExpression = null;
		RTCode returnStatement = new RTReturn("error", (Expression)null, null, 0);
		switch (retvalType) {
		case usingDouble:
		case usingBool:
		case usingInt:
		case usingTemplate:
		case usingString:
		case usingColor:
			final IdentifierExpression retval = new IdentifierExpression("ret$val", methodDeclaration.returnType(),
					methodDeclaration.enclosedScope(), methodDeclaration.lineNumber());
			returnExpression = new ReturnExpression(methodDeclaration.name(), retval, methodDeclaration.lineNumber(),
					retval.type(), StaticScope.globalScope());
			returnStatement = new RTReturn(methodDeclaration.name(), returnExpression.returnExpression(),
					rTEnclosingMegaType,
					returnExpression.nestingLevelInsideMethod());
			break;
		case none:
			returnStatement = new RTReturn(methodDeclaration.name(), (Expression)null,
					rTEnclosingMegaType,
					(int)0);
			break;
		case undefined:
		default:
			assert false;
		}
		returnStatement.setNextCode(last.nextCode());
		last.setNextCode(returnStatement);
		codeVector.add(returnStatement);
	}
	private void processListMethodDefinition(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		RetvalTypes retvalType;
		
		final RTType rtListTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
		assert null != rtListTypeDeclaration;
		final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
		rtListTypeDeclaration.addMethod(rtMethod.name(), rtMethod);
		final List<RTCode> listCode = new ArrayList<RTCode>();
		if (methodDeclaration.name().equals("List")) {
			listCode.add(new ListClass.RTListCtorCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.none;
		} else if (methodDeclaration.name().equals("size")) {
			listCode.add(new ListClass.RTSizeCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.usingInt;
		} else if (methodDeclaration.name().equals("add")) {
			listCode.add(new ListClass.RTAddCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.none;
		} else if (methodDeclaration.name().equals("get")) {
			listCode.add(new ListClass.RTGetCode(methodDeclaration.enclosedScope(),
					methodDeclaration.lineNumber()));
			retvalType = RetvalTypes.usingTemplate;
		} else if (methodDeclaration.name().equals("indexOf")) {
			listCode.add(new ListClass.RTIndexOfCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.usingInt;
		} else if (methodDeclaration.name().equals("contains")) {
			listCode.add(new ListClass.RTContainsCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.usingBool;
		} else if (methodDeclaration.name().equals("isEmpty")) {
			listCode.add(new ListClass.RTIsEmptyCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.usingBool;
		} else if (methodDeclaration.name().equals("sort")) {
			listCode.add(new ListClass.RTSortCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.none;
		} else if (methodDeclaration.name().equals("remove")) {
			if (methodDeclaration.returnType().name().equals("boolean")) {
				listCode.add(new ListClass.RTRemoveTCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingBool;
			} else {
				listCode.add(new ListClass.RTRemoveICode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingTemplate;
			}
		} else {
			assert false;	// error message instead? Should be caught earlier
			retvalType = RetvalTypes.undefined;
		}
		
		addReturn(methodDeclaration, retvalType, listCode);
		
		rtMethod.addCode(listCode);
	}
	private void processSetMethodDefinition(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		RetvalTypes retvalType;
		
		final RTType rtListTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
		assert null != rtListTypeDeclaration;
		final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
		rtListTypeDeclaration.addMethod(rtMethod.name(), rtMethod);
		final List<RTCode> listCode = new ArrayList<RTCode>();
		if (methodDeclaration.name().equals("Set")) {
			listCode.add(new SetClass.RTSetCtorCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.none;
		} else if (methodDeclaration.name().equals("size")) {
			listCode.add(new SetClass.RTSizeCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.usingInt;
		} else if (methodDeclaration.name().equals("add")) {
			listCode.add(new SetClass.RTAddCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.none;
		} else if (methodDeclaration.name().equals("contains")) {
			listCode.add(new SetClass.RTContainsCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.usingBool;
		} else if (methodDeclaration.name().equals("isEmpty")) {
			listCode.add(new SetClass.RTIsEmptyCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.usingBool;
		} else if (methodDeclaration.name().equals("remove")) {
			listCode.add(new SetClass.RTRemoveTCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.usingTemplate;
		} else {
			assert false;	// error message instead? Should be caught earlier
			retvalType = RetvalTypes.undefined;
		}
		
		addReturn(methodDeclaration, retvalType, listCode);
		
		rtMethod.addCode(listCode);
	}
	private void processMapMethodDefinition(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		final RTType rtMapTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
		assert null != rtMapTypeDeclaration;
		RetvalTypes retvalType = RetvalTypes.undefined;
		final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
		rtMapTypeDeclaration.addMethod(rtMethod.name(), rtMethod);
		final List<RTCode> mapCode = new ArrayList<RTCode>();
		if (methodDeclaration.name().equals("Map")) {
			mapCode.add(new MapClass.RTMapCtorCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.none;
		} else if (methodDeclaration.name().equals("size")) {
			mapCode.add(new MapClass.RTSizeCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.usingInt;
		} else if (methodDeclaration.name().equals("put")) {
			mapCode.add(new MapClass.RTPutCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.none;
		} else if (methodDeclaration.name().equals("putAll")) {
			mapCode.add(new MapClass.RTPutAllCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.none;
		} else if (methodDeclaration.name().equals("get")) {
			mapCode.add(new MapClass.RTGetCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.usingTemplate;
		} else if (methodDeclaration.name().equals("remove")) {
			mapCode.add(new MapClass.RTRemoveCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.usingTemplate;
		} else if (methodDeclaration.name().equals("containsKey")) {
			mapCode.add(new MapClass.RTContainsKeyCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.usingBool;
		} else if (methodDeclaration.name().equals("containsValue")) {
			mapCode.add(new MapClass.RTContainsValueCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.usingBool;
		} else {
			retvalType = RetvalTypes.undefined;
			assert false;	// error message instead? Should be caught earlier
		}
		
		addReturn(methodDeclaration, retvalType, mapCode);
		
		assert mapCode.size() > 0;
		
		rtMethod.addCode(mapCode);
	}
	private void processMathMethodDefinition(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		RetvalTypes retvalType;
		final RTType rtMathTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
		assert null != rtMathTypeDeclaration;
		final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
		rtMathTypeDeclaration.addMethod(rtMethod.name(), rtMethod);
		
		final FormalParameterList formalParameterList = methodDeclaration.formalParameterList();
		final Type firstParamType = formalParameterList.count() > 1?
				formalParameterList.typeOfParameterAtPosition(1):
					null;
		boolean firstParameterIsInteger = (null != firstParamType) &&
				(firstParamType.pathName().equals("int.") ||
						firstParamType.pathName().equals("Integer."));
		
		final List<RTCode> mathCode = new ArrayList<RTCode>();
		if (methodDeclaration.name().equals("Math")) {
			ErrorLogger.error(ErrorIncidenceType.Fatal, "Cannot instantiate class Math", "", "", "");
			retvalType = RetvalTypes.none;
		} else if (methodDeclaration.name().equals("random")) {
			mathCode.add(new MathClass.RTRandomCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.usingDouble;
		} else if (methodDeclaration.name().equals("abs")) {
			if (firstParameterIsInteger) {
				mathCode.add(new MathClass.RTIntAbsCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingInt;
			} else {
				mathCode.add(new MathClass.RTRealAbsCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingDouble;
			}
		} else if (methodDeclaration.name().equals("max")) {
			if (firstParameterIsInteger) {
				mathCode.add(new MathClass.RTIntMaxCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingInt;
			} else {
				mathCode.add(new MathClass.RTRealMaxCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingDouble;
			}
		} else if (methodDeclaration.name().equals("min")) {
			if (firstParameterIsInteger) {
				mathCode.add(new MathClass.RTIntMinCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingInt;
			} else {
				mathCode.add(new MathClass.RTRealMinCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingDouble;
			}
		} else if (methodDeclaration.name().equals("sqrt")) {
			mathCode.add(new MathClass.RTSqrtCode(methodDeclaration.enclosedScope()));
			retvalType = RetvalTypes.usingDouble;
		} else {
			retvalType = RetvalTypes.none;
			assert false;	// error message instead? Should be caught earlier
		}
		
		addReturn(methodDeclaration, retvalType, mathCode);
		
		rtMethod.addCode(mathCode);
	}
	private void processPrintStreamMethodDefinition(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		final FormalParameterList formalParameterList = methodDeclaration.formalParameterList();
		final List<RTCode> printlnCode = new ArrayList<RTCode>();
		RTMethod rtMethod = null;
		if (formalParameterList.count() == 2) {
			final ObjectDeclaration printableArgumentDeclaration = formalParameterList.parameterAtPosition(1);
			final Type printableArgumentType = printableArgumentDeclaration.type();
			final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
			assert null != rtTypeDeclaration;
			rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
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
			
		} else if (1 == formalParameterList.count()) {
			final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
			assert null != rtTypeDeclaration;
			rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
			if (methodDeclaration.name().equals("println")) {
				printlnCode.add(new SystemClass.RTPrintlnCode(methodDeclaration.enclosedScope()));
			} else {
				assert false;
			}
		} else if (formalParameterList.count() == 3) {
			final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
			assert null != rtTypeDeclaration;
			rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
			if (methodDeclaration.name().equals("format")) {
				printlnCode.add(new SystemClass.RTFormatCode(methodDeclaration.enclosedScope()));
			} else {
				assert false;
			}
		} else {
			assert false;
		}
		
		final int sizeOfCodeArray = printlnCode.size();
		assert (sizeOfCodeArray > 0);
		RTCode last = printlnCode.get(sizeOfCodeArray - 1);
		final IdentifierExpression self = new IdentifierExpression("this", methodDeclaration.returnType(),
				methodDeclaration.enclosedScope(), methodDeclaration.lineNumber());
		final ReturnExpression returnExpression = new ReturnExpression(
				methodDeclaration.name(),
				self, methodDeclaration.lineNumber(),
				self.type(), StaticScope.globalScope());
		final StaticScope myScope = methodDeclaration.enclosedScope();
		final Type enclosingMegaType = Expression.nearestEnclosingMegaTypeOf(myScope);
		final RTType rTEnclosingMegaType = scopeToRTTypeDeclaration(enclosingMegaType.enclosedScope());
		final RTCode returnStatement = new RTReturn(methodDeclaration.name(),
				returnExpression.returnExpression(), rTEnclosingMegaType,
				returnExpression.nestingLevelInsideMethod());
		returnStatement.setNextCode(last.nextCode());
		last.setNextCode(returnStatement);
		printlnCode.add(returnStatement);
		
		assert printlnCode.size() > 0;
		
		rtMethod.addCode(printlnCode);
	}
	
	private void processInputStreamMethodDefinition(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		final FormalParameterList formalParameterList = methodDeclaration.formalParameterList();
		final List<RTCode> readCode = new ArrayList<RTCode>();
		RTMethod rtMethod = null;
		RetvalTypes retvalType = RetvalTypes.undefined;
		
		final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
		assert null != rtTypeDeclaration;
		rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
		rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
		
		if (formalParameterList.count() == 1) {
			if (methodDeclaration.name().equals("read")) {
				readCode.add(new InputStreamClass.RTReadCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingInt;
			} else {
				assert false;
			}
		} else if (formalParameterList.count() == 2) {
			if (methodDeclaration.name().equals("InputStream")) {
				readCode.add(new InputStreamClass.RTInputStreamCtor1Code(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.none;
			} else {
				assert false;
			}
		} else {
			assert false;
		}
		
		addReturn(methodDeclaration, retvalType, readCode);
		
		rtMethod.addCode(readCode);
	}
	private void processPanelMethodDefinition(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		final FormalParameterList formalParameterList = methodDeclaration.formalParameterList();
		final List<RTCode> panelCode = new ArrayList<RTCode>();
		RTMethod rtMethod = null;
		RetvalTypes retvalType = RetvalTypes.none;
		
		final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
		assert null != rtTypeDeclaration;
		rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
		
		if (formalParameterList.count() == 1) {
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
			
			if (methodDeclaration.name().equals("Panel")) {
				panelCode.add(new PanelClass.RTPanelCtorCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.none;
			} else if (methodDeclaration.name().equals("removeAll")) {
				panelCode.add(new PanelClass.RTRemoveAllCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.none;
			} else if (methodDeclaration.name().equals("repaint")) {
				panelCode.add(new PanelClass.RTRepaintCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.none;
			} else if (methodDeclaration.name().equals("clear")) {
				panelCode.add(new PanelClass.RTClearCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.none;
			} else if (methodDeclaration.name().equals("getBackground")) {
				panelCode.add(new PanelClass.RTGetBackgroundCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingColor;
			} else if (methodDeclaration.name().equals("getForeground")) {
				panelCode.add(new PanelClass.RTGetForegroundCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingColor;
			} else {
				assert false;
			}
		} else if (formalParameterList.count() == 2) {
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
		
			if (methodDeclaration.name().equals("setBackground")) {
				panelCode.add(new PanelClass.RTSetBackgroundCode(methodDeclaration.enclosedScope()));
			} else if (methodDeclaration.name().equals("setForeground")) {
				panelCode.add(new PanelClass.RTSetForegroundCode(methodDeclaration.enclosedScope()));
			} else if (methodDeclaration.name().equals("remove")) {
				panelCode.add(new PanelClass.RTRemoveCode(methodDeclaration.enclosedScope()));
			} else {
				assert false;
			}
			retvalType = RetvalTypes.none;
		} else if (formalParameterList.count() == 4) {
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
			retvalType = RetvalTypes.none;
			
			if (methodDeclaration.name().equals("drawString")) {
				panelCode.add(new PanelClass.RTDrawStringCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingString;
			} else {
				assert false;
			}
		} else if (formalParameterList.count() == 5) {
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
			
			if (methodDeclaration.name().equals("drawLine")) {
				panelCode.add(new PanelClass.RTDrawLineCode(methodDeclaration.enclosedScope()));
			} else if (methodDeclaration.name().equals("drawRect")) {
				panelCode.add(new PanelClass.RTDrawRectCode(methodDeclaration.enclosedScope()));
			} else if (methodDeclaration.name().equals("fillRect")) {
				panelCode.add(new PanelClass.RTFillRectCode(methodDeclaration.enclosedScope()));
			} else if (methodDeclaration.name().equals("drawOval")) {
				panelCode.add(new PanelClass.RTDrawEllipseCode(methodDeclaration.enclosedScope()));
			} else if (methodDeclaration.name().equals("fillOval")) {
				panelCode.add(new PanelClass.RTFillEllipseCode(methodDeclaration.enclosedScope()));
			} else {
				assert false;
			}
			retvalType = RetvalTypes.none;
		} else {
			assert false;
		}
		
		addReturn(methodDeclaration, retvalType, panelCode);
		
		rtMethod.addCode(panelCode);
	}
	private void processFrameMethodDefinition(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		final FormalParameterList formalParameterList = methodDeclaration.formalParameterList();
		final List<RTCode> readCode = new ArrayList<RTCode>();
		RTMethod rtMethod = null;
		
		final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
		assert null != rtTypeDeclaration;
		rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
		
		if (formalParameterList.count() == 1) {
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
			
			if (methodDeclaration.name().equals("show")) {
				readCode.add(new FrameClass.RTShowCode(methodDeclaration.enclosedScope()));
			} else {
				assert false;
			}
		} else if (formalParameterList.count() == 2) {
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
			
			if (methodDeclaration.name().equals("Frame")) {
				readCode.add(new FrameClass.RTFrameCtorCode(methodDeclaration.enclosedScope()));
			} else if (methodDeclaration.name().equals("setVisible")) {
				readCode.add(new FrameClass.RTSetVisibleCode(methodDeclaration.enclosedScope()));
			} else {
				assert false;
			}
		} else if (formalParameterList.count() == 3) {
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
		
			if (methodDeclaration.name().equals("add")) {
				readCode.add(new FrameClass.RTAddCode(methodDeclaration.enclosedScope()));
			} else if (methodDeclaration.name().equals("resize")) {
				readCode.add(new FrameClass.RTResizeCode(methodDeclaration.enclosedScope()));
			} else if (methodDeclaration.name().equals("setSize")) {
				readCode.add(new FrameClass.RTSetSizeCode(methodDeclaration.enclosedScope()));
			} else {
				assert false;
			}
		} else {
			assert false;
		}
		
		// Yes, it's always "none."
		addReturn(methodDeclaration, RetvalTypes.none, readCode);
		
		rtMethod.addCode(readCode);
	}
	private void processEventMethodDefinition(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		final FormalParameterList formalParameterList = methodDeclaration.formalParameterList();
		final List<RTCode> eventCode = new ArrayList<RTCode>();
		RTMethod rtMethod = null;
		
		final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
		assert null != rtTypeDeclaration;
		rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
		
		if (formalParameterList.count() == 1) {
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
			
			if (methodDeclaration.name().equals("Event")) {
				eventCode.add(new PanelClass.EventClass.RTEventCtorCode(methodDeclaration.enclosedScope()));
			} else {
				assert false;
			}
		} else {
			assert false;
		}
		
		addReturn(methodDeclaration, RetvalTypes.none, eventCode);
		
		rtMethod.addCode(eventCode);
	}
	private void processColorMethodDefinition(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		final FormalParameterList formalParameterList = methodDeclaration.formalParameterList();
		final List<RTCode> colorMethodCode = new ArrayList<RTCode>();
		RetvalTypes retvalType = RetvalTypes.none;
		RTMethod rtMethod = null;
		
		final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
		assert null != rtTypeDeclaration;
		rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
		rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
		
		if (formalParameterList.count() == 4) {
			if (methodDeclaration.name().equals("Color")) {
				final Type elementsParamType = formalParameterList.typeOfParameterAtPosition(1);
				if (elementsParamType.pathName().equals("int.") || elementsParamType.pathName().equals("Integer.")) {
					colorMethodCode.add(new ColorClass.RTColorCtor1Code(methodDeclaration.enclosedScope()));
				} else {
					colorMethodCode.add(new ColorClass.RTColorCtor2Code(methodDeclaration.enclosedScope()));
				}
			} else {
				assert false;
			}
		} else if (formalParameterList.count() == 1) {
			if (methodDeclaration.name().equals("getRed")) {
				retvalType = RetvalTypes.usingInt;
				colorMethodCode.add(new ColorClass.RTGetRedCode(methodDeclaration.enclosedScope()));
			} else if (methodDeclaration.name().equals("getGreen")) {
				retvalType = RetvalTypes.usingInt;
				colorMethodCode.add(new ColorClass.RTGetGreenCode(methodDeclaration.enclosedScope()));
			} else if (methodDeclaration.name().equals("getBlue")) {
				retvalType = RetvalTypes.usingInt;
				colorMethodCode.add(new ColorClass.RTGetBlueCode(methodDeclaration.enclosedScope()));
			} else {
				assert false;
			}
		} else {
			assert false;
		}
		
		addReturn(methodDeclaration, retvalType, colorMethodCode);
		
		rtMethod.addCode(colorMethodCode);
	}
	private void processDateMethodDefinition(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		final FormalParameterList formalParameterList = methodDeclaration.formalParameterList();
		RetvalTypes retvalType;
		
		final List<RTCode> getSomethingInDateCode = new ArrayList<RTCode>();
		
		final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
		assert null != rtTypeDeclaration;
		final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
		rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
		
		if (formalParameterList.count() == 4) {
			// probably the constructor
			if (methodDeclaration.name().equals("Date")) {
				retvalType = RetvalTypes.none;
				getSomethingInDateCode.add(new DateClass.RTDateCtorCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.none;
			} else {
				retvalType = RetvalTypes.undefined;
				assert false;
			}
		} else if (formalParameterList.count() == 2) {
			final ObjectDeclaration argumentDeclaration = formalParameterList.parameterAtPosition(1);
			final Type argumentType = argumentDeclaration.type();

			if (argumentType.name().equals("int") || argumentType.name().equals("Integer")) {
				if (methodDeclaration.name().equals("setYear")) {
					getSomethingInDateCode.add(new DateClass.RTSetYearCode(methodDeclaration.enclosedScope()));
					retvalType = RetvalTypes.none;
				} else if (methodDeclaration.name().equals("setMonth")) {
					getSomethingInDateCode.add(new DateClass.RTSetMonthCode(methodDeclaration.enclosedScope()));
					retvalType = RetvalTypes.none;
				} else if (methodDeclaration.name().equals("setDay")) {
					getSomethingInDateCode.add(new DateClass.RTSetDayCode(methodDeclaration.enclosedScope()));
					retvalType = RetvalTypes.none;
				} else if (methodDeclaration.name().equals("setDate")) {
					getSomethingInDateCode.add(new DateClass.RTSetDateCode(methodDeclaration.enclosedScope()));
					retvalType = RetvalTypes.none;
				} else {
					retvalType = RetvalTypes.undefined;
					assert false;
				}
			} else if (argumentType.name().equals("Date")) {
				if (methodDeclaration.name().equals("compareTo")) {
					getSomethingInDateCode.add(new DateClass.RTCompareToCode(methodDeclaration.enclosedScope()));
					retvalType = RetvalTypes.usingInt;
				} else {
					retvalType = RetvalTypes.undefined;
					assert false;
				}
			} else {
				retvalType = RetvalTypes.undefined;
				assert false;
			}
		} else if (formalParameterList.count() == 1) {
			if (methodDeclaration.name().equals("getYear")) {
				getSomethingInDateCode.add(new DateClass.RTGetYearCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingInt;
			} else if (methodDeclaration.name().equals("getMonth")) {
				getSomethingInDateCode.add(new DateClass.RTGetMonthCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingInt;
			} else if (methodDeclaration.name().equals("getDay")) {
				getSomethingInDateCode.add(new DateClass.RTGetDayCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingInt;
			} else if (methodDeclaration.name().equals("getDate")) {
				getSomethingInDateCode.add(new DateClass.RTGetDateCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingInt;
			} else if (methodDeclaration.name().equals("toString")) {
				getSomethingInDateCode.add(new DateClass.RTToStringCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingString;
			} else if (methodDeclaration.name().equals("Date")) {
				// Simple constructor
				getSomethingInDateCode.add(new DateClass.RTDateSimpleCtorCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.none;
			} else {
				retvalType = RetvalTypes.undefined;
				assert false;
			}
		} else {
			retvalType = RetvalTypes.undefined;
			assert false;
		}
		
		assert getSomethingInDateCode.size() > 0;
		
		addReturn(methodDeclaration, retvalType, getSomethingInDateCode);
		
		rtMethod.addCode(getSomethingInDateCode);
	}
	private void processScannerMethodDefinition(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		final FormalParameterList formalParameterList = methodDeclaration.formalParameterList();
		final List<RTCode> code = new ArrayList<RTCode>();
		RetvalTypes retvalType = RetvalTypes.undefined;
		
		final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
		assert null != rtTypeDeclaration;
		final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
		rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
		
		if (formalParameterList.count() == 1) {
			if (methodDeclaration.name().equals("nextLine")) {
				code.add(new ScannerClass.RTNextLineCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingString;
			} else {
				retvalType = RetvalTypes.undefined;
				assert false;
			}
		} else if (formalParameterList.count() == 2) {
			if (methodDeclaration.name().equals("Scanner")) {
				final Type elementsParamType = formalParameterList.typeOfParameterAtPosition(1);
				if (elementsParamType.name().equals("InputStream")) {
					code.add(new ScannerClass.RTScannerCtor1Code(methodDeclaration.enclosedScope()));
				} else {
					assert false;
				}
				retvalType = RetvalTypes.none;
			} else {
				retvalType = RetvalTypes.undefined;
				assert false;
			}
		} else {
			retvalType = RetvalTypes.undefined;
			assert false;
		}
		
		addReturn(methodDeclaration, retvalType, code);
		
		assert code.size() > 0;
		
		rtMethod.addCode(code);
	}
	private void processStringMethodDefinition(final MethodDeclaration methodDeclaration,
			final TypeDeclaration typeDeclaration) {
		final FormalParameterList formalParameterList = methodDeclaration.formalParameterList();
		final List<RTCode> code = new ArrayList<RTCode>();
		RetvalTypes retvalType = RetvalTypes.undefined;
		
		final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
		assert null != rtTypeDeclaration;
		final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
		rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
		
		if (formalParameterList.count() == 1) {
			if (methodDeclaration.name().equals("length")) {
				code.add(new RTStringClass.RTLengthCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingInt;
			} else if (methodDeclaration.name().equals("toString")) {
				retvalType = RetvalTypes.usingString;
				code.add(new RTStringClass.RTToStringCode(methodDeclaration.enclosedScope()));
			} else {
				retvalType = RetvalTypes.undefined;
				assert false;
			}
		} else if (2 == formalParameterList.count()) {
			if (methodDeclaration.name().equals("+")) {
				code.add(new RTStringClass.RTPlusCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingString;
			} else if (methodDeclaration.name().equals("indexOf")) {
				code.add(new RTStringClass.RTIndexOfCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingInt;
			} else if (methodDeclaration.name().equals("contains")) {
				code.add(new RTStringClass.RTContainsCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingBool;
			} else if (methodDeclaration.name().equals("compareTo")) {
				code.add(new RTStringClass.RTCompareToCode(methodDeclaration.enclosedScope(), methodDeclaration.name()));
				retvalType = RetvalTypes.usingInt;
			} else if (methodDeclaration.name().equals("join")) {
				final Type elementsParamType = formalParameterList.typeOfParameterAtPosition(1);
				if (elementsParamType.name().equals("List<String>") || elementsParamType.name().equals("List")) {
					code.add(new RTStringClass.RTJoinListCode(methodDeclaration.enclosedScope()));
				} else if (elementsParamType instanceof ArrayType) {
					code.add(new RTStringClass.RTJoinArrayCode(methodDeclaration.enclosedScope()));
				} else {
					ErrorLogger.error(ErrorIncidenceType.Fatal, methodDeclaration.lineNumber(),
							"Invalid parameter type `",
							elementsParamType.name(),
							"' to String.join.", "", "", "");
				}
				retvalType = RetvalTypes.usingString;
			} else if (methodDeclaration.name().equals("split")) {
				code.add(new RTStringClass.RTSplitCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingString;
			} else {
				retvalType = RetvalTypes.undefined;
				assert false;
			}
		} else if (3 == formalParameterList.count()) {
			if (methodDeclaration.name().equals("substring")) {
				code.add(new RTStringClass.RTSubstringCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingString;
			} else if (methodDeclaration.name().equals("replaceFirst")) {
				code.add(new RTStringClass.RTReplaceFirstCode(methodDeclaration.enclosedScope()));
				retvalType = RetvalTypes.usingString;
			} else {
				retvalType = RetvalTypes.undefined;
				assert false;
			}
		} else {
			retvalType = RetvalTypes.undefined;
			assert false;
		}
		
		addReturn(methodDeclaration, retvalType, code);
		
		assert code.size() > 0;
		
		rtMethod.addCode(code);
	}
	private void processDoubleMethodDefinition(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		final FormalParameterList formalParameterList = methodDeclaration.formalParameterList();
		if (formalParameterList.count() == 1) {
			final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
			assert null != rtTypeDeclaration;
			final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
			final List<RTCode> code = new ArrayList<RTCode>();
			if (methodDeclaration.name().equals("toString")) {
				code.add(new RTDoubleClass.RTToStringCode(methodDeclaration.enclosedScope()));
			} else {
				assert false;
			}
			
			addReturn(methodDeclaration, RetvalTypes.usingString, code);
			
			assert code.size() > 0;
			
			rtMethod.addCode(code);
			
		} else if (formalParameterList.count() == 2) {
			final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
			assert null != rtTypeDeclaration;
			final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
			final List<RTCode> code = new ArrayList<RTCode>();
			final String methodName = methodDeclaration.name();
			if (methodName.equals("*") || methodName.equals("/") || methodName.equals("+") ||
					 methodName.equals("-")) {
				code.add(new RTDoubleClass.RTBinaryOpCode(methodDeclaration.enclosedScope(), methodName));
				assert code.size() > 0;
				addReturn(methodDeclaration, RetvalTypes.usingDouble, code);
				rtMethod.addCode(code);
			} else if (methodName.equals("compareTo")) {
				code.add(new RTDoubleClass.RTCompareToCode(methodDeclaration.enclosedScope(), methodName));
				assert code.size() > 0;
				addReturn(methodDeclaration, RetvalTypes.usingInt, code);
				rtMethod.addCode(code);
			} else {
				// assert false;
			}
		} else {
			assert false;
		}
	}
	
	private void processIntegerMethodDefinition(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		final FormalParameterList formalParameterList = methodDeclaration.formalParameterList();
		if (formalParameterList.count() == 1) {
			final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
			assert null != rtTypeDeclaration;
			RetvalTypes retvalType;
			final Type intType = StaticScope.globalScope().lookupTypeDeclaration("int");
			
			// Odd that these built-ins have survived this long without designating a
			// return expression... The primitives just put it on top the stack.
			// Just recognize that they do that
			Expression returnExpr = null;
			if (methodDeclaration.name().equals("toString")) {
				final Expression expressionToReturn = new TopOfStackExpression();
				returnExpr = new ReturnExpression(
						methodDeclaration.name(),
						expressionToReturn, 0,
						StaticScope.globalScope().lookupTypeDeclaration("int"),
						methodDeclaration.enclosedScope());
				retvalType = RetvalTypes.usingString;
			} else if (methodDeclaration.name().equals("toInteger")) {
				final Expression expressionToReturn = new TopOfStackExpression();
				returnExpr = new ReturnExpression(
						methodDeclaration.name(),
						expressionToReturn, 0,
						intType,
						methodDeclaration.enclosedScope());
				retvalType = RetvalTypes.usingInt;
			} else if (methodDeclaration.name().equals("to1CharString")) {
				final Expression expressionToReturn = new IdentifierExpression("ret$val", intType, methodDeclaration.enclosedScope(), 0);
				returnExpr = new ReturnExpression(
						methodDeclaration.name(),
						expressionToReturn, 0,
						StaticScope.globalScope().lookupTypeDeclaration("String"),
						methodDeclaration.enclosedScope());
				retvalType = RetvalTypes.usingString;
			} else {
				retvalType = RetvalTypes.none;
				assert false;
			}
			final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration, returnExpr);
			
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
			final List<RTCode> code = new ArrayList<RTCode>();
			if (methodDeclaration.name().equals("toString")) {
				code.add(new RTIntegerClass.RTToStringCode(methodDeclaration.enclosedScope()));
			} else if (methodDeclaration.name().equals("toInteger")) {
				code.add(new RTIntegerClass.RTToIntegerCode(methodDeclaration.enclosedScope()));
			} else if (methodDeclaration.name().equals("to1CharString")) {
				code.add(new RTIntegerClass.RTToChar1StringCode(methodDeclaration.enclosedScope()));
			} else {
				assert false;
			}
			
			addReturn(methodDeclaration, retvalType, code);
			
			assert code.size() > 0;
			
			rtMethod.addCode(code);
		} else if (formalParameterList.count() == 2) {
			final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
			assert null != rtTypeDeclaration;
			final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
			final List<RTCode> code = new ArrayList<RTCode>();
			final String methodName = methodDeclaration.name();
			if (methodName.equals("*") || methodName.equals("/") || methodName.equals("+") ||
					 methodName.equals("-")) {
				code.add(new RTIntegerClass.RTBinaryOpCode(methodDeclaration.enclosedScope(), methodName));
				assert code.size() > 0;
				addReturn(methodDeclaration, RetvalTypes.usingDouble, code);
				rtMethod.addCode(code);
			} else if (methodName.equals("compareTo")) {
				code.add(new RTIntegerClass.RTCompareToCode(methodDeclaration.enclosedScope(), methodName));
				assert code.size() > 0;
				addReturn(methodDeclaration, RetvalTypes.usingInt, code);
				rtMethod.addCode(code);
			} else {
				// assert false;
			}
		} else {
			assert false;
		}
	}
	
	private void processBooleanMethodDefinition(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		final FormalParameterList formalParameterList = methodDeclaration.formalParameterList();
		if (formalParameterList.count() == 1) {
			final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
			assert null != rtTypeDeclaration;
			final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
			final List<RTCode> code = new ArrayList<RTCode>();
			if (methodDeclaration.name().equals("toString")) {
				code.add(new RTBooleanClass.RTToStringCode(methodDeclaration.enclosedScope()));
				assert code.size() > 0;
				addReturn(methodDeclaration, RetvalTypes.usingBool, code);
				rtMethod.addCode(code);
			} else {
				assert false;
			}
			/*
			 * } else if (formalParameterList.count() == 2) {
			final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
			assert null != rtTypeDeclaration;
			final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
			final List<RTCode> code = new ArrayList<RTCode>();
			final String methodName = methodDeclaration.name();
			if (methodName.equals("*") || methodName.equals("/") || methodName.equals("+") ||
					 methodName.equals("-")) {
				code.add(new RTDoubleClass.RTToStringCode(methodDeclaration.enclosedScope()));
				assert code.size() > 0;
				addReturn(methodDeclaration, RetvalTypes.usingDouble, code);
				rtMethod.addCode(code);
			} else {
				// assert false;
			}
			 */
		} else if (formalParameterList.count() == 2) {
			final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
			assert null != rtTypeDeclaration;
			final String methodName = methodDeclaration.name();
			final RTMethod rtMethod = new RTMethod(methodName, methodDeclaration);
			final List<RTCode> code = new ArrayList<RTCode>();
			if (methodName.equals("||") || methodName.equals("&&") ||
					methodName.equals("^") || methodName.equals("==") || methodName.equals("!=")) {
				rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
				code.add(new RTBooleanClass.RTBinaryOpCode(methodDeclaration.enclosedScope(), methodDeclaration.name()));
				assert code.size() > 0;
				addReturn(methodDeclaration, RetvalTypes.usingBool, code);
				rtMethod.addCode(code);
			} else if (methodName.equals("compareTo")) {
				code.add(new RTBooleanClass.RTCompareToCode(methodDeclaration.enclosedScope(), methodName));
				assert code.size() > 0;
				addReturn(methodDeclaration, RetvalTypes.usingInt, code);
				rtMethod.addCode(code);
			} else {
				assert false;
			}
		} else {
			assert false;
		}
	}
	
	private void processObjectMethodDefinition(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration) {
		final FormalParameterList formalParameterList = methodDeclaration.formalParameterList();
		if (formalParameterList.count() == 3) {
			final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
			assert null != rtTypeDeclaration;
			final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
			final List<RTCode> code = new ArrayList<RTCode>();
			if (methodDeclaration.name().equals("assert")) {
				code.add(new RTObjectClass.RTAssertCode(methodDeclaration.enclosedScope()));
				assert code.size() > 0;
				rtMethod.addCode(code);
			} else {
				assert false;
			}
		} else if (formalParameterList.count() == 2) {
			final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
			assert null != rtTypeDeclaration;
			final RTMethod rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
			rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
			final List<RTCode> code = new ArrayList<RTCode>();
			final String methodName = methodDeclaration.name();
			if (methodName.equals("assert")) {
				code.add(new RTObjectClass.RTAssertCodeMinimal(methodDeclaration.enclosedScope()));
				assert code.size() > 0;
				rtMethod.addCode(code);
			} else if (methodName.equals("compareTo$toBoolean")) {
				code.add(new RTObjectClass.RTConvertCompareToToBooleanCode(methodDeclaration.enclosedScope()));
				assert code.size() > 0;
				addReturn(methodDeclaration, RetvalTypes.usingInt, code);
				rtMethod.addCode(code);
			} else {
				assert false;
			}
		} else {
			assert false;
		}
	}
	
	private List<BodyPart> rescopeInitializations(final List<BodyPart> initializations, final Type classType,
			final StaticScope methodScope) {
		final List<BodyPart> retval = new ArrayList<BodyPart>();
		for (final BodyPart initializer : initializations) {
			if (initializer instanceof AssignmentExpression) {
				final AssignmentExpression assignmentExpr = (AssignmentExpression)initializer;
				final Expression lhs = assignmentExpr.lhs();
				Expression rhs = assignmentExpr.rhs();
				Expression newLhs;
				if (lhs instanceof IdentifierExpression) {
					final IdentifierExpression self = new IdentifierExpression("this", classType,
							methodScope, lhs.lineNumber());
					newLhs = new QualifiedIdentifierExpression(self, lhs.name(), lhs.type());
				} else {
					ErrorLogger.error(ErrorIncidenceType.Fatal, lhs.lineNumber(), "Improperly formed initialization of `",
							lhs.name(), "'.", "");
					newLhs = new ErrorExpression(lhs);
				}
				
				boolean isWellFormedInitialization = false;
				if (rhs instanceof Constant) {
					isWellFormedInitialization = true;	// O.K.
				} else if (rhs instanceof UnaryAbelianopExpression) {
					// Negative numbers. Generalize this later to constant expressions?
					final UnaryAbelianopExpression abelianRhs = (UnaryAbelianopExpression)rhs;
					final Expression innerExpr = abelianRhs.rhs();
					isWellFormedInitialization = innerExpr instanceof Constant;
				}
				if (false == isWellFormedInitialization) {
					ErrorLogger.error(ErrorIncidenceType.Fatal, rhs.lineNumber(), "Improperly formed initialization of `",
							lhs.name() + "': non-constant right-hand side `", rhs.getText(), "'.");
					rhs = new ErrorExpression(rhs);
				}
				final AssignmentExpression newAssignmentExpression =
						new InternalAssignmentExpression(newLhs, "=", rhs, lhs.lineNumber(), null);
				retval.add(newAssignmentExpression);
			} else {
				retval.add(initializer);
			}
		}
		return retval;
	}
	
	private void compileMethodInScope(final MethodDeclaration methodDeclaration, final StaticScope scope) {
		// BodyParts start with the prefixBodyParts, which mainly entail
		// the protocol used in constructors to call the base class constructor.
		// After that, constructors also have initializationBodyParts, where
		// we find the code for in-situ initializations of instance objects
		// which were syntactically placed with the object declarations. Last
		// are the regularBodyParts which is just the ordinary method body.
		// They come together in odd orders during parsing; here, during code
		// generation, we just pick all of them off and put them back together.
		final Declaration associatedMegaTypeDeclaration = scope.associatedDeclaration();
		List<BodyPart> initializationBodyParts = null;
		final boolean isCtor = methodDeclaration.name().equals(associatedMegaTypeDeclaration.name());
		if (associatedMegaTypeDeclaration instanceof ObjectSubclassDeclaration) {
			if (isCtor) {
				// Process these initializations only in constructor bodies
				initializationBodyParts = ((ObjectSubclassDeclaration)associatedMegaTypeDeclaration).inSituInitializations();
				initializationBodyParts = rescopeInitializations(initializationBodyParts, 
						associatedMegaTypeDeclaration.type(),
						methodDeclaration.enclosedScope());
			} else {
				initializationBodyParts = new ArrayList<BodyPart>();
			}
		} else {
			initializationBodyParts = new ArrayList<BodyPart>();
		}
		assert null != initializationBodyParts;
		
		final List<BodyPart> prefixBodyParts = methodDeclaration.prefixBodyParts();
		final List<BodyPart> regularBodyParts = methodDeclaration.bodyParts();
		if (prefixBodyParts.isEmpty()) {
			// It could be either because there was no suitable default
			// constructor or because a manual construct canceled out the
			// automatically provided call
			
			final Type megaTypeOfPotentialCtor = Expression.nearestEnclosingMegaTypeOf(methodDeclaration.enclosedScope());
			Declaration baseClassDeclaration = null;
			if (megaTypeOfPotentialCtor.enclosedScope().associatedDeclaration() instanceof ClassDeclaration) {
				final ClassDeclaration classDeclaration = (ClassDeclaration)megaTypeOfPotentialCtor.enclosedScope().associatedDeclaration();
				baseClassDeclaration = classDeclaration.baseClassDeclaration();
			}
			if (null != baseClassDeclaration) {
				if (megaTypeOfPotentialCtor.name().equals(methodDeclaration.name()) &&
						false == (baseClassDeclaration.type().pathName().equals("Object."))) {
					if (methodDeclaration.hasManualBaseClassConstructorInvocations() == false) {
						ErrorLogger.error(ErrorIncidenceType.Fatal, methodDeclaration.lineNumber(),
							"Constructor `", methodDeclaration.name(),
							"' has no valid means to ensure that the base class part of the object is initialized.", "");
					}
				}
			}
		}
		
		final List<BodyPart> bodyParts = prefixBodyParts;
		bodyParts.addAll(initializationBodyParts);
		bodyParts.addAll(regularBodyParts);
		
		// Null body parts here for "add" declaration in simpletemplate.k
		// Called by compileScope for List<int,String> scope, from compileClass,
		// from compileDeclarations, from original compile
		
		TypeDeclaration typeDeclaration = null;
		final StaticScope myScope = scope; // methodDeclaration.enclosingScope();
		final Type nearestEnclosingMegaType = Expression.nearestEnclosingMegaTypeOf(myScope);
		final StaticScope rightEnclosingScope = nearestEnclosingMegaType.enclosedScope();
		final Declaration roleOrContextOrClass = rightEnclosingScope.associatedDeclaration();
		if (roleOrContextOrClass instanceof StagePropDeclaration) {
			// FYI: StagePropDeclaration is a subclass of RoleDeclaration
			typeDeclaration = (StagePropDeclaration)roleOrContextOrClass;
		} else if (roleOrContextOrClass instanceof RoleDeclaration) {
			typeDeclaration = (RoleDeclaration)roleOrContextOrClass;
		} else if (roleOrContextOrClass instanceof ClassDeclaration) {
			typeDeclaration = (ClassDeclaration)roleOrContextOrClass;
			if (typeDeclaration.name().equals("PrintStream")) {
				processPrintStreamMethodDefinition(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().equals("InputStream")) {
				processInputStreamMethodDefinition(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().equals("Color")) {
				processColorMethodDefinition(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().equals("Panel")) {
				processPanelMethodDefinition(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().equals("Frame")) {
				processFrameMethodDefinition(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().equals("Event")) {
				processEventMethodDefinition(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().equals("Scanner")) {
				processScannerMethodDefinition(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().startsWith("List<")) {
				processListMethodDefinition(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().startsWith("Set<")) {
				processSetMethodDefinition(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().startsWith("Map<")) {
				processMapMethodDefinition(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().equals("Math")) {
				processMathMethodDefinition(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().equals("Date")) {
				processDateMethodDefinition(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().equals("String")) {
				processStringMethodDefinition(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().equals("double")) {
				processDoubleMethodDefinition(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().equals("int")) {
				processIntegerMethodDefinition(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().equals("Integer")) {
				processIntegerMethodDefinition(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().equals("boolean")) {
				processBooleanMethodDefinition(methodDeclaration, typeDeclaration);
				return;
			} else if (typeDeclaration.name().equals("Object")) {
				processObjectMethodDefinition(methodDeclaration, typeDeclaration);
				return;
			}
		} else if (roleOrContextOrClass instanceof ContextDeclaration) {
			typeDeclaration = (ContextDeclaration)roleOrContextOrClass;
		} else {
			assert false;	// unanticipated...
		}
		
		// As a side-effect, convertTypeDeclarationToRTTypeDeclaration will add the
		// necessary types to the run-time environment
		final RTType rtTypeDeclaration = convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
		
		// Give them all a return statement. This is never executed but
		// is only consulted at run time to see if the method returns
		// anything — hence the instantiation of a dummy.
		//
		// Wait a minute... some of these go live...
		RTMethod rtMethod = null;
		final Type returnType = methodDeclaration.returnType();
		if (null != returnType && false == returnType.name().equals("void")) {
			final Expression expressionToReturn = new TopOfStackExpression();
			final Expression returnExpression = new ReturnExpression(
					methodDeclaration.name(),
					expressionToReturn,	/* Dummy? */
					methodDeclaration.lineNumber(),
					nearestEnclosingMegaType, methodDeclaration.enclosedScope());
			rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration, returnExpression);
		} else {
			rtMethod = new RTMethod(methodDeclaration.name(), methodDeclaration);
		}
		
		rtTypeDeclaration.addMethod(methodDeclaration.name(), rtMethod);
		this.compileBodyPartsForMethodOfTypeInScope(bodyParts, rtMethod, rtTypeDeclaration, scope);
	}
	
	private List<RTCode> compileDeclarationForMethodOfTypeInScope(final Declaration declaration, final MethodDeclaration methodDeclaration,
			final RTType runtimeType, final StaticScope scope) {
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
	private List<RTCode> compileDeclarationForMethodOfTypeHelper(final Declaration declaration, final MethodDeclaration methodDeclaration,
			final RTType runtimeType, final StaticScope scope) {
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
	private void compileExprAndDeclListForMethodOfTypeInScope(final ExprAndDeclList declaration, final RTMethod runTimeMethodDecl,
			final RTType declarationType, final StaticScope scope) {
		final List<BodyPart> bodyParts = declaration.bodyParts();
		this.compileBodyPartsForMethodOfTypeInScope(bodyParts, runTimeMethodDecl, declarationType, scope);
	}
	private void compileBodyPartsForMethodOfTypeInScope(final List<BodyPart> bodyParts, final RTMethod rtMethod, final RTType rtTypeDeclaration, final StaticScope scope) {
		for (final BodyPart bodyPart : bodyParts) {
			final List<RTCode> code = this.compileBodyPartForMethodOfTypeInScope(bodyPart, rtMethod, rtTypeDeclaration, scope);
			rtMethod.addCode(code);
		}
	}
	public List<RTCode> compileExpressionForMethodOfTypeInScope(final Expression expression, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		// This boomerangs and comes back to the methods defined below
		// We use Expression as the dispatcher
		return expression.compileCodeForInScope(this, methodDeclaration, rtTypeDeclaration, scope);
	}
	public List<RTCode> compileQualifiedIdentifierExpression(final QualifiedIdentifierExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTQualifiedIdentifier(expr.name(), expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileQualifiedIdentifierExpressionUnaryOp(final QualifiedIdentifierExpressionUnaryOp expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTQualifiedIdentifierUnaryOp(expr.name(), expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileQualifiedClassMemberExpression(final QualifiedClassMemberExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		assert false; 	// unreachable?
		return null;
	}
	public List<RTCode> compileQualifiedClassMemberExpressionUnaryOp(final QualifiedClassMemberExpressionUnaryOp expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		assert false; 	// never executed?
		return null;
	}
	public List<RTCode> compileMessageExpression(final MessageExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(RTMessage.makeRTMessage(expr.name(), expr, rtTypeDeclaration, scope, expr.isStatic()));
		return retval;
	}
	public List<RTCode> compileDupMessageExpression(final DupMessageExpression expr, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTDupMessage(expr.name(), expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileIdentifierExpression(final IdentifierExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		assert true;	// are these ever called? yup.
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTIdentifier(expr.name(), expr));
		return retval;
	}
	public List<RTCode> compileRelopExpression(final RelopExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTRelop(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileIdentityBooleanExpression(final IdentityBooleanExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTIdentityBooleanExpression(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileBooleanExpression(final BooleanExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTBoolean(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileBinopExpression(final BinopExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTBinop(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileUnaryopExpressionWithSideEffect(final UnaryopExpressionWithSideEffect expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTUnaryopWithSideEffect(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileUnaryAbelianopExpression(final UnaryAbelianopExpression expr, final String operation, final StaticScope scope, final RTType rtTypeDeclaration) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTUnaryAbelianop(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileAssignmentExpression(final AssignmentExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTAssignment(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileInternalAssignmentExpression(final InternalAssignmentExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTInternalAssignment(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileDoubleCasterExpression(final DoubleCasterExpression expr, final RTType rtTypeDeclaration) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTDoubleCaster(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileNewExpression(final NewExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		assert true;
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTNew(expr,rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileNewArrayExpression(final NewArrayExpression expr, final MethodDeclaration methodDeclaration,
			final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTNewArray(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileArrayExpression(final ArrayExpression expr, final StaticScope scope) {
		assert false;
		return null;
	}
	public List<RTCode> compileArrayIndexExpression(final ArrayIndexExpression expr, final StaticScope scope, final RTType rtTypeDeclaration) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTArrayIndexExpression(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileArrayIndexExpressionUnaryOp(final ArrayIndexExpressionUnaryOp expr, final StaticScope scope, final RTType rtTypeDeclaration)
	{
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTArrayIndexExpressionUnaryOp(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileRoleArrayIndexExpression(final RoleArrayIndexExpression expr, final RTType nearestEnclosingType, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTRoleArrayIndexExpression(expr, nearestEnclosingType));
		return retval;
	}
	public List<RTCode> compileIfExpression(final IfExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTIf(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileForExpression(final ForExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		final RTFor newFor = new RTFor(expr, rtTypeDeclaration);
		retval.add(newFor);
		assert null == expr.thingToIterateOver();
		return retval;
	}
	public List<RTCode> compileForIterationExpression(final ForExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		final RTForIteration newFor = new RTForIteration(expr, rtTypeDeclaration);
		retval.add(newFor);
		assert null != expr.thingToIterateOver();
		return retval;
	}
	public List<RTCode> compileWhileExpression(final WhileExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		final RTWhile newWhile = new RTWhile(expr, rtTypeDeclaration);
		retval.add(newWhile);
		return retval;
	}
	public List<RTCode> compileDoWhileExpression(final DoWhileExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		final RTDoWhile newDoWhile = new RTDoWhile(expr, rtTypeDeclaration);
		retval.add(newDoWhile);
		return retval;
	}
	public List<RTCode> compileSwitchExpression(final SwitchExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		final RTSwitch newSwitch = new RTSwitch(expr, rtTypeDeclaration);
		retval.add(newSwitch);
		return retval;
	}
	public List<RTCode> compileBreakExpression(final BreakExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		assert false;	// ever reached? just curious
		return null;
	}
	public List<RTCode> compileContinueExpression(final ContinueExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		assert false;	// ever reached? just curious
		return null;
	}
	public List<RTCode> compileExpressionList(final ExpressionList expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTExpressionList(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileSumExpression(final SumExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTSum(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileProductExpression(final ProductExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTProduct(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compilePowerExpression(final PowerExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTPower(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileReturnExpression(final ReturnExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		List<RTCode> rTExpr = null;
		if (null != expr) {
			final Expression returnExpression = expr.returnExpression();
			if (null != returnExpression && returnExpression instanceof NullExpression == false) {
				rTExpr = this.compileExpressionForMethodOfTypeInScope(returnExpression, methodDeclaration, rtTypeDeclaration, scope);
				if (null == rTExpr) {
					assert null != rTExpr;
				}
			}
		}
		retval.add(new RTReturn(methodDeclaration.name(), rTExpr, rtTypeDeclaration,
				null == expr? 0: expr.nestingLevelInsideMethod()));
		return retval;
	}
	public List<RTCode> compileBlockExpression(final BlockExpression expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTBlock(expr, rtTypeDeclaration));
		return retval;
	}
	public List<RTCode> compileConstant(final Constant expr, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTConstant(expr));
		return retval;
	}
	public List<RTCode> compilePromoteToDoubleExpression(final PromoteToDoubleExpr expr, final StaticScope scope, final RTType t) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTPromoteToDoubleExpr(expr, t));
		return retval;
	}
	public List<RTCode> compileIndexExpression(final IndexExpression indexExpression) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTIndexExpression(indexExpression));
		return retval;
	}
	public List<RTCode> compileLastIndexExpression(final LastIndexExpression lastIndexExpression) {
		final List<RTCode> retval = new ArrayList<RTCode>();
		retval.add(new RTLastIndexExpression(lastIndexExpression));
		return retval;
	}
	public List<RTCode> compileBodyPartForMethodOfTypeInScope(final BodyPart bodyPart, final RTMethod rtMethod, final RTType rtTypeDeclaration, final StaticScope scope) {
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
	private List<RTCode> compileObjectDecl(final ObjectDeclaration objectDeclaration) {
		// Declarations are a side effect on setting up ...
		return new ArrayList<RTCode>();
	}
	private void compileScope(final StaticScope scope) {
		for (final ClassDeclaration cd : scope.classDeclarations()) {
			this.compileClass(cd);
		}
		for (final MethodDeclaration md : scope.methodDeclarations()) {
			this.compileMethodInScope(md, scope);
		}
		for (final ObjectDeclaration od : scope.objectDeclarations()) {
			final List<RTCode> ignored = this.compileObjectDecl(od);
			assert ignored.size() == 0;
		}
		
		// There's some kind of problem that we need to do this.
		// FIXME.
		for (final RoleDeclaration rd : scope.roleDeclarations()) {
			if (rd instanceof StagePropDeclaration == false) {
				this.compileRole(rd);
			}
		}
		for (final StagePropDeclaration spd : scope.stagePropDeclarations()) {
			if (spd instanceof StagePropDeclaration == true) {
				this.compileStageProp(spd);
			}
		}
		for (final ContextDeclaration cd : scope.contextDeclarations()) {
			this.compileContext(cd);
		}
	}
	
	public static RTType convertTypeDeclarationToRTTypeDeclaration(final TypeDeclaration typeDeclaration) {
		final StaticScope enclosedScope = typeDeclaration.enclosedScope();
		return InterpretiveCodeGenerator.scopeToRTTypeDeclaration(enclosedScope);
	}
	private static RTType lookInGlobalScopeForRTTypeDeclaration(final StaticScope enclosedScope) {
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
				} else if (associatedDeclaration instanceof MethodDeclaration) {
					// It's in the scope hierarchy but not the type hierarchy.  TRIAL -- seems to work
					tempRetval = retval;
					;
				} else {
					assert false;
				}
				assert null != tempRetval;
				retval = tempRetval;
			} else {
				retval = tempRetval;
			}
			
			assert null != retval;
		}
		
		return retval;
	}
	
	private static RTType lookInTopLevelTypeForRTTypeDeclaration(final StaticScope enclosedScope) {
		RTType retval = null;

		assert enclosedScope.associatedDeclaration() instanceof TypeDeclaration;
		final TypeDeclaration typeDeclaration = (TypeDeclaration)enclosedScope.associatedDeclaration();
		
		retval = RunTimeEnvironment.runTimeEnvironment_.topLevelTypeNamed(typeDeclaration.name());
		if (null == retval) {
			if (typeDeclaration instanceof ClassDeclaration) {
				final RTClass classDeclaration = new RTClass(typeDeclaration);
				RunTimeEnvironment.runTimeEnvironment_.addTopLevelClass(typeDeclaration.name(), classDeclaration);
			} else if (typeDeclaration instanceof ContextDeclaration) {
				final RTContext contextDeclaration = new RTContext(typeDeclaration);
				RunTimeEnvironment.runTimeEnvironment_.addTopLevelContext(typeDeclaration.name(), contextDeclaration);
			} else if (typeDeclaration instanceof InterfaceDeclaration) {
				final RTInterface interfaceDeclaration = new RTInterface(typeDeclaration);
				RunTimeEnvironment.runTimeEnvironment_.addTopLevelInterface(typeDeclaration.name(), interfaceDeclaration);
			} else {
				assert false;
			}
			
			retval = InterpretiveCodeGenerator.convertTypeDeclarationToRTTypeDeclaration(typeDeclaration);
			
			assert null != retval;
		}
		return retval;
	}
	public static RTType scopeToRTTypeDeclaration(final StaticScope enclosedScope) {
		RTType retval = null;
		if (null == enclosedScope) {
			assert null != enclosedScope;
		}
		final StaticScope enclosingScope = enclosedScope.parentScope();
		
		final String scopePathName = enclosedScope.pathName();
		
		if (null == retval) {
			final RTType aType = RunTimeEnvironment.runTimeEnvironment_.typeFromPath(scopePathName);
			if (null != aType) {
				retval = aType;
			} else if (enclosingScope != StaticScope.globalScope()) {
				// retval = InterpretiveCodeGenerator.lookInGlobalScopeForRTTypeDeclaration(enclosedScope);
				retval = InterpretiveCodeGenerator.lookInGlobalScopeForRTTypeDeclaration(enclosedScope);
				RunTimeEnvironment.runTimeEnvironment_.registerTypeByPath(scopePathName, retval);
				assert null != retval;
			} else {
				// Top-level
				assert enclosingScope == StaticScope.globalScope();
				retval = InterpretiveCodeGenerator.lookInTopLevelTypeForRTTypeDeclaration(enclosedScope);
				assert null != retval;
			}
		}
		
		assert null != retval;
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
