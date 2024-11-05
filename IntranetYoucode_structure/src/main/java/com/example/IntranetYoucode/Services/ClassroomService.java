package com.example.IntranetYoucode.Services;

import com.example.IntranetYoucode.Entities.Classroom;
import com.example.IntranetYoucode.Entities.DTO.ClassroomDTO;
import com.example.IntranetYoucode.Exceptions.EntityNotFoundException;
import com.example.IntranetYoucode.Exceptions.InvalidEntityException;
import com.example.IntranetYoucode.Repositories.ClassroomRepository;
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
public class ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final ObjectProvider<Classroom> classroomProvider;
    private final ObjectProvider<ClassroomDTO> classroomDTOProvider;

    public List<ClassroomDTO> getAllClassrooms() {
        return classroomRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ClassroomDTO getClassroomById(Long id) {
        return classroomRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Classroom", id));
    }

    public ClassroomDTO createClassroom(@Valid ClassroomDTO classroomDTO) {
        if (classroomDTO.getName() == null || classroomDTO.getRoomNumber() == null) {
            throw new InvalidEntityException("Classroom", "Name and Room Number cannot be null");
        }

        if (classroomRepository.findByName(classroomDTO.getName()).isPresent()) {
            throw new InvalidEntityException("Classroom", "Classroom with this name already exists");
        }

        Classroom classroomEntity = createClassroomEntity(classroomDTO);
        return convertToDTO(classroomRepository.save(classroomEntity));
    }

    public Optional<ClassroomDTO> updateClassroom(Long id, @Valid ClassroomDTO classroomDetails) {
        return classroomRepository.findById(id)
                .map(classroomEntity -> {
                    classroomEntity.setName(classroomDetails.getName());
                    classroomEntity.setRoomNumber(classroomDetails.getRoomNumber());
                    return convertToDTO(classroomRepository.save(classroomEntity));
                });
    }

    public void deleteClassroom(Long id) {
        if (!classroomRepository.existsById(id)) {
            throw new EntityNotFoundException("Classroom", id);
        }
        classroomRepository.deleteById(id);
    }

    public Classroom getClassroomEntityById(Long id) {
        return classroomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classroom", id));
    }

    private Classroom createClassroomEntity(ClassroomDTO classroomDTO) {
        Classroom classroom = classroomProvider.getObject();
        classroom.setName(classroomDTO.getName());
        classroom.setRoomNumber(classroomDTO.getRoomNumber());
        // Additional fields can be set here if necessary
        return classroom;
    }

    private ClassroomDTO convertToDTO(Classroom classroomEntity) {
        ClassroomDTO classroomDTO = classroomDTOProvider.getObject();
        classroomDTO.setId(classroomEntity.getId());
        classroomDTO.setName(classroomEntity.getName());
        classroomDTO.setRoomNumber(classroomEntity.getRoomNumber());
        return classroomDTO;
    }
}
