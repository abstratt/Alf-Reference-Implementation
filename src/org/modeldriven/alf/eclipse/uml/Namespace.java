package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Namespace extends NamedElement implements
		org.modeldriven.alf.uml.Namespace {

	public Namespace(org.eclipse.uml2.uml.Namespace base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Namespace getBase() {
		return (org.eclipse.uml2.uml.Namespace) this.base;
	}

	public List< org.modeldriven.alf.uml.NamedElement> getMember
() {
		List< org.modeldriven.alf.uml.NamedElement> list = new ArrayList< org.modeldriven.alf.uml.NamedElement>();
		for (org.eclipse.uml2.uml.NamedElement
 element: this.getBase().getMember
s()) {
			list.add( new NamedElement(element)
);
		}
		return list;
	}

	public List< org.modeldriven.alf.uml.NamedElement> getOwnedMember
() {
		List< org.modeldriven.alf.uml.NamedElement> list = new ArrayList< org.modeldriven.alf.uml.NamedElement>();
		for (org.eclipse.uml2.uml.NamedElement
 element: this.getBase().getOwnedMember
s()) {
			list.add( new NamedElement(element)
);
		}
		return list;
	}

	public List< org.modeldriven.alf.uml.ElementImport> getElementImport
() {
		List< org.modeldriven.alf.uml.ElementImport> list = new ArrayList< org.modeldriven.alf.uml.ElementImport>();
		for (org.eclipse.uml2.uml.ElementImport
 element: this.getBase().getElementImport
s()) {
			list.add( new ElementImport(element)
);
		}
		return list;
	}

	public void addElementImport
( org.modeldriven.alf.uml.ElementImport elementImport) {
		this.getBase().getElementImport
s.add( elementImport == null? null: ((ElementImport)elementImport).getBase()
);
	}

	public List< org.modeldriven.alf.uml.PackageImport> getPackageImport
() {
		List< org.modeldriven.alf.uml.PackageImport> list = new ArrayList< org.modeldriven.alf.uml.PackageImport>();
		for (org.eclipse.uml2.uml.PackageImport
 element: this.getBase().getPackageImport
s()) {
			list.add( new PackageImport(element)
);
		}
		return list;
	}

	public void addPackageImport
( org.modeldriven.alf.uml.PackageImport packageImport) {
		this.getBase().getPackageImport
s.add( packageImport == null? null: ((PackageImport)packageImport).getBase()
);
	}

	public List< org.modeldriven.alf.uml.PackageableElement> getImportedMember
() {
		List< org.modeldriven.alf.uml.PackageableElement> list = new ArrayList< org.modeldriven.alf.uml.PackageableElement>();
		for (org.eclipse.uml2.uml.PackageableElement
 element: this.getBase().getImportedMember
s()) {
			list.add( new PackageableElement(element)
);
		}
		return list;
	}

}