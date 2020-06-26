package com.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//TODO: Categorized not working correctly
//TODO: Help not working correctly 
public class ScannerRun {
    private String saveFileName = "reasonSave"; 
    private ReasonParse reasonParse; 
    private Scanner scanner; 
    private HashMap<String, String> helpPartitions; 
    private ScannerHelp scannerHelp; 

    public ScannerRun() {
        reasonParse = new ReasonParse();
        scanner = new Scanner(System.in); 
        helpPartitions = new HashMap<String, String>();
        scannerHelp = new ScannerHelp(); 

        helpPartitions.put("ADD", "ADDs a new REASON to the system."); 
        helpPartitions.put("REMOVE", "REMOVES an existing reason to the system. Can be done with an ID parameter, GROUP, or REASON.");
        helpPartitions.put("REMOVE", "REMOVES an existing reason to the system. Can be done with an ID parameter, GROUP, or REASON.");
        helpPartitions.put("VIEW ALL", "Shows ALL reasons."); 
        helpPartitions.put("VIEW ONE", "Shows ONE reason. Retrieved with an ID Parameter."); 
        helpPartitions.put("RECEIPT", "Saves a FILE RECEIPT with a name of your choice. CANNOT be loaded from."); 
        helpPartitions.put("SAVE", "Saves RAW DATA under default name \"reasonSave\". DEFAULT NAME can be changed at the top of ScannerRun.java. This SAVE FILE can be loaded from.");
        helpPartitions.put("SUMMARY", "Provides a SUMMARY by either CATEGORY or GROUP of all of your reasons."); 
        helpPartitions.put("EXIT", "Completely EXITS the program. All REASONS are discarded."); 
        helpPartitions.put("LOAD",  "Looks for DEFAULT SAVE FILE and LOADS it.");
        helpPartitions.put("CHANGE", "Allows you to CHANGE portions of the REASONS."); 
    }

    private void scannerAdd() {
        int x = 0; 
        while(true) {
            System.out.println("Enter MENU to return to the MENU. EXIT to EXIT."); 
            System.out.println("Enter a GROUP. Remember that case is important."); 
            String group = scanner.nextLine(); 
            if (x > 0) 
                group = scanner.nextLine(); 
            scannerHelp.exitCheck(scanner, group);    
            if(group.toUpperCase().equals("MENU"))
                break; 

            System.out.println("Enter an INTERNAL CATEGORY NUMBER."); 
            String internalCatStr = scanner.nextLine(); 
            scannerHelp.exitCheck(scanner, internalCatStr); 
            if(group.toUpperCase().equals("MENU"))
                break; 
                
            int internalCat = scannerHelp.parseCat(internalCatStr); 

            System.out.println("Enter a REASON."); 
            String reason = scanner.nextLine(); 
            scannerHelp.exitCheck(scanner, reason.toUpperCase());
            if(reason.toUpperCase().equals("MENU"))
                break; 
                
            System.out.println("Enter a WEIGHT.");
            int weight = scanner.nextInt(); 

            reasonParse.addRawReason(new RawReason(group, internalCat, reason, weight));
            x++; 
        }
    }

    private void scannerRemove() {
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
                        scannerHelp.exitCheck(scanner, idStr.toUpperCase()); 
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
                scannerHelp.exitCheck(scanner, output);
                if(output.toUpperCase().equals("MENU")) 
                    break; 
            } else if (choice.equals("REASON")) {
                System.out.println("WHAT REASON would you like to remove?"); 
                String output = scanner.nextLine(); 
                reasonParse.removeRawReason(output);
                scannerHelp.exitCheck(scanner, output); 
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
            if(r != null) { 
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
            scannerHelp.exitCheck(scanner, choice); 
            if(choice.equals("CATEGORIZED")) {
                System.out.println("Would you like ALL reasons (grouped by category and group) or reasons in a SPECIFIC category?"); 
                String internalChoice = scanner.nextLine();
                internalChoice = internalChoice.toUpperCase();
                scannerHelp.exitCheck(scanner, internalChoice); 

                if (internalChoice.equals("ALL")) {
                    HashMap<String[], Integer> hash = reasonParse.getTotalWeightPerGroupCat(); 
                    System.out.println(scannerHelp.unpackHash(hash)); 
                } else if (internalChoice.equals("SPECIFIC")) {
                    System.out.println("What CATEGORY would you like a report on?"); 
                    String choiceCat = scanner.nextLine(); 
                    scannerHelp.exitCheck(scanner, choiceCat);

                    if(choiceCat.toUpperCase().equals("MENU")) 
                        break; 

                    int intChoiceCat = scannerHelp.parseCat(choiceCat); 
                    HashMap<String[], Integer> hash = reasonParse.getTotalWeightPerCat(intChoiceCat);
                    
                    System.out.println(scannerHelp.unpackHash(hash)); 

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
        FileHandler fileHandler = new FileHandler(name + ".txt"); 
        fileHandler.open(); 
        fileHandler.write(reasonParse.toString());
        fileHandler.close(); 
    }

    private void scannerSave() {
        FileHandler fileHandler = new FileHandler(saveFileName); 
        fileHandler.open(); 
        fileHandler.write(reasonParse.getFileString()); 
        fileHandler.close(); 
    }

    private void scannerLoad() {
        FileHandler fileHandler = new FileHandler (saveFileName); 
        char[] arr = fileHandler.read();
        ArrayList<String> arrStr = new ArrayList<String>(); 
        String str = ""; 
        for(char a : arr) {
            if(a != '}') {
                str += a; 
            } else {
                arrStr.add(str); 
                str = ""; 
            }

            if(a == '{')
                break; 
        }
        int len = arrStr.size(); 
        for(int i = 0; i < len; i += 4) {
            reasonParse.addRawReason(new RawReason(arrStr.get(i), Integer.parseInt(arrStr.get(i+1)), arrStr.get(i+2), Integer.parseInt(arrStr.get(i+3))));
        }
         
    }

    private void scannerHelp() {
        while (true) {
            System.out.println("What would you like help with? ALL, ADD, REMOVE, VIEW ALL, VIEW ONE, RECEIPT, SAVE, or SUMMARY?"); 
            System.out.println("Notice: You will not be able to EXIT while in the help function. To EXIT, please type MENU to return to the MENU."); 

            String choice = scanner.nextLine(); 
            choice = choice.toUpperCase(); 

            if(choice.equals("MENU")) 
                break; 
            else if(choice.equals("ALL")) {
                for(String s : helpPartitions.keySet()) {
                    System.out.println(s + " : " + helpPartitions.get(s)); 
                }
            } else {
                System.out.println(choice + " : " + helpPartitions.get(choice)); 
            }

        }
    }

    private void scannerChange() {
        while(true) {
            System.out.println("What ID would you like to change?"); 
            String ID = scanner.nextLine(); 

            scannerHelp.exitCheck(scanner, ID); 
            if(ID.toUpperCase().equals("MENU")) 
                break; 

            int intID = Integer.parseInt(ID); 
            RawReason rr = reasonParse.getReasonByID(intID); 
            System.out.println("Would you like to change the GROUP, INTERNAL CATEGORY, or WEIGHT?");
            String choice = scanner.nextLine();
            choice = choice.toUpperCase(); 
            scannerHelp.exitCheck(scanner, choice); 
            if(choice.equals("GROUP")){
                System.out.println("What would you like to change the GROUP to?"); 
                String group = scanner.nextLine(); 
                rr.setGroup(group); 
            } else if (choice.equals("INTERNAL CATEGORY") || choice.equals("CAT")) {
                System.out.println("What would you like to change the INTERNAL CATEGORY to?"); 
                String cat = scanner.nextLine(); 
                int intCat = scannerHelp.parseCat(cat); 
                rr.setInternalCategory(intCat); 
            } else if (choice.equals("WEIGHT") || choice.equals("WEIGHTING")) {
                System.out.println("What would you like to change the WEIGHT to?"); 
                int weight = scanner.nextInt(); 
                rr.setWeighting(weight); 
            } else if (choice.equals("MENU")) {
                break; 
            }
        }
    }

    public void run() {
        System.out.println("Your options are: ADD, REMOVE, VIEW ALL, VIEW ONE, RECEIPT, SAVE, and SUMMARY."); 
        System.out.println("For help with a particular option, type HELP [option]. To see the options again, type HELP."); 
        System.out.println("To exit, type \"EXIT\". Note that EXITing the program will CANCEL the entire program."); 
        loop: while(true) {
            System.out.println("What would you like to do?"); 
            String str = scanner.nextLine(); 
            str = str.toUpperCase(); 
            switch(str) {
                case "ADD":
                    scannerAdd(); 
                    break; 
                case "REMOVE": 
                    scannerRemove(); 
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
                case "HELP": 
                    scannerHelp(); 
                    break; 
                case "SAVE": 
                    scannerSave(); 
                    break; 
                case "LOAD": 
                    scannerLoad(); 
                    break; 
                case "CHANGE": 
                    scannerChange(); 
                    break; 
                case "EXIT": 
                    scanner.close(); 
                    break loop;
            } 
        }
    }
}