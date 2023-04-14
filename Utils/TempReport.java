package Utils;

import java.util.ArrayList;

// Aribel Ruiz
// 04/13/2023

// ==========================================================================
// COP4520 : Temperature Report Interface (TempReport.java)
// ==========================================================================
//      This program is the interface for a temperature report within the temperature 
//      reading module. This interface is used for solving COP4520 Assignment 3, 
//      Problem 2: Atmospheric Temperature Reading Module.

public class TempReport {
    public ArrayList<Integer> highestTemps;
    public ArrayList<Integer> lowestTemps;
    public String intervalStr;
    public int difference;

    // Used for 60-minute reports
    public TempReport(ArrayList<Integer> highTemps, ArrayList<Integer> lowTemps, String interval){
        this.highestTemps = highTemps;
        this.lowestTemps = lowTemps;
        this.intervalStr = interval;
    }

    // Used for 10-minute reports
    public TempReport(ArrayList<Integer> highTemps, ArrayList<Integer> lowTemps, String interval, int difference){
        this.highestTemps = highTemps;
        this.lowestTemps = lowTemps;
        this.intervalStr = interval;
        this.difference = difference;
    }
}
