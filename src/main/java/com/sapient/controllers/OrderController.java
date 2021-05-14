package com.sapient.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sapient.dao.*;
import com.sapient.entity.Order;
import com.sapient.enums.Enums.OrderStatus;
import com.sapient.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {


	// @Autowired
	// private OrderDao orderDao;

	OrderDaoDataBaseAccess orderDao = new OrderDaoDataBaseAccess();
	
	@GetMapping
	public ResponseEntity<?> getOrdersForSpecificUser(
			@RequestHeader(name = "Authorization", required = false) String authHeader) {

		
		// log.info("authHeader = {}", authHeader);
		if(authHeader==null) {
			// Authorization header is missing
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
		}
		
		try {
			String token = authHeader.split(" ")[1]; // second element from the header's value
			// log.info("token = {}", token);
			Integer userId = JwtUtil.verify(token);
			
			List<Order> orders = orderDao.returnAllOrders(userId);
			
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("user_id", userId);
			map.put("orders", orders); 
			return ResponseEntity.ok(map);
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is invalid or " + ex.getMessage());
		}
	}

	@GetMapping(value="", params = "status")
	public ResponseEntity<?> getOrdersOnStatus(@RequestParam(name="status", required = true) String status,
			@RequestHeader(name = "Authorization", required = false) String authHeader) {
		// log.info("authHeader = {}", authHeader);
		if(authHeader==null) {
			// Authorization header is missing
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
		}
		
		try {
			String token = authHeader.split(" ")[1]; // second element from the header's value
			// log.info("coming_status = {}", String.valueOf(OrderStatus.CONFIRMED.ordinal()));
			List<Order> orders = new ArrayList<Order>();
			Integer userId = JwtUtil.verify(token);

			if (status.equals(String.valueOf(OrderStatus.CONFIRMED.ordinal()))){
				orders = orderDao.returnAllIncompleteOrders(userId);
			}
			else if (status.equals(String.valueOf(OrderStatus.COMPLETED.ordinal()))){
				orders = orderDao.returnAllPastCompletedOrders(userId);
			}
			else if (status.equals(String.valueOf(OrderStatus.CANCELLED.ordinal()))){
				orders = orderDao.returnAllCancelledOrders(userId);
			}
			else if (status.equals(String.valueOf(OrderStatus.REQUESTED.ordinal()))){
				orders = orderDao.returnAllRequestedOrders(userId);
			}
			else if (status.equals(String.valueOf(OrderStatus.REJECTED.ordinal()))){
				orders = orderDao.returnAllRejectedOrders(userId);
			}
			
			
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("user_id", userId);
			map.put("orders", orders); 
			return ResponseEntity.ok(map);
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is invalid or " + ex.getMessage());
		}
	}

	@GetMapping("/{order_id}")
	public ResponseEntity<?> getDetailsOfASpecificOrder(@PathVariable("order_id") Integer orderId,
		@RequestHeader(name = "Authorization", required = false) String authHeader) {

	if(authHeader==null) {
		// Authorization header is missing
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
	}
	
	try {
		String token = authHeader.split(" ")[1]; // second element from the header's value
		// log.info("token = {}", token);
		Integer userId = JwtUtil.verify(token);
		
		Order order = orderDao.returnSpecificOrder(orderId, userId);
		
		Map<String, Object> map = new HashMap<>();
		
		if (order == null){
			// map.put("success", true);
			// map.put("user_id", userId);
			// map.put("order", new Object[] {}); 
			// return ResponseEntity.ok(map);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No requested Order found for the current User");
		}
		else{
			map.put("success", true);
			map.put("user_id", userId);
			map.put("order", order); 
			return ResponseEntity.ok(map);
		}
	}
	catch(Exception ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is invalid or " + ex.getMessage());
	}
}

	@PostMapping(value="", params = "order_id")
	public ResponseEntity<?> changeOrderStatus(@RequestParam(name="order_id", required = true) Integer orderId, @RequestHeader(name = "Authorization", required = false) String authHeader,
			@RequestBody Order order) {
		// log.info("authHeader = {}", authHeader);
		if (authHeader == null) {
			// Authorization header is missing
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
		}

		try {
			String token = authHeader.split(" ")[1];
			// log.info("token = {}", token);
			Integer userId = JwtUtil.verify(token);
			Order mod_order = new Order();

			Integer status = order.getOrderStatus();

			if (status == OrderStatus.COMPLETED.ordinal()){
				orderDao.completeOrder(orderId);
			}
			else if (status == OrderStatus.CANCELLED.ordinal()){
				orderDao.cancelOrder(orderId);
			}
			else if (status == OrderStatus.REJECTED.ordinal()){
				orderDao.rejectOrder(orderId);
			}
			else if (status == OrderStatus.CONFIRMED.ordinal()){
				orderDao.acceptOrder(orderId);
			}
			mod_order = orderDao.returnSpecificOrder(orderId, userId);
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("user_id", userId);
			map.put("order_id", orderId);
			map.put("order_details", mod_order); 
			return ResponseEntity.ok(map);

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Authorization token is invalid or " + ex.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> makeANewOrder(
			@RequestHeader(name = "Authorization", required = false) String authHeader, @RequestBody Order order) {

		
		// log.info("authHeader = {}", authHeader);
		if(authHeader==null) {
			// Authorization header is missing
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
		}
		
		try {
			String token = authHeader.split(" ")[1]; // second element from the header's value
			// log.info("token = {}", token);
			Integer userId = JwtUtil.verify(token);

			orderDao.addNewOrder(order);
			
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("user_id", userId);
			map.put("order", order); 
			return ResponseEntity.ok(map);
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is invalid or " + ex.getMessage());
		}
	}

}