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

package io.fusion.water.order.domainLayer.models;

import java.util.ArrayList;

import io.fusion.water.order.utils.Utils;

/**
 * Order Entity
 * 
 * @author arafkarsh
 *
 */
public class OrderEntity {

	private Customer customer;
	private ArrayList<OrderItem> orderItems;
	private ShippingAddress shippingAddress;
	private PaymentType paymentType;
	private OrderStatus orderStatus;
	
	/**
	 * Create Order
	 */
	public OrderEntity() {
		orderItems = new ArrayList<OrderItem>();
	}
	
	/**
	 * Adds the Customer
	 * @param _customer
	 */
	protected void addCustomer(Customer _customer) {
		customer = _customer;
		customer.validate();
	}
	
	/**
	 * Add Order Item
	 * @param _item
	 */
	protected void addOrderItem(OrderItem _item) {
		if(_item != null) {
			orderItems.add(_item);
		}
	}
	
	/**
	 * Add Shipping Address
	 * @param _address
	 */
	protected void addShippingAddress(ShippingAddress _address) {
		shippingAddress = _address;
	}
	
	/**
	 * Add Payment Type
	 * @param _pType
	 */
	protected void addPaymentType(PaymentType _pType) {
		paymentType = _pType;
	}

	/**
	 * Returns true 
	 * @return
	 */
	public boolean isCustomerAvailable()  {
		return (customer != null) ? true : false;
	}
	
	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @return the orderItems
	 */
	public ArrayList<OrderItem> getOrderItems() {
		return orderItems;
	}
	
	/**
	 * Returns the Total Items in the Order
	 * @return
	 */
	public int getTotalItems() {
		return orderItems.size();
	}
	
	/**
	 * Calculate the Total Order Value
	 * @return
	 */
	public double getTotalValue() {
		double totalValue = 0.00;
		for(OrderItem item : orderItems) {
			totalValue += item.getItemValue();
		}
		return totalValue;
	}

	/**
	 * Returns TRUE if the Shipping Address is available
	 * @return
	 */
	public boolean isShippingAddressAvailable()  {
		return (shippingAddress != null) ? true : false;
	}
	
	/**
	 * @return the shippingAddress
	 */
	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * @return the paymentType
	 */
	public PaymentType getPaymentType() {
		return paymentType;
	}
	
	/**
	 * Build Order
	 */
	public static class Builder {
		
		OrderEntity order = new OrderEntity();
		
		/**
		 * Add Custmer
		 * @param _customer
		 * @return
		 */
		public Builder addCustomer(Customer _customer) {
			order.orderStatus = OrderStatus.INITIATED;
			order.addCustomer(_customer);
			return this;
		}
		
		/**
		 * Add Order Item
		 * @param _item
		 * @return
		 */
		public Builder addOrderItem(OrderItem _item) {
			order.addOrderItem(_item);
			return this;
		}
		
		/**
		 * Add a List of Order Items
		 * @param _items
		 * @return
		 */
		public Builder addOrderItems(ArrayList<OrderItem> _items) {
			if(_items != null && _items.size() > 0) {
				order.getOrderItems().addAll(_items);
			}
			return this;
		}
		
		/**
		 * Add Shipping Address
		 * @param _address
		 * @return
		 */
		public Builder addShippingAddress(ShippingAddress _address) {
			order.addShippingAddress(_address);
			return this;
		}
		
		/**
		 * Add Payment Type
		 * @param _pType
		 * @return
		 */
		public Builder addPaymentType(PaymentType _pType) {
			order.addPaymentType(_pType);
			return this;
		}
		
		/**
		 * Build the Order
		 * @return
		 */
		public OrderEntity build() {
			return order;
		}
	}
	
	/**
	 * For Testing Purpose Only
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
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
		
		System.out.println(Utils.toJson(oEntity));
	}
}
