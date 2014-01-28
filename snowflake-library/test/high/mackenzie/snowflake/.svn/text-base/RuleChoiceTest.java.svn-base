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

public final class RuleChoiceTest
{
    
    /**
     * Test: of match method, of class OptionListRule.
     *
     * Case: two options in option-list, expect the first option to match.
     */
    @Test
    public void testMatchTwoOptionsExpectFirst()
    {
        /*
         * Test: Grammar:
         *
         * opt1 = sr1 / sr2;
         *
         * sr1 = "abc";
         *
         * sr2 = "def";
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("opt1");
        g.choose("opt1", "sr1", "sr2");
        g.str("sr1", "abc");
        g.str("sr2", "def");

        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abc");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(1, root.childCount());
        assertEquals(0, root.childAt(0).childCount());

        final ITreeNode m1 = root.childAt(0);
        final ITreeNode mz = root;

        assertEquals("abc", m1.text());
        assertEquals("abc", mz.text());
    }

    /**
     * Test: of match method, of class OptionListRule.
     *
     * Case: two options in option-list, expect option #2 to match.
     */
    @Test
    public void testMatchTwoOptionsExpectSecond()
    {
        /*
         * Test: Grammar:
         *
         * opt1 = sr1 / sr2;
         *
         * sr1 = "abc";
         *
         * sr2 = "def";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("opt1");
        g.choose("opt1", "sr1", "sr2");
        g.str("sr1", "abc");
        g.str("sr2", "def");

        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("def");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(1, root.childCount());
        assertEquals(0, root.childAt(0).childCount());

        final ITreeNode m1 = root.childAt(0);
        final ITreeNode mz = root;

        assertEquals("def", m1.text());
        assertEquals("def", mz.text());
    }

    /**
     * Test: of match method, of class OptionListRule.
     *
     * Case: three options in option-list, expect the first option to match.
     */
    @Test
    public void testMatchThreeOptionsExpectFirst()
    {
        /*
         * Test: Grammar:
         *
         * opt1 = sr1 / sr2 / sr3;
         *
         * sr1 = "abc";
         *
         * sr2 = "def";
         *
         * sr3 = "ghi";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("opt1");
        g.choose("opt1", "sr1", "sr2", "sr3");
        g.str("sr1", "abc");
        g.str("sr2", "def");
        g.str("sr3", "ghi");

        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abc");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(1, root.childCount());
        assertEquals(0, root.childAt(0).childCount());

        final ITreeNode m1 = root.childAt(0);
        final ITreeNode mz = root;

        assertEquals("abc", m1.text());
        assertEquals("abc", mz.text());
    }

    /**
     * Test: of match method, of class OptionListRule.
     *
     * Case: three options in option-list, expect the second option to match.
     */
    @Test
    public void testMatchThreeOptionsExpectSecond()
    {
        /*
         * Test: Grammar:
         *
         * opt1 = sr1 / sr2 / sr3;
         *
         * sr1 = "abc";
         *
         * sr2 = "def";
         *
         * sr3 = "ghi";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("opt1");
        g.choose("opt1", "sr1", "sr2", "sr3");
        g.str("sr1", "abc");
        g.str("sr2", "def");
        g.str("sr3", "ghi");

        final Parser p = g.build().newParser();
        
        final ParserOutput out = p.parse("def");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(1, root.childCount());
        assertEquals(0, root.childAt(0).childCount());

        final ITreeNode m1 = root.childAt(0);
        final ITreeNode mz = root;

        assertEquals("def", m1.text());
        assertEquals("def", mz.text());
    }

    /**
     * Test: of match method, of class OptionListRule.
     *
     * Case: three options in option-list, expect the last option to match.
     */
    @Test
    public void testMatchThreeOptionsExpectLast()
    {
        /*
         * Test: Grammar:
         *
         * opt1 = sr1 / sr2 / sr3;
         *
         * sr1 = "abc";
         *
         * sr2 = "def";
         *
         * sr3 = "ghi";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("opt1");
        g.choose("opt1", "sr1", "sr2", "sr3");
        g.str("sr1", "abc");
        g.str("sr2", "def");
        g.str("sr3", "ghi");

        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("ghi");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(1, root.childCount());
        assertEquals(0, root.childAt(0).childCount());

        final ITreeNode m1 = root.childAt(0);
        final ITreeNode mz = root;

        assertEquals("ghi", m1.text());
        assertEquals("ghi", mz.text());
    }

    /**
     * Test: of match method, of class OptionListRule.
     *
     * Case: three options in option-list, all fail to match.
     */
    @Test
    public void testMatchThreeOptionsExpectTotalFailure()
    {
        /*
         * Test: Grammar:
         *
         * opt1 = sr1 / sr2 / sr3;
         *
         * sr1 = "abc";
         *
         * sr2 = "def";
         *
         * sr3 = "ghi";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("opt1");
        g.choose("opt1", "sr1", "sr2", "sr3");
        g.str("sr1", "abc");
        g.str("sr2", "def");
        g.str("sr3", "ghi");

        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("jkl");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);
    }

}