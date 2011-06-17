
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.NumericUnaryExpressionImpl;

/**
 * A unary expression with a numeric operator.
 **/

public class NumericUnaryExpression extends UnaryExpression {

	public NumericUnaryExpression() {
		this.impl = new NumericUnaryExpressionImpl(this);
	}

	public NumericUnaryExpressionImpl getImpl() {
		return (NumericUnaryExpressionImpl) this.impl;
	}

	/**
	 * A numeric unary expression must have type Integer.
	 **/
	public boolean numericUnaryExpressionTypeDerivation() {
		return this.getImpl().numericUnaryExpressionTypeDerivation();
	}

	/**
	 * A numeric unary expression has the same multiplicity lower bound as its
	 * operand expression.
	 **/
	public boolean numericUnaryExpressionLowerDerivation() {
		return this.getImpl().numericUnaryExpressionLowerDerivation();
	}

	/**
	 * A numeric unary expression has a multiplicity upper bound of 1.
	 **/
	public boolean numericUnaryExpressionUpperDerivation() {
		return this.getImpl().numericUnaryExpressionUpperDerivation();
	}

	/**
	 * The operand expression must have type Integer and a multiplicity upper
	 * bound of 1.
	 **/
	public boolean numericUnaryExpressionOperand() {
		return this.getImpl().numericUnaryExpressionOperand();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.numericUnaryExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"numericUnaryExpressionTypeDerivation", this));
		}
		if (!this.numericUnaryExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"numericUnaryExpressionLowerDerivation", this));
		}
		if (!this.numericUnaryExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"numericUnaryExpressionUpperDerivation", this));
		}
		if (!this.numericUnaryExpressionOperand()) {
			violations.add(new ConstraintViolation(
					"numericUnaryExpressionOperand", this));
		}
	}

	public String toString() {
		return this.toString(false);
	}

	public String toString(boolean includeDerived) {
		return "(" + this.hashCode() + ")"
				+ this.getImpl().toString(includeDerived);
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
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
} // NumericUnaryExpression
