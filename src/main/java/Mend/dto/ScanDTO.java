package Mend.dto;

import Mend.validator.ValidCommitId;
import Mend.validator.ValidScanType;
import jakarta.validation.constraints.NotNull;

public class ScanDTO {

    @NotNull(message = "Commit ID is required")
    @ValidCommitId
    private Integer commitId;

    @NotNull(message = "Scan Type is required")
    @ValidScanType
    private String scanType;

    @NotNull(message = "Initiated By User ID is required")
    private Integer initiatedByUserId;

    // Getters and Setters
    public Integer getCommitId() {
        return commitId;
    }

    public void setCommitId(Integer commitId) {
        this.commitId = commitId;
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public Integer getInitiatedByUserId() {
        return initiatedByUserId;
    }

    public void setInitiatedByUserId(Integer initiatedByUserId) {
        this.initiatedByUserId = initiatedByUserId;
    }
}
