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
CalculableMatrix matrix1 = new Matrix(new Integer[][]{
{1,2,3},  //row1
{4,5,6},  //row2
{7,8,9}   //row3
});
//Create a 3*3 matrix
CalculableMatrix matrix2 = new Matrix(new Integer[][]{
{1,2,3},  //row1
{4,5,6},  //row2
{7,8,9}   //row3
});
CalculableMatrix sum = matrix1.add(matrix2);               //do addition
CalculableMatrix difference = matrix1.substract(matrix2);  //do subtraction
CalculableMatrix product = matrix1.multiply(matrix2);      //do multiplication
```

# Exceptions

Please notice that there are few exceptions:  
   
      1. Sigma function  

      Case:   negative number, such as -a  
      Solved: Written explicitly in form 0-a  
      
      Case:   square root, such as sqrt(a)  
      Solved: Written explicitly in form a^(1/2)  

# Updates

   1. Version 0.0.1, added Sigma function, allow user to calculate the sum using Sigma notation. Added test cases 1~4.  
   2. Version 0.0.2, Sigma function is now support sin, cos and tan symbol. No custom variable name allowed, all variable name should be x. Added test case 5~9.  
   3. Version 0.0.3, added interfaces Matrixable and CalculableMatrix, added classes Matrix and SimpleMatrix. See javadoc for more informations.  

# Contribute

To contribute, you can added new classes. Or, you can add some tests that you think they might be helpful.
