//Student Author: Zachery Butler
//Student Number: C3232981
//Course: SENG4430, UoN, Semester 1, 2020
//Date last Modified: 25/05/2020

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

        double fiAverage = 0;
        int fiCount = 0;
        List<FogIndex> fiResults = new ArrayList<>();
        for(File entry : fr.getFileList()) {
            FogIndex fi = new FogIndex(entry);
            fiAverage = fiAverage + fi.getFogIndex();
            fiCount++;
            fiResults.add(fi);
            fi.showResults();
            System.out.println();
        }
        fiAverage = fiAverage/fiCount;
        System.out.println("The average FogIndex for this project is: " + fiAverage);

        System.out.println();
        System.out.println();
        System.out.println();

        double rfcAverage = 0;
        int rfcCount = 0;
        List<RFC> rfcResults = new ArrayList<>();
        for(CompilationUnit cu : cuList) {
            RFC rfc = new RFC(cu);
            rfcAverage = rfcAverage + rfc.getRfc();
            rfcCount++;
            rfcResults.add(rfc);
            rfc.showResults();
            System.out.println();
        }
        rfcAverage = rfcAverage/rfcCount;
        System.out.println("The average Response for a Class for this project: " + rfcAverage);
    }
}
