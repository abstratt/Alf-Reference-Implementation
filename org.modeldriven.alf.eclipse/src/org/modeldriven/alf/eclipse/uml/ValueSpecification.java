/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ValueSpecification extends TypedElement implements
		org.modeldriven.alf.uml.ValueSpecification {

	public ValueSpecification(org.eclipse.uml2.uml.ValueSpecification base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ValueSpecification getBase() {
		return (org.eclipse.uml2.uml.ValueSpecification) this.base;
	}

}