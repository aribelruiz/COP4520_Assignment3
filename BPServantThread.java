// Aribel Ruiz
// 04/13/2023

// ==========================================================================
// COP4520 : Servant Thread for Birthday Presents Party (BPServantThread.java)
// ==========================================================================
//      This program represents each of the Minotaur's servants helping him as a thread.

public class BPServantThread implements Runnable {
    // ====================================== Class Variables ======================================
    private int threadNumber;

    // ========================================= Functions =========================================

    public BPServantThread(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    @Override
    public synchronized void run() {
        System.out.println("BPServantThread number: " + threadNumber);
    }
}
