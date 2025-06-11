// GUARDLOG/app/src/main/java/guardlog/model/PatrolLogEntry.java
package guardlog.model;

import java.time.LocalDateTime;

public class PatrolLogEntry {
    private LocalDateTime timestamp;
    private String area; // e.g., "Main Gate", "Perimeter A", "CCTV Room"
    private String notes;
    private String recordedByOfficer; // Petugas yang mencatat patroli

    public PatrolLogEntry(LocalDateTime timestamp, String area, String notes, String recordedByOfficer) {
        this.timestamp = timestamp;
        this.area = area;
        this.notes = notes;
        this.recordedByOfficer = recordedByOfficer;
    }

    // Getters
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getArea() { return area; }
    public String getNotes() { return notes; }
    public String getRecordedByOfficer() { return recordedByOfficer; }

    // Setters (if editable, timestamp and recordedByOfficer might not be)
    public void setArea(String area) { this.area = area; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return "PatrolLogEntry{" +
               "timestamp=" + timestamp +
               ", area='" + area + '\'' +
               ", notes='" + notes + '\'' +
               ", recordedByOfficer='" + recordedByOfficer + '\'' +
               '}';
    }
}