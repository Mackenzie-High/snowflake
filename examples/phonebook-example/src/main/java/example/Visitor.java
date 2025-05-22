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

/**
 * An instance of this class transverses a parse-tree and prints the names and numbers therein.
 *
 * @author Mackenzie High
 */
final class Visitor
        extends AbstractVisitor
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void visitUnknown(ITreeNode node)
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
    protected void visit_name(ITreeNode node)
    {
        final String name = node.text();

        System.out.println("Name: " + name.trim());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void visit_phone_number(ITreeNode node)
    {
        final String number = node.text();

        System.out.println("Number: " + number.trim());
        System.out.println();
    }
}
