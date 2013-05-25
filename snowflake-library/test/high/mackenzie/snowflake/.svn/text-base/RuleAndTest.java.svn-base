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

public final class RuleAndTest 
{
    
    /**
     * Test: of match method, of class RuleAnd.
     *
     * Case: Success.
     */
    @Test
    public void testMatchSuccess()
    {
        /*
         * Test: Grammar
         *
         * seq1 = sr1, ar1, sr2;
         *
         * sr1 = "abc";
         *
         * ar1 = &sr2;
         *
         * sr2 = "def";
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        g.sequence("seq1", "sr1", "ar1", "sr2");
        g.str("sr1", "abc");
        g.str("sr2", "def");
        g.and("ar1", "sr2");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abcdef");

        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(3, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(1, root.childAt(1).childCount());
        assertEquals(0, root.childAt(1).childAt(0).childCount());
        assertEquals(0, root.childAt(2).childCount());

        final ITreeNode m0 = root.childAt(0);
        final ITreeNode m1 = root.childAt(1);
        final ITreeNode m10 = root.childAt(1).childAt(0);
        final ITreeNode m2 = root.childAt(2);
        final ITreeNode mz = root;

        assertEquals("abc", m0.text());
        assertEquals("def", m1.text());
        assertEquals("def", m10.text());
        assertEquals("def", m2.text());
        assertEquals("abcdef", mz.text());
    }

    /**
     * Test: of match method, of class RuleAnd.
     *
     * Case: Failure.
     */
    @Test
    public void testMatchFailure()
    {
        /*
         * Test: Grammar:
         *
         * seq1 = sr1 , opt1;
         *
         * opt1 = ar1 / sr3;
         *
         * ar1 = &sr2;
         *
         * sr1 = "abc";
         *
         * sr2 = "def";
         *
         * sr3 = "ghi";
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        g.sequence("seq1", "sr1", "opt1");
        g.choose("opt1", "ar1", "sr3");
        g.and("ar1", "sr2");
        g.str("sr1", "abc");
        g.str("sr2", "def");
        g.str("sr3", "ghi");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abcghi");
        
        final ITreeNode root = out.parseTree();

        assertFalse(root == null);

        assertEquals(2, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(1, root.childAt(1).childCount());
        assertEquals(0, root.childAt(1).childAt(0).childCount());

        final ITreeNode abc = root.childAt(0);
        final ITreeNode ghi = root.childAt(1).childAt(0);
        final ITreeNode all = root;

        assertEquals("abc", abc.text());
        assertEquals("ghi", ghi.text());
        assertEquals("abcghi", all.text());
    }

}