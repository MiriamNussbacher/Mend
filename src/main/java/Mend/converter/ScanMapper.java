package Mend.converter;
import Mend.domain.*;
import Mend.dto.ScanDTO;
import Mend.dto.ScanResponseDTO;
import Mend.repository.CommitRepository;
import Mend.repository.ScanTypeRepository;
import Mend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScanMapper {

    @Autowired
    private CommitRepository commitRepository;

    @Autowired
    private ScanTypeRepository scanTypeRepository;

    @Autowired
    private UserRepository userRepository;

    public Scan toEntity(ScanDTO scanDTO) {
        Scan scan = new Scan();

        Commit commit = commitRepository.findById(scanDTO.getCommitId())
                .orElseThrow(() -> new IllegalArgumentException("Commit not found"));
        scan.setCommit(commit);

        ScanType scanType = scanTypeRepository.findByScanTypeValue(scanDTO.getScanType())
                .orElseThrow(() -> new IllegalArgumentException("ScanType not found"));
        scan.setScanType(scanType);

        User user = userRepository.findById(scanDTO.getInitiatedByUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        scan.setInitiatedByUser(user);

        scan.setStatus(null); // Set status if applicable

        return scan;
    }

    public static ScanResponseDTO toScanResponseDTO(Scan scan) {
        ScanResponseDTO dto = new ScanResponseDTO();
        dto.setScanId(scan.getScanId());
        dto.setScanType(scan.getScanType().getScanTypeValue());
        dto.setProcessingTimeSeconds(scan.getScanType().getProcessingTimeSeconds());
        dto.setStatus(scan.getStatus().name());
        dto.setIssues(scan.getIssues());
        dto.setIsValid(scan.getIsValid());
        return dto;
    }
}
