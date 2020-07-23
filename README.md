# MyDistributedSystemsProjects

Lamport Algorithm :
The algorithm of Lamport timestamps is a simple algorithm used to determine the order of events in a distributed computer system.
As different nodes or processes will typically not be perfectly synchronized, this algorithm is used to provide a partial ordering of events with minimal overhead, and conceptually provide a starting point for the more advanced vector clock method. 
I have 3 processes, namely P1, P2 and P3. 
	There are 2 threads of each process, one thread assigned for sending and one for receiving. 
	First, I am splitting a sentence into words and then each process sends each of those words to itself and then to the other two processes enforcing multicasting. 
	When P1 is sending (for example), it’s incrementing its own logical clock by 1. Similarly, When P2 is sending, it’s incrementing its own logical clock by 2. And, when P3 is sending, it’s incrementing its own logical clock by 3. 
	When a process is receiving, let’s take for example P1 is sending data to P2, then P1 is also sending its own logical time along with the data to P2. 
	Before executing an event, Pi executes Ci ← Ci + 1.
	When process Pi sends a message m to Pj, it sets m’s timestamp ts (m) equal to Ci after having executed the previous step.
	Upon the receipt of a message m, process Pj adjusts its own local counter as Cj ← max {Cj, ts (m)}, after which it then executes the first step and delivers the message to the application.
	The outcome of this implementation is that: events occurred at different processes will appear in the same order at each individual process, thus enforcing totally ordered multicasting. 
	Challenges to implementation were implementing multithreading, sending and receiving the logical clock and sorting etc. 

Vector Clock Algorithm:
A vector clock is an algorithm for generating a partial ordering of events in a distributed system and detecting causality violations. Just as in Lamport timestamps, interprocess messages contain the state of the sending process's logical clock.
A vector clock of a system of N processes is an array/vector of N logical clocks, one clock per process; a local "smallest possible values" copy of the global clock-array is kept in each process, with the following rules for clock updates:
•	Initially all clocks are zero.
•	Each time a process experiences an internal event, it increments its own logical clock in the vector by one.
•	Each time a process sends a message, it increments its own logical clock in the vector by one (as in the bullet above, but not twice for the same event) and then sends a copy of its own vector.
•	Each time a process receives a message, it increments its own logical clock in the vector by one and updates each element in its vector by taking the maximum of the value in its own vector clock and the value in the vector in the received message (for every element).
multicasting is being done as was being done in the assignment 1 (using 2 threads for each process).
	The same sentence is being broken into words which are being sent and received by each receiver.
	Now, each process has a vector which is first initialized to [0, 0, 0]
	Each time any of the processes is sending a word, it is incrementing its own logical clock in the vector by 1. So, if P1 is sending to P2, P1’s logical clock in the vector becomes [1, 0, 0].
	Every time a process is receiving a word, it’s also receiving the sender’s vector along with the word. Then the receiving process is incrementing its own logical clock in the vector by one and updating each element in its vector by taking the maximum of the value in its own vector clock and the value in the vector in the received message. Therefore, before sending a message, Pi executes VCi [i] ← VCi [i] + 1. Upon the receipt of a message m, process Pj adjusts its own vector by setting VCj [k] ← max {VCj [k], ts (m)[k]} for each k and delivers the message to the application.
	Advantages of this implementation is that it enforces causality and by using more space, can also identify concurrent events.
	Challenges in implementing this theorem were:
	Implementing the vector for each process.
	Ensuring that the 2 threads (for each process) access the same instance of the vector.
	VCj [k] ← max {VCj [k], ts (m)[k]}, comparing and finding out the maximum values and updating them later. 

Centralised Distributed Locking Scheme:
The purpose of a lock is to ensure that among several nodes that might try to do the same piece of work, only one actually does it (at least only one at a time).
That work might be to write some data to a shared storage system, to perform some computation, to call some external API, or suchlike.
	This Centralized distributed locking scheme ensures that there is a centralized unit, the coordinator or the server (in our case), which does most of the work whether it’s keeping the information of whether the file is currently free or blocked and which process/client should it give the file next to access. Here, in this assignment, we use the concept of First Come First Serve to decide which process would be the next to access the file. The processes just sit and wait till they get access to the file.
	I am making use of 5 classes here, 3 classes to implement 3 clients/processes. One Server class, and 2 classes to maintain hash maps.
	The purpose of the 2 hash maps is very simple and I am using hash maps to facilitate easy implementation of my centralized locking. One hash map is used to check if anybody is currently accessing the file/ blocking the file. Other hash map is used to check how many times each process has accessed the file to ensure that it has only accessed the file a predefined number of times (5 in my case).
	So, the server is accepting the connections and it is making sure that any client is able to connect to the server.
	A client will keep on trying to access the file till it updates the counter in the file a predefined number of times which is 5. 
	Once, a client has access to the file, it opens the file, increments the counter inside the file and closes the file. All other clients which might be trying to access the file during this time are blocked and not provided the access. Also, each time a client is trying to access the file, it creates a new thread and once it updates the counter 5 times, it stops making the threads. 
	A file which is currently accessing the file makes its value in the key-value pair in the hash map as True. After its work is done, it makes the value as False, providing an indication to the server that the file is free and the server can give access to the next client on first come first serve basis. 
	It is worth noting that ANY process/client can connect to the server while the file is being accessed by some process but only when that process (which is accessing the file) has completed its job and has made the value in the hash map as False, would the processes trying to get access to the file can actually get access to the file with server’s permission as explained above, therefore serving the purpose of both centralized algorithm as well as locking. 
	Challenges to implementation were ensuring that no two threads could access the file at the same time and also maintaining the counter so that it exits when it hits the predefined limits. 







