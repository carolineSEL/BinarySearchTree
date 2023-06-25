
import java.util.Queue;
import java.util.LinkedList;
/*

The BinaryTree Class

@author Autumn C. Spaulding <a href="mailto:autumn@max.cs.kzoo.edu">email</a>
Creation Date: 24 July 2000

Modifications:
Modifier: Alyce Brady
Modification Date: November 11, 2002
Modifications Made: Modifications to documentation (e.g., to remove
empty preconditions); added levelOrderTraversal;
also modified to use NodeAction interface.
Modifier: Nathan Sprague
Modification Date: May 10, 2010
Modifications Made: Modified to use Java Queue interface.

Modifications:
Modifier: Caroline Lamb
Modification Date: 11/13/2021
Modifications Made:

Description:
This file contains some of the implementation of a BinaryTree class. 
It is intended as an outline and starting point for the "Binary Trees"
lab in the Data Structures course.  The implementation is based on the 
recursive definition of a tree rather than on the graph theory definition
of a tree (compare to Bailey, 190).

A binary tree is either:
1.  An empty tree; or
2.  a node, called a root (the node contains the data), and two 
children, left and right, each of which are themselves binary trees.
(Berman, "Data Structures via C++: Objects by Evolution", 1997.)

In this implementation, an empty tree is represented by a node with null
data and null references for the children.  A leaf node is represented by
a node with a data value and two references to empty trees (NOT a data
value and two null references!).  We could represent this as an object
invariant: data, left, right are either all null (representing an empty
tree) or none of them are null (a non-empty tree).

 */

public class BinaryTree
{
    //data:
    protected Object data;
    protected BinaryTree left;
    protected BinaryTree right;

    /*tested*/
    /** Creates an empty binary tree with no data and no children. */
    public BinaryTree()
    {
        //this is the contructor for the BinaryTree object
        data = null;
        left = null;
        right = null;
    }

    /*tested*/
    /** Tests whether this is an empty tree.
    @return true if the tree is empty; false otherwise
     */
    public boolean isEmpty()
    {
        return data == null;
    }

    /*tested*/
    /** Gets the data associated with the root node of this particular tree
    (recall recursive definition of trees).
    @return value associated with tree's root node; 
    null if tree is empty
     */
    public Object getElement()
    {
        return data;
    }

    /*tested*/
    /** Gets the left child of the current tree.
    @return the left child (a tree)
     */
    public BinaryTree leftTree()
    {
        return left;
    }

    /*tested*/
    /** Gets the right child of the current tree.
    @return the right child (a tree)
     */
    public BinaryTree rightTree()
    {
        return right;
    }

    /** Adds elements to a binary tree.  This implementation adds the
    elements in breadth-first (top-down, left-to-right) order.
    @param value the value to be added to the tree.
    @return true when the value has been added; should never return false
     */
    public boolean add(Object value)
    {
        Queue<Object> queue = new LinkedList<Object>();
        queue.add(this);
        while( ! queue.isEmpty() )
        {
            BinaryTree tree = (BinaryTree)queue.remove();

            //if the current position is null, then we know we can place a
            //value here.
            //place the value in that position in the tree, and create new 
            //BinaryTrees for its children, which will both initially be null.
            if (tree.isEmpty())
            {
                tree.data = value;
                tree.left = new BinaryTree();
                tree.right = new BinaryTree();
                return true;
            }
            //otherwise, if the position is not null (that is, we can't place
            //it at the current position), then we add the left and right children
            //to the queue (if we can) and go back to the beginning of the loop.
            queue.add(tree.left);
            queue.add(tree.right);
        }
        return false;    //this statement should never be executed.
    }

    /** Traverses the tree in breadth-first order.
    @param action an object that will act on all the nodes in the tree
     */
    public void breadthFirstTraversal(NodeVisitor action)
    {
        Queue<Object> queue = new LinkedList<Object>();
        queue.add(this);
        while( ! queue.isEmpty() )
        {
            BinaryTree tree = (BinaryTree)queue.remove();
            if ( ! tree.isEmpty() )
            {
                action.visit(tree.getElement());
                queue.add(tree.leftTree());
                queue.add(tree.rightTree());
            }
        }
    } 

    /** Traverses the tree in pre-order.
    @param action an object that will act on all the nodes in the tree
     */
    public void preOrderTraversal(NodeVisitor action)
    {
        // base case
        if ( !this.isEmpty() )
        {
            // visit the root of the tree.
            action.visit(getElement());
            // visit the rest of the tree
            this.leftTree().preOrderTraversal(action);
            this.rightTree().preOrderTraversal(action);
        }
    }

    /** Traverses the tree in order.
    @param action an object that will act on all the nodes in the tree
     */
    public void inOrderTraversal(NodeVisitor action)
    {
        // base case.
        if ( !this.isEmpty() )
        {
            // visit the left tree, then the root, then the right tree.
            this.leftTree().inOrderTraversal(action);
            action.visit(getElement());
            this.rightTree().inOrderTraversal(action);
        }
    }
    
    /** Traverses the tree in post-order.
    @param action an object that will act on all the nodes in the tree
     */
    public void postOrderTraversal(NodeVisitor action)
    {
        // base case
        if ( !this.isEmpty() )
        {
            // visit the left tree, then the right tree, then the root
            this.leftTree().postOrderTraversal(action);
            this.rightTree().postOrderTraversal(action);
            action.visit(getElement());
        }
    }

    /** Checks whether this node is a leaf node.
    @return true if it is a leaf, otherwise false
     */
    public boolean isLeaf()
    {
        // if the tree is empty or if it is not empty but its left and right
        // subtrees are empty, then it is a leaf
        
        if ( !this.isEmpty() && this.leftTree().isEmpty() && this.rightTree().isEmpty() )
        {
            return true;
        }
        return false;
    }

    /** Counts the number of nodes in the tree
    @return number of nodes in the tree
     */
    public int numNodes()
    {
        int nodes = 0;
        NodeVisitor action = new PrintAction();
        // base case
        if ( !this.isEmpty() )
        {
            // each time a node is visited increment the counter.
            this.getElement();
            nodes++;
            // visit nodes on left and right trees.
            nodes += this.leftTree().numNodes();
            nodes += this.rightTree().numNodes();
        }
        return nodes;
    }

    /** Counts the number of leaves in the tree
    @return number of leaves in the tree.  
     */
    public int numLeaves()
    {
        int leaves = 0;
        NodeVisitor action = new PrintAction();
        // base case
        if ( !this.isEmpty() )
        {
            // if the node is a leaf, increment the counter
            if ( this.isLeaf() )
            {
                leaves++;
            }
            // do the same for nodes in left and right trees.
            leaves += this.leftTree().numLeaves();
            leaves += this.rightTree().numLeaves();
        }
        return leaves;
    }

    /** Gets the depth of the tree. 
    @return depth of the tree.  
     */
    public int depth()
    {
        // starts at 1 to represent the root.
        int total = 1;
        // base case
        if ( !this.isEmpty() )
        {
            // get the depth of the left and right trees
            int leftDepth = this.leftTree().depth();
            int rightDepth = this.rightTree().depth();
            // whichever tree (left or right) is bigger will represent the 
            // depth of the entire tree. Add the depth to the total.
            if(leftDepth > rightDepth)
            {
                total += leftDepth;
            }
            else
            {
                total += rightDepth;
            }
        }
        return total;
    }

    /** Searches for a specific item in the tree. 
    @param      the item to search for
    @return     true if it contains the item, otherwise false. 
     */
    public boolean contains( Object item )
    {
        // base case
        if(!this.isEmpty())
        {
            // if the item is equal to the root, return. Otherwise, check
            // for data in the left and right trees. 
            if(this.data != null && this.data.equals(item))
            {
                return true;
            }
            else if(this.data != null &&this.leftTree().contains(item))
            {
                return true;
            }
            else if(this.data != null &&this.rightTree().contains(item))
            {
                return true;
            }
        }
        // return false if the item is not in the tree.
        return false;
    }

    /** Calculates the number of times an item occurs in the tree. 
    @param  the item to search for
    @return the number of times the item occurs
     */
    public int numOccurances(Object item){
        int total = 0;
        if ( !this.isEmpty() )
        {
            // search for item in the left and right trees. 
            total += this.leftTree().numOccurances(item);
            total += this.rightTree().numOccurances(item);
            // if the item is found, increment the counter.
            if(this.data.equals(item))
            {
                total ++;
            }
        }
        return total;
    }
}//end class BinaryTree