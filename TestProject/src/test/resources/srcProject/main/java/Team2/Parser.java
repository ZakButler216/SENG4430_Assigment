package Team2;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.symbolsolver.utils.SymbolSolverCollectionStrategy;
import com.github.javaparser.utils.ProjectRoot;
import com.github.javaparser.utils.SourceRoot;



import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

public class Parser {
    //private String directory;

    public Parser() {

    }


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

            for (int j = 0; j < compilationUnits.size(); j++) {

                allCompilationUnits.add(compilationUnits.get(j));

            }
        }

        return allCompilationUnits;
    }

    public CompilationUnit getCompilationUnitByName(List<CompilationUnit> compilationUnits, String compilationUnitName) {

        for(int i=0;i<compilationUnits.size();i++) {
            String className = compilationUnits.get(i).getPrimaryTypeName().toString();
            className = className.substring(className.indexOf("[")+1);
            className = className.substring(0,className.indexOf("]"));

            if(className.equalsIgnoreCase(compilationUnitName)) {
                return compilationUnits.get(i);
            }
        }
        return null;
    }

}