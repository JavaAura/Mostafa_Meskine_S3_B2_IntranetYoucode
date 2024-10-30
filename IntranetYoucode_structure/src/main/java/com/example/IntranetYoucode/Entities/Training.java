package com.example.IntranetYoucode.Entities;

import com.example.IntranetYoucode.Entities.Enum.TrainingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String level;
    private String prerequisites;
    private Integer minCapacity;
    private Integer maxCapacity;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private TrainingStatus status;

    // Relationships

    // Getters and Setters
}


