package com.db.cloudschool.employeefeedback.controller;

import com.db.cloudschool.employeefeedback.dto.ReviewDTO;
import com.db.cloudschool.employeefeedback.model.Email;
import com.db.cloudschool.employeefeedback.payload.response.ReviewResponse;
import com.db.cloudschool.employeefeedback.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/review")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @PostMapping(path = "/id/{id}")
    public ReviewResponse setReviewForUserById(@PathVariable Long userId, @RequestBody ReviewDTO reviewDTO){
        return null;
    }

    @PostMapping(path = "/email/{email}")
    public ReviewResponse setReviewForUserByEmail(@PathVariable Email userEmail, @RequestBody ReviewDTO reviewDTO){
        return null;
    }
}
