package info.fulloo.trygve.expressions;

/*
 * Trygve IDE 1.3
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

import java.util.List;

import info.fulloo.trygve.code_generation.CodeGenerator;
import info.fulloo.trygve.declarations.Type;
import info.fulloo.trygve.declarations.Declaration.MethodDeclaration;
import info.fulloo.trygve.mylibrary.Unescape_perl_string;
import info.fulloo.trygve.run_time.RTCode;
import info.fulloo.trygve.run_time.RTType;
import info.fulloo.trygve.semantic_analysis.StaticScope;


public abstract class Constant extends Expression {
	private static boolean isDouble(final String s) {
		boolean retval = true;
		try {
			@SuppressWarnings("unused")
			final double value = Double.parseDouble(s);
		} catch (NumberFormatException x) {
			retval = false;
		} catch (Exception anyExceptionIsBad) {
			retval = false;
		}
		return retval;
	}
	private static boolean isInteger(final String s) {
		boolean retval = true;
		try {
			@SuppressWarnings("unused")
			final int value = Integer.parseInt(s);
		} catch (NumberFormatException x) {
			retval = false;
		} catch (Exception anyExceptionIsBad) {
			retval = false;
		}
		return retval;
	}
	
	public abstract boolean isEqualTo(Constant constant);
	
	public static Expression makeConstantExpressionFrom(final String s) {
		final int l = s.length();
		Expression expression = null;
		if (s.equals("true")) {
			expression = new BooleanConstant(true);
		} else if (s.equals("false")) {
			expression = new BooleanConstant(false);
	    } else if (l > 1 && s.charAt(0) == '"' && s.charAt(l-1) == '"') {
			final String subString = s.substring(1, l-1);
			final String parsedString = Unescape_perl_string.unescape_perl_string(subString);
			expression = new StringConstant(parsedString);
		} else if (l == 3 && s.charAt(0) == '\'' && s.charAt(2) == '\'') {
			expression = new CharacterConstant(s.charAt(1));
		} else if (l > 0 && isInteger(s)) {
			expression = new IntegerConstant(s);
		} else if (l > 0 && isDouble(s)) {
			expression = new DoubleConstant(s);
		} else if (l == 0) {
			expression = new NullExpression();
		}
		return expression;
	}
	public static class StringConstant extends Constant {
		public StringConstant(String s) {	// FIXME ? not final
			super(s, StaticScope.globalScope().lookupTypeDeclaration("String"));
			
			// Need to convert all \" to "
			final CharSequence from = "\\\"", to = "\"";
			s = s.replace(from, to);
			string_ = s;
		}
		public String value() {
			return string_;
		}
		@Override public String getText() {
			return '"' + string_ + '"';
		}
		public boolean isEqualTo(Constant constant) {
			boolean retval = false;
			if (constant instanceof StringConstant == false) {
				retval = false;
			} else {
				final StringConstant constantAsString = (StringConstant) constant;
				retval = string_.equals(constantAsString.value());
			}
			return retval;
		}
		
		private String string_;
	}
	public static class IntegerConstant extends Constant {
		public IntegerConstant(final String s) {
			super(s, StaticScope.globalScope().lookupTypeDeclaration("int"));
			l_ = Integer.parseInt(s);
		}
		public IntegerConstant(long l) {
			super(String.valueOf(l), StaticScope.globalScope().lookupTypeDeclaration("int"));
			l_ = l;
		}
		public long value() {
			return l_;
		}
		@Override public String getText() {
			return String.valueOf(l_);
		}
		public boolean isEqualTo(Constant constant) {
			boolean retval = false;
			if (constant instanceof IntegerConstant == false) {
				retval = false;
			} else {
				final IntegerConstant constantAsInteger = (IntegerConstant) constant;
				retval = l_ == constantAsInteger.value();
			}
			return retval;
		}
		
		private long l_;
	}
	public static class DoubleConstant extends Constant {
		public DoubleConstant(final String s) {
			super(s, StaticScope.globalScope().lookupTypeDeclaration("double"));
			l_ = Double.parseDouble(s);
		}
		public DoubleConstant(final double l) {
			super(String.valueOf(l), StaticScope.globalScope().lookupTypeDeclaration("double"));
			l_ = l;
		}
		public double value() {
			return l_;
		}
		@Override public String getText() {
			return String.valueOf(l_);
		}
		public boolean isEqualTo(final Constant constant) {
			boolean retval = false;
			if (constant instanceof DoubleConstant == false) {
				retval = false;
			} else {
				final DoubleConstant constantAsDouble = (DoubleConstant) constant;
				retval = l_ == constantAsDouble.value();
			}
			return retval;
		}
		
		private double l_;
	}
	public static class CharacterConstant extends Constant {
		public CharacterConstant(final char c) {
			super(String.valueOf(c), StaticScope.globalScope().lookupTypeDeclaration("char"));
			c_ = c;
		}
		public char value() {
			return c_;
		}
		@Override public String getText() {
			return "'" + String.valueOf(c_) + "'";
		}
		public boolean isEqualTo(final Constant constant) {
			boolean retval = false;
			if (constant instanceof CharacterConstant == false) {
				retval = false;
			} else {
				final CharacterConstant constantAsCharacter = (CharacterConstant) constant;
				retval = c_ == constantAsCharacter.value();
			}
			return retval;
		}

		private char c_;
	}
	public static class BooleanConstant extends Constant {
		public BooleanConstant(final boolean tf) {
			super(tf? "true": "false", StaticScope.globalScope().lookupTypeDeclaration("boolean"));
			tf_ = tf;
		}
		public boolean value() {
			return tf_;
		}
		@Override public String getText() {
			return tf_? "true": "false";
		}
		public boolean isEqualTo(final Constant constant) {
			boolean retval = false;
			if (constant instanceof BooleanConstant == false) {
				retval = false;
			} else {
				final BooleanConstant constantAsBool = (BooleanConstant) constant;
				retval = tf_ == constantAsBool.value();
			}
			return retval;
		}
		
		private boolean tf_;
	}
	
	@Override public List<RTCode> compileCodeForInScope(final CodeGenerator cg, final MethodDeclaration methodDeclaration, final RTType rtTypeDeclaration, final StaticScope scope) {
		return cg.compileConstant(this, methodDeclaration, rtTypeDeclaration, scope);
	}
	
	private Constant(final String name, final Type type) {
		super(name, type, null);
	}
}
