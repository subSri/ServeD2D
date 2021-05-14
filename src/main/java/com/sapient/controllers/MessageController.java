package com.sapient.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sapient.dao.MessageDao;
import com.sapient.entity.Message;
import com.sapient.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chats")
public class MessageController {

    @Autowired
	private MessageDao messageDao;
    
    @PostMapping
    public ResponseEntity<?> addAMessage(
			@RequestHeader(name = "Authorization", required = false) String authHeader, @RequestBody Message message) {

		
		// log.info("authHeader = {}", authHeader);
		if(authHeader==null) {
			// Authorization header is missing
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
		}
		
		try {
			String token = authHeader.split(" ")[1]; // second element from the header's value
			// log.info("token = {}", token);
			Integer userId = JwtUtil.verify(token);

			Boolean done =  messageDao.liveMessage(message);
			if (done == true){
                Map<String, Object> map = new HashMap<>();
                map.put("success", true);
                map.put("user_id", userId);
                map.put("messages", message); 

                return ResponseEntity.ok(map);
            }
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Message not sent");
            }
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is invalid or " + ex.getMessage());
		}
	}
    
    @GetMapping(value="", params = "order_id")
	public ResponseEntity<?> getMessagesOfASpecificOrder(@RequestParam(name="order_id", required = true) Integer orderId,
		@RequestHeader(name = "Authorization", required = false) String authHeader) {

        if(authHeader==null) {
            // Authorization header is missing
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
        }
	
        try {
            String token = authHeader.split(" ")[1]; // second element from the header's value
            // log.info("token = {}", token);
            Integer userId = JwtUtil.verify(token);
            
            List<Message> messages = messageDao.returnAllMessagesForAnOrder(orderId);
            
            Map<String, Object> map = new HashMap<>();
            map.put("success", true);
            map.put("user_id", userId);
            map.put("messages", messages); 
            return ResponseEntity.ok(map);
        }
        catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is invalid or " + ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getDetailsOfASpecificOrder(
        @RequestHeader(name = "Authorization", required = false) String authHeader) {

        if(authHeader==null) {
            // Authorization header is missing
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
        }
        
        try {
            String token = authHeader.split(" ")[1]; // second element from the header's value
            // log.info("token = {}", token);
            Integer userId = JwtUtil.verify(token);
            
            List<Integer> chats = messageDao.returnChatsOfUser(userId);
            
            Map<String, Object> map = new HashMap<>();
            map.put("success", true);
            map.put("user_id", userId);
            map.put("chats", chats); 
            return ResponseEntity.ok(map);
        }
        catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is invalid or " + ex.getMessage());
        }
}
    
	}