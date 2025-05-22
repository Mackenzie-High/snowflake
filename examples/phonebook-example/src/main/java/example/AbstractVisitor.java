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
 * <p>Generated On: Mon Jan 27 16:42:45 EST 2014</p>
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

        if ("book".equals(name))
        {
            visit_book(node);
        }
        else if ("entries".equals(name))
        {
            visit_entries(node);
        }
        else if ("entry".equals(name))
        {
            visit_entry(node);
        }
        else if ("name".equals(name))
        {
            visit_name(node);
        }
        else if ("full_name".equals(name))
        {
            visit_full_name(node);
        }
        else if ("partial_name".equals(name))
        {
            visit_partial_name(node);
        }
        else if ("first_name".equals(name))
        {
            visit_first_name(node);
        }
        else if ("middle_name".equals(name))
        {
            visit_middle_name(node);
        }
        else if ("last_name".equals(name))
        {
            visit_last_name(node);
        }
        else if ("phone_number".equals(name))
        {
            visit_phone_number(node);
        }
        else if ("area_code".equals(name))
        {
            visit_area_code(node);
        }
        else if ("prefix".equals(name))
        {
            visit_prefix(node);
        }
        else if ("line".equals(name))
        {
            visit_line(node);
        }
        else if ("DASH".equals(name))
        {
            visit_DASH(node);
        }
        else if ("DIGIT".equals(name))
        {
            visit_DIGIT(node);
        }
        else if ("EQ".equals(name))
        {
            visit_EQ(node);
        }
        else if ("LETTERS".equals(name))
        {
            visit_LETTERS(node);
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
     * This method visits a parse-tree node created by rule "DASH".
     */
    protected void visit_DASH(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "DIGIT".
     */
    protected void visit_DIGIT(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "EQ".
     */
    protected void visit_EQ(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "LETTERS".
     */
    protected void visit_LETTERS(ITreeNode node)
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
     * This method visits a parse-tree node created by rule "area_code".
     */
    protected void visit_area_code(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "book".
     */
    protected void visit_book(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "entries".
     */
    protected void visit_entries(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "entry".
     */
    protected void visit_entry(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "first_name".
     */
    protected void visit_first_name(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "full_name".
     */
    protected void visit_full_name(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "last_name".
     */
    protected void visit_last_name(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "line".
     */
    protected void visit_line(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "middle_name".
     */
    protected void visit_middle_name(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "name".
     */
    protected void visit_name(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "partial_name".
     */
    protected void visit_partial_name(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "phone_number".
     */
    protected void visit_phone_number(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }

    /**
     * This method visits a parse-tree node created by rule "prefix".
     */
    protected void visit_prefix(ITreeNode node)
    {
        // You should *not* place your code right here.
        // Instead, you should override this method via a subclass.
        visitUnknown(node); // Default Behavior
    }
}
