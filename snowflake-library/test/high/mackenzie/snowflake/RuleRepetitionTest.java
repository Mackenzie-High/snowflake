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

public final class RuleRepetitionTest 
{

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int MORE = Integer.MAX_VALUE;

    private static CharacterClassRange chr(final char c)
    {
        return new CharacterClassRange(c, c);
    }
    
    private static CharacterClassRange range(final char x, final char y)
    {
        return new CharacterClassRange(x, y);
    }    
    
    /**
     * Test: of match method, of class RuleRepetition.
     *
     * Case: one-or-more rule matches one.
     */
    @Test
    public void testMatchOMOne()
    {
        /*
         * Test: Grammar:
         *
         * root = cr1+;
         *
         * cr1 = 'a' -> 'z';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        g.range("LETTER1", 'a', 'z');
        g.plus("root", "cr1");
        g.chr("cr1", "LETTER1");
      
        final Grammar grammar = g.build();
        
        final Parser p = grammar.newParser();

        final ParserOutput out = p.parse("a");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(1, root.childCount());
        assertEquals(0, root.childAt(0).childCount());

        final ITreeNode mz = root;
        final ITreeNode m0 = root.childAt(0);

        assertEquals("a", mz.text());
        assertEquals("a", m0.text());
        
        final RuleRepetition repetition = (RuleRepetition) grammar.root;
        assertEquals("cr1", repetition.item.name());
        assertEquals(ONE,  repetition.minimum);
        assertEquals(MORE, repetition.maximum);
    }

    /**
     * Test: of match method, of class RuleRepetition.
     *
     * Case: one-or-more rule matches two.
     */
    @Test
    public void testMatchOMTwo()
    {
        /*
         * Test: Grammar:
         *
         * root = cr1+;
         *
         * cr1 = 'a' -> 'z';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        g.range("LETTER1", 'a', 'z');
        g.plus("root", "cr1");
        g.chr("cr1", "LETTER1");
      
        final Grammar grammar = g.build();
        
        final Parser p = grammar.newParser();

        final ParserOutput out = p.parse("ab");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(2, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(0, root.childAt(1).childCount());

        final ITreeNode mz = root;
        final ITreeNode m0 = root.childAt(0);
        final ITreeNode m1 = root.childAt(1);

        assertEquals("ab", mz.text());
        assertEquals("a", m0.text());
        assertEquals("b", m1.text());
        
        final RuleRepetition repetition = (RuleRepetition) grammar.root;
        assertEquals("cr1", repetition.item.name());
        assertEquals(ONE,  repetition.minimum);
        assertEquals(MORE, repetition.maximum);
    }

    /**
     * Test: of match method, of class RuleRepetition.
     *
     * Case: one-or-more rule matches three.
     */
    @Test
    public void testMatchOMThree()
    {
        /*
         * Test: Grammar:
         *
         * root = cr1+;
         *
         * cr1 = 'a' -> 'z';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        g.range("LETTER1", 'a', 'z');
        g.plus("root", "cr1");
        g.chr("cr1", "LETTER1");
      
        final Grammar grammar = g.build();
        
        final Parser p = grammar.newParser();

        final ParserOutput out = p.parse("abc");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(3, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(0, root.childAt(1).childCount());
        assertEquals(0, root.childAt(2).childCount());

        final ITreeNode mz = root;
        final ITreeNode m0 = root.childAt(0);
        final ITreeNode m1 = root.childAt(1);
        final ITreeNode m2 = root.childAt(2);

        assertEquals("abc", mz.text());
        assertEquals("a", m0.text());
        assertEquals("b", m1.text());
        assertEquals("c", m2.text());
        
        final RuleRepetition repetition = (RuleRepetition) grammar.root;
        assertEquals("cr1", repetition.item.name());
        assertEquals(ONE,  repetition.minimum);
        assertEquals(MORE, repetition.maximum);
    }

    /**
     * Test: of match method, of class RuleRepetition.
     *
     * Case: one-or-more rule fails to match.
     */
    @Test
    public void testMatchOMZero()
    {
        /*
         * Test: Grammar:
         *
         * root = cr1+;
         *
         * cr1 = 'a' -> 'y';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        g.range("LETTER1", 'a', 'y');
        g.plus("root", "cr1");
        g.chr("cr1", "LETTER1");
      
        final Grammar grammar = g.build();
        
        final Parser p = grammar.newParser();

        final ParserOutput out = p.parse("z");
        
        final ITreeNode root = out.parseTree();

        assertTrue(root == null);
        
        final RuleRepetition repetition = (RuleRepetition) grammar.root;
        assertEquals("cr1", repetition.item.name());
        assertEquals(ONE,  repetition.minimum);
        assertEquals(MORE, repetition.maximum);
    }

    /**
     * Test: of match method, of class RuleRepetition.
     *
     * Case: zero-or-more rule matches zero.
     */
    @Test
    public void testMatchZMZero()
    {
        /*
         * Test: Grammar:
         *
         * root = cr1*;
         *
         * cr1 = 'a' -> 'y';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        g.range("LETTER1", 'a', 'y');
        g.star("root", "cr1");
        g.chr("cr1", "LETTER1");
      
        final Grammar grammar = g.build();
        
        final Parser p = grammar.newParser();

        final ParserOutput out = p.parse("z");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(0, root.childCount());

        final ITreeNode mz = root;

        assertEquals("", mz.text());
        
        final RuleRepetition repetition = (RuleRepetition) grammar.root;
        assertEquals("cr1", repetition.item.name());
        assertEquals(ZERO,  repetition.minimum);
        assertEquals(MORE, repetition.maximum);
    }

    /**
     * Test: of match method, of class RuleRepetition.
     *
     * Case: zero-or-more rule matches one.
     */
    @Test
    public void testMatchZMOne()
    {
        /*
         * Test: Grammar:
         *
         * root = cr1*;
         *
         * cr1 = 'a' -> 'z';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        g.range("LETTER1", 'a', 'z');
        g.star("root", "cr1");
        g.chr("cr1", "LETTER1");
      
        final Grammar grammar = g.build();
        
        final Parser p = grammar.newParser();

        final ParserOutput out = p.parse("a");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(1, root.childCount());
        assertEquals(0, root.childAt(0).childCount());

        final ITreeNode mz = root;
        final ITreeNode m0 = root.childAt(0);

        assertEquals("a", mz.text());
        assertEquals("a", m0.text());
        
        final RuleRepetition repetition = (RuleRepetition) grammar.root;
        assertEquals("cr1", repetition.item.name());
        assertEquals(ZERO,  repetition.minimum);
        assertEquals(MORE, repetition.maximum);
    }

    /**
     * Test: of match method, of class RuleRepetition.
     *
     * Case: zero-or-more rule matches two.
     */
    @Test
    public void testMatchZMTwo()
    {
        /*
         * Test: Grammar:
         *
         * root = cr1*;
         *
         * cr1 = 'a' -> 'z';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        g.range("LETTER1", 'a', 'z');
        g.star("root", "cr1");
        g.chr("cr1", "LETTER1");
      
        final Grammar grammar = g.build();
        
        final Parser p = grammar.newParser();
        
        final ParserOutput out = p.parse("ab");

        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(2, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(0, root.childAt(1).childCount());

        final ITreeNode mz = root;
        final ITreeNode m0 = root.childAt(0);
        final ITreeNode m1 = root.childAt(1);

        assertEquals("ab", mz.text());
        assertEquals("a", m0.text());
        assertEquals("b", m1.text());
        
        final RuleRepetition repetition = (RuleRepetition) grammar.root;
        assertEquals("cr1", repetition.item.name());
        assertEquals(ZERO,  repetition.minimum);
        assertEquals(MORE, repetition.maximum);
    }

    /**
     * Test: of match method, of class RuleRepetition.
     *
     * Case: zero-or-more rule matches three.
     */
    @Test
    public void testMatchZMThree()
    {
        /*
         * Test: Grammar:
         *
         * root = cr1*;
         *
         * cr1 = 'a' -> 'z';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        g.range("LETTER1", 'a', 'z');
        g.star("root", "cr1");
        g.chr("cr1", "LETTER1");
      
        final Grammar grammar = g.build();
        
        final Parser p = grammar.newParser();

        final ParserOutput out = p.parse("abc");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(3, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(0, root.childAt(1).childCount());
        assertEquals(0, root.childAt(2).childCount());

        final ITreeNode mz = root;
        final ITreeNode m0 = root.childAt(0);
        final ITreeNode m1 = root.childAt(1);
        final ITreeNode m2 = root.childAt(2);

        assertEquals("abc", mz.text());
        assertEquals("a", m0.text());
        assertEquals("b", m1.text());
        assertEquals("c", m2.text());
        
        final RuleRepetition repetition = (RuleRepetition) grammar.root;
        assertEquals("cr1", repetition.item.name());
        assertEquals(ZERO,  repetition.minimum);
        assertEquals(MORE, repetition.maximum);
    }

    /**
     * Test: of match method, of class RuleRepetition.
     *
     * Case: zero-or-one rule matches zero.
     */
    @Test
    public void testMatchZOZero()
    {
        /*
         * Test: Grammar:
         *
         * root = cr1?;
         *
         * cr1 = 'a' -> 'y';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        g.range("LETTER1", 'a', 'y');
        g.option("root", "cr1");
        g.chr("cr1", "LETTER1");
      
        final Grammar grammar = g.build();
        
        final Parser p = grammar.newParser();

        final ParserOutput out = p.parse("z");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(0, root.childCount());

        final ITreeNode mz = root;

        assertEquals("", mz.text());
        
        final RuleRepetition repetition = (RuleRepetition) grammar.root;
        assertEquals("cr1", repetition.item.name());
        assertEquals(ZERO,  repetition.minimum);
        assertEquals(ONE, repetition.maximum);
    }

    /**
     * Test: of match method, of class RuleRepetition.
     *
     * Case: zero-or-one rule matches zero.
     */
    @Test
    public void testMatchZOOne()
    {
        /*
         * Test: Grammar:
         *
         * root = cr1?;
         *
         * cr1 = 'a' -> 'y';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        g.range("LETTER1", 'a', 'y');
        g.option("root", "cr1");
        g.chr("cr1", "LETTER1");
      
        final Grammar grammar = g.build();
        
        final Parser p = grammar.newParser();

        final ParserOutput out = p.parse("m");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(1, root.childCount());
        assertEquals(0, root.childAt(0).childCount());

        final ITreeNode mz = root;
        final ITreeNode m0 = root.childAt(0);

        assertEquals("m", mz.text());
        assertEquals("m", m0.text());
        
        final RuleRepetition repetition = (RuleRepetition) grammar.root;
        assertEquals("cr1", repetition.item.name());
        assertEquals(ZERO,  repetition.minimum);
        assertEquals(ONE, repetition.maximum);
    }

    /**
     * Test: of match method, of class RuleRepetition.
     *
     * Case: A repetition rule must terminate, if all the input was already consumed. 
     */
    @Test
    public void testInfiniteLoop()
    {
        /*
         * Test: Grammar:
         *
         * root = cr1, rep1;
         * 
         * cr1 = 'm';
         * 
         * rep1 = END+;
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        g.range("LETTER1", 'm');
        g.sequence("root", "cr1", "rep1");
        g.chr("cr1", "LETTER1");
        g.plus("rep1", "END");
      
        final Grammar grammar = g.build();
        
        final Parser p = grammar.newParser();

        final ParserOutput out = p.parse("m");
        
        final ITreeNode root = out.parseTree();

        assertFalse(root == null);

        assertEquals(2, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(1, root.childAt(1).childCount());
        assertEquals(0, root.childAt(1).childAt(0).childCount());

        final ITreeNode cr = root.childAt(0);
        final ITreeNode rep = root.childAt(1);
        final ITreeNode end = root.childAt(1).childAt(0);

        assertEquals("cr1", cr.rule());
        assertEquals("m", cr.text());
        assertEquals("rep1", rep.rule());
        assertEquals("END", end.rule());

    }
    
/**
     * Test: of match method, of class RuleRepetition.
     *
     * Case: bounded, minimum equals maximum
     */
    @Test
    public void testMatchBoundedExact()
    {
        /*
         * Test: Grammar:
         *
         * root = cr1 , rep1 , cr1;
         *
         * rep1 = cr2 { 4 , 4 };
         * 
         * cr1 = 'a';
         * 
         * cr2 = 'b';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        g.range("LETTER1", 'a');
        g.range("LETTER2", 'b');
        
        g.sequence("root", "cr1", "rep1", "cr1");
        g.repeat("rep1", "cr2", 4, 4);
        g.chr("cr1", "LETTER1");
        g.chr("cr2", "LETTER2");
      
        final Grammar grammar = g.build();
        
        final Parser p = grammar.newParser();

        final ParserOutput out = p.parse("abbbba");
        
        final ITreeNode root = out.parseTree();

        assertFalse(root == null);

        assertEquals(3, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(4, root.childAt(1).childCount());
        assertEquals(0, root.childAt(2).childCount());

        final ITreeNode mz = root;
        final ITreeNode m0 = root.childAt(0);
        final ITreeNode m1 = root.childAt(1);
        final ITreeNode m2 = root.childAt(2);

        assertEquals("abbbba", mz.text());
        assertEquals("a", m0.text());
        assertEquals("bbbb", m1.text());
        assertEquals("a", m2.text());
        
        final RuleRepetition repetition = (RuleRepetition)
                                          ((RuleSequence) grammar.root).elements[1];
        
        assertEquals("cr2", repetition.item.name());
        assertEquals(4, repetition.minimum);
        assertEquals(4, repetition.maximum);
    }
}