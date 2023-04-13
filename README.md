# COP4520_Assignment3 (Two Parts)
Below you can find the ReadMe for Problem 1 and Problem 2 of Assignment 3 for Concepts of Parallel and Distributed Processing. 

# Problem 1: Birthday Presents Party
## How to Run Program 1 (Problem 1)
This program was written using java. To run problem 1, cd into the appropriate directory of the project and enter the following into the command line:

    javac BirthdayPresents.java && java BirthdayPresents

## Program Description (Problem 1)
The program for problem 1 simulates a strategy for successfully writing the proper number of thank you cards corresponding to the total number of presents in the ' Birthday Presents Party' problem by implementing a concurrent linked list that helps the servants complete the task. The output on the command line is the execution time of the program, the number of servants (represented by threads),k the number of presents brought to the party, and the total number of cards written at the end of the problem.

### Birthday Presents Party Problem
In the 'Birthday Presents Party' problem, all presents brought to the party have a tag with a unique number associated with the guest who brought them. All the presents are thrown into a bag in no particular order. The presents must be taken from the unordered bag to create a linked list of presents that are linked in incrementing order.

Four servants are asked to assist in creating the chain of presents and writing thank you cards. Each servant must do one of three actions in no particular order: 1. Take a present from the unordered bag and add it to the linked list. 2. Write a thank you card and remove a present from the chain. 3. Check whether a gift with a particular tag is currently in the chain of presents or not. The servants are also asked to alternate adding gifts to the ordered chain and writing thank you cards. 

## Summary of Approach (Problem 1)
My approach to problem 1 is to create a custom concurrent linked list and node class that allows for multiple threads to add, remove, or search a linked list synchronously without creating any deadlocks or interruptions.

- The add() method iterates through the chain and adds the present in the respective location by setting the previous and following node's 'next' parameter to point to the proper nodes.

- The remove() method iterates through the chain to search for the present to be removed. Once the present is found, the previous and following node's 'next' parameter is set to point to the proper nodes.

- The contains() method iterates through the chain to search for the present to be found. If the present is found, the method returns true; false otherwise.

I also implemented synchronized counters for the total number of presents in the bag and the total number of presents in the chain to determine when the program should end. The program ends when both the total number of presents in the bag and in the chain is 0.

### Coding Approach
I represented each servant assisting the Minotaur at the Birthday Presents party as a thread. Each thread is created and passed a thread number ***k*** and an integer ***currentServantTask*** representing the servant task. The integer 0 represents the task of adding a gift to the chain and removing it from the bag. The integer 1 represents removing the present from the chain and writing a thank you card. The integer 2 represents checking whether or not a particular present is currently in the chain of presents.

I created a boolean array of length ***NUM_OF_SERVANTS*** called ***servantLastAdded*** to start the alternation between adding and removing gifts from the chain. 

When creating a servant thread (BPServantThread), a random number is generated and given as a parameter to the BPServantThread. If the servant is assigned the task of adding a present (represented by 0) or removing a present (represented by 1), the boolean array ***servantLastAdded*** is checked to assure the servant adds the present if the last add/remove task they did was remove; or assures that the servant removes a present if the last add/remove task they did was add. Once a servant is assigned an add or remove task, the servantLastAdded array is updated to true at the servant's index if the servant's new given task is added; false otherwise. If the servant is given the checkPresent task, the BPServantThread is given a task value of 2.

Once the servant threads are created and assigned tasks, I used the join() method to assure that the servants must finish their task before another iteration of assigning and starting threads. This way, servant 0 will not be doing two tasks simultaneously.

Within the BPServantThread run() method, there is a switch case that calls the appropriate function depending on the task assigned to the thread. The functions that may be called are addPresent(), removePresent(), or checkPresent(). Each of these functions calls respective synchronized add(), remove(), and contains() methods within the ConcurrentLinkedList class. The synchronized methods allow for different threads to access the linked list without any conflict. The add(), remove(), and contains() methods also update the synchronized counter variables for presentsInBag and presentsInList within the Utils folder.

The program runs within a do-while loop, so if the presentsInBag counter or the presentsInList counter does not equal zero, the program will go through another iteration of setting and running tasks until the bag of presents and the chain of presents are both empty.

## Experimental Evaulation (Problem 1)
I first tested the program with a smaller number of presents, such as 10, to assure that the algorithm was working correctly. I initially tested all of my concurrent linked list methods functioned properly by manually executing the add(), remove(), and contains() methods and printing the linked list afterward using a printList() method to ensure I was getting the expected results. 

After ensuring all of my linked list methods worked properly, I began testing them within my thread's run method to ensure that my methods were synchronized and working properly while multiple threads tried accessing them. Using print statements I ensured the list was updating properly whenever a remove or add method was executed, and that executing the contains() method made no effect on the actual content of the linked list.

After ensuring the concurrent linked list worked correctly, I began to ensure that the simulation of servant tasks was working correctly Using print statements I ensured that the servant tasks alternated correctly so that servants whose last add/remover task was add made sure to do the remove task instead and vice-versa.

To ensure my counter variables were synchronized and updated properly, I printed them after updating them to ensure the number was correct. At the end of the program, I printed both the total number of presents brought and the total number of cards written to ensure the values matched and the counters updated properly. This final print also ensures that the program finished when it was supposed to and that the proper number of cards were written depending on how many presents were brought to the party.

After testing with smaller numbers of presents brought, like 10, I began to test with larger numbers such as 100, 1000, 100000, and 500000. I also updated the total number of servants to check if multiple threads ran faster and if fewer threads ran slower.

## Reasoning for Efficiency (Problem 1)
To improve efficiency, I only ever removed the head of the linked list when calling the removePresent() method within BPServantThread. I removed only the head when removing a present from the list because this avoids having to iterate through the entire list to remove the tail of the list or any other present in between. Removing the head is an O(1) operation because you only have to update the head to be the next present in the list.

Adding a present to the linked list takes O(N) in the worst-case runtime. The efficiency of the add() method could not be improved because the chain of presents must be traversed to search for the correct place to insert the present for the chain to be sorted in incrementing order.

To improve the runtime of the contains() and delete() methods, I created a hash map containing the numbers associated with the presents within the bag. A hash map has O(1) lookup, so to improve the efficiency of contains() and delete(), I checked to see if the present is in the bag of presents before traversing. If the present is in the bag of presents, there is no need to traverse the list because the present will not be in the list. Therefore, anytime the contains() method is called by a thread, the contains() method only traverses through the list if the present is not in the bag. This improves the efficiency of the program because, in scenarios where the present is absent from the list, the contains() method will take O(1) instead of O(N) to search the entire list and find nothing.

# Problem 2: Atmospheric Temperature Reading Module
## How to Run Program 2 (Problem 2)
This program was written using java. To run problem 2, cd into the appropriate directory of the project and enter the following into the command line:

    javac TemperatureModule.java && java TemperatureModule

## Program Description (Problem 2)
The program for problem 2 simulates the design of a module responsible for measuring the atmospheric temperature of the next-generation Mars Rover, equipped with a multicore CPU and 8 temperature reading sensors. The module is simulated by using concurrent threads; where each thread represents a temperature reading sensor. The program simulated the data storage and retrieval of memory shared among all the threads.

### Atmospheric Temperature Reading Module Problem
A module must be designed responsible for measuring the atmospheric temperature of the next-generation Mars Rover. This next-generation Mars Rover is equipped with a multicore CPU and 8 temperature sensors responsible for collecting temperature readings at regular intervals. The temperature readings are to be stored in a shared memory space and the atmospheric temperature module has to compile a report at the end of every hour. The report compiled must include the top 5 highest temperatures recorded for that hour, the top 5 lowest temperatures recorded for that hour, and the 10-minute interval of time when the largest temperature difference was observed. 

The data storage and retrieval of the shared memory region must be carefully handled and no sensors should be delayed and miss the interval of time when it is supposed to conduct a temperature reading. 

The temperature readings are taken every 1 minute and the solution must be designed using 8 threads.

## Summary of Approach (Problem 2)

