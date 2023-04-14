import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import Utils.HoursSimulated;
import Utils.TempStorage;

// Aribel Ruiz
// 04/13/2023

// ==========================================================================================
// COP4520 : Sensor Thread forAtmospheric Temperature Reading Module (TempModuleThread.java)
// ==========================================================================================
//      This program represents each sensor of the temperature module as a thread.

public class TempModuleThread implements Runnable {
    // ====================================== Class Variables ======================================
    private int threadNumber;

    // ========================================= Functions =========================================

    public TempModuleThread(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    @Override
    public synchronized void run() {
        // Thread Number used for testing
        System.out.println("TempModuleThread number: " + threadNumber);
        System.out.println("hours: " + HoursSimulated.hourSimulated);
        System.out.println("readings: " + TempStorage.tempReadings.size());
        System.out.println();

        // Increment HoursSimulated every hour
        if ((TempStorage.tempReadings.size() - (HoursSimulated.hourSimulated * 60)) == 0){
            HoursSimulated.incrementHourSimulated();
        }

        // Randomly generating temperature and adding to tempReadings
        int randomTemp = ThreadLocalRandom.current().nextInt(-100, 70);

        // Adds temperature recorded to tempReadings array shared by all threads
        TempStorage.tempReadings.add(randomTemp);

        if (TempStorage.tempReadings.size() - (HoursSimulated.hourSimulated * 60) == 0){
            
            int startIndex = (HoursSimulated.hourSimulated - 1) * 60;
            int endIndex = (HoursSimulated.hourSimulated * 60) - 1;

            System.out.println(startIndex + " and " + endIndex);
            
            List<Integer> tempReadings60Min = TempStorage.tempReadings.subList(startIndex, endIndex);
            TempStorage.createReport(tempReadings60Min, startIndex, endIndex);
        }

    }
}