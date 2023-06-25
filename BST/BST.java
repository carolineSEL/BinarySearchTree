import java.lang.Comparable;
import java.util.Queue;
import java.util.LinkedList;
import java.lang.UnsupportedOperationException;
/**
 * This class is used to represent a Binary Search Tree. It inherits the 
 * Binary Tree class and overrides some of its methods. 
 *
 * @author Caroline Lamb, with assistance from Madeline Schroeder and Pam Cutter
 * @version 11/20/2021
 */
public class BST extends BinaryTree
{
    /**
     * Constructs a Binary Search Tree with methods inherited from the 
     * BinaryTree class.
     */
    public BST()
    {
        this.data = null;
        this.left = null;
        this.right = null;
    }

    /** Adds elements to a BST. Sorts the elements as they are added to the 
     * tree with items less than the node value on the left and items larget 
     * than the node value on the right. 
    @param value the value to be added to the tree.
    @return true when the value has been added; should never return false
     */
    public boolean add(Object value)
    {
        // if the tree is empty or its root value is null, set the root value
        // to be the value passed through the parameter and left and right
        // children. 
        // if the tree is not empty or the current node is not null, compare
        // the parameter value to the value of the current node (root) to 
        // determine whether the value should be added to the left or right 
        // subtree. 

        if ( isEmpty()  )
        {
            data = value;
            left = new BST();
            right = new BST();
            return true;
        }

        int compared = ((Comparable)data).compareTo(value);
        if (compared > 0)
        {
            return left.add(value);
        }
        else
        {
            return right.add(value);
        }
    }

    // this method is not necessary
    /** Finds the left most node in the tree. 
    @return     the value of the left-most node in the tree. 
     */
    /*private Object leftmost()
    {
    if ( this.isEmpty() )
    {
    return null;
    }
    else if ( this.isLeaf() == true )
    {
    return this.getElement();
    }
    Object node = this.leftTree().getElement();
    BST cur = ((BST)this.left);
    while ( cur.getElement() != null  )
    {
    node = cur.getElement();
    cur = ((BST)cur.leftTree());
    }
    return node;
    } */

    /** Removes left most node in the tree and replaces that node with the 
     * root of the right tree.
    @return    the left most node in the tree.
     */
    private Object removeLeftMost()
    {
        // variable to be returned.
        Object returnVal = null;
        // if the tree is empty the leftmost is null. Otherwise, if the left
        // tree is empty, then the root of the tree is the left-most value. 
        // remove and return that value, then set the new value of the root
        // to the value of the right tree's root. The right tree now becomes
        // the tree. 
        // until you reach a tree with an empty left tree, recursively call
        // the function. 
        if ( this.isEmpty() )
        {
            return null;
        }
        else if ( this.left.isEmpty() )
        {
            // set return value to the root of the current tree
            returnVal = this.getElement();
            data = this.right.getElement();
            left = this.right.left;
            right = this.right.right;
            return returnVal;
        }
        else
        {
            return ((BST)this.left).removeLeftMost();
        }
    }

    /** Removes the object specified by the parameter from the tree. If the 
     * right subtree of that value is not empty, its smallest value will 
     * replace the value that was removed. Otherwise it will become an empty
     * tree. 
    @param  the value to be removed from the tree
    @return true if the value was in the tree and was removed, otherwise false.
     */
    public boolean remove(Object value)
    {
        // check if the tree is empty.
        if ( this.isEmpty() )
        {
            return false;
        }
        // cast parameter value to a comparable and compare it to the
        // root of the tree.
        Comparable newValue = (Comparable)value;
        int compared = newValue.compareTo(this.getElement());
        // if the value is equal to the root of the tree, and the value is 
        // in a leaf, remove the item and set the tree to be empty.
        // otherwise, if the right subtree is not empty, move the smallest 
        // value in the right subtree to its position. 
        // if the right subtree is empty, then the root of the left subtree
        // replaces the right subtree, the root of the right subtree replaces
        // that subtree, and what was to the left of the previous left subtree
        // becomes the new right subtree.
        // if the value is not the root of the tree, recursively call the 
        // method on either the right or left subtrees (depending on the value
        // comparison) until the value is removed and the method returns true.
        if ( compared == 0 )
        {
            if ( this.isLeaf() == true  )
            {
                this.left = null;
                this.right = null;
                this.data = null;
            }
            else if ( !this.right.isEmpty() )
            {

                Object smallestR = ((BST)right).removeLeftMost();
                this.data = smallestR;

            }
            else 
            {
                this.data = this.left.getElement();
                this.right = this.left.right;
                this.left = this.left.left;
            }
            return true;
        }
        else if ( compared < 0 )
        {
            return ((BST)this.left).remove(value);
        }
        else
        {
            return ((BST)this.right).remove(value);
        }
    }

    /** Checks whether two BST are the same tree. 
    @param    the tree that you want to compare.
    @return   true if the trees are equal, false if they are not. 
     */
    public boolean equals(Object value )
    {
        // cast parameter value to a BST
        BST otherTree = (BST)value;
        // check if same number of nodes, if they are not, then the trees are
        // not equal.
        if ( this.numNodes() != otherTree.numNodes() )
        {
            return false;
        }
        // if the number of nodes is the same for both trees, and this tree
        // is empty, then they are both empty, and therefore equal.
        else if ( this.isEmpty() )
        {
            return true;
        }
        // otherwise, add the tree to the queue. Dequeue each node, and 
        // compare the number of occurances of its value with the number of
        // occurances of the same value in the other tree. 
        // if that value has the same number of occurances in both trees, add
        // its child trees to the queue and continue the loop. if two values 
        // have different numbers of occurances, then the trees are not
        // equal, return false.
        else
        {
            Queue<Object> queue = new LinkedList<Object>();
            queue.add(this);
            while( ! queue.isEmpty() )
            {
                BST tree = (BST)queue.remove();
                if ( ! tree.isEmpty() )
                {
                    Object el = tree.getElement();
                    if ( this.numOccurances(el) == otherTree.numOccurances(el))
                    {
                        queue.add(tree.leftTree());
                        queue.add(tree.rightTree());
                    }
                    else
                    {
                        return false;
                    }
                }
            }
        }
        // is only reached when both trees are equal. 
        return true; 
    }

    /**
     * 
     */
    public int hashCode()
    {
        throw new UnsupportedOperationException();
    }

    /** Searches for a specific item in the tree. 
    @param      the item to search for
    @return     true if it contains the item, otherwise false. 
     */
    public boolean contains( Object item )
    {
        // case the parameter item to a comparable
        Comparable cItem = (Comparable)item;
        // base case.
        if ( !this.isEmpty() )
        {
            // if the item is found in the tree, return true. Otherwise,
            // if it is less than the root, recursively call method on the 
            // left tree, if it is larger than the root, recursively call 
            // the method on the right tree. 
            if ( ((Comparable)this.getElement()).compareTo(cItem) == 0 )
            {
                return true;
            }
            else if ( ((Comparable)this.getElement()).compareTo(cItem) > 0 )
            {
                return ((BST)this.left).contains(item);
            }
            else 
            {
                return ((BST)this.right).contains(item);
            }
        }
        // if item is not in the tree, return false.
        return false;
    }

    /** Calculates the number of times an item occurs in the tree. 
    @param  the item to search for
    @return the number of times the item occurs
     */
    public int numOccurances(Object item){
        // variable to store number of ocurrances
        int total = 0;
        // base case
        if ( !this.isEmpty() && this.getElement() != null )
        {
            // cast parameter object to a comparable
            Comparable cItem = (Comparable)item;
            // if the item is equal to the current root, add one to the total
            // otherwise, if it is less than the current root, recursively 
            // call the method on the left tree until you reach an empty tree
            // or null value, do the same to the right tree if the item is 
            // larger than the root value. 
            if ( ((Comparable)this.getElement()).compareTo(cItem) == 0 )
            {
                total++;
                if ( ((Comparable)this.getElement()).compareTo(cItem) > 0 )
                {
                    total += ((BST)this.left).numOccurances(item);
                }
                else
                {
                    total += ((BST)this.right).numOccurances(item);
                }
            }
            else if ( ((Comparable)this.getElement()).compareTo(cItem) > 0 )
            {
                total += ((BST)this.left).numOccurances(item);
            }
            else if ( ((Comparable)this.getElement()).compareTo(cItem) < 0 )
            {
                total += ((BST)this.right).numOccurances(item);
            }
        }
        return total;
    }
}
