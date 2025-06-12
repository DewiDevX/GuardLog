// GUARDLOG/app/src/main/java/guardlog/model/UserSession.java
package guardlog.model;

/**
 * Singleton class to hold the active officer's name and other session-related data.
 * This ensures that the officer's name is accessible globally across the application
 * after a successful login.
 */
public class UserSession {
    private static UserSession instance;
    private String activeOfficerName;

    // Private constructor to prevent direct instantiation
    private UserSession() {
        // Initialize any default values or perform setup if necessary
    }

    /**
     * Gets the singleton instance of UserSession.
     * @return The single instance of UserSession.
     */
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     * Gets the name of the currently active officer.
     * @return The active officer's name.
     */
    public String getActiveOfficerName() {
        return activeOfficerName;
    }

    /**
     * Sets the name of the active officer. This should be called upon successful login.
     * @param activeOfficerName The full name of the officer currently logged in.
     */
    public void setActiveOfficerName(String activeOfficerName) {
        this.activeOfficerName = activeOfficerName;
    }

    /**
     * Clears the current session data, typically called upon logout.
     */
    public void clearSession() {
        this.activeOfficerName = null;
        // If you want to completely reset the singleton instance on logout, you can uncomment this:
        // instance = null;
    }
}