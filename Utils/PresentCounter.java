package Utils;

public class PresentCounter {
    // ====================================== Class Variables ======================================
    public static int presentsInBag = 0;

    public synchronized static void incrementPresentsInBag() {
        presentsInBag = presentsInBag+1;
    }

    public synchronized static void decrementPresentsInBag() {
        presentsInBag--;
    }
}
