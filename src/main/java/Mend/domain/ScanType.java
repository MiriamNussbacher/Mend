package Mend.domain;
import jakarta.persistence.*;

@Entity
@Table(name = "ScanType")
public class ScanType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ScanTypeID")
    private int scanTypeId;

    @Column(name = "ScanTypeValue", length = 50, nullable = false)
    private String scanTypeValue;

    @Column(name = "ProcessingTimeSeconds")
    private int processingTimeSeconds;

    // Getters and Setters
    public int getScanTypeId() {
        return scanTypeId;
    }

    public void setScanTypeId(int scanTypeId) {
        this.scanTypeId = scanTypeId;
    }

    public String getScanTypeValue() {
        return scanTypeValue;
    }

    public void setScanTypeValue(String scanTypeValue) {
        this.scanTypeValue = scanTypeValue;
    }

    public int getProcessingTimeSeconds() {
        return processingTimeSeconds;
    }

    public void setProcessingTimeSeconds(int processingTimeSeconds) {
        this.processingTimeSeconds = processingTimeSeconds;
    }
}
