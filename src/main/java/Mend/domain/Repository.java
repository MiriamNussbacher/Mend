package Mend.domain;
import jakarta.persistence.*;

@Entity
@Table(name = "Repository")
public class Repository {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RepositoryID")
    private int repositoryId;

    @ManyToOne
    @JoinColumn(name = "OrganizationID")
    private Organization organization;

    @Column(name = "RepositoryName", length = 100)
    private String repositoryName;

    // Getters and Setters
    public int getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(int repositoryId) {
        this.repositoryId = repositoryId;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }
}
