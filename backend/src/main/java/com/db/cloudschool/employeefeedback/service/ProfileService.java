package com.db.cloudschool.employeefeedback.service;

import com.db.cloudschool.employeefeedback.exceptions.ProfileNotFoundException;
import com.db.cloudschool.employeefeedback.model.Profile;
import com.db.cloudschool.employeefeedback.model.Review;
import com.db.cloudschool.employeefeedback.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The ProfileService manages registration,
 * updates and the removal of a profile.
 * @author Tudor
 */
@Service
@RequiredArgsConstructor
public class ProfileService {
    final ProfileRepository profileRepository;

    /**
     * This function will look in the database for the profile
     * with the requested id
     * @param id
     * @return the profile with the specified id
     * @throws ProfileNotFoundException
     */
    public Profile getProfileById(Long id) throws ProfileNotFoundException{
        if(profileRepository.getProfileByProfileId(id) == null) {
            throw new ProfileNotFoundException();
        } else {
            return profileRepository.getProfileByProfileId(id);
        }
    }

    /**
     * This function will return a list of all profiles that have
     * the first name equal with the one sent as an argument
     * @param firstName
     * @return a list of profiles with the specified first name
     * @throws ProfileNotFoundException
     */
    public List<Profile> getProfilesByFirstName(String firstName) throws ProfileNotFoundException {
        List<Profile> profileList = profileRepository.getProfilesByFirstName(firstName);
        if (profileList == null) {
            throw new ProfileNotFoundException();
        } else {
            return profileList;
        }
    }


    /**
     * This function will return a list of all profiles that have
     * the last name equal with the one sent as an argument
     * @param lastName
     * @return a list of profiles with the specified last name
     * @throws ProfileNotFoundException
     */
    public List<Profile> getProfilesByLastName(String lastName) throws ProfileNotFoundException {
        List<Profile> profileList = profileRepository.getProfilesByLastName(lastName);
        if (profileList == null) {
            throw new ProfileNotFoundException();
        } else {
            return profileList;
        }
    }

    /**
     * This function finds all profiles that have a certain full name
     * @param firstName
     * @param lastName
     * @return a list of all profiles with the requested first and last names
     * @throws ProfileNotFoundException
     */
    public List<Profile> getProfilesByFullName(String firstName, String lastName) throws ProfileNotFoundException {
        List<Profile> profileList = getProfilesByFirstName(firstName);
        List<Profile> profileList1 = getProfilesByLastName(lastName);
        if(profileList == null || profileList1 == null) {
            throw new ProfileNotFoundException();
        } else {
            return profileList.stream().filter(profileList1 :: contains).collect(Collectors.toList());
        }
    }

    /**
     * This function saves a profile in the database
     * @param profile
     */
    public void register (Profile profile){
        profileRepository.save(profile);
    }

    /**
     * This function updates a profile's info and
     * overwrites the last version in the database
     * @param profile
     */
    public void update (Profile profile){
        profileRepository.save(profile);
    }

    /**
     * This function updates the list of reviews for
     * both the sender and the receiver. In addition,
     * the average scores are updated after adding a new review
     * in the receiver's list.
     * @param review
     */
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

    /**
     * This function calculates the average score for one category
     * of reviews. The category is decided by the index sent as an
     * argument.
     * index = 1 => score1
     * index = 2 => score2
     * index = 3 => score3
     * @param reviews
     * @param index
     * @return the average score which will be overwritten in the profile
     */
    public Double computeScores(List<Review> reviews, int index) {
        Double sum = 0.0;
        switch (index) {
            case 1:
                for (Review rev : reviews) {
                    sum += rev.getScore1();
                }
                break;
            case 2:
                for (Review rev : reviews) {
                    sum += rev.getScore2();
                }
                break;
            default:
                for (Review rev : reviews) {
                    sum += rev.getScore3();
                }
                break;
        }
        return sum/reviews.size();
    }

    /**
     * function that deletes a profile from the database
     * @param id
     */
    public void deleteProfile (Long id){
        profileRepository.delete(profileRepository.getProfileByProfileId(id));
    }
}