package com.mathutil.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.mathutil.mathutilTests.*;
import com.mathutil.sigmaTests.*;

@RunWith(Suite.class)
@SuiteClasses({ 
	TestSigma1.class, TestSigma2.class, TestSigma3.class, TestSigma4.class,
	TestSigma5.class, TestSigma6.class, TestSigma7.class , TestSigma8.class,
	TestSigma9.class, TestSigma10.class,
	TestRound1.class, TestRound2.class, TestRound3.class,
	TestDist1.class
	})
public class AllTests {

}
