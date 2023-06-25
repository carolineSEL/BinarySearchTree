
/**
 * This class provides the main method for testing the BST class. 
 *
 * @author Caroline Lamb
 * @version 11/19/2021
 */
public class BSTTester
{
    /**
     * The main method for the program
     */
    public static void main(String args[])
    {
        // create Binary Search tree
        BST tree = new BST();
        // create a 2nd tree to compare to first (to test the equals method)
        BST tree2 = new BST();
        // create a 3rd tree to compare to second tree. 
        BST tree3 = new BST();
        
        // add items to the tree. 
        tree.add(new Integer(12));
        tree.add(new Integer(7));
        tree.add(new Integer(3));
        tree.add(new Integer(4));
        tree.add(new Integer(8));
        tree.add(new Integer(25));
        tree.add(new Integer(0));
        tree.add(new Integer(142));
        tree.add(new Integer(17));
        tree.add(new Integer(26));
        
        // add items to the 2nd tree. 
        tree2.add(new Integer(12));
        tree2.add(new Integer(7));
        tree2.add(new Integer(3));
        tree2.add(new Integer(4));
        tree2.add(new Integer(8));
        tree2.add(new Integer(25));
        tree2.add(new Integer(0));
        tree2.add(new Integer(142));
        tree2.add(new Integer(17));
        tree2.add(new Integer(26));
        
        // add items to 3rd tree.
        tree3.add(12);
        tree3.add(7);
        tree3.add(3);
        tree3.add(4);
        tree3.add(8);
        tree2.add(25);
        tree3.add(0);
        tree3.add(142);
        tree3.add(17);
        tree3.add(26);
        tree3.add(24);
        
        // create NodeVisitor object to be able to visit nodes in the tree. 
        NodeVisitor printer = new PrintAction();
        // traverse the tree using in-order traversal and test.
        System.out.println("******Traversing Tree: in-order******");
        tree.inOrderTraversal(printer);
        System.out.println("******Traversing 2nd Tree: in-order******");
        tree2.inOrderTraversal(printer);
        
        // test the methods in the BST class.
        System.out.println("Testing the equals method, this should be true: " + tree2.equals(tree2));
        //System.out.println("The left-most node of the tree is: " + tree.leftmost());
        //tree.inOrderTraversal(printer);
        //System.out.println("The left-most node of the tree is: " + tree.removeLeftMost() );
        //tree.inOrderTraversal(printer);
        //System.out.println("The left-most node of the tree is: " + tree.removeLeftMost() );
        //System.out.println("The left-most node was removed. The left-most node of the tree is now: " + tree.leftmost());
        //tree.inOrderTraversal(printer);
        System.out.println("The number 8 should now be removed from the tree ");
        tree.remove(8);
        tree.inOrderTraversal(printer);
        System.out.println("This should be true " + tree.contains(142));
        System.out.println("This should be false " + tree.contains(15));
        System.out.println("The number 17 occurs " + tree.numOccurances(17) + " times in the tree.");
        System.out.println("Testing the equals method, this should be false: " + tree2.equals(tree3));
    
    }
}
