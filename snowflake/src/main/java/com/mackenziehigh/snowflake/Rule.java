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
 * An instance of this class is a grammar-rule in a Parsing Expression Grammar.
 * 
 * @author Michael Mackenzie High 
 */
abstract class Rule
{
    /**
     * This is the the name of this rule.
     */
    public final String name;

    /**
     * This field is true, if this rule causes the parser to advance forward on success.
     * 
     * <p>
     * For example, predicate rules (i.e. "and" and "not" rules) match without
     * causing the parser's position to advance; therefore, this field would
     * be false for them. On the other hand, rules such as string-rules and
     * and sequence-rules can cause the position of the parser to advance;
     * therefore, this field would be true for them. 
     * </p>
     */
    public final boolean advances;
    
    /**
     * Sole Constructor.
     *
     * @param name is the name of the new rule.
     * @param advances is true, if the parser's position advances upon a successful match.
     */
    public Rule(final String name, 
                final boolean advances)
    {
        Utils.checkNonNull(name);
        
        this.name = name;
        
        this.advances = advances;
    }    
      
    /**
     * This method matches this rule against the parser's input.
     * 
     * <p>
     * When implemented in a subclass, this method determines whether the input, 
     * starting at the current position, matches this rule. If this rule does match, 
     * then this method returns a new tree-node for use in a syntax-tree. 
     * If this rule does not match, then this method ensures that its execution 
     * has not caused the state of the parser to adversely change. In other words, 
     * if this rule does not match, the state will be the same after this method executes 
     * as it was before this method executed. This does not mean that the state will not 
     * change during the execution of this method, rather it will simply return
     * to its original state, if the rule does not match. 
     * </p>
     * 
     * @param state is the mutable state of the parser.
     * @return a new tree-node, if this rule matches; otherwise, return null.
     */
    public abstract TreeNode match(State state);    

    /**
     * This method returns the name of this rule.
     * 
     * @return the name of this rule.
     */
    public final String name() { return name; }
    
    /**
     * This method returns a string representation of this rule.
     * 
     * @return the string representation of this rule, which is simply its name.
     */
    @Override
    public final String toString() { return name; }
}