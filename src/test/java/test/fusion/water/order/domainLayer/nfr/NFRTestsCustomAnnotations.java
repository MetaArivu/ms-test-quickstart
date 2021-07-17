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

package test.fusion.water.order.domainLayer.nfr;


import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.slf4j.Logger;

import test.fusion.water.order.core.PerformanceLoad;
import test.fusion.water.order.core.PerformanceStress;
import test.fusion.water.order.core.Security;
import test.fusion.water.order.core.Usability;
/**
 * 
 * @author arafkarsh
 *
 */
@Tag("non-functional")
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NFRTestsCustomAnnotations {

	static final Logger log = getLogger(lookup().lookupClass());

    @Test
    // @Tag("performance")
    // @Tag("load")
    @PerformanceLoad()
    @DisplayName("1. NFR Test (Performance/Load)")
    @Order(1)
    void testOne() {
        log.debug("NFR Test 1 (Performance/Load)");
    }

    @Test
    // @Tag("performance")
    // @Tag("stress")
    @PerformanceStress()
    @DisplayName("2. NFR Test (Performance/Stress)")
    @Order(2)
    void testTwo() {
        log.debug("NFR Test 2 (Performance/Stress)");
    }

    @Test
    // @Tag("security")
    @Security()
    @DisplayName("3. NFR Test (Security)")
    @Order(3)
    void testThree() {
        log.debug("NFR Test 3 (Security)");
    }

    @Test
    // @Tag("usability")
    @Usability()
    @DisplayName("4. NFR Test (Usability)")
    @Order(4)
    void testFour() {
        log.debug("NFR Test 4 (Usability)");
    }
}
