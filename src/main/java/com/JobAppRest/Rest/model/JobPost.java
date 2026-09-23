package com.JobAppRest.Rest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

//using lombok dependency , no need to create getters setters and noargs and all args annottaion for constructors reduce lines of code
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    @NotBlank(message = "Profile is required")
    private String postProfile;
    @NotBlank(message = "Description is required")
    private String postDesc;
    @PositiveOrZero(message="Experience cannot be negative")
    private Integer reqExperience;
    @Size(min=1,max=10,message = "Tech stack must contains 1-10 technologies")
    private List<String> postTechStack;

    public JobPost(String postProfile, String postDesc, Integer reqExperience, List<String> postTechStack) {
        this.postProfile = postProfile;
        this.postDesc = postDesc;
        this.reqExperience = reqExperience;
        this.postTechStack = postTechStack;
    }
}
