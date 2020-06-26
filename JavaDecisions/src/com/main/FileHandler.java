package com.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {

    File file; 

    String fileName; 
    FileWriter fileWriter; 
    FileReader fileReader; 

    public FileHandler(String fileName) { 
        this.fileName = fileName;    
       try { 
           file = new File(fileName); 
           file.createNewFile(); 
        } catch(Exception e) {
            System.out.println(e); 
        }
    }

    public void write(String text) {
        try {
            fileWriter.write(text); 
            fileWriter.flush(); 
         } catch(IOException e) {
             e.printStackTrace();
         }
    }

    public char[] read() {
        try {
            char[] charArr = new char[1024];
            fileReader.read(charArr); 
            return charArr; 
        } catch(IOException e) {
            e.printStackTrace(); 
        }
        return null;
    }

    public void open() {
        try {
            this.fileWriter = new FileWriter(file);    
            this.fileReader = new FileReader(file); 
        } catch (IOException e) {
            System.out.println(e); 
        }
    }

    public void close() {
        try {  
            fileReader.close(); 
            fileWriter.close(); 
        } catch (IOException e) {
            System.out.println(e); 
        }
    }
}