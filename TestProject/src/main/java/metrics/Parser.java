package metrics;

/**
 File Name: Parser.java
 Author: Cliff Ng
 Description: A class which provides all functionalities in relation to parsing java files.
 */

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.symbolsolver.utils.SymbolSolverCollectionStrategy;
import com.github.javaparser.utils.ProjectRoot;
import com.github.javaparser.utils.SourceRoot;


import java.awt.color.ProfileDataException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

/**
 This class is for everything related to parsing the file(s).
 */
public class Parser {

    private static List<CompilationUnit> storedCompilationUnits;
    private static String storedDirectory;
    private static CompilationUnit storedCurrentCompilationUnit;

    public Parser() {

    }

    static {
        storedCompilationUnits = new ArrayList<>();
        storedDirectory=null;
        storedCurrentCompilationUnit = new CompilationUnit();
    }

    public static CompilationUnit getStoredCurrentCompilationUnit() {
        return storedCurrentCompilationUnit;
    }

    public void setStoredCurrentCompilationUnit(CompilationUnit storedCurrent) {
        storedCurrentCompilationUnit = storedCurrent;
    }

    public void setStoredDirectory(String directory) {

        storedDirectory = directory;
    }

    public static String getStoredDirectory() {
        return storedDirectory;
    }

    public void setStoredCompilationUnits(List<CompilationUnit> compilationUnits) {
        storedCompilationUnits = compilationUnits;
    }

    public static List<CompilationUnit> getStoredCompilationUnits() {
        return storedCompilationUnits;
    }


    /**
     This method gets a list of compilation units from a directory.
     */
    public List<CompilationUnit> getCompilationUnits(String directory) {

        //initialize an empty list of compilation units
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();

        //Get the root path from directory string
        Path root = Paths.get(directory);

        //Get the project root from the root path
        ProjectRoot projectRoot = new SymbolSolverCollectionStrategy().collect(root);

        //Get all the source roots from the project root
        for (int i = 0; i < projectRoot.getSourceRoots().size(); i++) {

            //For each source root
            SourceRoot sourceRoot = projectRoot.getSourceRoots().get(i);

            //Parse the source root
            try {
                sourceRoot.tryToParse();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Get all the compilation units of that source root
            List<CompilationUnit> compilationUnits = sourceRoot.getCompilationUnits();

            //Add it to the list of compilation units
            for (int j = 0; j < compilationUnits.size(); j++) {

                allCompilationUnits.add(compilationUnits.get(j));

            }
        }

        //return the list of compilation units
        return allCompilationUnits;
    }

    /**
     This method gets a list of classes/compilation unit names as String
     */
    //modified due to package.className change
    public List<String> getClassesAsString(List<CompilationUnit> compilationUnits) {

        //Initializes new list to store compilation unit names
        List<String> compilationUnitNames = new ArrayList<>();

        //Traverses the list of all compilation units
        for(int i=0;i<compilationUnits.size();i++) {

            //String packageName = compilationUnitNames.get(i).

            //Gets the class name of each compilation unit, which is of form Optional[Classname]
            String className = compilationUnits.get(i).getPrimaryTypeName().toString();

            //Trim the string from Optional[Classname] to Classname
            className = className.substring(className.indexOf("[")+1);
            className = className.substring(0,className.indexOf("]"));


            String packageName ="";
            String totalName="";
            if(compilationUnits.get(i).getPackageDeclaration().isPresent()) {
                packageName = compilationUnits.get(i).getPackageDeclaration().get().getName().toString()+".";
            }

            totalName = packageName+className;


            //Add to list
            compilationUnitNames.add(totalName);
        }

        //return list
        return compilationUnitNames;
    }



    /**
     This method gets a compilation unit from a list of compilation units,
     by searching via it's class name.
     */
    //modified due to package.className change
    public CompilationUnit getCompilationUnitByName(List<CompilationUnit> compilationUnits, String compilationUnitName) {

        //Traverses the list of all compilation units
        for(int i=0;i<compilationUnits.size();i++) {

            //Gets the class name of each compilation unit, which is of form Optional[Classname]
            String className = compilationUnits.get(i).getPrimaryTypeName().toString();

            //Trim the string from Optional[Classname] to Classname
            className = className.substring(className.indexOf("[")+1);
            className = className.substring(0,className.indexOf("]"));

            String packageName ="";
            String totalName="";
            if(compilationUnits.get(i).getPackageDeclaration().isPresent()) {
                packageName = compilationUnits.get(i).getPackageDeclaration().get().getName().toString()+".";
            }

            totalName = packageName+className;

            //If name searched equals class name, return the compilation unit
            if(totalName.equalsIgnoreCase(compilationUnitName)) {
                return compilationUnits.get(i);
            }
        }

        //else return null
        return null;
    }

    //modified due to package.className change
    public String getClassNameFromCompilationUnit(CompilationUnit cu) {
        String className = cu.getPrimaryTypeName().toString();
        //System.out.println(className);

        className = className.substring(className.indexOf("[")+1);
        className = className.substring(0,className.indexOf("]"));

        String packageName ="";
        String totalName="";
        if(cu.getPackageDeclaration().isPresent()) {
            packageName = cu.getPackageDeclaration().get().getName().toString()+".";
        }
        totalName = packageName+className;

        return totalName;
    }

    /**
     This method gets a method by it's name,
     from a compilation unit.
     */
    public MethodDeclaration getMethodByName(CompilationUnit cu, String methodName) {

        //Find all methods in the compilation unit
        List<MethodDeclaration> allMethods = cu.findAll(MethodDeclaration.class);

        //For each method
        for(int i=0;i<allMethods.size();i++) {

            //If name searched is equal to method name
            if(allMethods.get(i).getName().toString().equals(methodName)) {

                //return that method
                return allMethods.get(i);
            }
        }

        //else return null
        return null;
    }

}
