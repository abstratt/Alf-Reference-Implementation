
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

public class StringLiteral extends Expression {

	private String image = "";

	public StringLiteral(String image) {
		this.image = image;
	} // StringLiteral

	public String getImage() {
		return this.image;
	} // getImage

	public String toString() {
		return super.toString() + " image:" + this.getImage();
	} // toString

} // StringLiteral
