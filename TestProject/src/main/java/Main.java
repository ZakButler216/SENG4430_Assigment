import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    private static String filePath;

    public static void main(String[] args) throws FileNotFoundException {
        List<String> results = new ArrayList<String>();

        //test file (Tree)
//        File path = new File("src/test/java/Tree_TestData/src/");

        //test file (Animals)
        File path = new File("src/test_animals/");
        File[] files = path.listFiles();

        //get all classes in the folder
        for (File file : files) {
            if (file.isFile()) {
                if (file.getName().contains(".java")) {
                    results.add(file.getName().substring(0, file.getName().length() - 5));
                }
            }
        }

        CherrenSection t = new CherrenSection();
        for (int i = 0; i < results.size(); i++) {
            filePath = results.get(i);
            CompilationUnit cu = StaticJavaParser.parse(files[i]);
            t.readFile(cu, results.get(i));
        }

        t.buildTree();


//       get number of children
        System.out.println("Number of children in the file list: " + t.getNumChildren());

//       get depth of tree
        System.out.println("Max depth in the file list: " + t.getMaxDepth());
    }
}
