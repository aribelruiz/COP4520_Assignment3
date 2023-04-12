package Utils;

public class CardCounter {
    // ====================================== Class Variables ======================================
    public static int cardsWritten = 0;

    public synchronized static void incrementCardsWritten() {
        cardsWritten++;
    }

}
