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

public final class RuleSequenceTest 
{
    
    /**
     * Test: of match method, of class RuleSequence.
     *
     * Case: one item in the sequence, expect success.
     */
    @Test
    public void testMatchOneItemSuccess()
    {
        /*
         * Test: Grammar
         *
         * seq1 = sr1;
         *
         * sr1 = "a";
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        g.sequence("seq1", "sr1");
        g.str("sr1", "a");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("a");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(1, root.childCount());
        assertEquals(0, root.childAt(0).childCount());

        final ITreeNode mz = root;
        final ITreeNode m0 = root.childAt(0);

        assertEquals("a", mz.text());
        assertEquals("a", m0.text());
    }

    /**
     * Test: of match method, of class RuleSequence.
     *
     * Case: one item in the sequence, expect failure.
     */
    @Test
    public void testMatchOneItemFailure()
    {
        /*
         * Test: Grammar
         *
         * seq1 = sr1;
         *
         * sr1 = "a";
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        g.sequence("seq1", "sr1");
        g.str("sr1", "a");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("z");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);

    }

    /**
     * Test: of match method, of class RuleSequence.
     * 
     * Case: two items in the sequence, expect success.
     */
    @Test
    public void testMatchTwoItemsSuccess()
    {
        /*
         * Test: Grammar
         *
         * seq1 = sr1 , sr2;
         *
         * sr1 = "a";
         *
         * sr2 = "b";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        g.sequence("seq1", "sr1", "sr2");
        g.str("sr1", "a");
        g.str("sr2", "b");
        
        final Parser p = g.build().newParser();

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
    }

    /**
     * Test: of match method, of class RuleSequence.
     *
     * Case: two items in the sequence, expect failure.
     */
    @Test
    public void testMatchTwoItemsFailure()
    {
        /*
         * Test: Grammar
         *
         * seq1 = sr1 , sr2;
         *
         * sr1 = "a";
         *
         * sr2 = "b";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("seq1");
        g.sequence("seq1", "sr1", "sr2");
        g.str("sr1", "a");
        g.str("sr2", "b");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("ac");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);
    }

    /**
     * Test: of match method, of class RuleSequence.
     *
     * Case: three items in the sequence, expect success.
     */
    @Test
    public void testMatchThreeItemsSuccess()
    {
        /*
         * Test: Grammar
         *
         * seq1 = sr1 , sr2 , sr3;
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
        g.sequence("seq1", "sr1", "sr2", "sr3");
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
     * Test: of match method, of class RuleSequence.
     *
     * Case: three items in the sequence, expect the middle item to not match.
     */
    @Test
    public void testMatchThreeItemsFailure()
    {
        /*
         * Test: Grammar
         *
         * seq1 = sr1 , sr2 , sr3;
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
        g.sequence("seq1", "sr1", "sr2", "sr3");
        g.str("sr1", "a");
        g.str("sr2", "b");
        g.str("sr3", "c");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("adc");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);
    }

    /**
     * Test: of match method, of class RuleSequence.
     *
     * Case: three items in the sequence, expect the first item to not match.
     */
    @Test
    public void testMatchThreeItemsFirstFails()
    {
        /*
         * Test: Grammar
         *
         * opt1 = seq1 / seq2;
         *
         * seq1 = sr1 , sr2 , sr3;
         *
         * seq2 = sr3 , sr1 , sr2;
         *
         * sr1 = "a";
         *
         * sr2 = "b";
         *
         * sr3 = "c";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("opt1");
        g.choose("opt1", "seq1", "seq2");
        g.sequence("seq1", "sr1", "sr2", "sr3");
        g.sequence("seq2", "sr3", "sr1", "sr2");
        g.str("sr1", "a");
        g.str("sr2", "b");
        g.str("sr3", "c");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("cab");
        
        final ITreeNode root = out.parseTree();

        assertFalse(root == null);

        assertEquals(1, root.childCount());
        assertEquals(3, root.childAt(0).childCount());
        assertEquals(0, root.childAt(0).childAt(0).childCount());
        assertEquals(0, root.childAt(0).childAt(1).childCount());
        assertEquals(0, root.childAt(0).childAt(2).childCount());

        final ITreeNode mz = root;
        final ITreeNode m0 = root.childAt(0);
        final ITreeNode m00 = root.childAt(0).childAt(0);
        final ITreeNode m01 = root.childAt(0).childAt(1);
        final ITreeNode m02 = root.childAt(0).childAt(2);

        assertEquals("cab", mz.text());
        assertEquals("cab", m0.text());
        assertEquals("c", m00.text());
        assertEquals("a", m01.text());
        assertEquals("b", m02.text());
    }

    /**
     * Test: of match method, of class RuleSequence.
     *
     * Case: three items in the sequence, expect the last item to not match.
     */
    @Test
    public void testMatchThreeItemsLastFails()
    {
        /*
         * Test: Grammar:
         *
         * opt1 = seq1 / seq2;
         *
         * seq1 = sr1 , sr2 , sr3;
         *
         * seq2 = sr1 , sr2 , sr4;
         *
         * sr1 = "a";
         *
         * sr2 = "b";
         *
         * sr3 = "c";
         *
         * sr4 = "d";
         *
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("opt1");
        g.choose("opt1", "seq1", "seq2");
        g.sequence("seq1", "sr1", "sr2", "sr3");
        g.sequence("seq2", "sr1", "sr2", "sr4");
        g.str("sr1", "a");
        g.str("sr2", "b");
        g.str("sr3", "c");
        g.str("sr4", "d");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abd");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(1, root.childCount());
        assertEquals(3, root.childAt(0).childCount());
        assertEquals(0, root.childAt(0).childAt(0).childCount());
        assertEquals(0, root.childAt(0).childAt(1).childCount());
        assertEquals(0, root.childAt(0).childAt(2).childCount());

        final ITreeNode mz = root;
        final ITreeNode m0 = root.childAt(0);
        final ITreeNode m00 = root.childAt(0).childAt(0);
        final ITreeNode m01 = root.childAt(0).childAt(1);
        final ITreeNode m02 = root.childAt(0).childAt(2);

        assertEquals("abd", mz.text());
        assertEquals("abd", m0.text());
        assertEquals("a", m00.text());
        assertEquals("b", m01.text());
        assertEquals("d", m02.text());
    }

}
