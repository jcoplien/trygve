// Generated from Kant.g4 by ANTLR 4.4

    package info.fulloo.trygve.parser;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link KantParser}.
 */
public interface KantListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link KantParser#return_type}.
	 * @param ctx the parse tree
	 */
	void enterReturn_type(@NotNull KantParser.Return_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#return_type}.
	 * @param ctx the parse tree
	 */
	void exitReturn_type(@NotNull KantParser.Return_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#type_name}.
	 * @param ctx the parse tree
	 */
	void enterType_name(@NotNull KantParser.Type_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#type_name}.
	 * @param ctx the parse tree
	 */
	void exitType_name(@NotNull KantParser.Type_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(@NotNull KantParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(@NotNull KantParser.ConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#null_expr}.
	 * @param ctx the parse tree
	 */
	void enterNull_expr(@NotNull KantParser.Null_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#null_expr}.
	 * @param ctx the parse tree
	 */
	void exitNull_expr(@NotNull KantParser.Null_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#role_vec_modifier}.
	 * @param ctx the parse tree
	 */
	void enterRole_vec_modifier(@NotNull KantParser.Role_vec_modifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#role_vec_modifier}.
	 * @param ctx the parse tree
	 */
	void exitRole_vec_modifier(@NotNull KantParser.Role_vec_modifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#expr_and_decl_list}.
	 * @param ctx the parse tree
	 */
	void enterExpr_and_decl_list(@NotNull KantParser.Expr_and_decl_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#expr_and_decl_list}.
	 * @param ctx the parse tree
	 */
	void exitExpr_and_decl_list(@NotNull KantParser.Expr_and_decl_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#main}.
	 * @param ctx the parse tree
	 */
	void enterMain(@NotNull KantParser.MainContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#main}.
	 * @param ctx the parse tree
	 */
	void exitMain(@NotNull KantParser.MainContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(@NotNull KantParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(@NotNull KantParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#trivial_object_decl}.
	 * @param ctx the parse tree
	 */
	void enterTrivial_object_decl(@NotNull KantParser.Trivial_object_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#trivial_object_decl}.
	 * @param ctx the parse tree
	 */
	void exitTrivial_object_decl(@NotNull KantParser.Trivial_object_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#method_name}.
	 * @param ctx the parse tree
	 */
	void enterMethod_name(@NotNull KantParser.Method_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#method_name}.
	 * @param ctx the parse tree
	 */
	void exitMethod_name(@NotNull KantParser.Method_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#class_body_element}.
	 * @param ctx the parse tree
	 */
	void enterClass_body_element(@NotNull KantParser.Class_body_elementContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#class_body_element}.
	 * @param ctx the parse tree
	 */
	void exitClass_body_element(@NotNull KantParser.Class_body_elementContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(@NotNull KantParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(@NotNull KantParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#abelian_unary_op}.
	 * @param ctx the parse tree
	 */
	void enterAbelian_unary_op(@NotNull KantParser.Abelian_unary_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#abelian_unary_op}.
	 * @param ctx the parse tree
	 */
	void exitAbelian_unary_op(@NotNull KantParser.Abelian_unary_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#stageprop_body}.
	 * @param ctx the parse tree
	 */
	void enterStageprop_body(@NotNull KantParser.Stageprop_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#stageprop_body}.
	 * @param ctx the parse tree
	 */
	void exitStageprop_body(@NotNull KantParser.Stageprop_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#class_body}.
	 * @param ctx the parse tree
	 */
	void enterClass_body(@NotNull KantParser.Class_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#class_body}.
	 * @param ctx the parse tree
	 */
	void exitClass_body(@NotNull KantParser.Class_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#context_body_element}.
	 * @param ctx the parse tree
	 */
	void enterContext_body_element(@NotNull KantParser.Context_body_elementContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#context_body_element}.
	 * @param ctx the parse tree
	 */
	void exitContext_body_element(@NotNull KantParser.Context_body_elementContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#expr_or_null}.
	 * @param ctx the parse tree
	 */
	void enterExpr_or_null(@NotNull KantParser.Expr_or_nullContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#expr_or_null}.
	 * @param ctx the parse tree
	 */
	void exitExpr_or_null(@NotNull KantParser.Expr_or_nullContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#identifier_list}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier_list(@NotNull KantParser.Identifier_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#identifier_list}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier_list(@NotNull KantParser.Identifier_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#stageprop_decl}.
	 * @param ctx the parse tree
	 */
	void enterStageprop_decl(@NotNull KantParser.Stageprop_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#stageprop_decl}.
	 * @param ctx the parse tree
	 */
	void exitStageprop_decl(@NotNull KantParser.Stageprop_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#abelian_product}.
	 * @param ctx the parse tree
	 */
	void enterAbelian_product(@NotNull KantParser.Abelian_productContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#abelian_product}.
	 * @param ctx the parse tree
	 */
	void exitAbelian_product(@NotNull KantParser.Abelian_productContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#abelian_expr}.
	 * @param ctx the parse tree
	 */
	void enterAbelian_expr(@NotNull KantParser.Abelian_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#abelian_expr}.
	 * @param ctx the parse tree
	 */
	void exitAbelian_expr(@NotNull KantParser.Abelian_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#switch_body}.
	 * @param ctx the parse tree
	 */
	void enterSwitch_body(@NotNull KantParser.Switch_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#switch_body}.
	 * @param ctx the parse tree
	 */
	void exitSwitch_body(@NotNull KantParser.Switch_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#if_expr}.
	 * @param ctx the parse tree
	 */
	void enterIf_expr(@NotNull KantParser.If_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#if_expr}.
	 * @param ctx the parse tree
	 */
	void exitIf_expr(@NotNull KantParser.If_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#do_while_expr}.
	 * @param ctx the parse tree
	 */
	void enterDo_while_expr(@NotNull KantParser.Do_while_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#do_while_expr}.
	 * @param ctx the parse tree
	 */
	void exitDo_while_expr(@NotNull KantParser.Do_while_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#role_body}.
	 * @param ctx the parse tree
	 */
	void enterRole_body(@NotNull KantParser.Role_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#role_body}.
	 * @param ctx the parse tree
	 */
	void exitRole_body(@NotNull KantParser.Role_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#context_body}.
	 * @param ctx the parse tree
	 */
	void enterContext_body(@NotNull KantParser.Context_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#context_body}.
	 * @param ctx the parse tree
	 */
	void exitContext_body(@NotNull KantParser.Context_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#boolean_expr}.
	 * @param ctx the parse tree
	 */
	void enterBoolean_expr(@NotNull KantParser.Boolean_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#boolean_expr}.
	 * @param ctx the parse tree
	 */
	void exitBoolean_expr(@NotNull KantParser.Boolean_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#implements_list}.
	 * @param ctx the parse tree
	 */
	void enterImplements_list(@NotNull KantParser.Implements_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#implements_list}.
	 * @param ctx the parse tree
	 */
	void exitImplements_list(@NotNull KantParser.Implements_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#switch_expr}.
	 * @param ctx the parse tree
	 */
	void enterSwitch_expr(@NotNull KantParser.Switch_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#switch_expr}.
	 * @param ctx the parse tree
	 */
	void exitSwitch_expr(@NotNull KantParser.Switch_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#abelian_atom}.
	 * @param ctx the parse tree
	 */
	void enterAbelian_atom(@NotNull KantParser.Abelian_atomContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#abelian_atom}.
	 * @param ctx the parse tree
	 */
	void exitAbelian_atom(@NotNull KantParser.Abelian_atomContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#compound_type_name}.
	 * @param ctx the parse tree
	 */
	void enterCompound_type_name(@NotNull KantParser.Compound_type_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#compound_type_name}.
	 * @param ctx the parse tree
	 */
	void exitCompound_type_name(@NotNull KantParser.Compound_type_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#type_parameters}.
	 * @param ctx the parse tree
	 */
	void enterType_parameters(@NotNull KantParser.Type_parametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#type_parameters}.
	 * @param ctx the parse tree
	 */
	void exitType_parameters(@NotNull KantParser.Type_parametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#role_decl}.
	 * @param ctx the parse tree
	 */
	void enterRole_decl(@NotNull KantParser.Role_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#role_decl}.
	 * @param ctx the parse tree
	 */
	void exitRole_decl(@NotNull KantParser.Role_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#for_expr}.
	 * @param ctx the parse tree
	 */
	void enterFor_expr(@NotNull KantParser.For_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#for_expr}.
	 * @param ctx the parse tree
	 */
	void exitFor_expr(@NotNull KantParser.For_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(@NotNull KantParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(@NotNull KantParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#method_signature}.
	 * @param ctx the parse tree
	 */
	void enterMethod_signature(@NotNull KantParser.Method_signatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#method_signature}.
	 * @param ctx the parse tree
	 */
	void exitMethod_signature(@NotNull KantParser.Method_signatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#param_decl}.
	 * @param ctx the parse tree
	 */
	void enterParam_decl(@NotNull KantParser.Param_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#param_decl}.
	 * @param ctx the parse tree
	 */
	void exitParam_decl(@NotNull KantParser.Param_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#message}.
	 * @param ctx the parse tree
	 */
	void enterMessage(@NotNull KantParser.MessageContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#message}.
	 * @param ctx the parse tree
	 */
	void exitMessage(@NotNull KantParser.MessageContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#method_decl_hook}.
	 * @param ctx the parse tree
	 */
	void enterMethod_decl_hook(@NotNull KantParser.Method_decl_hookContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#method_decl_hook}.
	 * @param ctx the parse tree
	 */
	void exitMethod_decl_hook(@NotNull KantParser.Method_decl_hookContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#param_list}.
	 * @param ctx the parse tree
	 */
	void enterParam_list(@NotNull KantParser.Param_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#param_list}.
	 * @param ctx the parse tree
	 */
	void exitParam_list(@NotNull KantParser.Param_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#method_decl}.
	 * @param ctx the parse tree
	 */
	void enterMethod_decl(@NotNull KantParser.Method_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#method_decl}.
	 * @param ctx the parse tree
	 */
	void exitMethod_decl(@NotNull KantParser.Method_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#access_qualifier}.
	 * @param ctx the parse tree
	 */
	void enterAccess_qualifier(@NotNull KantParser.Access_qualifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#access_qualifier}.
	 * @param ctx the parse tree
	 */
	void exitAccess_qualifier(@NotNull KantParser.Access_qualifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#type_list}.
	 * @param ctx the parse tree
	 */
	void enterType_list(@NotNull KantParser.Type_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#type_list}.
	 * @param ctx the parse tree
	 */
	void exitType_list(@NotNull KantParser.Type_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#object_decl}.
	 * @param ctx the parse tree
	 */
	void enterObject_decl(@NotNull KantParser.Object_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#object_decl}.
	 * @param ctx the parse tree
	 */
	void exitObject_decl(@NotNull KantParser.Object_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#while_expr}.
	 * @param ctx the parse tree
	 */
	void enterWhile_expr(@NotNull KantParser.While_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#while_expr}.
	 * @param ctx the parse tree
	 */
	void exitWhile_expr(@NotNull KantParser.While_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#self_methods}.
	 * @param ctx the parse tree
	 */
	void enterSelf_methods(@NotNull KantParser.Self_methodsContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#self_methods}.
	 * @param ctx the parse tree
	 */
	void exitSelf_methods(@NotNull KantParser.Self_methodsContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#type_parameter}.
	 * @param ctx the parse tree
	 */
	void enterType_parameter(@NotNull KantParser.Type_parameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#type_parameter}.
	 * @param ctx the parse tree
	 */
	void exitType_parameter(@NotNull KantParser.Type_parameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#interface_body}.
	 * @param ctx the parse tree
	 */
	void enterInterface_body(@NotNull KantParser.Interface_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#interface_body}.
	 * @param ctx the parse tree
	 */
	void exitInterface_body(@NotNull KantParser.Interface_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#argument_list}.
	 * @param ctx the parse tree
	 */
	void enterArgument_list(@NotNull KantParser.Argument_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#argument_list}.
	 * @param ctx the parse tree
	 */
	void exitArgument_list(@NotNull KantParser.Argument_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#type_declaration_list}.
	 * @param ctx the parse tree
	 */
	void enterType_declaration_list(@NotNull KantParser.Type_declaration_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#type_declaration_list}.
	 * @param ctx the parse tree
	 */
	void exitType_declaration_list(@NotNull KantParser.Type_declaration_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link KantParser#type_declaration}.
	 * @param ctx the parse tree
	 */
	void enterType_declaration(@NotNull KantParser.Type_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KantParser#type_declaration}.
	 * @param ctx the parse tree
	 */
	void exitType_declaration(@NotNull KantParser.Type_declarationContext ctx);
}