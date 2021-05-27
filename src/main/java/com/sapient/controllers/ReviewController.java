package com.sapient.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sapient.dao.*;
import com.sapient.entity.Review;
import com.sapient.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@RequestMapping("/api/reviews")
// @Slf4j
public class ReviewController {

    @Autowired
    private ReviewDao reviewDao;

    @GetMapping("/{service_id}")
    public ResponseEntity<?> getSelectedReviews(@PathVariable("service_id") Integer serviceId,
            @RequestHeader(name = "Authorization", required = false) String authHeader) {

        // log.info("authHeader = {}", authHeader);
        if (authHeader == null) {
            // Authorization header is missing
            return ifAuthNull();
        }

        try {
            Integer userId = auth(authHeader);
            List<Map<String, String>> reviews = reviewDao.returnSelectedReviews(serviceId);

            Map<String, Object> map = getResponse(userId);
            map.put("review", reviews);
            return ResponseEntity.ok(map);
        } catch (Exception ex) {
            return getCatchResponse(ex);
        }
    }

    private Map<String, Object> getResponse(Integer userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("user_id", userId);
        return map;
    }

    private Integer auth(String authHeader) throws Exception {
        String token = authHeader.split(" ")[1]; // second element from the header's value
        // log.info("token = {}", token);
        Integer userId = JwtUtil.verify(token);
        return userId;
    }

    private ResponseEntity<?> ifAuthNull() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing");
    }

    @PostMapping
    public ResponseEntity<?> makeANewReview(@RequestHeader(name = "Authorization", required = false) String authHeader,
            @RequestBody Review review) {

        if (authHeader == null) {
            return ifAuthNull();
        }

        try {
            Integer userId = auth(authHeader);
            System.out.println(review);
            Review getReview = reviewDao.addReview(review);
            Map<String, Object> map = getResponse(userId);
            map.put("review", getReview);
            return ResponseEntity.ok(map);
        } catch (Exception ex) {
            return getCatchResponse(ex);
        }
    }

    private ResponseEntity<?> getCatchResponse(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Authorization token is invalid or " + ex.getMessage());
    }

    @GetMapping
    public ResponseEntity<?> getAllRatings() {

        try {

            List<Review> reviews = reviewDao.returnAllReviews();

            Map<String, Object> map = new HashMap<>();
            map.put("success", true);
            map.put("reviews", reviews);
            return ResponseEntity.ok(map);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }
}
