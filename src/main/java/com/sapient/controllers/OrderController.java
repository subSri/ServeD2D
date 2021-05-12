package com.sapient.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sapient.dao.OrderDao;
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

// import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/orders")
// @Slf4j
public class OrderController {


	@Autowired
	private OrderDao orderDao;
	
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

	@GetMapping
	public ResponseEntity<?> getOrdersOnStatus(@RequestParam(name="status", required = true, defaultValue = "0") String status,
			@RequestHeader(name = "Authorization", required = false) String authHeader) {
		// log.info("authHeader = {}", authHeader);
		if(authHeader==null) {
			// Authorization header is missing
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
		}
		
		try {
			String token = authHeader.split(" ")[1]; // second element from the header's value
			// log.info("token = {}", token);
			List<Order> orders = new ArrayList<Order>();
			Integer userId = JwtUtil.verify(token);
			if (status == String.valueOf(OrderStatus.CONFIRMED.ordinal())){
				orders = orderDao.returnAllIncompleteOrders(userId);
			}
			else if (status == String.valueOf(OrderStatus.COMPLETED.ordinal())){
				orders = orderDao.returnAllPastCompletedOrders(userId);
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
	public ResponseEntity<?> getDetailsOfASpecificOrder(@PathVariable("order_id") Integer order_id,
		@RequestHeader(name = "Authorization", required = false) String authHeader) {

	if(authHeader==null) {
		// Authorization header is missing
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
	}
	
	try {
		String token = authHeader.split(" ")[1]; // second element from the header's value
		// log.info("token = {}", token);
		Integer userId = JwtUtil.verify(token);
		
		Order order = orderDao.returnSpecificOrder(userId);
		
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

	@PostMapping
	public ResponseEntity<?> changeOrderStatus(@RequestParam(name="order_id", required = true) Integer orderId, @RequestHeader(name = "Authorization", required = false) String authHeader,
			@RequestBody OrderStatus status) {
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

			if (status == OrderStatus.COMPLETED){
				orderDao.completeOrder(orderId);
			}
			else if (status == OrderStatus.CANCELLED){
				orderDao.cancelOrder(orderId);
			}
			else if (status == OrderStatus.REJECTED){
				orderDao.rejectOrder(orderId);
			}
			else if (status == OrderStatus.CONFIRMED){
				orderDao.acceptOrder(orderId);
			}
			mod_order = orderDao.returnSpecificOrder(orderId)
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