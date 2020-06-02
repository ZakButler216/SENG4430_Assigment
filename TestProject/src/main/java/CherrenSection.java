import com.github.javaparser.ast.CompilationUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CherrenSection{

    BST tree = new BST();

    private final CompilationUnit cu;
    private String javaContent;
    private int numChildren;
    private int numNode;
    private int number;
    private int depth;
    Node root;

    CherrenSection(CompilationUnit newCu){
        cu = newCu;
        numChildren = 0;
        numNode = 0;
        javaContent = "";
        depth = 0;

        setup();
    }

    public void setup() {

        //scanner for the question.
        Scanner scan = new Scanner(System.in);
        javaContent = String.valueOf(cu.getChildNodes());

        //copy BST
        //copy all numbers into an array list
        String numbersLine = javaContent.replaceAll("[^0-9]+", " ");
        String[] strArray = numbersLine.split(" ");
        List<Integer> intArrayList = new ArrayList<>();

        for (String string : strArray) {
            if (!string.equals("")) {
                intArrayList.add(Integer.parseInt(string));
            }
        }

        //create BST
        root = new Node(intArrayList.get(0));
        tree.insert(root, intArrayList.get(0));
        for (int i = 2; i < intArrayList.size(); i++){
            tree.insert(root, intArrayList.get(i));
        }

        //depth of tree
        depth = maxDepth(root);

        //ask for the node that user want to check for the number of children
        System.out.println ("Which node do you want to check for the number of children?");
        number = Integer.parseInt(scan.nextLine());
        numChildren = numOfChildren(tree.search(number, root).getLeft()) + numOfChildren(tree.search(number, root).getRight());
    }

    //number of children
    public int numOfChildren(Node node) {
        int temp = 0;
        if (node != null) {
            if (node.getLeft() != null) {
                temp += numOfChildren(node.getLeft());
            }
            if (node.getRight() != null) {
                temp += numOfChildren(node.getRight());
            }
            return temp + 1;
        }
        return 0;
    }

    //depth of tree
    private int maxDepth(Node node)
    {
        int lDepth = 0;
        int rDepth = 0;

        if (node == null)
            return 0;
        else
        {
            // compute the depth of each subtree
            if (node.getLeft() != null) {
                lDepth = maxDepth(node.getLeft());
            }
            if (node.getRight() != null) {
                rDepth = maxDepth(node.getRight());
            }
        }
        // use the larger one
        if (lDepth > rDepth)
            return lDepth + 1;
        else
            return rDepth + 1;
    }

    public void NumChildrenresult(){

        //depth of tree
        System.out.println ("Number of child nodes are: " + numChildren);
    }

    public void DepthTreeresult() {

        //number of children
        System.out.println("Depth of Tree is " + depth);
    }
}