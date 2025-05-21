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
import java.util.Queue;

/**
 * An instance of this class is an iterator that visits tree nodes in the order they were created.
 * 
 * @author Mackenzie High
 */
final class IteratorLeavesFirst implements Iterator<ITreeNode>
{
    // Note: In the future, the internals of this class should be changed to save runtime memory.
    //       However, the current implementation will work, for now.  
    
    final LinkedList<ITreeNode> linearization = new LinkedList<ITreeNode>();
    
    /**
     * Sole Constructor. 
     * 
     * @param node is the root node of the tree to iterate over. 
     */    
    public IteratorLeavesFirst(ITreeNode node)
    {
        Utils.checkNonNull(node);
        
        linearize(node);
    }
    
    private void linearize(final ITreeNode node)
    {
        for(ITreeNode child : node.children()) { linearize(child); }
        
        linearization.addLast(node);
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean hasNext() { return !linearization.isEmpty(); }

    /**
     * {@inheritDoc}
     */
    public ITreeNode next() 
    {
        return linearization.remove();
    }
    
    /**
     * {@inheritDoc}
     */
    public void remove() { throw new UnsupportedOperationException(); }
    
}
