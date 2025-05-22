/*
 * Copyright 2014 Michael Mackenzie High
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example;

import com.mackenziehigh.snowflake.ITreeNode;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

/**
 * An instance of this class is a parse-tree transverser that implements an infix calculator.
 *
 * @author Mackenzie High
 */
final class Visitor
        extends AbstractVisitor
{
    private final Map<String, Double> variables = new TreeMap<>();

    private final Stack<Double> operands = new Stack<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitUnknown(final ITreeNode node)
    {
        for (ITreeNode x : node.children())
        {
            visit(x);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void visit_input(final ITreeNode node)
    {
        // Visit the child nodes of the current parse-tree node.
        visitUnknown(node);

        final Double value = operands.pop();

        System.out.println(value);

        assert operands.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void visit_negate(ITreeNode node)
    {
        // Visit the child nodes of the current parse-tree node.
        visitUnknown(node);

        final Double operand = operands.pop();

        final Double result = -operand;

        operands.push(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void visit_divide(ITreeNode node)
    {
        // Visit the child nodes of the current parse-tree node.
        visitUnknown(node);

        final Double right_operand = operands.pop();

        final Double left_operand = operands.pop();

        final Double result = left_operand / right_operand;

        operands.push(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void visit_multiply(ITreeNode node)
    {
        // Visit the child nodes of the current parse-tree node.
        visitUnknown(node);

        final Double right_operand = operands.pop();

        final Double left_operand = operands.pop();

        final Double result = left_operand * right_operand;

        operands.push(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void visit_add(ITreeNode node)
    {
        // Visit the child nodes of the current parse-tree node.
        visitUnknown(node);

        final Double right_operand = operands.pop();

        final Double left_operand = operands.pop();

        final Double result = left_operand + right_operand;

        operands.push(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void visit_subtract(ITreeNode node)
    {
        // Visit the child nodes of the current parse-tree node.
        visitUnknown(node);

        final Double right_operand = operands.pop();

        final Double left_operand = operands.pop();

        final Double result = left_operand - right_operand;

        operands.push(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void visit_number(ITreeNode node)
    {
        final String text = node.text().trim();

        final Double result = Double.parseDouble(text);

        operands.push(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void visit_variable_datum(ITreeNode node)
    {
        final String variable = node.childAt(0).text().toUpperCase();

        final Double result = variables.containsKey(variable) ? variables.get(variable) : 0.0;

        operands.push(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void visit_assignment(ITreeNode node)
    {
        // Visit the child nodes of the current parse-tree node.
        visitUnknown(node);

        final String variable = node.childAt(0).text().toUpperCase();

        final Double value = operands.peek();

        variables.put(variable, value);
    }
}
