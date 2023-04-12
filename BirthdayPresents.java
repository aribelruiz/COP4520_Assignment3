// Aribel Ruiz
// 04/13/2023

// ===========================================================================
// COP4520 : Assignment 3 - Birthday Presents Party (BirthdayPresents.java)
// ===========================================================================
//      Concepts of Parallel and Distributed Processing Assignment 3. 
//      This program simulates a solution for Problem 1 of Assignment 3, Birthday Presents Party.

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import Utils.Node;
import Utils.CardCounter;
import Utils.ConcurrentLinkedList;
import Utils.ListCounter;
import Utils.PresentCounter;

public class BirthdayPresents {

    // ====================================== Class Variables ======================================
    public static final int NUM_OF_SERVANTS = 4;
    public static final int NUM_OF_PRESENTS = 10;

    // Creating a list of sequential numbers representing all gifts in the bag
    public static List<Integer> presentList = IntStream.rangeClosed(1, 10)
    .boxed().collect(Collectors.toList());

    public static Node head = null;
    public static int task = 0;
    public static int currentServantTask = 0;

    // Creates a boolean array where server at index i is true if the last task they did was adding
    public static boolean[] servantLastAdded = new boolean[NUM_OF_SERVANTS];
    
    // ========================================= Functions =========================================
    public static void main(String[] args) {
        System.out.println("Problem 1: Birthday Presents Party!\n");

        // Shuffling present list so bag of presents is unordered
        Collections.shuffle(presentList);

        // Printing bag of presents list
        System.out.println("Present List (unsorted): ");
        for (int num : presentList) {
            System.out.print(num + " ");
        }
        System.out.println("\n");

        // Setting servantLastAdded[] to random booleans to start
        Random rand = new Random();
        int randBool;
        System.out.println("servantsLastAdded[]: ");
        for (int i = 0; i < servantLastAdded.length; i++) {
            // Gets random int (0 represents false, 1 represents true)
            randBool = rand.nextInt(2);

            if (randBool == 1){
                servantLastAdded[i] = true;
            }
            System.out.println(servantLastAdded[i]);
        }

        // ================================== TESTING LINKED LIST ==================================
        // // ================= Testing add() =================
        // System.out.println("Present List (Linked List): ");
        // for (int num : presentList) {
        //     head = ConcurrentLinkedList.add(head, num);
        // }
        // ConcurrentLinkedList.printList(head);

        // // ================= Testing remove() =================
        // System.out.println("presents before remove: " + PresentCounter.presentsInBag);
        // head = ConcurrentLinkedList.remove(head, 1);
        // head = ConcurrentLinkedList.remove(head, 10);
        // System.out.println("presents after remove: " + PresentCounter.presentsInBag);
        
        // ConcurrentLinkedList.printList(head);
        // System.out.println("");

        System.out.println("=============================== LOOP ===============================");
        
        // Loops until both the present bag and linked list are empty
        do {
    
            // Create servant threads and sets tasks
            for (int i = 0; i < NUM_OF_SERVANTS; i++) {

                // Sets task for servant (0 represents add(). 1 represents remove(), 2 represents contains())
                task = rand.nextInt(3);
                
                if (task == 0 || task == 1) {  
                    if(servantLastAdded[i] == true) {
                        // Set servant task to remove() if last task was add()
                        currentServantTask = 1;
                    }
                    else {
                        // Set servant task to be add() if last task was remove()
                        currentServantTask = 0;
                    }
                }
                else {
                    // Set servant task to check() if present is in bag
                    currentServantTask = 2;
                }
            
                // Creates and joins new servant thread
                BPServantThread servantThread = new BPServantThread(i, currentServantTask);
                Thread newThread = new Thread(servantThread);
                newThread.start();
    
                try {
                    newThread.join();
                } catch (Exception e) {
                    System.out.println("\nAn error occured joning a thread.");
                }
            }

            System.out.println("List after iteration:");
            ConcurrentLinkedList.printList(head);
            System.out.println("=========================== ITERATION ===========================");

        }
        while (PresentCounter.presentsInBag != 0 || ListCounter.presentsInList != 0);

        System.out.println("List after while:");
        ConcurrentLinkedList.printList(head);
        System.out.println("");

        System.out.println("Number of Presents: " + NUM_OF_PRESENTS);
        System.out.println("Cards Written: " + CardCounter.cardsWritten);
        System.out.println("");
    }
}
