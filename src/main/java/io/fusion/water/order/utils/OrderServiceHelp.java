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

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

// Logging System
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

/**
 * 
 * @author arafkarsh
 *
 */
@Component
public class OrderServiceHelp {
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(OrderServiceHelp.class);

	private static int counter;
	
	
	public static final String NL = System.getProperty("line.separator");
	
	public static final String PADDING = "                 ";
	public static final String LINE = "                 --------------------------------------------";

	
	public static final String DL = "------------------------------------------------------------------------------------";

	public static final String VERSION = "1.0.0";
	
	public static final String LOGO = "" +NL
		+"	  ============================================" + NL
		+"    :: Order Service ::      (v"+VERSION+")" + NL
		+"	  ============================================" + NL;


	public OrderServiceHelp() {
		counter++;
	}
	
	/**
	 * Returns the Restart Counter
	 * @return
	 */
	public static int getCounter() {
		return counter;
	}
	
	@PostConstruct
	public void printDeployedAppProperties() {
		
		ArrayList<String> topics = new ArrayList<String>();
		for(String s: topics) {
			log.info(">>> List    = "+s);
		}
		HashMap<String, String> map = new HashMap<String, String>();
		for(String k : map.keySet()) {
			log.info(">>> Map Key = "+k+" | Value = "+map.get(k));
		}
 	}
}
