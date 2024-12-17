package com.linkedin.backend.features.authentication.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linkedin.backend.features.feed.model.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class AuthenticationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Email
    @NotNull
    private String email;

    private Boolean emailVerified = false;
    private String emailVerificationToken = null;

    @Column(columnDefinition = "datetime")
    private LocalDateTime emailVerificationTokenExpiryDate;

    @JsonIgnore
    private String password;

    private String passwordResetToken=null;

    @Column(columnDefinition = "datetime")
    private LocalDateTime passwordResetTokenExpiryDate;

    private String firstName=null;

    private String lastName=null;

    private String company=null;

    private String position =null;

    private String location=null;

    private Boolean profileComplete=false;


    @JsonIgnore
    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Post>posts;

//    @CreationTimestamp
//    private LocalDateTime creationDate;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        updateProfileCompletionStatus();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        updateProfileCompletionStatus();
    }

    public void setCompany(String company) {
        this.company = company;
        updateProfileCompletionStatus();
    }

    public void setPosition(String position) {
        this.position = position;
        updateProfileCompletionStatus();
    }

    public void setLocation(String location) {
        this.location = location;
        updateProfileCompletionStatus();
    }

    public AuthenticationUser(String email, String password){
        this.email=email;
        this.password=password;
    }

    private void updateProfileCompletionStatus(){
        this.profileComplete = (this.firstName!=null && this.lastName!=null&&this.position!=null&& this.location!=null);
    }

}

