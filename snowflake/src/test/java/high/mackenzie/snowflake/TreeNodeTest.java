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

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public final class TreeNodeTest 
{  
 
    /**
     * Method: <code>initialize(Rule, char[], int, int)</code>
     * 
     * Case: The first argument is null.
     */
    @Test (expected = NullPointerException.class)
    public void testInitializeRCII_firstArgNull()
    {
        final TreeNode node = new TreeNode();
        
        final Rule rule = null;
        
        final char[] input = "abcdef".toCharArray();
        
        final int start = 1;
        
        final int length = 2;
        
        node.initialize(rule, input, start, length);
    }
    
    /**
     * Method: <code>initialize(Rule, char[], int, int)</code>
     * 
     * Case: The second argument is null.
     */
    @Test (expected = NullPointerException.class)
    public void testInitializeRCII_secondArgNull()
    {
        final TreeNode node = new TreeNode();
        
        final Rule rule = new RuleString("str1");
        
        final char[] input = null;
        
        final int start = 1;
        
        final int length = 2;
        
        node.initialize(rule, input, start, length);
    }    
    
    /**
     * Method: <code>initialize(Rule, char[], int, int)</code>
     * 
     * Case: [start] is less-than zero.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testInitializeRCII_startLessThanZero()
    {
        final TreeNode node = new TreeNode();
        
        final Rule rule = new RuleString("str1");
        
        final char[] input = "abcdef".toCharArray();
        
        final int start = -1;
        
        final int length = 2;
        
        node.initialize(rule, input, start, length);
    }  
    
    /**
     * Method: <code>initialize(Rule, char[], int, int)</code>
     * 
     * Case: [length] is less-than zero.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testInitializeRCII_lengthLessThanZero()
    {
        final TreeNode node = new TreeNode();
        
        final Rule rule = new RuleString("str1");
        
        final char[] input = "abcdef".toCharArray();
        
        final int start = 1;
        
        final int length = -1;
        
        node.initialize(rule, input, start, length);
    }       
    
    /**
     * Method: <code>initialize(Rule, char[], int, int)</code>
     * 
     * Case: ([start] + [length]) is greater-than [input].length
     */
    @Test (expected = IllegalArgumentException.class)
    public void testInitializeRCII_startPlusLengtTooLarge()
    {
        final TreeNode node = new TreeNode();
        
        final Rule rule = new RuleString("str1");
        
        final char[] input = "abc".toCharArray();
        
        final int start = 1;
        
        final int length = 3;
        
        node.initialize(rule, input, start, length);
    }     
    
    /**
     * Method: <code>initialize(Rule, char[], TreeNode[])</code>
     * 
     * Case: The first argument is null.
     */
    @Test (expected = NullPointerException.class)
    public void testInitializeRCT_firstArgNull()
    {
        final TreeNode node = new TreeNode();
        
        final TreeNode kid0 = new TreeNode();
        
        final TreeNode kid1 = new TreeNode();
        
        final Rule rule1 = new RuleSequence("seq1");
        
        final Rule rule2 = new RuleCharacter("chr1");
        
        final char[] input = "ab".toCharArray();
        
        final TreeNode[] kids = new TreeNode[] { kid0, kid1 };
        
        kid0.initialize(rule2, input, 0, 1);
        
        kid1.initialize(rule2, input, 1, 1);
        
        node.initialize(null, input, kids);
    }
    
    /**
     * Method: <code>initialize(Rule, char[], TreeNode[])</code>
     * 
     * Case: The second argument is null.
     */
    @Test (expected = NullPointerException.class)
    public void testInitializeRCT_secondArgNull()
    {
        final TreeNode node = new TreeNode();
        
        final TreeNode kid0 = new TreeNode();
        
        final TreeNode kid1 = new TreeNode();
        
        final Rule rule1 = new RuleSequence("seq1");
        
        final Rule rule2 = new RuleCharacter("chr1");
        
        final char[] input = "ab".toCharArray();
        
        final TreeNode[] kids = new TreeNode[] { kid0, kid1 };
        
        kid0.initialize(rule2, input, 0, 1);
        
        kid1.initialize(rule2, input, 1, 1);
        
        node.initialize(rule1, null, kids);
    }   
    
    /**
     * Method: <code>initialize(Rule, char[], TreeNode[])</code>
     * 
     * Case: The third argument is null.
     */
    @Test (expected = NullPointerException.class)
    public void testInitializeRCT_thirdArgNull()
    {
        final TreeNode node = new TreeNode();
        
        final Rule rule1 = new RuleSequence("seq1");
        
        final char[] input = "ab".toCharArray();
        
        node.initialize(rule1, input, null);
    }    
    
    /**
     * Method: <code>initialize(Rule, char[], TreeNode[])</code>
     * 
     * Case: The third argument is an array with zero elements.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testInitializeRCT_emptyChildArray()
    {
        final TreeNode node = new TreeNode();
        
        final Rule rule1 = new RuleSequence("seq1");
        
        final char[] input = "ab".toCharArray();
        
        final TreeNode[] kids = new TreeNode[] { };
        
        node.initialize(rule1, input, kids);
    }    
    
    /**
     * Method: <code>text()</code>
     * 
     * Case: The text is an empty string.
     */
    @Test
    public void testGetText_emptyString()
    {
        final TreeNode node = new TreeNode();
        
        node.initialize(new RuleString("moo"), "abcdef".toCharArray(), 2, 0);
        
        final String result = node.text();
        
        assertEquals("", result);
    }
    
    /**
     * Method: <code>text()</code>
     * 
     * Case: The text is NOT an empty string.
     */
    @Test
    public void testGetText_nonEmptyString()
    {
        final TreeNode node = new TreeNode();
        
        node.initialize(new RuleString("moo"), "abcdef".toCharArray(), 2, 3);
        
        final String result = node.text();
        
        assertEquals("cde", result);
    }    
    
    /**
     * Method: <code>text()</code>
     * 
     * Case: The object has not been initialized. 
     */
    @Test (expected = IllegalStateException.class)
    public void testGetText_nonInitialized()
    {
        final TreeNode node = new TreeNode();
        
        node.text();
    }  
    
    /**
     * Method: <code>childCount()</code>
     * 
     * Case: no children
     */
    @Test
    public void testGetChildCount_noChildren()
    {
        final TreeNode node = new TreeNode();
        final Rule rule = new RuleString("moo");
        final char[] input = "abcde".toCharArray();
        
        node.initialize(rule, input, 0, 3);
        
        final int result = node.childCount();
        
        assertEquals(0, result);
    }
    
    /**
     * Method: <code>childCount()</code>
     * 
     * Case: multiple children
     */
    @Test
    public void testGetChildCount_multipleChildren()
    {
        final TreeNode node = new TreeNode();
        final Rule rule = new RuleString("moo");
        final char[] input = "abcde".toCharArray();
        final TreeNode[] children = new TreeNode[2];
        children[0] = new TreeNode();
        children[1] = new TreeNode();
        children[0].initialize(rule, input, 1, 1);
        children[1].initialize(rule, input, 2, 1);
        
        node.initialize(rule, input, children);
        
        final int result = node.childCount();
        
        assertEquals(2, result);
    }    
    
    /**
     * Method: <code>childCount()</code>
     * 
     * Case: The object has not been initialized yet.
     */
    @Test (expected = IllegalStateException.class)
    public void testGetChildCount_uninitializedObject()
    {
        final TreeNode node = new TreeNode();
        
        node.childCount();
    }   
    
    /**
     * Method: <code>childAt(int)</code>
     * 
     * Case: The given [index] is out-of-bounds.
     */
    @Test (expected = IndexOutOfBoundsException.class)
    public void testGetChild_indexOutOfBounds()
    {
        final TreeNode node = new TreeNode();
        final Rule rule = new RuleString("moo");
        final char[] input = "abcde".toCharArray();
        final TreeNode[] children = new TreeNode[2];
        children[0] = new TreeNode();
        children[1] = new TreeNode();
        children[0].initialize(rule, input, 1, 1);
        children[1].initialize(rule, input, 2, 1);
        
        node.initialize(rule, input, children);
        
        node.childAt(2);
    }    
    
    /**
     * Method: <code>childAt(int)</code>
     * 
     * Case: The object has not been initialized.
     */
    @Test (expected = IllegalStateException.class)
    public void testGetChild_uninitializedObject()
    {
        final TreeNode node = new TreeNode();
        
        node.childAt(0);
    }     
    
    /**
     * Method: <code>childAt(int)</code>
     * 
     * Case: normal
     */
    @Test 
    public void testGetChild_normal()
    {
        final TreeNode node = new TreeNode();
        final Rule rule = new RuleString("moo");
        final char[] input = "abcde".toCharArray();
        final TreeNode[] children = new TreeNode[2];
        children[0] = new TreeNode();
        children[1] = new TreeNode();
        children[0].initialize(rule, input, 1, 1);
        children[1].initialize(rule, input, 2, 1);
        
        node.initialize(rule, input, children);
        
        final ITreeNode result = node.childAt(1);
        
        assertTrue(result == children[1]);
    }     
    
    /**
     * Method: <code>children()</code>
     * 
     * Case: no children
     */
    @Test 
    public void testGetChildren_noChildren()
    {
        final TreeNode node = new TreeNode();
        final Rule rule = new RuleString("moo");
        final char[] input = "abcde".toCharArray();

        node.initialize(rule, input, 0, 3);
        
        final TreeNode[] result = node.children();
        
        assertEquals(0, result.length);
    }  
    
    /**
     * Method: <code>children()</code>
     * 
     * Case: multiple children
     */
    @Test 
    public void testGetChildren_multipleChildren()
    {
        final TreeNode node = new TreeNode();
        final Rule rule = new RuleString("moo");
        final char[] input = "abcde".toCharArray();
        final TreeNode[] children = new TreeNode[2];
        children[0] = new TreeNode();
        children[1] = new TreeNode();
        children[0].initialize(rule, input, 1, 1);
        children[1].initialize(rule, input, 2, 1);        
        
        node.initialize(rule, input, children);
        
        final TreeNode[] result = node.children();
        
        assertEquals(2, result.length);
        assertTrue(result == children);
    }    
    
    /**
     * Method: <code>children()</code>
     * 
     * Case: The object has not been initialized. 
     */
    @Test (expected = IllegalStateException.class)
    public void testGetChildren_uninitializedObject()
    {
        final TreeNode node = new TreeNode();

        node.children();
    } 
    
    /**
     * Method: <code>rule()</code>
     * 
     * Case: normal
     */
    @Test
    public void testGetRule_normal()
    {
        final TreeNode node = new TreeNode();
        final Rule rule = new RuleString("moo");
        final char[] input = "abcde".toCharArray();
        
        node.initialize(rule, input, 2, 1);
        
        final String result = node.rule();
        
        assertEquals(rule.name(), result);
    }
    
    /**
     * Method: <code>rule()</code>
     * 
     * Case: uninitialized object
     */
    @Test (expected = IllegalStateException.class)
    public void testGetRule_uninitializedObject()
    {
        final TreeNode node = new TreeNode();
        
        node.rule();
    }  
    
    /**
     * Method: <code>input()</code>
     * 
     * Case: normal
     */
    @Test
    public void testInput_normal()
    {
        final TreeNode node = new TreeNode();
        final Rule rule = new RuleString("moo");
        final char[] input = "abcde".toCharArray();
        
        node.initialize(rule, input, 2, 1);
        
        final char[] result = node.input();
        
        assertTrue(result == input);
    }    
    
    /**
     * Method: <code>input()</code>
     * 
     * Case: uninitialized object
     */
    @Test (expected = IllegalStateException.class)
    public void testInput_uninitializedObject()
    {
        final TreeNode node = new TreeNode();
        
        node.input();
    }      
    
    /**
     * Method: <code>start()</code>
     * 
     * Case: The tree-node is a terminal.
     */
    @Test
    public void testGetTextStartPosition_terminal()
    {
        final TreeNode node = new TreeNode();
        final Rule rule = new RuleString("moo");
        final char[] input = "abcde".toCharArray();
        
        node.initialize(rule, input, 2, 1);
        
        final int result = node.start();
        
        assertTrue(result == 2);
    }    
    
    /**
     * Method: <code>start()</code>
     * 
     * Case: The tree-node is a non-terminal.
     */
    @Test
    public void testGetTextStartPosition_nonTerminal()
    {
        final TreeNode node = new TreeNode();
        final Rule rule = new RuleString("moo");
        final char[] input = "abcde".toCharArray();
        final TreeNode[] children = new TreeNode[2];
        children[0] = new TreeNode();
        children[1] = new TreeNode();     
        children[0].initialize(rule, input, 2, 1);
        
        node.initialize(rule, input, 2, 1);
        
        final int result = node.start();
        
        assertTrue(result == 2);
    }   
    
    /**
     * Method: <code>start()</code>
     * 
     * Case: The tree-node is a non-terminal.
     */
    @Test (expected = IllegalStateException.class)
    public void testGetTextStartPosition_uninitializedObject()
    {
        final TreeNode node = new TreeNode();
        
        node.start();
    }      
    
    /**
     * Method: <code>length()</code>
     * 
     * Case: The tree-node is a zero-width terminal.
     */
    @Test
    public void testGetTextLength_zeroWidthTerminal()
    {
        final TreeNode node = new TreeNode();
        final Rule rule = new RuleString("moo");
        final char[] input = "abcde".toCharArray();
        
        node.initialize(rule, input, 2, 0);
        
        final int result = node.length();
        
        assertTrue(result == 0);
    }      
    
    /**
     * Method: <code>length()</code>
     * 
     * Case: The tree-node is a zero-width non-terminal.
     */
    @Test
    public void testGetTextLength_zeroWidthNonTerminal()
    {
        final TreeNode node = new TreeNode();
        final Rule rule = new RuleString("moo");
        final char[] input = "abcde".toCharArray();
        final TreeNode[] children = new TreeNode[1];
        children[0] = new TreeNode();
        children[0].initialize(rule, input, 3, 0);
        
        node.initialize(rule, input, children);
        
        final int result = node.length();
        
        assertTrue(result == 0);
    }    
    
    /**
     * Method: <code>length()</code>
     * 
     * Case: The tree-node is a non-zero-width terminal.
     */
    @Test
    public void testGetTextLength_nonZeroWidthTerminal()
    {
        final TreeNode node = new TreeNode();
        final Rule rule = new RuleString("moo");
        final char[] input = "abcde".toCharArray();
        
        node.initialize(rule, input, 2, 3);
        
        final int result = node.length();
        
        assertTrue(result == 3);
    }    
    
    /**
     * Method: <code>length()</code>
     * 
     * Case: The tree-node is a non-zero-width single-child non-terminal.
     */
    @Test
    public void testGetTextLength_nonZeroWidthSingleChildTerminal()
    {
        final TreeNode node = new TreeNode();
        final Rule rule = new RuleString("moo");
        final char[] input = "abcde".toCharArray();
        final TreeNode[] children = new TreeNode[1];
        children[0] = new TreeNode();
        children[0].initialize(rule, input, 1, 3);
        
        node.initialize(rule, input, children);
        
        final int result = node.length();
        
        assertTrue(result == 3);
    }   
    
    /**
     * Method: <code>length()</code>
     * 
     * Case: The tree-node is a non-zero-width multi-child non-terminal.
     */
    @Test
    public void testGetTextLength_nonZeroWidthMultiChildTerminal()
    {
        final TreeNode node = new TreeNode();
        final Rule rule = new RuleString("moo");
        final char[] input = "abcdefghi".toCharArray();
        final TreeNode[] children = new TreeNode[2];
        children[0] = new TreeNode();
        children[0].initialize(rule, input, 2, 3);
        children[1] = new TreeNode();
        children[1].initialize(rule, input, 5, 2);
        
        node.initialize(rule, input, children);
        
        final int result = node.length();
        
        assertEquals(5, result);
    } 
    
    /**
     * Method: <code>length()</code>
     * 
     * Case: uninitialized object
     */
    @Test (expected = IllegalStateException.class)
    public void testGetTextLength_uninitializedObject()
    {
        final TreeNode node = new TreeNode();
        
        node.length();
    }    
    
    /**
     * Method: <code>toString()</code>
     * 
     * Case: normal
     */
    @Test
    public void testToString()
    {
        final TreeNode node = new TreeNode();
        final Rule rule = new RuleString("moo");
        final char[] input = "abcde\r\nfghij".toCharArray();
        
        node.initialize(rule, input, 0, input.length);
        
        final String result = node.toString();
        
        assertEquals(new String(input), result);
    }
    
    /**
     * Test: 20130203210145258673
     *
     * <p>
     * Case: iterator(), iteratorBFS(), iteratorDFS()
     * </p>
     */
    @Test
    public void test20130203210145258673()
    {
        System.out.println("Test: 20130203210145258673");
        
        final TreeNode node = new TreeNode();
        
        node.initialize(new RuleString("xoo"), "abcdefghi".toCharArray(), 2, 1);
        
        assertFalse(node.iterator() == null);
        assertFalse(node.iterableDFS() == null);
        assertFalse(node.iterableBFS() == null);
        assertFalse(node.iterableDFS().iterator() == null);
        assertFalse(node.iterableBFS().iterator() == null);
        
        assertTrue(node.iterator() instanceof IteratorBFS);
        assertTrue(node.iterableBFS().iterator() instanceof IteratorBFS);
        assertTrue(node.iterableDFS().iterator() instanceof IteratorDFS);
    }
    
    /**
     * Test: 20130203210748491929
     *
     * <p>
     * Case: iterator(), when uninitialized.
     * </p>
     */
    @Test(expected = IllegalStateException.class)
    public void test20130203210748491929()
    {
        System.out.println("Test: 20130203210748491929");
        
        final TreeNode node = new TreeNode();
        
        node.iterator();
    }
    
    /**
     * Test: 20130203210748492020
     *
     * <p>
     * Case: iterableBFS(), when uninitialized.
     * </p>
     */
    @Test(expected = IllegalStateException.class)
    public void test20130203210748492020()
    {
        System.out.println("Test: 20130203210748492020");
        
        final TreeNode node = new TreeNode();
        
        node.iterableBFS();
    }
    
    /**
     * Test: 20130203210748492073
     *
     * <p>
     * Case: iterableDFS(), when uninitialized.
     * </p>
     */
    @Test(expected = IllegalStateException.class)
    public void test20130203210748492073()
    {
        System.out.println("Test: 20130203210748492073");
        
        final TreeNode node = new TreeNode();
        
        node.iterableDFS();
    }
    
    /**
     * Test: 20130412014715987255
     * 
     * <p>
     * Method: <code>find(Iterable iterable, String name)</code>
     * </p>
     *
     * <p>
     * Case: A tree-node was found. 
     * </p>
     */
     @Test
     public void test20130412014715987255()
     {
         System.out.println("Test: 20130412014715987255");
         
         final GrammarBuilder g = new GrammarBuilder();
         
         g.setRoot("root");
         g.sequence("root", "X", "Y", "Z", "X");
         g.chr("X", "LETTER");
         g.chr("Y", "LETTER");
         g.chr("Z", "LETTER");
         g.range("LETTER", 'A', 'Z');
         
         final Parser p = g.build().newParser();
         
         final ParserOutput out = p.parse("ABCD");
         
         final ITreeNode tree = out.parseTree();
         
         assertEquals("A", TreeNode.find(tree.iterableBFS(), "X").text());
         assertEquals("B", TreeNode.find(tree.iterableBFS(), "Y").text());
         assertEquals("C", TreeNode.find(tree.iterableBFS(), "Z").text());
         assertEquals("A", TreeNode.find(tree.iterableBFS(), "X").text());
     }
     
    /**
     * Test: 20130412014715987381
     * 
     * <p>
     * Method: <code>find(Iterable iterable, String name)</code>
     * </p>
     *
     * <p>
     * Case: A tree-node was not found. 
     * </p>
     */
     @Test
     public void test20130412014715987381()
     {
         System.out.println("Test: 20130412014715987381");
         
         final GrammarBuilder g = new GrammarBuilder();
         
         g.setRoot("root");
         g.sequence("root", "X", "Y", "Z", "X");
         g.chr("X", "LETTER");
         g.chr("Y", "LETTER");
         g.chr("Z", "LETTER");
         g.range("LETTER", 'A', 'Z');
         
         final Parser p = g.build().newParser();
         
         final ParserOutput out = p.parse("ABCD");
         
         final ITreeNode tree = out.parseTree();
         
         assertEquals(null, TreeNode.find(tree.iterableBFS(), "M"));
     }
     
    /**
     * Test: 20130412014715987432
     * 
     * <p>
     * Method: <code>findAll(Iterable iterable, String name)</code>
     * </p>
     *
     * <p>
     * Case: Tree-nodes were found. 
     * </p>
     */
     @Test
     public void test20130412014715987432()
     {
         System.out.println("Test: 20130412014715987432");
         
         final GrammarBuilder g = new GrammarBuilder();
         
         g.setRoot("root");
         g.sequence("root", "X", "Y", "Z", "X");
         g.chr("X", "LETTER");
         g.chr("Y", "LETTER");
         g.chr("Z", "LETTER");
         g.range("LETTER", 'A', 'Z');
         
         final Parser p = g.build().newParser();
         
         final ParserOutput out = p.parse("ABCD");
         
         final ITreeNode tree = out.parseTree();
         
         final List<ITreeNode> actual = TreeNode.findAll(tree.iterableBFS(), "X");
         
         assertEquals("A", actual.get(0).text());
         assertEquals("D", actual.get(1).text());
     }
     
    /**
     * Test: 20130412014715987476
     * 
     * <p>
     * Method: <code>findAll(Iterable iterable, String name)</code>
     * </p>
     *
     * <p>
     * Case: No tree-nodes were found. 
     * </p>
     */
     @Test
     public void test20130412014715987476()
     {
         System.out.println("Test: 20130412014715987476");
         
         final GrammarBuilder g = new GrammarBuilder();
         
         g.setRoot("root");
         g.sequence("root", "X", "Y", "Z", "X");
         g.chr("X", "LETTER");
         g.chr("Y", "LETTER");
         g.chr("Z", "LETTER");
         g.range("LETTER", 'A', 'Z');
         
         final Parser p = g.build().newParser();
         
         final ParserOutput out = p.parse("ABCD");
         
         final ITreeNode tree = out.parseTree();
         
         final List<ITreeNode> actual = TreeNode.findAll(tree.iterableBFS(), "M");
         
         assertEquals(0, actual.size());
     }    
}
