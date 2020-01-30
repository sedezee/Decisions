package com.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//TODO: write file reading portion that grabs data from receipt 

public class FileHandler {

    File file; 

    public FileHandler(String fileName) {       
       try { 
           file = new File(fileName); 
           file.createNewFile(); 
        } catch(Exception e) {
            System.out.println(e); 
        }
    }

    public void writeFile(String text) {
        try {
            FileWriter fWriter = new FileWriter(file); 
            fWriter.write(text); 
            fWriter.flush(); 
            fWriter.close(); 
         } catch(IOException e) {
             e.printStackTrace();
         }
}
}