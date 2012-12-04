package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Class_ extends BehavioredClassifier implements
		org.modeldriven.alf.uml.Class_ {
	public Class_() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createClass());
	}

	public Class_(org.eclipse.uml2.uml.Class base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Class getBase() {
		return (org.eclipse.uml2.uml.Class) this.base;
	}

	public List< org.modeldriven.alf.uml.Operation> getOwnedOperation
() {
		List< org.modeldriven.alf.uml.Operation> list = new ArrayList< org.modeldriven.alf.uml.Operation>();
		for (org.eclipse.uml2.uml.Operation
 element: this.getBase().getOwnedOperation
s()) {
			list.add( new Operation(element)
);
		}
		return list;
	}

	public void addOwnedOperation
( org.modeldriven.alf.uml.Operation ownedOperation) {
		this.getBase().getOwnedOperation
s.add( ownedOperation == null? null: ((Operation)ownedOperation).getBase()
);
	}

	public boolean getIsActive() {
		return this.getBase().getIsActive();
	}

	public void setIsActive(boolean isActive) {
		this.getBase().setIsActive(isActive);
	}

	public List< org.modeldriven.alf.uml.Reception> getOwnedReception
() {
		List< org.modeldriven.alf.uml.Reception> list = new ArrayList< org.modeldriven.alf.uml.Reception>();
		for (org.eclipse.uml2.uml.Reception
 element: this.getBase().getOwnedReception
s()) {
			list.add( new Reception(element)
);
		}
		return list;
	}

	public void addOwnedReception
( org.modeldriven.alf.uml.Reception ownedReception) {
		this.getBase().getOwnedReception
s.add( ownedReception == null? null: ((Reception)ownedReception).getBase()
);
	}

	public List< org.modeldriven.alf.uml.Property> getOwnedAttribute
() {
		List< org.modeldriven.alf.uml.Property> list = new ArrayList< org.modeldriven.alf.uml.Property>();
		for (org.eclipse.uml2.uml.Property
 element: this.getBase().getOwnedAttribute
s()) {
			list.add( new Property(element)
);
		}
		return list;
	}

	public void addOwnedAttribute
( org.modeldriven.alf.uml.Property ownedAttribute) {
		this.getBase().getOwnedAttribute
s.add( ownedAttribute == null? null: ((Property)ownedAttribute).getBase()
);
	}

	public List< org.modeldriven.alf.uml.Classifier> getNestedClassifier
() {
		List< org.modeldriven.alf.uml.Classifier> list = new ArrayList< org.modeldriven.alf.uml.Classifier>();
		for (org.eclipse.uml2.uml.Classifier
 element: this.getBase().getNestedClassifier
s()) {
			list.add( new Classifier(element)
);
		}
		return list;
	}

	public void addNestedClassifier
( org.modeldriven.alf.uml.Classifier nestedClassifier) {
		this.getBase().getNestedClassifier
s.add( nestedClassifier == null? null: ((Classifier)nestedClassifier).getBase()
);
	}

	public boolean getIsID() {
		return this.getBase().getIsID();
	}

	public void setIsID(boolean isID) {
		this.getBase().setIsID(isID);
	}

	public List< org.modeldriven.alf.uml.Class_> getSuperClass
() {
		List< org.modeldriven.alf.uml.Class_> list = new ArrayList< org.modeldriven.alf.uml.Class_>();
		for (org.eclipse.uml2.uml.Class
 element: this.getBase().getSuperClass
s()) {
			list.add( new Class_(element)
);
		}
		return list;
	}

}