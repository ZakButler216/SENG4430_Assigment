//Student Author: Zachery Butler
//Student Number: C3232981
//Course: SENG4430, UoN, Semester 1, 2020
//Date last Modified: 30/05/2020

package metrics;

import java.io.File;
import java.util.*;

//This class is used to get all the files from a directory,
//Then set those files to a File object,
//Then put all the file objects into a list
public class FolderReader {

    //List of files
    private final ArrayList<File> fileList;

    //Constructor that sets file list to a new list,
    //Then calls the method to get all the files in the directory
    //The directory will need to be parsed into the constructor as a File object
    FolderReader(File newDirectory){
        fileList = new ArrayList<>();
        getFilesInDirectory(newDirectory);
    }

    //Goes through the directory and sets each file in it to a File object
    //Also goes through any folders in the root directory and gets the files in them as well
    private void getFilesInDirectory(File directory){
        for(File fileEntry : Objects.requireNonNull(directory.listFiles())){
            if(fileEntry.isDirectory()){
                getFilesInDirectory(fileEntry);
            }else{
                fileList.add(fileEntry);
            }
        }
    }

    //Used to get a specific file representing a java class from the list of files
    public File getClassFile(String currentClass) {
        File classFile = null;
        for(File file : fileList){
            if(file.getName().equals(currentClass + ".java")){
                classFile = file;
            }
        }
        return classFile;
    }

    public ArrayList<File> getFileList() {
        return fileList;
    }
}