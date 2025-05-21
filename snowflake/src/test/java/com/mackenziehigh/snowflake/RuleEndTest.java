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

public final class RuleEndTest 
{
    
    /**
     * Method: <code>match(State)</code>
     *
     * Case: Attempt to match, when not at end of input.
     */
    @Test
    public void testITreeNodeNotAtEnd()
    {
        /*
         * Test: Grammar:
         *
         * seq1 = END , sr1;
         *
         * sr1 = "abc";
         */
        final GrammarBuilder g= new GrammarBuilder();
        g.setRoot("seq1");
        g.sequence("seq1", "END", "sr1");
        g.str("sr1", "abc");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abc");
        
        final ITreeNode root = out.parseTree();

        assertEquals(true, root == null);
    }

    /**
     * Method: <code>match(State)</code>
     *
     * Case: Attempt to match, when at end of input.
     */
    @Test
    public void testITreeNodeAtEnd()
    {
        /*
         * Test: Grammar:
         *
         * seq1 = sr1 , END;
         *
         * sr1 = "abc";
         */
        final GrammarBuilder g= new GrammarBuilder();
        g.setRoot("seq1");
        g.sequence("seq1", "sr1", "END");
        g.str("sr1", "abc");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("abc");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(2, root.childCount());
        assertEquals(0, root.childAt(0).childCount());
        assertEquals(0, root.childAt(1).childCount());

        final ITreeNode m0 = root.childAt(0);
        final ITreeNode m1 = root.childAt(1);
        final ITreeNode mz = root;

        assertEquals("abc", m0.text());
        assertEquals("", m1.text());
        assertEquals("abc", mz.text());
    }

    /**
     *Method: <code>match(State)</code>
     *
     * Case: Attempt to match, when input is empty.
     */
    @Test
    public void testITreeNodeEmpty()
    {
        final GrammarBuilder g= new GrammarBuilder();
        g.setRoot("END");
        
        final Parser p = g.build().newParser();

        final ParserOutput out = p.parse("");
        
        final ITreeNode root = out.parseTree();

        assertEquals(false, root == null);

        assertEquals(0, root.childCount());

        final ITreeNode mz = root;

        assertEquals("", mz.text());
    }

}