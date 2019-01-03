/*******************************************************************************
 * Copyright 2018-2019 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.interactive.execution;

import java.util.List;
import java.util.stream.Collectors;

import org.modeldriven.alf.interactive.units.ActivityDefinitionWrapper;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.ExpressionStatement;
import org.modeldriven.alf.syntax.statements.ReturnStatement;
import org.modeldriven.alf.syntax.statements.Statement;
import org.modeldriven.alf.syntax.units.FormalParameter;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public class AlfInteractiveUtil {

	protected static List<Member> filterMembers(NamespaceDefinition namespace, boolean isMapped) {
		return namespace.getOwnedMember().stream().
				filter(member->(member.getImpl().getMapping() != null) == isMapped).
				collect(Collectors.toList());
	}

	protected static List<Member> getMappedMembers(NamespaceDefinition namespace) {
		return filterMembers(namespace, true);
	}

	protected static List<Member> getUnmappedMembers(NamespaceDefinition namespace) {
		return filterMembers(namespace, false);
	}
	
	public static UnitDefinition addUnit(UnitDefinition unit) {
		AlfWorkspace.INSTANCE.addOwnedMember(unit.getDefinition());
		return unit;
	}
	
	public static UnitDefinition makeUnit(int counter, SyntaxElement element) {
		return element instanceof UnitDefinition? 
				addUnit((UnitDefinition)element): 
				makeUnit("_" + counter, (Block)element);
	}
	
	public static UnitDefinition makeUnit(NamespaceDefinition definition) {
		UnitDefinition unit = new UnitDefinition();
		unit.setDefinition(definition);
		definition.setUnit(unit);		
		return addUnit(unit);
	}

	public static UnitDefinition makeUnit(String unitName, Block body) {		
		List<Statement> statements = body.getStatement();
		int n = statements.size() - 1;
		if (n >= 0) {
			Statement lastStatement = statements.get(n);
			if (lastStatement instanceof ExpressionStatement) {
				ReturnStatement statement = new ReturnStatement();
				statement.setExpression(((ExpressionStatement)lastStatement).getExpression());
				statements.set(n, statement);
			}
		}		
		return makeUnit(new ActivityDefinitionWrapper(unitName, body));
	}
	
	public static FormalParameter copyFormalParameter(FormalParameter parameter) {
		FormalParameter copy = new FormalParameter();
		copy.getImpl().setExactName(parameter.getName());
		copy.setDirection(parameter.getDirection());
		copy.setType(parameter.getType());
		copy.setLower(parameter.getLower());
		copy.setUpper(parameter.getUpper());
		copy.setIsOrdered(true);
		copy.setIsNonunique(true);
		return copy;	
	}
	
}
