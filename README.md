# Multi-Threaded Practice 2
Below you can find the ReadMe for two separate problems, Birthday Presents Party and Temperature Module. 

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
The program for problem 2 simulates the design of a module responsible for measuring the atmospheric temperature of the next-generation Mars Rover, equipped with a multicore CPU and 8 temperature reading sensors. The module is simulated by using concurrent threads; where each thread represents a temperature reading sensor. The program simulated the data storage and retrieval of memory shared among all the threads. The output of the program is in the console and is a series of print statements showing the simulation.

### Atmospheric Temperature Reading Module Problem
A module must be designed responsible for measuring the atmospheric temperature of the next-generation Mars Rover. This next-generation Mars Rover is equipped with a multicore CPU and 8 temperature sensors responsible for collecting temperature readings at regular intervals. The temperature readings are to be stored in a shared memory space and the atmospheric temperature module has to compile a report at the end of every hour. The report compiled must include the top 5 highest temperatures recorded for that hour, the top 5 lowest temperatures recorded for that hour, and the 10-minute interval of time when the largest temperature difference was observed. 

The data storage and retrieval of the shared memory region must be carefully handled and no sensors should be delayed and miss the interval of time when it is supposed to conduct a temperature reading. 

The temperature readings are taken every 1 minute and the solution must be designed using 8 threads.

## Summary of Approach (Problem 2)
My approach involves having each temperature sensor represented by a thread. To simulate each sensor recording a temperature, I simulated one minute as 1 second. Each thread enacts a wait() of 1 second before starting to simulate time within the temperature module. To store the temperature readings, the reading is added to a concurrent ArrayList shared among all of the threads. To simulate the memory among threads, I created a TempStorage class with synchronized methods for checking how many readings have been made, getting report stats from a 10-minute interval, and creating a report for a 60-minute interval time.

### Coding Approach (Problem 2)
The module currently runs in an infinite while loop, representing the 8 sensor threads constantly reading temperatures. When a TempModuleThread begins after sleeping a second, a random number is generated and then added to an arrayList named tempReadings within the TempStorage class. The size of tempReadings is then checked to see if it is divisible by 60. If tempReadings is divisible by 60, a synchronized counter called hourSimulated in the HoursSimulated class is incremented to represent a 60-minute interval that has passed.

Once a 60-minute interval passes, a sublist is created containing all of the temperature readings recorded within the 60-minute interval. this sublist is then used to create a report for the hour interval using a function called createReport() within the TempStorage class.

The createReport() function creates multiple smaller reports for each 10-minute interval within it. Each report is stored within an interface called TempReport. The TempReport interface contains the top highest temperatures recorded within the interval as a List, the 5 lowest temperatures recorded within the interval as a List, a string representing the 10-minute interval, and an integer containing the largest difference of temperature found within the interval. Using the 10-minute temperature reports, a separate value for the largest difference between temperatures is updated as new 10-minute temperature reports are created containing greater differences. A separate string representing the 10-minute interval with the greatest temperature difference is also updated when a 10-minute report is found with a greater difference. The highest and lowest temperatures recorded for each 10-minute interval are also stored in a separate list of the highest temperatures recorded within the hour as new 10-minute reports are created.

Once all of the 10-minute reports are created, I use the Collections.sort() function on the array containing the hour's highest and lowest temperatures. Once this array is sorted in logarithmic time, I create a sublist of the bottom 5 indexes which contain the 5 lowest temperatures recorded within the 60-minute interval. I also create a sublist of the highest 5 indexes which contains the 5 highest temperatures recorded within the 60-minute interval. Once all this information is gathered on the 60-minute window, a separate temperature report for the 60-minute interval is created containing the highest 5 temperatures recorded as a List, the lowest 5 temperatures recorded as a List, and a string containing the 10-minute interval with the greatest difference between temperatures. Once the 60-minute report is created, it is printed onto the screen.

All of the threads can add to the tempReadings array when they first record a temperature, so there is no delay in recording and adding the temperature to the tempReadings array. But, separating the storage methods for obtaining interval stats through the getReportStats() method within the TempStorage class and creating reports separately ensures that the data retrieval is more carefully and accurately handled as these methods are all synchronized.

## Experimental Evaulation (Problem 2)
To test my program I first tested that the threads were working properly. After setting up the threads I made all of my utilities for the program which included Interval.java, HoursSimulated.java, TempReport, and TempStorage. I ensured the Interval, HoursSimulated, and TempReports all worked properly by writing print statements within the functions to ensure that they were running when I intended and updating values as intended. After ensuring those utilities worked properly, I began to test the TempStorage functions. Due to a lack of time management, I was unable to fully test TempStorage and its functions to the best of my abilities. 

I first included print statements to ensure that my functions returned and did as intended. As I ran my program I ran into casting errors when trying to get the sublist from an ArrayList and set it to a different ArrayList. I realized a sublist returns a List, not an Arraylist, so I had to change the data type of some variables from an ArrayList to a list. Once I did this, I started receiving an error that I believe to be improperly attaining the sublist. Although the ArrayList I tried to obtain a sublist from was not empty and the starting and ending index of the ArrayList were valid, the sublist function kept returning a null List. I'm unsure why this happened and I was unable to solve this error to complete my program.

## Reasoning for Efficiency (Problem 1)
To improve efficiency I made the most of ArrayLists for dynamic lists instead of static ones. This improved the space complexity of the program when storing reports or temperarure readings.

I also made use of functions in Collections, such as Collections.sort(), for better runtimes on functions. Instead of sorting through an array to find the highest and lowest temperatures, I sorted the arrays using Collections.sort for a logarithmic runtime.

I made sure to do checks before getting temperature readings or creating reports to ensure these functions only ran when a 60-minute interval had passed instead of when every thread began.


