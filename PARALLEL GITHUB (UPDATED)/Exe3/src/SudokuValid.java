/** \file      SudokuValid.java
*   \brief     Sudoku Solver
*   \details   Program that checks if the Sudoku answer is correct using threads (using array)
*   \author    Stavrinos Kostopoulos, Paraskeuas Kosta
*   \version   0.3
*   \date      2021-2022
*   \bug       No bugs so far
*   \copyright Stavrinos Kostopoulos, Paraskeuas Kosta (University of Nicosia) - 4th Year Course Project
*/

/**
* Class <code>SudokuValid</code> is the driver for our program
* Description -> runnable object that specifies if numbers 1-9 only appear once in a 3x3 subsection
* @author Stavrinos Kostopoulos
* @author Paraskeuas Kosta
*/
public class SudokuValid extends Rows_Columns_Object implements Runnable 
{
	
   /**
    * Default constructor
    * general object that will be extended by worker thread objects, only contains
    * @param row_r row that is relevant to the thread 
    * @param col_c column that is relevant to the thread 
	*/
	SudokuValid(int row_r, int col_c) 
	{
		super(row_r, col_c);
	}

   /**
    * Function <code>run</code> is the running function 
    * equivalent to the compute() of fork/join,
    * which is be responsible for solving the problem
    * @return Returns whether valid or not the Sudoku (based on rows/columns)
    */ 
	public void run() 
	{

		if (row_r > 6 || row_r % 3 != 0 || col_c > 6 || col_c % 3 != 0) 
		{
			System.out.println("Invalid row or column for subsection!");
			return;
		}
		
		//check for the numbers 1 to 9 if they only appear once in 3x3 subsection
		boolean[] validArray = new boolean[9];
		
		for (int i = row_r; i < row_r + 3; i++) 
		{
			for (int j = col_c; j < col_c + 3; j++) 
			{
				int num = sudokuTable[i][j];
				
				if (num < 1 || num > 9 || validArray[num - 1]) 
				{
					return;
				} 
				
				else 
					
				{
					validArray[num - 1] = true;
				}
			}
		}
		
        //if we reach here then 3x3 is indeed valid
		validation[row_r + col_c / 3] = true;
	}

}