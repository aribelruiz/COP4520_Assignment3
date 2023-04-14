package Utils;

// Aribel Ruiz
// 04/13/2023

// ==========================================================================
// COP4520 : Temperature Storage (TempStorage.java)
// ==========================================================================
//      This program keeps track of all the temperature recordings within a 60 minute window.

import java.util.*;

public class TempStorage {
    // ====================================== Class Variables ======================================
    public static ArrayList<Integer> tempReadings = new ArrayList<Integer>();
    public static int hourSimulated = 1;

    // This array list stores all of the temperature reports every hour
    public static List<TempReport> allReports = Collections.synchronizedList(new ArrayList<TempReport>());
    
    // ====================================== Class Functions ======================================
   
    // Function checks if temp readings for 60 minutes has been stores to create a report
    public synchronized static void checkTempReadings(ArrayList<Integer> copyTempReadings) {

        // Check if a new hour is complete
        if (copyTempReadings.size() - (hourSimulated * 60) == 0){
            
            // Full new hour has been completed. Increment hourSimulated
            hourSimulated++;
            
            // Getting sublist to create report
            int startIndex = (hourSimulated - 1) * 60;
            int endIndex = (hourSimulated * 60) - 1;
            ArrayList<Integer> tempReadings60Min = (ArrayList<Integer>) copyTempReadings.subList(startIndex, endIndex);
            
            // Creating a report for the hour 
            createReport(tempReadings60Min, startIndex, endIndex);
        }
    }

    // Function creates a report using given list
    public static void createReport(ArrayList<Integer> tempReadings60Min, int ogStartIndex, int ogEndIndex) {

        int tenMinIntervals = 0;
        int startIndex = 0;
        int endIndex = 0;

        int largestDifference = 0;
        String biggestInterval = "";

        ArrayList<Integer> topTempsForHour = new ArrayList<Integer>();
        ArrayList<Integer> bottomTempsForHour = new ArrayList<Integer>();

        for (int i = 0; i < tempReadings60Min.size(); i++) {
            // Every multiple of 10 represents end of 10 minute interval
            if (((10 - i) / 10) == 0) {

                // incrementing tenMinIntervals
                tenMinIntervals++;

                // Getting start and end index
                startIndex = (tenMinIntervals - 1) * 10;
                endIndex = (tenMinIntervals * 10) - 1;

                // Creating subList for 10 minute interval and calling getReportStats
                ArrayList<Integer> tempReadings10Min = (ArrayList<Integer>) tempReadings60Min.subList(startIndex, endIndex);
                TempReport tempReport10 = getReportStats(tempReadings10Min, ogStartIndex + startIndex, ogEndIndex + endIndex);

                // Update largest difference if new interval has larger difference in temperatures
                if (largestDifference < tempReport10.difference){
                    largestDifference = tempReport10.difference;
                    biggestInterval = tempReport10.intervalStr;
                }
                else if(biggestInterval == "") {
                    // Biggest interval has yet to be initialized. Initialize interval
                    biggestInterval = tempReport10.intervalStr;
                }

                // Adding top 5 and bottom 5 temps to topTempsForHour and bottomTempsForHour
                topTempsForHour.addAll(tempReport10.highestTemps);
                bottomTempsForHour.addAll(tempReport10.highestTemps);

                // Sorting topTempsForHour and bottomTempsForHour to get top and bottom 5 for hour
                Collections.sort(topTempsForHour);
                Collections.sort(bottomTempsForHour);

                // Getting top 5 and bottom 5 temps for hour
                // Gets top 5 highest temperatures
                ArrayList<Integer> top5ForHour = new ArrayList<Integer>();
                if (topTempsForHour.size() >= 5){
                    int end = topTempsForHour.size() - 1; 
                    int start = topTempsForHour.size() - 5;
                    top5ForHour = (ArrayList<Integer>) topTempsForHour.subList(start, end);
                }

                // Gets bottom 5 lowest temperatures
                ArrayList<Integer> bottom5ForHour = new ArrayList<Integer>();
                if (bottomTempsForHour.size() >= 5){
                    int start = 0;
                    int end = 4; 
                    bottom5ForHour = (ArrayList<Integer>) bottomTempsForHour.subList(start, end);
                }
                
                // Creating report for the hour
                TempReport hourReport = new TempReport(top5ForHour, bottom5ForHour, biggestInterval);

                // Printing hourly report
                System.out.println("=============================== Hourly Report ===============================");
                System.out.println("Top 5 Highest Temperatures: " + hourReport.highestTemps.toString());
                System.out.println("Top 5 Lowest Temperatures: " + hourReport.lowestTemps.toString());
                System.out.println("10-minute Interval with Greatest Temperature Difference: " + hourReport.intervalStr);
                System.out.println("");
            }
        }
    }

    // Function gets report stats of given sublist of 10 minute interval
    public static TempReport getReportStats(ArrayList<Integer> tempReadings10Min, int ogStartIndex, int ogEndIndex) {
        
        // Gets interval and 10 minute difference
        String interval = "[ " + ogStartIndex + ", " + ogEndIndex + " ]";
        Integer difference = Collections.max(tempReadings10Min) - Collections.min(tempReadings10Min);
        
        Collections.sort(tempReadings10Min);  

        // Gets top 5 highest temperatures
        ArrayList<Integer> top5 = new ArrayList<Integer>();
        if (tempReadings10Min.size() >= 10){
            top5 = (ArrayList<Integer>) tempReadings10Min.subList(5, 9);
        }

        // Gets bottom 5 highest temperatures
        ArrayList<Integer> bottom5 = new ArrayList<Integer>();
        if (tempReadings10Min.size() >= 5){
            bottom5 = (ArrayList<Integer>) tempReadings10Min.subList(0, 4);
        }

        // Create a report for 10 minute interval
        TempReport tempReport10Min = new TempReport(top5, bottom5, interval, difference);

        return tempReport10Min;
    }

}
