/*
 * Copyright 2013 Michael Mackenzie High
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

package com.mackenziehigh.snowflake.designer.gui;

import com.mackenziehigh.snowflake.ITreeNode;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.tree.TreeNode;

/**
 * This class facilitates the display of a parse-tree via a GUI.
 * 
 * @author Michael Mackenzie High
 */
public final class DisplayableTreeNode implements TreeNode 
{
    private final DisplayableTreeNode parent;
    
    private final List<DisplayableTreeNode> children = new LinkedList<DisplayableTreeNode>();
    
    private final String ruleName;
    
    private final String matchText;
    
    public DisplayableTreeNode(final DisplayableTreeNode parent, 
                               final ITreeNode node)
    {
        for(ITreeNode child : node.children())
        {
            this.children.add(new DisplayableTreeNode(this, child));
        }
        
        this.parent = parent;
        
        this.ruleName = node.rule();
        
        this.matchText = node.text();
    }
    
    public DisplayableTreeNode getChildAt(int childIndex)
    {
        return children.get(childIndex);
    }

    @Override
    public DisplayableTreeNode getParent()
    {
        return parent;
    }

    public int getIndex(TreeNode node)
    {
        int index = 0;

        for(Object child : children)
        {
            if(child.equals(node)) { return index; } else { ++index; }
        }

        return -1;
    }

    public boolean getAllowsChildren()
    {
        return true;
    }

    public boolean isLeaf()
    {
        return getChildCount() == 0;
    }

    public Enumeration children()
    {
        return new ChildrenEnumeration(children.iterator());
    }

    public int getChildCount()
    {
        return children.size();
    }   
    
    public String getRuleName()
    {
        return ruleName;
    }
    
    public String getMatchText()
    {
        return matchText;
    }
    
    @Override
    public String toString()
    {
        return ruleName;
    }

    private static class ChildrenEnumeration implements Enumeration
    {
        private final Iterator iter;

        public ChildrenEnumeration(Iterator i)
        {
            iter = i;
        }

        public boolean hasMoreElements()
        {
            return iter.hasNext();
        }

        public Object nextElement()
        {
            return iter.next();
        }
    }
}
