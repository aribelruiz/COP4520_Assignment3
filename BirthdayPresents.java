// Aribel Ruiz
// 04/13/2023

// ===========================================================================
// COP4520 : Assignment 3 - Birthday Presents Party (BirthdayPresents.java)
// ===========================================================================
//      Concepts of Parallel and Distributed Processing Assignment 3. 
//      This program simulates a solution for Problem 1 of Assignment 3, Birthday Presents Party.

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
    public static final int NUM_OF_PRESENTS = PresentCounter.presentsInBag;
    public static boolean DEBUG = false;

    // Creating a list of sequential numbers representing all gifts in the bag
    public static List<Integer> presentList = IntStream.rangeClosed(1, NUM_OF_PRESENTS)
    .boxed().collect(Collectors.toList());

    public static ConcurrentHashMap<Integer, Integer> presentBagHash = new ConcurrentHashMap<Integer, Integer>();

    public static Node head = null;
    public static int task = 0;
    public static int currentServantTask = 0;

    // Creates a boolean array where server at index i is true if the last task they did was adding
    public static boolean[] servantLastAdded = new boolean[NUM_OF_SERVANTS];
    
    // ========================================= Functions =========================================
    public static void main(String[] args) {

        if (DEBUG)
            System.out.println("Problem 1: Birthday Presents Party!\n");

        // Shuffling present list so bag of presents is unordered
        Collections.shuffle(presentList);

        if (DEBUG) {
            // Printing bag of presents list
            System.out.println("Present List (unsorted): ");
            for (int num : presentList) {
                System.out.print(num + " ");
            }
            System.out.println("\n");
        }

        // Adding list to hash set for better complexity (checking if list contains before searching)
        for (int num : presentList) {
            presentBagHash.put(num, num);
        }

        if (DEBUG) {
            // Printing hash set
            System.out.println("Present List (Hash Set): ");
            System.out.println(presentBagHash);
            System.out.print(" ");
        }
   
        // Setting servantLastAdded[] to random booleans to start
        Random rand = new Random();
        int randBool;

        // Iterates through servantLastAdded to randomize boolean values
        for (int i = 0; i < servantLastAdded.length; i++) {
            // Gets random int (0 represents false, 1 represents true)
            randBool = rand.nextInt(2);

            if (randBool == 1){
                servantLastAdded[i] = true;
            }
        }

        if (DEBUG)
            System.out.println("=============================== LOOP ===============================");
        
        // Starts timer
        long start = System.nanoTime();

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

            if (DEBUG) {
                System.out.println("List after iteration:");
                ConcurrentLinkedList.printList(head);
                System.out.println("=========================== ITERATION ===========================");
            }
        }
        while (PresentCounter.presentsInBag != 0 || ListCounter.presentsInList != 0);

        // Ends timer
        long end = System.nanoTime();

        // Prints execution time
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.println("Execution time: " + formatter.format((end - start) / 1000000000d) + "s");
        System.out.println("Number of Servants (Threads): " + NUM_OF_SERVANTS + "\n");

        System.out.println("Number of Presents: " + NUM_OF_PRESENTS);
        System.out.println("Cards Written: " + CardCounter.cardsWritten);
        System.out.println("");

        if (DEBUG) {
            System.out.println("List after while:");
            ConcurrentLinkedList.printList(head);
            System.out.println("");
        }
    }
}
