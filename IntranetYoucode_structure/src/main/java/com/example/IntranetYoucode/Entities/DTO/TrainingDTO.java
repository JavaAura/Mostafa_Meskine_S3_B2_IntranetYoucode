package com.example.IntranetYoucode.Entities.DTO;

import com.example.IntranetYoucode.Entities.Enum.TrainingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Scope("prototype")
public class TrainingDTO {

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
    private Date startDate;

    @FutureOrPresent(message = "End date must be today or in the future")
    private Date endDate;

    private TrainingStatus status;
}

