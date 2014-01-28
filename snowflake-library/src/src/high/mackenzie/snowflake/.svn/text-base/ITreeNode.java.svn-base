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

import java.util.Iterator;

/**
 * An instance of this class is a node in a syntax-tree. 
 * 
 * @author Mackenzie High
 */
public interface ITreeNode extends Iterable<ITreeNode>
{

    /**
     * This method returns the child-node of this node at a given index.
     * 
     * <p> Zero is the index of the left-most child of this node, if this node has children. </p>
     * 
     * @param index is the index of the child-node to return.
     * @return the child-node at <code>index</code> in the array of children.
     * @throws IndexOutOfBoundsException if no child-node is at the given <code>index</code>.
     */    
    public ITreeNode childAt(final int index);

    /**
     * This method returns the number of children of this node. 
     * 
     * @return the number of children that this node has, or zero, if this node is a leaf node.
     */    
    public int childCount();

    /**
     * This method returns an array that contains the children of this node.
     *
     * <p> <b>Warning:</b> Do NOT modify the returned array! </p>
     *
     * @return the children of this node, or an empty array, if this node has no children.
     */    
    public ITreeNode[] children();

    /**
     * This method returns the name of the rule that created this node.
     * 
     * @return the aforedescribed name.
     */
    public String rule();

    /**
     * This method returns the start index of the region of the input that this node covers.
     * 
     * @return the index of the first character in the matched region of the parser's input.
     */
    public int start();

    /**
     * This method returns the length of the region of the input that this node covers.
     * 
     * @return the length of the matched region of the parser's input.
     */
    public int length();

    /**
     * This method returns the text from the matched region of input that this node covers.
     * 
     * @return the textual form of the region of input that is covered by this node. 
     */
    public String text();

    /**
     * This method returns the <code>char[]</code> that is the parser's input.
     * 
     * <p> <b>Warning:</b> Do NOT modify the returned array. </p>
     * 
     * @return the <code>char[]</code> that backs this object.
     */
    public char[] input();
    
    /**
     * This method returns a depth-first-search iterable over the tree rooted at this node. 
     * 
     * @return the aforedescribed iterator. 
     */
    public Iterable<ITreeNode> iterableDFS();

    /**
     * This method returns a breadth-first-search iterable over the tree rooted at this node. 
     * 
     * @return the aforedescribed iterator. 
     */
    public Iterable<ITreeNode> iterableBFS();   
    
    /**
     * This method returns a leaves-first iterable over the tree rooted at this node. 
     * 
     * <p>In other words, this iterator visits all-leafs nodes first, from left to right.</p>
     * 
     * @return the aforedescribed iterator. 
     */
    public Iterable<ITreeNode> iterableLeavesFirst();
    
    /**
     * This method returns a depth-first-search iterator over the tree rooted at this node. 
     * 
     * @return <code>iteratorDFS()</code>
     */
    public Iterator<ITreeNode> iterator();
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString();
}
