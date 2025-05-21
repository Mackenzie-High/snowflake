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

import org.junit.Test;
import static org.junit.Assert.*;

public final class RuleStringTest 
{

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match at start of input, when the string's length
     *       is zero, expect success.
     */
    @Test
    public void testMatchStartLenZeroSuccess()
    {
        /*
         * Test: Grammar
         *
         * seq1 = sr1, sr2;
         * 
         * sr1 = "";
         * 
         * sr2 = "x";
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        g.sequence("seq1", "sr1", "sr2");
        g.str("sr1", "");
        g.str("sr2", "x");

        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("x");
        
        final ITreeNode root = out.parseTree();

        assertFalse(root == null);

        assertEquals(2, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(0, root.childAt(1).childCount());

        final ITreeNode seq1Node = root;
        final ITreeNode sr1Node = root.childAt(0);
        final ITreeNode sr2Node = root.childAt(1);
        
        assertEquals("x", seq1Node.text());
        assertEquals("", sr1Node.text());
        assertEquals("x", sr2Node.text());

        assertEquals(seq1Node.rule(), "seq1");
        assertEquals(sr1Node.rule(), "sr1");
        assertEquals(sr2Node.rule(), "sr2");
    }
    
    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match at end of input, when the string's length
     *       is zero, expect success.
     */
    @Test
    public void testMatchEndLenZeroSuccess()
    {
        /*
         * Test: Grammar
         *
         * seq1 = sr1, sr2;
         * 
         * sr1 = "x";
         * 
         * sr2 = "";
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        g.sequence("seq1", "sr1", "sr2");
        g.str("sr1", "x");
        g.str("sr2", "");

        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("x");
        
        final ITreeNode root = out.parseTree();

        assertFalse(root == null);

        assertEquals(2, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(0, root.childAt(1).childCount());

        final ITreeNode seq1Node = root;
        final ITreeNode sr1Node = root.childAt(0);
        final ITreeNode sr2Node = root.childAt(1);
        
        assertEquals("x", seq1Node.text());
        assertEquals("x", sr1Node.text());
        assertEquals("", sr2Node.text());

        assertEquals(seq1Node.rule(), "seq1");
        assertEquals(sr1Node.rule(), "sr1");
        assertEquals(sr2Node.rule(), "sr2");
    }
    
    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match at start of input, when the string's length
     *       is zero, expect success.
     */
    @Test
    public void testMatchMiddleLenZeroSuccess()
    {
        /*
         * Test: Grammar
         *
         * seq1 = sr1, sr2, sr3;
         * 
         * sr1 = "x";
         * 
         * sr2 = "";
         * 
         * sr3 = "y";
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        g.sequence("seq1", "sr1", "sr2", "sr3");
        g.str("sr1", "x");
        g.str("sr2", "");
        g.str("sr3", "y");
     
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("xy");
        
        final ITreeNode root = out.parseTree();

        assertFalse(root == null);

        assertEquals(3, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(0, root.childAt(1).childCount());
        assertEquals(0, root.childAt(2).childCount());

        final ITreeNode seq1Node = root;
        final ITreeNode sr1Node = root.childAt(0);
        final ITreeNode sr2Node = root.childAt(1);
        final ITreeNode sr3Node = root.childAt(2);
        
        assertEquals("xy", seq1Node.text());
        assertEquals("x", sr1Node.text());
        assertEquals("", sr2Node.text());
        assertEquals("y", sr3Node.text());

        assertEquals(seq1Node.rule(), "seq1");
        assertEquals(sr1Node.rule(), "sr1");
        assertEquals(sr2Node.rule(), "sr2");
        assertEquals(sr3Node.rule(), "sr3");
    }
    
    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match at start of input, when the string's length
     *       is one, expect success.
     */
    @Test
    public void testMatchStartLenOneSuccess()
    {
        /*
         * Test: Grammar
         *
         * sr1 = "a";
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("sr1");
        g.str("sr1", "a");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("a");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(0, root.childCount());

        final ITreeNode m = root;

        assertEquals("a", m.text());

        assertEquals(m.rule(), "sr1");
    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match at start of input, when the string's length
     *       is one, expect failure.
     */
    @Test
    public void testMatchStartLenOneFailure()
    {
        /**
         * Test: Grammar
         *
         * sr1 = "a";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("sr1");
        g.str("sr1", "a");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("b");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);
    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match at start of input, when the string's length
     *       is two, expect success.
     */
    @Test
    public void testMatchStartLenTwoSuccess()
    {
        /*
         * Test: Grammar
         *
         * sr1 = "ab";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("sr1");
        g.str("sr1", "ab");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("ab");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(0, root.childCount());

        final ITreeNode m = root;

        assertEquals("ab", m.text());

        assertEquals(m.rule(), "sr1");
    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match at start of input, when the string's length
     *       is two, expect failure.
     */
    @Test
    public void testMatchStartLenTwoFailure()
    {
        /*
         * Test: Grammar
         *
         * sr1 = "ab";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("sr1");
        g.str("sr1", "ab");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("ac");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);
    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match at start of input, when the string's length
     *       is three, expect success.
     */
    @Test
    public void testMatchStartLenThreeSuccess()
    {
        /*
         * Test: Grammar
         *
         * sr1 = "abc";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("sr1");
        g.str("sr1", "abc");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abc");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(0, root.childCount());

        final ITreeNode m = root;

        assertEquals("abc", m.text());
    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match at start of input, when the string's length
     *       is three, expect failure.
     */
    @Test
    public void testMatchStartLenThreeFailure()
    {
        /**
         * Test: Grammar
         *
         * sr1 = "abc";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("sr1");
        g.str("sr1", "abc");
        
        final Parser p = g.build().newParser();
        
        final ParserOutput out = p.parse("abd");

        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);
    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match in middle of input, when the string's length
     *       is one, expect success.
     */
    @Test
    public void testMatchMiddleLenOneSuccess()
    {
        /*
         * Test: Grammar
         *
         * seq = sr1 , sr2 , sr3;
         *
         * sr1 = "a";
         *
         * sr2 = "b";
         *
         * sr3 = "c";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq");
        g.sequence("seq", "sr1", "sr2", "sr3");
        g.str("sr1", "a");
        g.str("sr2", "b");
        g.str("sr3", "c");
        
        final Parser p = g.build().newParser();

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
    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match in middle of input, when the string's length
     *       is one, expect failure.
     */
    @Test
    public void testMatchMiddleLenOneFailure()
    {
        /*
         * Test: Grammar
         *
         * seq = sr1 , sr2 , sr3;
         *
         * sr1 = "a";
         *
         * sr2 = "b";
         *
         * sr3 = "c";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq");
        g.sequence("seq", "sr1", "sr2", "sr3");
        g.str("sr1", "a");
        g.str("sr2", "b");
        g.str("sr3", "c");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("aec");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);
    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match in middle of input, when the string's length
     *       is two, expect success.
     */
    @Test
    public void testMatchMiddleLenTwoSuccess()
    {
        /*
         * Test: Grammar
         *
         * seq = sr1 , sr2 , sr3;
         *
         * sr1 = "a";
         *
         * sr2 = "bc";
         *
         * sr3 = "d";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq");
        g.sequence("seq", "sr1", "sr2", "sr3");
        g.str("sr1", "a");
        g.str("sr2", "bc");
        g.str("sr3", "d");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abcd");
        
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

        assertEquals("abcd", mz.text());
        assertEquals("a", m0.text());
        assertEquals("bc", m1.text());
        assertEquals("d", m2.text());
    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match in middle of input, when the string's length
     *       is two, expect failure.
     */
    @Test
    public void testMatchMiddleLenTwoFailure()
    {
        /*
         * Test: Grammar
         *
         * seq = sr1 , sr2 , sr3;
         *
         * sr1 = "a";
         *
         * sr2 = "bc";
         *
         * sr3 = "d";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq");
        g.sequence("seq", "sr1", "sr2", "sr3");
        g.str("sr1", "a");
        g.str("sr2", "bc");
        g.str("sr3", "d");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abzd");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);
    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match in middle of input, when the string's length
     *       is three, expect success.
     */
    @Test
    public void testMatchMiddleLenThreeSuccess()
    {
        /*
         * Test: Grammar
         *
         * seq = sr1 , sr2 , sr3;
         *
         * sr1 = "a";
         *
         * sr2 = "bcd";
         *
         * sr3 = "e";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq");
        g.sequence("seq", "sr1", "sr2", "sr3");
        g.str("sr1", "a");
        g.str("sr2", "bcd");
        g.str("sr3", "e");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abcde");
        
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

        assertEquals("abcde", mz.text());
        assertEquals("a", m0.text());
        assertEquals("bcd", m1.text());
        assertEquals("e", m2.text());
    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match in middle of input, when the string's length
     *       is three, expect failure.
     */
    @Test
    public void testMatchMiddleLenThreeFailure()
    {
        /*
         * Test: Grammar
         *
         * seq = sr1 , sr2 , sr3;
         *
         * sr1 = "a";
         *
         * sr2 = "bcd";
         *
         * sr3 = "e";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq");
        g.sequence("seq", "sr1", "sr2", "sr3");
        g.str("sr1", "a");
        g.str("sr2", "bcd");
        g.str("sr3", "e");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abcpe");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);
    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match at end of input, when the string's length
     *       is one, expect success.
     */
    @Test
    public void testMatchEndLenOneSuccess()
    {
        /*
         * Test: Grammar
         *
         * seq = sr1 , sr2 , sr3;
         *
         * sr1 = "a";
         *
         * sr2 = "b";
         *
         * sr3 = "c";
         * 
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq");
        g.sequence("seq", "sr1", "sr2", "sr3");
        g.str("sr1", "a");
        g.str("sr2", "b");
        g.str("sr3", "c");
        
        final Parser p = g.build().newParser();

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
    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match at end of input, when the string's length
     *       is one, expect failure.
     */
    @Test
    public void testMatchEndLenOneFailure()
    {
        /*
         * Test: Grammar
         *
         * seq = sr1 , sr2 , sr3;
         *
         * sr1 = "a";
         *
         * sr2 = "b";
         *
         * sr3 = "c";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq");
        g.sequence("seq", "sr1", "sr2", "sr3");
        g.str("sr1", "a");
        g.str("sr2", "b");
        g.str("sr3", "c");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abd");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);

    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match at end of input, when the string's length
     *       is two, expect success.
     */
    @Test
    public void testMatchEndLenTwoSuccess()
    {
        /*
         * Test: Grammar
         *
         * seq = sr1 , sr2 , sr3;
         *
         * sr1 = "a";
         *
         * sr2 = "b";
         *
         * sr3 = "cd";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq");
        g.sequence("seq", "sr1", "sr2", "sr3");
        g.str("sr1", "a");
        g.str("sr2", "b");
        g.str("sr3", "cd");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abcd");
        
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

        assertEquals("abcd", mz.text());
        assertEquals("a", m0.text());
        assertEquals("b", m1.text());
        assertEquals("cd", m2.text());
    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match at end of input, when the string's length
     *       is two, expect failure.
     */
    @Test
    public void testMatchEndLenTwoFailure()
    {
        /*
         * Test: Grammar
         *
         * seq = sr1 , sr2 , sr3;
         *
         * sr1 = "a";
         *
         * sr2 = "b";
         *
         * sr3 = "cd";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq");
        g.sequence("seq", "sr1", "sr2", "sr3");
        g.str("sr1", "a");
        g.str("sr2", "b");
        g.str("sr3", "cd");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abce");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);

    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match at end of input, when the string's length
     *       is three, expect success.
     */
    @Test
    public void testMatchEndLenThreeSuccess()
    {
        /*
         * Test: Grammar
         *
         * seq = sr1 , sr2 , sr3;
         *
         * sr1 = "a";
         *
         * sr2 = "b";
         *
         * sr3 = "cde";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq");
        g.sequence("seq", "sr1", "sr2", "sr3");
        g.str("sr1", "a");
        g.str("sr2", "b");
        g.str("sr3", "cde");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abcde");
        
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

        assertEquals("abcde", mz.text());
        assertEquals("a", m0.text());
        assertEquals("b", m1.text());
        assertEquals("cde", m2.text());
    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match at end of input, when the string's length
     *       is three, expect failure.
     */
    @Test
    public void testMatchEndLenThreeFailure()
    {
        /*
         * Test: Grammar
         *
         * seq = sr1 , sr2 , sr3;
         *
         * sr1 = "a";
         *
         * sr2 = "b";
         *
         * sr3 = "cde";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq");
        g.sequence("seq", "sr1", "sr2", "sr3");
        g.str("sr1", "a");
        g.str("sr2", "b");
        g.str("sr3", "cde");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abcdz");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);
    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match with insufficient input available.
     */
    @Test
    public void testMatchInsufficientRoom()
    {
        /*
         * Test: Grammar
         *
         * sr1 = "ab";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("sr1");
        g.str("sr1", "ab");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("a");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);

    }

    /**
     * Test: of match method, of class RuleString.
     *
     * Case: attempt to match when the input is empty, but the string is not.
     */
    @Test
    public void testMatchEmptyInput()
    {
        /*
         * Test: Grammar
         *
         * sr1 = "ab";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("sr1");
        g.str("sr1", "ab");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);
    }  
    
    /**
     * Method: <code>getName()</code>
     * 
     * Case: normal
     */
    @Test
    public void testGetName()
    {
        final RuleString str1 = new RuleString("str1");
        
        assertEquals("str1", str1.name());
    }    
    
    /**
     * Method: <code>toString()</code>
     * 
     * Case: normal
     */
    @Test
    public void testToString()
    {
        final RuleString str1 = new RuleString("str1");
        
        assertEquals("str1", str1.toString());
    }
}