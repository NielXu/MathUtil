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

# Contribute

To contribute, you can added new classes. Or, you can add some tests that you think they might be helpful.
