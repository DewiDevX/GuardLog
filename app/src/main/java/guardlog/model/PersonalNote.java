// GUARDLOG/app/src/main/java/guardlog/model/PersonalNote.java
package guardlog.model;

import java.time.LocalDateTime;

public class PersonalNote {
    private String title;
    private String content;
    private String category; // e.g., "To-Do", "Reminder", "Handover"
    private LocalDateTime timestamp;
    private String createdByOfficer; // Petugas yang membuat catatan

    public PersonalNote(String title, String content, String category, String createdByOfficer) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.timestamp = LocalDateTime.now(); // Automatically set timestamp
        this.createdByOfficer = createdByOfficer;
    }

    // Getters
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCategory() { return category; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getCreatedByOfficer() { return createdByOfficer; }

    // Setters (timestamp and createdByOfficer might not have public setters)
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setCategory(String category) { this.category = category; }

    @Override
    public String toString() {
        return "PersonalNote{" +
               "title='" + title + '\'' +
               ", content='" + content + '\'' +
               ", category='" + category + '\'' +
               ", timestamp=" + timestamp + '\'' +
               ", createdByOfficer='" + createdByOfficer + '\'' +
               '}';
    }
}