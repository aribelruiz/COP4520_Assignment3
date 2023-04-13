package Utils;

public class PresentCounter {
    // ====================================== Class Variables ======================================
    public static int presentsInBag = 500000;

    public synchronized static void decrementPresentsInBag() {
        if (presentsInBag > 0)
            presentsInBag--;
    }
}
