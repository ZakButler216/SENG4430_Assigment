package metrics;
/*
 * File name:    Identifiers.java
 * Author:       Tamara Wold
 * Date:         20 May 2020
 * Version:      1.0
 * Description:  Calculates the average length of
 *               identifiers in a string object
 * */

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.nodeTypes.NodeWithIdentifier;
import com.github.javaparser.utils.SourceRoot;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static com.github.javaparser.StaticJavaParser.parse;

public class Identifiers {
    public static void main(String[] args) {
        Path pathToSource = Paths.get("src/main/java/Test");
        SourceRoot sourceRoot = new SourceRoot(pathToSource);
        try {
            sourceRoot.tryToParse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<CompilationUnit> compilations = sourceRoot.getCompilationUnits();

        AtomicInteger identifierCount = new AtomicInteger();
        AtomicInteger totalStringCount = new AtomicInteger();
        //Parse each compilation unit
        for (CompilationUnit cu: compilations) {
            String compToString = cu.toString();
            parse(compToString).walk(node -> {
                String identifier = "";
                int stringCount = 0;

                //Check if node is an identifier, if yes increment identifier
                //count and count how many characters are in the identifier
                if (node instanceof NodeWithIdentifier) {
                    identifier = ((NodeWithIdentifier<?>) node).getIdentifier();
                    identifierCount.getAndIncrement();
                    for (int j = 0; j < identifier.length(); j++) {
                        if (identifier.charAt(j) != ' ') {
                            stringCount++;
                        }
                    }
                    totalStringCount.addAndGet(stringCount);
                }
            });
        }
        //Calculate the average length of identifiers
        int tSC = totalStringCount.get();
        int iC = identifierCount.get();
        int average = tSC/iC;
        System.out.println(average);
    }
}
