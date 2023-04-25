/** \file      ValidCols.java
*   \brief     Sudoku Solver
*   \details   Program that checks if the Sudoku answer is correct using threads
*   \author    Stavrinos Kostopoulos, Paraskeuas Kosta
*   \version   0.3
*   \date      2021-2022
*   \bug       No bugs so far
*   \copyright Stavrinos Kostopoulos, Paraskeuas Kosta (University of Nicosia) - 4th Year Course Project
*/

/**
 * Class <code>ValidCols</code> 
 * Description -> checking if columns valid
 * @author Stavrinos Kostopoulos
 * @author Paraskeuas Kosta
 */
public class ValidCols extends Rows_Columns_Object implements Runnable 
{
	
	 /**
	    * Default constructor
	    * general object that will be extended by worker thread objects, only contains
	    * @param row_r row that is relevant to the thread 
	    * @param col_c column that is relevant to the thread 
		*/
	ValidCols(int row_r, int col_c) 
	{
		super(row_r, col_c);
	}

	 /**
	    * Function <code>run</code> is the running function 
	    * equivalent to the compute() of fork/join,
	    * which is be responsible for solving the problem
	    * @return Returns whether the column's valid or not 
	    */ 
	public void run() 
	{
		if (row_r != 0 || col_c > 8) 
		{
			System.out.println("Invalid row or column for col ");
			return;
		}
		
		//check for the numbers 1 to 9 if they only appear once in a column subsection
		boolean[] validArray = new boolean[9];
		int i;
		
		for (i = 0; i < 9; i++) 
		{

			int num = sudokuTable[i][col_c];
			
			if (num < 1 || num > 9 || validArray[num - 1]) 
			{
				return;
			} 
			
			else if (!validArray[num - 1]) 
			{
				validArray[num - 1] = true;
			}
		}
		
		//if we reach here then column subsection is valid
		validation[18 + col_c] = true;
	}
}