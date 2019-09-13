/*******************************************************************************
 * Copyright 2011, 2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;


public class InitialNode extends ControlNode implements
		org.modeldriven.alf.uml.InitialNode {
	public InitialNode() {
		this(new fUML.Syntax.Activities.IntermediateActivities.InitialNode());
	}

	public InitialNode(
			fUML.Syntax.Activities.IntermediateActivities.InitialNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.InitialNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.InitialNode) this.base;
	}

}
