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

package io.fusion.water.order.adapters.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.fusion.water.order.adapters.external.PaymentGateWay;
import io.fusion.water.order.domainLayer.models.PaymentDetails;
import io.fusion.water.order.domainLayer.models.PaymentStatus;
import io.fusion.water.order.domainLayer.models.PaymentType;
import io.fusion.water.order.domainLayer.services.PaymentService;

/**
 * Order Payment Service
 * 
 * @author arafkarsh
 *
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentGateWay paymentGateWay;
	
	/**
	 * 
	 * @param _gw
	 */
	public PaymentServiceImpl(PaymentGateWay _gw) {
		paymentGateWay = _gw;
	}
	
	/**
	 * Default 
	 * @param _paymentDetails
	 * @return
	 */
	public PaymentStatus processPaymentsDefault(PaymentDetails _paymentDetails) {
		return new PaymentStatus(
				_paymentDetails.getTransactionId(), 
				_paymentDetails.getTransactionDate(), 
				"Accepted", "Ref-uuid", 
				LocalDateTime.now(), 
				PaymentType.CREDIT_CARD);
	}
	
	/**
	 * 
	 * @param _paymentDetails
	 * @return
	 */
	public PaymentStatus processPayments(PaymentDetails _paymentDetails) {
		return paymentGateWay.processPayments(_paymentDetails);
	}

}
