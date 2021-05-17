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

@CrossOrigin
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
			return ifAuthNull();
		}

		try {
			Integer userId = auth(authHeader);
			List<Address> addresses =addressDao.getAddress(userId);
			return getResponse(userId, addresses);
			
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Authorization token is invalid or " + ex.getMessage());
		}
	}

	private Integer auth(String authHeader) throws Exception {
		String token = authHeader.split(" ")[1];
		log.info("token = {}", token);
		Integer userId = JwtUtil.verify(token);
		return userId;
	}

	private ResponseEntity<?> getResponse(Integer userId, List<Address> addresses) {
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("user_id", userId);
		map.put("address", addresses);
		return ResponseEntity.ok(map);
	}
	
	@PostMapping("/edit")
	public ResponseEntity<?> editAddress(@RequestHeader(name = "Authorization", required = false) String authHeader, @RequestBody Address address) {
		log.info("authHeader = {}", authHeader);
		if (authHeader == null) {
			return ifAuthNull();
		}

		try {
			Integer userId = auth(authHeader);
			addressDao.updateAddress(address,userId);
			return getResponse(address, userId);
			
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Authorization token is invalid or " + ex.getMessage());
		}
	}

	private ResponseEntity<?> getResponse(Address address, Integer userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("user_id", userId);
		map.put("address", address);
		return ResponseEntity.ok(map);
	}

	@PostMapping("/add")
	public ResponseEntity<?> addAddress(@RequestHeader(name = "Authorization", required = false) String authHeader,@RequestBody Address address) {
		log.info("authHeader = {}", authHeader);
		if (authHeader == null) {
			return ifAuthNull();
		}

		try {
			Integer userId = auth(authHeader);
			addressDao.addNewAddress(address, userId);
			return getResponse(address, userId);
			
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Authorization token is invalid or " + ex.getMessage());
		}
	}

	private ResponseEntity<?> ifAuthNull() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
	}
}
