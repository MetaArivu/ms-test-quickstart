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

package io.fusion.water.order.adapters.webService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import io.fusion.water.order.domainLayer.models.OrderEntity;
import io.fusion.water.order.domainLayer.services.OrderBusinessService;
import io.fusion.water.order.domainLayer.services.OrderWebService;

/**
 * Order Web Service
 * 
 * @author arafkarsh
 *
 */
@RestController
@RequestMapping(value = "/api/order")
@RequestScope
public class OrderWebServiceImpl implements OrderWebService {

	@Autowired
	OrderBusinessService orderBusinessService;
	
	/**
	 * Get Order - Follows REST Guidelines for URI
	 * By Order ID
	 */
	@Override
	@GetMapping("/{orderId}/")
	public ResponseEntity<OrderEntity> getOrderById(@PathVariable("orderId") String _id) {
		OrderEntity orderEntity = null;
		try  {
			orderEntity = orderBusinessService.getOrderById(_id);
		} catch (Exception e) {
			return new ResponseEntity<OrderEntity>(orderEntity, HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(orderEntity);
	}

	/**
	 * Create Order - REST Guidelines says No Verbs to be used in the URI
	 * "/api/order/create" is part of the POST URI for ease of use.
	 */
	@Override
	@PostMapping("/create")
	public ResponseEntity<OrderEntity> saveOrder(@RequestBody OrderEntity _order) {
		OrderEntity orderEntity = null;
		try  {
			orderEntity = orderBusinessService.saveOrder(_order);
		} catch (Exception e) {
			return new ResponseEntity<OrderEntity>(orderEntity, HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(orderEntity);
	}

	/**
	 * Cancel Order - REST Guidelines says No Verbs to be used in the URI
	 * By Order
	 * "/api/order/cancel" is part of the PUT URI for ease of use.
	 */
	@Override
	@PutMapping("/cancel")
	public ResponseEntity<OrderEntity> cancelOrder(@RequestBody  OrderEntity _order) {
		OrderEntity orderEntity = null;
		try  {
			orderEntity = orderBusinessService.cancelOrder(_order);
		} catch (Exception e) {
			return new ResponseEntity<OrderEntity>(orderEntity, HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(orderEntity);
	}

	/**
	 * Cancel Order - REST Guidelines says No Verbs to be used in the URI
	 * By Order ID
	 * "/api/order/cancel/{orderId}/" is part of the PUT URI for ease of use.
	 */
	@Override
	@PutMapping("/cancel/{orderId}/")
	public ResponseEntity<OrderEntity> cancelOrder(@PathVariable("orderId") String _id) {
		OrderEntity orderEntity = null;
		try  {
			orderEntity = orderBusinessService.cancelOrder(_id);
		} catch (Exception e) {
			return new ResponseEntity<OrderEntity>(orderEntity, HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(orderEntity);
	}
}
