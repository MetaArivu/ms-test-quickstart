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

package io.fusion.water.order.adapters.domainService;

import org.springframework.stereotype.Service;

import io.fusion.water.order.domainLayer.models.PaymentDetails;
import io.fusion.water.order.domainLayer.models.PaymentStatus;
import io.fusion.water.order.domainLayer.services.OrderPaymentService;

/**
 * Order Payment Service
 * 
 * @author arafkarsh
 *
 */
@Service
public class PaymentServiceImpl implements OrderPaymentService {


	@Override
	public PaymentStatus processPayments(PaymentDetails _paymentDetails) {
		// TODO Auto-generated method stub
		return null;
	}

}
