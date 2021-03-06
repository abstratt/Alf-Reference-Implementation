
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
import org.modeldriven.alf.syntax.statements.impl.SwitchClauseImpl;

/**
 * A clause in a switch statement with a set of cases and a sequence of
 * statements that may be executed if one of the cases matches the switch value.
 **/

public class SwitchClause extends SyntaxElement {

	public SwitchClause() {
		this.impl = new SwitchClauseImpl(this);
	}

	public SwitchClause(Parser parser) {
		this();
		this.init(parser);
	}

	public SwitchClause(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public SwitchClauseImpl getImpl() {
		return (SwitchClauseImpl) this.impl;
	}

	public Collection<Expression> getCase() {
		return this.getImpl().getCase();
	}

	public void setCase(Collection<Expression> case_) {
		this.getImpl().setCase(case_);
	}

	public void addCase(Expression case_) {
		this.getImpl().addCase(case_);
	}

	public Block getBlock() {
		return this.getImpl().getBlock();
	}

	public void setBlock(Block block) {
		this.getImpl().setBlock(block);
	}

	/**
	 * The assignments before any case expression of a switch clause are the
	 * same as the assignments before the clause. The assignments before the
	 * block of a switch clause are the assignments after all case expressions.
	 **/
	public boolean switchClauseAssignmentsBefore() {
		return this.getImpl().switchClauseAssignmentsBefore();
	}

	/**
	 * If a name is unassigned before a switch clause, then it must be
	 * unassigned after all case expressions of the clause (i.e., new local
	 * names may not be defined in case expressions).
	 **/
	public boolean switchClauseCaseLocalNames() {
		return this.getImpl().switchClauseCaseLocalNames();
	}

	/**
	 * All the case expressions of a switch clause must have a multiplicity no
	 * greater than 1.
	 **/
	public boolean switchClauseCases() {
		return this.getImpl().switchClauseCases();
	}

	/**
	 * The assignments before a switch clause are the assignments before any
	 * case expression of the clause.
	 **/
	public Collection<AssignedSource> assignmentsBefore() {
		return this.getImpl().assignmentsBefore();
	}

	/**
	 * The assignments after a switch clause are the assignments after the block
	 * of the switch clause.
	 **/
	public Collection<AssignedSource> assignmentsAfter() {
		return this.getImpl().assignmentsAfter();
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getCase());
        addExternalReferencesFor(references, this.getBlock());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		Collection<Expression> case_ = this.getCase();
		if (case_ != null) {
			for (Object _case_ : case_.toArray()) {
				((Expression) _case_).deriveAll();
			}
		}
		Block block = this.getBlock();
		if (block != null) {
			block.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.switchClauseAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"switchClauseAssignmentsBefore", this));
		}
		if (!this.switchClauseCaseLocalNames()) {
			violations.add(new ConstraintViolation(
					"switchClauseCaseLocalNames", this));
		}
		if (!this.switchClauseCases()) {
			violations.add(new ConstraintViolation("switchClauseCases", this));
		}
		Collection<Expression> case_ = this.getCase();
		if (case_ != null) {
			for (Object _case_ : case_.toArray()) {
				((Expression) _case_).checkConstraints(violations);
			}
		}
		Block block = this.getBlock();
		if (block != null) {
			block.checkConstraints(violations);
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
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
		Collection<Expression> case_ = this.getCase();
		if (case_ != null && case_.size() > 0) {
			System.out.println(prefix + " case:");
			for (Object _object : case_.toArray()) {
				Expression _case_ = (Expression) _object;
				if (_case_ != null) {
					_case_.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		Block block = this.getBlock();
		if (block != null) {
			System.out.println(prefix + " block:");
			block.print(prefix + "  ", includeDerived);
		}
	}
} // SwitchClause
