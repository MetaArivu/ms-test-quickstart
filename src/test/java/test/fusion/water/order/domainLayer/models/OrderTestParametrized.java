package test.fusion.water.order.domainLayer.models;
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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Order;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;


import io.fusion.water.order.domainLayer.models.Customer;
import io.fusion.water.order.domainLayer.models.OrderEntity;
import io.fusion.water.order.utils.Utils;
import test.fusion.water.order.adapters.extensions.PrintTestDuration;
import test.fusion.water.order.adapters.extensions.TestTimeExtension;
import test.fusion.water.order.core.VariableSource;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;
import org.slf4j.Logger;

/**
 * Order Test Suite
 * 
 * @author arafkarsh
 *
 */
@Tag("Critical")
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// @PrintTestDuration
@ExtendWith(TestTimeExtension.class)
public class OrderTestParametrized {
	
	static final Logger log = getLogger(lookup().lookupClass());

	private OrderEntity order;
	private int counter = 1;
	
	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @BeforeAll
    public void setupAll() {
        System.out.println("== Order Parametrized Suite Execution Started...");
    }
    
    @BeforeEach
    public void setup() {
        System.out.println(counter+". Create an Empty Order..");
        order = new OrderEntity();
    }
    
    
    @DisplayName("1.1 Value Source : String Array")
    @ParameterizedTest
    @ValueSource(strings = {"0123456777", "0123456888", "0123456999"})
    @Order(1)
    @Tag("functional")
    public void shouldTestPhoneNumberFormatUsingValueSource(String phoneNumber) {
    	order = new OrderEntity.Builder()
    			.addCustomer(
    					new Customer
    					("UUID", "John", "Doe", phoneNumber))
    			.build(); 
    	assertTrue(order.isCustomerAvailable());
    	assertEquals(1, order.getCustomer().getPhoneList().size());
    }
    
    @DisplayName("1.2 Value Source - Number Array")
    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, -3, 15, Integer.MAX_VALUE}) // six numbers
    @Order(2)
    @Tag("non-functional")
    public void isOddShouldReturnTrueForOddNumbers(int number) {
        assertTrue(Utils.Numbers.isOdd(number));
    }
    
    @DisplayName("1.3 Value Source - Null Source")
    @ParameterizedTest
    @NullSource
    @Order(3)
    @Tag("non-functional")
    public void isBlankShouldReturnTrueForNullInputs(String input) {
        assertTrue(Utils.Strings.isBlank(input));
    }
    
    @DisplayName("1.4 Value Source - Empty Source 1")
    @ParameterizedTest
    @EmptySource
    @Order(4)
    @Tag("non-functional")
    public void isBlankShouldReturnTrueForEmptyStrings(String input) {
        assertTrue(Utils.Strings.isBlank(input));
    }
    
    @DisplayName("1.5 Value Source - Null & Empty Source 2")
    @ParameterizedTest
    @NullAndEmptySource
    @Order(5)
    @Tag("non-functional")
    public void isBlankShouldReturnTrueForNullAndEmptyStrings(String input) {
        assertTrue(Utils.Strings.isBlank(input));
    }
    
    @DisplayName("1.6 Value Source - Null & Empty Source 3")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    @Order(6)
    @Tag("non-functional")
    public void isBlank_ShouldReturnTrueForAllTypesOfBlankStrings(String input) {
        assertTrue(Utils.Strings.isBlank(input));
    }
    
    @DisplayName("2.1 Enum Source - 12 Months")
    @ParameterizedTest
    @EnumSource(Month.class) 
    @Order(7)
    @Tag("non-functional")
    void getValueForAMonthIsAlwaysBetweenOneAndTwelve(Month month) {
        int monthNumber = month.getValue();
        assertTrue(monthNumber > 0 && monthNumber < 13);
    }
    
    @DisplayName("2.2 Enum Source - 4 Months")
    @ParameterizedTest(name = "{index} {0} is 30 days long")
    @EnumSource(value = Month.class, names = 
				{"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"})
    @Order(8)
    @Tag("non-functional")
    public void someMonths_Are30DaysLong(Month month) {
        final boolean isALeapYear = false;
        assertEquals(30, month.length(isALeapYear));
    }
    
    @DisplayName("3.1 CSV Source Case - Phone Number should match the Format")
    @ParameterizedTest
    @CsvSource({"0123456777", "0123456888", "0123456999"})
    @Order(9)
    @Tag("functional")
    public void shouldTestPhoneNumberFormatUsingCSVSource(String phoneNumber) {
    	order = new OrderEntity.Builder()
    			.addCustomer(
    					new Customer
    					("UUID", "John", "Doe", phoneNumber))
    			.build(); 
    	assertTrue(order.isCustomerAvailable());
    	assertEquals(1, order.getCustomer().getPhoneList().size());
    }
    
    @DisplayName("3.2 CSV File Source Case - Phone No. should match the Format")
    @ParameterizedTest
    @CsvFileSource(resources = "/phoneList.csv")
    @Order(10)
    @Tag("functional")
    public void shouldTestPhoneNumberFormatUsingCSVFileSource(String phoneNumber) {
    	order = new OrderEntity.Builder()
    			.addCustomer(
    					new Customer
    					("UUID", "John", "Doe", phoneNumber))
    			.build(); 
    	assertTrue(order.isCustomerAvailable());
    	assertEquals(1, order.getCustomer().getPhoneList().size());
    }
    
    @DisplayName("4.1 Method Source Case - Phone No. should match the Format")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    @Order(11)
    @Tag("functional")
    public void shouldTestPhoneNumberFormatUsingMethodSource(String phoneNumber) {
    	order = new OrderEntity.Builder()
    			.addCustomer(
    					new Customer
    					("UUID", "John", "Doe", phoneNumber))
    			.build(); 
    	assertTrue(order.isCustomerAvailable());
    	assertEquals(1, order.getCustomer().getPhoneList().size());
    }

    private static List<String> phoneNumberList() {
        return Arrays.asList("0123456789", "0123456798", "0123456897");
    }
    
    @DisplayName("5.1 Argument Source - Customer Object")
    @ParameterizedTest
    @ArgumentsSource(CustomerArgumentProvider.class)
    @Order(12)
    @Tag("non-functional")
    void testWithArgumentsSource(
    		String uuid, String fn, String ln, String phone) {
        log.debug(">>> Parameterized test with (String) {} and (int) {} ", 
        		uuid, fn, ln, phone);

       assertNotNull(uuid);
       assertNotNull(fn);        
       assertNotNull(ln);
       assertNotNull(phone);
       // assertTrue(age > 0);
    }

    static Stream<Arguments> arguments = Stream.of(
  		  Arguments.of("", true), // null strings should be considered blank
  		  Arguments.of("", true),
  		  Arguments.of("  ", true),
  		  Arguments.of("not blank", false)
  	);
    
    @DisplayName("5.2 Argument Source >> Custom Variable Source")
	// @ParameterizedTest
	@VariableSource("arguments")
    @Order(13)
    @Tag("non-functional")
	public void isBlankShouldReturnTrueForNullOrBlankStringsVariableSource(
	  String input, boolean expected) {
	    assertEquals(expected, Utils.Strings.isBlank(input));
	}

    @AfterEach
    public void tearDown() {
        System.out.println(counter+". Should Execute After Each Test");
        counter++;
    }
    
	/**
	 * if the @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * is available then the method need not be static
	 */
    @AfterAll
    public void tearDownAll() {
        System.out.println("== Order Parametrized Suite Execution Completed...");
    }
}
