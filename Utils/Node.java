package Utils;

// Aribel Ruiz
// 04/13/2023

// ==========================================================================
// COP4520 : Node Interface for Linked List (Node.java)
// ==========================================================================
//      This program is the interface for a node within a linked list. This interface is used for
//      solving COP4520 Assignment 3, Problem 1: Birthday Presents Party.

public class Node {
    public int data;
    public volatile Node next;

    public Node(int num){
        this.data = num;
        this.next = null;
    }
}
