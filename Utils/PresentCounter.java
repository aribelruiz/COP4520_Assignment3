package Utils;

public class PresentCounter {
    // ====================================== Class Variables ======================================
    public static int presentsInBag = 10;

    // public synchronized static void incrementPresentsInBag() {
    //     presentsInBag = presentsInBag++;
    // }

    public synchronized static void decrementPresentsInBag() {
        presentsInBag--;
    }
}
