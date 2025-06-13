// GUARDLOG/app/src/main/java/guardlog/model/Incident.java
package guardlog.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Incident {
    private String id;
    private String category;
    private LocalDateTime dateTime;
    private String location;
    private String description;
    private String reportedByOfficer;
    private String status;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // Untuk format tanggal/waktu yang konsisten

    public Incident(String category, LocalDateTime dateTime, String location, String description, String reportedByOfficer) {
        this.id = "INC-" + System.currentTimeMillis(); // ID unik sederhana
        this.category = category;
        this.dateTime = dateTime;
        this.location = location;
        this.description = description;
        this.reportedByOfficer = reportedByOfficer;
        this.status = "Pending";
    }

    // Constructor untuk memuat dari CSV
    public Incident(String id, String category, LocalDateTime dateTime, String location, String description, String reportedByOfficer, String status) {
        this.id = id;
        this.category = category;
        this.dateTime = dateTime;
        this.location = location;
        this.description = description;
        this.reportedByOfficer = reportedByOfficer;
        this.status = status;
    }

    // Getters dan Setters
    public String getId() { return id; }
    public String getCategory() { return category; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public String getReportedByOfficer() { return reportedByOfficer; }
    public String getStatus() { return status; }

    public void setCategory(String category) { this.category = category; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public void setLocation(String location) { this.location = location; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(String status) { this.status = status; }

    /**
     * Mengonversi objek Incident menjadi string CSV.
     * Deskripsi akan di-"escape" untuk menangani koma atau karakter khusus lainnya.
     */
    public String toCsvString() {
        // Ganti koma dan backslash di deskripsi dengan representasi yang di-escape
        String escapedDescription = description.replace("\\", "\\\\").replace(",", "\\,");
        return String.join(",",
                id,
                category,
                dateTime.format(FORMATTER),
                location,
                escapedDescription,
                reportedByOfficer,
                status
        );
    }

    /**
     * Membuat objek Incident dari string CSV.
     */
    public static Incident fromCsvString(String csvLine) {
        // Membagi string berdasarkan koma yang TIDAK didahului oleh backslash
        String[] parts = csvLine.split("(?<!\\\\),");
        if (parts.length != 7) {
            System.err.println("Error parsing Incident CSV: Bagian tidak lengkap atau salah format: " + csvLine);
            return null; // Atau lempar exception yang lebih spesifik
        }
        String id = parts[0];
        String category = parts[1];
        LocalDateTime dateTime = LocalDateTime.parse(parts[2], FORMATTER);
        String location = parts[3];
        // Mengembalikan escape karakter
        String description = parts[4].replace("\\,", ",").replace("\\\\", "\\");
        String reportedByOfficer = parts[5];
        String status = parts[6];
        return new Incident(id, category, dateTime, location, description, reportedByOfficer, status);
    }

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