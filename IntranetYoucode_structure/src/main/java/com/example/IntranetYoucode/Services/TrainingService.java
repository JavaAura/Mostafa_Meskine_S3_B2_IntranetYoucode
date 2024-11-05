package com.example.IntranetYoucode.Services;

import com.example.IntranetYoucode.Entities.DTO.TrainingDTO;
import com.example.IntranetYoucode.Entities.Training;
import com.example.IntranetYoucode.Exceptions.EntityNotFoundException;
import com.example.IntranetYoucode.Exceptions.InvalidEntityException;
import com.example.IntranetYoucode.Repositories.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
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
    private final ObjectProvider<Training> trainingProvider;
    private final ObjectProvider<TrainingDTO> trainingDTOProvider;

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

    // New method to get the Training entity directly
    public Training getTrainingEntityById(Long id) {
        return trainingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Training", id));
    }

    public TrainingDTO createTraining(@Valid TrainingDTO trainingDTO) {
        if (trainingDTO.getTitle() == null || trainingDTO.getLevel() == null) {
            throw new InvalidEntityException("Training", "Title and Level cannot be null");
        }
        Training trainingEntity = createTrainingEntity(trainingDTO);
        return convertToDTO(trainingRepository.save(trainingEntity));
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
        Training training = trainingProvider.getObject();
        training.setTitle(trainingDTO.getTitle());
        training.setLevel(trainingDTO.getLevel());
        training.setPrerequisites(trainingDTO.getPrerequisites());
        training.setMinCapacity(trainingDTO.getMinCapacity());
        training.setMaxCapacity(trainingDTO.getMaxCapacity());
        training.setStartDate(trainingDTO.getStartDate());
        training.setEndDate(trainingDTO.getEndDate());
        training.setStatus(trainingDTO.getStatus());
        return training;
    }

    private TrainingDTO convertToDTO(Training trainingEntity) {
        TrainingDTO trainingDTO = trainingDTOProvider.getObject();
        trainingDTO.setId(trainingEntity.getId());
        trainingDTO.setTitle(trainingEntity.getTitle());
        trainingDTO.setLevel(trainingEntity.getLevel());
        trainingDTO.setPrerequisites(trainingEntity.getPrerequisites());
        trainingDTO.setMinCapacity(trainingEntity.getMinCapacity());
        trainingDTO.setMaxCapacity(trainingEntity.getMaxCapacity());
        trainingDTO.setStartDate(trainingEntity.getStartDate());
        trainingDTO.setEndDate(trainingEntity.getEndDate());
        trainingDTO.setStatus(trainingEntity.getStatus());
        return trainingDTO;
    }
}
