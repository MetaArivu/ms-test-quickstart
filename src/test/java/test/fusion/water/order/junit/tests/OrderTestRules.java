package test.fusion.water.order.junit.tests;
/**
 * (C) Copyright 2021 Araf Karsh Hamid 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport;

import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestName;
import org.junit.rules.Timeout;

import org.junit.Rule;

import static org.junit.Assert.assertThat;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.io.File;
import java.io.IOException;





import io.fusion.water.order.domainLayer.models.OrderEntity;

/**
 * Order Test Rules Suite
 * 
 * @author arafkarsh
 *
 */
@EnableRuleMigrationSupport
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderTestRules {

	private OrderEntity order;
	
	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @BeforeAll
    public void setupAll() {
        System.out.println("== Order Test Rules Suite Execution Started...");
    }
    
    
	/**
	 * Following from Junit4 - As part of the Vintage Package
	 * Field Level Rules introduced in JUnit 4.7
	 * Class Level Rules introduced in JUnit 4.9
	 */
	@Rule
	private TemporaryFolder tmpFolder1 = new TemporaryFolder();
	
	/**
	 * From JUnit v4.13 assuredDeletion ensures that tmp files/folders 
	 * are removed and if that fails then you will get an
	 * assertion failure.
	 */
	@Rule
	private TemporaryFolder tmpFolder2 = TemporaryFolder.builder().assureDeletion().build();
	
	/**
	 * The Error Collector rule allows the execution of a test to 
	 * continue after one or more problems are found:
	 */
	@Rule
	private ErrorCollector collector = new ErrorCollector();
	
	/**
	 * This Rule is configured in anticipation of an Exception Thrown
	 * in the tests. 
	 * E**
	 */
	@Rule
	private ExpectedException thrown = ExpectedException.none();
	
	/**
	 * This Returns the currently running Test Method Name
	 * E**
	 */
	@Rule
	private TestName testName = new TestName();
	
	/**
	 * Rule Must Timed out after the specified seconds
	 */
	@Rule
	private Timeout timeout = Timeout.seconds(3);

    @BeforeEach
    public void setup() throws IOException {
        System.out.println("Setup Rules.....");
			tmpFolder1.create();
			tmpFolder2.create();
    }
	
	@Test
	@DisplayName("Rule Test - Temporary Folder")
    public void folderTest()  {
    	try {
			File newFile = tmpFolder2.newFile("MyNewFile.txt");
			File newDir = tmpFolder2.newFolder("MyNewFolder");
			System.out.println(newFile.getAbsolutePath());
			System.out.println(newDir.getAbsolutePath());

    	} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Rule Test - Error Collector")
    public void test() {
        collector.checkThat("a", equalTo("b"));
        collector.checkThat(1, equalTo(2));
        collector.checkThat("c", equalTo("c"));
    }

    @Test
 	@DisplayName("Rule Test - ExpectedException : No Error")
    public void throwsNothing() {
    }
     
    @Test
  	@DisplayName("Rule Test - ExpectedException : Error Thrown")
    public void throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        try {
        	throw new NullPointerException();
        } catch (Exception e) {
        	collector.addError(e);
        	collector.checkThat(e.getMessage(), is(equalTo("NullPointerException")));
        }
    }
    
    @Test
    @DisplayName("Rule Test - TestName") 
    public void ruleTestName() {
    	System.out.println(">> TestName="+testName.getMethodName());
    	assertThat("ruleTestName",is(equalTo(testName.getMethodName())));
    }

    @Test
    @DisplayName("Rule Test - Timeout R1") 
    public void testTimeout1() throws InterruptedException {
    	long start = System.currentTimeMillis();
    	Thread.sleep(1000);
    	System.out.println("Time Taken R1 = "+(System.currentTimeMillis()-start));
    }
    
    @Test
    @DisplayName("Rule Test - Timeout R2") 
    public void testTimeout2() throws InterruptedException {
    	long start = System.currentTimeMillis();
    	Thread.sleep(5000);
    	System.out.println("Time Taken R2 = "+(System.currentTimeMillis()-start));
    }
    
    @AfterEach
    public void tearDown() {
        System.out.println("Should Execute After Each Test");
    }

	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @AfterAll
    public void tearDownAll() {
        System.out.println("== Order Test Rules Suite Execution Completed...");
    }
}
