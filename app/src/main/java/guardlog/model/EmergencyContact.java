// GUARDLOG/app/src/main/java/guardlog/model/EmergencyContact.java
package guardlog.model;

public class EmergencyContact {
    private String name;
    private String role;
    private String phoneNumber;

    public EmergencyContact(String name, String role, String phoneNumber) {
        this.name = name;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }

    // Constructor untuk memuat dari CSV
    public EmergencyContact(String name, String role, String phoneNumber, boolean fromCsv) {
        this(name, role, phoneNumber); // Panggil constructor utama
    }

    // Getters dan Setters
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getPhoneNumber() { return phoneNumber; }

    public void setName(String name) { this.name = name; }
    public void setRole(String role) { this.role = role; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String toCsvString() {
        String escapedName = name.replace("\\", "\\\\").replace(",", "\\,");
        String escapedRole = role.replace("\\", "\\\\").replace(",", "\\,");
        String escapedPhoneNumber = phoneNumber.replace("\\", "\\\\").replace(",", "\\,");
        return String.join(",",
                escapedName,
                escapedRole,
                escapedPhoneNumber
        );
    }

    public static EmergencyContact fromCsvString(String csvLine) {
        String[] parts = csvLine.split("(?<!\\\\),");
        if (parts.length != 3) {
            System.err.println("Error parsing EmergencyContact CSV: Bagian tidak lengkap atau salah format: " + csvLine);
            return null;
        }
        String name = parts[0].replace("\\,", ",").replace("\\\\", "\\");
        String role = parts[1].replace("\\,", ",").replace("\\\\", "\\");
        String phoneNumber = parts[2].replace("\\,", ",").replace("\\\\", "\\");
        return new EmergencyContact(name, role, phoneNumber, true);
    }

    @Override
    public String toString() {
        return "EmergencyContact{" +
               "name='" + name + '\'' +
               ", role='" + role + '\'' +
               ", phoneNumber='" + phoneNumber + '\'' +
               '}';
    }
}