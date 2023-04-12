import java.util.Random;

import Utils.ConcurrentLinkedList;
import Utils.ListCounter;
import Utils.PresentCounter;

// Aribel Ruiz
// 04/13/2023

// ==========================================================================
// COP4520 : Servant Thread for Birthday Presents Party (BPServantThread.java)
// ==========================================================================
//      This program represents each of the Minotaur's servants helping him as a thread.

public class BPServantThread implements Runnable {
    // ====================================== Class Variables ======================================
    private int threadNumber;
    private int task;

    // ========================================= Functions =========================================

    public BPServantThread(int threadNumber, int task) {
        this.threadNumber = threadNumber;
        this.task = task;
    }

    @Override
    public synchronized void run() {
        System.out.println("\nBPServantThread number: " + threadNumber);
        
        // Servant executes given task 
        switch(task) {
            case 0:
                // Updating servantLastAdded array to true because servant is doing add()
                BirthdayPresents.servantLastAdded[threadNumber] = true;
                addPresent();
                break;
            
            case 1:
                // Updating servantLastAdded array to true because servant is doing add()
                BirthdayPresents.servantLastAdded[threadNumber] = false;
                removePresent();
                break;
           
            default: 
                checkPresent();
        }
    }

    // Function takes a present from unordered bag and adds it to linked list
    public void addPresent() {

        System.out.println("Servant " + threadNumber + " doing add()");
        System.out.println("presentsInBag: " + PresentCounter.presentsInBag);

        if (PresentCounter.presentsInBag > 0) {
            // Getting present from unordered list and remove from unordered list
            int present = BirthdayPresents.presentList.get(0);
            BirthdayPresents.presentList.remove(0);

            // Prints for TESTING
            System.out.println("Servant " + threadNumber + " adding " + present);
            System.out.println("Present List (unsorted): ");
            for (int num : BirthdayPresents.presentList) {
                System.out.print(num + " ");
            }

            // Adding present from unordered list to linked list
            BirthdayPresents.head = ConcurrentLinkedList.add(BirthdayPresents.head, present);

            // Prints of TESTING
            System.out.println("\nPresent List (Linked List): ");
            ConcurrentLinkedList.printList(BirthdayPresents.head);
        }
    }

    // Function takes a present from head of linked list and removes it (writes thank you card)
    public void removePresent() {

        System.out.println("Servant " + threadNumber + " doing remove()");

        if (ListCounter.presentsInList > 0) {
            int present = BirthdayPresents.head.data;

            System.out.println("Servant " + threadNumber + " removing " + present);
            BirthdayPresents.head = ConcurrentLinkedList.remove(BirthdayPresents.head, present);
        }
    }

    // Function checks if random present is in linked list
    public void checkPresent() {

        System.out.println("Servant " + threadNumber + " doing check()");

        Random rand = new Random(); 
        int present = rand.nextInt(BirthdayPresents.NUM_OF_PRESENTS);

        boolean presentInList = ConcurrentLinkedList.contains(BirthdayPresents.head, present);

        if (presentInList){
            System.out.println("Present #" + present + " FOUND in linked list.");
        }
        else {
            System.out.println("Present #" + present + " NOT FOUND in linked list.");
        }

    }
}
