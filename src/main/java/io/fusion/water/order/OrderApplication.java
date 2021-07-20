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
package io.fusion.water.order;

import java.util.Arrays;

import javax.annotation.PostConstruct;
// import java.util.logging.Logger;
import javax.servlet.MultipartConfigElement;

import org.springframework.boot.ApplicationArguments;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

//Logging System
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

/**
 * Order Service - Spring Boot Application
 * 
 * @author arafkarsh
 *
 */
@Controller
@EnableScheduling
@ServletComponentScan
@RestController
@SpringBootApplication(scanBasePackages = { "io.fusion.water.order.*" })
public class OrderApplication {

	private static final Logger log = (Logger) LoggerFactory.getLogger(OrderApplication.class);

	private final String title = "<h1>Welcome to Order Service<h1/>"
								+"<h3>Copyright (c) MetaArivu Pvt Ltd, 2021</h3>";
	
	private static ConfigurableApplicationContext context;
	
	/**
	 * Start the Application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Start the Server
		start(args);
	}
	
	/**
	 * Start the Server
	 * @param args
	 */
	public static void start(String[] args) {
		log.info("Booting Order Service ..... ..");
		try {
			context = SpringApplication.run(OrderApplication.class, args);
			log.info("Booting Order Service ..... ...Startup completed!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Restart the Server
	 */
    public static void restart() {
    	
		log.info("Restarting Order Service ..... .. 1");
        ApplicationArguments args = context.getBean(ApplicationArguments.class);
		log.info("Restarting Order Service ..... .. 2");

        Thread thread = new Thread(() -> {
            context.close();
            start(args.getSourceArgs());
        });
		log.info("Restarting Order Service ..... .. 3");

        thread.setDaemon(false);
        thread.start();
    }
	
	/**
	 * Load the Configuration 
	 */
	@PostConstruct
	public void configure() {
		// Loading Integration Config
	}
	
	/**
	 * Order Service - Home Page
	 * @return
	 */
	@GetMapping("/")
	public String home() {
		System.out.println("Request to Home Page... ");
		return this.title;
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			log.debug("Inspect the beans provided by Spring Boot:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				log.debug(beanName);
			}
		};
	}
	
	/**
	 * Returns the REST Template
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * Returns the Object Mapper
	 * @return
	 */
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	/**
	 * All file upload till 512 MB
	 * returns MultipartConfigElement
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofBytes(500000000L));
		return factory.createMultipartConfig();
	}
}