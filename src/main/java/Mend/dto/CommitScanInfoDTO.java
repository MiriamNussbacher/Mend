package Mend.dto;

public class CommitScanInfoDTO {

    private int userId;
    private String userName;
    private int commitId;
    private String scanType;
    private String status;
    private int issues;


    public CommitScanInfoDTO(int userId, String userName, int commitId, String scanType, String status, int issues) {
        this.userId = userId;
        this.userName = userName;
        this.commitId = commitId;
        this.scanType = scanType;
        this.status = status;
        this.issues = issues;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCommitId() {
        return commitId;
    }

    public void setCommitId(int commitId) {
        this.commitId = commitId;
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIssues() {
        return issues;
    }

    public void setIssues(int issues) {
        this.issues = issues;
    }
}
