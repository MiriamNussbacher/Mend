package Mend.domain;
import Mend.types.ScanStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Scan")
public class Scan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ScanID")
    private int scanId;

    @ManyToOne
    @JoinColumn(name = "CommitID")
    private Commit commit;

    @ManyToOne
    @JoinColumn(name = "ScanTypeID")
    private ScanType scanType;

    @Enumerated(EnumType.STRING) // Store the enum as a string in the database
    @Column(name = "status")
    private ScanStatus status;

    @ManyToOne
    @JoinColumn(name = "InitiatedByUserID")
    private User initiatedByUser;

    @Column(name = "Issues")
    private int issues;

    @Column(name = "CreatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @Column(name = "IsValid")
    private boolean isValid;  // New field for Valid


    // Getters and Setters
    public int getScanId() {
        return scanId;
    }

    public void setScanId(int scanId) {
        this.scanId = scanId;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public ScanType getScanType() {
        return scanType;
    }

    public void setScanType(ScanType scanType) {
        this.scanType = scanType;
    }

    public ScanStatus getStatus() {
        return status;
    }

    public void setStatus(ScanStatus status) {
        this.status = status;
    }

    public User getInitiatedByUser() {
        return initiatedByUser;
    }

    public void setInitiatedByUser(User initiatedByUser) {
        this.initiatedByUser = initiatedByUser;
    }

    public int getIssues() {
        return issues;
    }

    public void setIssues(int issues) {
        this.issues = issues;
    }

    public LocalDateTime getCreatedAt() {

        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAtAt() {

        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean getIsValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }
}
