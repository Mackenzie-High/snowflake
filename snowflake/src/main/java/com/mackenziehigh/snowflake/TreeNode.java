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

/**
 * An instance of this class is a node in the parse-tree data-structure created via parsing. 
 * 
 * @author Michael Mackenzie High
 */
public final class TreeNode extends AbstractTreeNode
{

    /**
     * This field becomes true, when this object is initialized.
     */
    private boolean initialized = false;
    
    /**
     * This array stores the children of this tree-node, if any.
     * 
     * <p>This field is set during the initialization of this object.</p>
     */
    private TreeNode[] children;

    /**
     * This is the rule that caused this node to be created.
     * 
     * <p>This field is set during the initialization of this object.</p
     */
    private Rule rule;

    /**
     * This is a reference to the character-array being parsed by the parser.
     * 
     * <p>This field is set during the initialization of this object.</p
     */
    private char[] input;

    /**
     * This is the index of the first character in the matched region of input.
     * 
     * <p>This field is set during the initialization of this object.</p
     */
    private int textStart;

    /**
     * This is the length of the matched region of input.
     * 
     * <p>This field is set during the initialization of this object.</p
     */
    private int textLength;
    
    /** 
     * This method initializes this object.
     * 
     * @param rule is the rule that is creating this tree-node.
     * @param input is the <code>char[]</code> that is being parsed.
     * @param start is the index of the first character in the matched region of input.
     * @param length is the numbers of characters in the matched region of input.
     * @throws IllegalArgumentException if <code>start &lt; 0</code>.
     * @throws IllegalArgumentException if <code>length &lt; 0</code>.
     * @throws IllegalArgumentException if <code>(start + length) &gt; input.length</code>
     */
    void initialize(final Rule rule,
                    final char[] input,
                    final int start,
                    final int length)
    {   
        Utils.checkNonNull(rule);
        Utils.checkNonNull(input);
        
        if(start < 0) { throw new IllegalArgumentException("start < 0"); }
        
        if(length < 0) { throw new IllegalArgumentException("length < 0"); }
        
        if(start + length > input.length)
        {
            throw new IllegalArgumentException("start + length > input.length");
        }
     
        this.initialized = true;
        this.rule = rule;
        this.input = input;
        this.textStart = start;
        this.textLength = length; 
    }
    
    /**
     * This method initializes this object.
     * 
     * @param rule is the rule that is creating this tree-node.
     * @param input is the <code>char[]</code> that is being parsed.
     * @param children are the child-nodes of this node in the syntax-tree.
     * @throws IllegalArgumentException if <code>children.length == 0</code>.
     */    
    void initialize(final Rule rule,
                    final char[] input,
                    final TreeNode[] children)
    {
        Utils.checkNonNull(rule);
        Utils.checkNonNull(input);
        Utils.checkNonNullArray(children);
        
        if(children.length == 0) { throw new IllegalArgumentException("children.length == 0"); }
        
        final TreeNode last = children[children.length - 1];
        
        this.initialized = true;
        this.rule = rule;
        this.input = input;
        this.children = children;
        this.textStart = children[0].textStart;
        this.textLength = (last.start() + last.length()) - this.textStart;
    }    

    private void requireInitialization() 
    { 
        if(!initialized) { throw new IllegalStateException("uninitialized object"); } 
    }
    
    /**
     * {@inheritDoc}
     */ 
    @Override 
    public final TreeNode[] children()
    {
        requireInitialization();
        
        return children == null 
             ? new TreeNode[0] 
             : children;
    }

    /**
     * {@inheritDoc}
     */ 
    @Override
    public final String rule()
    {
        requireInitialization();
        
        return rule.name();
    }

    /**
     * {@inheritDoc}
     */ 
    @Override
    public final int start()
    {
        requireInitialization();
        
        return textStart;
    }

    /**
     * {@inheritDoc}
     */ 
    @Override
    public final int length()
    {
        requireInitialization();
        
        return textLength;
    }

    /**
     * {@inheritDoc}
     */ 
    @Override
    public final char[] input()
    {
        requireInitialization();
        
        return input;
    }
    
    /**
     * {@inheritDoc}
     */ 
    @Override
    public Iterable<ITreeNode> iterableDFS()
    {
        requireInitialization();
        
        return super.iterableDFS();
    }

    /**
     * {@inheritDoc}
     */ 
    @Override
    public Iterable<ITreeNode> iterableBFS()
    {
        requireInitialization();
        
        return super.iterableBFS();
    }    
    
    /**
     * {@inheritDoc}
     */ 
    @Override
    public Iterable<ITreeNode> iterableLeavesFirst()
    {
        requireInitialization();
        
        return super.iterableLeavesFirst();
    } 
}
