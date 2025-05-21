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
import java.util.Map;
import java.util.TreeMap;

/**
 * This class provides a concrete grammar builder that can build grammars for immediate usage. 
 * 
 * @author Mackenzie High
 */
public final class GrammarBuilder implements IGrammarBuilder
{
    
    /**
     * {@inheritDoc}
     */
    public IGrammarBuilder setRoot(String name)
    {
        Utils.checkNonNull(name);
        
        this.root = name;
        
        return this;
    }    

    /**
     * {@inheritDoc}
     */
    public IGrammarBuilder setTraceCount(final int count)
    {
        if(count < 0) { throw new IllegalArgumentException(); }
        
        this.trace_count = count;
        
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder and(String name, String item) 
    {
        Utils.checkNonNull(name);
        Utils.checkNonNull(item);
        
        final DesignRuleAnd design = new DesignRuleAnd();
        design.name = name;
        design.item = item;
        
        this.grammar_rules.add(design);
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder not(String name, String item) 
    {
        Utils.checkNonNull(name);
        Utils.checkNonNull(item);
        
        final DesignRuleNot design = new DesignRuleNot();
        design.name = name;
        design.item = item;
        
        this.grammar_rules.add(design);
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder sequence(String name, String... elements) 
    {
        Utils.checkNonNull(name);
        Utils.checkNonNullArray(elements);
        
        final DesignRuleSequence design = new DesignRuleSequence();
        design.name = name;
        for(String element : elements) { design.elements.add(element); }
        
        this.grammar_rules.add(design);
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder sequenceDLR(String name, String base, String... shared)
    {
        Utils.checkNonNull(name);
        Utils.checkNonNull(base);
        Utils.checkNonNullArray(shared);
        
        final DesignRuleSequenceDLR design = new DesignRuleSequenceDLR();
        design.name = name;
        design.base = base;
        for(String element : shared) { design.shared.add(element); }
        
        this.grammar_rules.add(design);
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder choose(String name, String... options) 
    {
        Utils.checkNonNull(name);
        Utils.checkNonNullArray(options);
        
        final DesignRuleChoice design = new DesignRuleChoice();
        design.name = name;
        for(String option : options) { design.options.add(option); }
        
        this.grammar_rules.add(design);
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder repeat(String name, String item, int minimum, int maximum) 
    {
        Utils.checkNonNull(name);
        Utils.checkNonNull(item);
        
        if(minimum > maximum) { throw new IllegalArgumentException("minimum > maximum"); }
        
        final DesignRuleRepetition design = new DesignRuleRepetition();
        design.name = name;
        design.item = item;
        design.minimum = minimum;
        design.maximum = maximum;
        
        this.grammar_rules.add(design);
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder star(String name, String item) 
    {
        return repeat(name, item, 0, Integer.MAX_VALUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder plus(String name, String item) 
    {
        return repeat(name, item, 1, Integer.MAX_VALUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder option(String name, String item) 
    {
        return repeat(name, item, 0, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGrammarBuilder str(String name, String string) 
    {
        Utils.checkNonNull(name);
        Utils.checkNonNull(string);
        
        final DesignRuleString design = new DesignRuleString();
        design.name = name;
        design.string = string;
        
        this.grammar_rules.add(design);
        
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    public IGrammarBuilder chr(String name, String character) 
    {
        Utils.checkNonNull(name);
        Utils.checkNonNull(character);
        
        final DesignRuleCharacter design = new DesignRuleCharacter();
        design.name = name;
        design.character = character;
        
        this.grammar_rules.add(design);
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public IGrammarBuilder range(String name, char character) 
    {
        return range(name, character, character);
    }

    /**
     * {@inheritDoc}
     */
    public IGrammarBuilder range(String name, char minimum, char maximum) 
    {
        Utils.checkNonNull(name);
        
        declareCC(name, new CharacterClassRange(minimum, maximum));
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public IGrammarBuilder combine(String name, String... classes) 
    {
        Utils.checkNonNull(name);
        Utils.checkNonNull(classes);
        
        final List<ICharacterClass> list = new LinkedList<ICharacterClass>();
        
        for(String x : classes) { list.add(findCC(x)); }
        
        declareCC(name, new CharacterClassCombination(list.toArray(new ICharacterClass[0])));

        return this;
    }

    /**
     * {@inheritDoc}
     */
    public IGrammarBuilder exclude(String name, String include, String exclude) 
    {
        Utils.checkNonNull(name);
        Utils.checkNonNull(include);
        Utils.checkNonNull(exclude);
        
        declareCC(name, new CharacterClassExclusion(findCC(include), findCC(exclude)));
        
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public IGrammarBuilder negate(String name, String negates) 
    {
        Utils.checkNonNull(name);
        Utils.checkNonNull(negates);
        
        declareCC(name, new CharacterClassNegation(findCC(negates)));
        
        return this;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * This method performs the actual construction of the new grammar and returns the result. 
     * 
     * <p>Each invocation of this method returns a new object.</p>
     * 
     * @return the completed grammar. 
     * @throws IllegalStateException if the grammar cannot be built. 
     */
    public Grammar build() 
    {
        final Map<String, Rule> rules = new TreeMap<String, Rule>();
        
        rules.put("END", new RuleEnd());
        
        for(RuleDesign design : grammar_rules) { design.declare(rules); }     
        
        for(RuleDesign design : grammar_rules) { design.initialize(rules); }
        
        if(rules.containsKey(root) == false) 
        { 
            throw new IllegalStateException("The root grammar rule does not exist.");
        }
        
        final Grammar grammar = new Grammar(rules.get(root), trace_count);
        
        return grammar;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * This is the name of the root grammar rule. 
     */
    private String root = "";
    
    /**
     * These are the rules in the grammar being built. 
     */
    private final List<RuleDesign> grammar_rules = new LinkedList<RuleDesign>();
    
    /**
     * These are the character classes in the grammar being built. 
     */
    private final Map<String, ICharacterClass> classes = new TreeMap<String, ICharacterClass>();
    
    /**
     * This is the maximum number of backtrace records to store at any given moment. 
     */
    private int trace_count = 1024;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * This method adds a new rule object to a given map.
     * 
     * @param map maps the name of a rule to the rule itself. 
     * @param rule is the rule to add to the map
     * @throws IllegalStateException if the rule has the same name as a previously declared rule.
     */
    private static void declareRule(final Map<String, Rule> map, 
                                    final Rule rule)
    {
        if(map.containsKey(rule.name()))
        {
            throw new IllegalStateException("Duplicate Rule: " + rule.name());
        }
        else
        {
            map.put(rule.name(), rule);
        }
    }
    
    /**
     * This method retrieves a named rule from a given map.
     * 
     * @param map maps the name of a rule to the rule itself. 
     * @param name is the name of the rule to return.
     * @return the rule with the given name. 
     * @throws IllegalStateException if there is no rule in the map with the given name. 
     */
    private static Rule findRule(final Map<String, Rule> map, 
                                 final String name)
    {
        if(map.containsKey(name))
        {
            return map.get(name);
        }
        else
        {
            throw new IllegalStateException("No Such Rule: " + name);
        }
    }
    
    /**
     * This method retrieves zero or more named rules from a given map.
     * 
     * <p>The order of the returned array is the same as the list of names.</p>
     * 
     * @param map maps the name of a rule to the rule itself. 
     * @param names are the names of the rules to return. 
     * @return an array containing the retrieved rules.
     * @throws IllegalStateException if there is no rule in the map with one of the given names. 
     */
    private static Rule[] findRules(final Map<String, Rule> rules, 
                                    final List<String> names)
    {
        final Rule[] array = new Rule[names.size()];
        
        int i = 0;
        for(String name : names) { array[i++] = findRule(rules, name); } 
        
        return array;
    }
    
    /**
     * This method adds a new character-class to the grammar that is being built.
     * 
     * @param name is the name of the character-class.
     * @param clazz is the character-class itself.
     * @throws IllegalStateException if the character-class does not have a unique name. 
     */
    private void declareCC(final String name, final ICharacterClass clazz)
    {
        if(classes.containsKey(name))
        {
            throw new IllegalStateException("Duplicate Character-Class: " + name);
        }
        else
        {
            classes.put(name, clazz);
        }
    }
    
    /**
     * This method retrieves a named character-class from a given map.
     * 
     * @param name is the name of the character-class to retrieve. 
     * @return the desired character-class.
     * @throws IllegalStateException if there is no character-class in the map with the given name. 
     */
    private ICharacterClass findCC(final String name)
    {
        if(classes.containsKey(name))
        {
            return classes.get(name);
        }
        else
        {
            throw new IllegalStateException("No Such Character-Class: " + name);
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * An instance of this interface is the design of a grammar rule. 
     */
    private interface RuleDesign 
    { 
        /**
         * This method adds this rule to a given map. 
         * 
         * @param rules maps the name of a rule to the rule itself. 
         * @throws IllegalStateException if this rule shares its name with another rule.
         */
        public void declare(Map<String, Rule> rules);
        
        /**
         * This method initializes the rule object that implements this grammar rule.
         * 
         * @param rules maps the name of a rule to the rule itself.
         * @throws IllegalStateException if rule is needed that does not exist. 
         */
        public void initialize(Map<String, Rule> rules);
    }
    
    /**
     * An instance of this class is the design of an and-rule.
     */
    private final class DesignRuleAnd implements RuleDesign
    {
        public String name;
        
        public String item;

        public void declare(Map<String, Rule> rules) 
        {
           GrammarBuilder.declareRule(rules, new RuleAnd(name));
        }

        public void initialize(Map<String, Rule> rules)
        {
            final RuleAnd rule = (RuleAnd) findRule(rules, name);
            
            rule.item = findRule(rules, item);
        }
    }
    
    /**
     * An instance of this class is the design of an not-rule.
     */
    private final class DesignRuleNot implements RuleDesign
    {
        public String name;
        
        public String item;

        public void declare(Map<String, Rule> rules) 
        {
            GrammarBuilder.declareRule(rules, new RuleNot(name));
        }

        public void initialize(Map<String, Rule> rules) 
        {
            final RuleNot rule = (RuleNot) findRule(rules, name);
            
            rule.item = findRule(rules, item);
        }
    }
    
    /**
     * An instance of this class is the design of an sequence-rule.
     */
    private final class DesignRuleSequence implements RuleDesign
    {
        public String name;
        
        public final List<String> elements = new LinkedList<String>();

        public void declare(Map<String, Rule> rules) 
        {
            GrammarBuilder.declareRule(rules, new RuleSequence(name));
        }

        public void initialize(Map<String, Rule> rules) 
        {
            final RuleSequence rule = (RuleSequence) findRule(rules, name);
            
            rule.elements = findRules(rules, elements);
        }
    }

    /**
     * An instance of this class is the design of an sequence-dlr-rule.
     */
    private final class DesignRuleSequenceDLR implements RuleDesign
    {
        public String name;
        
        public String base;
        
        public final List<String> shared = new LinkedList<String>();

        public void declare(Map<String, Rule> rules) 
        {
            GrammarBuilder.declareRule(rules, new RuleSequenceDLR(name));
        }

        public void initialize(Map<String, Rule> rules) 
        {
            final RuleSequenceDLR rule = (RuleSequenceDLR) findRule(rules, name);
            
            final Rule base_rule = findRule(rules, base);

            rule.base_case = base_rule;
            
            rule.shared.elements = findRules(rules, shared);
        }
    }
    
    /**
     * An instance of this class is the design of a choice-rule.
     */
    private final class DesignRuleChoice implements RuleDesign
    {
        public String name;
        
        public final List<String> options = new LinkedList<String>();

        public void declare(Map<String, Rule> rules) 
        {
            GrammarBuilder.declareRule(rules, new RuleChoice(name));
        }

        public void initialize(Map<String, Rule> rules) 
        {
            final RuleChoice rule = (RuleChoice) findRule(rules, name);
            
            rule.options = findRules(rules, options);
        }
    }
    
    /**
     * An instance of this class is the design of a repetition-rule.
     */
    private final class DesignRuleRepetition implements RuleDesign
    {
        public String name;
        
        public String item;
        
        public int minimum;
        
        public int maximum;

        public void declare(Map<String, Rule> rules) 
        {
            GrammarBuilder.declareRule(rules, new RuleRepetition(name));
        }

        public void initialize(Map<String, Rule> rules) 
        {
            final RuleRepetition rule = (RuleRepetition) findRule(rules, name);
            
            rule.item = findRule(rules, item);
            rule.minimum = minimum;
            rule.maximum = maximum;
        }
    }    
    
    /**
     * An instance of this class is the design of a string-rule.
     */
    private final class DesignRuleString implements RuleDesign
    {
        public String name;
        
        public String string;

        public void declare(Map<String, Rule> rules) 
        {
            GrammarBuilder.declareRule(rules, new RuleString(name));
        }

        public void initialize(Map<String, Rule> rules)
        {
            final RuleString rule = (RuleString) findRule(rules, name);
            
            rule.string = string.toCharArray();
        }
    }
    
    /**
     * An instance of this class is the design of a character-rule.
     */
    private final class DesignRuleCharacter implements RuleDesign
    {
        public String name;
        
        public String character;

        public void declare(Map<String, Rule> rules) 
        {
            GrammarBuilder.declareRule(rules, new RuleCharacter(name));
        }

        public void initialize(Map<String, Rule> rules) 
        {
            final RuleCharacter rule = (RuleCharacter) findRule(rules, name);
            
            rule.character_class = findCC(character);
        }
    }
}
