package com.example.IntranetYoucode.Entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("INSTRUCTOR")
@AllArgsConstructor
@NoArgsConstructor
public class Instructor extends User {

    private String specialty;

}
