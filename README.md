# MathUtil
The MathUtil library provides useful math functions

# Functions

   1. Sigma function, using sigma notation to calculate the sum.
   2. Matrix function, create a matrix and do calculations.

# Example

An basic example about how to use sigma function:
```java
String exp = "x*(x+1)";
double result = Sigma.sum(2 , 5 , exp);
```

An example about how to use pi, e:
```java
String exp = "x*(2+e)*pi";
double result = Sigma.sum(1 , 4 , exp);
```

An example about how to use sin, cos and tan:
```java
String exp = "sin(cos(tan(x)))";
double result = Sigma.sum(1 , 10 , exp);
```

An example about how to use round in MathUtil:
```java
double rounded = MathUtil.round(1.9999 , 2); //expect 2.0
```

An example about how to create a SuperVector:
```java
SuperVector v = new SuperVector(1 , 2 , 3); //A vector with xyz components
```

An example about how to use SimpleMatrix:
```java
SimpleMatrix<String> simple = new SimpleMatrix<String>(new String[][]{
{"1","2","3"}, //row 1
{"4","5","6"}  //row 2
});
simple.switchRow(0,1); //Swicth the elements in row 1 and row 2
simple.switchCol(1,2); //Switch the elements in column 2 and column 3
```

An example about how to use CalculableMatrix:
```java
//Create a 3*3 matrix
Matrix matrix1 = new Matrix(new Integer[][]{
{1,2,3},  //row1
{4,5,6},  //row2
{7,8,9}   //row3
});
//Create a 3*3 matrix
Matrix matrix2 = new Matrix(new Integer[][]{
{1,2,3},  //row1
{4,5,6},  //row2
{7,8,9}   //row3
});
Matrix sum = matrix1.add(matrix2);               //do addition
Matrix difference = matrix1.substract(matrix2);  //do subtraction
Matrix product = matrix1.multiply(matrix2);      //do multiplication
```
For more informations about Matrix, please check out the MatrixDemo.  

# Exceptions

Please notice that there are few exceptions:  
   
      1. Sigma function  

      Case:   negative number, such as -a  
      Solved: Written explicitly in form 0-a  
      
      Case:   square root, such as sqrt(a)  
      Solved: Written explicitly in form a^(1/2)  

# Updates

   1. Version 0.0.1, added Sigma function, allow user to calculate the sum using Sigma notation. Added test cases 1-4.  
   2. Version 0.0.2, Sigma function now supports sin, cos and tan symbol. No custom variable name allowed anymore, all variable name should be x. Added test case 5-9.  
   3. Version 0.0.3, added interfaces Matrixable and CalculableMatrix, added classes Matrix and SimpleMatrix. See javadoc for more informations.  
   4. Version 0.0.4, removed CalculableMatrix interface, added factor(multiply the matrix by a numebr) method, power(raise the matrix to the power of n) method and det(determinant) method in Matrix class. Matrix class no longer extends SimpleMatrix class, but instead, it implements the Matrixable interface.  
   5. Version 0.0.5, Sigma function now supports abs symbol. Added SuperVector class and MathUtil class. Added MathUtil tests, round method test 1-3, dist method test 1-2.  
   6. Version 0.0.6, due to the accuracy loss by using double data type in calculations, I added a ExactMatrix class. This class uses BigDecimal to do the calculations which has the highest accuracy, and it implements Matrixable as well. Normally, when doing some ordinary calcualtoins, ExactMatrix class is not necessary. But when accuracy it's matter, for example, when one wants the exact result after a long and complex calculation, ExactMatrix class is necessary. Added ExactMatrix demo under MatrixDemo class. See javadoc for more informations. Added invert method in Matrix, it's the same as saying power(-1).

# Contribute

To contribute, you can added new classes. Or, you can add some tests that you think they might be helpful. To add test cases, create the cases under the corresponding package, and
then add it to the AllTests test suite.
