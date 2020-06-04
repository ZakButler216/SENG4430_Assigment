/*
 * File name:    Identifiers.java
 * Author:       Tamara Wold
 * Date:         20 May 2020
 * Version:      1.0
 * Description:  Calculates the average length of
 *               identifiers in the given data.
 *               Identifier length should be a balance
 *               between being informative and being
 *               readable. 6-9 characters of length
 *               is considered to be the desirable
 *               length of identifiers.
 * */

package metrics;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.nodeTypes.NodeWithIdentifier;
import com.github.javaparser.utils.SourceRoot;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static com.github.javaparser.StaticJavaParser.parse;

/**
 * This method parses given compilation units and retrieves
 * all identifiers. It then calculates the average length
 * of all found identifiers.
 */
public class Identifiers {

    public String getResult(CompilationUnit compilationUnit) {
        AtomicInteger identifierCount = new AtomicInteger();
        AtomicInteger totalStringCount = new AtomicInteger();
        //Parse compilation unit

            String compToString = compilationUnit.toString();
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

        //Calculate the average length of identifiers
        int tSC = totalStringCount.get();
        int iC = identifierCount.get();
        int average = tSC/iC;
        String total ="";
        String s1 = "The average length of identifiers is: " + average+"\n";
        String s2 = "";
        if (average == 6 || average == 7 || average == 8 || average == 9) {
            s2="This is an acceptable average length of identifiers.\n";
        }
        else if (average < 6) {
            s2="The average length of identifiers is too short and not informative enough.\n";
        }
        else if (average > 9) {
            s2="The average length of identifiers is too long and may affect readability.\n";
        }

        total = s1+s2;
        return total;
    }


}
