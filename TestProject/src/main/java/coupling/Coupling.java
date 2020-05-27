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
    private String fileName, compareFile;
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
        compareFile = "";
        coupledClasses = new ArrayList<Set<String>>();
        innerClasses = new ArrayList<Set<String>>();
        cuList = newCuList;
        cu = cuList.get(0);
        findAllTypes = false;
        rootPackage = cu.getPackageDeclaration();

        for(CompilationUnit comp: cuList)
        {
            System.out.println(comp.getPrimaryTypeName());
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
            System.out.println("Root package is: " + rootPackage.get());
        } else {
            packages = false;
            System.out.println("No root package.");
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
            if(ptn.isPresent())
            {
                paths.add(pd.get().getName().toString()+"."+ptn.get());
            } else {
                paths.add("null");
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
        //System.out.println("P " + test + " | " + maxCommonLevel + " | " + test.split("\\.")[0] + " | " + test.split("\\.")[1]);
        for(int j = 0; j<maxCommonLevel; j++)
        {
            minPath += test.split("\\.")[j];
            if(j!=maxCommonLevel && j!=maxCommonLevel-1)
                minPath += ".";
        }
        //System.out.println("Min Path: " + minPath + " " + maxCommonLevel + " " + test.split("\\.").length + " " + test);


        for(int i =0; i<cuList.size(); i++) //cuList.size() is param
        {
            cu = cuList.get(i);
            //testing
            /*
            index = 0;
            for(CompilationUnit compu: cuList)
            {
                if(compu.getPrimaryTypeName().toString().contains("Item"))
                {
                    cu = compu;
                    break;
                }
                index++;
            }*/
            if(cu.getPrimaryTypeName().isPresent())
                fileName = cu.getPrimaryTypeName().get().toString();
            else
                fileName = "";
            //System.out.println(cu.getPrimaryTypeName().toString());

            findFieldVarType(cu);
            findVarType(cu);
            findMethodCallClass(cu);
            findImports(cu);
            findAllTypes = true;
            findAllTypes(cu);
            findAllTypes = false;
/*
            content = cu.getChildNodes().toString();*/
            index++;
        }
        printResults();
        //printContent();
    }

    public void incrementClass(int i)
    {
        if(!findAllTypes)
        {
            Set<String> set = coupledClasses.get(i);
            if(!set.contains(paths.get(i)))
            {
                set.add(paths.get(i));
            }
            /*if(fileName.equals("BST"))
                Thread.dumpStack();*/
            classCouple.set(i, classCouple.get(i)+1);
        } else { //if findAllTypes is being called
            Set<String> set = coupledClasses.get(i);
            if(!set.contains(paths.get(i)))
            {
                set.add(paths.get(i));
                classCouple.set(i, classCouple.get(i)+1);
            }
        }
    }

    public void printResults()
    {
        int i = 0;
        for(int coupling: classCouple)
        {
            System.out.println("For class: " + paths.get(i) + ",");
            System.out.println("\tCoupling = " + coupling);
            i++;
        }
    }

    public void printResults2()
    {
        System.out.println("For class: " + cuList.get(index).getPrimaryTypeName() + ",");
        System.out.println("\tCoupling = " + coupling);
        /*
        int i = 0;
        for(int coupling: classCouple)
        {
            System.out.println("For class:" + cuList.get(i).getPrimaryTypeName() + ",");
            System.out.println("\tCoupling = " + coupling);
            i++;
        }*/
    }

    public void printContent()
    {
        System.out.println("/////////// start //////////");
        System.out.println(content);
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
                int i = 0;
                for(CompilationUnit comp: cuList)
                {
                    if(comp.getPrimaryTypeName().toString().equals(className))
                    {
                        incrementClass(index); //increment this class
                        //System.out.println("0");
                        incrementClass(i); //increment that class
                        //System.out.println("1");
                        break;
                    }
                    i++;
                }
            }
        } else { //if packages
            if(!fileName.equals(className) && pkgName.contains(minPath)) //diff classes - diff class name and pkg name
            {
                for(int i =0; i<paths.size(); i++)
                {
                    String str = paths.get(i).substring(paths.get(i).lastIndexOf('.')+1);
                    if(str.equals(className) && paths.get(i).contains(pkgName))
                    {
                        incrementClass(index); //increment this class
                        //System.out.println("2");
                        incrementClass(i); //increment that class
                        //System.out.println("3");
                        break;
                    }
                }
            } else if (fileName.equals(className)) //diff classes - same class name diff pkg name
            {
                if(!paths.get(index).substring(0, paths.get(index).lastIndexOf('.')).contains(pkgName) && pkgName.contains(minPath))
                {
                    //System.out.println("Checking resolve for: " + className + " | " + pkgName);
                    for(int i =0; i<paths.size(); i++)
                    {
                        String str = paths.get(i).substring(paths.get(i).lastIndexOf('.')+1);
                        if(str.equals(className) && paths.get(i).contains(pkgName))
                        {
                            incrementClass(index); //increment this class
                            //System.out.println("4 " + paths.get(index) + " | " + fileName);
                            incrementClass(i); //increment that class
                            //System.out.println("5");
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
            //System.out.println("Method Call: " + m + " " + fileName); //uncomment
            /*if(m.toString().equals("v.getVariables()"))
            {
                m.resolve();
                System.out.println("/Method Call: " + m + " " + m.resolve());
            }*/
            try {
                //System.out.println("Class: " + m.resolve().getClass());
                //System.out.println("Class: " + m.resolve().getPackageName() + "." + m.resolve().getClassName() + " for " + m); //check package name to identify Java libraries, uncomment
                if(m.resolve().getClassName().contains("."))
                {
                    checkResolve(m.resolve().getPackageName()+"."+m.resolve().getClass()); //class is inner class
                } else {
                    checkResolve(m.resolve().getClassName(), m.resolve().getPackageName()); //check coupling
                }
            } catch (UnsolvedSymbolException e) {
                System.out.println("Method call unsolvable for " + m + "\n"); //for method calls made in lambda expr
                //unsolvable
            } catch (UnsupportedOperationException e) {
                System.out.println("wtf? " + e); //v.getVariables
            } catch (RuntimeException e) {
                System.out.println("y u do dis? " + e);
                System.out.println("For: " + m);
            }
            //System.out.println(); //uncomment
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
            //if(fileName.equals("Main"))
                //System.out.println("YOOOOOOOOOOOOOOOOOOOOOOOOOO " + printOut + v); //uncomment
            //System.out.println(printOut + v.getVariables() + " " + v.getVariables().get(0).getType() + " " + v.getVariables().get(0).getType().getClass());
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
                        checkResolve(d.resolve().getType().asReferenceType().getQualifiedName()); //check coupling
                    } else if (d.getType().getClass().toString().equals("class com.github.javaparser.ast.type.PrimitiveType"))
                    {
                        //System.out.println(" (qualified name: " + d.resolve().getType().toString() + ")"); //uncomment
                    } else {
                        System.out.println("Leaving out: " + v);
                    }
                } catch (UnsolvedSymbolException e) {
                    System.out.print(printOut + " unsolvable for " + " " + v); //unsolvable
                } catch (UnsupportedOperationException e) {
                    System.out.print("\nfindVarTypeHelper wtf? " + printOut + " " + e);
                }
                //System.out.println(); //uncomment
            });
            //System.out.println(); //uncomment
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
                    //System.out.println(t.resolve().asReferenceType().getQualifiedName()); //qualified name includes both package and class name here, uncomment
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
            //System.out.print(i.getName()); //uncomment
            if (i.isAsterisk()) {
                //System.out.print(" (package) " + i.getName()); //uncomment
            }
            //System.out.println();
        });
    }

    private void findInnerClassParent(CompilationUnit cu) {
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(c -> {
            if (c.isInnerClass()) {
                String qName = c.resolve().getQualifiedName();
                //System.out.println("Parent Class: " + qName.substring(0, qName.lastIndexOf('.')));
                System.out.println("Inner Class: " + qName);
                Set<String> set = innerClasses.get(index);
                if(!set.contains(c.resolve().getQualifiedName())) //if inner classes isn't in list already
                {
                    set.add(c.resolve().getQualifiedName());
                }
            }
        });
    }
}