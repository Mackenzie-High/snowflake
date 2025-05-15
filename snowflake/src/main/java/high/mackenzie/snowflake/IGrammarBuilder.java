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
 * An instance of this interface constructs a new Parsing Expression Grammar. 
 * 
 * <p>The namespace for rules is distinct from that of character-classes.</p>
 * 
 * <p>An end-rule is always available via the name "END".</p>
 * 
 * @author Mackenzie High
 */
public interface IGrammarBuilder 
{  
    
    /**
     * This method adds an and-rule to the grammar that is being built.
     * 
     * @param name is the name of the new rule. 
     * @param item the the rule that the new rule will attempt to match. 
     * @return this.
     */
    public IGrammarBuilder and(String name, String item);
    
    /**
     * This method adds a not-rule to the grammar that is being built. 
     * 
     * @param name is the name of the new rule. 
     * @param item is the rule that the new rule will attempt to match.
     * @return this.
     */
    public IGrammarBuilder not(String name, String item);
    
    /**
     * This method adds a new sequence-rule to the grammar that is being built. 
     * 
     * @param name is the name of the new rule. 
     * @param elements are the names of the rules in the sequence. 
     * @return this.
     */
    public IGrammarBuilder sequence(String name, String... elements);
    
    /**
     * This method adds a directly-left-recursive sequence-rule to the grammar that is being built.
     * 
     * @param name is the name of the new rule. 
     * @param base is the base-case of the recursive sequence. 
     * @param shared is the subsequence that is shared by all matches of the recursive sequence. 
     * @return this. 
     */
    public IGrammarBuilder sequenceDLR(String name, String base, String... shared);
    
    /**
     * This method adds an ordered choice-rule to the grammar that is being built. 
     * 
     * @param name is the name of the new rule. 
     * @param options are the names of the rules that are the options. 
     * @return this. 
     */
    public IGrammarBuilder choose(String name, String... options);
    
    /**
     * This method adds a new repetition-rule to the grammar that is being built. 
     * 
     * @param name is the name of the new rule. 
     * @param item is the name of the rule that will be repeated. 
     * @param minimum is the minimum number of times that the item must match. 
     * @param maximum is the maximum number of times that the item will be matched. 
     * @return this.
     * @throws IllegalArgumentException if <code>minimum &gt; maximum</code>.
     */
    public IGrammarBuilder repeat(String name, String item, int minimum, int maximum);
    
    /**
     * This method adds a "Zero or More" repetition-rule to the grammar that is being built.  
     * 
     * <p> Equivalence: <code>repeat(name, item, 0, Integer.MAX_VALUE)</code> </p>
     * 
     * @param name is the name of the new rule. 
     * @param item is the name of the rule that may be repeated. 
     * @return this. 
     */
    public IGrammarBuilder star(String name, String item);
    
    /**
     * This method adds a "One or More" repetition-rule to the grammar that is being built.  
     * 
     * <p> Equivalence: <code>repeat(name, item, 1, Integer.MAX_VALUE)</code> </p>
     * 
     * @param name is the name of the new rule. 
     * @param item is the name of the rule that may be repeated. 
     * @return this. 
     */
    public IGrammarBuilder plus(String name, String item);
    
    /**
     * This method adds a "Zero or One" repetition-rule to the grammar that is being built.  
     * 
     * <p> Equivalence: <code>repeat(name, item, 0, 1)</code> </p>
     * 
     * @param name is the name of the new rule. 
     * @param item is the name of the rule that is optional. 
     * @return this. 
     */
    public IGrammarBuilder option(String name, String item);
    
    /**
     * This method adds a string-rule to the grammar that is being built. 
     * 
     * @param name is the name of the new rule. 
     * @param string is the text that the string-rule will match. 
     * @return this.
     */
    public IGrammarBuilder str(String name, String string);
    
    /**
     * This method adds a character-rule to the grammar that is being built. 
     * 
     * @param name is the name of the new rule. 
     * @param character is the name of a character-class.
     * @return this.
     */
    public IGrammarBuilder chr(String name, String character);
    
    /**
     * This method adds a single-char character-class to the grammar that is being built.
     * 
     * @param name is the name of the new character-class.
     * @param character is the character that the new character-class will match. 
     * @return this. 
     */
    public IGrammarBuilder range(String name, char character);
    
    /**
     * This method adds a range character-class to the grammar that is being built.
     * 
     * @param name is the name of the new character-class.
     * @param minimum is the minimum character in the inclusive range of characters.
     * @param maximum is the maximum character in the inclusive range of characters.
     * @return this. 
     */
    public IGrammarBuilder range(String name, char minimum, char maximum);
    
    /**
     * This method adds a combination character-class to the grammar that is being built.
     * 
     * @param name is the name of the new character-class.
     * @param elements are the character-classes that will be combined.
     * @return this. 
     * @throws IllegalArgumentException if any of the elements do not exist. 
     */
    public IGrammarBuilder combine(String name, String... elements);
    
    /**
     * This method adds a single-char character-class to the grammar that is being built.
     * 
     * @param name is the name of the new character-class.
     * @param include is the name of the character-class that is a superset of the new class.
     * @param exclude is the name of the character-class that is the complement of the new class.
     * @return this. 
     * @throws IllegalArgumentException if either argument character-class does not already exist.
     */    
    public IGrammarBuilder exclude(String name, String include, String exclude);
    
    /**
     * This method adds a negation character-class to the grammar that is being built.
     * 
     * @param name is the name of the new character-class.
     * @param negates is the name of a character-class to negate. 
     * @return this. 
     * @throws IllegalArgumentException if the character-class to negate does not exist. 
     */  
    public IGrammarBuilder negate(String name, String negates);
    
    /**
     * This method specifies the name of the root rule in the grammar.
     * 
     * @param name is the name of the root rule. 
     * @return this.
     */
    public IGrammarBuilder setRoot(String name);
    
    /**
     * This method specifies the number of match starts/successes/failures to record. 
     * 
     * <p>Higher counts may make debugging grammars easier, but more memory will be used.</p>
     * 
     * <p>If this method is not invoked, then a default value of (1024) will be used.</p>
     * 
     * @param count is the new maximum size of the backtrace.  
     * @return this.
     * @throws IllegalArgumentException if the new maximum size is less than zero. 
     */
    public IGrammarBuilder setTraceCount(int count);
    
}
