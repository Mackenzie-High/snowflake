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
import com.mackenziehigh.snowflake.ITreeNodeVisitor;

/**
 * This class was auto-generated using the Snowflake parser-generator.
 *
 * <p>Generated On: Mon Jan 27 19:08:36 EST 2014</p>
 */
abstract class AbstractVisitor
        implements ITreeNodeVisitor
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(ITreeNode node)
    {
        final String name = node.rule();

        if ("input".equals(name))
        {
            visit_input(node);
        }
        else if ("expression".equals(name))
        {
            visit_expression(node);
        }
        else if ("unary_operation".equals(name))
        {
            visit_unary_operation(node);
        }
        else if ("negate_operation".equals(name))
        {
            visit_negate_operation(node);
        }
        else if ("binary_operation".equals(name))
        {
            visit_binary_operation(node);
        }
        else if ("divide".equals(name))
        {
            visit_divide(node);
        }
        else if ("multiply".equals(name))
        {
            visit_multiply(node);
        }
        else if ("add".equals(name))
        {
            visit_add(node);
        }
        else if ("subtract".equals(name))
        {
            visit_subtract(node);
        }
        else if ("operand".equals(name))
        {
            visit_operand(node);
        }
        else if ("nested_expression".equals(name))
        {
            visit_nested_expression(node);
        }
        else if ("number".equals(name))
        {
            visit_number(node);
        }
        else if ("ADD".equals(name))
        {
            visit_ADD(node);
        }
        else if ("DIV".equals(name))
        {
            visit_DIV(node);
        }
        else if ("MUL".equals(name))
        {
            visit_MUL(node);
        }
        else if ("SUB".equals(name))
        {
            visit_SUB(node);
        }
        else if ("MINUS".equals(name))
        {
            visit_MINUS(node);
        }
        else if ("NUMBER".equals(name))
        {
            visit_NUMBER(node);
        }
        else if ("PAREN_L".equals(name))
        {
            visit_PAREN_L(node);
        }
        else if ("PAREN_R".equals(name))
        {
            visit_PAREN_R(node);
        }
        else if ("WS".equals(name))
        {
            visit_WS(node);
        }
        else
        {
            visitUnknown(node);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitUnknown(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
    }

    /**
     * This method visits a parse-tree node created by rule "ADD".
     */
    protected void visit_ADD(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "DIV".
     */
    protected void visit_DIV(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "MINUS".
     */
    protected void visit_MINUS(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "MUL".
     */
    protected void visit_MUL(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "NUMBER".
     */
    protected void visit_NUMBER(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "PAREN_L".
     */
    protected void visit_PAREN_L(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "PAREN_R".
     */
    protected void visit_PAREN_R(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "SUB".
     */
    protected void visit_SUB(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "WS".
     */
    protected void visit_WS(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "add".
     */
    protected void visit_add(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "binary_operation".
     */
    protected void visit_binary_operation(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "divide".
     */
    protected void visit_divide(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "expression".
     */
    protected void visit_expression(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "input".
     */
    protected void visit_input(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "multiply".
     */
    protected void visit_multiply(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "negate_operation".
     */
    protected void visit_negate_operation(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "nested_expression".
     */
    protected void visit_nested_expression(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "number".
     */
    protected void visit_number(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "operand".
     */
    protected void visit_operand(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "subtract".
     */
    protected void visit_subtract(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "unary_operation".
     */
    protected void visit_unary_operation(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }
}
