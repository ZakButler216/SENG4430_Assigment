package metrics;

/*
 * File name:    ProgramSize.java
 * Author:       Geethma Wijenayake
 * Date:         29 May 2020
 * Version:      7.0
 * Description:  Calculates coupling metric for every class in the list of classes passed into it.
 * */

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithVariables;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.ast.PackageDeclaration;
import java.util.Optional;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class Coupling
{
    private CompilationUnit cu;
    private String content;
    private int coupling;
    private Optional<PackageDeclaration> rootPackage;
    private final List<CompilationUnit> cuList;
    private List<Integer> classCouple;
    private int index;
    private Optional<TypeDeclaration<?>> pt;
    private String fileName;
    private List<String> paths;
    private String minPath;
    private boolean packages, findAllTypes;
    private ArrayList<Set<String>> coupledClasses;
    private ArrayList<Set<String>> innerClasses;
    private String parsingThis;
    private String results;
    //private String rootPackage;

    public Coupling(List<CompilationUnit> newCuList, String curentClass) //the full name plus the packname must be unique within a program
    {
        results = "";
        parsingThis = curentClass;
        minPath = "";
        classCouple = new LinkedList<Integer>();
        paths = new LinkedList<String>();
        coupling = 0;
        index = 0;
        coupledClasses = new ArrayList<Set<String>>();
        innerClasses = new ArrayList<Set<String>>();
        cuList = newCuList;
        cu = cuList.get(0);
        findAllTypes = false;
        rootPackage = cu.getPackageDeclaration();

        for(CompilationUnit comp: cuList) //initialise lists
        {
            classCouple.add(0); //initialise coupling values
            coupledClasses.add(new HashSet<>());
            innerClasses.add(new HashSet<>());
        }
        for(CompilationUnit comp: cuList)
        {
            findInnerClassParent(comp); //set up inner classes for each class
        }

        if(rootPackage.isPresent()) { //set up whether there are packages
            packages = true;
        } else {
            packages = false;
            results += "No root package. Coupling results might not be accurate.\n\n";
        }

        /* Plan:
        1. create variable to track C, have list of C vars for every class
        2. increment twice when finding the class (once for the class its in, once for the class it resolved to)
            2.1. find obj/method -> increment class its in -> resolve it -> find class it resolved to -> increment class it belonged to
        3. have C var for each class in program
        4. add all C var to print final result
        5. show C var in table for each class as well
         */
        Optional<String> ptn;
        Optional<PackageDeclaration> pd;
        for(CompilationUnit c: cuList)
        {
            ptn = c.getPrimaryTypeName();
            pd = c.getPackageDeclaration();
            if (pd.isPresent()) {
                if(ptn.isPresent())
                {
                    paths.add(pd.get().getName().toString()+"."+ptn.get()); //initialise qualified names
                } else {
                    paths.add("null");
                }
            } else {
                if(ptn.isPresent())
                {
                    if(!paths.contains(ptn.get()))
                        paths.add(ptn.get()); //initialise qualified names for when theres no packages
                    else //error
                        results += "Error: When there are no packages each class must have unique class names. Multiple classes named " + ptn.get() + ".\n Coupling results might not be accurate.\n\n";
                } else {
                    paths.add("null"); //error
                }
            }
        }

        String longestPrefix = paths.get(0);
        String[] subPackages = longestPrefix.split("\\."); //splits into all packages
        int minLength = Integer.MAX_VALUE;
        for(String p: paths)
        {
            String[] levels = p.split("\\.");
            if(levels.length<minLength)
            {
                minLength = levels.length; //set up minimum longest path length based on packages' level
            }
        }

        int maxCommonLevel = 0;

        for (int i=0; i < minLength; i++) { // for each level
            String current = paths.get(0).split("\\.")[i]; // pick one to start with
            for (String path : paths) { // for each class
                if (!path.split("\\.")[i].equals(current)) { // if the part at the current level does not match then break off, we found the max common prefix level
                    maxCommonLevel--;
                    break;
                }
            }
            maxCommonLevel++; // otherwise check the next level
        }
        //test maxCommonLevel

        String test = paths.get(0);

        for(int j = 0; j<maxCommonLevel; j++) //find minimum longest path
        {
            minPath += test.split("\\.")[j]; //add next package to minPath
            if(j!=maxCommonLevel && j!=maxCommonLevel-1) //formatting
                minPath += ".";
        }


        for(int i =0; i<cuList.size(); i++)
        {
            cu = cuList.get(i);

            if(cu.getPrimaryTypeName().isPresent())
                fileName = cu.getPrimaryTypeName().get().toString(); //set up current class name
            else
                fileName = ""; //error

            findFieldVarType(cu);
            findVarType(cu);
            findMethodCallClass(cu);
            findAllTypes = true; //for exceptions
            findImports(cu);
            findAllTypes(cu);
            findAllTypes = false;

            index++;
        }
    }

    /*
    Preconditions: must have at least class name and index, i, where paths.get(i) is path of cuList.get(i)
    Postconditions: increments coupling value for class at paths.get(i) as well as paths.get(index) where paths.get(index) is same as fileName
    so paths.get(i). and paths.get(index) are coupled together
     */
    public void incrementClass(int i, String pkgName, String className)
    {
        Set<String> set = coupledClasses.get(index);
        Set<String> set2 = coupledClasses.get(i);
        if(!findAllTypes)
        {
            if(!paths.get(i).equals(paths.get(index)))// class being coupled isn't fileName
            {
                if(!set.contains(paths.get(i))) //if not already added to set of coupled  classes
                {
                    set.add(paths.get(i)); //add
                }
                if(!set2.contains(paths.get(index))) //if not already added to set of coupled  classes
                {
                    set2.add(paths.get(index));
                }
            }
            classCouple.set(i, classCouple.get(i)+1); //set new coupling value
        } else { //if findAllTypes is being called
            if(!paths.get(i).equals(paths.get(index)))
            {
                if(!set.contains(paths.get(i))) //if not already added to set of coupled  classes
                {
                    set.add(paths.get(i));
                    classCouple.set(i, classCouple.get(i)+1); //set new coupling value
                }
                if(!set2.contains(paths.get(index))) //if not already added to set of coupled  classes
                {
                    set2.add(paths.get(index));
                }
            } else { //if i is index, e.i. class it is trying to resolve is itself - it is coupled to itself (exception case)
                String qN = pkgName+"."+className;
                if(!set.contains(qN)) //if set of coupled classes of class 'fileName' doesn't contain itself
                    classCouple.set(i, classCouple.get(i)+1); //set new coupling value
            }
        }
    }

    /*
    Preconditions: Coupling results have been determined
    Postconditions: Prints results straight to console
     */
    public void printResults()
    {

        final Object[][] table = new String[paths.size()+2][]; //create table
        table[0] = new String[] { "Class", "Coupling Value"}; //add headers
        table[1] = new String[] { "-----", "--------------"};


        int j=0;
        for(int i = 2; i<=paths.size()+1; i++)
        {
            String path = paths.get(j);

            if(path.length()>40) //if path too long to print
            {
                String[] spliting = path.split(".");
                if(path.contains("\\.{2}"))
                    path = spliting[spliting.length - 2] + "." + spliting[spliting.length - 1]; //only print class name and last pkg name
            }

            table[i] = new String[] { path, classCouple.get(j).toString()}; //set row with coupling values of each class
            j++;
        }


        for (final Object[] row : table) {
            System.out.format("%40s%40s\n", row); //format table rows
        }

        int total = 0;
        int totalAvg = 0;
        int looselyCoupled = 0;
        String[] evalResult = {"High", "Medium", "Low"};

        for(int c: classCouple)
        {
            total += c; //sum coupling values
            if(c <= 4)
            {
                looselyCoupled++; //sum classes that count as being loosely couples
            }
        }

        totalAvg = total/cuList.size();

        System.out.println(); //print result stats
        System.out.println("Total coupling value of program: " + total);
        System.out.println("Total coupling value of program per class (excluding internal classes): " + totalAvg);
        System.out.println("Loosely coupled classes (excluding internal classes): " + looselyCoupled + "/" + cuList.size() + " classes");
        System.out.println("Final evaluation is based on this loosely coupled ratio. A class is loosely coupled when their coupling value is less than or equal to 4.");
        System.out.println();

        float eval = 0;

        if(looselyCoupled!=0)
            eval = looselyCoupled/cuList.size(); //loosely coupled ratio

        String meaning = "";
        String action = "";

        System.out.print("Final Evaluation of Program: ");
        if(eval<0.4)
        {
            System.out.print(evalResult[0]);
            meaning = "a bad";
            action = "greatly reduced.";
        } else if(totalAvg>=0.4 && totalAvg<=0.6)
        {
            System.out.print(evalResult[1]);
            meaning = "an average";
            action = "reduced further.";
        } else if(totalAvg>0.6)
        {
            System.out.print(evalResult[2]);
            meaning = "a good";
        }
        System.out.println(" coupling");
        System.out.print("This is " + meaning + " result."); //add final result
        if(!action.equals(""))
        {
            System.out.print(" Coupling needs to be " + action);
        }
        System.out.println("");

    }

    /*
    Preconditions: Coupling results have been determined
    Postconditions: Returns a string containing formatted results
     */
    public String getResults()
    {
        int tableSize = 0;

        if(parsingThis.equals("")) {
            tableSize = paths.size()+2;
        } else {
            tableSize = 3;
        }

        final Object[][] table = new String[tableSize][]; //create table

        table[0] = new String[] { "Class", "Coupling Value"}; //add headers
        table[1] = new String[] { "-----", "--------------"};


        int j=0;
        for(int i = 2; i<=paths.size()+1; i++)
        {
            String path = paths.get(j);

            if(path.length()>40) //if path too long to print
            {
                String[] spliting = path.split(".");
                if(path.contains("\\.{2}"))
                    path = spliting[spliting.length - 2] + "." + spliting[spliting.length - 1];
            }

            if(parsingThis.equals("")) //if printing all classes
            {
                table[i] = new String[] { path, classCouple.get(j).toString()};
            } else { //if printing one class
                if(path.equals(parsingThis)) //only print result if path contains class, excludes packages
                {
                    table[2] = new String[] { path, classCouple.get(j).toString()};
                    break;
                }
            }

            j++;
        }


        for (final Object[] row : table) {
            results += String.format("%40s%40s\n", row);; //format rows of table
        }

        int total = 0;
        int totalAvg = 0;
        int looselyCoupled = 0;
        boolean currCoupled = false;
        String[] evalResult = {"High", "Medium", "Low"};

        int ind = 0;
        for(int c: classCouple)
        {
            total += c;
            if(c <= 4)
            {

                looselyCoupled++;
                if(paths.get(ind).equals(parsingThis)) //if looking at coupling value of class you are printing
                    currCoupled = true; //means it is loosely coupled
            }
            ind++;
        }

        totalAvg = total/cuList.size();
        String currResult = "";

        if(currCoupled)
        {
            currResult = "is"; //is loosely coupled
        } else {
            currResult = "is not";
        }

        results += "\n";
        if(!parsingThis.equals("")) //if printing one class, print results of that class
        {
            results += "The class being tested " + currResult + " loosely coupled. This " + currResult + " a good result.\n\n";
        }
        results += "Total coupling value of program: " + total + "\n";
        results += "Total coupling value of program per class (excluding internal classes): " + totalAvg + "\n";
        results += "Loosely coupled classes (excluding internal classes): " + looselyCoupled + "/" + cuList.size() + " classes" + "\n";
        results += "Final evaluation is based on this loosely coupled ratio. A class is loosely coupled when their coupling value is less than or equal to 4." + "\n\n";
        results += "Final Evaluation of Program: ";

        float eval = 0;

        if(looselyCoupled!=0)
            eval = looselyCoupled/cuList.size(); //loosely coupled ratio

        String meaning = "";
        String action = "";

        if(eval<0.4)
        {
            results += evalResult[0];
            meaning = "a bad";
            action = "greatly reduced.";
        } else if(totalAvg>=0.4 && totalAvg<=0.6)
        {
            results += evalResult[1];
            meaning = "an average";
            action = "reduced further.";
        } else if(totalAvg>0.6)
        {
            results += evalResult[2];
            meaning = "a good";
        }
        results += " coupling\n";
        results += "This is " + meaning + " result."; //add final result
        if(!action.equals(""))
        {
            results += " Coupling needs to be " + action + "\n";
        }

        return results;
    }

    /*
    Preconditions: Have a valid class name and package name
    Postconditions: Checks if you are trying to resolve something to a class that's coupled to fileName and exists in the current program
     */
    public void checkResolve(String className, String pkgName)
    {
        for(int i=0; i<paths.size(); i++)
        {
            if(paths.get(i).equals(pkgName)) //if it's an inner class of fileName
            {
                Set<String> s = innerClasses.get(i);
                if(s.contains(pkgName+"."+className)) //if trying to resolve an inner class, do nothing
                {
                    return;
                }
            }
        }

        if(!packages)
        {
            if(!fileName.equals(className)) //diff class
            {
                for(int i=0; i<paths.size(); i++)
                {
                    if(paths.get(i).equals(className)) //check if className is part of program
                    {
                        incrementClass(index, pkgName, className); //increment this class
                        incrementClass(i, pkgName, className); //increment that class
                        break;
                    }
                }
            }
        } else { //if packages
            if(!fileName.equals(className) && pkgName.startsWith(minPath)) //diff classes - diff class name and pkg name
            {
                for(int i =0; i<paths.size(); i++)
                {
                    String str = paths.get(i).substring(paths.get(i).lastIndexOf('.')+1); //get class name from full path
                    if(str.equals(className) && paths.get(i).contains(pkgName)) //if class is in program
                    {
                        incrementClass(index, pkgName, className); //increment this class
                        incrementClass(i, pkgName, className); //increment that class
                        break;
                    }
                }
            } else if (fileName.equals(className)) //diff classes - same class name diff pkg name
            {
                if(paths.get(index).lastIndexOf('.')!=-1)
                {
                    if(!paths.get(index).substring(0, paths.get(index).lastIndexOf('.')).contains(pkgName) && pkgName.startsWith(minPath))
                    { //if not the same package name as fileName but still same program
                        for(int i =0; i<paths.size(); i++)
                        {
                            String str = paths.get(i).substring(paths.get(i).lastIndexOf('.')+1);
                            if(str.equals(className) && paths.get(i).contains(pkgName)) //if found coupled class
                            {
                                incrementClass(index, pkgName, className); //increment this class
                                incrementClass(i, pkgName, className); //increment that class
                                break;
                            }
                        }
                    }
                }
            }
        } //end if packages
    }

    /*
    Preconditions: Have a valid qualified name
    Postconditions: Checks if you are trying to resolve something to a class that's coupled to fileName and exists in the current program
     */
    private void checkResolve(String qualifiedName)
    {
        if(!qualifiedName.contains(".")) //if no packages
        {
            checkResolve(qualifiedName, ""); //put class name and empty package name into check resolve
        } else {
            String parts[] = qualifiedName.split("\\.");
            String pkg = qualifiedName.substring(0, (qualifiedName.length()-parts[parts.length-1].length()-1)); //find package name
            checkResolve(parts[parts.length-1], pkg); //put class name and package name into check resolve
        }
    }

    /*
    Preconditions: Have a valid compilation unit containing a valid class
    Postconditions: Tries to resolve all method calls in class 'cu'
     */
    private void findMethodCallClass(CompilationUnit cu) {
        cu.findAll(MethodCallExpr.class).forEach(m -> { //for each method call
            try {
                if(m.resolve().getClassName().contains("."))
                {
                    checkResolve(m.resolve().getPackageName()+"."+m.resolve().getClass()); //class is inner class
                } else {
                    checkResolve(m.resolve().getClassName(),  m.resolve().getPackageName()); //check coupling
                }
            } catch (UnsolvedSymbolException e) {
                //unsolvable
            } catch (UnsupportedOperationException e) {
                //error
            } catch (RuntimeException e) {
                //error
            }
        });
    }

    /*
    Preconditions: Have a valid compilation unit containing a valid class
    Postconditions: Tries to resolve all variable declarations (local variables) in class 'cu'
     */
    private void findVarType(CompilationUnit cu) { // does not include class fields
        findVarTypeHelper(cu, VariableDeclarationExpr.class, "Declaration: ");
    }

    /*
    Preconditions: Have a valid compilation unit containing a valid class
    Postconditions: Tries to resolve all field declarations (instance variables) in class 'cu'
     */
    private void findFieldVarType(CompilationUnit cu) {
        findVarTypeHelper(cu, FieldDeclaration.class, "Class Declaration: ");
    }

    /*
    Preconditions: Have a valid compilation unit containing a valid class
    Postconditions: Tries to resolve all 'cType's in class 'cu'
     */
    private <T extends Node & NodeWithVariables<T>> void findVarTypeHelper(CompilationUnit cu, Class<T> cType, String printOut) {
        cu.findAll(cType).forEach(v -> { //for each expression
            v.getVariables().forEach(d -> { //for each declaration
                try {
                    if (d.getType().isArrayType()) { //array types are also reference types so check it first
                        ResolvedType arrType = d.resolve().getType().asArrayType().getComponentType();
                        if (arrType.isReferenceType()) { //check in case it's an array of primitives
                            //this also gets rid of the array part
                            checkResolve(arrType.asReferenceType().getQualifiedName()); //check coupling
                        }
                    } else if (d.getType().isReferenceType()) { //shouldn't be an array now
                        if(fileName.equals("Internal Stage") && d.resolve().getType().asReferenceType().getQualifiedName().equals("Item"))
                            checkResolve(d.resolve().getType().asReferenceType().getQualifiedName()); //check coupling
                    } else if (d.getType().getClass().toString().equals("class com.github.javaparser.ast.type.PrimitiveType"))
                    { //do nothing
                    } else {
                        //do nothing
                    }
                } catch (UnsolvedSymbolException e) {
                    //unsolvable
                } catch (UnsupportedOperationException e) {
                    //error, do nothing
                }
            });
        });
    }

    /*
    Preconditions: Have a valid compilation unit containing a valid class
    Postconditions: Tries to resolve all types of statements in class 'cu'
     */
    private void findAllTypes(CompilationUnit cu) { //includes generics
        cu.findAll(Type.class).forEach(t -> {
            try {
                if (t.isArrayType()) { //array types are also reference types so check it first
                    ResolvedType arrType = t.resolve().asArrayType().getComponentType();
                    if (arrType.isReferenceType()) { //check in case it's an array of primitives
                        //this also gets rid of the array part
                        checkResolve(arrType.asReferenceType().getQualifiedName()); //check coupling
                    }
                } else if (t.isReferenceType()) { //shouldn't be an array now
                    checkResolve(t.resolve().asReferenceType().getQualifiedName()); //check coupling
                }
            } catch (UnsolvedSymbolException e) {
                //unsolvable
            } catch (UnsupportedOperationException e) {
                //do nothing
            }
        });
    }

    /*
    Preconditions: Have a valid compilation unit containing a valid class
    Postconditions: Tries to resolve all imports in class 'cu'
     */
    private void findImports(CompilationUnit cu) { // assumes that all imports are used
        cu.findAll(ImportDeclaration.class).forEach(i -> {
            if (i.isAsterisk()) {
                //do nothing
            } else {
                for(String s: paths)
                {
                    if(i.getName().toString().equals(s)) //if import resolves to a class in program
                    {
                        Set<String> set = coupledClasses.get(index);
                        if(!set.contains(i.getName().toString()) && !fileName.equals(i.getName().toString())) //if resolving class is not already counted as a coupled class
                        {
                            checkResolve(i.getName().toString()); //count it as a coupled class
                        }
                    }
                }
            }
        });
    }

    /*
    Preconditions: Have a valid compilation unit containing a valid class
    Postconditions: Resolves parent classes of all inner classes
     */
    private void findInnerClassParent(CompilationUnit cu) {
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(c -> {
            if (c.isInnerClass()) { //if c is inner class
                String qName = c.resolve().getQualifiedName();
                Set<String> set = innerClasses.get(index);
                if(!set.contains(c.resolve().getQualifiedName())) //if inner classes isn't in list already
                {
                    set.add(c.resolve().getQualifiedName()); //set it as an inner class of paths.get(index) i.e. cu
                }
            }
        });
    }
}