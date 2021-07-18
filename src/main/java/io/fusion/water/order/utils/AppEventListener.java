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
package io.fusion.water.order.utils;
 
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

//Logging
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

/**
 * 
 * @author arafkarsh
 *
 */
@Configuration
public class AppEventListener {

	private static final Logger log = (Logger) LoggerFactory.getLogger(AppEventListener.class);
	
	/**
	 * 
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		log.info("Application is getting ready......");
	    log.info(CPU.printCpuStats());
		showLogo();
	}
	
	/**
	 * Shows AOS Logo and Deployed App Details
	 */
	public void showLogo() {
		log.info("Application is ready! ....... ..." 
				+ OrderServiceHelp.LOGO
				);
	}
}