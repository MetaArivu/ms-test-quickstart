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
package io.fusion.water.order.adapters.external;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import io.fusion.water.order.domainLayer.models.PaymentDetails;
import io.fusion.water.order.domainLayer.models.PaymentStatus;
import io.fusion.water.order.utils.Utils;

/***
 * 
 * @author arafkarsh
 *
 */
@Service
public class PaymentGateWay {

	
	@Value("${remote.host}")
	private String host = "localhost";
	@Value("${remote.port}")
	private int port = 8080;
	
	private String payments 	= "/payments";
	private String remoteEcho 	= "/remoteEcho";

	private String gwBaseURL;
	private String paymentURL;
	private String echoURL;

	private boolean urlsSet = false;
	
	@Autowired
	private PaymentGateWayRestTemplate gw = new PaymentGateWayRestTemplate();
	
	/**
	 * 
	 */
	public PaymentGateWay() {
		setURLs();
	}
	
	/**
	 * Set the Payment GateWay
	 */
	public PaymentGateWay(String _host, int _port) {
		host = _host;
		port = _port;
		setURLs();
	}
	
	/**
	 * Set the Base URLs
	 */
	private void setURLs() {
		if(!urlsSet) {
			gwBaseURL = "http://" + host + ":" + port;
			paymentURL = gwBaseURL + payments;
			echoURL = gwBaseURL + remoteEcho;
			urlsSet = true;
			System.out.println("PaymentGateway Service Initialize...............");
			System.out.println("REMOTE  |> "+paymentURL+"/");
			System.out.println("REMOTE  |> "+echoURL+"/");
		}
	}

	/**
	 * Do a Remote Echo - For Testing Purpose ONLY
	 * 
	 * @param _word
	 * @return
	 */
	public String remoteEcho(String _word) {
		setURLs();
		System.out.println("REQUEST |> "+Utils.toJsonString(_word));
	    // Set Headers
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("sessionId", UUID.randomUUID().toString());
	    headers.add("app", "bigBasket");

	    List<String> cookies = new ArrayList<>();
	    cookies.add("token="+UUID.randomUUID().toString());
	    cookies.add("domain=arafkarsh.com");
	    headers.put(HttpHeaders.COOKIE, cookies);
	    
		HttpEntity<String> request = new HttpEntity<String>
											(_word, headers);
		System.out.println("REQUEST |> "+Utils.toJsonString(request));

		// Call Remote Service > POST
		String response = gw.postForObject(echoURL, request, String.class);

		System.out.println("RESPONSE|> "+Utils.toJsonString(response));
		
		return response;
	}
	
	/**
	 * Process Payments
	 * @param _paymentDetails
	 * @return
	 */
	public PaymentStatus processPayments(PaymentDetails _paymentDetails) {
		setURLs();
		System.out.println("REQUEST |> "+Utils.toJsonString(_paymentDetails));
	    // Set Headers
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("sessionId", UUID.randomUUID().toString());
	    headers.add("app", "bigBasket");

	    List<String> cookies = new ArrayList<>();
	    cookies.add("token="+UUID.randomUUID().toString());
	    cookies.add("domain=arafkarsh.com");
	    headers.put(HttpHeaders.COOKIE, cookies);
	    
		HttpEntity<PaymentDetails> request = new HttpEntity<PaymentDetails>
											(_paymentDetails, headers);
		System.out.println("REQUEST |> "+Utils.toJsonString(request));

		// Call Remote Service > POST
		PaymentStatus ps = gw.postForObject(paymentURL, request, 
											PaymentStatus.class);

		System.out.println("RESPONSE|> "+Utils.toJsonString(ps));
		
		return ps;
	}
}
