package Mend.api;
import Mend.dto.ScanDTO;
import Mend.dto.ScanResponseDTO;
import Mend.service.ScanService;
import Mend.types.ScanStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/scans")
public class ScanController {

    @Autowired
    private ScanService scanService;

    @PostMapping
    public HttpEntity<ScanResponseDTO> initiateScan(@Valid @RequestBody ScanDTO scan) {
       ScanResponseDTO response= scanService.processScan(scan);
       return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getScanCountByStatus(@RequestParam ScanStatus status) {
        long count = scanService.getScansCountByStatus(status);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/issuesCount")
    public ResponseEntity<?> getTotalIssues(@RequestParam String user) {
        int totalIssues;

        if (user.equalsIgnoreCase("all")) {
            totalIssues = scanService.getTotalIssuesForAllUsers();
            return ResponseEntity.ok(totalIssues);  // Return total issues for all users
        } else {
            try {
                int userId = Integer.parseInt(user);
                boolean isValidUserId = scanService.validateUserId(userId);

                if (!isValidUserId) {
                    return ResponseEntity.badRequest().body("Invalid user identifier: must be 'all' or a valid numeric userId.");
                }

                totalIssues = scanService.getTotalIssuesByUserId(userId);
                return ResponseEntity.ok(totalIssues);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("Invalid user identifier: must be 'all' or a valid numeric userId.");
            }
        }
    }

//    @GetMapping("/{commitId}/scans")
//    public ResponseEntity<List<CommitScanInfoDTO>> getScansByCommitId(@PathVariable int commitId) {
//        List<CommitScanInfoDTO> scanInfoList = scanService.getScanInfoByCommit(commitId);
//        return ResponseEntity.ok(scanInfoList);
//    }

}



