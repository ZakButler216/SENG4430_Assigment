import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static String filePath;

    public static void main(String[] args) throws FileNotFoundException {
        List<String> results = new ArrayList<String>();

        //File path for test data (Tree)
//        File path = new File("src/test/java/Tree_TestData/src/");

        //Test data test_animals
        File path = new File("src/test_animals/");
        File[] files = path.listFiles();

        //get all java file's name
        for (File file : files) {
            if (file.isFile()) {
                if (file.getName().contains(".java")) {
                    results.add(file.getName().substring(0, file.getName().length() - 5));
                }
            }
        }

        //call CherrenSection
        CherrenSection t = new CherrenSection();
        for (int i = 0; i < results.size(); i++) {
            filePath = results.get(i);
            CompilationUnit cu = StaticJavaParser.parse(files[i]);
            t.readFile(cu, results.get(i));
        }

        t.buildTree();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please insert the class name (Not include '. java') that you want to check for the number of children: ");
        String classToCheckChildren = scanner.nextLine();
        //get number of children
        System.out.println(t.getNumChildren(classToCheckChildren));

        System.out.println("Please insert the class name (Not include '. java') that you want to check for the depth: ");
        String classToCheckDepth = scanner.nextLine();
        //get depth of tree
        System.out.println(t.getMaxDepth(classToCheckDepth));
    }
}
