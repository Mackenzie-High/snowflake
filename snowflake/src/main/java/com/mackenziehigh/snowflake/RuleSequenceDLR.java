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

/**
 * An instance of this class is directly-left-recursive sequence-rule. 
 * 
 * @author Michael Mackenzie High
 */
final class RuleSequenceDLR extends Rule  
{

    /**
     * This is the first element in a match of this sequence rule, if the match is not recursive. 
     */
    public Rule base_case;
    
    /**
     * This rule contains the elements that are shared by both recursive and non-recursive matches. 
     */
    public final RuleSequence shared;
    
    /**
     * Sole Constructor.
     * 
     * @param name is the name of the new rule.
     */
    public RuleSequenceDLR(final String name)
    {
        super(name, true);
        
        shared = new RuleSequence(name + "(shared)");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public TreeNode match(final State state) 
    {
        final int start_position = state.begin(this);

        TreeNode match;
        
        TreeNode node;
        
        node = base_case.match(state);
        
        if(node == null) { state.fail(this, start_position); return null; }
        
        match = node; 
        node = null;
        
        //
        
        int recureCount = 0;
        
        while(true)
        {
            node = shared.match(state);
            
            if(node == null)
            {
                if(recureCount == 0) 
                { 
                    state.fail(this, start_position); 
                    return null; 
                } 
                else 
                { 
                    break; 
                }
            }
            else
            {
                match = reduce(match, node); 
                node = null;
            }
            
            ++recureCount;
        }
        
        state.succeed(this);

        return match;
    }
    
    /**
     * This method joins two nodes together in order to form a left-recursive node.
     * 
     * @param match is a match of this rule.
     * @param node is a match of the tail sequence.
     * @return a left-recursive match of this rule.
     */
    private TreeNode reduce(final TreeNode previousMatch, 
                            final TreeNode tailMatch)
    {
        final TreeNode[] children = new TreeNode[1 + tailMatch.childCount()];
        
        children[0] = previousMatch;
      
        final TreeNode[] tailKids = tailMatch.children();
        
        System.arraycopy(tailKids, 0, children, 1, tailKids.length);
        
        final char[] input = previousMatch.input();
        
        final TreeNode retval = new TreeNode();
        
        retval.initialize(this, input, children);
        
        return retval;
    }    
}
