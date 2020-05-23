package metrics;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.printer.XmlPrinter;
import com.github.javaparser.printer.YamlPrinter;

import java.util.List;

/**
 This class is for generic functions that everybody can use
 Feel free to add to it
 */
public class Generic {

    public void exploreChildNodes(Node n) {

        //Process node

        List<Node> childNodes = n.getChildNodes();

        for(Node a:childNodes) {
            exploreChildNodes(a);
        }

    }

    public void printASTTreeAsText(CompilationUnit cu) {
        YamlPrinter printer = new YamlPrinter(true);
        System.out.println(printer.output(cu));
    }

    public void printASTTreeAsXML(CompilationUnit cu) {
        XmlPrinter printer = new XmlPrinter(true);
        System.out.println(printer.output(cu));
    }


}
