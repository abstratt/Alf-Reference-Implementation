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
import java.util.Collection;
import java.util.List;
import org.modeldriven.alf.syntax.statements.impl.BlockImpl;

/**
 * A grouped sequence of statements.
 **/

public class Block extends SyntaxElement {

	public Block() {
		this.impl = new BlockImpl(this);
	}

	public Block(Parser parser) {
		this();
		this.init(parser);
	}

	public Block(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public BlockImpl getImpl() {
		return (BlockImpl) this.impl;
	}

	public List<Statement> getStatement() {
		return this.getImpl().getStatement();
	}

	public void setStatement(List<Statement> statement) {
		this.getImpl().setStatement(statement);
	}

	public void addStatement(Statement statement) {
		this.getImpl().addStatement(statement);
	}

	public Collection<AssignedSource> getAssignmentAfter() {
		return this.getImpl().getAssignmentAfter();
	}

	public void setAssignmentAfter(Collection<AssignedSource> assignmentAfter) {
		this.getImpl().setAssignmentAfter(assignmentAfter);
	}

	public void addAssignmentAfter(AssignedSource assignmentAfter) {
		this.getImpl().addAssignmentAfter(assignmentAfter);
	}

	public Collection<AssignedSource> getAssignmentBefore() {
		return this.getImpl().getAssignmentBefore();
	}

	public void setAssignmentBefore(Collection<AssignedSource> assignmentBefore) {
		this.getImpl().setAssignmentBefore(assignmentBefore);
	}

	public void addAssignmentBefore(AssignedSource assignmentBefore) {
		this.getImpl().addAssignmentBefore(assignmentBefore);
	}

	/**
	 * The assignments before each statement in a block other than the first are
	 * the same as the assignments after the previous statement.
	 **/
	public boolean blockAssignmentsBeforeStatements() {
		return this.getImpl().blockAssignmentsBeforeStatements();
	}

	/**
	 * The assignments before the first statement of a block are the same as the
	 * assignments before the block.
	 **/
	public boolean blockAssignmentsBefore() {
		return this.getImpl().blockAssignmentsBefore();
	}

	/**
	 * If a block is not empty, then the assignments after the block are the
	 * same as the assignments after the last statement of the block. Otherwise
	 * they are the same as the assignments before the block.
	 **/
	public boolean blockAssignmentAfterDerivation() {
		return this.getImpl().blockAssignmentAfterDerivation();
	}

    /**
     * A block has a return value if any of its statements has a return value.
     */
    public boolean hasReturnValue() {
        return this.getImpl().hasReturnValue();
    }

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getStatement());
    }

	@Override
    public void _deriveAll() {
		this.getAssignmentAfter();
		this.getAssignmentBefore();
		super._deriveAll();
		Collection<Statement> statement = this.getStatement();
		if (statement != null) {
			for (Object _statement : statement.toArray()) {
				((Statement) _statement).deriveAll();
			}
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.blockAssignmentsBeforeStatements()) {
			violations.add(new ConstraintViolation(
					"blockAssignmentsBeforeStatements", this));
		}
		if (!this.blockAssignmentsBefore()) {
			violations.add(new ConstraintViolation("blockAssignmentsBefore",
					this));
		}
		if (!this.blockAssignmentAfterDerivation()) {
			violations.add(new ConstraintViolation(
					"blockAssignmentAfterDerivation", this));
		}
		Collection<Statement> statement = this.getStatement();
		if (statement != null) {
			for (Object _statement : statement.toArray()) {
				((Statement) _statement).checkConstraints(violations);
			}
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
		List<Statement> statement = this.getStatement();
		if (statement != null && statement.size() > 0) {
			System.out.println(prefix + " statement:");
			for (Object _object : statement.toArray()) {
				Statement _statement = (Statement) _object;
				if (_statement != null) {
					_statement.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		if (includeDerived) {
			Collection<AssignedSource> assignmentAfter = this
					.getAssignmentAfter();
			if (assignmentAfter != null && assignmentAfter.size() > 0) {
				System.out.println(prefix + " /assignmentAfter:");
				for (Object _object : assignmentAfter.toArray()) {
					AssignedSource _assignmentAfter = (AssignedSource) _object;
					System.out.println(prefix + "  "
							+ _assignmentAfter.toString(includeDerived));
				}
			}
		}
		if (includeDerived) {
			Collection<AssignedSource> assignmentBefore = this
					.getAssignmentBefore();
			if (assignmentBefore != null && assignmentBefore.size() > 0) {
				System.out.println(prefix + " /assignmentBefore:");
				for (Object _object : assignmentBefore.toArray()) {
					AssignedSource _assignmentBefore = (AssignedSource) _object;
					System.out.println(prefix + "  "
							+ _assignmentBefore.toString(includeDerived));
				}
			}
		}
	}
} // Block
