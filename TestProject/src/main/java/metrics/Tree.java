package metrics;

import com.github.javaparser.ast.CompilationUnit;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tree {

    BST tree = new BST();

    private final CompilationUnit cu;
    private String javaContent;
    private int numChildren;
    private int numNode;
    private int number;
    private int depth;
    Node root;

    Tree(CompilationUnit newCu){
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

        //javaContent = cu.getChildNodes();
        if (javaContent.contains("insert")) {
            numNode++;
        }

        //copy BST
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(javaContent);
        while(m.find()) {
            root = new Node(Integer.parseInt(m.group(1)));
            for(int i = 2; i < m.group().length(); i++){
                BST.insert(root, Integer.parseInt(m.group(i)));
            }
        }

        //depth of tree
        depth = maxDepth(root);

        //ask for the node that user want to check for the number of children
        System.out.println ("Which node do you want to check for the number of children?");
        number = Integer.parseInt(scan.nextLine());
        numChildren = numOfChildren(tree.search(number, root));
    }

    //number of children
    public int numOfChildren(Node node) {
        int temp = 0;
        if (node.getLeft() != null) {
            temp = numOfChildren(node.getLeft());
        }
        if (node.getRight() != null) {
            temp = numOfChildren(node.getRight());
        }
        return temp + 1;
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

    public void result(){

        //print total number of children and number of nodes
        System.out.println ("Total number of nodes: " + numNode);

        //number of children
        System.out.println ("Depth of Tree is " + depth);

        //depth of tree
        System.out.println ("Number of children are: " + numChildren);
    }
}