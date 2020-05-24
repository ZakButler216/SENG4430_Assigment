//Student Author: Zachery Butler
//Student Number: C3232981
//Course: SENG4430, UoN, Semester 1, 2020
//Date last Modified: 24/05/2020

import java.io.File;
import java.util.*;


public class FolderReader {

    private final ArrayList<File> fileList;

    FolderReader(File newDirectory){
        fileList = new ArrayList<>();
        getFilesInDirectory(newDirectory);
    }

    private void getFilesInDirectory(File directory){
        for(File fileEntry : Objects.requireNonNull(directory.listFiles())){
            if(fileEntry.isDirectory()){
                getFilesInDirectory(fileEntry);
            }else{
                fileList.add(fileEntry);
            }
        }
    }

    public ArrayList<File> getFileList(){
        return fileList;
    }
}
