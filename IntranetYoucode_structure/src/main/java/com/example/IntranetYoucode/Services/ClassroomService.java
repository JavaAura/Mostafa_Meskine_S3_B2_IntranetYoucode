package com.example.IntranetYoucode.Services;

import com.example.IntranetYoucode.Entities.Classroom;
import com.example.IntranetYoucode.Entities.DTO.ClassroomDTO;
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
        return classroomRepository.findById(id).map(this::convertToDTO);
    }

    public ClassroomDTO createClassroom(ClassroomDTO classroomDTO) {
        Classroom newClassroom = new Classroom();
        newClassroom.setName(classroomDTO.getName());
        newClassroom.setNumRoom(classroomDTO.getNumRoom());
        return convertToDTO(classroomRepository.save(newClassroom));
    }

    public ClassroomDTO updateClassroom(Long id, ClassroomDTO classroomDetails) {
        return classroomRepository.findById(id)
                .map(classroomEntity -> {
                    classroomEntity.setName(classroomDetails.getName());
                    classroomEntity.setNumRoom(classroomDetails.getNumRoom());
                    return convertToDTO(classroomRepository.save(classroomEntity));
                })
                .orElseThrow(() -> new RuntimeException("Classroom not found with id " + id));
    }

    public void deleteClassroom(Long id) {
        classroomRepository.deleteById(id);
    }

    private ClassroomDTO convertToDTO(Classroom classroomEntity) {
        return new ClassroomDTO(classroomEntity.getId(), classroomEntity.getName(), classroomEntity.getNumRoom());
    }
}

