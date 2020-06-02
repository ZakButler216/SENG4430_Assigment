package metrics;
/*
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DepthOfInheritanceTree {

    private static List<CompilationUnit> allUnits;
    private static int depthLevel;
    static {
        depthLevel=0;
        allUnits = new ArrayList<>();
    }

    public static void getDepth() {
        int depthOfInheritanceTree;

        //Parse
        String s1 = "C:\\Users\\Cliff\\eclipse-workspace\\Java Project 4";
        Parser parser = new Parser();
        allUnits = parser.getCompilationUnits(s1);
        CompilationUnit cu = parser.getCompilationUnitByName(allUnits,"Stag");

        NodeList<TypeDeclaration<?>> allTheTypes = cu.getTypes();

        //Get the class declaration from current class
        List<ClassOrInterfaceDeclaration> classList = allTheTypes.get(0).findAll(ClassOrInterfaceDeclaration.class);
        ClassOrInterfaceDeclaration aClass = classList.get(0);

        //Recursively find it's inherited class
        depth(aClass);

        //get result
        depthOfInheritanceTree=depthLevel;
        //reset static variables
        depthLevel=0;
        allUnits = new ArrayList<>();

        System.out.println("Depth of inheritance tree is "+depthOfInheritanceTree);

    }

    public static void depth(ClassOrInterfaceDeclaration aClass) {

        //Base Case
        //If a class has no extended types (Means it doesn't inherit from any class)
        if (aClass.getExtendedTypes().size() == 0) {
            System.out.println("End of inheritance tree is "+aClass.getName());

            //return
            return;

            //Recursive case
        } else {
            System.out.println("Found an extra level of inheritance");
            System.out.println("Inherited "+aClass.getExtendedTypes().get(0).getName());

            //To store the inherited class
            ClassOrInterfaceDeclaration chosen=null;

            //add depth level
            depthLevel++;

            //Traverse through all the compilation units in the whole program
            outerloop:
            for (int i = 0; i < allUnits.size(); i++) {

                //get each compilation unit
                CompilationUnit unit = allUnits.get(i);
                NodeList<TypeDeclaration<?>> allTheTypes = unit.getTypes();

                //Get that compilation unit's class (and inner class if it exists)
                List<ClassOrInterfaceDeclaration> classDeclarations = allTheTypes.get(0).findAll(ClassOrInterfaceDeclaration.class);

                //In that compilation unit class declarations
                for (int j = 0; j < classDeclarations.size(); j++) {

                    //for each of it's class (Main or Inner)
                    ClassOrInterfaceDeclaration checkClass = classDeclarations.get(j);

                    //If it matches the name of the class that was inherited
                    if (checkClass.getName().equals(aClass.getExtendedTypes().get(0).getName())) {

                        //chose this class declaration
                        chosen = checkClass;

                        //break out of outerloop
                        break outerloop;
                    }

                }
            }

            //check this chosen class declaration again for inherited class
            depth(chosen);

        }
    }

    public static void main (String[]args) throws IOException {

        getDepth();
    }

}


 */