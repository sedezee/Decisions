package com.main;

import java.util.ArrayList;
import java.util.HashMap;

public class ReasonParse {
    ArrayList<RawReason> arr; 

    public ReasonParse(ArrayList<RawReason> arr) {
        this.arr = arr; 
    }

    public ReasonParse() {
        arr = new ArrayList<RawReason>(); 
    }

    //ADDITION AND SUBTRACTION METHODS
    public void addRawReason(RawReason rReason) {
        arr.add(rReason); 
    }

    public void removeRawReason(int ID) {
        for(RawReason i : arr) {
            if(i.getID() == ID) {
                arr.remove(arr.indexOf(i));  
            }
        }
    }

    public void removeRawReason(String reason) {
        for(RawReason i : arr) {
            if(i.getReason().equals(reason)) {
                arr.remove(arr.indexOf(i)); 
            }
        }
    }

    public void removeGroup(String group) {
        for(RawReason i : arr) {
            if(i.getGroup().equals(group)) {
                arr.remove(arr.indexOf(i)); 
            }
        }
    }

    //GETTER METHODS
    //RAWREASON
    public RawReason getReasonByID(int ID) {
        RawReason rawReason = new RawReason("xxxjkal", 3, "xxxjkal", 3); 
        for(RawReason i : arr) {
            if(i.getID() == ID) 
                return i; 
        }
        return rawReason; 
    }

    //LISTS
    //RAWREASON LISTS
    private ArrayList<RawReason> getRawReasons(String group) {
        ArrayList<RawReason> arrRes = new ArrayList<RawReason>();
        for(RawReason i : arr) {
            if(i.getGroup().equals(group)) {
                arrRes.add(i); 
            }
        }
        return arrRes; 
    }

    private ArrayList<RawReason> getRawReasons(String group, int cat) {
        ArrayList<RawReason> arrRes = new ArrayList<>(); 
        for(RawReason i : arr) {
            if (i.getGroup().equals(group) && i.getInternalCategory() == cat) {
                arrRes.add(i); 
            }
        }
        return arrRes; 
    }

    //SUB-CATEGORY LISTS
    public ArrayList<String> getGroups() {
        ArrayList<String> arrStr = new ArrayList<String>();
        for(RawReason i : arr) {
            if(!arrStr.contains(i.getGroup().toString())) 
                arrStr.add(i.getGroup().toString()); 
        }
        return arrStr; 
    }

    public ArrayList<Integer> getCategories() {
        ArrayList<Integer> arrInt = new ArrayList<Integer>(); 
        for(RawReason i : arr) {
            if(!arrInt.contains(i.getInternalCategory()))
                arrInt.add(i.getInternalCategory()); 
        }
        return arrInt; 
    }

    //SPECIFIC LISTS
    public HashMap<String, Integer> getTotalWeightPerGroup() {
        HashMap<String, Integer> total = new HashMap<String, Integer>();
        int totalWeight = 0; 
        for(String i : getGroups()) {
            for(RawReason j : getRawReasons(i)) {
                totalWeight += j.getWeighting(); 
            }
            total.put(i, totalWeight); 
            totalWeight = 0; 
        }
        return total; 
    }

    public HashMap<String[], Integer> getTotalWeightPerGroupCat() {
        HashMap<String[], Integer> total = new HashMap<String[], Integer>(); 
        int totalWeight = 0; 
        for(String i : getGroups()) {
            for(int j : getCategories()) {
                for(RawReason k : getRawReasons(i, j)) {
                    totalWeight += k.getWeighting(); 
                }
                total.put(new String[]{i, Integer.toString(j)}, totalWeight); 
                totalWeight = 0; 
            }
        }
        return total; 
    }

    public HashMap<String[], Integer> getTotalWeightPerCat(int cat) {
        HashMap<String[], Integer> total = new HashMap<String[], Integer>(); 
        int totalWeight = 0; 
        for(String i : getGroups()) {
            for(RawReason k : getRawReasons(i, cat)) {
                totalWeight += k.getWeighting(); 
            }
            total.put(new String[] {i, Integer.toString(cat)}, totalWeight); 
            totalWeight = 0; 
        }
        return total; 
    }

    //OTHER
    public String toString() {
        String str = "";  
        for(String i : getGroups()) {
            for(RawReason j : getRawReasons(i)) {
                str += j.toString() + "\n";
            }
        }

        return str; 
    }
}