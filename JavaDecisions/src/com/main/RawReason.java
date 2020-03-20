package com.main;

public class RawReason {
    private String group;
    private int internalCat;
    private String reason;
    private int weight;

    static int allReasons = 0;

    private int reasonID; 

    public RawReason(String group, int internalCat, String reason, int weight) {
        this.group = group;
        this.internalCat = internalCat;
        this.reason = reason;
        this.weight = weight;
        reasonID = allReasons++; 
    }

    //getter methods
    public String getGroup() {
        return group; 
    }
    
    public int getInternalCategory() {
        return internalCat; 
    }

    public String getReason() {
        return reason; 
    }

    public int getWeighting() {
        return weight;
    }

    public int getID() {
        return reasonID; 
    }

    //setter methods
    public void setGroup(String group) {
        this.group = group; 
    }

    public void setInternalCategory(int internalCat) {
        this.internalCat = internalCat;
    }

    public void setWeighting(int weight) {
        this.weight = weight; 
    }

    public String toString() {
        return ("GROUP: " + group + ", CAT: " + internalCat + ", ID: " + reasonID + "\n\tREASON: " + reason + "\n\tWEIGHT: " + weight); 
    }
}