//Student Author: Cliff Kah Weng Ng
//Student Number: C3134188
//Course: SENG4430, UoN, Semester 1, 2020
//Date last Modified: 24/05/2020

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.utils.SymbolSolverCollectionStrategy;
import com.github.javaparser.utils.ProjectRoot;
import com.github.javaparser.utils.SourceRoot;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public List<CompilationUnit> getCompilationUnits(String directory) {

        List<CompilationUnit> allCompilationUnits = new ArrayList<>();
        Path root = Paths.get(directory);
        ProjectRoot projectRoot = new SymbolSolverCollectionStrategy().collect(root);
        for (int i = 0; i < projectRoot.getSourceRoots().size(); i++) {
            SourceRoot sourceRoot = projectRoot.getSourceRoots().get(i);
            try {
                sourceRoot.tryToParse();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<CompilationUnit> compilationUnits = sourceRoot.getCompilationUnits();
            allCompilationUnits.addAll(compilationUnits);
        }
        return allCompilationUnits;
    }

    public CompilationUnit getCompilationUnitByName(List<CompilationUnit> compilationUnits, String compilationUnitName) {
        for (CompilationUnit compilationUnit : compilationUnits) {
            String className = compilationUnit.getPrimaryTypeName().toString();
            className = className.substring(className.indexOf("[") + 1);
            className = className.substring(0, className.indexOf("]"));

            if (className.equalsIgnoreCase(compilationUnitName)) {
                return compilationUnit;
            }
        }
        return null;
    }
}
