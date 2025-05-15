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

import org.junit.Test;
import static org.junit.Assert.*;

public final class GrammarBuilderTest 
{
    
    @Test
    public void testAnd() 
    {
        System.out.println("and");
        
        final GrammarBuilder g = new GrammarBuilder();
        
        g.str("planet", "Vulcan");
        g.and("root", "planet");
        
        g.setRoot("root");
        
        final RuleAnd rule = (RuleAnd) g.build().root;
        
        assertEquals("root", rule.name());
        
        final RuleString item = (RuleString) rule.item;
        
        assertEquals("planet", item.name());
        assertEquals("Vulcan", new String(item.string));
    }

    @Test
    public void testNot() 
    {
        System.out.println("not");
        
        final GrammarBuilder g = new GrammarBuilder();
        
        g.str("planet", "Vulcan");
        g.not("root", "planet");
        
        g.setRoot("root");
        
        final RuleNot rule = (RuleNot) g.build().root;
        
        assertEquals("root", rule.name());
        
        final RuleString item = (RuleString) rule.item;
        
        assertEquals("planet", item.name());
        assertEquals("Vulcan", new String(item.string));
    }

    @Test
    public void testEnd() 
    {
        System.out.println("end");
        
        final GrammarBuilder g = new GrammarBuilder();
        
        g.sequence("root", "END");
        
        g.setRoot("root");
        
        final RuleSequence rule = (RuleSequence) g.build().root;
        
        assertEquals("root", rule.name());
        
        final RuleEnd target = (RuleEnd) rule.elements[0];
        
        assertEquals("END", target.name());
    }

    @Test
    public void testSequence() 
    {
        System.out.println("sequence");
        
        final GrammarBuilder g = new GrammarBuilder();
        
        g.str("planet1", "Vulcan");
        g.str("planet2", "Romulus");
        g.str("planet3", "Earth");
        g.sequence("root", "planet1", "planet2", "planet3");
        
        g.setRoot("root");
        
        final RuleSequence rule = (RuleSequence) g.build().root;
        
        assertEquals("root", rule.name());   
        assertEquals(3, rule.elements.length);
     
        final RuleString element1 = (RuleString) (rule.elements[0]);
        final RuleString element2 = (RuleString) (rule.elements[1]);
        final RuleString element3 = (RuleString) (rule.elements[2]);
        
        assertEquals("planet1", element1.name());
        assertEquals("Vulcan", new String(element1.string));
        
        assertEquals("planet2", element2.name());
        assertEquals("Romulus", new String(element2.string));
        
        assertEquals("planet3", element3.name());
        assertEquals("Earth", new String(element3.string));
    }

    @Test
    public void testSequenceDLR()
    {
        System.out.println("sequenceDLR");
        
        final GrammarBuilder g = new GrammarBuilder();
        
        g.str("planet1", "Vulcan");
        g.str("planet2", "Romulus");
        g.str("planet3", "Earth");
        g.str("planet4", "Mars");
        g.sequenceDLR("root", "planet1", "planet2", "planet3", "planet4");
        
        g.setRoot("root");
        
        final RuleSequenceDLR rule = (RuleSequenceDLR) g.build().root;
        
        assertEquals("root", rule.name());   
        assertEquals(3, rule.shared.elements.length);
     
        final RuleString head = (RuleString) rule.base_case;
        
        final RuleString tail1 = (RuleString) rule.shared.elements[0];
        final RuleString tail2 = (RuleString) rule.shared.elements[1];
        final RuleString tail3 = (RuleString) rule.shared.elements[2];
        
        assertEquals("planet1", head.name());
        assertEquals("Vulcan", new String(head.string));
        
        assertEquals("planet2", tail1.name());
        assertEquals("Romulus", new String(tail1.string));
        
        assertEquals("planet3", tail2.name());
        assertEquals("Earth", new String(tail2.string));
        
        assertEquals("planet4", tail3.name());
        assertEquals("Mars", new String(tail3.string));
    }

    @Test
    public void testChoose() 
    {
        System.out.println("choose");
        
        final GrammarBuilder g = new GrammarBuilder();
        
        g.str("planet1", "Vulcan");
        g.str("planet2", "Romulus");
        g.str("planet3", "Earth");
        g.choose("root", "planet1", "planet2", "planet3");
        
        g.setRoot("root");
        
        final RuleChoice rule = (RuleChoice) g.build().root;
        
        assertEquals("root", rule.name());   
        assertEquals(3, rule.options.length);
     
        final RuleString option1 = (RuleString) (rule.options[0]);
        final RuleString option2 = (RuleString) (rule.options[1]);
        final RuleString option3 = (RuleString) (rule.options[2]);
        
        assertEquals("planet1", option1.name());
        assertEquals("Vulcan", new String(option1.string));
        
        assertEquals("planet2", option2.name());
        assertEquals("Romulus", new String(option2.string));
        
        assertEquals("planet3", option3.name());
        assertEquals("Earth", new String(option3.string));
    }

    @Test
    public void testRepeat() 
    {
        System.out.println("repeat");
        
        final GrammarBuilder g = new GrammarBuilder();
        
        g.str("planet", "Vulcan");
        g.repeat("root", "planet", 21, 23);
        
        g.setRoot("root");
        
        final RuleRepetition rule = (RuleRepetition) g.build().root;
        
        assertEquals("root", rule.name());
        
        assertEquals(21, rule.minimum);
        assertEquals(23, rule.maximum);
        
        final RuleString item = (RuleString) (rule.item);
        
        assertEquals("planet", item.name());
        assertEquals("Vulcan", new String(item.string));
    }

    /**
     * Test: 20130326231241062122
     * 
     * <p>
     * Method: <code>repeat(String, String, int, int)</code>
     * </p>
     *
     * <p>
     * Case: minimum is greater-than maximum
     * </p>
     */
     @Test (expected = IllegalArgumentException.class)
     public void test20130326231241062122()
     {
         System.out.println("Test: 20130326231241062122");
         
         final GrammarBuilder g = new GrammarBuilder();
        
         g.repeat("root", "planet", 24, 23);
     }
     
    /**
     * Test: 20130326231650076467
     * 
     * <p>
     * Method: <code>repeat(String, String, int, int)</code>
     * </p>
     *
     * <p>
     * Case: minimum equals maximum
     * </p>
     */
     @Test
     public void test20130326231650076467()
     {
         System.out.println("Test: 20130326231650076467");
         
        final GrammarBuilder g = new GrammarBuilder();
        
        g.str("planet", "Vulcan");
        g.repeat("root", "planet", 17, 17);
        
        g.setRoot("root");
        
        final RuleRepetition rule = (RuleRepetition) g.build().root;
        
        assertEquals("root", rule.name());
        
        assertEquals(17, rule.minimum);
        assertEquals(17, rule.maximum);
        
        final RuleString item = (RuleString) (rule.item);
        
        assertEquals("planet", item.name());
        assertEquals("Vulcan", new String(item.string));
     }
    
    @Test
    public void testStar() 
    {
        System.out.println("star");
        
        final GrammarBuilder g = new GrammarBuilder();
        
        g.str("planet", "Vulcan");
        g.star("root", "planet");
        
        g.setRoot("root");
        
        final RuleRepetition rule = (RuleRepetition) g.build().root;
        
        assertEquals("root", rule.name());
        
        assertEquals(0, rule.minimum);
        assertEquals(Integer.MAX_VALUE, rule.maximum);
        
        final RuleString item = (RuleString) (rule.item);
        
        assertEquals("planet", item.name());
        assertEquals("Vulcan", new String(item.string));
    }

    @Test
    public void testPlus() 
    {
        System.out.println("plus");
        
        final GrammarBuilder g = new GrammarBuilder();
        
        g.str("planet", "Vulcan");
        g.plus("root", "planet");
        
        g.setRoot("root");
        
        final RuleRepetition rule = (RuleRepetition) g.build().root;
        
        assertEquals("root", rule.name());
        
        assertEquals(1, rule.minimum);
        assertEquals(Integer.MAX_VALUE, rule.maximum);
        
        final RuleString item = (RuleString) (rule.item);
        
        assertEquals("planet", item.name());
        assertEquals("Vulcan", new String(item.string));
    }

    @Test
    public void testOption() 
    {
        System.out.println("option");
        
        final GrammarBuilder g = new GrammarBuilder();
        
        g.str("planet", "Vulcan");
        g.option("root", "planet");
        
        g.setRoot("root");
        
        final RuleRepetition rule = (RuleRepetition) g.build().root;
        
        assertEquals("root", rule.name());
        
        assertEquals(0, rule.minimum);
        assertEquals(1, rule.maximum);
        
        final RuleString item = (RuleString) (rule.item);
        
        assertEquals("planet", item.name());
        assertEquals("Vulcan", new String(item.string));
    }

    @Test
    public void testStr() 
    {
        System.out.println("str");
        
        final GrammarBuilder g = new GrammarBuilder();
        
        g.str("root", "Hello World!");
        
        g.setRoot("root");
        
        final RuleString rule = (RuleString) g.build().root;
        
        assertEquals("root", rule.name());
        assertEquals("Hello World!", new String(rule.string));
    }

    @Test
    public void testChr() 
    {
        System.out.println("str");
        
        final GrammarBuilder g = new GrammarBuilder();
        
        g.chr("root", "LETTER"); g.range("LETTER", 'G');
        
        g.setRoot("root");
        
        final RuleCharacter rule = (RuleCharacter) g.build().root;
        
        assertEquals("root", rule.name());
        assertEquals('G', ((CharacterClassRange) rule.character_class).maximum);
        assertEquals('G', ((CharacterClassRange) rule.character_class).minimum);
    }
    
    @Test
    public void testRangeSingleChar()
    {
        System.out.println("range(char)");
        
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        g.chr("root", "LETTER"); g.range("LETTER", 'J');
        
        final RuleCharacter rule = (RuleCharacter) g.build().root;
        
        final CharacterClassRange clazz = (CharacterClassRange) rule.character_class;
        
        assertEquals('J', clazz.maximum);
        assertEquals('J', clazz.minimum);
    }
    
    @Test
    public void testRangeMultiChar()
    {
        System.out.println("range(char, char)");
        
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        g.chr("root", "LETTER"); g.range("LETTER", 'H', 'K');
        
        final RuleCharacter rule = (RuleCharacter) g.build().root;
        
        final CharacterClassRange clazz = (CharacterClassRange) rule.character_class;
        
        assertEquals('H', clazz.minimum);
        assertEquals('K', clazz.maximum);
    }
    
    @Test
    public void testExclude()
    {
        System.out.println("exclude(CharacterClass, CharacterClass)");
        
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        g.range("c1", 'A'); 
        g.range("c2", 'B'); 
        g.exclude("LETTER", "c1", "c2");
        
        g.chr("root", "LETTER"); 
        
        final RuleCharacter rule = (RuleCharacter) g.build().root;
        
        final CharacterClassExclusion clazz = (CharacterClassExclusion) rule.character_class;
        
        assertTrue(clazz.includes.match('A'));
        assertTrue(clazz.excludes.match('B'));
    }
    
    @Test
    public void testCombine()
    {
        System.out.println("combine(CharacterClass...)");

        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        g.range("c1", 'A'); 
        g.range("c2", 'B'); 
        g.combine("LETTER", "c1", "c2");
        
        g.chr("root", "LETTER"); 
        
        final RuleCharacter rule = (RuleCharacter) g.build().root;
        
        final CharacterClassCombination clazz = (CharacterClassCombination) rule.character_class;
        
        assertTrue(clazz.elements[0].match('A'));
        assertTrue(clazz.elements[1].match('B'));
    }
    
    @Test
    public void testNegate()
    {
        System.out.println("negate(CharacterClass)");
        
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        g.range("X", 'A', 'Z');
        g.negate("Y", "X");
        
        g.chr("root", "Y"); 
        
        final RuleCharacter rule = (RuleCharacter) g.build().root;
        
        final CharacterClassNegation clazz = (CharacterClassNegation) rule.character_class;
        
        assertTrue(clazz.negates.match('A'));
        assertTrue(clazz.negates.match('K'));
        assertTrue(clazz.negates.match('Z'));
    }

    @Test
    public void testBuildParser()
    {
        System.out.println("buildParser()");
        
        final GrammarBuilder g = new GrammarBuilder();
        
        g.str("x", "AA");
        g.str("y", "BB");
        g.sequence("root", "x", "y");
        
        g.setRoot("root");
        
        final Parser p = g.build().newParser();
        
        final ParserOutput out = p.parse("AABB");
        
        final ITreeNode root = out.parseTree();
        
        assertEquals("root", root.rule());
        assertEquals("AABB", root.text());
        
        assertEquals("x", root.childAt(0).rule());
        assertEquals("AA", root.childAt(0).text());
        
        assertEquals("y", root.childAt(1).rule());
        assertEquals("BB", root.childAt(1).text());
    }
    
    @Test(expected = IllegalStateException.class)
    public void testDuplicateRule()
    {
        System.out.println("testDuplicateRule()");
        
        final GrammarBuilder g = new GrammarBuilder();
        
        g.str("x", "AA");
        g.str("x", "BB");
        
        g.setRoot("x");
        
        g.build();
    }
    
    @Test(expected = IllegalStateException.class)
    public void testNoSuchRule()
    {
        System.out.println("testNoSuchRule()");
        
        final GrammarBuilder g = new GrammarBuilder();
        
        g.str("x", "AA");
        g.str("y", "BB");
        g.sequence("root", "x", "y", "z");
        
        g.setRoot("root");
        
        g.build().newParser();
    }
    
    /**
     * Test: 20130326233523816285
     *
     * <p>
     * Case: duplicate character-class
     * </p>
     */
     @Test (expected = IllegalStateException.class)
     public void test20130326233523816285()
     {
         System.out.println("Test: 20130326233523816285");
         
         final GrammarBuilder g = new GrammarBuilder();
         
         g.range("x", 'a');
         g.range("x", 'b');
     }
     
    /**
     * Test: 20130326233523816407
     *
     * <p>
     * Case: undeclared character-class
     * </p>
     */
     @Test (expected = IllegalStateException.class)
     public void test20130326233523816407()
     {
         System.out.println("Test: 20130326233523816407");
                  
         final GrammarBuilder g = new GrammarBuilder();
         
         g.range("x", 'a');
         g.range("y", 'b');
         g.combine("m", "x", "y", "z");
     }
     
    /**
     * Test: 20130326233523816456
     *
     * <p>
     * Case: no root
     * </p>
     */
     @Test (expected = IllegalStateException.class)
     public void test20130326233523816456()
     {
         System.out.println("Test: 20130326233523816456");
         
         final GrammarBuilder g = new GrammarBuilder();
         
         g.build();
     }
     
    /**
     * Test: 20130326233523816497
     * 
     * <p>
     * Method: <code>setTraceCount(int)</code>
     * </p>
     *
     * <p>
     * Case: negative argument
     * </p>
     */
     @Test (expected = IllegalArgumentException.class)
     public void test20130326233523816497()
     {
         System.out.println("Test: 20130326233523816497");
         
         final GrammarBuilder g = new GrammarBuilder();
         
         g.setTraceCount(-1);
     }

}
