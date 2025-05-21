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
import java.util.List;

/**
 * An instance of this class is a node in a syntax-tree. 
 * 
 * @author Mackenzie High
 */
public abstract class AbstractTreeNode implements ITreeNode
{
    /**
     * {@inheritDoc}
     */ 
    @Override 
    public abstract ITreeNode[] children();

    /**
     * {@inheritDoc}
     */ 
    @Override
    public abstract String rule();

    /**
     * {@inheritDoc}
     */ 
    @Override
    public abstract int start();

    /**
     * {@inheritDoc}
     */ 
    @Override
    public abstract int length();

    /**
     * {@inheritDoc}
     */ 
    @Override
    public abstract char[] input();
    
    /**
     * {@inheritDoc}
     */ 
    @Override
    public String text()
    {
        final int start = start();
        
        final int length = length();
        
        final char[] input = input();
        
        final StringBuilder str = new StringBuilder();

        for(int i = 0; i < length; i++) { str.append(input[start + i]); }

        return str.toString();
    }

    /**
     * {@inheritDoc}
     */ 
    @Override
    public ITreeNode childAt(final int index) { return children()[index]; }

    /**
     * {@inheritDoc}
     */ 
    @Override
    public int childCount() { return children().length; }
    
    /**
     * {@inheritDoc}
     */ 
    @Override
    public Iterable<ITreeNode> iterableDFS()
    {
        final Iterator<ITreeNode> iterator = new IteratorDFS(this);
        
        return new Iterable<ITreeNode>() 
        { 
            public Iterator<ITreeNode> iterator() { return iterator; }
        };
    }

    /**
     * {@inheritDoc}
     */ 
    @Override
    public Iterable<ITreeNode> iterableBFS()
    {
        final Iterator<ITreeNode> iterator = new IteratorBFS(this);
        
        return new Iterable<ITreeNode>() 
        { 
            public Iterator<ITreeNode> iterator() { return iterator; }
        };
    }    
    
    /**
     * {@inheritDoc}
     */ 
    @Override
    public Iterable<ITreeNode> iterableLeavesFirst()
    {
        final Iterator<ITreeNode> iterator = new IteratorLeavesFirst(this);
        
        return new Iterable<ITreeNode>() 
        { 
            public Iterator<ITreeNode> iterator() { return iterator; }
        };
    } 
    
    /**
     * {@inheritDoc}
     */ 
    @Override
    public Iterator<ITreeNode> iterator()
    {
        return this.iterableBFS().iterator();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() { return text(); }  
    
    /**
     * This method searches an iterable for a particular tree-node.
     * 
     * @param iterable is the iterable to search.
     * @param name is the name of the rule that created the sought after tree-node. 
     * @return the first acceptable tree-node, or null, if no such tree-node is found. 
     */
    public static ITreeNode find(final Iterable<ITreeNode> iterable, 
                                 final String name)
    {
        Utils.checkNonNull(iterable);
        Utils.checkNonNull(name);
        
        for(ITreeNode node : iterable)
        {
            if(name.equals(node.rule())) { return node; }
        }
        
        return null;
    }
    
    /**
     * This method searches an iterable for a particular group of tree-nodes.
     * 
     * @param iterable is the iterable to search.
     * @param name is the name of the rule that created the sought after tree-nodes. 
     * @return the found tree-nodes in the order they were found, 
     *         or an empty list, if no such tree-nodes are found. 
     */
    public static List<ITreeNode> findAll(final Iterable<ITreeNode> iterable, 
                                          final String name)
    {
        Utils.checkNonNull(iterable);
        Utils.checkNonNull(name);

        final List<ITreeNode> result = new LinkedList<ITreeNode>();
        
        for(ITreeNode node : iterable)
        {
            if(name.equals(node.rule())) { result.add(node); }
        }
        
        return result;
    }
}
