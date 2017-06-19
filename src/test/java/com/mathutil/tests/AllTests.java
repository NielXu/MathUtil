package com.mathutil.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.mathutil.calculusTests.TestCalculus1;
import com.mathutil.calculusTests.TestCalculus2;
import com.mathutil.calculusTests.TestCalculus3;
import com.mathutil.calculusTests.TestCalculus4;
import com.mathutil.mathutilTests.*;
import com.mathutil.sigmaTests.*;

@RunWith(Suite.class)
@SuiteClasses({ 
	//Sigma tests
	TestSigma1.class, TestSigma2.class, TestSigma3.class, TestSigma4.class,
	TestSigma5.class, TestSigma6.class, TestSigma7.class , TestSigma8.class,
	TestSigma9.class, TestSigma10.class, TestSigma11.class,
	
	//Round tests
	TestRound1.class, TestRound2.class, TestRound3.class,
	
	//Dist tests
	TestDist1.class,
	
	//Calculus tests
	TestCalculus1.class, TestCalculus2.class, TestCalculus3.class, TestCalculus4.class
	})
public class AllTests {

}
