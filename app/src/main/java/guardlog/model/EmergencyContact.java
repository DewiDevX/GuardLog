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

    public String getName() { return name; }
    public String getRole() { return role; }
    public String getPhoneNumber() { return phoneNumber; }

    public void setName(String name) { this.name = name; }
    public void setRole(String role) { this.role = role; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public String toString() {
        return "EmergencyContact{" +
               "name='" + name + '\'' +
               ", role='" + role + '\'' +
               ", phoneNumber='" + phoneNumber + '\'' +
               '}';
    }
}