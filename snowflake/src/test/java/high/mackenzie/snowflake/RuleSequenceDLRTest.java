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

public final class RuleSequenceDLRTest 
{

    /**
     * Method: match(State)
     * 
     * Case: no recursion
     */
    @Test
    public void testMatch_noRecursion() 
    {
        /*
         * root = x : y;
         * 
         * x = 'a';
         * 
         * y = 'b'
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        // Lexical Syntax
        g.range("LETTER1", 'a');
        g.range("LETTER2", 'b');
        
        // Grammatical Syntax
        g.sequenceDLR("root", "x", "y");
        g.chr("x", "LETTER1");
        g.chr("y", "LETTER2");
        
        final Parser p = g.build().newParser();
        
        final ParserOutput out = p.parse("ab");
        
        final ITreeNode tree = out.parseTree();
        
        assertEquals(2, tree.childCount());
        assertEquals(0, tree.childAt(0).childCount());
        assertEquals(0, tree.childAt(1).childCount());
        
        final ITreeNode mz = tree;
        final ITreeNode m0 = tree.childAt(0);
        final ITreeNode m1 = tree.childAt(1);
        
        assertEquals("ab", mz.text());
        assertEquals("a", m0.text());
        assertEquals("b", m1.text());
    }
    
    /**
     * Method: match(State)
     * 
     * Case: recurs one time
     */
    @Test
    public void testMatch_recursOnce() 
    {
        /**
         * root = x : y;
         * 
         * x = 'a';
         * 
         * y = 'b' -> 'z'
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        // Lexical Syntax
        g.range("LETTER1", 'a');
        g.range("LETTER2", 'b', 'z');
        
        // Grammatical Syntax
        g.sequenceDLR("root", "x", "y");
        g.chr("x", "LETTER1");
        g.chr("y", "LETTER2");
        
        final Parser p = g.build().newParser();
        
        final ParserOutput out = p.parse("abc");
        
        final ITreeNode tree = out.parseTree();
        
        assertEquals(2, tree.childCount());
        assertEquals(2, tree.childAt(0).childCount());
        assertEquals(0, tree.childAt(0).childAt(0).childCount());
        assertEquals(0, tree.childAt(0).childAt(1).childCount());
        assertEquals(0, tree.childAt(1).childCount());
        
        final ITreeNode mz = tree;
        final ITreeNode m0 = tree.childAt(0);
        final ITreeNode m00 = tree.childAt(0).childAt(0);
        final ITreeNode m01 = tree.childAt(0).childAt(1);
        final ITreeNode m1 = tree.childAt(1);
        
        assertEquals("abc", mz.text());
        assertEquals("ab", m0.text());
        assertEquals("a", m00.text());
        assertEquals("b", m01.text());
        assertEquals("c", m1.text());
    }    
       
    /**
     * Method: match(State)
     * 
     * Case: The shared part of the rule contains only one item.
     */
    @Test
    public void testMatch_singleItemShared() 
    {
        /**
         * root = x : y;
         * 
         * x = 'a';
         * 
         * y = 'b' -> 'z'
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        // Lexical Syntax
        g.range("LETTER1", 'a');
        g.range("LETTER2", 'b', 'z');
        
        // Grammatical Syntax
        g.sequenceDLR("root", "x", "y");
        g.chr("x", "LETTER1");
        g.chr("y", "LETTER2");
        
        final Parser p = g.build().newParser();
        
        final ParserOutput out = p.parse("ab");
        
        final ITreeNode tree = out.parseTree();
        
        assertEquals(2, tree.childCount());
        assertEquals(0, tree.childAt(0).childCount());
        assertEquals(0, tree.childAt(1).childCount());
        
        final ITreeNode mz = tree;
        final ITreeNode m0 = tree.childAt(0);
        final ITreeNode m1 = tree.childAt(1);
        
        assertEquals("ab", mz.text());
        assertEquals("a", m0.text());
        assertEquals("b", m1.text());
    }      
    
    /**
     * Method: match(State)
     * 
     * Case: There are two items in the tail part of the rule.
     */
    @Test
    public void testMatch_twoItemTail() 
    {
        /**
         * root = x : y , z;
         * 
         * x = 'a';
         * 
         * y = 'b';
         * 
         * z = 'c';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        // Lexical Syntax
        g.range("LETTER1", 'a');
        g.range("LETTER2", 'b');
        g.range("LETTER3", 'c');
        
        // Grammatical Syntax
        g.sequenceDLR("root", "x", "y", "z");
        g.chr("x", "LETTER1");
        g.chr("y", "LETTER2");
        g.chr("z", "LETTER3");
        
        final Parser p = g.build().newParser();
        
        final ParserOutput out = p.parse("abc");
        
        final ITreeNode tree = out.parseTree();
        
        assertEquals(3, tree.childCount());
        assertEquals(0, tree.childAt(0).childCount());
        assertEquals(0, tree.childAt(1).childCount());
        assertEquals(0, tree.childAt(2).childCount());
        
        final ITreeNode mz = tree;
        final ITreeNode m0 = tree.childAt(0);
        final ITreeNode m1 = tree.childAt(1);
        final ITreeNode m2 = tree.childAt(2);
        
        assertEquals("abc", mz.text());
        assertEquals("a", m0.text());
        assertEquals("b", m1.text());
        assertEquals("c", m2.text());
    }    
    
    /**
     * Method: match(State)
     * 
     * Case: There are three items in the tail part of the rule.
     */
    @Test
    public void testMatch_threeItemTail() 
    {
        /*
         * root = w : x , y , z;
         * 
         * w = 'a';
         * 
         * x = 'b';
         * 
         * y = 'c';
         * 
         * z = 'd';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        // Lexical Syntax
        g.range("LETTER1", 'a');
        g.range("LETTER2", 'b');
        g.range("LETTER3", 'c');
        g.range("LETTER4", 'd');
        
        // Grammatical Syntax
        g.sequenceDLR("root", "w", "x", "y", "z");
        g.chr("w", "LETTER1");
        g.chr("x", "LETTER2");
        g.chr("y", "LETTER3");
        g.chr("z", "LETTER4");
        
        final Parser p = g.build().newParser();
        
        final ParserOutput out = p.parse("abcd");
        
        final ITreeNode tree = out.parseTree();
        
        assertEquals(4, tree.childCount());
        assertEquals(0, tree.childAt(0).childCount());
        assertEquals(0, tree.childAt(1).childCount());
        assertEquals(0, tree.childAt(2).childCount());
        assertEquals(0, tree.childAt(3).childCount());
        
        final ITreeNode mz = tree;
        final ITreeNode m0 = tree.childAt(0);
        final ITreeNode m1 = tree.childAt(1);
        final ITreeNode m2 = tree.childAt(2);
        final ITreeNode m3 = tree.childAt(3);
        
        assertEquals("abcd", mz.text());
        assertEquals("a", m0.text());
        assertEquals("b", m1.text());
        assertEquals("c", m2.text());
        assertEquals("d", m3.text());
    }      
    
    /**
     * Method: match(State)
     * 
     * Case: The rule fails to match, because of the alternative.
     */
    @Test
    public void testMatch_failAlternative()
    {
        /*
         * root = x : y;
         * 
         * x = 'a';
         * 
         * y = 'b';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        // Lexical Syntax
        g.range("LETTER1", 'a');
        g.range("LETTER2", 'b');
        
        // Grammatical Syntax
        g.sequenceDLR("root", "x", "y");
        g.chr("x", "LETTER1");
        g.chr("y", "LETTER2");
        
        final Parser p = g.build().newParser();
        
        final ParserOutput out = p.parse("zb");
        
        final ITreeNode tree = out.parseTree();
        
        assertNull(tree);
    }
    
    /**
     * Method: match(State)
     * 
     * Case: The rule fails to match, because of the tail.
     */
    @Test
    public void testMatch_failTail()
    {
        /*
         * root = x : y;
         * 
         * x = 'a';
         * 
         * y = 'b';
         */
        final GrammarBuilder g = new GrammarBuilder();
        g.setRoot("root");
        
        // Lexical Syntax
        g.range("LETTER1", 'a');
        g.range("LETTER2", 'b');
        
        // Grammatical Syntax
        g.sequenceDLR("root", "x", "y");
        g.chr("x", "LETTER1");
        g.chr("y", "LETTER2");
        
        final Parser p = g.build().newParser();
        
        final ParserOutput out = p.parse("az");
        
        final ITreeNode tree = out.parseTree();
        
        assertNull(tree);
    }    
    
}
