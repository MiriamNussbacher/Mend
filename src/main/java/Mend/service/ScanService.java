package Mend.service;

import Mend.com.MendApplication;
import Mend.converter.ScanMapper;
import Mend.dto.CommitScanInfoDTO;
import Mend.dto.ScanDTO;
import Mend.dto.ScanResponseDTO;
import Mend.repository.ScanRepository;
import Mend.domain.Scan;
import Mend.repository.UserRepository;
import Mend.types.ScanStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ScanService {

    @Autowired
    private ScanRepository scanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScanMapper scanMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${app.use-async-processing:false}")
    private boolean useAsyncProcessing;

    private static final Logger logger = LoggerFactory.getLogger(ScanService.class);

    @Async
    public ScanResponseDTO processScan(ScanDTO scanDTO) {
        Scan scan = createNewScan(scanDTO);

        if (useAsyncProcessing) {
            rabbitTemplate.convertAndSend("scanQueue", scan.getScanId());
        } else {
            processScanSynchronously(scan);
        }

        return scanMapper.toScanResponseDTO(scan);
    }

//    public List<CommitScanInfoDTO> getScanInfoByCommit(int commitId) {
//
//        List<Scan> scans = scanRepository.findScansByCommitId(commitId);
//        return mapToCommitScanInfoDTO(scans);
//    }

//    private List<CommitScanInfoDTO> mapToCommitScanInfoDTO(List<Scan> scans) {
//        List<CommitScanInfoDTO> commitScanInfoDTOList = new ArrayList<>();
//
//        for (Scan scan : scans) {
//            int userId = scan.getInitiatedByUser().getUserId();
//            String userName = scan.getInitiatedByUser().getFirstName() + " " + scan.getInitiatedByUser().getLastName();
//            int commitId = scan.getCommit().getCommitId();
//            String scanType = scan.getScanType().getScanTypeValue();
//            String status = scan.getStatus().name();
//            int issues = scan.getIssues();
//
//            CommitScanInfoDTO dto = new CommitScanInfoDTO(userId, userName, commitId, scanType, status, issues);
//            commitScanInfoDTOList.add(dto);
//        }
//
//        return commitScanInfoDTOList;
//    }

    private Scan createNewScan(ScanDTO scanDTO) {
        Scan scan = scanMapper.toEntity(scanDTO);
        scan.setCreatedAt(LocalDateTime.now());
        scan.setUpdatedAt(LocalDateTime.now());
        scan.setStatus(ScanStatus.PENDING);
        scanRepository.save(scan);
        return scan;
    }

    private void processScanSynchronously(Scan scan) {
        try {
            int issuesCount = executeScanProcessing(scan);
            scan.setIssues(issuesCount);
            scan.setValid(issuesCount == 0);
            scan.setStatus(ScanStatus.COMPLETED);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Scan processing was interrupted for Scan ID {}: {}", scan.getScanId(), e.getMessage());
            scan.setStatus(ScanStatus.FAILED);
        } catch (Exception e) {
            logger.error("Scan processing failed for Scan ID {}: {}", scan.getScanId(), e.getMessage());
            scan.setStatus(ScanStatus.FAILED);
        } finally {
            scan.setUpdatedAt(LocalDateTime.now());
            scanRepository.save(scan);
        }
    }

    private int executeScanProcessing(Scan scan) throws InterruptedException {
        int processingTimeMs = scan.getScanType().getProcessingTimeSeconds() * 1000;
        Thread.sleep(processingTimeMs); // Simulate the scan processing
        return new Random().nextInt(10); // Randomly generates an issue count
    }


    public long getScansCountByStatus(ScanStatus status) {
        return scanRepository.countByStatus(status);
    }

    // Get total issues for a specific user
    public int getTotalIssuesByUserId(int userId) {
        Integer issues=  scanRepository.sumIssuesByUserId(userId);
        return issues != null ? issues : 0;
    }

    // Get total issues for all users
    public int getTotalIssuesForAllUsers() {
        Integer totalIssues = scanRepository.sumIssuesForAllUsers();
        return totalIssues != null ? totalIssues : 0;
    }

    // Validate that the user exists by userId
    public boolean validateUserId(int userId) {
        if (!userRepository.existsById(userId)) {
            return false;
        }
        return true;
    }
}

