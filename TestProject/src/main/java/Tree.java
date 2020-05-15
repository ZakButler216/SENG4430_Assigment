import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class Tree {

    BST tree = new BST();

    private final CompilationUnit cu;
    private String javaContent;
    private int numChildren;
    private String rootKeyword = "Node(";
    private String Keyword = "root, ";
    private int numNode;
    private int number;
    private int depth;
    Node root;

    Tree(CompilationUnit newCu){
        cu = newCu;
        numChildren = 0;
        numNode = 0;
        javaContent = "";
        node = 0;
        depth = 0;

        setup();
    }

    public void setup() {

        //scanner for the question.
        Scanner scan = new Scanner(System.in);

        javaContent = cu.getChildNodes();
        if (javaContent.contains("insert")) {
            numNode++;
        }

        //print total number of children and number of nodes
        numOfNode();

        //copy BST
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(javaContent);
        while(m.find()) {
            root = new Node(m.group(1));
            for(int i = 2; i < m.group().length(); i++){
                BST.insert(root, m.group(i));
            }
        }

        depth = maxDepth(root);
        system.out.printlin ("Depth of Tree is " + depth);

        //ask for the node that user want to check for the number of children
        system.out.printlin ("Which node do you want to check for the number of children?");
        number = Integer.parseInt(scan.nextLine());
        numChildren = numOfChildren(tree.Search(number, root));
        system.out.printlin ("Number of children are: " + numChildren);
    }

    public void numOfNode(){
        system.out.printlin ("Total number of nodes: " + numNode);
    }

    //number of children
    public int numOfChildren(Node node) {
        int temp = 0;
        if (node.getLeft() != null) {
            temp = numChildren(node.getLeft());
        }
        if (node.getRight() != null) {
            temp = numChildren(node.getRight());
        }
        return temp + 1;
    }

    //depth of tree
    private void maxDepth(Node node)
    {
        if (node == null)
            return 0;
        else
        {
            // compute the depth of each subtree
            int lDepth = maxDepth(node.left);
            int rDepth = maxDepth(node.right);

            // use the larger one
            if (lDepth > rDepth)
                return lDepth + 1;
            else
                return rDepth + 1;
        }
    }
}