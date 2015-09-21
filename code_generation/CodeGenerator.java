package code_generation;

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
 */

import java.util.List;

import run_time.RTCode;
import run_time.RTExpression;
import run_time.RTType;
import run_time.RunTimeEnvironment;
import semantic_analysis.StaticScope;
import declarations.Declaration.MethodDeclaration;
import expressions.Constant;
import expressions.Expression.*;

public interface CodeGenerator {
	public void compile();
	public List<RTCode> compileQualifiedClassMemberExpression(QualifiedClassMemberExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileQualifiedClassMemberExpressionUnaryOp(QualifiedClassMemberExpressionUnaryOp expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileQualifiedIdentifierExpression(QualifiedIdentifierExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileQualifiedIdentifierExpressionUnaryOp(QualifiedIdentifierExpressionUnaryOp expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileMessageExpression(MessageExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileDupMessageExpression(DupMessageExpression expr, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileIdentifierExpression(IdentifierExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileRelopExpression(RelopExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileBooleanExpression(BooleanExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileBinopExpression(BinopExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileUnaryopExpressionWithSideEffect(UnaryopExpressionWithSideEffect expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileUnaryAbelianopExpression(UnaryAbelianopExpression expr, String operation, StaticScope scope, RTType rtTypeDeclaration);
	public List<RTCode> compileAssignmentExpression(AssignmentExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileNewExpression(NewExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileNewArrayExpression(NewArrayExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileIfExpression(IfExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileForExpression(ForExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileForIterationExpression(ForExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileWhileExpression(WhileExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileDoWhileExpression(DoWhileExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileSwitchExpression(SwitchExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileBreakExpression(BreakExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileContinueExpression(ContinueExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileExpressionList(ExpressionList expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileSumExpression(SumExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileProductExpression(ProductExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compilePowerExpression(PowerExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileReturnExpression(ReturnExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileBlockExpression(BlockExpression expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileConstant(Constant expr, MethodDeclaration methodDeclaration, RTType rtTypeDeclaration, StaticScope scope);
	public List<RTCode> compileArrayExpression(ArrayExpression expr, StaticScope scope);
	public List<RTCode> compileArrayIndexExpression(ArrayIndexExpression expr, StaticScope scope, RTType rtTypeDeclaration);
	public List<RTCode> compileArrayIndexExpressionUnaryOp(ArrayIndexExpressionUnaryOp expr, StaticScope scope, RTType rtTypeDeclaration);
	public List<RTCode> compilePromoteToDoubleExpression(PromoteToDoubleExpr expr, StaticScope scope, RTType t);
	public RunTimeEnvironment virtualMachine();
	public RTExpression mainExpr();
}
