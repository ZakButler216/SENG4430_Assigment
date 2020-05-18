package main.java.coupling;

import com.github.javaparser.ast.CompilationUnit;

public class Coupling
{
    private final CompilationUnit cu;
    private String content;

    public Coupling(CompilationUnit newCu)
    {
        cu = newCu;
        content = cu.getChildNodes().toString();
        printContent();
    }

    public void printContent()
    {
        System.out.println("/////////// start //////////");
        System.out.println(content);
    }
}