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

/**
 * An instance of this interface is a visitor that visits a node in a syntax-tree. 
 * 
 * @author Mackenzie High
 */
public interface ITreeNodeVisitor 
{
    /**
     * This method performs the visitation of a given node in a syntax-tree.
     * 
     * @param node is the syntax-tree node to visit. 
     */
    public void visit(ITreeNode node);
    
    /**
     * This method is invoked by the <code>visit(ITreeNode)</code> method,
     * if the method does not recognize the node that it is attempting to visit.
     * 
     * <p>
     * It is acceptable and often useful for this method to simply recursively invoke 
     * the <code>visit(ITreeNode)</code> method on the children of the unrecognized node. 
     * Such an implementation can be used to create a recursive tree-transversal. 
     * </p>
     * 
     * @param node is the node that cannot be visited. 
     */
    public void visitUnknown(ITreeNode node);
}
