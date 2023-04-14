package Utils;

// Aribel Ruiz
// 04/13/2023

// ==========================================================================
// COP4520 : 10-Minute Interval with Biggest Difference (Interval.java)
// ==========================================================================
//      This program keeps track of the 10-minute ineterval with the largest temperature
//      difference observed through getters and setters.

public class Interval {
    // ====================================== Class Variables ======================================
    public static int difference = 0;
    public static String intervalStr = "";
    
    // ====================================== Class Functions ======================================
   
    public synchronized static void checkDifference(int newDiff, String newInterval) {     
        if (difference < newDiff){
            System.out.println("Changing interval");
            
            // Updating difference and interval
            difference = newDiff;
            intervalStr = newInterval;
        }   
    }
}
