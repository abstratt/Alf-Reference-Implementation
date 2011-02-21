
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

/**
 * An invocation of a behavior referenced by name.
 **/

public class BehaviorInvocationExpressionImpl
		extends
		org.modeldriven.alf.syntax.expressions.impl.gen.InvocationExpressionImpl {

	public BehaviorInvocationExpressionImpl(BehaviorInvocationExpression self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.BehaviorInvocationExpression getSelf() {
		return (BehaviorInvocationExpression) this.self;
	}

	/**
	 * The referent of a behavior invocation expression is the behavior named by
	 * the target or, if the target disambiguates to a feature reference, the
	 * operation or signal being invoked.
	 **/
	public boolean behaviorInvocationExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	/**
	 * If the target qualified name disambiguates to a feature reference, then
	 * the feature of a behavior invocation expression is that feature
	 * reference.
	 **/
	public boolean behaviorInvocationExpressionFeatureDerivation() {
		this.getSelf().getFeature();
		return true;
	}

	/**
	 * If the target qualified name does not disambiguate to a feature
	 * reference, then it must resolve to a behavior or an association end.
	 * Otherwise it must resolve to a single feature referent according to the
	 * overloading resolution rules, unless it is an implicit destructor call
	 * (in which case it has no referent).
	 **/
	public boolean behaviorInvocationExpressionReferentConstraint() {
		return true;
	}

	/**
	 * An input argument expression must be assignable to its corresponding
	 * parameter. An output parameter must be assignable to its corresponding
	 * argument expression. (Note that this implies that the type of an argument
	 * expression for an inout parameter must be the same as the type of that
	 * parameter.)
	 **/
	public boolean behaviorInvocationExpressionArgumentCompatibility() {
		return true;
	}

} // BehaviorInvocationExpressionImpl
