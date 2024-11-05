package com.example.IntranetYoucode.Services;

import com.example.IntranetYoucode.Entities.DTO.UserDTO;
import com.example.IntranetYoucode.Entities.User;
import com.example.IntranetYoucode.Entities.Instructor;
import com.example.IntranetYoucode.Entities.Learner;
import com.example.IntranetYoucode.Entities.Classroom; // Ensure Classroom entity is imported
import com.example.IntranetYoucode.Entities.Training; // Ensure Training entity is imported
import com.example.IntranetYoucode.Exceptions.EntityNotFoundException;
import com.example.IntranetYoucode.Exceptions.InvalidEntityException;
import com.example.IntranetYoucode.Repositories.UserRepository;
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
public class UserService {

    private final UserRepository userRepository;
    private final ClassroomService classroomService;
    private final TrainingService trainingService;
    private final ObjectProvider<User> userProvider;
    private final ObjectProvider<UserDTO> userDTOProvider;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    public UserDTO createUser(@Valid UserDTO userDTO) {
        // Validate necessary fields
        if (userDTO.getFirstName() == null || userDTO.getLastName() == null || userDTO.getEmail() == null) {
            throw new InvalidEntityException("User", "First name, Last name, and Email cannot be null");
        }

        // Check for existing email
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new InvalidEntityException("User", "Email already exists");
        }

        User userEntity = createUserEntity(userDTO);
        return convertToDTO(userRepository.save(userEntity));
    }

    public Optional<UserDTO> updateUser(Long id, @Valid UserDTO userDetails) {
        return userRepository.findById(id)
                .map(userEntity -> {
                    userEntity.setFirstName(userDetails.getFirstName());
                    userEntity.setLastName(userDetails.getLastName());
                    userEntity.setEmail(userDetails.getEmail());

                    if (userDetails.getClassroomId() != null) {
                        Classroom classroom = classroomService.getClassroomEntityById(userDetails.getClassroomId());
                        userEntity.setClassroom(classroom);
                    }

                    if (userDetails.getTrainingId() != null) {
                        Training training = trainingService.getTrainingEntityById(userDetails.getTrainingId());
                        userEntity.setTraining(training);
                    }

                    // Populate level or specialty based on subclass
                    if (userEntity instanceof Learner && userDetails.getLevel() != null) {
                        ((Learner) userEntity).setLevel(userDetails.getLevel());
                    } else if (userEntity instanceof Instructor && userDetails.getSpecialty() != null) {
                        ((Instructor) userEntity).setSpecialty(userDetails.getSpecialty());
                    }

                    return convertToDTO(userRepository.save(userEntity));
                });
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User", id);
        }
        userRepository.deleteById(id);
    }

    private User createUserEntity(UserDTO userDTO) {
        User user = userProvider.getObject();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());

        // Assign classroom and training if provided
        if (userDTO.getClassroomId() != null) {
            Classroom classroom = classroomService.getClassroomEntityById(userDTO.getClassroomId());
            user.setClassroom(classroom);
        }
        if (userDTO.getTrainingId() != null) {
            Training training = trainingService.getTrainingEntityById(userDTO.getTrainingId());
            user.setTraining(training);
        }


        // Populate level or specialty based on user type
        if ("LEARNER".equals(userDTO.getUserType())) {
            Learner learner = (Learner) user;  // Cast to Learner
            learner.setLevel(userDTO.getLevel());
        } else if ("INSTRUCTOR".equals(userDTO.getUserType())) {
            Instructor instructor = (Instructor) user;  // Cast to Instructor
            instructor.setSpecialty(userDTO.getSpecialty());
        }

        return user;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = userDTOProvider.getObject();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());

        // Populate level or specialty based on subclass
        if (user instanceof Learner) {
            userDTO.setLevel(((Learner) user).getLevel());
            userDTO.setUserType("LEARNER");
        } else if (user instanceof Instructor) {
            userDTO.setSpecialty(((Instructor) user).getSpecialty());
            userDTO.setUserType("INSTRUCTOR");
        }

        if (user.getClassroom() != null) {
            userDTO.setClassroomId(user.getClassroom().getId());
        }
        if (user.getTraining() != null) {
            userDTO.setTrainingId(user.getTraining().getId());
        }

        return userDTO;
    }
}
