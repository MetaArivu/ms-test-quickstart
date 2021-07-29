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
package test.fusion.water.order.pact4.tests;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import io.fusion.water.order.domainLayer.models.EchoData;
import io.fusion.water.order.domainLayer.models.EchoResponseData;
import io.fusion.water.order.domainLayer.services.PaymentService;
import io.fusion.water.order.utils.Utils;
import test.fusion.water.order.junit5.annotations.tests.Critical;
import test.fusion.water.order.junit5.annotations.tests.Functional;
import test.fusion.water.order.junit5.annotations.tools.Pact4;
import test.fusion.water.order.junit5.extensions.TestTimeExtension;
import test.fusion.water.order.utils.SampleData;

/**
 * 
 * @author arafkarsh
 *
 */
//Following Annotations Tags the Tests --------------------------------
@Pact4()
@Critical()
@Functional()
//Tagging done ---------------------------------------------------------
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(TestTimeExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "PaymentService")
@SpringBootTest(classes={io.fusion.water.order.OrderApplication.class})
public class PactContractTest {
	
	@Autowired
	PaymentService paymentService;
		
	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @BeforeAll
    public void setupAll() {
        System.out.println("== Payment Service SpringBoot/Pact HTTP Tests Started...");
    }
    
    @BeforeEach
    public void setup() {
        System.out.println("@BeforeEach: Payment Service is autowired using SpringBoot!");
    }
    
    @Pact(consumer = "OrderService")
    public RequestResponsePact remoteEcho(PactDslWithProvider builder) {
		EchoData param = new EchoData("Jane");
		EchoResponseData expectedResult = new EchoResponseData("Jane");
		return builder
			.given("remote echo")
				.uponReceiving("word")
				.path("/remoteEcho")
				.method("POST")
				.body(Utils.toJsonString(param))
			.willRespondWith()
				.status(200)
				.body(Utils.toJsonString(expectedResult))
			.toPact();
    }
	
	// @Test
	@DisplayName("1. Payment Service > Local Echo")
	@Order(1)
	public void localEcho() {
		String param = "World";
		String expectedResult = "Hello "+param;
		String result = paymentService.echo("World");
		System.out.println("@Test: Spring Boot test "+result);
        assertThat(expectedResult, org.hamcrest.CoreMatchers.equalTo(result));

	}
	
	@Test
	@DisplayName("2. Pact > Payment Service > Remote Echo")
	@Order(2)
	@PactTestFor(pactMethod = "remoteEcho", port="8080")
	public void remoteEcho(MockServer mockServer) throws IOException {
		// productServiceClient.setBaseUrl(mockServer.getUrl());

		EchoData param = new EchoData("Jane");
		EchoResponseData expectedResult =  SampleData.getEchoResponseData("Jane");
		
		EchoResponseData result = paymentService.remoteEcho(param);
        assertThat(expectedResult.getWordData(), 
        		org.hamcrest.CoreMatchers.equalTo(result.getWordData()));
  }
	
    @AfterEach
    void tearDown() {
    }

    @AfterAll
    public void tearDownAll() {
        System.out.println("== Payment Service SpringBoot/Pact HTTP Tests Completed...");
    }
    

}
