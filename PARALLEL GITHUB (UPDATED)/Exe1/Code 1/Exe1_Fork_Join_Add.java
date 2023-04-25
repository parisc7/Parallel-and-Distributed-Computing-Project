/** \file      Exe1_Fork_Join_Add.java
*   \brief     It's a Parallel program thats uses fork join
*   \details   Program that sums all the numbers from 1 to 3 million
*   \author    Stavrinos Kostopoulos, Paraskeuas Kosta
*   \version   0.2
*   \date      2021-2022
*   \bug       No bugs so far
*   \copyright Stavrinos Kostopoulos, Paraskeuas Kosta (University of Nicosia) - 4th Year Course Project
*/

//USED A REDUCED MAP PROGRAM

//imports packets
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * Class <code>Exe1_Fork_Join_Add</code> is the driver 
 * which adds all numbers (1 - 3 million) of array in parallel
 * @author Stavrinos Kostopoulos
 * @author Paraskeuas Kosta
 */
public class Exe1_Fork_Join_Add extends RecursiveTask<Long> 
{
	//Initialisation variables
	private static final long serialVersionUID = 1L;
	private final long[] numbers_n;
    private final int starting;
    private final int ending;
    public static final long threshold_t = 10_000;

    /**
     * Default constructor - public
     * @param numbers_n the array of numbers included
     */
    public Exe1_Fork_Join_Add(long[] numbers_n) 
    {
        this(numbers_n, 0, numbers_n.length);
    }

    /**
     * Default constructor - private
     * @param numbers_n the array of numbers included
     * @param starting the starting point of array
     * @param ending the ending point of the array
     */
    private Exe1_Fork_Join_Add(long[] numbers_n, int starting, int ending) 
    {
        this.numbers_n = numbers_n;
        this.starting = starting;
        this.ending = ending;
    }

    /**
     * Function <code>compute</code> is the computing function 
     * equivalent to the run() of divide and conquer,
     * which is be responsible for solving the problem directly or 
     * by executing the task in parallel
     * @return Returns sum of first and last tasks (meaning two threads)
     */
    protected Long compute() 
    {

        int length_l = ending - starting;
        
        if (length_l <= threshold_t) 
        {
            return add();
        }

        //like thread A
        Exe1_Fork_Join_Add firstTask = new Exe1_Fork_Join_Add(numbers_n, starting, starting + length_l / 2);
        
        //starts asynchronously
        firstTask.fork(); 

        //like thread B
        Exe1_Fork_Join_Add secondTask = new Exe1_Fork_Join_Add(numbers_n, starting + length_l / 2, ending);

        Long secondTaskResult = secondTask.compute();
        Long firstTaskResult = firstTask.join();

        return firstTaskResult + secondTaskResult;

    }
    
    /**
     * Function <code>add</code> is the adding function that adds the numbers in the array
     * @return Returns results of the addition
     */
    private long add() 
    {
        long overall_result = 0;
        
        for (int i = starting; i < ending; i++) 
        {
        	overall_result += numbers_n[i];
        }
        
        return overall_result;
    }
    
    /**
     * Function <code>startForkJoinSum</code> is the parallelised summing function 
     * @param n the range, meaning [i.e 1 to n (range)]
     * @return Returns a new parallelised task
     */
    public static long startForkJoinSum(long n) 
    {
        long[] total_numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new Exe1_Fork_Join_Add(total_numbers);
        return new ForkJoinPool().invoke(task);
    }
    
    /**
     * Function <code>main</code> is the driver which jump-starts the
     * application 
     * @param args The default command line arguments
     */
    public static void main(String[] args) 
    {
    	 System.out.println("The summation of the numbers from 1 to 3 million IN PARALLEL is: ");
         System.out.println(Exe1_Fork_Join_Add.startForkJoinSum(3_000_000));
		
    }

}
