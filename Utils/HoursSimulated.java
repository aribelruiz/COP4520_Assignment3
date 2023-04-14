package Utils;

public class HoursSimulated {
    // ====================================== Class Variables ======================================
    public static int hourSimulated = 0;

    // ====================================== Class Functions ======================================
    public synchronized static void incrementHourSimulated() {
        hourSimulated++;
        System.out.println("HOURS SIMULATED: " + hourSimulated);
    }

}
