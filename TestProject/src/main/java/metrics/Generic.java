package metrics;

/**
 File Name: Generics.java
 Author: Cliff Ng
 Description: A class which provides generic functionalities that anyone could use.
 */

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.printer.XmlPrinter;
import com.github.javaparser.printer.YamlPrinter;

import java.util.List;

/**
 This class is for generic functions
 */
public class Generic {

    /**
     This method explores child nodes of a tree.
     */
    public void exploreChildNodes(Node n) {

        //Process node

        List<Node> childNodes = n.getChildNodes();

        for(Node a:childNodes) {
            exploreChildNodes(a);
        }

    }

    /**
     This method prints the AST Tree of a Compilation Unit as Text Document.
     */
    public void printASTTreeAsText(CompilationUnit cu) {
        YamlPrinter printer = new YamlPrinter(true);
        System.out.println(printer.output(cu));
    }

    /**
     This method prints the AST Tree of a Compilation Unit as XML.
     */
    public void printASTTreeAsXML(CompilationUnit cu) {
        XmlPrinter printer = new XmlPrinter(true);
        System.out.println(printer.output(cu));
    }


}
