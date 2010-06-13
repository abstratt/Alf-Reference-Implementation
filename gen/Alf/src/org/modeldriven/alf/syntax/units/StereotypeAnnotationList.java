
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class StereotypeAnnotationList {

	private ArrayList<StereotypeAnnotation> list = new ArrayList<StereotypeAnnotation>();

	public void add(StereotypeAnnotation annotation) {
		this.list.add(annotation);
	} // add

	public ArrayList<StereotypeAnnotation> getList() {
		return this.list;
	} // getList

	public void print(String prefix) {
		for (StereotypeAnnotation annotation : this.getList()) {
			annotation.print(prefix);
		}
	} // print

} // StereotypeAnnotationList
