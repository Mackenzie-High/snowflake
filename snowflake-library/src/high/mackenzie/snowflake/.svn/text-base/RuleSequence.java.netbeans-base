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

package high.mackenzie.snowflake;

/**
 * An instance of this class represents a sequence-rule.
 *
 * @author Michael Mackenzie High  
 */
final class RuleSequence extends Rule
{
    /**
     * These are the elements that make up the sequence.
     */
    public Rule[] elements;

    /**
     * This constructor creates a new empty sequence-rule object.
     *
     * @param name is the name of this rule.
     * @throws NullPointerException if <code>name</code> is null.
     */
    public RuleSequence(String name) { super(name, true); }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreeNode match(final State state)
    {
        final int start_position = state.begin(this);

        final TreeNode[] children = new TreeNode[elements.length];
        
        for(int i = 0; i < elements.length; i++)
        {
            children[i] = elements[i].match(state);
            
            if(children[i] == null) { state.fail(this, start_position); return null; }            
        }
        
        final TreeNode retval = new TreeNode();
        
        retval.initialize(this, state.input, children);
        
        state.succeed(this);

        return retval;
    }

}
