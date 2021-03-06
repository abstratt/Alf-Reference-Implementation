/*******************************************************************************
 *  Copyright 2019 Model Driven Solutions, Inc.
 *  All rights reserved worldwide. This program and the accompanying materials
 *  are made available for use under the terms of the GNU General Public License 
 *  (GPL) version 3 that accompanies this distribution and is available at 
 *  http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 *  contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.parser;

import org.modeldriven.alf.syntax.common.SourceProblem;

public class ParsingProblem extends SourceProblem {
    public ParsingProblem(String problemKey, ParsedElement violatingElement) {
        super(problemKey, violatingElement);
    }
}
