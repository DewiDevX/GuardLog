// GUARDLOG/app/src/main/java/guardlog/model/Incident.java
package guardlog.model;

import java.time.LocalDateTime;

public class Incident {
    private String id; // Could be auto-generated or sequential
    private String category;
    private LocalDateTime dateTime;
    private String location;
    private String description;
    private String reportedByOfficer; // Petugas yang melaporkan
    private String status; // e.g., "Pending", "Resolved", "Closed"

    public Incident(String category, LocalDateTime dateTime, String location, String description, String reportedByOfficer) {
        // Simple ID generation for example, use UUID or database ID in real app
        this.id = "INC-" + System.currentTimeMillis();
        this.category = category;
        this.dateTime = dateTime;
        this.location = location;
        this.description = description;
        this.reportedByOfficer = reportedByOfficer;
        this.status = "Pending"; // Default status
    }

    // Getters
    public String getId() { return id; }
    public String getCategory() { return category; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public String getReportedByOfficer() { return reportedByOfficer; }
    public String getStatus() { return status; }

    // Setters (ID and reportedByOfficer might not have public setters)
    public void setCategory(String category) { this.category = category; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public void setLocation(String location) { this.location = location; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Incident{" +
               "id='" + id + '\'' +
               ", category='" + category + '\'' +
               ", dateTime=" + dateTime +
               ", location='" + location + '\'' +
               ", description='" + description + '\'' +
               ", reportedByOfficer='" + reportedByOfficer + '\'' +
               ", status='" + status + '\'' +
               '}';
    }
}