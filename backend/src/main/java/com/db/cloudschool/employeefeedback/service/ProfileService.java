package com.db.cloudschool.employeefeedback.service;

import com.db.cloudschool.employeefeedback.exceptions.ProfileNotFoundException;
import com.db.cloudschool.employeefeedback.model.Profile;
import com.db.cloudschool.employeefeedback.model.Review;
import com.db.cloudschool.employeefeedback.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    public Profile getProfileById(Long id) {
        return profileRepository.getProfileByProfileId(id);
    }

    public List<Profile> getProfilesByFirstName(String firstName) throws ProfileNotFoundException {
        List<Profile> profileList = profileRepository.getProfilesByFirstName(firstName);
        if (profileList == null) {
            throw new ProfileNotFoundException();
        } else {
            return profileList;
        }
    }

    public List<Profile> getProfilesByLastName(String lastName) throws ProfileNotFoundException {
        List<Profile> profileList = profileRepository.getProfilesByLastName(lastName);
        if (profileList == null) {
            throw new ProfileNotFoundException();
        } else {
            return profileList;
        }
    }

    public List<Profile> getProfilesByFullName(String firstName, String lastName) throws ProfileNotFoundException {
        List<Profile> profileList = getProfilesByFirstName(firstName);
        List<Profile> profileList1 = getProfilesByLastName(lastName);
        if(profileList == null || profileList1 == null) {
            throw new ProfileNotFoundException();
        } else {
            return profileList.stream().filter(profileList1 :: contains).collect(Collectors.toList());
        }
    }

    public void register (Profile profile){
        profileRepository.save(profile);
    }

    public void update (Profile profile){
        profileRepository.save(profile);
    }

    public void updateReviews (Review review) {
        Profile reviewSender = review.getSender();
        List<Review> sentReviews = reviewSender.getSentReviews();
        sentReviews.add(review);
        reviewSender.setSentReviews(sentReviews);
        profileRepository.save(reviewSender);

        Profile reviewReceiver = review.getReceiver();
        List<Review> receivedReviews = reviewReceiver.getReceivedReviews();
        receivedReviews.add(review);
        reviewReceiver.setComputedScore1(computeScores(receivedReviews, 1));
        reviewReceiver.setComputedScore2(computeScores(receivedReviews, 2));
        reviewReceiver.setComputedScore3(computeScores(receivedReviews, 3));
        profileRepository.save(reviewReceiver);
    }

    public Double computeScores(List<Review> reviews, int index) {
        Double sum = 0.0;
        if(index == 1) {
            for(Review rev : reviews) {
                sum += rev.getScore1();
            }
        } else if (index == 2) {
            for(Review rev : reviews) {
                sum += rev.getScore2();
            }
        } else {
            for(Review rev : reviews) {
                sum += rev.getScore3();
            }
        }
        return sum/reviews.size();
    }

    public void deleteProfile (Long id){
        profileRepository.delete(profileRepository.getProfileByProfileId(id));
    }
}