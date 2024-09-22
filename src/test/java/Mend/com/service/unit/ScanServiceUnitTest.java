package Mend.com.service.unit;


import Mend.converter.ScanMapper;
import Mend.dto.ScanDTO;
import Mend.dto.ScanResponseDTO;
import Mend.repository.ScanRepository;
import Mend.domain.Scan;
import Mend.repository.ScanTypeRepository;
import Mend.repository.UserRepository;
import Mend.service.ScanService;
import Mend.types.ScanStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ScanServiceTest {

    @Mock
    private ScanRepository scanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ScanMapper scanMapper;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private ScanService scanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testProcessScanWithAsyncProcessing() {
        ScanDTO scanDTO = mock(ScanDTO.class);
        Scan scan = new Scan();
        scan.setScanId(1);
        when(scanMapper.toEntity(scanDTO)).thenReturn(scan);
        when(scanRepository.save(any(Scan.class))).thenReturn(scan);
        when(scanMapper.toScanResponseDTO(scan)).thenReturn(new ScanResponseDTO());

        // Mock async processing
     //   scanService.useAsyncProcessing = true;

        ScanResponseDTO response = scanService.processScan(scanDTO);

        verify(scanRepository, times(1)).save(scan);
     //   verify(rabbitTemplate, times(1)).convertAndSend("scanQueue", scan.getScanId());
        assertNotNull(response);
    }

    @Test
    void testProcessScanWithoutAsyncProcessing() {
        ScanDTO scanDTO = mock(ScanDTO.class);
        Scan scan = new Scan();
        scan.setScanId(1);
        when(scanMapper.toEntity(scanDTO)).thenReturn(scan);
        when(scanRepository.save(any(Scan.class))).thenReturn(scan);
        when(scanMapper.toScanResponseDTO(scan)).thenReturn(new ScanResponseDTO());

        // Mock sync processing
       // scanService.useAsyncProcessing = false;

        ScanResponseDTO response = scanService.processScan(scanDTO);

        verify(scanRepository, times(2)).save(scan); // Once when scan is created, once after sync processing
        assertEquals(ScanStatus.COMPLETED, scan.getStatus());
        assertNotNull(response);
    }

    @Test
    void testGetScansCountByStatus() {
        when(scanRepository.countByStatus(ScanStatus.PENDING)).thenReturn(10);

        long count = scanService.getScansCountByStatus(ScanStatus.PENDING);

        assertEquals(10, count);
        verify(scanRepository, times(1)).countByStatus(ScanStatus.PENDING);
    }

    @Test
    void testGetTotalIssuesByUserId() {
        int userId = 1;
        when(scanRepository.sumIssuesByUserId(userId)).thenReturn(5);

        int totalIssues = scanService.getTotalIssuesByUserId(userId);

        assertEquals(5, totalIssues);
        verify(scanRepository, times(1)).sumIssuesByUserId(userId);
    }

    @Test
    void testValidateUserIdExists() {
        int userId = 1;
        when(userRepository.existsById(userId)).thenReturn(true);

        boolean result = scanService.validateUserId(userId);

        assertTrue(result);
        verify(userRepository, times(1)).existsById(userId);
    }

    @Test
    void testValidateUserIdDoesNotExist() {
        int userId = 1;
        when(userRepository.existsById(userId)).thenReturn(false);

        boolean result = scanService.validateUserId(userId);

        assertFalse(result);
        verify(userRepository, times(1)).existsById(userId);
    }




}
