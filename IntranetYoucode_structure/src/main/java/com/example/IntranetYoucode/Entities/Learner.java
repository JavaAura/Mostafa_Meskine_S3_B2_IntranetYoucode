package com.example.IntranetYoucode.Entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("LEARNER")
@AllArgsConstructor
@NoArgsConstructor
public class Learner extends User {

    private String level;
}

