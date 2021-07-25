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

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import io.fusion.water.order.domainLayer.models.Customer;
import io.fusion.water.order.domainLayer.models.OrderEntity;
import io.fusion.water.order.domainLayer.models.OrderItem;
import io.fusion.water.order.domainLayer.models.PaymentDetails;
import io.fusion.water.order.domainLayer.models.PaymentStatus;
import io.fusion.water.order.domainLayer.models.PaymentType;
import io.fusion.water.order.domainLayer.models.ShippingAddress;

/**
 * 
 * @author arafkarsh
 *
 */
public class ModelToJson {
	
	/**
	 * Order Entity Sample Data
	 * 
	 * @param args
	 */
	public static String OrderEntityToJson() {
		OrderEntity oEntity = new OrderEntity.Builder()
					.addCustomer(new Customer
        					("UUID", "John", "Doe", "0123456789"))
					.addOrderItem(new OrderItem
							("uuid1", "iPhone 12", 799, "USD", 1))
					.addOrderItem(new OrderItem
							("uuid2", "iPhone 12 Pro", 999, "USD", 1))
					.addOrderItem(new OrderItem
							("uuid3", "Apple Watch Series 6", 450, "USD", 2))
					.addShippingAddress(new ShippingAddress
							("321 Cobblestone Ln,", "", "Edison", "NJ", "", "USA", "08820"))
					.addPaymentType(PaymentType.CREDIT_CARD)
					.build();
		
		return Utils.toJson(oEntity);
	}
	
	/**
	 * Payment Details Sample Data
	 * 
	 * @return
	 */
	public static String PaymentDetailsToJson() {
		PaymentDetails p = new PaymentDetails(
				"fb908151-d249-4d30-a6a1-4705729394f4", 
				LocalDate.now(), 
				230, 
				PaymentType.CREDIT_CARD
				);
		
		return Utils.toJson(p);
	}
	
	/**
	 * 
	 * @return
	 */
	public static PaymentDetails PaymentDetailsToObject() {
		return new PaymentDetails(
				"fb908151-d249-4d30-a6a1-4705729394f4", 
				LocalDate.now(), 
				230, 
				PaymentType.CREDIT_CARD
				);
	}
	
	/**
	 * Payment Status Sample Data
	 * 
	 * @return
	 */
	public static String PaymentStatusToJson() {
		PaymentStatus ps = new PaymentStatus(
				"fb908151-d249-4d30-a6a1-4705729394f4", 
				LocalDate.now(), 
				"Accepted", 
				UUID.randomUUID().toString(), 
				LocalDate.now(), 
				PaymentType.CREDIT_CARD);
		
		return Utils.toJson(ps);
	}
	
	/**
	 * 
	 * @param _txId
	 * @param _txDate
	 * @return
	 */
	public static PaymentStatus PaymentStatusToObject(
			String _txId, LocalDate _txDate) {
		return new PaymentStatus(
				_txId, 
				_txDate, 
				"Accepted", 
				UUID.randomUUID().toString(), 
				LocalDate.now(), 
				PaymentType.CREDIT_CARD);
	}
	
	/**
	 * For Testing Purpose Only
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("-Order Entity-");
		System.out.println(OrderEntityToJson());
		System.out.println("-Payment Details Entity-");
		System.out.println(PaymentDetailsToJson());
		System.out.println("-Payment Status Entity-");
		System.out.println(PaymentStatusToJson());
	}
}
