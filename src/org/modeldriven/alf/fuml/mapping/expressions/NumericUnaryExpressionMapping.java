
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import org.modeldriven.alf.fuml.mapping.expressions.UnaryExpressionMapping;

import org.modeldriven.alf.syntax.expressions.NumericUnaryExpression;

public class NumericUnaryExpressionMapping extends UnaryExpressionMapping {

	public NumericUnaryExpression getNumericUnaryExpression() {
		return (NumericUnaryExpression) this.getSource();
	}

} // NumericUnaryExpressionMapping
