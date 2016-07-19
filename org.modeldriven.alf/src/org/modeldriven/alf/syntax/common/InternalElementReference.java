
/*******************************************************************************
 * Copyright 2011, 2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.common;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.parser.Token;

import java.util.Collection;
import org.modeldriven.alf.syntax.common.impl.InternalElementReferenceImpl;

/**
 * A direct reference to a UML model element.
 **/

public class InternalElementReference extends ElementReference {

	public InternalElementReference() {
		this.impl = new InternalElementReferenceImpl(this);
	}

	public InternalElementReference(Parser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public InternalElementReference(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public InternalElementReferenceImpl getImpl() {
		return (InternalElementReferenceImpl) this.impl;
	}

	public SyntaxElement getElement() {
		return this.getImpl().getElement();
	}

	public void setElement(SyntaxElement element) {
		this.getImpl().setElement(element);
	}

	public void _deriveAll() {
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		SyntaxElement element = this.getElement();
		if (element != null) {
			System.out.println(prefix + " element:"
					+ element.toString(includeDerived));
		}
	}
} // InternalElementReference