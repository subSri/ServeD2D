package com.sapient.controllers;

import java.util.HashMap;
import java.util.Map;

import com.sapient.entity.User;
import com.sapient.utils.JwtUtil;
import com.sapient.dao.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class LoginController {

	// @Autowired
    // private UserDao userDao;
	UserDao userDao = new UserDao();

	@PostMapping("/api/login")
	public ResponseEntity<?> login(@RequestBody User user) throws Exception {

		if (userDao.verifyUserCreds(user)) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", user.getId()); // need to get this from DB using DAO
			map.put("fullname", user.getName());
			map.put("token", JwtUtil.createToken(user.getId(), user.getName()));
			
			return ResponseEntity.ok(map);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email/password");
		}

	}
	
	@PostMapping("/api/register")
	public ResponseEntity<?> register(@RequestBody User user) throws Exception {
		if (!(userDao.verifyUserCreds(user))) {
			userDao.addNewUser(user);
			Map<String, Object> map = new HashMap<>();
			map.put("success", true);
			map.put("id", user.getId());
			map.put("fullname", user.getName());
			

			return ResponseEntity.ok(map);
		} else {
			return  ResponseEntity.status(HttpStatus.CONFLICT).body("This account already exists!");
		}

	}
	

}
