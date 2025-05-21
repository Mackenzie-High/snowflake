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
 * An instance of this class is an end-rule.
 *
 * @author Michael Mackenzie High
 */
final class RuleEnd extends Rule
{
    /**
     * Sole Constructor. 
     */
    public RuleEnd() { super("END", false); }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreeNode match(final State state)
    {
        final int start_position = state.begin(this);

        if(state.remaining() != 0) { state.fail(this, start_position); return null; }

        final TreeNode retval = new TreeNode();
        
        retval.initialize(this, state.input, state.position, 0);
        
        state.succeed(this);

        state.position = start_position;
        
        return retval;
    }

}