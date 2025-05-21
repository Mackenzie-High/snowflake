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

import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

public final class IteratorDFSTest 
{
    
    /**
     * Test: 20130203173802103928
     *
     * <p>
     * Case: One Element Tree
     * </p>
     */
    @Test
    public void test20130203200725792777()
    {
        System.out.println("Test: 20130203200725792777");
        
        final GrammarBuilder g = new GrammarBuilder();
        
        g.str("root", "ABC");
        
        g.setRoot("root");
        
        final Parser p = g.build().newParser();
        
        final ParserOutput out = p.parse("ABC");
        
        final ITreeNode root = out.parseTree();
        
        final Iterator<ITreeNode> iter = root.iterableDFS().iterator();
        
        ITreeNode node = null;
        
        assertTrue(iter.hasNext());
        node = iter.next();
        assertEquals("root", node.rule());
        assertEquals("ABC", node.text());
        
        assertFalse(iter.hasNext());
    }
    
    /**
     * Test: 20130203173802104038
     *
     * <p>
     * Case: Multiple Element Tree
     * </p>
     */
    @Test
    public void test20130203200725792895()
    {
        System.out.println("Test: 20130203200725792895");
        
        final GrammarBuilder g = new GrammarBuilder();
        
        g.sequence("x1", "y1", "y2");
            g.str("y1", "AA");
            g.str("y2", "BB");
        g.sequence("x2", "y3", "y4");
            g.str("y3", "CC");
            g.str("y4", "DD");
            
        g.sequence("root", "x1", "x2");
        
        g.setRoot("root");
        
        final Parser p = g.build().newParser();
        
        final ParserOutput out = p.parse("AABBCCDD");
        
        final ITreeNode root = out.parseTree();
        
        final Iterator<ITreeNode> iter = root.iterableDFS().iterator();
        
        ITreeNode node = null;
        
        assertTrue(iter.hasNext());
        node = iter.next();
        assertEquals("root", node.rule());
        assertEquals("AABBCCDD", node.text());

        assertTrue(iter.hasNext());
        node = iter.next();
        assertEquals("x1", node.rule());
        assertEquals("AABB", node.text());
        
        assertTrue(iter.hasNext());
        node = iter.next();
        assertEquals("y1", node.rule());
        assertEquals("AA", node.text());
        
        assertTrue(iter.hasNext());
        node = iter.next();
        assertEquals("y2", node.rule());
        assertEquals("BB", node.text());
        
        assertTrue(iter.hasNext());
        node = iter.next();
        assertEquals("x2", node.rule());
        assertEquals("CCDD", node.text());

        assertTrue(iter.hasNext());
        node = iter.next();
        assertEquals("y3", node.rule());
        assertEquals("CC", node.text());
        
        assertTrue(iter.hasNext());
        node = iter.next();
        assertEquals("y4", node.rule());
        assertEquals("DD", node.text());
        
        assertFalse(iter.hasNext());
    }
    
    /**
     * Test: 20130203173802104087
     *
     * <p>
     * Case: remove() method
     * </p>
     */
    @Test(expected = UnsupportedOperationException.class)
    public void test20130203200725792945()
    {
        System.out.println("Test: 20130203200725792945");
        
        final TreeNode node = new TreeNode();
        
        node.initialize(new RuleString("xoo"), "abcdefghi".toCharArray(), 2, 1);
        
        final Iterator<ITreeNode> iter = node.iterableDFS().iterator();
        
        iter.remove();
    }
}
