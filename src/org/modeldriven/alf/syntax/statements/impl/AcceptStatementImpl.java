
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A statement used to accept the receipt of instances of one or more signals.
 **/

public class AcceptStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.StatementImpl {

	public AcceptStatementImpl(AcceptStatement self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.statements.AcceptStatement getSelf() {
		return (AcceptStatement) this.self;
	}

	public ElementReference deriveBehavior() {
		return null; // STUB
	}

	public Boolean deriveIsSimple() {
		return null; // STUB
	}

	/**
	 * An accept statement can only be used within the definition of an active
	 * behavior or the classifier behavior of an active class.
	 **/
	public boolean acceptStatementContext() {
		return true;
	}

	/**
	 * The containing behavior of an accept statement must have receptions for
	 * all signals from all accept blocks of the accept statement. No signal may
	 * be referenced in more than one accept block of an accept statement.
	 **/
	public boolean acceptStatementSignals() {
		return true;
	}

	/**
	 * Any name defined in an accept block of an accept statement must be
	 * unassigned before the accept statement.
	 **/
	public boolean acceptStatementNames() {
		return true;
	}

	/**
	 * A local name specified in the accept block of a simple accept statement
	 * has the accept statement as its assigned source after the accept
	 * statement. The type of the local name is the effective common ancestor of
	 * the specified signals, if one exists, and it is untyped otherwise.
	 **/
	public boolean acceptStatementSimpleAcceptLocalName() {
		return true;
	}

	/**
	 * For a compound accept statement, a local name defined in an accept block
	 * has the accept block as its assigned source before the block associated
	 * with the accept block. The type of the local name is the effective common
	 * ancestor of the specified signals for that accept clause, if one exists,
	 * and it is untyped otherwise. However, the local name is considered
	 * unassigned after the accept statement.
	 **/
	public boolean acceptStatementCompoundAcceptLocalName() {
		return true;
	}

	/**
	 * The assignments before any block of an accept statement are the
	 * assignments before the accept statement.
	 **/
	public boolean acceptStatementAssignmentsBefore() {
		return true;
	}

	/**
	 * If a name is assigned in any block of an accept statement, then the
	 * assigned source of the name after the accept statement is the accept
	 * statement itself.
	 **/
	public boolean acceptStatementAssignmentsAfter() {
		return true;
	}

	/**
	 * If a name is unassigned before an accept statement and assigned in any
	 * block of an accept statement, then it must be assigned in every block.
	 **/
	public boolean acceptStatementNewAssignments() {
		return true;
	}

	/**
	 * An accept statement is simple if it has exactly one accept block and that
	 * accept block does not have a block.
	 **/
	public boolean acceptStatementIsSimpleDerivation() {
		this.getSelf().getIsSimple();
		return true;
	}

	/**
	 * The enclosing statement for all statements in the blocks of all accept
	 * blocks of an accept statement is the accept statement.
	 **/
	public boolean acceptStatementEnclosedStatements() {
		return true;
	}

} // AcceptStatementImpl
