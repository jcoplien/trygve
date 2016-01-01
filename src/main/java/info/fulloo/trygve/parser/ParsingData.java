package info.fulloo.trygve.parser;

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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

import info.fulloo.trygve.declarations.ActualArgumentList;
import info.fulloo.trygve.declarations.FormalParameterList;
import info.fulloo.trygve.declarations.Message;
import info.fulloo.trygve.declarations.Declaration.ClassDeclaration;
import info.fulloo.trygve.declarations.Declaration.DeclarationList;
import info.fulloo.trygve.declarations.Declaration.ExprAndDeclList;
import info.fulloo.trygve.declarations.Declaration.MethodSignature;
import info.fulloo.trygve.declarations.Declaration.TemplateDeclaration;
import info.fulloo.trygve.declarations.Declaration.TypeDeclarationList;
import info.fulloo.trygve.expressions.BreakableExpression;
import info.fulloo.trygve.expressions.Expression;
import info.fulloo.trygve.expressions.ExpressionStackAPI;
import info.fulloo.trygve.expressions.Constant.IntegerConstant;
import info.fulloo.trygve.expressions.Constant.StringConstant;
import info.fulloo.trygve.expressions.Expression.BlockExpression;
import info.fulloo.trygve.expressions.Expression.DoWhileExpression;
import info.fulloo.trygve.expressions.Expression.ForExpression;
import info.fulloo.trygve.expressions.Expression.IdentifierExpression;
import info.fulloo.trygve.expressions.Expression.NewExpression;
import info.fulloo.trygve.expressions.Expression.SwitchExpression;
import info.fulloo.trygve.expressions.Expression.WhileExpression;
import info.fulloo.trygve.run_time.RTBreakableExpression;
import info.fulloo.trygve.semantic_analysis.StaticScope;

public class ParsingData {
	public ParsingData()
	{
		StaticScope.resetGlobalScope();
		globalScope_ = StaticScope.globalScope();
		
		messages_ = new Stack<Message>();
		expressions_ = new Stack<ExpressionStackAPI>();
		argumentLists_ = new Stack<ActualArgumentList>();
		declarationList_ = new Stack<DeclarationList>();
		typeNameListStack_ = new Stack<ArrayList<String>>();
		classDeclarations_ = new Stack<ClassDeclaration>();
		forExpressionStack_ = new Stack<ForExpression>();
		loopExpressionStack_ = new Stack<Expression>();
		templateDeclarations_ = new Stack<TemplateDeclaration>();
		breakableExpressions_ = new LinkedHashMap<String, BreakableExpression>();
		whileExpressionStack_ = new Stack<WhileExpression>();
		methodSignatureStack_ = new Stack<MethodSignature>();
		exprAndDeclListStack_ = new Stack<ExprAndDeclList>();
		blockExpressionStack_ = new Stack<BlockExpression>();
		formalParameterLists_ = new Stack<FormalParameterList>();
		switchExpressionStack_ = new Stack<SwitchExpression>();
		breakableRTExpressions_ = new LinkedHashMap<String, RTBreakableExpression>();
		doWhileExpressionStack_ = new Stack<DoWhileExpression>();
		typeDeclarationListStack_ = new Stack<TypeDeclarationList>();
		templateInstantationList_ = new TypeDeclarationList(0);	// argument is lineNumber
		
		variableGeneratorCounter_ = 101;
	}
	
	public void addBreakableExpression(final String uniqueName, final BreakableExpression breakableExpression) {
		breakableExpressions_.put(uniqueName, breakableExpression);
	}
	public Map<String, BreakableExpression> breakableExpressions() {
		return breakableExpressions_;
	}
	private Map<String, BreakableExpression> breakableExpressions_;
	
	public void addBreakableRTExpression(final String uniqueName, final RTBreakableExpression breakableExpression) {
		breakableRTExpressions_.put(uniqueName, breakableExpression);
	}
	public Map<String, RTBreakableExpression> breakableRTExpressions() {
		return breakableRTExpressions_;
	}
	private Map<String, RTBreakableExpression> breakableRTExpressions_;
	
	
	private StaticScope globalScope_;
	public StaticScope globalScope() { return globalScope_; }
	

	public ActualArgumentList    currentArgumentList() { return argumentLists_.peek(); }
	public void                     pushArgumentList(ActualArgumentList al) { argumentLists_.push(al); }
	public ActualArgumentList        popArgumentList() { assert argumentLists_.size() > 0; return argumentLists_.pop(); }
	public void                     pushDeclarationList(final DeclarationList l) { declarationList_.push(l); }
	public DeclarationList           popDeclarationList() { return declarationList_.pop(); }
	public FormalParameterList       popFormalParameterList() { assert formalParameterLists_.size() > 0; return formalParameterLists_.pop(); }
	public FormalParameterList   currentFormalParameterList() { return formalParameterLists_.peek(); }
	public void                     pushFormalParameterList(final FormalParameterList pl) { formalParameterLists_.push(pl); }
	public Message                   popMessage() { if (messages_.size() > 0) return messages_.pop(); else return null; }
	public void                     pushMessage(final Message m) { messages_.push(m); }

	
	private Stack<ActualArgumentList>   argumentLists_;
	private Stack<ArrayList<String>>    typeNameListStack_;
	private Stack<BlockExpression>      blockExpressionStack_;
	private Stack<ClassDeclaration>     classDeclarations_;
	private Stack<DeclarationList>      declarationList_;
	private Stack<DoWhileExpression>    doWhileExpressionStack_;
	private Stack<ExprAndDeclList>      exprAndDeclListStack_;
	private Stack<ExpressionStackAPI>   expressions_;
	private Stack<ForExpression>        forExpressionStack_;
	private Stack<FormalParameterList>  formalParameterLists_;
	private Stack<Expression>           loopExpressionStack_;
	private Stack<Message>              messages_;
	private Stack<MethodSignature>      methodSignatureStack_;
	private Stack<SwitchExpression>     switchExpressionStack_;
	private Stack<TemplateDeclaration>  templateDeclarations_;
	private TypeDeclarationList         templateInstantationList_;
	private Stack<TypeDeclarationList>  typeDeclarationListStack_;
	private Stack<WhileExpression>      whileExpressionStack_;

	public BlockExpression  		 popBlockExpression() { return blockExpressionStack_.pop(); }
	public void 					pushBlockExpression(BlockExpression expr) { blockExpressionStack_.push(expr); }
	public BlockExpression 		 currentBlockExpression() { return blockExpressionStack_.peek(); }
	private Expression               popBreakableExpression() { return loopExpressionStack_.pop(); }
	private void                    pushBreakableExpression(final Expression e) { loopExpressionStack_.push(e); }
	public Expression            currentBreakableExpression() { return loopExpressionStack_.peek(); }
	public ClassDeclaration 	     popClassDeclaration() { return classDeclarations_.pop(); }
	public ClassDeclaration 	 currentClassDeclaration() { return classDeclarations_.peek(); }
	public void 					pushClassDeclaration(ClassDeclaration cd) { classDeclarations_.push(cd); };
	public DoWhileExpression  	     popDoWhileExpression() { popBreakableExpression(); return doWhileExpressionStack_.pop(); }
	public void 				    pushDoWhileExpression(final DoWhileExpression expr) { doWhileExpressionStack_.push(expr); pushBreakableExpression(expr); }
	public DoWhileExpression 	 currentDoWhileExpression() { return doWhileExpressionStack_.peek(); }
	public ExprAndDeclList			 popExprAndDecl() { return exprAndDeclListStack_.pop(); }
	public void						pushExprAndDecl(final ExprAndDeclList e) { exprAndDeclListStack_.push(e); }
	public ExprAndDeclList	     currentExprAndDecl() { return exprAndDeclListStack_.peek(); }
	public boolean               currentExprAndDeclExists() { return exprAndDeclListStack_.size() > 0; }
	public void 					pushExpression(final ExpressionStackAPI expression) { assert null != expression; expressions_.push(expression); }
	public Expression               peekExpression() { return (Expression)expressions_.peek(); }
	public boolean               currentExpressionExists() { return expressions_.size() > 0; }
	public ExpressionStackAPI     popRawExpression() { assert expressions_.size() > 0; assert expressions_.peek() != null; return expressions_.pop(); }
	public Expression                popExpression() { assert expressions_.size() > 0; assert expressions_.peek() != null; final Expression retval = (Expression)expressions_.pop(); assert retval instanceof Expression; return retval; }
	public ForExpression  			 popForExpression() { popBreakableExpression();  return forExpressionStack_.pop(); }
	public void 					pushForExpression(final ForExpression expr) { forExpressionStack_.push(expr); pushBreakableExpression(expr); }
	public ForExpression 		 currentForExpression() { return forExpressionStack_.peek(); }
	public void 					pushMethodSignature(final MethodSignature m) { methodSignatureStack_.push(m); }
	public MethodSignature 		 currentMethodSignature() { return methodSignatureStack_.peek(); }
	public MethodSignature 			 popMethodSignature() { return methodSignatureStack_.pop(); }
	public void                     pushSwitchExpr(final SwitchExpression sw) { switchExpressionStack_.push(sw); pushBreakableExpression(sw); }
	public SwitchExpression		     popSwitchExpr() { popBreakableExpression(); return switchExpressionStack_.pop(); }
	public SwitchExpression      currentSwitchExpr() { return switchExpressionStack_.peek(); }
	public TemplateDeclaration 	     popTemplateDeclaration() { return templateDeclarations_.pop(); }	
	public TemplateDeclaration 	 currentTemplateDeclaration() { return templateDeclarations_.peek(); }
	public void 					pushTemplateDeclaration(final TemplateDeclaration td) { templateDeclarations_.push(td); };
	public void					    pushTypeDeclarationList(final TypeDeclarationList decl) { typeDeclarationListStack_.push(decl); }
	public TypeDeclarationList		 popTypeDeclarationList() { return typeDeclarationListStack_.pop(); }
	public TypeDeclarationList   currentTypeDeclarationList() { return typeDeclarationListStack_.peek(); }
	public TypeDeclarationList   currentTemplateInstantiationList() { return templateInstantationList_; }
	public void                     pushTypeNameList(final ArrayList<String> los) { typeNameListStack_.push(los); }
    public ArrayList<String>         popTypeNameList() { return typeNameListStack_.pop(); }
    public ArrayList<String>     currentTypeNameList() { return typeNameListStack_.peek(); }
    public boolean               currentWhileExpressionExists() { return whileExpressionStack_.size() > 0; }
    public WhileExpression  		 popWhileExpression() { popBreakableExpression(); return whileExpressionStack_.pop(); }
	public void 					pushWhileExpression(final WhileExpression expr) { whileExpressionStack_.push(expr); pushBreakableExpression(expr); }
	public WhileExpression 		 currentWhileExpression() { return whileExpressionStack_.peek(); }
	public Expression            nearestContinuableLoop() {
		                             Expression retval = null;
		                             final int loopExpressionsStackSize = loopExpressionStack_.size();
		                             for (int i = loopExpressionsStackSize; i >= 0; --i) {
		                            	 final Expression expr = loopExpressionStack_.get(i);
		                            	 if (expr instanceof ForExpression || expr instanceof WhileExpression ||
		                            			 expr instanceof DoWhileExpression) {
		                            		 retval = expr;
		                            		 break;
		                            	 } else {
		                            		 continue;
		                            	 }
		                             }
		                             return retval;
		                         }
	
	public void stackSnapshotDebug() {
		final int stackSize = expressions_.size();
		if (0 == stackSize) {
			System.err.println(">\t***empty***");
		} else {
			System.err.print(">");
			for (int i = stackSize-1; i >= 0 && i > stackSize-6-1; --i) {
				final ExpressionStackAPI expr = expressions_.get(i);
				if (null == expr) {
					System.err.println("\tNULL");
				} else if (expr instanceof NewExpression) {
					System.err.print("\t");
					System.err.print("NewExpression ");
					System.err.println(((NewExpression)expr).getText());
				} else if (expr instanceof IdentifierExpression) {
					System.err.print("\t");
					System.err.print("IdentifierExpression (");
					System.err.print(((IdentifierExpression)expr).getText());
					System.err.println(")");
				} else if (expr instanceof StringConstant) {
					System.err.print("\t");
					System.err.print("StringConstant (\"");
					System.err.print(((StringConstant)expr).value());
					System.err.println("\")");
				} else if (expr instanceof IntegerConstant) {
					System.err.print("\t");
					System.err.print("IntegerConstant (");
					System.err.print(((IntegerConstant)expr).value());
					System.err.println(")");
				} else {
					final String toPrint = expr.getClass().getSimpleName();
					System.err.print("\t");
					System.err.println(toPrint);
				}
			}
		}
	}
	public int variableGeneratorCounter_;
}
