//Student Author: Zachery Butler
//Student Number: C3232981
//Course: SENG4430, UoN, Semester 1, 2020
//Date last Modified: 24/05/2020

import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String directory = "src";

    public static void main(String[] args) throws IOException {

        File root = new File(directory);
        FolderReader fr = new FolderReader(root);

        Parser p = new Parser();
        List<CompilationUnit> cuList = p.getCompilationUnits(directory);


        List<FogIndex> fiResults = new ArrayList<>();
        for(File entry : fr.getFileList()) {
            FogIndex fi = new FogIndex(entry);
            fiResults.add(fi);
            fi.showResults();
            System.out.println();
        }

        List<RFC> rfcResults = new ArrayList<>();
        for(CompilationUnit cu : cuList){
            RFC rfc = new RFC(cu);
            rfcResults.add(rfc);
            rfc.showResults();
            System.out.println();
        }
    }
}
