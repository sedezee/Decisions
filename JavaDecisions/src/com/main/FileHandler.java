package com.main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//TODO: write file reading portion that grabs data from receipt 

public class FileHandler {

    File file; 

    String fileName; 

    public FileHandler(String fileName) { 
        this.fileName = fileName;       
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

    public char[] readFile() {
        try {
            //this file will only ever have to rea the save file. 
            FileReader fReader = new FileReader(fileName); 
            char[] charArr = new char[1024];
            fReader.read(charArr); 
            fReader.close();  
            return charArr; 
        } catch(IOException e) {
            e.printStackTrace(); 
        }
        return null;
    }
}