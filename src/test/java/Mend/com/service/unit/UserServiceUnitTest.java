package Mend.com.service.unit;


import Mend.converter.UserMapper;
import Mend.dto.UserActivityDTO;
import Mend.repository.UserRepository;
import Mend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    public UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize the mocks
    }

    @Test
    public void testGetTopActiveUsers_ReturnsCorrectDTOs() {
        // Arrange
        int limit = 10;
        Pageable pageable = PageRequest.of(0, limit);

        // Mocking repository return value
        List<Object[]> mockResult = new ArrayList<>();
        mockResult.add(new Object[]{1, "John Doe", 5L});
        mockResult.add(new Object[]{2, "Jane Smith", 3L});

        // Mock the repository method call
        Mockito.when(userRepository.findTopActiveUsers(pageable)).thenReturn(mockResult);
        //when(userRepository.)thenReturn(mockResult);

        // Mock the mapper behavior
        UserActivityDTO user1DTO = new UserActivityDTO(1, "John Doe", 5L);
        UserActivityDTO user2DTO = new UserActivityDTO(2, "Jane Smith", 3L);
        when(userMapper.toUserActivityDTO(1, "John Doe", 5L)).thenReturn(user1DTO);
        when(userMapper.toUserActivityDTO(2, "Jane Smith", 3L)).thenReturn(user2DTO);

        // Act
        List<UserActivityDTO> result = userService.getTopActiveUsers(limit);

        // Assert
        assertEquals(2, result.size());
        assertEquals(user1DTO, result.get(0));
        assertEquals(user2DTO, result.get(1));

        // Verify repository and mapper were called correctly
        verify(userRepository, times(1)).findTopActiveUsers(pageable);
        verify(userMapper, times(1)).toUserActivityDTO(1, "John Doe", 5L);
        verify(userMapper, times(1)).toUserActivityDTO(2, "Jane Smith", 3L);
    }

    @Test
    public void testGetTopActiveUsers_EmptyResult() {
        // Arrange
        int limit = 10;
        Pageable pageable = PageRequest.of(0, limit);

        // Mock repository to return an empty result
        when(userRepository.findTopActiveUsers(pageable)).thenReturn(new ArrayList<>());

        // Act
        List<UserActivityDTO> result = userService.getTopActiveUsers(limit);

        // Assert
        assertEquals(0, result.size());  // Expecting no users

        // Verify repository method was called and mapper was not called
        verify(userRepository, times(1)).findTopActiveUsers(pageable);
        verifyNoInteractions(userMapper);  // Mapper shouldn't be called when result is empty
    }
}
