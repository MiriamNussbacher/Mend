package Mend.domain;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name ="\"Commit\"")
public class Commit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommitID")
    private int commitId;

    @ManyToOne
    @JoinColumn(name = "BranchID")
    private Branch branch;

    @Column(name = "CommitMessage", columnDefinition = "TEXT")
    private String commitMessage;

    @Column(name = "CommitDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commitDate;

    // Getters and Setters
    public int getCommitId() {
        return commitId;
    }

    public void setCommitId(int commitId) {
        this.commitId = commitId;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    public Date getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(Date commitDate) {
        this.commitDate = commitDate;
    }
}
