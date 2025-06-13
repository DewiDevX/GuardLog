// GUARDLOG/app/src/main/java/guardlog/model/PersonalNote.java
package guardlog.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PersonalNote {
    private String title;
    private String content;
    private String category;
    private LocalDateTime timestamp;
    private String createdByOfficer;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public PersonalNote(String title, String content, String category, String createdByOfficer) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.timestamp = LocalDateTime.now();
        this.createdByOfficer = createdByOfficer;
    }

    // Constructor untuk memuat dari CSV
    public PersonalNote(String title, String content, String category, LocalDateTime timestamp, String createdByOfficer, boolean fromCsv) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.timestamp = timestamp;
        this.createdByOfficer = createdByOfficer;
    }

    // Getters dan Setters
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCategory() { return category; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getCreatedByOfficer() { return createdByOfficer; }

    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setCategory(String category) { this.category = category; }

    public String toCsvString() {
        // Escape koma dan backslash
        String escapedContent = content.replace("\\", "\\\\").replace(",", "\\,");
        String escapedTitle = title.replace("\\", "\\\\").replace(",", "\\,");
        return String.join(",",
                escapedTitle,
                escapedContent,
                category,
                timestamp.format(FORMATTER),
                createdByOfficer
        );
    }

    public static PersonalNote fromCsvString(String csvLine) {
        String[] parts = csvLine.split("(?<!\\\\),");
        if (parts.length != 5) {
            System.err.println("Error parsing PersonalNote CSV: Bagian tidak lengkap atau salah format: " + csvLine);
            return null;
        }
        String title = parts[0].replace("\\,", ",").replace("\\\\", "\\");
        String content = parts[1].replace("\\,", ",").replace("\\\\", "\\");
        String category = parts[2];
        LocalDateTime timestamp = LocalDateTime.parse(parts[3], FORMATTER);
        String createdByOfficer = parts[4];
        return new PersonalNote(title, content, category, timestamp, createdByOfficer, true);
    }

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