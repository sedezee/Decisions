package com.main;

import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.CancellationException;

public class ScannerHelp {
    public ScannerHelp() {

    }

    //some nice code to unpack hashmaps. Currently floating until I do more with it later. 
    public String unpackHash(HashMap<String[], Integer> hash) {
        String str = ""; 
        for(String[] key : hash.keySet()) {
            str+= ("GROUP: " + key[0] + " CATEGORY: " + key[1] + " TOTAL: " + hash.get(key)) + "\n";
        }
        return str; 
    }

    public void exitCheck(Scanner scanner, String exit) {
        if(exit.toUpperCase().equals("EXIT")){
            scanner.close(); 
            throw new CancellationException(); 
        }
    }

    public int parseCat(String str) {
        int internalCat = 0; 
        try {  
            internalCat = Integer.parseInt(str); 
        }  catch (Exception e) {
            if(str.length() == 1)
                internalCat = Character.getNumericValue(str.charAt(0)); 
            else 
                System.out.println("Please enter a number or a single character."); 
        } 
        return internalCat; 
    }

}