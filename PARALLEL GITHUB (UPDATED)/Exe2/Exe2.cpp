/** \file      Exe2.cpp
*   \brief     It's a concurrent program that uses two condition variables
*   \details   Program that keeps generating data forever, unless you manually kill it through the terminal of yours
*   \author    Stavrinos Kostopoulos, Paraskeuas Kosta
*   \version   0.2
*   \date      2021-2022
*   \bug       No bugs so far (No starvation, no deadlocks, no race conditions)
*   \copyright Stavrinos Kostopoulos, Paraskeuas Kosta (University of Nicosia) - 4th Year Course Project
*/

#include <queue>
#include <thread>
#include <iostream>
#include <mutex>
#include <condition_variable>

using namespace std;

std::queue<int> dataQueue;
std::mutex queue_Mutex;
std::mutex queue_Mutex2;
std::condition_variable queue_Condition_Variable;
std::condition_variable queue_Condition_Variable2;

/**
 * Function <code>producer_Function</code> is the producers function.
 * <BR>
 */
void producer_Function() 
{
	//Function that keeps generating data forever unless you manually kill it
	int sleep_for_Seconds;
	int new_Number;
	int other_Number;

	std::cout << "---------------------------------------------------------------------------------------------" << endl;
	std::cout << "PARALLEL COMPUTING - PROJECT 1 / PROGRAM 2 - CONCURRENT PROGRAMMING USING CONDITION VARIABLES" << endl;
	std::cout << "---------------------------------------------------------------------------------------------";
	std::cout << endl;

	while (true) 
	{
		//Waits from 1 to 5 secs before generating any new data
		sleep_for_Seconds = rand() % 5 + 1;
		std::this_thread::sleep_for(std::chrono::seconds(sleep_for_Seconds));

		//Adds num to our queue
		new_Number = rand() % 100 + 1; //Random num from 1 all the way to 100
		other_Number = rand() % 100 + 1; //Random num from 1 all the way to 100
		std::lock_guard<std::mutex> g(queue_Mutex);
		dataQueue.push(new_Number);
		dataQueue.push(other_Number); 
		std::cout << endl;

		std::cout << "Added Numbers To Queue Are The Following Ones: " << new_Number << ", " << other_Number << std::endl;

		//Notifies one thread that the condition variables have the possibility of a change
		queue_Condition_Variable.notify_one();
		queue_Condition_Variable2.notify_one();
	}

	//We don't have to test the post condition because it's a void funtion, which is not returning something
}

/**
 * Function <code>consumer_Function</code> is the consumers function.
 * <BR>
 */
void consumer_Function() 
{
	//Function that consumes data forever unless you manually kill it
	while (true) 
	{
		int number_To_Process = 0;

		/*Only need to lock mutex for the time it takes us to pop item out. 
		Adding this scope, it releases the lock right after item is popped*/
		{
			/*Condition variables need unique_lock rather  a lock_guard, cause
			mutex might be locked and unlocked numerous times. By default, this
			line will lock the mutex*/
			std::unique_lock<std::mutex> g(queue_Mutex);
			std::unique_lock<std::mutex> g2(queue_Mutex2);

			/*This call to `wait` will firstly check if contions are met. 
			For instance, If queue is not empty. Now, If queue not empty, the execution of code will continue onwards
			If queue is empty, it will unlock the mutex and wait until a signal is sent to the condition variables. 
			When signal sent, it will acquire the lock and check the conditions again!
			Also, WE DO NEED BOTH OF THEM, OTHERWISE IT WILL CRASH*/
			queue_Condition_Variable.wait(g, [] { return !dataQueue.empty(); });
			queue_Condition_Variable2.wait(g2, [] { return !dataQueue.empty(); });

			/*Don't need to check if queue is empty anymore, 
			because the Condition Variables do that for us*/
			number_To_Process = dataQueue.front();
			dataQueue.pop();
		}

		//Proceeds onwards ONLY if numbers are available
		if (number_To_Process)
		{
			std::cout << "Now Processing Number: " << number_To_Process << std::endl;
		}
	}

	//We don't have to test the post condition because it's a void funtion, which is not returning something
}

//Main Driver
/**
 * Function <code>main</code> is the driver function that starts the programme.
 * <BR>
 * @return Returns <code>0</code> on success, any other value otherwise.
 */
int main() 
{
	std::thread producer_1(producer_Function);
	std::thread consumer_1(consumer_Function);

	producer_1.join();
	consumer_1.join();

	system("pause");
	return 0;
}