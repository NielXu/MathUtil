package com.mathutil.linearalgebra;

/**
 * The solution cases.
 * <ul>
 * <li>INFINITE - There are infinite solutions</li>
 * <li>ONLY - There is only one solution</li>
 * <li>UNSOLVALBE - The system is unsolvable and has no solutions</li>
 * <li>ZEROS - The only solution is all zeros</li>
 * </ul>
 * @author danielxu
 *
 */
public enum SolutionCase {
	
	/**There are infinite solutions**/
	INFINITE,
	
	/**There is only one solution**/
	ONLY,
	
	/**There are no solutions**/
	UNSOLVABLE,
	
	/**The only solution is all 0**/
	ZEROS,
	
}
