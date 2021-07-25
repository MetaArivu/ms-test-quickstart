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
import test.fusion.water.order.junit5.extensions.TestTimeExtension;

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
	private int port	= 9090;
	
	// for Junit 4 use 
	// @Rule private WireMockRule wireMockRule = new WireMockRule();
	// @Rule private WireMockRule wireMockRule = new WireMockRule(
	//								options().port(8888).httpsPort(8889)
	
	// Actual Payment Service
	PaymentServiceImpl paymentService;
	
	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @BeforeAll
    public void setupAll() {
        System.out.println("== Payment Service WireMock Suite Execution Started...");
    }
    
    @BeforeEach
    public void setup() {
        System.out.println("Remote Host:Port > "+host+":"+port);

    	wireMockServer = new WireMockServer();
        configureFor(host, port);
        wireMockServer.start();
        
        System.out.println("Setting up the Payment Gateway Service: "+host+":"+port);
        PaymentGateWay gw = new PaymentGateWay(host, port);
        paymentService = new PaymentServiceImpl(gw);

    }
    
    @Test
    public void defaultTest() {
    	
    }

	// @Test
	@DisplayName("1. Payment Service HTTP Test 1")
	@Order(1)
	public void paymentServiceTest() {
	    stubFor(post("/payments")
	        .withHeader("Content-Type", containing("xml"))
	        .willReturn(ok()
	            .withHeader("Content-Type", "text/xml")
	            .withBody("<response>SUCCESS</response>")));

	    // Result result = myHttpServiceCallingObject.doSomething();
	    // assertTrue(result.wasSuccessful());

	    verify(postRequestedFor(urlPathEqualTo("/payments"))
	        .withRequestBody(matching(".*message-1234.*"))
	        .withHeader("Content-Type", equalTo("text/xml")));
	}
	
	//  @Test
	@DisplayName("2. Payment Service HTTP Test 2")
	@Order(2)
	public void paymentServiceTest2() {
	    stubFor(post("/my/resource")
	        .withHeader("Content-Type", containing("xml"))
	        .willReturn(ok()
	            .withHeader("Content-Type", "text/xml")
	            .withBody("<response>SUCCESS</response>")));

	    // Result result = myHttpServiceCallingObject.doSomething();
	    // assertTrue(result.wasSuccessful());

	    verify(postRequestedFor(urlPathEqualTo("/my/resource"))
	        .withRequestBody(matching(".*message-1234.*"))
	        .withHeader("Content-Type", equalTo("text/xml")));
	}
	
	// @Test
	@DisplayName("3. Payment Service HTTP Test 3")
	@Order(3)
	public void exactUrlOnly() {
	    stubFor(get(urlEqualTo("/some/thing"))
	            .willReturn(aResponse()
	                .withHeader("Content-Type", "text/plain")
	                .withBody("Hello world!")));
	    
	    verify(postRequestedFor(urlPathEqualTo("/some/thing"))
		        .withRequestBody(matching(".*message-1234.*"))
		        .withHeader("Content-Type", equalTo("text/plain")));

	}
	
    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }
    
	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @AfterAll
    public void tearDownAll() {
        System.out.println("== Payment Service WireMock Suite Execution Completed...");
    }
}
