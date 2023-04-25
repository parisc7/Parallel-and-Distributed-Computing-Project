/** \file      Exe1_Fork_Join_Fibonacci.java
*   \brief     It's a Parallel program thats uses fork join
*   \details   Program that finds the value of the nth fibonacci number (user can change it in the main driver)
*   \author    Stavrinos Kostopoulos, Paraskeuas Kosta
*   \version   0.2
*   \date      2021-2022
*   \bug       No bugs so far
*   \copyright Stavrinos Kostopoulos, Paraskeuas Kosta (University of Nicosia) - 4th Year Course Project
*/

//USED A REDUCED MAP PROGRAM

//import Packets
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * Class <code>Exe1_Fork_Join_Fibonacci</code> is the driver 
 * which finds the nth fibonacci numbers value in parallel
 * @author Stavrinos Kostopoulos
 * @author Paraskeuas Kosta
 */
public class Exe1_Fork_Join_Fibonacci extends RecursiveAction 
{

   
	private static final long serialVersionUID = 1L;
	private static final long threshold_t = 10;
    private volatile long number_n;
    
    /**
     * Default constructor - public
     * @param number_n the fibonacci number to find its value
     */
    public Exe1_Fork_Join_Fibonacci(long number_n) 
    {
        this.number_n = number_n;
    }

    /**
     * Function <code>get_Number</code> is the getting the number function
     * @return Returns the number_n provided
     */
    public long get_Number() 
    {
        return number_n;
    }

    /**
     * Function <code>compute</code> is the computing function 
     * equivalent to the run() of divide and conquer,
     * which is be responsible for solving the problem directly or 
     * by executing the task in parallel
     * @return Returns number_n (n) as a sum of f1 and f2 numbers (meaning the two threads)
     */
    protected void compute() 
    {
    	// n number
        long n = number_n;
        
        if (n <= threshold_t) 
        {
        	//fibonacci function kicks in to do its job
            number_n = fibbonacci(n);
        } 
        
        else 
        	
        {
        	//like thread A
        	Exe1_Fork_Join_Fibonacci f1 = new Exe1_Fork_Join_Fibonacci(n - 1);
        	
        	//like thread B
        	Exe1_Fork_Join_Fibonacci f2 = new Exe1_Fork_Join_Fibonacci(n - 2);
        	
            ForkJoinTask.invokeAll(f1, f2);
            number_n = f1.number_n + f2.number_n;
        }
    }

    /**
     * Function <code>startForkJoinSum</code> is the fibonacci function 
     * which essentially sums the -1 and -2 position of a given number 
     * @param n the number
     * @return Returns sum of the n number
     */
    private static long fibbonacci(long n) 
    {
        if (n <= 1) 
        {
        	return n;
        }
        
        else 
        	
        {
        	//once number (n) provided, 
        	//will sum the values of the -1 AND -2 positions of it (of the number provided)
        	return fibbonacci(n - 1) + fibbonacci(n - 2);
        }
    }
    
    /**
     * Function <code>main</code> is the driver which jump-starts the
     * application 
     * @param args The default command line arguments
     */
    public static void main(String[] args) 
    {

    	Exe1_Fork_Join_Fibonacci test = new Exe1_Fork_Join_Fibonacci(30);
        new ForkJoinPool().invoke(test);
        System.out.println("The value of the Fibonacci nth number provided, which is " + test.number_n + ", is indeed:");
        System.out.println(test.get_Number());

    }

}