/*******************************************************************************
 * Copyright 2011, 2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Slot extends Element implements org.modeldriven.alf.uml.Slot {
	public Slot() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createSlot());
	}

	public Slot(org.eclipse.uml2.uml.Slot base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Slot getBase() {
		return (org.eclipse.uml2.uml.Slot) this.base;
	}

	public org.modeldriven.alf.uml.InstanceSpecification getOwningInstance() {
		return (org.modeldriven.alf.uml.InstanceSpecification) wrap(this
				.getBase().getOwningInstance());
	}

	public org.modeldriven.alf.uml.StructuralFeature getDefiningFeature() {
		return (org.modeldriven.alf.uml.StructuralFeature) wrap(this.getBase()
				.getDefiningFeature());
	}

	public void setDefiningFeature(
			org.modeldriven.alf.uml.StructuralFeature definingFeature) {
		this.getBase().setDefiningFeature(
				definingFeature == null ? null
						: ((StructuralFeature) definingFeature).getBase());
	}

	public List<org.modeldriven.alf.uml.ValueSpecification> getValue() {
		List<org.modeldriven.alf.uml.ValueSpecification> list = new ArrayList<org.modeldriven.alf.uml.ValueSpecification>();
		for (org.eclipse.uml2.uml.ValueSpecification element : this.getBase()
				.getValues()) {
			list
					.add((org.modeldriven.alf.uml.ValueSpecification) wrap(element));
		}
		return list;
	}

	public void addValue(org.modeldriven.alf.uml.ValueSpecification value) {
		this.getBase().getValues().add(
				value == null ? null : ((ValueSpecification) value).getBase());
	}

}
