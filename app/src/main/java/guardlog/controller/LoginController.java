// GUARDLOG/app/src/main/java/guardlog/controller/LoginController.java
package guardlog.controller;

import guardlog.model.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.Objects; // Added for Objects.requireNonNull()

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField officerNameField; // Field for the officer's full name

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String officerName = officerNameField.getText();

        // Simple validation for a single-user application
        if ("admin".equals(username) && "secure".equals(password) && !officerName.trim().isEmpty()) {
            // Login successful
            UserSession.getInstance().setActiveOfficerName(officerName.trim()); // Store officer's name

            // Navigate to Dashboard
            try {
                // Path to DashboardView.fxml, relative to classpath root
                Parent dashboardParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/guardlog/view/fxml/DashboardView.fxml")));
                Scene dashboardScene = new Scene(dashboardParent);

                // Get the current window (Stage) from the clicked button's scene
                Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
                window.setScene(dashboardScene);
                window.setTitle("GuardLog - Security Dashboard");
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load dashboard. Please try again or contact support.");
            }
        } else {
            // Login failed
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Username, password, or officer's name is invalid!");
        }
    }

    /**
     * Helper method to display an alert dialog.
     * @param type The type of alert (ERROR, INFORMATION, WARNING, etc.).
     * @param title The title of the alert dialog.
     * @param message The content text of the alert dialog.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text
        alert.setContentText(message);
        alert.showAndWait();
    }
}