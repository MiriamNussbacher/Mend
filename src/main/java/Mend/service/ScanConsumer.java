package Mend.service;

import Mend.api.SSEController;
import Mend.config.RabbitMQConfig;
import Mend.converter.ScanMapper;
import Mend.domain.Scan;
import Mend.dto.ScanDTO;
import Mend.dto.ScanResponseDTO;
import Mend.types.ScanStatus;
import Mend.repository.ScanRepository;
import Mend.types.ScanStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ScanConsumer {

    @Autowired
    private ScanService scanService;
    @Autowired
    private ScanRepository scanRepository;

    @Autowired
    private ScanMapper scanMapper;

    @Autowired
    private SSEController sseController;  // Inject the SSE controller

    private static final Logger logger = LoggerFactory.getLogger(ScanConsumer.class);


    @RabbitListener(queues = RabbitMQConfig.SCAN_QUEUE)
    public void receiveScan(Integer scanId) {
        Scan scan = scanRepository.findById(scanId).orElseThrow(() -> new RuntimeException("Scan not found"));
        try {
            scan.setStatus(ScanStatus.RUNNING);

            ScanResponseDTO scanDTO = scanMapper.toScanResponseDTO(scan);
            sseController.sendScanUpdate(scanId, scanDTO);
            // Use the processing time from the database
            int issuesCount = processScanAndDecideIssues(scan);
            scan.setIssues(issuesCount);
            scan.setValid(issuesCount==0);

            scan.setStatus(ScanStatus.COMPLETED);
            // Send SSE update: Scan is completed
            scanDTO = scanMapper.toScanResponseDTO(scan);
            sseController.sendScanUpdate(scanId, scanDTO);

        } catch (Exception e) {
            handleScanFailure(scan, e);
        } finally {
            scanRepository.save(scan);
        }
    }

    private int processScanAndDecideIssues(Scan scan) throws InterruptedException {
        int processingTimeMs = scan.getScanType().getProcessingTimeSeconds() * 1000;
        Thread.sleep(processingTimeMs); // Simulate the scan processing
        return new Random().nextInt(10); // Randomly generates an issue count
    }

    private void handleScanFailure(Scan scan, Exception e) {
        logger.error("Scan processing failed for Scan ID {}: {}", scan.getScanId(), e.getMessage());
        scan.setStatus(ScanStatus.FAILED);

    }
}
