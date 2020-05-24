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

public class Coupling
{
    private final CompilationUnit cu;
    private String content;
    private int coupling;
    private Optional<PackageDeclaration> rootPackage;
    //private String rootPackage;

    public Coupling(CompilationUnit newCu) //the full name plus the packname must be unique within a program
    {
        coupling = 0;
        cu = newCu;
        content = cu.getChildNodes().toString();
        rootPackage = cu.getPackageDeclaration();
        if(rootPackage.isPresent())
            System.out.println(rootPackage.get());
        else
            System.out.println("No root package.");

        findFieldVarType(cu);
        findVarType(cu);
        findAllTypes(cu);
        findMethodCallClass(cu);
        findImports(cu);
        //printContent();
    }

    public void printContent()
    {
        System.out.println("/////////// start //////////");
        System.out.println(content);
    }

    private static void findMethodCallClass(CompilationUnit cu) {
        cu.findAll(MethodCallExpr.class).forEach(m -> { //for each method call
            System.out.println("Method Call: " + m); //uncomment
            /*if(m.toString().equals("v.getVariables()"))
            {
                m.resolve();
                System.out.println("/Method Call: " + m + " " + m.resolve());
            }*/
            try {
                //System.out.println("Class: " + m.resolve().getClass()); //uncomment
                System.out.println("Class: " + m.resolve().getPackageName() + "." + m.resolve().getClassName() + " for " + m); //check package name to identify Java libraries, uncomment
            } catch (UnsolvedSymbolException e) {
                System.out.println("Method call unsolvable for " + m + "\n"); //for method calls made in lambda expr
                //unsolvable
            } catch (UnsupportedOperationException e) {
                System.out.println("wtf? " + e); //v.getVariables
            } catch (RuntimeException e) {
                System.out.println("y u do dis? " + e);
                System.out.println("For: " + m);
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
            System.out.println(printOut + v); //uncomment
            //System.out.println(printOut + v.getVariables() + " " + v.getVariables().get(0).getType() + " " + v.getVariables().get(0).getType().getClass());
            v.getVariables().forEach(d -> { //for each declaration
                //System.out.print("Type: " + d.resolve().getType().asArrayType().getComponentType()); //arrays count as a different type
                try {
                    if (d.getType().isArrayType()) { //array types are also reference types so check it first
                        ResolvedType arrType = d.resolve().getType().asArrayType().getComponentType();
                        if (arrType.isReferenceType()) { //check in case it's an array of primitives
                            System.out.print(" (qualified name: " + arrType.asReferenceType().getQualifiedName() + ")"); //this also gets rid of the array part, uncomment
                        }
                    } else if (d.getType().isReferenceType()) { //shouldn't be an array now
                        System.out.print(" (qualified name: " + d.resolve().getType().asReferenceType().getQualifiedName() + ")"); //qualified name includes both package and class name here, uncomment
                    } else if (d.getType().getClass().toString().equals("class com.github.javaparser.ast.type.PrimitiveType"))
                    {
                        System.out.print(" (qualified name: " + d.resolve().getType().toString() + ")"); //uncomment
                    } else {
                        System.out.println("Leaving out: " + v);
                    }
                } catch (UnsolvedSymbolException e) {
                    System.out.print(printOut + " unsolvable for " + " " + v); //unsolvable
                } catch (UnsupportedOperationException e) {
                    System.out.print("\nfindVarTypeHelper wtf? " + printOut + " " + e);
                }
                System.out.println();
            });
            System.out.println();
        });
    }

    private static void findAllTypes(CompilationUnit cu) {
        cu.findAll(Type.class).forEach(t -> {
            try {
                if (t.isArrayType()) { //array types are also reference types so check it first
                    ResolvedType arrType = t.resolve().asArrayType().getComponentType();
                    if (arrType.isReferenceType()) { //check in case it's an array of primitives
                        System.out.println(arrType.asReferenceType().getQualifiedName()); //this also gets rid of the array part, uncomment
                    }
                } else if (t.isReferenceType()) { //shouldn't be an array now
                    System.out.println(t.resolve().asReferenceType().getQualifiedName()); //qualified name includes both package and class name here, uncomment
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
            System.out.print(i.getName()); //uncomment
            if (i.isAsterisk()) {
                System.out.print(" (package) " + i.getName()); //uncomment
            }
            System.out.println();
        });
    }
}