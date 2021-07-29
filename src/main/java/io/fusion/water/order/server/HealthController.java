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
package io.fusion.water.order.server;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

// Logging System
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDateTime;

import static java.lang.invoke.MethodHandles.lookup;


import io.fusion.water.order.OrderApplication;
import io.fusion.water.order.domainLayer.models.EchoData;
import io.fusion.water.order.domainLayer.models.EchoResponseData;

/**
 * Health Controller for the Service
 * 
 * @author arafkarsh
 * @version 1.0
 * 
 */
@Configuration
@RestController
@RequestMapping("/api")
@RequestScope
public class HealthController {

	// Set Logger -> Lookup will automatically determine the class name.
	private static final Logger log = getLogger(lookup().lookupClass());

	@Autowired
	private ServiceConfiguration serviceConfig;
	
	/**
	 * Get Method Call to Check the Health of the App
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@GetMapping("/health")
	@ResponseBody
	public ResponseEntity<String> getHealth( 
			HttpServletRequest request) throws Exception {
		System.out.println(LocalDateTime.now()+"|Request to Health of Service... ");
		if(serviceConfig == null) {
			System.out.println(LocalDateTime.now()+"|OrderService|Error Autowiring Service config!!!");
		} else {
			System.out.println(LocalDateTime.now()+"|OrderService|Version="+serviceConfig.getServerVersion());
		}
		return ResponseEntity.ok("200:Service-Health-OK");	
	}
	
	@GetMapping("/ready")
	@ResponseBody
	public ResponseEntity<String> isReady( 
			HttpServletRequest request) throws Exception {
		System.out.println(LocalDateTime.now()+"|Request to Ready Check.. ");
		if(serviceConfig == null) {
			System.out.println(LocalDateTime.now()+"|OrderService|Error Autowiring Service config!!!");
		} else {
			System.out.println(LocalDateTime.now()+"|OrderService|Version="+serviceConfig.getServerVersion());
		}
		return ResponseEntity.ok("200:Service-Ready");	
	}
	
	/**
	 * Check the Current Log Levels
	 * @return
	 */
	@GetMapping("/log")
    public String log() {
		System.out.println(LocalDateTime.now()+"|Request to Log Level.. ");
    	log.trace("OrderService|This is TRACE level message");
        log.debug("OrderService|This is a DEBUG level message");
        log.info("OrderService|This is an INFO level message");
        log.warn("OrderService|This is a WARN level message");
        log.error("OrderService|This is an ERROR level message");
        return "OrderService|See the log for details";
    }
	
	/**
	 * Restart the Service
	 */
    @PostMapping("/restart")
    public void restart() {
		System.out.println(LocalDateTime.now()+"|Request to Restart... ");

		if(serviceConfig == null) {
			System.out.println(LocalDateTime.now()+"|OrderService|Error Autowiring Service config!!!");
		} else {
			System.out.println(LocalDateTime.now()+"|OrderService|Version="+serviceConfig.getServerVersion());
		}
    	if(serviceConfig != null && serviceConfig.isServerRestart()) {
    		log.info("OrderService|Server Restart Request Received ....");
    		OrderApplication.restart();
    	}
    } 
    /**
     * Remote Echo Test
     * @param echoData
     * @return
     */
    @PostMapping("/remoteEcho")
    public ResponseEntity<EchoResponseData> remoteEcho(@RequestBody EchoData echoData) {
		System.out.println(LocalDateTime.now()+"|Request for RemoteEcho ");

    	if(serviceConfig == null) {
			System.out.println(LocalDateTime.now()+"|OrderService|Error Autowiring Service config!!!");
		} else {
			System.out.println(LocalDateTime.now()+"|OrderService|Version="+serviceConfig.getServerVersion());
    	}
		System.out.println(LocalDateTime.now()+"|OrderService|RemoteEcho Call .... "+echoData);
    	if(echoData == null) {
			return ResponseEntity.notFound().build();
		}
    	return ResponseEntity.ok(new EchoResponseData(echoData.getWord()));
    }
    
	/**
	 * Basic Testing
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("/home")
	@ResponseBody
	public String apiHome(HttpServletRequest request) {
		System.out.println(LocalDateTime.now()+"|Request to /home/ path... ");
		StringBuilder sb = new StringBuilder();
		sb.append(printRequestURI(request));
		return sb.toString();
	}
	
	/**
	 * For Testing purpose only
	 * 
	 */
	@GetMapping("/test/{service}/{ops}/**")
	@ResponseBody
	public String functions(
			@PathVariable("service") String service, 
			@PathVariable("ops") String operation,
			HttpServletRequest request) {

		StringBuilder sb = new StringBuilder();
		sb.append(printRequestURI(request));
		return sb.toString();
	}

	/**
	 * Print the Request
	 * 
	 * @param request
	 * @return
	 */
	private String printRequestURI(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		String[] req = request.getRequestURI().split("/");
		sb.append("Params Size = "+req.length+" : ");
		for(int x=0; x < req.length; x++) {
			sb.append(req[x]).append("|");
		}
 		sb.append("\n");
		log.info(sb.toString());
		return sb.toString();
	}
 }

