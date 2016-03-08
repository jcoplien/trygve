grammar Kant;

/*
 * Trygve IDE
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

@header {
    // package info.fulloo.trygve.parser;
}

program
		: type_declaration_list main
		| type_declaration_list		// missing main
		;

main
		: expr
		;

type_declaration_list
   	    : type_declaration
        | type_declaration_list type_declaration
        | /* null */
        ;

type_declaration
		: context_declaration
		| class_declaration
		| interface_declaration
		;
		
context_declaration
        : 'context' JAVA_ID '{' context_body '}'
        ;
        
class_declaration
        : 'class'   JAVA_ID type_parameters (implements_list)* '{' class_body '}'
        | 'class'   JAVA_ID type_parameters 'extends' JAVA_ID (implements_list)* '{' class_body '}'
        | 'class'   JAVA_ID (implements_list)* '{' class_body '}'
        | 'class'   JAVA_ID 'extends' JAVA_ID (implements_list)* '{' class_body '}'
        | 'class'   JAVA_ID (implements_list)* 'extends' JAVA_ID '{' class_body '}'
        ;
        
interface_declaration
        : 'interface' JAVA_ID '{' interface_body '}'
        ;
        
implements_list
		: 'implements' JAVA_ID
		| implements_list ',' JAVA_ID
		;

type_parameters
    	: '<' type_parameter (',' type_parameter)* '>'
   		;

type_list
		: '<' type_name (',' type_name)* '>'
		;

type_parameter
    	: type_name ('extends' type_name)?
    	;

context_body
        : context_body context_body_element
        | context_body_element
        | /* null */
        ;

context_body_element
        : method_decl
        | object_decl
        | role_decl
        | stageprop_decl
        | context_declaration
        ;

role_decl
		: 'role' role_vec_modifier JAVA_ID '{' role_body '}'
		| 'role' role_vec_modifier JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		| access_qualifier 'role' role_vec_modifier JAVA_ID '{' role_body '}'
		| access_qualifier 'role' role_vec_modifier JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		| 'role' role_vec_modifier JAVA_ID '{' role_body '}'
		| 'role' role_vec_modifier JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		| access_qualifier 'role' role_vec_modifier JAVA_ID '{' role_body '}'
		| access_qualifier 'role' role_vec_modifier JAVA_ID '{' role_body '}' REQUIRES '{' self_methods '}'
		;

role_vec_modifier
		: '[' ']'
		| /* null */
		;

role_body
        : method_decl
        | role_body method_decl
        | object_decl				// illegal
        | role_body object_decl		// illegal - for better error messages only
        | /* null */
        ;

self_methods
		: self_methods ';' method_signature
		| method_signature
		| self_methods /* null */ ';'
		;

stageprop_decl
		: 'stageprop' role_vec_modifier JAVA_ID '{' stageprop_body '}'
		| 'stageprop' role_vec_modifier JAVA_ID '{' stageprop_body '}' REQUIRES '{' self_methods '}'
		| access_qualifier 'stageprop' role_vec_modifier JAVA_ID '{' stageprop_body '}'
		| access_qualifier 'stageprop' role_vec_modifier JAVA_ID '{' stageprop_body '}' REQUIRES '{' self_methods '}'
		| 'stageprop' role_vec_modifier JAVA_ID '{' '}'
		| 'stageprop' role_vec_modifier JAVA_ID '{'  '}' REQUIRES '{' self_methods '}'
		| access_qualifier 'stageprop' role_vec_modifier JAVA_ID '{' '}'
		| access_qualifier 'stageprop' role_vec_modifier JAVA_ID '{' '}' REQUIRES '{' self_methods '}'
		;

stageprop_body
        : method_decl
        | stageprop_body method_decl
        | object_decl				// illegal
        | stageprop_body object_decl		// illegal - for better error messages only
        ;

class_body
        : class_body class_body_element
        | class_body_element
        | /* null */
        ;

class_body_element
        : method_decl
        | object_decl
        | type_declaration
        ;
        
interface_body
		: interface_body ';' method_signature
		| method_signature
		| interface_body /* null */ ';'
		;

method_decl
        : method_decl_hook '{' type_and_expr_and_decl_list '}'
        ;

method_decl_hook
		: method_signature
		;

method_signature
		: access_qualifier return_type method_name '(' param_list ')' CONST*
		| access_qualifier return_type method_name CONST*
		| access_qualifier method_name '(' param_list ')' CONST*
		;

expr_and_decl_list
		: object_decl
        | expr ';' object_decl
        | expr_and_decl_list object_decl
        | expr_and_decl_list expr
        | expr_and_decl_list /* null-expr */ ';'
        | /* null */
        ;
 
type_and_expr_and_decl_list
 		: expr_and_decl_list
 		| expr_and_decl_list type_declaration
 		| type_declaration expr_and_decl_list
 		;

return_type
        : type_name '[' ']'
        | type_name
        | /* null */
        ;

method_name
        : JAVA_ID
        | ABELIAN_SUMOP
		| ABELIAN_MULOP
        ;

access_qualifier
        : 'public' | 'private' | /* null */
        ;

object_decl
        : access_qualifier compound_type_name identifier_list ';'
        | access_qualifier compound_type_name identifier_list
        | compound_type_name identifier_list /* null expr */ ';'
        | compound_type_name identifier_list
        ;

trivial_object_decl
		: compound_type_name JAVA_ID
		;

compound_type_name
        : type_name '[' ']'
        | type_name
        ;

type_name
        : JAVA_ID
        | JAVA_ID type_list
        | builtin_type_name
        ;
        
builtin_type_name
		: 'int'
        | 'double'
        | 'char'
        | 'String'
        ;

identifier_list
        : JAVA_ID
        | identifier_list ',' JAVA_ID
        | JAVA_ID ASSIGN expr
        | identifier_list ',' JAVA_ID ASSIGN expr
        ;

param_list
        : param_decl
        | param_list ',' param_decl
        | param_list ELLIPSIS
        | param_list ',' ELLIPSIS
        | /* null */
        ;

param_decl
        : compound_type_name JAVA_ID
        ;

expr
		: abelian_expr
		| boolean_expr
        | block
        | if_expr
        | for_expr
        | while_expr
        | do_while_expr
        | switch_expr
        | BREAK
        | CONTINUE
        | RETURN expr
        | RETURN
        ;

// Abelian grammar inspired by: http://stackoverflow.com/questions/4019687/antlr-problem-differntiating-unary-and-binary-operators-e-g-minus-sign

abelian_expr
		: abelian_product (ABELIAN_SUMOP abelian_product)*
		| if_expr
		| <assoc=right> abelian_expr ASSIGN expr
		;
		
abelian_product
		: <assoc=right> abelian_product POW abelian_atom
		| abelian_unary_op (ABELIAN_MULOP abelian_unary_op)*
		;
		
abelian_unary_op
		:  ABELIAN_SUMOP abelian_atom
  		|  abelian_atom
 		;
	
abelian_atom
		: NEW message
        | NEW type_name '[' expr ']'
        | NEW JAVA_ID type_list '(' argument_list ')'
        | <assoc=left> abelian_atom '.' message
        | <assoc=left> builtin_type_name '.' message
		| abelian_atom '.' JAVA_ID
		| null_expr
		| /* this. */ message
        | JAVA_ID
        | abelian_atom ABELIAN_INCREMENT_OP
        | ABELIAN_INCREMENT_OP abelian_atom
        | constant
        | '(' abelian_expr ')'
        | abelian_atom '[' expr ']'
        | abelian_atom '[' expr ']' ABELIAN_INCREMENT_OP
        | ABELIAN_INCREMENT_OP expr '[' expr ']'
        | ABELIAN_INCREMENT_OP expr '.' JAVA_ID
        | abelian_atom  '.' JAVA_ID ABELIAN_INCREMENT_OP
        | abelian_atom '.' CLONE
        | abelian_atom '.' CLONE '(' ')'
        ;

boolean_expr
		: boolean_product (BOOLEAN_SUMOP boolean_product)*
		| if_expr
		| boolean_expr op=('&&' | '||' | '^' ) boolean_expr
		| abelian_expr op1=('is' | 'Is') op2 =('not' | 'Not') abelian_expr
		| abelian_expr op=('!=' | '==' | GT | LT | '>=' | '<=' | 'is' | 'isnot' | 'Is' | 'IsNot') abelian_expr
		| <assoc=right> boolean_expr ASSIGN expr
		| abelian_expr
		;
		
boolean_product
		: boolean_unary_op (BOOLEAN_MULOP boolean_unary_op)*
		;
		
boolean_unary_op
  		:  LOGICAL_NEGATION boolean_expr
  		|  boolean_atom
 		;
	
boolean_atom
		: constant
		| JAVA_ID
		| null_expr
		| '(' boolean_expr ')'
        ;

message
        : <assoc=right> method_name '(' argument_list ')'
        | <assoc=right> type_name '(' argument_list ')'
        ;

block
        : '{' expr_and_decl_list '}'
        | '{' '}'
        ;

expr_or_null
        : expr
        | /* null */
        ;

if_expr
        : 'if' '(' expr ')' expr
        | 'if' '(' expr ')' expr 'else' expr
        ;

for_expr
		: 'for' '(' expr ';' expr ';' expr ')' expr
        | 'for' '(' object_decl expr ';' expr ')' expr  // O.K. � expr can be a block
        | 'for' '(' JAVA_ID ':' expr ')' expr
        | 'for' '(' trivial_object_decl ':' expr ')' expr
        ;

while_expr
		: 'while' '(' expr ')' expr
		;

do_while_expr
		: 'do' expr 'while' '(' expr ')'
		;

switch_expr
		: SWITCH '(' expr ')' '{'  ( switch_body )* '}'
		;

switch_body
		: ( CASE constant | DEFAULT ) ':' expr_and_decl_list
		;

null_expr
		: NULL
		;

constant
        : STRING
        | INTEGER
        | FLOAT
        | BOOLEAN
        ;

argument_list
        : expr
        | argument_list ',' expr
        | /* null */
        ;


// Lexer rules

fragment
OctalDigit
    :   [0-7]
    ;
    
fragment
HexDigit
    :   [0-9a-fA-F]
    ;

//-----------------------


// STRING : '"' ( ~'"' | '\\' '"' )* '"' ;

STRING
    :   '"' StringCharacters? '"'
    ;
fragment
StringCharacters
    :   StringCharacter+
    ;
fragment
StringCharacter
    :   ~["\\]
    |   EscapeSequence
    ;
// §3.10.6 Escape Sequences for Character and String Literals
fragment
EscapeSequence
    :   '\\' [btnfr"'\\]
    |   OctalEscape
    |   UnicodeEscape
    ;

fragment
OctalEscape
    :   '\\' OctalDigit
    |   '\\' OctalDigit OctalDigit
    |   '\\' ZeroToThree OctalDigit OctalDigit
    ;

fragment
UnicodeEscape
    :   '\\' 'u' HexDigit HexDigit HexDigit HexDigit
    ;

fragment
ZeroToThree
    :   [0-3]
    ;

INTEGER : ('1' .. '9')+ ('0' .. '9')* | '0';

FLOAT : (('1' .. '9')+ ('0' .. '9')* | '0') '.' ('0' .. '9')* ;

BOOLEAN : 'true' | 'false' ;

SWITCH : 'switch' ;

CASE : 'case' ;

DEFAULT : 'default' ;

BREAK : 'break' ;

CONTINUE : 'continue' ;

RETURN : 'return' ;

REQUIRES : 'requires' ;

NEW : 'new' ;

CLONE : 'clone' ;

NULL : 'null' ;

CONST : 'const' ;

POW : '**' ;

ELLIPSIS : '...' ;

BOOLEAN_SUMOP : '||' | '^' ;

BOOLEAN_MULOP : '&&'  ;

ABELIAN_SUMOP : '+' | '-' ;

ABELIAN_MULOP : '*' | '/' | '%' ;

MINUS : '-' ;

PLUS : '+' ;

LT : '<' ;

GT : '>' ;

LOGICAL_NEGATION : '!' ;

ABELIAN_INCREMENT_OP : '++' | '--' ;

JAVA_ID: (('a' .. 'z') | ('A' .. 'Z')) (('a' .. 'z') | ('A' .. 'Z') | ('0' .. '9') | '_')* ;

INLINE_COMMENT: '//' ~[\r\n]* -> channel(HIDDEN) ;

C_COMMENT: '/*' .*? '*/' -> channel(HIDDEN) ;

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> channel(HIDDEN) ;

ASSIGN : '=' ;