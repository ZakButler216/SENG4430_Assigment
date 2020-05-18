package main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FogIndex {

    private double wordAveragePerLine;
    private double complexAverage;
    private double fWordCount;
    private double fComplexCount;
    private double fLongestLine;
    private double lineCount;
    private double fogIndex;

    FogIndex(List<String> list){
        calcFogIndex(list);
    }

    private void calcFogIndex(List<String> list){
        double wordCount = 0;
        double complexCount = 0;
        double longestLine = 0;
        for(String s: list)
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
        wordAveragePerLine = wordCount/list.size();
        complexAverage = complexCount/wordCount;
        fWordCount = wordCount;
        fComplexCount = complexCount;
        fLongestLine = longestLine;
        lineCount = list.size();
        fogIndex = 0.4*(wordAveragePerLine+(100*(complexCount/wordCount)));
    }

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

    public void showResults(){
        System.out.println("Word average per line: " + wordAveragePerLine);
        System.out.println("Complex word average: " + complexAverage);
        System.out.println("Word count: " + fWordCount);
        System.out.println("Complex word count: " + fComplexCount);
        System.out.println("Length of longest line: " + fLongestLine);
        System.out.println("Total Line Count: " + lineCount);
        System.out.println("Fog Index: " + fogIndex);
    }
}
