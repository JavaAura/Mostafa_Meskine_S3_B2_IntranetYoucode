package com.example.IntranetYoucode;

import com.example.IntranetYoucode.Entities.Classroom;
import com.example.IntranetYoucode.Entities.DTO.ClassroomDTO;
import com.example.IntranetYoucode.Exceptions.EntityNotFoundException;
import com.example.IntranetYoucode.Exceptions.InvalidEntityException;
import com.example.IntranetYoucode.Repositories.ClassroomRepository;
import com.example.IntranetYoucode.Services.ClassroomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClassroomServiceTest {

    @Mock
    private ClassroomRepository classroomRepository;

    @InjectMocks
    private ClassroomService classroomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllClassrooms() {
        Classroom classroom1 = new Classroom(1L, "Math", 101, null);
        Classroom classroom2 = new Classroom(2L, "Science", 102, null);
        when(classroomRepository.findAll()).thenReturn(Arrays.asList(classroom1, classroom2));

        List<ClassroomDTO> classrooms = classroomService.getAllClassrooms();

        assertEquals(2, classrooms.size());
        assertEquals("Math", classrooms.get(0).getName());
        assertEquals(101, classrooms.get(0).getRoomNumber());
    }

    @Test
    void getClassroomById() {
        Classroom classroom = new Classroom(1L, "Math", 101, null);
        when(classroomRepository.findById(1L)).thenReturn(Optional.of(classroom));

        ClassroomDTO result = classroomService.getClassroomById(1L);

        assertNotNull(result);
        assertEquals("Math", result.getName());
        assertEquals(101, result.getRoomNumber());
    }

    @Test
    void createClassroom() {
        ClassroomDTO classroomDTO = new ClassroomDTO(null, "Math", 101);
        Classroom classroom = new Classroom(1L, "Math", 101, null);
        when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom);

        ClassroomDTO result = classroomService.createClassroom(classroomDTO);

        assertNotNull(result);
        assertEquals("Math", result.getName());
        assertEquals(101, result.getRoomNumber());
    }

    @Test
    void updateClassroom() {
        Classroom classroom = new Classroom(1L, "Math", 101, null);
        ClassroomDTO updatedDTO = new ClassroomDTO(null, "Updated Math", 102);
        when(classroomRepository.findById(1L)).thenReturn(Optional.of(classroom));
        when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom);

        Optional<ClassroomDTO> result = classroomService.updateClassroom(1L, updatedDTO);

        assertTrue(result.isPresent());
        assertEquals("Updated Math", result.get().getName());
        assertEquals(102, result.get().getRoomNumber());
    }

    @Test
    void deleteClassroom() {
        when(classroomRepository.existsById(1L)).thenReturn(true);

        classroomService.deleteClassroom(1L);

        verify(classroomRepository, times(1)).deleteById(1L);
    }
}

