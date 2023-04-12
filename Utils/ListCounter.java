package Utils;

public class ListCounter {
    // ====================================== Class Variables ======================================
    public static int presentsInList = 0;

    public synchronized static void incrementPresentsInList() {
        presentsInList++;
    }

    public synchronized static void decrementPresentsInList() {
        if (presentsInList > 0)
            presentsInList--;
    }
}
