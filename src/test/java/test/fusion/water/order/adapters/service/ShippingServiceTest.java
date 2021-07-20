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

package test.fusion.water.order.adapters.service;




import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.AdditionalMatchers.or;

import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;

import org.slf4j.Logger;

import io.fusion.water.order.adapters.service.DeliveryCityServiceImpl;
import io.fusion.water.order.adapters.service.ShippingServiceImpl;
import io.fusion.water.order.domainLayer.models.DeliveryCity;

import test.fusion.water.order.adapters.extensions.TestTimeExtension;

/**
 * Shipping Service Test
 * 
 * @author arafkarsh
 *
 */

@Tag("Critical")
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(TestTimeExtension.class)
@ExtendWith(MockitoExtension.class)
public class ShippingServiceTest {

	static final Logger log = getLogger(lookup().lookupClass());

	private int counter = 1;
	
	@Mock
	private DeliveryCityServiceImpl deliveryCityService;
	
	private DeliveryCity bengaluru;
	private DeliveryCity kochi;
	private DeliveryCity chennai;
	
	@InjectMocks
	private ShippingServiceImpl shippingService;
	
	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @BeforeAll
    public void setupAll() {
        System.out.println("== Shipping Service Mock Suite Execution Started...");
    }
    
    @BeforeEach
    public void setup() {
        System.out.println(counter+". Create DeliveryCity...");
        bengaluru 	= new DeliveryCity("Bengaluru", "", "India", "00000");
        kochi 		= new DeliveryCity("Kochi", "", "India", "00000");
        chennai 	= new DeliveryCity("Chennai", "", "India", "00000");

    }
    
	@Test
	@DisplayName("Test Delivery City Bengaluru")
	@Order(1)
	public void testDeliveryCity() {
		// Set the Shipping Service with Bengaluru City
		when(deliveryCityService.getDeliveryCity("Bengaluru", "", "India")).thenReturn(bengaluru);
		
		// Check the Delivery City in Shipping Service
		DeliveryCity city = shippingService.getCity("Bengaluru", "", "India");
		
		// Then Check the City
		assertEquals(bengaluru.getCityName(), city.getCityName());
	}
	
	@Test
	@DisplayName("Test Delivery Cities Bengaluru, Chennai, Kochi")
	@Order(2)
	public void testDeliveryCities() {
		// Set the Shipping Service with multiple Cities
		when(deliveryCityService.getDeliveryCity("Bengaluru", "", "India")).thenReturn(bengaluru);
		when(deliveryCityService.getDeliveryCity("Chennai", "", "India")).thenReturn(chennai);
		when(deliveryCityService.getDeliveryCity("Kochi", "", "India")).thenReturn(kochi);

		ArrayList<String> cities = new ArrayList<String>();
		cities.add("Bengaluru");
		cities.add("Chennai");
		cities.add("Kochi");
		
		// Check the Delivery City in Shipping Service
		// Arguments (numbers) to the Stub (DeliveryCityService) must be same
		ArrayList<DeliveryCity> cityList = shippingService.getCities(cities, "", "India");
		
		// Then Check the City
		assertEquals(3, cityList.size());
	}
	
	@Test
	@DisplayName("Test Delivery City Kochi - AnyString()")
	@Order(3)
	public void testDeliveryCityAnyString() {
		// Set the Shipping Service with Kochi City
		when(deliveryCityService.getDeliveryCity(anyString(), anyString(), anyString())).thenReturn(kochi);
		
		// Check the Delivery City in Shipping Service
		DeliveryCity city = shippingService.getCity("Kochi", "", "Japan");
		
		// Then Check the City
		assertEquals(kochi.getCityName(), city.getCityName());
	}
	
	@Test
	@DisplayName("Test Delivery City Kochi - eq('Kochi') ")
	@Order(4)
	public void testDeliveryCityEQString() {
		// Set the Shipping Service with Kochi City
		when(deliveryCityService.getDeliveryCity(eq("Kochi"), anyString(), anyString())).thenReturn(kochi);
		
		// Check the Delivery City in Shipping Service
		DeliveryCity city = shippingService.getCity("Kochi", "", "Japan");
		
		// Then Check the City
		assertEquals(kochi.getCityName(), city.getCityName());
	}
	
	@Test
	@DisplayName("Test Delivery City Kochi - OR Condition ")
	@Order(5)
	public void testDeliveryCityOR() {
		// Set the Shipping Service with Kochi City
		when(deliveryCityService.getDeliveryCity( or(eq("Kochi"), eq("Cochin")), anyString(), eq("India"))).thenReturn(kochi);
		
		// Check the Delivery City in Shipping Service
		DeliveryCity city = shippingService.getCity("Cochin", "", "India");
		
		// Then Check the City
		assertEquals(kochi.getCityName(), city.getCityName());
	}
	
    @AfterEach
    public void tearDown() {
        counter++;
    }
    
	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @AfterAll
    public void tearDownAll() {
        System.out.println("== Shipping Service Mock Suite Execution Completed...");
    }
	
}
