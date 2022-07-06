package com.db.cloudschool.employeefeedback.service;

import com.db.cloudschool.employeefeedback.dto.ReviewDTO;
import com.db.cloudschool.employeefeedback.model.Profile;
import com.db.cloudschool.employeefeedback.model.Review;
import com.db.cloudschool.employeefeedback.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    final ReviewRepository reviewRepository;

    public List<Review> getAll(){
        return reviewRepository.findAll();
    }

    public Review getById(Long reviewId){
        Optional<Review> reviewOpt = reviewRepository.findById(reviewId);
        return reviewOpt.orElse(null);
    }

    public Review create(ReviewDTO reviewDTO, Profile sender, Profile receiver) throws Exception {
        if(!Objects.equals(sender.getIdentity().getApiUserId(), receiver.getIdentity().getApiUserId())) {
            Review newReview = new Review();

            newReview.setReceiver(receiver);
            newReview.setSender(sender);
            newReview.setScore1(reviewDTO.getScore1() * 10);
            newReview.setScore2(reviewDTO.getScore2() * 10);
            newReview.setScore3(reviewDTO.getScore3() * 10);
            newReview.setComment(reviewDTO.getComment());
            newReview.setTimestamp(new Date().getTime());

            reviewRepository.save(newReview);

            return newReview;
        }
        else{
            throw new Exception("cannot rate yourself!");
        }
    }

    public Review update(Long oldReviewId, ReviewDTO newReview){
        Review updatedReview = this.getById(oldReviewId);

        updatedReview.setScore1(newReview.getScore1() * 10);
        updatedReview.setScore2(newReview.getScore2() * 10);
        updatedReview.setScore3(newReview.getScore3() * 10);
        updatedReview.setComment(newReview.getComment());
        updatedReview.setTimestamp(new Date().getTime());

        reviewRepository.save(updatedReview);

        return updatedReview;
    }

    public void delete(Long reviewId){
        reviewRepository.deleteById(reviewId);
    }
}
