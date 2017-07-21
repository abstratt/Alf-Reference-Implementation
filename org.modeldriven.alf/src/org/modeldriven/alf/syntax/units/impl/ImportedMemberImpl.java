/*******************************************************************************
 * Copyright 2011-2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.units.*;
import org.modeldriven.alf.uml.Element;

public class ImportedMemberImpl extends MemberImpl {

    private ElementReference referent = null;
    
    private boolean isImported = true;

	public ImportedMemberImpl(ImportedMember self) {
		super(self);
	}

	@Override
	public ImportedMember getSelf() {
		return (ImportedMember) this.self;
	}

	@Override
    public ElementReference getReferent() {
        return this.referent;
    }
	
    public void setReferent(ElementReference referent) {
        this.referent = referent;
    }
    
    @Override
    public boolean isImported() {
        return this.isImported;
    }
    
    public void setIsImported(boolean isImported) {
        this.isImported = isImported;
    }
    
    @Override
    public Boolean getIsStub() {
        if (super.getIsStub()) {
            return true;
        } else if (this.isImported()) {
            return false;
        } else {
            // Consider an external operation, activity or property (default
            // value) to be a stub, since they can be specified using Alf 
            // subunits.
            ElementReference referent = this.getReferent();
            return referent.getImpl().isOperation() || 
                    referent.getImpl().isActivity() ||
                    referent.getImpl().isProperty();
        }
    }
    
    @Override
    public UnitDefinition getSubunit() {
        return null;
    }

    /**
     * An imported element should not generally be considered a feature of the
     * namespace it is imported into.
     **/
	@Override
	protected Boolean deriveIsFeature() {
	    return !this.isImported && this.getReferent().getImpl().isFeature();
	}
	
	/*
	 * Derivations
	 */

    public boolean importedMemberIsFeatureDerivation() {
        this.getSelf().getIsFeature();
        return true;
    }
    
    /*
     * Constraints
     */

	/**
	 * An imported element is not a stub.
	 **/
	public boolean importedMemberNotStub() {
		return !this.isImported() || !this.getSelf().getIsStub();
	}
	
	 @Override
	 public boolean memberStub() {
	     return true;
	 }

    /*
     * Helper Methods
     */
    
	/**
	 * Returns false. (Imported members do not have annotations.)
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return false;
	} // annotationAllowed

	/**
	 * If the given member is not an imported member, then return the result of
	 * checking whether the given member is the same kind as this member.
	 * Else, if the element of the referent for this member is an Alf member,
	 * then return the result of checking whether that element is
	 * distinguishable from the given member. Else, if the element of the
	 * referent for the given member is an Alf member, then return the result of
	 * checking whether that element is distinguishable from this member. Else,
	 * the referents for both this and the given member are UML elements, so
	 * return the result of checking their distinguishability according to the
	 * rules of the UML superstructure.
	 **/
	// TODO: Clean up.
//	public Boolean isSameKindAs(Member member) {
//	    ImportedMember self = this.getSelf();
//	    if (member == null) {
//	        return false;
//	    } else if (!(member instanceof ImportedMember)) {
//	        return member.isSameKindAs(self);
//	    } else {
//	        ElementReference referent = self.getReferent();
//	        ElementReference otherReferent = ((ImportedMember)member).getReferent();
//	        if (referent == null || otherReferent == null) {
//	            return false;
//	        } else {
//    	        SyntaxElement element = referent.getImpl().getAlf();
//    	        SyntaxElement otherElement = otherReferent.getImpl().getAlf();
//    	        if (element != null) {
//    	            return element instanceof Member && ((Member)element).isSameKindAs(member);
//    	        } else if (otherElement != null) {
//    	            return otherElement instanceof Member && ((Member)otherElement).isSameKindAs(self);
//    	        } else {
//    	            Element umlElement = referent.getImpl().getUml();
//    	            Element otherUmlElement = otherReferent.getImpl().getUml();
//    	            if (!(umlElement instanceof NamedElement && 
//    	                    otherUmlElement instanceof NamedElement)) {
//    	                return false;
//    	            } else {
//    	                NamedElement namedElement = (NamedElement)umlElement;
//    	                Namespace namespace = namedElement.getNamespace();
//    	                return namespace != null && 
//    	                        !namedElement.isDistinguishableFrom(
//    	                                (NamedElement)otherUmlElement, namespace);
//    	            }
//    	        }
//	        }
//	    }
//	}
	
	/**
	 * Allow an external operation, owned behavior (activity) or property to 
	 * serve as the "stub" for an activity definition. The names are assumed to
	 * already match. (Note: The case of a property covers the use of an 
	 * activity definition to define the default value of an external property.)
	 */
	@Override
	public Boolean matchForStub(UnitDefinition unit) {
	    ElementReference referent = this.getReferent();
	    return !this.isImported() && 
	            unit.getDefinition() instanceof ActivityDefinition &&
	            (referent.getImpl().isOperation() || 
	                    referent.getImpl().isOwnedBehavior() ||
	                    referent.getImpl().isProperty());
	}

    @Override
    public boolean isProfile() {
        return this.getReferent().getImpl().isProfile();
    }
    
    @Override
    public boolean isStereotype() {
        return this.getReferent().getImpl().isStereotype();
    }
    
    public static ImportedMember makeImportedMember(Member member) {
        ImportedMember importedMember = new ImportedMember();
        importedMember.setReferent(member.getImpl().getReferent());
        importedMember.setName(member.getName());
        importedMember.setVisibility(member.getImpl().getVisibility());
        return importedMember;
    }
    
    public static ImportedMember makeImportedMember(ElementReference reference) {
        return makeImportedMember(reference, true);        
    }

    public static ImportedMember makeImportedMember(ElementReference reference, boolean isImported) {
        ImportedMember importedMember = new ImportedMember();
        importedMember.setReferent(reference);
        importedMember.getImpl().setExactName(reference.getImpl().getName());
        importedMember.getImpl().setIsImported(isImported);
        importedMember.setVisibility(reference.getImpl().getVisibility());
        return importedMember;        
    }

    public static ImportedMember makeImportedMember(
            String name, Element element, NamespaceDefinition namespace) {
        ImportedMember importedMember = makeImportedMember(
                ElementReferenceImpl.makeElementReference(element), false);
        importedMember.getImpl().setExactName(name == null? "": name);
        importedMember.setNamespace(namespace);
        return importedMember;
    }
    
    protected void bindTo(SyntaxElement base, 
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof Member) {
            ImportedMember baseMember = (ImportedMember)base;
            this.setReferent(baseMember.getImpl().getReferent());
            this.setIsImported(baseMember.getImpl().isImported());
        }
    }
    
} // ImportedMemberImpl
