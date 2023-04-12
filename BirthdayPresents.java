// Aribel Ruiz
// 04/13/2023

// ===========================================================================
// COP4520 : Assignment 3 - Birthday Presents Party (BirthdayPresents.java)
// ===========================================================================
//      Concepts of Parallel and Distributed Processing Assignment 3. 
//      This program simulates a solution for Problem 1 of Assignment 3, Birthday Presents Party.

public class BirthdayPresents {

    // ====================================== Class Variables ======================================
    public static final int NUM_OF_SERVANTS = 4;
    
    // ========================================= Functions =========================================
    public static void main(String[] args) {
        System.out.println("Problem 1: Birthday Presents Party!");

        for (int i = 0; i < NUM_OF_SERVANTS; i++) {
            
            // Creates and joins new servant thread
            BPServantThread servantThread = new BPServantThread(i);
            Thread newThread = new Thread(servantThread);
            newThread.start();

            try {
                newThread.join();
            } catch (Exception e) {
                System.out.println("\nAn error occured joning a thread.");
            }
        }
    }
}
