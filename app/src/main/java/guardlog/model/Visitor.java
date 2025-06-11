// GUARDLOG/app/src/main/java/guardlog/model/Visitor.java
package guardlog.model;

import java.time.LocalDateTime;

public class Visitor {
    private String name;
    private String purpose;
    private String officerName; // Petugas yang mencatat
    private LocalDateTime timeIn;
    private LocalDateTime timeOut; // Null if not checked out yet

    public Visitor(String name, String purpose, String officerName, LocalDateTime timeIn) {
        this.name = name;
        this.purpose = purpose;
        this.officerName = officerName;
        this.timeIn = timeIn;
        this.timeOut = null; // Initially no checkout time
    }

    // Getters
    public String getName() { return name; }
    public String getPurpose() { return purpose; }
    public String getOfficerName() { return officerName; }
    public LocalDateTime getTimeIn() { return timeIn; }
    public LocalDateTime getTimeOut() { return timeOut; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public void setOfficerName(String officerName) { this.officerName = officerName; }
    public void setTimeIn(LocalDateTime timeIn) { this.timeIn = timeIn; }
    public void setTimeOut(LocalDateTime timeOut) { this.timeOut = timeOut; }

    @Override
    public String toString() {
        return "Visitor{" +
               "name='" + name + '\'' +
               ", purpose='" + purpose + '\'' +
               ", officerName='" + officerName + '\'' +
               ", timeIn=" + timeIn +
               ", timeOut=" + timeOut +
               '}';
    }
}