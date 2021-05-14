package com.sapient.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sapient.dao.*;
import com.sapient.entity.Service;
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

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    // @Autowired
	// private ServiceDao serviceDao;

	ServiceDaoDataBaseAccess serviceDao = new ServiceDaoDataBaseAccess();
    UserDaoDataBaseAccess userDao = new UserDaoDataBaseAccess();
    
    @GetMapping
	public ResponseEntity<?> getAllServices() {

		try {
			
			List<Service> services = serviceDao.returnAllService();
			
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("services", services); 
			return ResponseEntity.ok(map);
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}

    @GetMapping("/categories")
	public ResponseEntity<?> getAllCategories() {

		try {
			
			List<String> categories = serviceDao.returnAllCategories();
			
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("categories", categories); 
			return ResponseEntity.ok(map);
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}

    @GetMapping(value="", params = "category")
	public ResponseEntity<?> getAllServicesOfACategory(@RequestParam(name="category", required = true) String category) {

		try {
			
			List<Service> services = serviceDao.returnAllServiceOfACategory(category);
			
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("services", services); 
			return ResponseEntity.ok(map);
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}

    @GetMapping("/{service_id}")
    public ResponseEntity<?> getServiceFromId(@PathVariable("service_id") Integer service_id) {

		try {
			Service service = serviceDao.returnASpecificService(service_id);
			
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("service", service); 
			return ResponseEntity.ok(map);
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}

    @PostMapping
	public ResponseEntity<?> addAService(
			@RequestHeader(name = "Authorization", required = false) String authHeader, @RequestBody Service service) {

		
		// log.info("authHeader = {}", authHeader);
		if(authHeader==null) {
			// Authorization header is missing
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
		}
		
		try {
			String token = authHeader.split(" ")[1]; // second element from the header's value
			// log.info("token = {}", token);
			Integer userId = JwtUtil.verify(token);

			serviceDao.addNewService(service);
			
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("user_id", userId);
			map.put("service", service); 

			return ResponseEntity.ok(map);
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is invalid or " + ex.getMessage());
		}
	}

    @PostMapping("/edit")
	public ResponseEntity<?> updateAService(
			@RequestHeader(name = "Authorization", required = false) String authHeader, @RequestBody Service service) {

		
		// log.info("authHeader = {}", authHeader);
		if(authHeader==null) {
			// Authorization header is missing
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
		}
		
		try {
			String token = authHeader.split(" ")[1]; // second element from the header's value
			// log.info("token = {}", token);
			Integer userId = JwtUtil.verify(token);

			serviceDao.updateAService(service);
			
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("user_id", userId);
			map.put("updated_service", service); 
            
			return ResponseEntity.ok(map);
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is invalid or " + ex.getMessage());
		}
	}

     @GetMapping("{category_name}/{n}") // not sure of this
	public ResponseEntity<?> getTopServiceInCategory(@PathVariable("n") Integer n,
			@PathVariable("category_name") String category) {
		try {
			List<Service> service = userDao.getTopRatedNServicesWithinACategory(category, n);
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("service", service);
			return ResponseEntity.ok(map);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}

}
