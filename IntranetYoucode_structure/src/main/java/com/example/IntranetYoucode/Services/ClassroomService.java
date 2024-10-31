package com.example.IntranetYoucode.Services;

import com.example.IntranetYoucode.Entities.Classroom;
import com.example.IntranetYoucode.Entities.DTO.ClassroomDTO;
import com.example.IntranetYoucode.Exceptions.EntityNotFoundException;
import com.example.IntranetYoucode.Exceptions.InvalidEntityException;
import com.example.IntranetYoucode.Repositories.ClassroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassroomService {
    private final ClassroomRepository classroomRepository;

    public List<ClassroomDTO> getAllClassrooms() {
        return classroomRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ClassroomDTO> getClassroomById(Long id) {
        return classroomRepository.findById(id)
                .map(this::convertToDTO);
    }

    public ClassroomDTO createClassroom(ClassroomDTO classroomDTO) {
        if (classroomDTO.getName() == null || classroomDTO.getRoomNumber() == null) {
            throw new InvalidEntityException("Classroom", "Name and Room Number cannot be null");
        }
        return convertToDTO(classroomRepository.save(createClassroomEntity(classroomDTO)));
    }

    public Optional<ClassroomDTO> updateClassroom(Long id, ClassroomDTO classroomDetails) {
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

    private Classroom createClassroomEntity(ClassroomDTO classroomDTO) {
        return new Classroom(null, classroomDTO.getName(), classroomDTO.getRoomNumber(), null);
    }

    private ClassroomDTO convertToDTO(Classroom classroomEntity) {
        return new ClassroomDTO(classroomEntity.getId(), classroomEntity.getName(), classroomEntity.getRoomNumber());
    }
}
