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
For more informations about all the features, please check out the Demo class.

# Updates

Updates informations move to the Versions Updates folder.

# Contribute

To contribute, you can added new classes. Or, you can add some tests that you think they might be helpful. To add test cases, create the cases under the corresponding package, and
then add it to the AllTests test suite.
Feel free to issues if you have any problems :)
