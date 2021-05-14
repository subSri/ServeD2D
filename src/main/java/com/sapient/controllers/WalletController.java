package com.sapient.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import com.sapient.dao.*;
import com.sapient.entity.Order;
import com.sapient.entity.Service;
import com.sapient.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/wallet")
public class WalletController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ServiceDao serviceDao;

	@GetMapping("/balance")
	public ResponseEntity<?> getBalanceOfUser(
			@RequestHeader(name = "Authorization", required = false) String authHeader) {
		log.info("authHeader = {}", authHeader);
		if (authHeader == null) {
			// Authorization header is missing
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
		}

		try {
			String token = authHeader.split(" ")[1]; // second element from the header's value
			log.info("token = {}", token);
			Integer userId = JwtUtil.verify(token);

			Double myBalance = userDao.getBalance(userId);
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("user_id", userId);
			map.put("balance", myBalance);
			return ResponseEntity.ok(map);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Authorization token is invalid or " + ex.getMessage());
		}

	}

	@PostMapping("/balance/add")
	public ResponseEntity<?> addToWallet(@RequestHeader(name = "Authorization", required = false) String authHeader,
	@RequestHeader(name = "Amount", required = true) Double amount) {
		log.info("authHeader = {}", authHeader);
		if (authHeader == null) {
			// Authorization header is missing
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
		}

		try {
			String token = authHeader.split(" ")[1];
			log.info("token = {}", token);
			Integer userId = JwtUtil.verify(token);

			userDao.addToWallet(userId, amount);
			Double myBalance = userDao.getBalance(userId);
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("user_id", userId);
			map.put("balance", myBalance);
			return ResponseEntity.ok(map);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Authorization token is invalid or " + ex.getMessage());
		}
	}

	@PostMapping("/balance/withdraw")
	public ResponseEntity<?> withdrawFromWallet(
			@RequestHeader(name = "Authorization", required = false) String authHeader,@RequestHeader(name = "Amount", required = true) Double amount) {
		log.info("authHeader = {}", authHeader);
		if (authHeader == null) {
			// Authorization header is missing
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
		}

		try {
			String token = authHeader.split(" ")[1];
			log.info("token = {}", token);
			Integer userId = JwtUtil.verify(token);

			userDao.withdrawFromWallet(userId, amount);
			Double myBalance = userDao.getBalance(userId);
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("user_id", userId);
			map.put("balance", myBalance);
			return ResponseEntity.ok(map);
			
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Authorization token is invalid or " + ex.getMessage());
		}
	}

    @PostMapping("/balance/payment")
	public ResponseEntity<?> makePayment(
			@RequestHeader(name = "Authorization", required = false) String authHeader, @RequestBody Order order) {
		log.info("authHeader = {}", authHeader);
		if (authHeader == null) {
			// Authorization header is missing
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
		}

		try {
			String token = authHeader.split(" ")[1];
			log.info("token = {}", token);
			Integer userId = JwtUtil.verify(token);

            if(userDao.getBalance(userId) >= order.getAmount())
            {
            	Integer serviceId = order.getServiceId();

            	Service service = new Service();

            	service = serviceDao.returnASpecificService(serviceId);
				userDao.withdrawFromWallet(order.getUserId(), order.getAmount());
            	userDao.addToWallet(service.getProviderId(), order.getAmount());
            	Map<String, Object> map = new HashMap<>();
				map.put("success", true);
				map.put("user_id", userId);
				map.put("wallet_balance", userDao.getBalance(userId));
				map.put("provider_id", service.getProviderId());
				map.put("service", service);
				return ResponseEntity.ok(map);
            }
            else
            {
            	return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body("Insufficient balance in your wallet");
            }
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Authorization token is invalid or " + ex.getMessage());
		}
	}

}
