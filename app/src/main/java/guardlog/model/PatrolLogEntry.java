// GUARDLOG/app/src/main/java/guardlog/model/PatrolLogEntry.java
package guardlog.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PatrolLogEntry {
    private LocalDateTime timestamp;
    private String area;
    private String notes;
    private String recordedByOfficer;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public PatrolLogEntry(LocalDateTime timestamp, String area, String notes, String recordedByOfficer) {
        this.timestamp = timestamp;
        this.area = area;
        this.notes = notes;
        this.recordedByOfficer = recordedByOfficer;
    }

    // Constructor untuk memuat dari CSV
    public PatrolLogEntry(LocalDateTime timestamp, String area, String notes, String recordedByOfficer, boolean fromCsv) {
        this(timestamp, area, notes, recordedByOfficer);
    }

    // Getters dan Setters
    // --- PERUBAHAN DI SINI ---
    public LocalDateTime getTimestamp() { return timestamp; } // Ganti String menjadi LocalDateTime
    // --- AKHIR PERUBAHAN ---
    public String getArea() { return area; }
    public String getNotes() { return notes; }
    public String getRecordedByOfficer() { return recordedByOfficer; }

    public void setArea(String area) { this.area = area; }
    public void setNotes(String notes) { this.notes = notes; }

    public String toCsvString() {
        String escapedNotes = notes.replace("\\", "\\\\").replace(",", "\\,");
        return String.join(",",
                timestamp.format(FORMATTER),
                area,
                escapedNotes,
                recordedByOfficer
        );
    }

    public static PatrolLogEntry fromCsvString(String csvLine) {
        String[] parts = csvLine.split("(?<!\\\\),");
        if (parts.length != 4) {
            System.err.println("Error parsing PatrolLogEntry CSV: Bagian tidak lengkap atau salah format: " + csvLine);
            return null;
        }
        LocalDateTime timestamp = LocalDateTime.parse(parts[0], FORMATTER);
        String area = parts[1];
        String notes = parts[2].replace("\\,", ",").replace("\\\\", "\\");
        String recordedByOfficer = parts[3];
        return new PatrolLogEntry(timestamp, area, notes, recordedByOfficer, true);
    }

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