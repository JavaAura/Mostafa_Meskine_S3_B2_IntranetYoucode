package com.example.IntranetYoucode.Controllers;

import com.example.IntranetYoucode.Entities.DTO.TrainingDTO;
import com.example.IntranetYoucode.Services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/trainings")
public class TrainingController {

    private final TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping
    public List<TrainingDTO> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    @GetMapping("/{id}")
    public TrainingDTO getTrainingById(@PathVariable Long id) {
        return trainingService.getTrainingById(id);
    }

    @PostMapping
    public TrainingDTO createTraining(@RequestBody @Valid TrainingDTO trainingDTO) {
        return trainingService.createTraining(trainingDTO);
    }

    @PutMapping("/{id}")
    public TrainingDTO updateTraining(@PathVariable Long id, @RequestBody @Valid TrainingDTO trainingDetails) {
        return trainingService.updateTraining(id, trainingDetails)
                .orElse(null);  // Return null if training not found
    }

    @DeleteMapping("/{id}")
    public void deleteTraining(@PathVariable Long id) {
        trainingService.deleteTraining(id);
    }
}

