package main.java;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.Comment;

import java.util.List;

public class CommentPercentage {

    public CommentPercentage() {

    }

    public double getCommentPercentageForAllCompilationUnits(List<CompilationUnit> compilationUnits) {

        double totalCommentPercentage=0;
        double amountOfCompilationUnits = (double) compilationUnits.size();
        double averageCommentPercentage = 0;

        for(int i=0;i<compilationUnits.size();i++) {
            CompilationUnit cu = compilationUnits.get(i);
            double commentPercentage = getCommentPercentageForOneCompilationUnit(cu);
            totalCommentPercentage += commentPercentage;

        }

        averageCommentPercentage = totalCommentPercentage/amountOfCompilationUnits;

        return averageCommentPercentage;



    }

    public double getCommentPercentageForOneCompilationUnit(CompilationUnit cu) {

        int comments = countComments(cu);
        int code = countCode(cu);

        double commentsLines = (double) comments;
        double codeLines = (double) code;

        return commentsLines/codeLines;

    }

    public int countCode(CompilationUnit cu) {

        Node rootNode = cu.findRootNode();

        String code = rootNode.toString();

        String[] codeContainer = code.split("\n");

        int linesOfCode = 0;

        for(int i=0;i<codeContainer.length;i++) {

            String commentMarkersRemoved = removeCommentMarkers(codeContainer[i]);

            if(!commentMarkersRemoved.isBlank()) {
                linesOfCode++;
            }

        }

        return linesOfCode;

    }

    public int countComments(CompilationUnit cu) {

        List<Comment> allComments = cu.getAllComments();

        int commentCounter = 0;

        for(int i=0;i<allComments.size();i++) {

            Comment comment = allComments.get(i);

            String[] commentLines = comment.getContent().split("\n");

            for(int j=0;j<commentLines.length;j++) {
                if(!commentLines[j].isBlank()) {
                    commentCounter++;
                }
            }
        }

        return commentCounter;

    }

    public String removeCommentMarkers(String s) {

        String removeOne = s.replaceAll("/\\*","");
        String removeTwo = removeOne.replaceAll("\\*/","");
        String removeThree = removeTwo.replaceAll("/\\*\\*","");
        String removeFour = removeThree.replaceAll("\\*","");
        String removeFive = removeFour.replaceAll("//","");

        return removeFive;

    }
}
