package com.mathutil.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.mathutil.mathutilTests.TestDist1;
import com.mathutil.mathutilTests.TestDist2;
import com.mathutil.mathutilTests.TestRound1;
import com.mathutil.mathutilTests.TestRound2;
import com.mathutil.mathutilTests.TestRound3;
import com.mathutil.sigmaTests.TestSigma1;
import com.mathutil.sigmaTests.TestSigma10;
import com.mathutil.sigmaTests.TestSigma2;
import com.mathutil.sigmaTests.TestSigma3;
import com.mathutil.sigmaTests.TestSigma4;
import com.mathutil.sigmaTests.TestSigma5;
import com.mathutil.sigmaTests.TestSigma6;
import com.mathutil.sigmaTests.TestSigma7;
import com.mathutil.sigmaTests.TestSigma8;
import com.mathutil.sigmaTests.TestSigma9;

@RunWith(Suite.class)
@SuiteClasses({ 
	TestSigma1.class, TestSigma2.class, TestSigma3.class, TestSigma4.class,
	TestSigma5.class, TestSigma6.class, TestSigma7.class , TestSigma8.class,
	TestSigma9.class, TestSigma10.class,
	TestRound1.class, TestRound2.class, TestRound3.class,
	TestDist1.class, TestDist2.class
	})
public class AllTests {

}
