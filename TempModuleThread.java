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
        System.out.println("TempModuleThread number: " + threadNumber);
    }
}