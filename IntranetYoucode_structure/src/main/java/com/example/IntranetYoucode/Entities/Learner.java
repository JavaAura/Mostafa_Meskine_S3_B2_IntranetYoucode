package com.example.IntranetYoucode.Entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@DiscriminatorValue("LEARNER")
@AllArgsConstructor
@NoArgsConstructor
public class Learner extends User {

    @NotBlank(message = "Level cannot be blank")
    private String level;
}
