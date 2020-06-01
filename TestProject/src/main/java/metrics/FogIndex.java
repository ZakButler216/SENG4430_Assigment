//Student Author: Zachery Butler
//Student Number: C3232981
//Course: SENG4430, UoN, Semester 1, 2020
//Date last Modified: 29/05/2020
package metrics;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//Student Author: Zachery Butler
//Student Number: C3232981
//Course: SENG4430, UoN, Semester 1, 2020
//Date last Modified: 30/05/2020

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Class used to calculate the Fog Index of a file.
public class FogIndex {

    //The Fog Index for the file
    private double fogIndex;

    //Constructor that calls method that calculates all required metrics when called
    FogIndex(File file) throws IOException {
        calcFogIndex(file);
    }

    //Calculates the fog index of the file
    //takes a file object and reads each line in the file into a string which is added to a list of strings
    //Breaks down each string into a  list of strings where each entry is a word
    //Adds size of list of words to total word count
    //Checks if each word is complex or not
    //Increments total complex word count if needed
    //Checks the length of each line to find the longest line
    //Increments number of lines
    //After going through each line in the list of lines,
    //Calculates all values needed
    private void calcFogIndex(File file) throws IOException {
        double wordCount = 0;
        double complexCount = 0;
        double longestLine = 0;

        List<String> lines = Files.readAllLines(Paths.get(String.valueOf(file)), StandardCharsets.UTF_8);

        for(String s: lines)
        {
            if(s.length() != 0){
                List<String> wordsList = lineToWords(s);
                wordCount = wordCount + wordsList.size();
                for(String word : wordsList)
                {
                    if(isComplex(word)){
                        complexCount++;
                    }
                }
                if(s.length() > longestLine){
                    longestLine = s.length();
                }
            }
        }
        double wordAveragePerLine = wordCount / lines.size();
        fogIndex = 0.4*(wordAveragePerLine +(100*(complexCount/wordCount)));
    }

    //Checks if a word is complex by seeing if the word has 3 or more syllables
    //VowelNum is used to represent the number of syllables
    //This is because each separate vowel in a word means another syllable
    //Checks if each letter is a vowel, and checks if the previous letter was also a vowel
    //If the letter is a vowel and the previous letter was not a vowel,
    //This means another syllable for the word
    //Also checks if the last letter is an "e" or a "y"
    //Even though "e" is a vowel, if the last letter is "e" it does not create another syllable
    //And even though "y" is not a vowel, if the last letter is "y" it adds a syllable to the word
    private static boolean isComplex(String word) {
        char[] letters = word.toCharArray();
        int vowelNum = 0;
        boolean prevLetter = false;
        for(int i = 0; i<letters.length; i++) {
            char letter = letters[i];
            if(isVowel(letter) && !prevLetter){
                vowelNum++;
                prevLetter = true;
            }else{
                prevLetter = false;
            }
            if(i == letters.length-1){
                if(letter=='y' || letter=='Y'){
                    vowelNum++;
                }
            }
        }
        return vowelNum >= 3;
    }

    //Simple method that checks if a character is a vowel
    private static boolean isVowel(char letter) {
        char[] vowels = new char[]{'a', 'A', 'e', 'E', 'i', 'I', 'o','O', 'u', 'U'};
        for (char vowel : vowels)
        {
            if (letter == vowel)
            {
                return true;
            }
        }
        return false;
    }


    //Takes a string that represents a line and returns of list of strings,
    //Where each string entry is a word in the line
    //First separates the line by " " (a space) and puts the results into a string array
    //Then each entry in the array is separated by "(" (opening bracket)
    //Puts the results in a string array
    //The resulting arrays from each entry of the first array are stored in a list for string arrays
    //Each entry array in the list is then separated again by "." (a "full stop" or "period")
    //Finally, the final results are stored in a list of strings
    //Each entry is a word from the original line
    private static List<String> lineToWords(String line){
        if(line == null){
            return null;
        }
        String[] array1 = line.split("\\s+");
        List<String[]> list1 = new ArrayList<>();
        String[] array2;
        for (String word : array1) {
            array2 = word.split("\\(");
            list1.add(array2);
        }
        List<String[]> list2 = new ArrayList<>();
        for(String[] array : list1) {
            for(String word : array){
                array2 = word.split("\\.");
                list2.add(array2);
            }
        }
        List<String> finalList = new ArrayList<>();
        for(String[] array : list2){
            finalList.addAll(Arrays.asList(array));
        }
        return finalList;
    }

    //Method to print out the results
    //A fog index between 1 and 5 means the code is super readable
    //Between 6 and 8 means the code is very easy to read
    //Between 9 and 12 means the code is readable, but not super optimized
    //Between 13 and 17 means the code is not well optimized and could be hard to read
    //Over 17 means the code is not optimized at all and should be refactored
    public String getResults(){
        int fogIndexInt = (int)fogIndex;
        String results = "Fog Index for this class is: "  + fogIndexInt + ".\n";
        switch(fogIndexInt){
            case 1: case 2: case 3: case 4: case 5:
                results += "Well done, your code is super readable.";
                break;
            case 6: case 7: case 8:
                results += "Good, your code is easily readable.";
                break;
            case 9: case 10: case 11: case 12:
                results += "Your code is readable.";
                break;
            case 13: case 14: case 15: case 16: case 17:
                results += "Your code might be difficult to read.";
                break;
            default:
                results += "Your code is very difficult to read, consider refactoring it.";
                break;
        }
        results=results+"\n";
        return results;
    }

    //Returns the fog index
    public double getFogIndex() {
        return fogIndex;
    }
}