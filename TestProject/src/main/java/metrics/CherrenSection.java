package metrics;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CherrenSection {

    Tree tree;

    private final CompilationUnit cu;
    private String javaContent;
    private int numChildren;
    private int numNode;
    private int number;
    private int depth;
    Node root;
    private int count;
    private String parentClass;
    private List<Node> fileList;
    private static String filePath;
    private List<File> javaFilesList;

    CherrenSection(){
        cu = null;
        numChildren = 0;
        numNode = 0;
        javaContent = "";
        depth = 0;
        count = 0;
        fileList = new ArrayList<Node>();
        javaFilesList = new ArrayList<>();
    }

    public void setup(String directory) throws FileNotFoundException {
        List<String> results = new ArrayList<String>();
        //List<File> javaFilesList = new ArrayList<>();

        Parser parser = new Parser();
        FolderReader fr = new FolderReader(new File(directory));
        ArrayList<File> allFiles = fr.getFileList();

        //File path for test data (Tree)
//        File path = new File("src/test/java/Tree_TestData/src/");

        //Test data test_animals
        //File path = new File("src/test_animals/");
        //File[] files = path.listFiles();

        //get all java file's name
        for (File file : allFiles) {
            if (file.isFile()) {
                if (file.getName().contains(".java")) {
                    javaFilesList.add(file);
                    //results.add(file.getName().substring(0, file.getName().length() - 5));
                }
            }
        }

        for (int i = 0; i < javaFilesList.size(); i++) {
            //filePath = results.get(i);
            //System.out.println(filePath);
            //System.out.println(results.get(i));
            CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(javaFilesList.get(i)));
            //System.out.println("Reading file "+javaFilesList.get(i).getName());
            readFile(cu, javaFilesList.get(i).getName().substring(0, javaFilesList.get(i).getName().length() - 5));
        }

        buildTree();

    }

    public void readFile(CompilationUnit cu, String fileName) {

        //scanner for the java file.
        Scanner scan = new Scanner(System.in);
        javaContent = String.valueOf(cu.getChildNodes());
        //System.out.println("Child nodes "+cu.getChildNodes());

        Node temp = new Node(fileName);

        boolean hasExtends = false;


        if(cu.getTypes().isNonEmpty()) {

            List<ClassOrInterfaceDeclaration> classList = cu.getTypes().get(0).findAll(ClassOrInterfaceDeclaration.class);
            NodeList<ClassOrInterfaceType> extendedList = classList.get(0).getExtendedTypes();
            if (extendedList.isNonEmpty()) {

                for(int i=0;i<Parser.getStoredCompilationUnits().size();i++) {

                    List<ClassOrInterfaceDeclaration> classDeclarationList =
                            Parser.getStoredCompilationUnits().get(i).getTypes().get(0).findAll(ClassOrInterfaceDeclaration.class);

                    if(extendedList.get(0).getName().equals(classDeclarationList.get(0).getName())) {

                        hasExtends = true;
                    }
                }

            }
        }

        //System.out.println("Has extends "+hasExtends);
        //define which one is main class and which one is inherited class
        if (javaContent.contains("static void main")) {
            temp.setMain(true);

        } else if (javaContent.contains("extends")&& hasExtends) {
            //System.out.println("File name "+fileName);
            int indexExtends = javaContent.indexOf("extends") + 8;
            int indexBrac = javaContent.indexOf("{");
            temp.setParent(javaContent.substring(indexExtends, indexBrac).trim());
        }

        fileList.add(temp);
    }

    //Function to build the tree once all files are read in as nodes
    public Tree buildTree() {
        tree = new Tree();

        //Find the root and set it
        for (int i = 0; i < fileList.size(); i++) {
            if (fileList.get(i).isMain()) {
                tree.insert(fileList.get(i));
                fileList.remove(i);
                break;
            }
        }

        //Add children of the root (files that have no parent classes)
        for (int i = 0; i < fileList.size(); i++) {
            if (fileList.get(i).getParent().equals("") || fileList.get(i).getParent().equals(tree.getRoot().getName())) {
                tree.insert(fileList.get(i));
                fileList.remove(i);
                i--;
            }
        }

        //make sure all nodes are added to the tree
        //add the remain nodes to the tree
        boolean ifNodeRemain;
        int counter = 0;
        while (fileList.size() != 0)
        {
            ifNodeRemain = tree.insert(fileList.get(counter));

            if (ifNodeRemain)
            {
                counter++;
            }
            else
            {
                fileList.remove(counter);
                counter = 0;
            }
        }

        return tree;
    }

    //get the number of Children for a certain class
    public String getNumChildren(String className)
    {
        int children = tree.getNumOfChildren(className);

        if (children == -1)
        {
            return "File does not exist in directory.";
        }
        return ("Number of children subclasses: " + tree.getNumOfChildren(className));
    }

    //get the number of Children for a certain class
    public int getNumChildrenInt(String className)
    {
        int children = tree.getNumOfChildren(className);

        if (children == -1)
        {
            return -1;
        }
        return tree.getNumOfChildren(className);
    }

    //get the inheritance depth for a certain class
    //For JUnit Purposes
    public String getMaxDepth(String className)
    {
        int depth = tree.maxDepth(className);

        if (depth == -1)
        {
            return "File does not exist in directory.";
        }
        return ("Depth of class in inheritance tree: " + tree.maxDepth(className));
    }

    //get the inheritance depth for a certain class
    //For JUnit purposes
    public int getMaxDepthInt(String className)
    {
        int depth = tree.maxDepth(className);

        if (depth == -1)
        {
            return -1;
        }
        return tree.maxDepth(className);
    }
}