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

        //call CherrenSection
        CherrenSection t = new CherrenSection();
        t.setup();

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
