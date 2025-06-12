// GUARDLOG/app/src/main/java/guardlog/controller/DashboardController.java
package guardlog.controller;

import guardlog.model.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label officerNameLabel; // Label to display the active officer's name

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Display the active officer's name when the dashboard is loaded
        String officerName = UserSession.getInstance().getActiveOfficerName();
        officerNameLabel.setText("Petugas: " + (officerName != null ? officerName : "Tidak Diketahui"));
    }

    // --- Navigation Methods ---

    @FXML
    private void handleVisitorManagement(ActionEvent event) {
        loadScene("/guardlog/view/fxml/VisitorView.fxml", "GuardLog - Manajemen Pengunjung", event);
    }

    @FXML
    private void handleIncidentReporting(ActionEvent event) {
        loadScene("/guardlog/view/fxml/IncidentView.fxml", "GuardLog - Pelaporan Insiden", event);
    }

    @FXML
    private void handlePatrolLog(ActionEvent event) {
        loadScene("/guardlog/view/fxml/PatrolLogView.fxml", "GuardLog - Log Patroli", event);
    }

    @FXML
    private void handleEmergencyContacts(ActionEvent event) {
        loadScene("/guardlog/view/fxml/EmergencyContactView.fxml", "GuardLog - Kontak Darurat", event);
    }

    @FXML
    private void handleDailyReports(ActionEvent event) {
        loadScene("/guardlog/view/fxml/DailyReportView.fxml", "GuardLog - Laporan Harian", event);
    }

    @FXML
    private void handlePersonalNotes(ActionEvent event) {
        loadScene("/guardlog/view/fxml/PersonalNotesView.fxml", "GuardLog - Catatan Pribadi", event);
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        UserSession.getInstance().clearSession(); // Clear session on logout
        loadScene("/guardlog/view/fxml/LoginView.fxml", "GuardLog - Login", event);
    }

    /**
     * Helper method to load a new scene.
     * @param fxmlPath The path to the FXML file (e.g., "/guardlog/view/fxml/SomeView.fxml").
     * @param title The title for the new window.
     * @param event The ActionEvent that triggered this navigation (used to get the current Stage).
     */
    private void loadScene(String fxmlPath, String title, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
            window.setTitle(title);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML: " + fxmlPath);
            // Optionally show an alert to the user
        }
    }
}