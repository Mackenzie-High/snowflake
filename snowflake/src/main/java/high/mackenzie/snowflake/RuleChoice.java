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
 * An instance of this class is a choice-rule.
 *
 * @author Michael Mackenzie High
 */
final class RuleChoice extends Rule
{
    /**
     * This is the ordered list of options in this choice.
     */
    protected Rule[] options;

    /**
     * Sole Constructor.
     *
     * @param name is the name of this rule.
     */
    public RuleChoice(final String name) { super(name, true); }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreeNode match(final State state)
    {
        final int start_position = state.begin(this);

        TreeNode option = null;
        
        for(Rule r : options)
        {
            option = r.match(state);

            if(option != null) { break; }
        }

        if(option == null) { state.fail(this, start_position); return null; }

        final TreeNode retval = new TreeNode();

        retval.initialize(this, state.input, new TreeNode[] { option });

        state.succeed(this);

        return retval;
    }

}
