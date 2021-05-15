package com.sapient.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sapient.dao.*;
import com.sapient.entity.Address;
import com.sapient.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/address")
public class AddressController {

	@Autowired
	private AddressDao addressDao;
	
	@GetMapping
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
			
			List<Address> addresses =addressDao.getAddress(userId);
			
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("user_id", userId);
			map.put("address", addresses);
			return ResponseEntity.ok(map);
     
			
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Authorization token is invalid or " + ex.getMessage());
		}
	}
	
	@PostMapping("/edit")
	public ResponseEntity<?> editAddress(@RequestHeader(name = "Authorization", required = false) String authHeader, @RequestBody Address address) {
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
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("user_id", userId);
			map.put("address", address);
			return ResponseEntity.ok(map);
			
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Authorization token is invalid or " + ex.getMessage());
		}
	}

	@PostMapping("/add")
	public ResponseEntity<?> addAddress(@RequestHeader(name = "Authorization", required = false) String authHeader,@RequestBody Address address) {
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
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("user_id", userId);
			map.put("address", address);
			return ResponseEntity.ok(map);
			
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Authorization token is invalid or " + ex.getMessage());
		}
	}
}
