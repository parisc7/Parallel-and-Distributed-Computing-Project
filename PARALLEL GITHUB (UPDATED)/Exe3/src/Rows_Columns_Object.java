/** \file      RowsColsObject.java
*   \brief     Sudoku Solver
*   \details   Program that checks if the Sudoku answer is correct using threads
*   \author    Stavrinos Kostopoulos, Paraskeuas Kosta
*   \version   0.3
*   \date      2021-2022
*   \bug       No bugs so far
*   \copyright Stavrinos Kostopoulos, Paraskeuas Kosta (University of Nicosia) - 4th Year Course Project
*/

/**
 * Class <code>Rows_Columns_Object</code> 
 * Description -> initialisong our columns and rows
 * @author Stavrinos Kostopoulos
 * @author Paraskeuas Kosta
 */
public class Rows_Columns_Object extends SudokuTest 
{

	int row_r;
	int col_c;

   /**
    * Default constructor
    * general object that will be extended by worker thread objects, only contains
    * @param rows_r row that is relevant to the thread 
    * @param cols_c column that is relevant to the thread 
	*/
	Rows_Columns_Object(int rows_r, int cols_c) 
	{
		this.row_r = rows_r;
		this.col_c = cols_c;
	}
}
