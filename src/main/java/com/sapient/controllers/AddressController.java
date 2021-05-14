package com.sapient.controllers;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.sapient.dao.*;
import com.sapient.entity.Address;
import com.sapient.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
public class AddressController {
	AddressDao addressDao = new AddressDao();
	
	@GetMapping("/address")
	public ResponseEntity<?> getAddressofUser(@RequestHeader(name = "Authorization", required = false) String authHeader) {
		log.info("authHeader = {}", authHeader);
		if (authHeader == null) {
			// Authorization header is missing
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
		}

		try {
			String token = authHeader.split(" ")[1];
			log.info("token = {}", token);
			Integer userId = JwtUtil.verify(token);
			
			Address address = new Address();
			address = addressDao.getAddress(userId);
			return ResponseEntity.ok(address);
     
			
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Authorization token is invalid or " + ex.getMessage());
		}
	}
	
	@PostMapping("/address/edit")
	public ResponseEntity<?> editAddress(@RequestHeader(name = "Authorization", required = false) String authHeader, Address address) {
		log.info("authHeader = {}", authHeader);
		if (authHeader == null) {
			// Authorization header is missing
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
		}

		try {
			String token = authHeader.split(" ")[1];
			log.info("token = {}", token);
			Integer userId = JwtUtil.verify(token);
			addressDao.updateAddress(address);
			Address newaddress = new Address();
			newaddress   = addressDao.getAddress(userId);
			return ResponseEntity.ok(newaddress);
			
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Authorization token is invalid or " + ex.getMessage());
		}
	}

	@PostMapping("/address/add")
	public ResponseEntity<?> addAddress(@RequestHeader(name = "Authorization", required = false) String authHeader, Address address) {
		log.info("authHeader = {}", authHeader);
		if (authHeader == null) {
			// Authorization header is missing
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
		}

		try {
			String token = authHeader.split(" ")[1];
			log.info("token = {}", token);
			Integer userId = JwtUtil.verify(token);
			addressDao.addNewAddress(address);
			Address newaddress = new Address();
			newaddress   = addressDao.getAddress(userId);
			return ResponseEntity.ok(newaddress);
			
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Authorization token is invalid or " + ex.getMessage());
		}
	}
}
