/*******************************************************************************
 * Copyright 2011-2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.SyntaxElementMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.InvocationExpression;
import org.modeldriven.alf.syntax.expressions.LeftHandSide;
import org.modeldriven.alf.syntax.expressions.LinkOperationExpression;
import org.modeldriven.alf.syntax.expressions.NamedExpression;
import org.modeldriven.alf.syntax.expressions.OutputNamedExpression;
import org.modeldriven.alf.syntax.expressions.Tuple;
import org.modeldriven.alf.syntax.expressions.UnboundedLiteralExpression;
import org.modeldriven.alf.syntax.units.RootNamespace;

import org.modeldriven.alf.uml.*;

public abstract class TupleMapping extends SyntaxElementMapping {

    private StructuredActivityNode node = null;
    private ActivityGraph tupleGraph = this.createActivityGraph();
    private ActivityGraph lhsGraph = this.createActivityGraph();
    private Map<String, ExpressionMapping> inoutExpressionMap =
        new HashMap<String, ExpressionMapping>();
    private Map<String, ActivityNode> assignedValueSourceMap = 
        new HashMap<String, ActivityNode>();
    private boolean isIndexFrom0 = false;

    /**
     * 1. An empty tuple (i.e., a positional tuple with no argument expressions)
     * is mapped to nothing. A nonempty tuple is mapped to a structured activity
     * node containing the mapping of each of its argument expressions. There is
     * a control flow from the structured activity node to the invocation action
     * taking input from the tuple mapping.
     */
    // TODO: Update tuple mapping in the spec.
    /*
     * Actually, output arguments should not be mapped inside the structured
     * activity node.
     */
    /**
     * 
     * 2. For an argument for an in parameter, the argument expression is mapped
     * as usual for an expression. The result source element of such an
     * expression provides the source for setting the value of the associated
     * parameter, unless conversion is required. If collection conversion is
     * required, then the result source element of the argument expression is
     * connect by an object flow to an invocation of the Collection::toSequence
     * operation, and the result of that invocation provides the source for
     * setting the value of the associated parameter, unless bit string
     * conversion is also require. If bit string conversion is required, then
     * either the result source element of the argument expression or the result
     * of the toSequence invocation, if collection conversion was required, is
     * connected by an object flow to an invocation of the
     * BitStringFunctions::toBitString function, and the result of that
     * invocation provides the source for setting the value of the associated
     * parameter. If the tuple is in a behavior invocation expression or
     * sequence operation expression to which indexing from 0 applies, and the
     * parameter is an affected index parameter, then the mapping of the
     * argument expression is adjusted as for the index expression of a sequence
     * access expression.
     * 
     * 3. For an argument for an out parameter, the argument expression is
     * mapped as a left hand side of an assignment: an argument that is a local
     * name is mapped as a fork node while an argument that is a feature
     * reference is mapped as write structural feature value action. The output
     * from the invocation action for the corresponding parameter provides the
     * assigned value.
     * 
     * 4. For an argument for an inout parameter, the argument expression is
     * mapped twice (as given above): once as for an in parameter, to provide
     * the input value for the parameter, and once as for an out parameter, to
     * provide the target for the output value.
     */
    
    public void setIsIndexFrom0(boolean isIndexFrom0) {
        this.isIndexFrom0 = isIndexFrom0;
    }

    public void mapTo(Action action) throws MappingError {
        Tuple tuple = this.getTuple();
        // System.out.println("[mapTo] tuple=" + tuple);
        InvocationExpression invocation = tuple.getInvocation();
        Collection<NamedExpression> inputs = 
                // NOTE: The action will only be ValueSpecification if the invocation is
                // of the library function "clear", which takes an inout parameter, but
                // ignores its input, simply setting the output target to null.
                action instanceof ValueSpecificationAction? Collections.emptyList():
                tuple.getInput();
        Collection<OutputNamedExpression> outputs = tuple.getOutput();
        
        if (!inputs.isEmpty()) {

            // Note: Tuple input and output expressions are presumed to be 
            // in the same order as their corresponding parameters, as 
            // implemented in TupleImpl.
            
            ActivityGraph subgraph = this.createActivityGraph();
            List<InputPin> inputPins = action instanceof InvocationAction? 
                    ((InvocationAction)action).getArgument(): 
                    action.getInput();
            int i = 0;
            for (NamedExpression input: inputs) {
                String name = input.getName();
                Expression expression = input.getExpression();
                FumlMapping mapping = this.fumlMap(expression);
                if (!(mapping instanceof ExpressionMapping)) {
                    this.throwError("Error mapping input expression for " + name, mapping);
                } else {
                    ExpressionMapping expressionMapping = 
                        (ExpressionMapping)mapping;
                    expressionMapping.setIsIndexFrom0(this.isIndexFrom0);
                    ElementReference parameter = 
                        invocation.getImpl().parameterNamed(name);
                    if (parameter != null && 
                            "inout".equals(parameter.getImpl().getDirection())) {
                        this.inoutExpressionMap.put(name, expressionMapping);
                    }

                    subgraph.addAll(expressionMapping.getGraph());
                    ActivityNode resultSource = 
                        expressionMapping.getResultSource();
                    if (resultSource == null) {
                        this.setErrorMessage("No result source for input expression for " 
                                + name + ": " + mapping);
                    } else {
                        
                        // Add conversions, if required.
                        resultSource = AssignmentExpressionMapping.mapConversions(
                                this, subgraph, 
                                resultSource, expression.getType(), 
                                input.getImpl().getIsCollectionConversion(parameter), 
                                input.getImpl().getIsBitStringConversion(parameter),
                                input.getImpl().getIsRealConversion(parameter),
                                input.getImpl().isUnlimitedNaturalConversion(parameter));
                        
                        // Adjust for indexing from 0, if necessary.
                        if (this.isIndexFrom0 && "index".equals(name) && 
                                invocation.getImpl().isIndexingInvocation()) {
                            resultSource = mapIncrement(subgraph, resultSource);
                        }
                                               
                        InputPin inputPin = inputPins.get(i);
                        this.tupleGraph.addObjectFlow(resultSource, inputPin);
                        
                        // Check for an index on the argument name (as opposed
                        // to the argument expression itself).
                        Expression index = input.getIndex();
                        if (index == null && 
                                tuple.getInvocation() instanceof LinkOperationExpression &&
                                inputPin.getIsOrdered()) {
                            index = new UnboundedLiteralExpression();
                        }
                        if (index != null) {
                            mapping = this.fumlMap(index);
                            if (!(mapping instanceof ExpressionMapping)) {
                                this.throwError("Error mapping index expression for " 
                                        + name, mapping);
                            } else {
                                ((ExpressionMapping)mapping).setIsIndexFrom0(this.isIndexFrom0);
                                subgraph.addAll(
                                        ((ExpressionMapping)mapping).getGraph());
                                resultSource =
                                    ((ExpressionMapping)mapping).getResultSource();
                                
                                if (resultSource == null) {
                                    this.throwError("No result Source: " + mapping);
                                } else {
                                    
                                    if (index.getType().getImpl().conformsTo(
                                            RootNamespace.getRootScope().getIntegerType())) {
                                        
                                        // Adjust for indexing from 0, if necessary.
                                        if (this.isIndexFrom0) {
                                            resultSource = mapIncrement(subgraph, resultSource);
                                        }
                                        
                                        resultSource = AssignmentExpressionMapping.mapConversion(
                                                subgraph, resultSource, 
                                                RootNamespace.getRootScope().
                                                    getIntegerFunctionToUnlimitedNatural());
                                        
                                    } else if (this.isIndexFrom0) {
                                        // NOTE: Incoming index value is UnlimitedNatural here, so it
                                        // must be converted to Integer to be incremented, and then
                                        // back to UnlimitedNatural.
                                        
                                        // Test if value is "*".
                                        ForkNode fork = subgraph.addForkNode(
                                                "Fork(" + resultSource.getName() + ")");
                                        subgraph.addObjectFlow(resultSource, fork);
                                        ValueSpecificationAction valueAction = 
                                                subgraph.addUnlimitedNaturalValueSpecificationAction(-1);
                                        TestIdentityAction testAction =
                                                subgraph.addTestIdentityAction("Test(" + resultSource.getName() + "=*)");
                                        subgraph.addObjectFlow(fork, testAction.getFirst());
                                        subgraph.addObjectFlow(valueAction.getResult(), testAction.getSecond());
                                        
                                        // Adjust for indexing from 0 (if value is not *).
                                        CallBehaviorAction callAction = subgraph.addCallBehaviorAction(
                                                getBehavior(RootNamespace.getRootScope().
                                                        getUnlimitedNaturalFunctionToInteger()));                                        
                                        ActivityNode incrementedResult = mapIncrement(
                                                subgraph, callAction.getResult().get(0));                                        
                                        incrementedResult = AssignmentExpressionMapping.mapConversion(
                                                subgraph, incrementedResult, 
                                                RootNamespace.getRootScope().
                                                    getIntegerFunctionToUnlimitedNatural());
                                        
                                        resultSource = subgraph.addMergeNode(
                                                "Merge(" + resultSource.getName() + ")");
                                        subgraph.addObjectDecisionNode(
                                                "Decision(" + testAction.getName() + ")", 
                                                fork, testAction.getResult(), 
                                                resultSource, callAction.getArgument().get(0));
                                        subgraph.addObjectFlow(incrementedResult, resultSource);                                        
                                    }
                                    
                                    // NOTE: This presumes that the "insertAt"
                                    // or "destroyAt" pin comes directly after
                                    // the value pin in the list of input pins.
                                    this.tupleGraph.addObjectFlow(
                                            resultSource, inputPins.get(++i));
                                }
                            }
                        }
                    }
                }
                i++;
            }
            
            if (!subgraph.isEmpty()) {
                this.node = this.tupleGraph.addStructuredActivityNode(
                        "Tuple@" + tuple.getId(), 
                        subgraph.getModelElements());
                this.tupleGraph.addControlFlow(this.node, action);
            }
        }

        int i = 0;
        OutputPin returnPin = ActivityGraph.getReturnPin(action);
        for (OutputNamedExpression output: outputs) {
            String name = output.getName();
            Expression expression = output.getExpression();
            if (!expression.getImpl().isNull()) {
                LeftHandSide lhs = output.getLeftHandSide();
                FumlMapping mapping = this.fumlMap(lhs);
                if (!(mapping instanceof LeftHandSideMapping)) {
                    this.throwError("Error mapping output for " + 
                            name + " as left hand side", mapping);
                } else {
                    LeftHandSideMapping lhsMapping = 
                        (LeftHandSideMapping)mapping;
                    lhsMapping.setIsIndexFrom0(this.isIndexFrom0);
                    ExpressionMapping inputMapping = 
                        this.inoutExpressionMap.get(name);
                    if (inputMapping != null) {
                        lhsMapping.setIndexSource(inputMapping.getIndexSource());
                        lhsMapping.setObjectSource(inputMapping.getObjectSource());
                    }
                    this.lhsGraph.addAll(lhsMapping.getGraph());
                                        
                    ActivityNode outputPin = action.getOutput().get(i);

                    // Unless this is the mapping of an in-place collection function invocation,
                    // skip a return pin. The return value is not normally given through a tuple
                    // argument.
                    if (outputPin.equals(returnPin) && 
                            !invocation.getReferent().getImpl().isCollectionFunction()) {
                        outputPin = action.getOutput().get(++i);
                    }
                    
                    ElementReference parameter = 
                        invocation.getImpl().parameterNamed(name);
                    
                    // Add conversions, if required.
                    outputPin = AssignmentExpressionMapping.mapConversions(
                            this, this.tupleGraph, 
                            outputPin, parameter.getImpl().getType(), 
                            output.getImpl().getIsCollectionConversion(parameter), 
                            output.getImpl().getIsBitStringConversion(parameter),
                            output.getImpl().getIsRealConversion(parameter),
                            output.getImpl().isUnlimitedNaturalConversion(parameter));
                   
                    // NOTE: These activity edges are part of the tuple graph, 
                    // NOT the LHS graph.
                    this.tupleGraph.addObjectFlow(
                            outputPin, 
                            lhsMapping.getAssignmentTarget());
                    ActivityNode controlTarget = lhsMapping.getControlTarget();
                    if (controlTarget != null) {
                        this.tupleGraph.addControlFlow(action, controlTarget);
                    }

                    String assignedName = lhs.getImpl().getAssignedName();
                    if (assignedName != null) {
                        this.getAssignedValueSourceMap().put(
                                assignedName, 
                                lhsMapping.getAssignedValueSource());
                    }
                }
            }
            i++;
        }
    }
    
    public static OutputPin mapIncrement(ActivityGraph graph, ActivityNode resultSource) throws MappingError {
        ValueSpecificationAction valueAction = graph.addIntegerValueSpecificationAction(1);
        CallBehaviorAction addAction = graph.addCallBehaviorAction(
                getBehavior(RootNamespace.getRootScope().getIntegerFunctionPlus()));
        graph.addObjectFlow(resultSource, addAction.getArgument().get(0));
        graph.addObjectFlow(valueAction.getResult(), addAction.getArgument().get(1));
        return addAction.getResult().get(0);
    }
    
    public Map<String, ActivityNode> getAssignedValueSourceMap() {
        return this.assignedValueSourceMap;
    }
    
    @Override
    public ActivityNode getAssignedValueSource(String name) {
        return this.getAssignedValueSourceMap().get(name);
    }
    
    @Override
    public Element getElement() {
        return this.node;
    }

    public ActivityGraph getTupleGraph() {
        return this.tupleGraph;
    }
    
    public ActivityGraph getLhsGraph() {
        return this.lhsGraph;
    }
    
    @Override
    public Collection<Element> getModelElements() {
        Collection<Element> elements = 
            new ArrayList<Element>(this.getTupleGraph().getModelElements());
        elements.addAll(this.getLhsGraph().getModelElements());
        return elements;
    }
    
    public Tuple getTuple() {
        return (Tuple) this.getSource();
    }
    
    @Override
    public void print(String prefix) {
        super.print(prefix);
        Tuple source = this.getTuple();
        
        Collection<NamedExpression> inputs = source.getInput();
        if (!inputs.isEmpty()) {
            System.out.println(prefix + " input:");
            for (NamedExpression input: inputs) {
                System.out.println(prefix + "  name: " + input.getName());
                Mapping mapping = input.getExpression().getImpl().getMapping();
                if (mapping != null) {
                    mapping.printChild(prefix + " ");
                }
            }
        }
        
        Collection<OutputNamedExpression> outputs = source.getOutput();
        if (!outputs.isEmpty()) {
            System.out.println(prefix + " output:");
            for (OutputNamedExpression output: outputs) {
                System.out.println(prefix + "  name: " + output.getName());
                Mapping mapping = output.getLeftHandSide().getImpl().getMapping();
                if (mapping != null) {
                    mapping.printChild(prefix + " ");
                }
            }
        }
        
        System.out.println(prefix + " structured node: " + node);
    }

} // TupleMapping
