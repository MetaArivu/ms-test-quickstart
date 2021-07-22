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

package test.fusion.water.order.bdd.junit;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import test.fusion.water.order.adapters.extensions.TestTimeExtension;

/**
 * 
 * @author arafkarsh
 *
 */

@Tag("Critical")
@Tag("BDD")
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(Cucumber.class)
// @CucumberOptions(plugin = {"pretty", "html:target/cucumber"})
@CucumberOptions(
		plugin = {"pretty"},
		features = {"src/test/java/test/fusion/water/order/bdd/specs/"},
		glue = {"test.fusion.water.order.bdd.steps"}
)
@ExtendWith(TestTimeExtension.class)
public class PaymentTest {

	
}
