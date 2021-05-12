package com.sapient.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.sapient.dao.*;
import com.sapient.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/wallet")
public class WalletController {

	@Autowired
	private UserDao userDao;

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
			return ResponseEntity.ok(myBalance);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Authorization token is invalid or " + ex.getMessage());
		}

	}

	@PostMapping("/balance/add")
	public ResponseEntity<?> addToWallet(@RequestHeader(name = "Authorization", required = false) String authHeader,
			@RequestBody Double amount) {
		log.info("authHeader = {}", authHeader);
		if (authHeader == null) {
			// Authorization header is missing
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
		}

		try {
			String token = authHeader.split(" ")[1];
			log.info("token = {}", token);
			Integer userId = JwtUtil.verify(token);

			Double myBalance = userDao.getBalance(userId);
			userDao.addToWallet(userId, amount);
			myBalance = userDao.getBalance(userId);
			return ResponseEntity.ok(myBalance);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Authorization token is invalid or " + ex.getMessage());
		}
	}

	@PostMapping("/balance/withdraw")
	public ResponseEntity<?> withdrawFromWallet(
			@RequestHeader(name = "Authorization", required = false) String authHeader, @RequestBody Double amount) {
		log.info("authHeader = {}", authHeader);
		if (authHeader == null) {
			// Authorization header is missing
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
		}

		try {
			String token = authHeader.split(" ")[1];
			log.info("token = {}", token);
			Integer userId = JwtUtil.verify(token);

			Double myBalance = userDao.getBalance(userId);
			userDao.withdrawFromWallet(userId, amount);
			myBalance = userDao.getBalance(userId);
			return ResponseEntity.ok(myBalance); // should i check if amount is sufficient bcz it is being checked in
													// withdraw method?
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Authorization token is invalid or " + ex.getMessage());
		}
	}

}
