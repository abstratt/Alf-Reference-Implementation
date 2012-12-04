package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class LiteralReal extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralReal {
	public LiteralReal() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createLiteralReal());
	}

	public LiteralReal(org.eclipse.uml2.uml.LiteralReal base) {
		super(base);
	}

	public org.eclipse.uml2.uml.LiteralReal getBase() {
		return (org.eclipse.uml2.uml.LiteralReal) this.base;
	}

	public float getValue() {
		return this.getBase().getValue();
	}

	public void setValue(float value) {
		this.getBase().setValue(value);
	}

}