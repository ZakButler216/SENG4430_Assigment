package metrics;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.Comment;

import java.text.DecimalFormat;
import java.util.List;

/**
 This class is for functionalities relating to calculate Comment Percentage for a project.
 */
public class CommentPercentage {

    public CommentPercentage() {

    }

    /**
     This takes a list of compilationUnits, and outputs comment percentage for all the compilation units.
     */
    public double getCommentPercentageForAllCompilationUnits(List<CompilationUnit> compilationUnits) {

        //initialize variables needed
        double totalCommentPercentage=0;
        double amountOfCompilationUnits = (double) compilationUnits.size();
        double averageCommentPercentage = 0;

        //traverses through all the compilation units
        for(int i=0;i<compilationUnits.size();i++) {

            //for each compilation unit, get the comment percentage for it
            CompilationUnit cu = compilationUnits.get(i);
            double commentPercentage = getCommentPercentageForOneCompilationUnit(cu);

            //add it to the total comment percentage
            totalCommentPercentage += commentPercentage;

        }

        //get the average comment percentage
        averageCommentPercentage = totalCommentPercentage/amountOfCompilationUnits;

        //format the average comment percentage to two decimal places
        double averageCommentPercentageFormatted = Math.round(averageCommentPercentage * 100.0) / 100.0;

        //return formatted average comment percentage
        return averageCommentPercentageFormatted;


    }


    /**
     This takes one compilation unit, and returns the comment percentage for that compilation unit.
     */
    public double getCommentPercentageForOneCompilationUnit(CompilationUnit cu) {

        //get amount of comments
        int comments = countComments(cu);

        //get amount of code
        int code = countCode(cu);

        //transform to double
        double commentsLines = (double) comments;
        double codeLines = (double) code;

        //get the percentage through division
        double commentPercentage = commentsLines/codeLines;

        //format the percentage to two decimal places
        double commentPercentageFormatted = Math.round(commentPercentage * 100.0) / 100.0;

        //returns the formatted percentage
        return commentPercentageFormatted;

    }

    /**
     This takes one compilation unit, and returns the amount of lines of code for that compilation unit.
     */
    public int countCode(CompilationUnit cu) {

        //From the root node
        Node rootNode = cu.findRootNode();

        //Get all the code
        String code = rootNode.toString();

        //Split it into array according to lines
        String[] codeContainer = code.split("\n");

        int linesOfCode = 0;

        //For each line of code
        for(int i=0;i<codeContainer.length;i++) {

            //Remove any comment markers
            String commentMarkersRemoved = removeCommentMarkers(codeContainer[i]);

            //Count all non blank lines
            if(!commentMarkersRemoved.isBlank()) {
                linesOfCode++;
            }

        }

        //return total amount of lines of code
        return linesOfCode;

    }

    /**
     This takes one compilation unit, and returns the amount of comments for that compilation unit.
     */
    public int countComments(CompilationUnit cu) {

        //Get all comments
        List<Comment> allComments = cu.getAllComments();

        int commentCounter = 0;

        //For each comment
        for(int i=0;i<allComments.size();i++) {

            Comment comment = allComments.get(i);

            //Split it according to lines
            String[] commentLines = comment.getContent().split("\n");

            //For each line of comment
            for(int j=0;j<commentLines.length;j++) {

                //Remove comment markers
                String commentMarkersRemoved = removeCommentMarkers(commentLines[j]);

                //If the line is not blank, count it
                if(!commentMarkersRemoved.isBlank()) {
                    commentCounter++;
                }
            }
        }

        //return counted amount
        return commentCounter;

    }

    /**
     This method removes all comment markers from a string.
     */
    public String removeCommentMarkers(String s) {

        String removeOne = s.replaceAll("/\\*","");
        String removeTwo = removeOne.replaceAll("\\*/","");
        String removeThree = removeTwo.replaceAll("/\\*\\*","");
        String removeFour = removeThree.replaceAll("\\*","");
        String removeFive = removeFour.replaceAll("//","");

        return removeFive;

    }
}
