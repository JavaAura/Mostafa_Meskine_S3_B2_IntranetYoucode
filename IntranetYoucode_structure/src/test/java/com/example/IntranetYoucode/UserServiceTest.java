package com.example.IntranetYoucode;

import com.example.IntranetYoucode.Entities.DTO.UserDTO;
import com.example.IntranetYoucode.Entities.User;
import com.example.IntranetYoucode.Exceptions.EntityNotFoundException;
import com.example.IntranetYoucode.Exceptions.InvalidEntityException;
import com.example.IntranetYoucode.Repositories.UserRepository;
import com.example.IntranetYoucode.Services.ClassroomService;
import com.example.IntranetYoucode.Services.TrainingService;
import com.example.IntranetYoucode.Services.UserService;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.ObjectProvider;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClassroomService classroomService;

    @Mock
    private TrainingService trainingService;

    @Mock
    private ObjectProvider<User> userProvider;

    @Mock
    private ObjectProvider<UserDTO> userDTOProvider;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, classroomService, trainingService, userProvider, userDTOProvider);
    }

    @Test
    void testGetAllUsers() {
        User user = mock(User.class);
        UserDTO userDTO = mock(UserDTO.class);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(userDTOProvider.getObject()).thenReturn(userDTO);

        var users = userService.getAllUsers();
        assertNotNull(users);
        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_UserFound() {
        Long userId = 1L;
        User user = mock(User.class);
        UserDTO userDTO = mock(UserDTO.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userDTOProvider.getObject()).thenReturn(userDTO);

        UserDTO result = userService.getUserById(userId);
        assertNotNull(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetUserById_UserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.getUserById(userId);
        });

        assertEquals("User not found with ID: " + userId, exception.getMessage());
    }

    @Test
    void testCreateUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john.doe@example.com");

        User userEntity = mock(User.class);
        when(userProvider.getObject()).thenReturn(userEntity);
        when(userRepository.save(any(User.class))).thenReturn(userEntity);
        when(userDTOProvider.getObject()).thenReturn(userDTO);

        UserDTO result = userService.createUser(userDTO);

        assertNotNull(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john.doe@example.com");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(mock(User.class)));

        InvalidEntityException exception = assertThrows(InvalidEntityException.class, () -> {
            userService.createUser(userDTO);
        });

        assertEquals("User with email john.doe@example.com already exists", exception.getMessage());
    }

    @Test
    void testUpdateUser_Success() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Jane");
        userDTO.setLastName("Smith");
        userDTO.setEmail("jane.smith@example.com");

        User userEntity = mock(User.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        Optional<UserDTO> result = userService.updateUser(userId, userDTO);

        assertTrue(result.isPresent());
        assertEquals("Jane", result.get().getFirstName());
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testUpdateUser_UserNotFound() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Jane");
        userDTO.setLastName("Smith");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<UserDTO> result = userService.updateUser(userId, userDTO);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testDeleteUser_Success() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUser_NotFound() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.deleteUser(userId);
        });

        assertEquals("User not found with ID: " + userId, exception.getMessage());
    }
}
