package com.main;

import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.CancellationException;

public class ScannerRun {
    ReasonParse reasonParse; 
    Scanner scanner; 

    public ScannerRun() {
        reasonParse = new ReasonParse();
        scanner = new Scanner(System.in); 
    }

    private void exitCheck(String exit) {
        if(exit.toUpperCase().equals("EXIT")){
            scanner.close(); 
            throw new CancellationException(); 
        }
    }

    //some nice code to unpack hashmaps. Currently floating until I do more with it later. 
    private String unpackHash(HashMap<String[], Integer> hash) {
        for(String[] key : hash.keySet()) {
            return ("GROUP: " + key[0] + " CATEGORY: " + key[1] + " TOTAL: " + hash.get(key));
        }
        return ""; 
    }

    private void scanAdd() {
        int x = 0; 
        while(true) {
            System.out.println("Enter MENU to return to the MENU. EXIT to EXIT."); 
            System.out.println("Enter a GROUP. Remember that case is important."); 
            String group = scanner.nextLine(); 
            if (x > 0) 
                group = scanner.nextLine(); 
            exitCheck(group);    
            if(group.toUpperCase().equals("MENU"))
                break; 

            System.out.println("Enter an INTERNAL CATEGORY NUMBER."); 
            String internalCatStr = scanner.nextLine(); 
            exitCheck(internalCatStr); 
            if(group.toUpperCase().equals("MENU"))
                break; 
                
            int internalCat = 0; 
            try {  
                internalCat = Integer.parseInt(internalCatStr); 
            }  catch (Exception e) {
                internalCat = Character.getNumericValue(internalCatStr.charAt(0)); 
            } 

            System.out.println("Enter a REASON."); 
            String reason = scanner.nextLine(); 
            exitCheck(reason.toUpperCase());
            if(reason.toUpperCase().equals("MENU"))
                break; 
                
            System.out.println("Enter a WEIGHT.");
            int weight = scanner.nextInt(); 

            reasonParse.addRawReason(new RawReason(group, internalCat, reason, weight));
            x++; 
        }
    }

    private void scanRemove() {
        while(true) {
            System.out.println("Would you like to REMOVE by ID, by GROUP, or by REASON?"); 
            String choice = scanner.nextLine().toUpperCase(); 
            if(choice.equals("ID")) {
                System.out.println("What is the ID you would like to remove?"); 
                try {
                    String idStr = scanner.nextLine(); 
                    int ID = -1; 
                    try {
                        ID = Integer.parseInt(idStr); 
                    } catch (Exception e) {
                        exitCheck(idStr.toUpperCase()); 
                        if(idStr.toUpperCase().equals("MENU"))
                            break; 
                    }
                    reasonParse.removeRawReason(ID); 
                } catch(Exception e) {
                    System.out.println("Please enter a NUMBER."); 
                }
            } else if (choice.equals("GROUP")) {
                System.out.println("What GROUP would like to remove?"); 
                String output = scanner.nextLine(); 
                reasonParse.removeGroup(output); 
                exitCheck(output);
                if(output.toUpperCase().equals("MENU")) 
                    break; 
            } else if (choice.equals("REASON")) {
                System.out.println("WHAT REASON would you like to remove?"); 
                String output = scanner.nextLine(); 
                reasonParse.removeRawReason(output);
                exitCheck(output); 
                if(output.toUpperCase().equals("MENU"))
                    break; 
            } else if (choice.equals("MENU")) {
                break; 
            }
        }  
    }

    private void scannerViewAll() {
        System.out.println(reasonParse.toString()); 
    }

    private void scannerViewOne() {
        while(true) {
            System.out.println("Enter a REASON ID. (Reason ID can be gotten from VIEW ALL.)"); 
            int ID = scanner.nextInt(); 
            RawReason r = reasonParse.getReasonByID(ID); 
            if(!r.getGroup().equals("xxxjkal")) { 
                System.out.println(r.toString());
                break;  
            } else {
                System.out.println("Please enter a REASON ID. (Reason ID can be retrieved from VIEW ALL. If you would like to return to the MENU, please take now to do so."); 
                if(scanner.nextLine().toUpperCase().equals("MENU"))
                    break; 
            }
            
        }
    }

    private void scannerSummary() {
        while(true) {
            System.out.println("Would you like to view a CATEGORIZED summary or a GROUPED summary?"); 
            String choice = scanner.nextLine(); 
            choice = choice.toUpperCase(); 
            exitCheck(choice); 
            if(choice.equals("CATEGORIZED")) {
                System.out.println("Would you like ALL reasons (grouped by category and group) or reasons in a SPECIFIC category?"); 
                String internalChoice = scanner.nextLine();
                internalChoice = internalChoice.toUpperCase();
                exitCheck(internalChoice); 

                if (internalChoice.equals("ALL")) {
                    HashMap<String[], Integer> hash = reasonParse.getTotalWeightPerGroupCat(); 
                    System.out.println(unpackHash(hash)); 
                } else if (internalChoice.equals("SPECIFIC")) {
                    System.out.println("What CATEGORY would you like a report on?"); 
                    String choiceCat = scanner.nextLine(); 
                    exitCheck(choiceCat);

                    if(choiceCat.toUpperCase().equals("MENU")) 
                        break; 

                    int intChoiceCat; 
                    try {  
                        intChoiceCat = Integer.parseInt(choiceCat); 
                    }  catch (Exception e) {
                        intChoiceCat = Character.getNumericValue(choiceCat.charAt(0)); 
                    } 
                    
                    HashMap<String[], Integer> hash = reasonParse.getTotalWeightPerCat(intChoiceCat);
                    
                    System.out.println(unpackHash(hash)); 

                } else if (internalChoice.equals("MENU")) {
                    break; 
                }
            } else if (choice.equals("GROUPED")) {
                HashMap<String, Integer> hash = reasonParse.getTotalWeightPerGroup(); 
                for(String i: reasonParse.getGroups()) {
                    System.out.println("GROUP: "  + i + " TOTAL: " + hash.get(i)); 
                }
            } else if (choice.equals("MENU")) {
                break; 
            }else {
                System.out.println("The choices are CATEGORIZED or GROUPED."); 
            }
        }
    }

    private void scannerReceipt() {
        System.out.println("Name your file."); 
        String name = scanner.nextLine(); 
        FileHandler fileWriter = new FileHandler(name + ".txt"); 
        fileWriter.writeFile(reasonParse.toString());
    }
    public void run() {
        System.out.println("Your options are: ADD, REMOVE, VIEW ALL, VIEW ONE, RECEIPT, and SUMMARY."); 
        System.out.println("For help with a particular option, type HELP [option]. To see the options again, type HELP options."); 
        System.out.println("To exit, type \"EXIT\". Note that EXITing the program will CANCEL the entire program."); 
        loop: while(true) {
            System.out.println("What would you like to do?"); 
            String str = scanner.nextLine(); 
            str = str.toUpperCase(); 
            switch(str) {
                case "ADD":
                    scanAdd(); 
                    break; 
                case "REMOVE": 
                    scanRemove(); 
                    break; 
                case "VIEW ALL": 
                    scannerViewAll();
                    break; 
                case "VIEW ONE": 
                    scannerViewOne(); 
                    break; 
                case "SUMMARY": 
                    scannerSummary(); 
                    break; 
                case "RECEIPT": 
                    scannerReceipt();
                    break; 
                case "EXIT": 
                    scanner.close(); 
                    break loop;
            } 
        }
    }
}