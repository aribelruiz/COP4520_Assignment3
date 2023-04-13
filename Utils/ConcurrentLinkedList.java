package Utils;

// Aribel Ruiz
// 04/13/2023

// ==========================================================================
// COP4520 : Linked List Implementation (LinkedList.java)
// ==========================================================================
//      This program is the implementation of a thread-safe Linked List with synchronized add, 
//      remove, contains, and print methods.

public class ConcurrentLinkedList<T> {

    // Function removes present from bag, inserts node into the linked list and returns the head
    public synchronized static Node add(Node head, int data){

        // Creates a new node with given data
        Node newNode = new Node(data);
        PresentCounter.decrementPresentsInBag();
        ListCounter.incrementPresentsInList();

        // If list is empty, make the new node the head
        if(head == null) {
            head = newNode;
        }
        else {
            // If head is greater than data, make newNode the new head
            if (data < head.data){
                newNode.next = head;
                head = newNode;                
                return head;
            }

            // traverse until you find place to insert
            Node prev = head;
            Node temp = prev.next;
            while (prev.next != null){    
                
                // if next node data is greater than data, insert data before next                
                if (prev.next.data > data){
                    
                    // Updating links for new inserted node
                    prev.next = newNode;
                    newNode.next = temp;
                    return head;
                }

                // continue traversing for a place to insert new node
                prev = temp;
                temp = prev.next;
            }

            // If data is greater than tail of linked list, append data to end of list
            if (prev.data < data) {
                prev.next = newNode;
            }
        }

        return head; 
    };

    // Function removes a node from the linked list and returns the head
    public synchronized static Node remove (Node head, int data){

        Node current = head;
        Node prev = null;

        // If the head is to be deleted, change the head
        if (current != null && current.data == data) {
            head = current.next;
            
            CardCounter.incrementCardsWritten();
            ListCounter.decrementPresentsInList();

            return head;
        }

        // If the data is not in the head, search for node with data and remove node
        while (current != null && current.data != data){
            
            // If current does not contain data, keep traversing
            prev = current;
            current = current.next;
        }

        // data should now be at current node after traversal
        if (current != null){
            // Remove current node from linked list
            prev.next = current.next;
            CardCounter.incrementCardsWritten();
            ListCounter.decrementPresentsInList();
        }
        else if (current == null){
            // data not found in list
            return head; 
        }

        return head;
    };

     // Function prints all data from nodes in a linked list
     public synchronized static void printList (Node head){
        
        Node current = head;

        while (current != null)
        {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println("");
    };
}
