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

import java.util.LinkedList;
import java.util.List;

/**
 * An instance of this class is a repetition-rule.
 *
 * @author Michael Mackenzie High
 */
final class RuleRepetition extends Rule
{
    /**
     * This is the rule that will be repeated.
     */
    public Rule item;

    /**
     * This is the inclusive minimum number of times that the item must repeat.
     */
    public int minimum;

    /**
     * This is the inclusive maximum number of times that the item can repeat.
     */
    public int maximum;

    /**
     * Sole Constructor.
     *
     * @param name is the name of the rule.
     */
    public RuleRepetition(final String name) { super(name, true); }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public TreeNode match(final State state)
    {
        final int start_position = state.begin(this);
  
        final List<TreeNode> repetitions = new LinkedList<TreeNode>();
        
        TreeNode node = null;       
     
        for(int i = 0; i< minimum; i++)
        {
            node = item.match(state);
            
            if(node == null)
            {
                state.fail(this, start_position); return null;
            }            
            
            repetitions.add(node);
        }

        int i = minimum;
        while(i < maximum && state.remaining() != 0 && (node=item.match(state)) != null)
        {
            repetitions.add(node);
            
            ++i;
        }

        TreeNode[] children = new TreeNode[repetitions.size()];
        
        children = repetitions.<TreeNode>toArray(children);
        
        final TreeNode retval = new TreeNode(); 
        
        if(children.length > 0)
        {
            retval.initialize(this, state.input, children);
        }
        else
        {
            retval.initialize(this, state.input, start_position, 0);
        }
        
        state.succeed(this);

        return retval;
    }
}
