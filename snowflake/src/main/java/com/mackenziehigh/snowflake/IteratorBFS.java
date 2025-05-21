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
 * An instance of this class is a lazy breadth-first-search iterator. 
 * 
 * @author Mackenzie High
 */
final class IteratorBFS implements Iterator<ITreeNode>
{
    final Queue<ITreeNode> queue = new LinkedList<ITreeNode>();
    
    /**
     * Sole Constructor. 
     * 
     * @param node is the root node of the tree to iterate over. 
     */
    public IteratorBFS(ITreeNode node)
    {
        Utils.checkNonNull(node);
        
        queue.add(node);
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean hasNext() { return !queue.isEmpty(); }

    /**
     * {@inheritDoc}
     */
    public ITreeNode next() 
    {
        final ITreeNode current = queue.remove();
        
        if(current.childCount() >= 0) { for(ITreeNode n : current.children()) { queue.add(n); } }
        
        return current;
    }
    
    /**
     * {@inheritDoc}
     */
    public void remove() { throw new UnsupportedOperationException(); }
    
}
