package org.springdemo.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springdemo.test.v1.V1AllTests;
import org.springdemo.test.v2.V2AllTests;

@RunWith(Suite.class)
@SuiteClasses({V1AllTests.class,V2AllTests.class})
public class AllTests {

}
