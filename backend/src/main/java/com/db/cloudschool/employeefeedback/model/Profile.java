package com.db.cloudschool.employeefeedback.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @Id
    @GeneratedValue
    @NotNull
    private Integer id;
    //    private User user;
    private String biographyText;
    private String fullName;
    private String profilePhotoURL;
//    private ArrayList<Review> reviews;

//    @Transient
//    private Double meanScore;
//    private ScoreOutputDTO meanScore;

//    private ScoreOutputDTO computeMeanScore(){
//          new ScoreOutputDTO(meanScore1, meanScore2, meanScore3);
//    }

//    private Double getMeanScore(ArrayList<Review> reviews, int index){
//          return meanScore for score1/2/3, depending on index
//    }
}
