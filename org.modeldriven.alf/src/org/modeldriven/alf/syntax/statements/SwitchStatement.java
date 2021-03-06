
/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.statements.impl.SwitchStatementImpl;

/**
 * A statement that executes (at most) one of a set of statement sequences based
 * on matching a switch value to a set of test cases.
 **/

public class SwitchStatement extends Statement {

	public SwitchStatement() {
		this.impl = new SwitchStatementImpl(this);
	}

	public SwitchStatement(Parser parser) {
		this();
		this.init(parser);
	}

	public SwitchStatement(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public SwitchStatementImpl getImpl() {
		return (SwitchStatementImpl) this.impl;
	}

	public Collection<SwitchClause> getNonDefaultClause() {
		return this.getImpl().getNonDefaultClause();
	}

	public void setNonDefaultClause(Collection<SwitchClause> nonDefaultClause) {
		this.getImpl().setNonDefaultClause(nonDefaultClause);
	}

	public void addNonDefaultClause(SwitchClause nonDefaultClause) {
		this.getImpl().addNonDefaultClause(nonDefaultClause);
	}

	public Expression getExpression() {
		return this.getImpl().getExpression();
	}

	public void setExpression(Expression expression) {
		this.getImpl().setExpression(expression);
	}

	public Block getDefaultClause() {
		return this.getImpl().getDefaultClause();
	}

	public void setDefaultClause(Block defaultClause) {
		this.getImpl().setDefaultClause(defaultClause);
	}

	public Boolean getIsAssured() {
		return this.getImpl().getIsAssured();
	}

	public void setIsAssured(Boolean isAssured) {
		this.getImpl().setIsAssured(isAssured);
	}

	public Boolean getIsDeterminate() {
		return this.getImpl().getIsDeterminate();
	}

	public void setIsDeterminate(Boolean isDeterminate) {
		this.getImpl().setIsDeterminate(isDeterminate);
	}

    /**
     * The assignments before the case expressions of all clauses of a switch
     * statement are the same as the assignments after the expression of the
     * switch statement.
     **/
	public boolean switchStatementAssignmentsBefore() {
		return this.getImpl().switchStatementAssignmentsBefore();
	}

	/**
	 * The same local name may not be assigned in more than one case expression
	 * in a switch statement.
	 **/
	public boolean switchStatementCaseAssignments() {
		return this.getImpl().switchStatementCaseAssignments();
	}

    /**
     * If a name has an assigned source after any clause of a switch statement
     * that is different than before that clause (including newly defined
     * names), the assigned source after the switch statement is the switch
     * statement, with a multiplicity lower bound that is the minimum of the
     * lower bound for the name in each clause and a multiplicity upper bound
     * that is the maximum for the name in each clause (where the name is
     * considered to have multiplicity [0..0] for clauses in which it is not
     * defined and unchanged multiplicity for an implicit default clause, unless
     * the switch statement is assured). Otherwise, the assigned source of a name
     * after the switch statement is the same as before the switch statement.
     **/
	public boolean switchStatementAssignmentsAfter() {
		return this.getImpl().switchStatementAssignmentsAfter();
	}

    /**
     * Any name that is unassigned before a switch statement and is assigned in
     * one or more clauses of the switch statement, has, after the switch
     * statement, a type that is is the effective common ancestor of the types
     * of the name in each clause in which it is defined.
     **/
	public boolean switchStatementAssignments() {
		return this.getImpl().switchStatementAssignments();
	}

	/**
	 * A switch statement expression must have a multiplicity no greater than 1.
	 **/
	public boolean switchStatementExpression() {
		return this.getImpl().switchStatementExpression();
	}

	/**
	 * A switch statement is the enclosing statement for the statements in all
	 * of its switch clauses.
	 **/
	public boolean switchStatementEnclosedStatements() {
		return this.getImpl().switchStatementEnclosedStatements();
	}

	/**
	 * A switch statement is determinate if it has a @determinate annotation.
	 **/
	public boolean switchStatementIsDeterminateDerivation() {
		return this.getImpl().switchStatementIsDeterminateDerivation();
	}

	/**
	 * A switch statement is assured if it has an @assured annotation.
	 **/
	public boolean switchStatementIsAssuredDerivation() {
		return this.getImpl().switchStatementIsAssuredDerivation();
	}

	/**
	 * In addition to an @isolated annotation, a switch statement may have @assured
	 * and @determinate annotations. They may not have arguments.
	 **/
	@Override
    public Boolean annotationAllowed(Annotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

    /**
     * A switch statement has a return value if the blocks of all its clauses
     * have return values, and it either as a default clause or is assured.
     */
    @Override
    public Boolean hasReturnValue() {
        return this.getImpl().hasReturnValue();
    }
    
    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getExpression());
        addExternalReferencesFor(references, this.getNonDefaultClause());
        addExternalReferencesFor(references, this.getDefaultClause());
    }

	@Override
    public void _deriveAll() {
		this.getIsAssured();
		this.getIsDeterminate();
		super._deriveAll();
		Collection<SwitchClause> nonDefaultClause = this.getNonDefaultClause();
		if (nonDefaultClause != null) {
			for (Object _nonDefaultClause : nonDefaultClause.toArray()) {
				((SwitchClause) _nonDefaultClause).deriveAll();
			}
		}
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.deriveAll();
		}
		Block defaultClause = this.getDefaultClause();
		if (defaultClause != null) {
			defaultClause.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.switchStatementAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"switchStatementAssignmentsBefore", this));
		}
		if (!this.switchStatementCaseAssignments()) {
			violations.add(new ConstraintViolation(
					"switchStatementCaseAssignments", this));
		}
		if (!this.switchStatementAssignmentsAfter()) {
			violations.add(new ConstraintViolation(
					"switchStatementAssignmentsAfter", this));
		}
		if (!this.switchStatementAssignments()) {
			violations.add(new ConstraintViolation(
					"switchStatementAssignments", this));
		}
		if (!this.switchStatementExpression()) {
			violations.add(new ConstraintViolation("switchStatementExpression",
					this));
		}
		if (!this.switchStatementEnclosedStatements()) {
			violations.add(new ConstraintViolation(
					"switchStatementEnclosedStatements", this));
		}
		if (!this.switchStatementIsDeterminateDerivation()) {
			violations.add(new ConstraintViolation(
					"switchStatementIsDeterminateDerivation", this));
		}
		if (!this.switchStatementIsAssuredDerivation()) {
			violations.add(new ConstraintViolation(
					"switchStatementIsAssuredDerivation", this));
		}
		Collection<SwitchClause> nonDefaultClause = this.getNonDefaultClause();
		if (nonDefaultClause != null) {
			for (Object _nonDefaultClause : nonDefaultClause.toArray()) {
				((SwitchClause) _nonDefaultClause).checkConstraints(violations);
			}
		}
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.checkConstraints(violations);
		}
		Block defaultClause = this.getDefaultClause();
		if (defaultClause != null) {
			defaultClause.checkConstraints(violations);
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isAssured:");
			s.append(this.getIsAssured());
		}
		if (includeDerived) {
			s.append(" /isDeterminate:");
			s.append(this.getIsDeterminate());
		}
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		Collection<SwitchClause> nonDefaultClause = this.getNonDefaultClause();
		if (nonDefaultClause != null && nonDefaultClause.size() > 0) {
			System.out.println(prefix + " nonDefaultClause:");
			for (Object _object : nonDefaultClause.toArray()) {
				SwitchClause _nonDefaultClause = (SwitchClause) _object;
				if (_nonDefaultClause != null) {
					_nonDefaultClause.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		Expression expression = this.getExpression();
		if (expression != null) {
			System.out.println(prefix + " expression:");
			expression.print(prefix + "  ", includeDerived);
		}
		Block defaultClause = this.getDefaultClause();
		if (defaultClause != null) {
			System.out.println(prefix + " defaultClause:");
			defaultClause.print(prefix + "  ", includeDerived);
		}
	}
} // SwitchStatement
