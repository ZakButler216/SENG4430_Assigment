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

    private static final String directory = "src\\main";

    public static void main(String[] args) throws IOException {

        //Creates new FolderReader object that contains a list of File objects
        //The File objects represent all the files in the given directory
        //As well as files in sub directories
        //These files are not limited by type
        File root = new File(directory);
        FolderReader fr = new FolderReader(root);

        //Creates new Parser object that contains a list of Compilation units
        //The compilation units represent all the .java files in the directory
        //As well as any .java files in the sub directory
        Parser p = new Parser();
        List<CompilationUnit> cuList = p.getCompilationUnits(directory);


        //Gets each File from the FolderReader object's file list and creates a FogIndex item for it
        //The results for that file are displayed, and the Fog Index
        //At the end, the average Fog Index for the whole directory will be displayed
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

        //Gets the compilation units for the Parser object's list
        //Creates a RFC object for each of them
        //The RFC results for each cu are displayed
        //At the end, the average RFC for the whole package is displayed
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
