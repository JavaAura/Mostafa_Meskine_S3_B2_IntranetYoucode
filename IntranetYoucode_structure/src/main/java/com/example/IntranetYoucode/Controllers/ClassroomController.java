package com.example.IntranetYoucode.Controllers;

import com.example.IntranetYoucode.Entities.DTO.ClassroomDTO;
import com.example.IntranetYoucode.Services.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {
    private final ClassroomService classroomService;

    @Autowired
    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public List<ClassroomDTO> getAllClassrooms() {
        return classroomService.getAllClassrooms();
    }

    @GetMapping("/{id}")
    public ClassroomDTO getClassroomById(@PathVariable Long id) {
        return classroomService.getClassroomById(id);
    }

    @PostMapping
    public ClassroomDTO createClassroom(@RequestBody ClassroomDTO classroomDTO) {
        return classroomService.createClassroom(classroomDTO);
    }

    @PutMapping("/{id}")
    public ClassroomDTO updateClassroom(@PathVariable Long id, @RequestBody ClassroomDTO classroomDetails) {
        return classroomService.updateClassroom(id, classroomDetails)
                .orElse(null);  // Return null if classroom not found
    }

    @DeleteMapping("/{id}")
    public void deleteClassroom(@PathVariable Long id) {
        classroomService.deleteClassroom(id);
    }
}
