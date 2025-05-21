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

package com.mackenziehigh.snowflake;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * An instance of this class is a lazy depth-first-search iterator. 
 * 
 * @author Mackenzie High
 */
final class IteratorDFS implements Iterator<ITreeNode>
{
    final LinkedList<ITreeNode> stack = new LinkedList<ITreeNode>();
    
    /**
     * Sole Constructor. 
     * 
     * @param node is the root node of the tree to iterate over. 
     */    
    public IteratorDFS(ITreeNode node)
    {
        Utils.checkNonNull(node);
        
        stack.push(node);
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean hasNext() { return !stack.isEmpty(); }

    /**
     * {@inheritDoc}
     */
    public ITreeNode next() 
    {
        final ITreeNode current = stack.pop();
        
        if(current.childCount() >= 0) 
        {
            for(int i = current.childCount() - 1; i >= 0; i--) 
            { 
                stack.push(current.childAt(i)); 
            } 
        }
        
        return current;
    }
    
    /**
     * {@inheritDoc}
     */
    public void remove() { throw new UnsupportedOperationException(); }
    
}
