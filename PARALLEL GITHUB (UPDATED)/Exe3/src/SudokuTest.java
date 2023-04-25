/** \file      SudokuTest.java
*   \brief     Sudoku Solver
*   \details   Program that checks if the Sudoku answer is correct using threads
*   \author    Stavrinos Kostopoulos, Paraskeuas Kosta
*   \version   0.3
*   \date      2021-2022
*   \bug       No bugs so far
*   \copyright Stavrinos Kostopoulos, Paraskeuas Kosta (University of Nicosia) - 4th Year Course Project
*/


/**
 * Class <code>SudokuTest</code> is the driver for our program
 * Description -> driver for a Sudoku Solver using threads to check for a correct answer
 * @author Stavrinos Kostopoulos
 * @author Paraskeuas Kosta
 */
public class SudokuTest 
{

	// gloabal constant for the number of threads
	private static final int THREAD_NUM = 27;
	
	// created the sudoku table
	public static final int[][] sudokuTable = 
		{ 
				{ 6, 2, 4, 5, 3, 9, 1, 8, 7 }, 
				{ 5, 1, 9, 7, 2, 8, 6, 3, 4 },
				{ 8, 3, 7, 6, 1, 4, 2, 9, 5 }, 
				{ 1, 4, 3, 8, 6, 5, 7, 2, 9 }, 
				{ 9, 5, 8, 2, 4, 7, 3, 6, 1 },
				{ 7, 6, 2, 3, 9, 1, 4, 5, 8 }, 
				{ 3, 7, 1, 9, 5, 6, 8, 4, 2 }, 
				{ 4, 9, 6, 1, 8, 2, 5, 7, 3 },
				{ 2, 8, 5, 4, 7, 3, 9, 1, 6 } 
		};

	// in this array the threads will update at run time
	public static boolean[] validation;

	
	/**
     * Function <code>main</code> is the driver which jump-starts the
     * application 
     * @param args The default command line arguments
     */
	public static void main(String[] args) 
	{
		validation = new boolean[THREAD_NUM];
		Thread[] thread = new Thread[THREAD_NUM];
		int threadNum = 0;

		// here we create 9 threads for 9 3x3, 9 threads for 9 columns and 9 threads for 9 rows
		for (int i = 0; i < 9; i++) 
		{
			for (int j = 0; j < 9; j++) 
			{
				if (i % 3 == 0 && j % 3 == 0) 
				{
					thread[threadNum++] = new Thread(new SudokuValid(i, j));
				}
				
				if (i == 0) 
				{
					thread[threadNum++] = new Thread(new ValidCols(i, j));
				}
				
				if (j == 0) 
				{
					thread[threadNum++] = new Thread(new ValidRows(i, j));
				}
			}
		}

		// here we start all threads
		for (int i = 0; i < thread.length; i++) 
		{
			thread[i].start();
		}

		// here we wait for all threads to finish
		for (int i = 0; i < thread.length; i++) 
		{
			try 
			{
				thread[i].join();
			} 
			
			catch (InterruptedException e) 
			
			{
				e.printStackTrace();
			}
		}

		for (int i = 0; i < validation.length; i++) 
		{
			if (!validation[i]) 
			{
				//System.out.println(sudokuTable);
				System.out.println("The Sudoku Provided By The User It Is INDEED Invalid!");
				return;
			}
		}
		
		//System.out.println(sudokuTable);
		System.out.println("The Sudoku Provided By The User It Is INDEED Valid!");
	}
}
