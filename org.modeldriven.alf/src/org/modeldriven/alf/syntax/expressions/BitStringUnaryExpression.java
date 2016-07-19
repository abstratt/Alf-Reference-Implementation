/*******************************************************************************
 * Copyright 2011, 2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.common.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.expressions.impl.BitStringUnaryExpressionImpl;

public class BitStringUnaryExpression extends UnaryExpression {

	public BitStringUnaryExpression() {
		this.impl = new BitStringUnaryExpressionImpl(this);
	}

	public BitStringUnaryExpression(Parser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public BitStringUnaryExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public BitStringUnaryExpressionImpl getImpl() {
		return (BitStringUnaryExpressionImpl) this.impl;
	}

	public Boolean getIsBitStringConversion() {
		return this.getImpl().getIsBitStringConversion();
	}

	public void setIsBitStringConversion(Boolean isBitStringConversion) {
		this.getImpl().setIsBitStringConversion(isBitStringConversion);
	}

	/**
	 * A BitString unary expression has type BitString.
	 **/
	public boolean bitStringUnaryExpressionTypeDerivation() {
		return this.getImpl().bitStringUnaryExpressionTypeDerivation();
	}

	/**
	 * A BitString unary expression has a multiplicity lower bound of 1.
	 **/
	public boolean bitStringUnaryExpressionLowerDerivation() {
		return this.getImpl().bitStringUnaryExpressionLowerDerivation();
	}

	/**
	 * A BitString unary expression has a multiplicity upper bound of 1.
	 **/
	public boolean bitStringUnaryExpressionUpperDerivation() {
		return this.getImpl().bitStringUnaryExpressionUpperDerivation();
	}

	/**
	 * The operand expression of a BitString unary expression must have type
	 * BitString or Integer and multiplicity lower and upper bounds of 1.
	 **/
	public boolean bitStringUnaryExpressionOperand() {
		return this.getImpl().bitStringUnaryExpressionOperand();
	}

	/**
	 * BitString conversion is required if the operand expression of a BitString
	 * unary expression has a type that conforms to type Integer.
	 **/
	public boolean bitStringUnaryExpressionIsBitStringConversionDerivation() {
		return this.getImpl()
				.bitStringUnaryExpressionIsBitStringConversionDerivation();
	}

	public void _deriveAll() {
		this.getIsBitStringConversion();
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.bitStringUnaryExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"bitStringUnaryExpressionTypeDerivation", this));
		}
		if (!this.bitStringUnaryExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"bitStringUnaryExpressionLowerDerivation", this));
		}
		if (!this.bitStringUnaryExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"bitStringUnaryExpressionUpperDerivation", this));
		}
		if (!this.bitStringUnaryExpressionOperand()) {
			violations.add(new ConstraintViolation(
					"bitStringUnaryExpressionOperand", this));
		}
		if (!this.bitStringUnaryExpressionIsBitStringConversionDerivation()) {
			violations.add(new ConstraintViolation(
					"bitStringUnaryExpressionIsBitStringConversionDerivation",
					this));
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isBitStringConversion:");
			s.append(this.getIsBitStringConversion());
		}
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
	}
} // BitStringUnaryExpression