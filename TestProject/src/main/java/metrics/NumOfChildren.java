package metrics;
/*
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import java.io.IOException;
import java.util.List;

public class NumOfChildren {

        //Number of children
        public static void getNumChildren() {

            //Parse
            String s1 = "C:\\Users\\Cliff\\eclipse-workspace\\Java Project 4";
            Parser parser = new Parser();
            List<CompilationUnit> allTheUnits = parser.getCompilationUnits(s1);
            CompilationUnit cu = parser.getCompilationUnitByName(allTheUnits, "Animal");

            NodeList<TypeDeclaration<?>> allTheTypes = cu.getTypes();
            List<ClassOrInterfaceDeclaration> classList = allTheTypes.get(0).findAll(ClassOrInterfaceDeclaration.class);

            //Java doesn't allow multiple inheritance, hence there will only be one extended class or none

            //Start going through all the compilation units in the program
            int noc = 0;
            for (int i = 0; i < allTheUnits.size(); i++) {

                //For each compilation unit
                CompilationUnit aUnit = allTheUnits.get(i);
                NodeList<TypeDeclaration<?>> types = aUnit.getTypes();

                //Get it's class (and inner classes if there exist inner classes)
                List<ClassOrInterfaceDeclaration> choices = types.get(0).findAll(ClassOrInterfaceDeclaration.class);

                //Go through that one compilation unit class list
                for (int j = 0; j < choices.size(); j++) {

                    //for each of it's class (Main or Inner)
                    ClassOrInterfaceDeclaration checkClass = choices.get(j);

                    //if it extends something (i.e. if it's a child of something)
                    if (checkClass.getExtendedTypes().size() > 0) {
                        //System.out.println("Extended "+checkClass.getExtendedTypes().get(0).getName());

                        //then check if it's parent matches the compilation unit class (or inner class) that you have selected
                        for (int k = 0; k < classList.size(); k++) {
                            ClassOrInterfaceDeclaration someClass = classList.get(k);
                            //System.out.println("Current "+someClass.getName());

                            //if there's a match
                            if (checkClass.getExtendedTypes().get(0).getName().equals(someClass.getName())) {

                                //Then it's a child of it
                                System.out.println("Child found " + checkClass.getName());
                                noc++;
                            }
                        }
                    }
                }

            }

            System.out.println("Num of children is " + noc);
        }


        public static void main (String[]args) throws IOException {

            getNumChildren();

        }
    }

 */

