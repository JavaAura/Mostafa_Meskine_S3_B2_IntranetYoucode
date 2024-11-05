package com.example.IntranetYoucode.Services;

import com.example.IntranetYoucode.Entities.DTO.TrainingDTO;
import com.example.IntranetYoucode.Entities.Training;
import com.example.IntranetYoucode.Exceptions.EntityNotFoundException;
import com.example.IntranetYoucode.Exceptions.InvalidEntityException;
import com.example.IntranetYoucode.Repositories.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class TrainingService {

    private final TrainingRepository trainingRepository;

    public List<TrainingDTO> getAllTrainings() {
        return trainingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TrainingDTO getTrainingById(Long id) {
        return trainingRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Training", id));
    }

    public TrainingDTO createTraining(@Valid TrainingDTO trainingDTO) {
        if (trainingDTO.getTitle() == null || trainingDTO.getLevel() == null) {
            throw new InvalidEntityException("Training", "Title and Level cannot be null");
        }
        return convertToDTO(trainingRepository.save(createTrainingEntity(trainingDTO)));
    }

    public Optional<TrainingDTO> updateTraining(Long id, @Valid TrainingDTO trainingDetails) {
        return trainingRepository.findById(id)
                .map(trainingEntity -> {
                    trainingEntity.setTitle(trainingDetails.getTitle());
                    trainingEntity.setLevel(trainingDetails.getLevel());
                    trainingEntity.setPrerequisites(trainingDetails.getPrerequisites());
                    trainingEntity.setMinCapacity(trainingDetails.getMinCapacity());
                    trainingEntity.setMaxCapacity(trainingDetails.getMaxCapacity());
                    trainingEntity.setStartDate(trainingDetails.getStartDate());
                    trainingEntity.setEndDate(trainingDetails.getEndDate());
                    trainingEntity.setStatus(trainingDetails.getStatus());
                    return convertToDTO(trainingRepository.save(trainingEntity));
                });
    }

    public void deleteTraining(Long id) {
        if (!trainingRepository.existsById(id)) {
            throw new EntityNotFoundException("Training", id);
        }
        trainingRepository.deleteById(id);
    }

    private Training createTrainingEntity(TrainingDTO trainingDTO) {
        return new Training(
                null,
                trainingDTO.getTitle(),
                trainingDTO.getLevel(),
                trainingDTO.getPrerequisites(),
                trainingDTO.getMinCapacity(),
                trainingDTO.getMaxCapacity(),
                trainingDTO.getStartDate(),
                trainingDTO.getEndDate(),
                trainingDTO.getStatus(),
                null
        );
    }

    private TrainingDTO convertToDTO(Training trainingEntity) {
        return new TrainingDTO(
                trainingEntity.getId(),
                trainingEntity.getTitle(),
                trainingEntity.getLevel(),
                trainingEntity.getPrerequisites(),
                trainingEntity.getMinCapacity(),
                trainingEntity.getMaxCapacity(),
                trainingEntity.getStartDate(),
                trainingEntity.getEndDate(),
                trainingEntity.getStatus()
        );
    }
}
