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
    private Label officerNameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String officerName = UserSession.getInstance().getActiveOfficerName();
        officerNameLabel.setText("Petugas: " + (officerName != null ? officerName : "Tidak Diketahui"));
    }

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
        UserSession.getInstance().clearSession();
        loadScene("/guardlog/view/fxml/LoginView.fxml", "GuardLog - Login", event);
    }

    private void loadScene(String fxmlPath, String title, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            Scene newScene = new Scene(root);

            // --- Menerapkan CSS ke Scene baru ---
            URL cssUrl = getClass().getResource("/guardlog/view/css/style.css");
            if (cssUrl != null) {
                newScene.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("ERROR: File CSS tidak ditemukan untuk FXML: " + fxmlPath + "! Jalur: /guardlog/view/css/style.css");
            }
            // ------------------------------------

            Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            window.setScene(newScene);
            window.setTitle(title);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Gagal memuat FXML: " + fxmlPath);
        }
    }
}