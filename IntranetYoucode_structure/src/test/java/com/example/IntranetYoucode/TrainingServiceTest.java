package com.example.IntranetYoucode;

import com.example.IntranetYoucode.Entities.DTO.TrainingDTO;
import com.example.IntranetYoucode.Entities.Enum.TrainingStatus;
import com.example.IntranetYoucode.Entities.Training;
import com.example.IntranetYoucode.Repositories.TrainingRepository;
import com.example.IntranetYoucode.Services.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingServiceTest {

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainingService trainingService;

    private Training training;
    private TrainingDTO trainingDTO;

    @BeforeEach
    void setUp() {
        training = new Training(1L, "Java Basics", "Beginner", "None", 10, 20, null, null, TrainingStatus.IN_PROGRESS, null);
        trainingDTO = new TrainingDTO(1L, "Java Basics", "Beginner", "None", 10, 20, null, null, TrainingStatus.IN_PROGRESS);
    }

    @Test
    void getAllTrainingsTest() {
        when(trainingRepository.findAll()).thenReturn(Arrays.asList(training));

        List<TrainingDTO> result = trainingService.getAllTrainings();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trainingDTO.getTitle(), result.get(0).getTitle());
        verify(trainingRepository, times(1)).findAll();
    }

    @Test
    void getTrainingByIdTest() {
        when(trainingRepository.findById(1L)).thenReturn(Optional.of(training));

        TrainingDTO result = trainingService.getTrainingById(1L);

        assertNotNull(result);
        assertEquals(trainingDTO.getTitle(), result.getTitle());
        verify(trainingRepository, times(1)).findById(1L);
    }

    @Test
    void createTrainingTest() {
        when(trainingRepository.save(any(Training.class))).thenReturn(training);

        TrainingDTO result = trainingService.createTraining(trainingDTO);

        assertNotNull(result);
        assertEquals(trainingDTO.getTitle(), result.getTitle());
        verify(trainingRepository, times(1)).save(any(Training.class));
    }

    @Test
    void updateTrainingTest() {
        Training updatedTraining = new Training(1L, "Advanced Java", "Intermediate", "Java Basics", 10, 20, null, null, TrainingStatus.IN_PROGRESS, null);
        TrainingDTO updatedTrainingDTO = new TrainingDTO(1L, "Advanced Java", "Intermediate", "Java Basics", 10, 20, null, null, TrainingStatus.IN_PROGRESS);

        when(trainingRepository.findById(1L)).thenReturn(Optional.of(training));
        when(trainingRepository.save(training)).thenReturn(updatedTraining);

        Optional<TrainingDTO> result = trainingService.updateTraining(1L, updatedTrainingDTO);

        assertTrue(result.isPresent());
        assertEquals("Advanced Java", result.get().getTitle());
        verify(trainingRepository, times(1)).findById(1L);
        verify(trainingRepository, times(1)).save(training);
    }

    @Test
    void deleteTrainingTest() {
        when(trainingRepository.existsById(1L)).thenReturn(true);

        trainingService.deleteTraining(1L);

        verify(trainingRepository, times(1)).existsById(1L);
        verify(trainingRepository, times(1)).deleteById(1L);
    }
}
