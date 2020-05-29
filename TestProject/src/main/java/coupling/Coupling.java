package main.java.coupling;

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
    //private String rootPackage;

    public Coupling(List<CompilationUnit> newCuList) //the full name plus the packname must be unique within a program
    {
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

        for(CompilationUnit comp: cuList)
        {
            //System.out.println(comp.getPrimaryTypeName()); //testing
            classCouple.add(0);
            coupledClasses.add(new HashSet<>());
            innerClasses.add(new HashSet<>());
        }
        for(CompilationUnit comp: cuList)
        {
            findInnerClassParent(comp);
        }
        //find file name
        if(rootPackage.isPresent()) {
            packages = true;
            //System.out.println("Root package is: " + rootPackage.get());
        } else {
            packages = false;
            System.out.println("No root package. Cooupling results might not be accurate.");
        }

        /*
        1. create variable to track C, have list of C vars for every class
        2. increment twice when finding the class (once for the class its in, once for the class it resolved to)
            2.1. find obj/method -> increment of class its in -> resolve it -> find class it resolved to -> increment class it belonged to
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
                    paths.add(pd.get().getName().toString()+"."+ptn.get());
                } else {
                    paths.add("null");
                }
            } else {
                if(ptn.isPresent())
                {
                    if(!paths.contains(ptn.get()))
                        paths.add(ptn.get());
                    else
                        System.out.println("Error: When there are no packages each class must have unique class names. Multiple classes named " + ptn.get() + ".\n Coupling results might not be accurate.");
                } else {
                    paths.add("null");
                }
            }
        }

        String longestPrefix = paths.get(0);
        String[] subPackages = longestPrefix.split("\\.");
        int minLength = Integer.MAX_VALUE;
        for(String p: paths)
        {
            String[] levels = p.split("\\.");
            if(levels.length<minLength)
            {
                minLength = levels.length;
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

        for(int j = 0; j<maxCommonLevel; j++)
        {
            minPath += test.split("\\.")[j];
            if(j!=maxCommonLevel && j!=maxCommonLevel-1)
                minPath += ".";
        }


        for(int i =0; i<cuList.size(); i++) //cuList.size() is param
        {
            cu = cuList.get(i);

            if(cu.getPrimaryTypeName().isPresent())
                fileName = cu.getPrimaryTypeName().get().toString();
            else
                fileName = "";

            findFieldVarType(cu);
            findVarType(cu);
            findMethodCallClass(cu);
            findAllTypes = true;
            findImports(cu);
            findAllTypes(cu);
            findAllTypes = false;
/*
            content = cu.getChildNodes().toString();*/
            index++;
        }
        printResults();
        //printContent();
    }

    public void incrementClass(int i, String pkgName, String className)
    {
        /*
        Main is not incrementing when Node is import into it, but Node is incrementing
        Both incrementClass(i) and fileName are called so do we need checks for both set and set2?
         */
        Set<String> set = coupledClasses.get(index);
        Set<String> set2 = coupledClasses.get(i);
        if(!findAllTypes)
        {
            if(!paths.get(i).equals(paths.get(index)))// class being coupled isn't fileName
            {
                if(!set.contains(paths.get(i)))
                {
                    set.add(paths.get(i));
                }
                if(!set2.contains(paths.get(index)))
                {
                    set2.add(paths.get(index));
                }
            }
            classCouple.set(i, classCouple.get(i)+1);
        } else { //if findAllTypes is being called
            if(!paths.get(i).equals(paths.get(index)))
            {
                if(!set.contains(paths.get(i)))
                {
                    set.add(paths.get(i));
                    classCouple.set(i, classCouple.get(i)+1);
                }
                if(!set2.contains(paths.get(index)))
                {
                    set2.add(paths.get(index));
                }
            } else { //if i is index
                String qN = pkgName+"."+className;
                if(!set.contains(qN))
                    classCouple.set(i, classCouple.get(i)+1);
            }
        }
    }

    public void printResults()
    {

        final Object[][] table = new String[paths.size()+2][];
        table[0] = new String[] { "Class", "Coupling Value"};
        table[1] = new String[] { "-----", "--------------"};


        int j=0;
        for(int i = 2; i<=paths.size()+1; i++)
        {
            table[i] = new String[] { paths.get(j), classCouple.get(j).toString()};
            j++;
        }


        for (final Object[] row : table) {
            System.out.format("%15s%15s\n", row);
        }
    }

    public void checkResolve(String className, String pkgName)
    {
        for(int i=0; i<paths.size(); i++)
        {
            if(paths.get(i).equals(pkgName))
            {
                Set<String> s = innerClasses.get(i);
                if(s.contains(pkgName+"."+className))
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
                    if(paths.get(i).equals(className))
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
                    String str = paths.get(i).substring(paths.get(i).lastIndexOf('.')+1);
                    if(str.equals(className) && paths.get(i).contains(pkgName))
                    {
                        incrementClass(index, pkgName, className); //increment this class
                        incrementClass(i, pkgName, className); //increment that class
                        break;
                    }
                }
            } else if (fileName.equals(className)) //diff classes - same class name diff pkg name
            {
                if(!paths.get(index).substring(0, paths.get(index).lastIndexOf('.')).contains(pkgName) && pkgName.startsWith(minPath))
                {
                    for(int i =0; i<paths.size(); i++)
                    {
                        String str = paths.get(i).substring(paths.get(i).lastIndexOf('.')+1);
                        if(str.equals(className) && paths.get(i).contains(pkgName))
                        {
                            incrementClass(index, pkgName, className); //increment this class
                            incrementClass(i, pkgName, className); //increment that class
                            break;
                        }
                    }
                }
            }
        } //end if packages
    }

    private void checkResolve(String qualifiedName)
    {
        if(!qualifiedName.contains("."))
        {
            checkResolve(qualifiedName, "");
        } else {
            String parts[] = qualifiedName.split("\\.");
            String pkg = qualifiedName.substring(0, (qualifiedName.length()-parts[parts.length-1].length()-1));
            checkResolve(parts[parts.length-1], pkg);
        }
    }

    private void findMethodCallClass(CompilationUnit cu) {
        cu.findAll(MethodCallExpr.class).forEach(m -> { //for each method call
            try {
                //System.out.println("Class: " + m.resolve().getClass());
                //System.out.println("Class: " + m.resolve().getPackageName() + "." + m.resolve().getClassName() + " for " + m);
                if(m.resolve().getClassName().contains("."))
                {
                    checkResolve(m.resolve().getPackageName()+"."+m.resolve().getClass()); //class is inner class
                } else {
                    checkResolve(m.resolve().getClassName(),  m.resolve().getPackageName()); //check coupling
                }
            } catch (UnsolvedSymbolException e) {
                //System.out.println("Method call unsolvable for " + m + "\n"); //for method calls made in lambda expr
                //unsolvable
            } catch (UnsupportedOperationException e) {
                //System.out.println("wtf? " + e); //v.getVariables
            } catch (RuntimeException e) {
                //System.out.println("y u do dis? " + e);
                //System.out.println("For: " + m);
            }
            //System.out.println();
        });
    }

    // does not include class fields
    private void findVarType(CompilationUnit cu) {
        findVarTypeHelper(cu, VariableDeclarationExpr.class, "Declaration: ");
    }

    private void findFieldVarType(CompilationUnit cu) {
        findVarTypeHelper(cu, FieldDeclaration.class, "Class Declaration: ");
    }

    // pass FieldDeclaration.class to find instance variables or VariableDeclarationExpr.class for local variables
    private <T extends Node & NodeWithVariables<T>> void findVarTypeHelper(CompilationUnit cu, Class<T> cType, String printOut) {
        cu.findAll(cType).forEach(v -> { //for each expression
            v.getVariables().forEach(d -> { //for each declaration
                //System.out.print("Type: " + d.resolve().getType().asArrayType().getComponentType()); //arrays count as a different type
                try {
                    if (d.getType().isArrayType()) { //array types are also reference types so check it first
                        ResolvedType arrType = d.resolve().getType().asArrayType().getComponentType();
                        if (arrType.isReferenceType()) { //check in case it's an array of primitives
                            //System.out.println(" (qualified name: " + arrType.asReferenceType().getQualifiedName() + ")"); //this also gets rid of the array part, uncomment, qualified name = pkgname+classname
                            checkResolve(arrType.asReferenceType().getQualifiedName()); //check coupling
                        }
                    } else if (d.getType().isReferenceType()) { //shouldn't be an array now
                        //System.out.println(" (qualified name: " + d.resolve().getType().asReferenceType().getQualifiedName() + ")"); //qualified name includes both package and class name here, uncomment
                        if(fileName.equals("Internal Stage") && d.resolve().getType().asReferenceType().getQualifiedName().equals("Item"))
                            //System.out.println("resolved class name: " + d.resolve().getType().asReferenceType().getQualifiedName());
                        checkResolve(d.resolve().getType().asReferenceType().getQualifiedName()); //check coupling
                    } else if (d.getType().getClass().toString().equals("class com.github.javaparser.ast.type.PrimitiveType"))
                    {
                        //System.out.println(" (qualified name: " + d.resolve().getType().toString() + ")");
                    } else {
                        //System.out.println("Leaving out: " + v);
                    }
                } catch (UnsolvedSymbolException e) {
                    //System.out.print(printOut + " unsolvable for " + " " + v); //unsolvable
                } catch (UnsupportedOperationException e) {
                    //System.out.print("\nfindVarTypeHelper wtf? " + printOut + " " + e);
                }
                //System.out.println();
            });
            //System.out.println();
        });
    }

    private void findAllTypes(CompilationUnit cu) { //includes generics
        cu.findAll(Type.class).forEach(t -> {
            try {
                if (t.isArrayType()) { //array types are also reference types so check it first
                    ResolvedType arrType = t.resolve().asArrayType().getComponentType();
                    if (arrType.isReferenceType()) { //check in case it's an array of primitives
                        //System.out.println(arrType.asReferenceType().getQualifiedName()); //this also gets rid of the array part, uncomment
                        checkResolve(arrType.asReferenceType().getQualifiedName()); //check coupling
                    }
                } else if (t.isReferenceType()) { //shouldn't be an array now
                    //System.out.println(t.resolve().asReferenceType().getQualifiedName()); //qualified name incl
                    // udes both package and class name here, uncomment
                    if(fileName.equals("Internal Stage") && t.resolve().asReferenceType().getQualifiedName().equals("Item"))
                        System.out.println("resolved class name: " + t.resolve().asReferenceType().getQualifiedName());
                    checkResolve(t.resolve().asReferenceType().getQualifiedName()); //check coupling
                }
            } catch (UnsolvedSymbolException e) {
                //System.out.println("Unsolvable: " + t);
            } catch (UnsupportedOperationException e) {
                //System.out.println("\nwtf? " + e);
            }
        });
    }

    // assumes that all imports are used
    private void findImports(CompilationUnit cu) {
        cu.findAll(ImportDeclaration.class).forEach(i -> {
            //System.out.print(i.getName());
            if (i.isAsterisk()) {
                //System.out.print(" (package) " + i.getName());
                //do nothing
            } else {
                for(String s: paths)
                {
                    if(i.getName().toString().equals(s))
                    {
                        Set<String> set = coupledClasses.get(index);
                        if(!set.contains(i.getName().toString()) && !fileName.equals(i.getName().toString()))
                        {
                            //System.out.println(" (package) " + i.getName() + " " + coupledClasses.get(index) + " " +s + " " + fileName);
                            checkResolve(i.getName().toString());
                        }
                    }
                }
            }
        });
    }

    private void findInnerClassParent(CompilationUnit cu) {
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(c -> {
            if (c.isInnerClass()) {
                String qName = c.resolve().getQualifiedName();
                Set<String> set = innerClasses.get(index);
                if(!set.contains(c.resolve().getQualifiedName())) //if inner classes isn't in list already
                {
                    set.add(c.resolve().getQualifiedName());
                }
            }
        });
    }
}