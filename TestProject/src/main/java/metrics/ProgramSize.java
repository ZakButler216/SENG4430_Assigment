package metrics;

/*
 * File name:    ProgramSize.java
 * Author:       Geethma Wijenayake
 * Date:         29 May 2020
 * Version:      1.0
 * Description:  Calculates program size metric for every class in the list of classes passed into it.
 * */

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.stmt.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProgramSize
{
    private List<CompilationUnit> cuList;
    private int statementsOfCode;
    private int statementsOfCodeInClass;
    private List<Integer> statementsPerClass;
    private String parsingThis;
    private List<String> paths;
    private String results;

    public ProgramSize(List<CompilationUnit> newCuList, String curentClass)
    {
        cuList = newCuList;
        statementsOfCode = 0;
        statementsOfCodeInClass = 0;
        parsingThis = curentClass;
        statementsPerClass = new ArrayList<Integer>();
        paths = new ArrayList<String>();
        results = "";

        Optional<String> ptn;
        Optional<PackageDeclaration> pd;
        for(CompilationUnit c: cuList)
        {
            ptn = c.getPrimaryTypeName();
            pd = c.getPackageDeclaration();
            if (pd.isPresent()) {  //if there are packages, add qualified name to paths
                if(ptn.isPresent())
                {
                    paths.add(pd.get().getName().toString()+"."+ptn.get()); //add path
                } else {
                    paths.add("null");
                }
            } else { //if no packages, just add class name to paths
                if(ptn.isPresent())
                {
                    if(!paths.contains(ptn.get()))
                        paths.add(ptn.get()); //add path
                    else
                        results += "Error: When there are no packages each class must have unique class names. Multiple classes named " + ptn.get() + ".\n Size results might not be accurate.\n\n";
                } else {
                    paths.add("null");
                }
            }
        }

        for(CompilationUnit cu: cuList)
        {
            findStatementClass(cu); //finds all statements
            findFieldVarType(cu); //finds all declarations
            statementsPerClass.add(statementsOfCodeInClass); //adds total to list of totals
            statementsOfCodeInClass = 0;
        }
    }

    /*
    Preconditions: Program size results have been determined
    Postconditions: Returns a string containing formatted results
     */
    public String getResults()
    {
        results += "Program size is measured by the number of statements in a program.\n";
        results += "Note: This metric (program size) is useful when comparing orders of magnitude. ";
        results += "While it is debatable exactly how to measure\nprogram size, discrepancies of an order of magnitude can be clear indicators of software complexity or man-hours.\n\n";

        int tableSize = 0;

        if(parsingThis.equals("")) {
            tableSize = paths.size()+2;
        } else {
            tableSize = 3;
        }

        final Object[][] table = new String[tableSize][];

        table[0] = new String[] { "Class", "Statements Value"};
        table[1] = new String[] { "-----", "--------------"};

        int j=0;
        for(int i = 2; i<=paths.size()+1; i++)
        {
            String path = paths.get(j);

            if(path.length()>40) //if path too long to print
            {
                String[] splitting = path.split(".");
                if(path.contains("\\.{2}"))
                    path = splitting[splitting.length - 2] + "." + splitting[splitting.length - 1];
            }

            if(parsingThis.equals("")) //if printing all classes
            {
                table[i] = new String[] { path, statementsPerClass.get(j).toString()};
            } else { //if printing one class
                if(path.equals(parsingThis)) //only print result if path contains class, excludes packages
                {
                    table[2] = new String[] { path, statementsPerClass.get(j).toString()};
                    break;
                }
            }

            j++;
        }

        for (final Object[] row : table) {
            results += String.format("%40s%40s\n", row); //format table
        }

        results += "\n";
        String grammar = "This result is";

        results += "Statements in all classes: ";
        results += statementsOfCode + "\n";
        if(!parsingThis.equals(""))
        {
            results += "The number of statements in the current class is shown in the above table.\n";
            grammar = "These results are";
        }

        results += "Note: " + grammar + " either good or bad depending on what the program is doing.\n";

        return results;
    }

    /*
    Preconditions: Contains valid compilation unit in parameter
    Postconditions: Increments number of statements for cu if there are statements in class
     */
    private void findStatementClass(CompilationUnit cu) {
        cu.findAll(Statement.class).forEach(s -> { //for each statement
            statementsOfCode++; //increment statement count for whole program
            statementsOfCodeInClass++; //increment statement count for class (cu)
        });
    }

    /*
    Preconditions: Contains valid compilation unit in parameter
    Postconditions:  Increments number of statements for cu if there are field declarations (any declarations) in class
     */
    private void findFieldVarType(CompilationUnit cu) {
        findVarTypeHelper(cu, FieldDeclaration.class);
    }

    /*
    Preconditions: Contains valid compilation unit in parameter
    Postconditions:  Increments number of statements for cu if there are 'cType's in class
     */
    private <T extends Node> void findVarTypeHelper(CompilationUnit cu, Class<T> cType) {
        cu.findAll(cType).forEach(v -> { //for each declaration
            statementsOfCode++; //increment statement count for whole program
            statementsOfCodeInClass++; //increment statement count for class (cu)
        });
    }
}
