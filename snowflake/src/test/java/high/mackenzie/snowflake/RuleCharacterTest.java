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

public final class RuleCharacterTest 
{

    /**
     * Test: of match method, of class CharRule.
     *
     * Case: attempt to match a specific character at the start of the input,
     *       expect success.
     */
    @Test
    public void testMatchSpecificStartSuccess()
    {
        /*
         * Test: Grammar:
         * 
         * seq1 = cr1 , sr1 , sr2;
         * 
         * cr1 = 'a';
         * 
         * sr1 = "bc";
         * 
         * sr2 = "def";
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        
        g.range("LETTER1", 'a');
        g.sequence("seq1", "cr1", "sr1", "sr2");
        g.chr("cr1", "LETTER1");
        g.str("sr1", "bc");
        g.str("sr2", "def");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abcdef");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(3, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(0, root.childAt(1).childCount());
        assertEquals(0, root.childAt(2).childCount());

        final ITreeNode m0 = root.childAt(0);
        final ITreeNode m1 = root.childAt(1);
        final ITreeNode m2 = root.childAt(2);
        final ITreeNode mz = root;

        assertEquals("a", m0.text());
        assertEquals("bc", m1.text());
        assertEquals("def", m2.text());
        assertEquals("abcdef", mz.text());
    }

    /**
     * Test: of match method, of class CharRule.
     *
     * Case: attempt to match a specific character at the start of the input,
     *       expect failure.
     */
    @Test
    public void testMatchSpecificStartFailure()
    {
        /*
         * Test: Grammar:
         * 
         * seq1 = cr1 , sr1 , sr2;
         * 
         * cr1 = 'a';
         * 
         * sr1 = "bc";
         * 
         * sr2 = "def";
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        
        g.range("LETTER1", 'a');
        g.sequence("seq1", "cr1", "sr1", "sr2");
        g.chr("cr1", "LETTER1");
        g.str("sr1", "bc");
        g.str("sr2", "def");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("bbcdef");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);        
    }

    /**
     * Test: of match method, of class CharRule.
     *
     * Case: attempt to match a specific character in the middle of the input, expect success.
     */
    @Test
    public void testMatchSpecificMiddleSuccess()
    {
        /*
         * Test: Grammar:
         * 
         * seq1 = sr1 , cr1 , sr2;
         * 
         * sr1 = "ab";   
         * 
         * cr1 = 'c';
         * 
         * sr2 = "def";
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        
        g.range("LETTER1", 'c');
        g.sequence("seq1", "sr1", "cr1", "sr2");
        g.chr("cr1", "LETTER1");
        g.str("sr1", "ab");
        g.str("sr2", "def");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abcdef");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(3, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(0, root.childAt(1).childCount());
        assertEquals(0, root.childAt(2).childCount());

        final ITreeNode m0 = root.childAt(0);
        final ITreeNode m1 = root.childAt(1);
        final ITreeNode m2 = root.childAt(2);
        final ITreeNode mz = root;

        assertEquals("ab", m0.text());
        assertEquals("c", m1.text());
        assertEquals("def", m2.text());
        assertEquals("abcdef", mz.text());        
    }

    /**
     * Test: of match method, of class CharRule.
     *
     * Case: attempt to match a specific character in the middle of the input,
     *       expect failure.
     */
    @Test
    public void testMatchSpecificMiddleFailure()
    {
        /*
         * Test: Grammar:
         *
         * seq1 = sr1 , cr1 , sr2;
         *
         * sr1 = "ab";
         *
         * cr1 = 'c';
         *
         * sr2 = "def";
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        
        g.range("LETTER1", 'c');
        g.sequence("seq1", "sr1", "cr1", "sr2");
        g.chr("cr1", "LETTER1");
        g.str("sr1", "ab");
        g.str("sr2", "def");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abddef");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);
    }

    /**
     * Test: of match method, of class CharRule.
     *
     * Case: attempt to match a specific character at the end of the input,
     *       expect success.
     */
    @Test
    public void testMatchSpecificEndSuccess()
    {
        /*
         * Test: Grammar:
         * 
         * seq1 = sr1 , sr2 , cr1;
         * 
         * sr1 = "abc";   
         * 
         * sr2 = "de";
         * 
         * cr1 = 'f';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        
        g.range("LETTER1", 'f');
        g.sequence("seq1", "sr1", "sr2", "cr1");
        g.chr("cr1", "LETTER1");
        g.str("sr1", "abc");
        g.str("sr2", "de");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abcdef");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(3, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(0, root.childAt(1).childCount());
        assertEquals(0, root.childAt(2).childCount());

        final ITreeNode m0 = root.childAt(0);
        final ITreeNode m1 = root.childAt(1);
        final ITreeNode m2 = root.childAt(2);
        final ITreeNode mz = root;

        assertEquals("abc", m0.text());
        assertEquals("de", m1.text());
        assertEquals("f", m2.text());
        assertEquals("abcdef", mz.text());          
    }

    /**
     * Test: of match method, of class CharRule.
     *
     * Case: attempt to match a specific character at the end of the input,
     *       expect failure.
     */
    @Test
    public void testMatchSpecificEndFailure()
    {
        /*
         * Test: Grammar:
         *
         * seq1 = sr1 , sr2 , cr1;
         *
         * sr1 = "abc";
         *
         * sr2 = "de";
         *
         * cr1 = 'f';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        
        g.range("LETTER1", 'f');
        g.sequence("seq1", "sr1", "sr2", "cr1");
        g.chr("cr1", "LETTER1");
        g.str("sr1", "abc");
        g.str("sr2", "de");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abcdeg");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);
    }

    /**
     * Test: of match method, of class CharRule.
     *
     * Case: attempt to match the max char in a range.
     */
    @Test
    public void testMatchMax()
    {
        /*
         * Test: Grammar:
         *
         * seq1 = sr1 , sr2 , cr1;
         *
         * sr1 = "abc";
         *
         * sr2 = "de";
         *
         * cr1 = 'f' -> 'i';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        
        g.range("LETTER1", 'f', 'i');
        g.sequence("seq1", "sr1", "sr2", "cr1");
        g.chr("cr1", "LETTER1");
        g.str("sr1", "abc");
        g.str("sr2", "de");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abcdei");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(3, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(0, root.childAt(1).childCount());
        assertEquals(0, root.childAt(2).childCount());

        final ITreeNode m0 = root.childAt(0);
        final ITreeNode m1 = root.childAt(1);
        final ITreeNode m2 = root.childAt(2);
        final ITreeNode mz = root;

        assertEquals("abc", m0.text());
        assertEquals("de", m1.text());
        assertEquals("i", m2.text());
        assertEquals("abcdei", mz.text());
    }

    /**
     * Test: of match method, of class CharRule.
     *
     * Case: attempt to match the min char in a range.
     */
    @Test
    public void testMatchMin()
    {
        /*
         * Test: Grammar:
         *
         * seq1 = sr1 , sr2 , cr1;
         *
         * sr1 = "abc";
         *
         * sr2 = "de";
         *
         * cr1 = 'f' -> 'i';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        
        g.range("LETTER1", 'f', 'i');
        g.sequence("seq1", "sr1", "sr2", "cr1");
        g.chr("cr1", "LETTER1");
        g.str("sr1", "abc");
        g.str("sr2", "de");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abcdef");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(3, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(0, root.childAt(1).childCount());
        assertEquals(0, root.childAt(2).childCount());

        final ITreeNode m0 = root.childAt(0);
        final ITreeNode m1 = root.childAt(1);
        final ITreeNode m2 = root.childAt(2);
        final ITreeNode mz = root;

        assertEquals("abc", m0.text());
        assertEquals("de", m1.text());
        assertEquals("f", m2.text());
        assertEquals("abcdef", mz.text());
    }

    /**
     * Test: of match method, of class CharRule.
     *
     * Case: attempt to match an over max char from a range.
     */
    @Test
    public void testMatchOverMax()
    {
        /*
         * Test: Grammar:
         *
         * seq1 = sr1 , sr2 , cr1;
         *
         * sr1 = "abc";
         *
         * sr2 = "de";
         *
         * cr1 = 'a' -> 'c';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        
        g.range("LETTER1", 'a', 'c');
        g.sequence("seq1", "sr1", "sr2", "cr1");
        g.chr("cr1", "LETTER1");
        g.str("sr1", "abc");
        g.str("sr2", "de");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abcded");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);
    }

    /**
     * Test: of match method, of class CharRule.
     *
     * Case: attempt to match an under min char from a range.
     */
    @Test
    public void testMatchUnderMin()
    {
        /*
         * Test: Grammar:
         *
         * seq1 = sr1 , sr2 , cr1;
         *
         * sr1 = "abc";
         *
         * sr2 = "de";
         *
         * cr1 = 'b' -> 'e';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        
        g.range("LETTER1", 'b', 'e');
        g.sequence("seq1", "sr1", "sr2", "cr1");
        g.chr("cr1", "LETTER1");
        g.str("sr1", "abc");
        g.str("sr2", "de");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abcdea");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);
    }

    /**
     * Test: of match method, of class CharRule.
     *
     * Case: attempt to match a specific char with no room in the input.
     */
    @Test
    public void testMatchNoRoom()
    {
        /*
         * Test: Grammar:
         *
         * seq1 = sr1 , sr2 , cr1;
         *
         * sr1 = "abc";
         *
         * sr2 = "def";
         *
         * cr1 = 'g';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        
        g.range("LETTER1", 'g');
        g.sequence("seq1", "sr1", "sr2", "cr1");
        g.chr("cr1", "LETTER1");
        g.str("sr1", "abc");
        g.str("sr2", "def");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abcdef");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);
    }

    /**
     * Test: of match method, of class CharRule.
     *
     * Case: attempt to match a specific char with empty input.
     */
    @Test
    public void testMatchEmpty()
    {
        /*
         * Test: Grammar:
         *
         * cr1 = 'g';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("cr1");
        
        g.range("LETTER1", 'g');
        g.chr("cr1", "LETTER1");

        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);
    }

}