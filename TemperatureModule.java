// Aribel Ruiz
// 04/13/2023

// ========================================================================================
// COP4520 : Assignment 3 - Atmospheric Temperature Reading Module (TemperatureModule.java)
// ========================================================================================
//      Concepts of Parallel and Distributed Processing Assignment 3. 
//      This program simulates a solution for Problem 2 of Assignment 3, Atmospheric Temperature 
//      Reading Module.


public class TemperatureModule {
    // ====================================== Class Variables ======================================
    public static final int NUM_OF_SENSORS = 8;

    // ========================================= Functions =========================================
    public static void main(String[] args) {
        System.out.println("Problem 2: Temperature Reading Module!");

        for (int i = 0; i < NUM_OF_SENSORS; i++) {
            
            // Creates and joins new servant thread
            TempModuleThread servantThread = new TempModuleThread(i);
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
