/*******************************************************************************
 * Copyright 2011, 2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class CallBehaviorAction extends CallAction implements
		org.modeldriven.alf.uml.CallBehaviorAction {
	public CallBehaviorAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createCallBehaviorAction());
	}

	public CallBehaviorAction(org.eclipse.uml2.uml.CallBehaviorAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.CallBehaviorAction getBase() {
		return (org.eclipse.uml2.uml.CallBehaviorAction) this.base;
	}

	public org.modeldriven.alf.uml.Behavior getBehavior() {
		return (org.modeldriven.alf.uml.Behavior) wrap(this.getBase()
				.getBehavior());
	}

	public void setBehavior(org.modeldriven.alf.uml.Behavior behavior) {
		this.getBase().setBehavior(
				behavior == null ? null : ((Behavior) behavior).getBase());
	}

}
