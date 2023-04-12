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
import Utils.ConcurrentLinkedList;
import Utils.PresentCounter;

public class BirthdayPresents {

    // ====================================== Class Variables ======================================
    public static final int NUM_OF_SERVANTS = 4;
    public static final int NUM_OF_GUESTS = 10;

    public static Node head = null;
    public static int presentsInBag = 0;
    
    // ========================================= Functions =========================================
    public static void main(String[] args) {
        System.out.println("Problem 1: Birthday Presents Party!\n");

        // Creating a list of sequential numbers representing all gifts in the bag
        List<Integer> presentList = IntStream.rangeClosed(0, 10)
        .boxed().collect(Collectors.toList());

        // Shuffling present list so bag of presents is unordered
        Collections.shuffle(presentList);

        // Printing bag of presents list
        System.out.println("Present List (unsorted): ");
        for (int num : presentList) {
            System.out.print(num + " ");
        }
        System.out.println("\n");

        // ================================== TESTING LINKED LIST ==================================
        // // ================= Testing add() =================
        // System.out.println("Present List (Linked List): ");
        // for (int num : presentList) {
        //     head = ConcurrentLinkedList.add(head, num);
        // }
        // ConcurrentLinkedList.printList(head);

        // // ================= Testing remove() =================
        // System.out.println("presents before remove: " + PresentCounter.presentsInBag);
        // head = ConcurrentLinkedList.remove(head, 10);
        // System.out.println("presents after remove: " + PresentCounter.presentsInBag);
        // System.out.println("");
        
        for (int i = 0; i < NUM_OF_SERVANTS; i++) {
            
            // Creates and joins new servant thread
            BPServantThread servantThread = new BPServantThread(i);
            Thread newThread = new Thread(servantThread);
            newThread.start();

            try {
                newThread.join();
            } catch (Exception e) {
                System.out.println("\nAn error occured joning a thread.");
            }
        }
    }
}
