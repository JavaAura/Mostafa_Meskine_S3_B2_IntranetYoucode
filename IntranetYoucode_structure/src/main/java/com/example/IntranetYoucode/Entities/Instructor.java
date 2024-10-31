package com.example.IntranetYoucode.Entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@DiscriminatorValue("INSTRUCTOR")
@AllArgsConstructor
@NoArgsConstructor
public class Instructor extends User {

    @NotBlank(message = "Specialty cannot be blank")
    private String specialty;
}
