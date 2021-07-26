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
package test.fusion.water.order.wiremock2.tests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;

import com.github.tomakehurst.wiremock.WireMockServer;

import io.fusion.water.order.adapters.external.PaymentGateWay;
import io.fusion.water.order.adapters.service.PaymentServiceImpl;
import io.fusion.water.order.domainLayer.models.PaymentDetails;
import io.fusion.water.order.domainLayer.models.PaymentStatus;
import io.fusion.water.order.utils.Utils;
import test.fusion.water.order.junit5.extensions.TestTimeExtension;
import test.fusion.water.order.utils.SampleData;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Wire Mock with JUnit 5
 * 
 * @author arafkarsh
 *
 */
@Tag("WireMock")
@Tag("Critical")
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(TestTimeExtension.class)
public class PaymentGateWayMockTest {

	// WireMock Server for Junit 5 
	private WireMockServer wireMockServer;
	
	@Value("${remote.host}")
	private String host	= "localhost";
	
	@Value("${remote.port}")
	private int port	= 8080;
	
	// Actual Payment Service
	PaymentServiceImpl paymentService;
	private static int counter=1;
	
    @BeforeAll
    public void setupAll() {
        System.out.println("== Payment Service WireMock HTTP Tests Started...");
    	// Setup WireMock Server (Defaults to Port 8080)
    	wireMockServer = new WireMockServer();
        wireMockServer.start();        
        System.out.println(counter+"] WireMock Server Started.. on "
        					+wireMockServer.baseUrl());
    }
    
    @BeforeEach
    public void setup() {
        // Initialize Payment Service with Payment Gateway
        PaymentGateWay gw = new PaymentGateWay(host, port);
        paymentService = new PaymentServiceImpl(gw);

    }
	
	@Test
	@DisplayName("1. Payment Service HTTP : Accepted")
	@Order(1)
	public void paymentServiceTest1() {

		PaymentDetails pd = SampleData.getPaymentDetails();
	    PaymentStatus ps = SampleData.getPaymentStatusAccepted(
	    		pd.getTransactionId(), pd.getTransactionDate());
		
	    // Given
	    stubFor(post("/payments")
		    .withRequestBody(equalToJson(Utils.toJsonString(pd)))
		    .willReturn(okJson(Utils.toJsonString(ps))));

	    // When
	    PaymentStatus payStatus = paymentService.processPayments(pd);

	    // Then
	    assertNotNull(payStatus);
	    assertEquals("Accepted", payStatus.getPaymentStatus());

	    // Verify
	    verify(postRequestedFor(urlPathEqualTo("/payments"))
		        .withRequestBody(equalToJson(Utils.toJsonString(pd)))
		        .withHeader("Content-Type", equalTo("application/json")));

	}
	
	@Test
	@DisplayName("2. Payment Service HTTP : Declined")
	@Order(2)
	public void paymentServiceTest2() {

		PaymentDetails pd = SampleData.getPaymentDetails();
	    PaymentStatus ps = SampleData.getPaymentStatusDeclined(
	    		pd.getTransactionId(), pd.getTransactionDate());
		
	    // Given
	    stubFor(post("/payments")
		    .withRequestBody(equalToJson(Utils.toJsonString(pd)))
		    .willReturn(okJson(Utils.toJsonString(ps))));

	    // When
	    PaymentStatus payStatus = paymentService.processPayments(pd);

	    // Then
	    assertNotNull(payStatus);
	    assertEquals("Declined", payStatus.getPaymentStatus());

	    // Verify
	    verify(postRequestedFor(urlPathEqualTo("/payments"))
		        .withRequestBody(equalToJson(Utils.toJsonString(pd)))
		        .withHeader("Content-Type", equalTo("application/json")));
	}
	
    @AfterEach
    void tearDown() {
        counter++;
    }

    @AfterAll
    public void tearDownAll() {
        wireMockServer.stop();
        System.out.println("== Payment Service WireMock HTTP Tests Completed...");
    }
	
	/**
	// @Test
	@DisplayName("1. Payment Service HTTP Test 1")
	@Order(1)
	public void paymentServiceTes10t() {

		PaymentDetails pd = SampleData.getPaymentDetails();
	    PaymentStatus ps = SampleData.getPaymentStatusAccepted(
	    		pd.getTransactionId(), pd.getTransactionDate());
		
	    stubFor(post("/payments")
		    .withRequestBody(equalToJson(
		    		"{\n"
		    		+ "    \"transactionId\": \"fb908151-d249-4d30-a6a1-4705729394f4\",\n"
		    		+ "    \"transactionDate\": \"2021-07-25\",\n"
		    		+ "    \"orderValue\": 230.0,\n"
		    		+ "    \"paymentType\": \"CREDIT_CARD\"\n"
		    		+ "}"))
		    .willReturn(okJson("{\n"
		    		+ "    \"transactionId\": \"fb908151-d249-4d30-a6a1-4705729394f4\",\n"
		    		+ "    \"transactionDate\": \"2021-07-25\",\n"
		    		+ "    \"paymentStatus\": \"Accepted\",\n"
		    		+ "    \"paymentReference\": \"b58dbe48-6d6a-4c37-b054-aaa0fd970bdd\",\n"
		    		+ "    \"paymentDate\": \"2021-07-25\",\n"
		    		+ "    \"paymentType\": \"CREDIT_CARD\"\n"
		    		+ "}")));

	    PaymentStatus payStatus = paymentService.processPayments(pd);
	    assertNotNull(payStatus);
	    
	    verify(postRequestedFor(urlPathEqualTo("/payments"))
		        .withRequestBody(equalToJson("{\n"
			    		+ "    \"transactionId\": \"fb908151-d249-4d30-a6a1-4705729394f4\",\n"
			    		+ "    \"transactionDate\": \"2021-07-25\",\n"
			    		+ "    \"orderValue\": 230.0,\n"
			    		+ "    \"paymentType\": \"CREDIT_CARD\"\n"
			    		+ "}")));
	}
	*/
	//  @Test
	@DisplayName("3. Payment Service HTTP Test 2")
	@Order(3)
	public void paymentServiceTest10() {
	    stubFor(post("/my/resource")
	        .withHeader("Content-Type", containing("xml"))
	        .willReturn(ok()
	            .withHeader("Content-Type", "text/xml")
	            .withBody("<response>SUCCESS</response>")));


	    verify(postRequestedFor(urlPathEqualTo("/my/resource"))
	        .withRequestBody(matching(".*message-1234.*"))
	        .withHeader("Content-Type", equalTo("text/xml")));
	}
	
	// @Test
	@DisplayName("4. Payment Service HTTP Test 3")
	@Order(4)
	public void exactUrlOnly() {
	    stubFor(get(urlEqualTo("/some/thing"))
	            .willReturn(aResponse()
	                .withHeader("Content-Type", "text/plain")
	                .withBody("Hello world!")));
	    
	    verify(postRequestedFor(urlPathEqualTo("/some/thing"))
		        .withRequestBody(matching("Hello world!"))
		        .withHeader("Content-Type", equalTo("text/plain")));

	}
	
}
