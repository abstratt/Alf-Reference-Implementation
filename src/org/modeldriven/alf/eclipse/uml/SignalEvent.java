package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class SignalEvent extends MessageEvent implements
		org.modeldriven.alf.uml.SignalEvent {
	public SignalEvent() {
		this(UMLFactory.eINSTANCE.createSignalEvent());
	}

	public SignalEvent(
			fUML.Syntax.CommonBehaviors.Communications.SignalEvent base) {
		super(base);
	}

	public org.eclipse.uml2.uml.SignalEvent getBase() {
		return (org.eclipse.uml2.uml.SignalEvent) this.base;
	}

	public org.modeldriven.alf.uml.Signal getSignal() {
		return new Signal(this.getBase().getSignal());
	}

	public void setSignal(org.modeldriven.alf.uml.Signal signal) {
		this.getBase().setSignal(
				signal == null ? null : ((Signal) signal).getBase());
	}

}
