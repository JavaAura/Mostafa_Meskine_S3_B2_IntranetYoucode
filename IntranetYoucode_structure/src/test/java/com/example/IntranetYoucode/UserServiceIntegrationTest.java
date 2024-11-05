package com.example.IntranetYoucode;

import com.example.IntranetYoucode.Entities.DTO.UserDTO;
import com.example.IntranetYoucode.Entities.User;
import com.example.IntranetYoucode.Entities.Classroom;
import com.example.IntranetYoucode.Entities.Training;
import com.example.IntranetYoucode.Repositories.UserRepository;
import com.example.IntranetYoucode.Services.ClassroomService;
import com.example.IntranetYoucode.Services.TrainingService;
import com.example.IntranetYoucode.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private TrainingService trainingService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateUserIntegration() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john.doe@example.com");

        UserDTO createdUserDTO = userService.createUser(userDTO);

        assertNotNull(createdUserDTO);
        assertEquals("John", createdUserDTO.getFirstName());
        assertEquals("Doe", createdUserDTO.getLastName());
        assertEquals("john.doe@example.com", createdUserDTO.getEmail());

        User user = userRepository.findById(createdUserDTO.getId()).orElse(null);
        assertNotNull(user);
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    void testGetUserByIdIntegration() {

        User user = new User("Jane", "Smith", "jane.smith@example.com");
        userRepository.save(user);

        UserDTO userDTO = userService.getUserById(user.getId());

        assertNotNull(userDTO);
        assertEquals("Jane", userDTO.getFirstName());
        assertEquals("Smith", userDTO.getLastName());
        assertEquals("jane.smith@example.com", userDTO.getEmail());
    }

    @Test
    void testUpdateUserIntegration() {

        User user = new User("Mike", "Johnson", "mike.johnson@example.com");
        userRepository.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Michael");
        userDTO.setLastName("Johnson");
        userDTO.setEmail("michael.johnson@example.com");

        userService.updateUser(user.getId(), userDTO);

        User updatedUser = userRepository.findById(user.getId()).orElse(null);

        assertNotNull(updatedUser);
        assertEquals("Michael", updatedUser.getFirstName());
        assertEquals("Johnson", updatedUser.getLastName());
        assertEquals("michael.johnson@example.com", updatedUser.getEmail());
    }

    @Test
    void testDeleteUserIntegration() {

        User user = new User("Sarah", "Lee", "sarah.lee@example.com");
        userRepository.save(user);

        User existingUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(existingUser);

        userService.deleteUser(user.getId());

        User deletedUser = userRepository.findById(user.getId()).orElse(null);
        assertNull(deletedUser);
    }

    @Test
    void testAssignClassroomToUser() {

        User user = new User("Mark", "Taylor", "mark.taylor@example.com");
        userRepository.save(user);

        Classroom classroom = new Classroom("Room 101", "101");
        classroomService.createClassroom(classroom);

        user.setClassroom(classroom);
        userService.updateUser(user.getId(), new UserDTO(user));

        User updatedUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(updatedUser);
        assertNotNull(updatedUser.getClassroom());
        assertEquals(classroom.getId(), updatedUser.getClassroom().getId());
    }

    @Test
    void testAssignTrainingToUser() {

        User user = new User("Alice", "Williams", "alice.williams@example.com");
        userRepository.save(user);

        Training training = new Training("Java Programming", "Beginner");
        trainingService.createTraining(training);

        user.setTraining(training);
        userService.updateUser(user.getId(), new UserDTO(user));

        User updatedUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(updatedUser);
        assertNotNull(updatedUser.getTraining());
        assertEquals(training.getId(), updatedUser.getTraining().getId());
    }
}

