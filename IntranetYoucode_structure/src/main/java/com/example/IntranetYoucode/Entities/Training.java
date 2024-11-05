package com.example.IntranetYoucode.Entities;

import com.example.IntranetYoucode.Entities.Enum.TrainingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "trainings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Scope("prototype")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Level is mandatory")
    private String level;

    private String prerequisites;

    @Min(value = 1, message = "Minimum capacity must be at least 1")
    private Integer minCapacity;

    @Min(value = 1, message = "Maximum capacity must be at least 1")
    private Integer maxCapacity;

    @Future(message = "Start date must be in the future")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @FutureOrPresent(message = "End date must be today or in the future")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private TrainingStatus status;

    // Relationships
    @OneToMany(mappedBy = "training", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<User> users;
}
