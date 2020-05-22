package metrics;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.javaparser.ast.nodeTypes.NodeWithVariables;
import com.github.javaparser.ast.Node;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.symbolsolver.utils.SymbolSolverCollectionStrategy;
import com.github.javaparser.utils.ProjectRoot;
import com.github.javaparser.utils.SourceRoot;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Coupling
{




    private final CompilationUnit cu;
    private String content;

    public Coupling(CompilationUnit newCu)
    {
        cu = newCu;
        content = cu.getChildNodes().toString();
        String[] str = new String[1];
        str[0] = "";
        try {
            test();
        } catch(Exception e) {}
        //findFieldVarType(cu);
        //findVarType(cu);
        //findMethodCallClass(cu);
        //printContent();
    }

    public static void test() throws IOException {
        System.out.println("YOOOOOOOOOOOOOOOOO");

        //Parses stuff
        List<CompilationUnit> allCompilationUnits = new ArrayList<>();

        String s1 = "C:\\Users\\Cliff\\eclipse-workspace\\TestMultipleSourceRoots";
        String s2 = "C:\\Users\\Cliff\\eclipse-workspace\\TestMultipleClasses";
        String s3 = "C:\\Users\\Cliff\\eclipse-workspace\\TestSingleClass";
        String s4 = "C:\\Users\\Cliff\\Documents\\GitHub\\Bank-System";
        String s5 = "C:\\Users\\Cliff\\eclipse-workspace\\TestConditionals";

        Path root = Paths.get(s5);
        ProjectRoot projectRoot = new SymbolSolverCollectionStrategy().collect(root);

        for(int i =0;i<projectRoot.getSourceRoots().size();i++) {

            SourceRoot sourceRoot = projectRoot.getSourceRoots().get(i);

            sourceRoot.tryToParse();

            List<CompilationUnit> compilationUnits = sourceRoot.getCompilationUnits();

            for(int j = 0;j<compilationUnits.size();j++) {

                allCompilationUnits.add(compilationUnits.get(j));
            }
        }
        CompilationUnit cu = allCompilationUnits.get(0);

        //Start processing AST Tree
        NodeList<TypeDeclaration<?>> allTypes = cu.getTypes();
        NodeList<BodyDeclaration<?>> allMembers = allTypes.get(0).getMembers();

        //Finds method call through nodes in AST Tree
        System.out.println("Method Call: "+allTypes.get(0).findAll(MethodCallExpr.class));

        //This is another way can find method call, through VoidVisitorAdaptor
        //Tho it gives you less control over the variables
        MethodCall mc = new MethodCall();
        mc.visit(cu,null);

        //Gets methods
        NodeList<BodyDeclaration<?>> allMethods = new NodeList<>();
        for(int i=0;i<allMembers.size();i++) {
            if(allMembers.get(i).isMethodDeclaration()) {
                allMethods.add(allMembers.get(i));

            }
        }

        //Gets a method
        MethodDeclaration aMethod = (MethodDeclaration) allMethods.get(1);

        //Gets variables in the method
        System.out.println("Variable Declaration in method: "+ aMethod.findAll(VariableDeclarationExpr.class));
        //System.out.println(aMethod);

    }

    private static class MethodCall extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodCallExpr n, Void arg) {

            super.visit(n,arg);
            System.out.println(n.getName());
        }
    }

    public void printContent()
    {
        System.out.println("/////////// start //////////");
        System.out.println(content);
    }

    private static void findMethodCallClass(CompilationUnit cu) {


        cu.findAll(MethodCallExpr.class).forEach(m -> { //for each method call
            //System.out.println("Method Call: " + m);
            try {
                System.out.println("Class: " + m.resolve().getPackageName() + "." + m.resolve().getClassName()); //check package name to identify Java libraries
            } catch (UnsolvedSymbolException e) {
                System.out.println("Unsolvable");
            } catch (UnsupportedOperationException e) {
                System.out.println("wtf? " + e);
            } catch (RuntimeException e) {
                System.out.println("y u do dis? " + e);
            }
            System.out.println();
        });
    }

    // does not include class fields
    private static void findVarType(CompilationUnit cu) {
        findVarTypeHelper(cu, VariableDeclarationExpr.class, "Declaration: ");
    }

    private static void findFieldVarType(CompilationUnit cu) {
        findVarTypeHelper(cu, FieldDeclaration.class, "Class Declaration: ");
    }

    // pass FieldDeclaration.class to find instance variables or VariableDeclarationExpr.class for local variables
    private static <T extends Node & NodeWithVariables<T>> void findVarTypeHelper(CompilationUnit cu, Class<T> cType, String printOut) {
        cu.findAll(cType).forEach(v -> { //for each expression
            //System.out.println(printOut + v);
            v.getVariables().forEach(d -> { //for each declaration
                //System.out.print("Type: " + d.resolve().getType().asArrayType().getComponentType()); //arrays count as a different type
                try {
                    if (d.getType().isArrayType()) { //array types are also reference types so check it first
                        ResolvedType arrType = d.resolve().getType().asArrayType().getComponentType();
                        if (arrType.isReferenceType()) { //check in case it's an array of primitives
                            System.out.print(" (qualified name: " + arrType.asReferenceType().getQualifiedName() + ")"); //this also gets rid of the array part
                        }
                    } else if (d.getType().isReferenceType()) { //shouldn't be an array now
                        System.out.print(" (qualified name: " + d.resolve().getType().asReferenceType().getQualifiedName() + ")"); //qualified name includes both package and class name here
                    }
                } catch (UnsolvedSymbolException e) {
                    System.out.print("Unsolvable");
                } catch (UnsupportedOperationException e) {
                    System.out.print("\nwtf? " + e);
                }
                System.out.println();
            });
            System.out.println();
        });
    }

    private static void findAllTypes(CompilationUnit cu) {
        cu.findAll(Type.class).forEach(t -> {
            try { //basically same as the inner part of findVarTypeHelper, can deduplicate
                if (t.isArrayType()) { //array types are also reference types so check it first
                    ResolvedType arrType = t.resolve().asArrayType().getComponentType();
                    if (arrType.isReferenceType()) { //check in case it's an array of primitives
                        System.out.println(arrType.asReferenceType().getQualifiedName()); //this also gets rid of the array part
                    }
                } else if (t.isReferenceType()) { //shouldn't be an array now
                    System.out.println(t.resolve().asReferenceType().getQualifiedName()); //qualified name includes both package and class name here
                }
            } catch (UnsolvedSymbolException e) {
                System.out.println("Unsolvable: " + t);
            } catch (UnsupportedOperationException e) {
                System.out.println("\nwtf? " + e);
            }
        });
    }

    // assumes that all imports are used
    private static void findImports(CompilationUnit cu) {
        cu.findAll(ImportDeclaration.class).forEach(i -> {
            System.out.print(i.getName());
            if (i.isAsterisk()) {
                System.out.print(" (package)");
            }
            System.out.println();
        });
    }
}