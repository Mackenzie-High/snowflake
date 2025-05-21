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

public final class RuleNotTest 
{
    /**
     * Test: of match method, of class RuleNot.
     *
     * Case: Success.
     */
    @Test
    public void testMatchSuccess()
    {
        /*
         * Test: Grammar:
         *
         * seq1 = sr1 , nr1 , sr3;
         *
         * nr1 = !sr2;
         *
         * sr1 = "abc";
         *
         * sr2 = "def";
         * 
         * sr3 = "ghi";
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        g.sequence("seq1", "sr1", "nr1", "sr3");
        g.not("nr1", "sr2");
        g.str("sr1", "abc");
        g.str("sr2", "def");
        g.str("sr3", "ghi");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abcghi");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(3, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(0, root.childAt(1).childCount());
        assertEquals(0, root.childAt(1).childCount());

        final ITreeNode mz = root;
        final ITreeNode m0 = root.childAt(0);
        final ITreeNode m1 = root.childAt(1);
        final ITreeNode m2 = root.childAt(2);
        
        assertEquals("abcghi", mz.text());
        assertEquals("abc",    m0.text());
        assertEquals("", m1.text());
        assertEquals("ghi", m2.text());
    }

    /**
     * Test: of match method, of class RuleNot.
     *
     * Case: Failure.
     */
    @Test
    public void testMatchFailure()
    {
        /*
         * Test: Grammar
         *
         * seq1 = sr1, opt1 , sr3;
         *
         * opt1 = nr1 / sr2;
         *
         * nr1 = !sr2;
         *
         * sr1 = "a";
         *
         * sr2 = "b";
         *
         * sr3 = "c";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        g.sequence("seq1", "sr1", "opt1", "sr3");
        g.choose("opt1", "nr1", "sr2");
        g.not("nr1", "sr2");
        g.str("sr1", "a");
        g.str("sr2", "b");
        g.str("sr3", "c");

        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abc");
        
        final ITreeNode root = out.parseTree();

        assertFalse(root == null);

        assertEquals(3, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(1, root.childAt(1).childCount());
        assertEquals(0, root.childAt(1).childAt(0).childCount());
        assertEquals(0, root.childAt(2).childCount());

        final ITreeNode mz = root;
        final ITreeNode m0 = root.childAt(0);
        final ITreeNode m1 = root.childAt(1);
        final ITreeNode m10 = root.childAt(1).childAt(0);
        final ITreeNode m2 = root.childAt(2);

        assertEquals("abc", mz.text());
        assertEquals("a", m0.text());
        assertEquals("b", m1.text());
        assertEquals("b", m10.text());
        assertEquals("c", m2.text());
    }

}