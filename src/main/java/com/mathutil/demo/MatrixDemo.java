package com.mathutil.demo;

import com.mathutil.ExactMatrix;
import com.mathutil.Matrix;
import com.mathutil.SimpleMatrix;

public class MatrixDemo {

	public static void main(String[] args){
		new SimpleMatrix<String>();		//Create an empty SimpleMatrix
		new Matrix();  					//Create an empty Matrix
		
		//Create a SimpleMatrix with elements, here we use String as an example
		SimpleMatrix<String> m3 = new SimpleMatrix<String>(new String[][]{
			{"ROW 1 COL 1" , "ROW 1 COL 2"},
			{"ROW 2 COL 1" , "ROW 2 COL 2"},
			{"ROW 3 COL 1" , "ROW 3 COL 2"}
		});
		m3.get(0, 0);						//Get the element at row1,col1 (indices start with 0)
		m3.set(0, 0, "NEW ROW 1 , COL 1");	//Set the element at row1,col1 to a new element
		m3.getRows();						//Get the number of rows of the matrix
		m3.getCols();						//Get the number of cols of the matrix
		m3.switchCol(0, 1); 				//Switch column 1 and column 2 in the matrix
		m3.switchRow(2, 1); 				//Switch row 2 and row 3 in the matrix
		System.out.println(m3);				//Display the matrix
		
		
		//Matrix also implements Matrixable interface, it means the methods above can also be used in Matrix
		//Here are the methods in Matrix class
		
		//Create a Matrix(A) with elements, here we use Integer as an example. The array can be any number type such as double, long, 
		//integer and so on. But they will be converted to double eventually during the calculation process.
		Matrix m4 = new Matrix(new Integer[][]{
			{1 , 2},
			{3 , 4},
		});
		//Create another matrix(B) to do calculation
		Integer[][] i = new Integer[][]{
			{5 , 6},
			{7 , 8},
		};
		Matrix m5 = new Matrix(i);
		i[0][0] = 999;						//Modify the array will not affect the value in the matrix
		m4.add(m5);	 						//A + B, the same as B + A
		m4.multiply(m5);  					//A * B, different from B * A
		m4.substract(m5);					//A - B, different from B - A
		m4.factor(1.5);						//1.5 * A, mutiply every element in the matrix by 1.5
		m4.power(-1);						//A ^ -1, invert the matrix, if the determinant is not 0
		m4.power(0);						//A ^ 0 = I, get the identity matrix
		m4.power(2);						//A ^ 2 = A * A
		m4.det();							//Get the determinant of the matrix
		m4.getMatrix()[0][0] = 99;			//Modify the array will not affect the value in the matrix
		m4.add(m5.power(-1)).multiply(m4);	//Combination, the same as (A + (B ^ -1)) * A
		m4.transpose();						//Transpose the matrix A
		m4.rref();                          //Get the rref of the matrix A
		m4.rank();                          //Find the rank of the matrix A
		System.out.println(); 			//Display the matrix A
		System.out.println(m5); 			//Display the matrix B
		
		//ExactMatrix is basically the same as Matrix, but it uses BigDecimal for calculations.
		//It has two method that Matrix does not have called setShowDecimal and setPrecision.
		//Create a ExactMatrix(A), recommend using String
		ExactMatrix em1 = new ExactMatrix(new String[][]{
			{"1","6","2"},
			{"1","8","1"},
			{"1","10","0"},
			{"1","14","2"},
			{"1","18","0"}
		});
		//Create another ExactMatrix(B), recommend using String
		ExactMatrix em2 = new ExactMatrix(new String[][]{
			{"7"},
			{"9"},
			{"13"},
			{"17.5"},
			{"18"}
		});
		
		//β = (A^T * A)^-1 * A^T * B (β, in Multiple Linear Regression), and show 8 decimal places of the result
		System.out.println(em1.transpose().multiply(em1).invert().multiply(em1.transpose()).multiply(em2).setShowDecimal(8));
	}
	
}
