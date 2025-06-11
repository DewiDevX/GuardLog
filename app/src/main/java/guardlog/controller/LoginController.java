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
import java.net.URL;
import java.util.Objects;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField officerNameField;

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String officerName = officerNameField.getText();

        if ("admin".equals(username) && "secure".equals(password) && !officerName.trim().isEmpty()) {
            UserSession.getInstance().setActiveOfficerName(officerName.trim());

            try {
                Parent dashboardParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/guardlog/view/fxml/DashboardView.fxml")));
                Scene dashboardScene = new Scene(dashboardParent);

                // --- Menerapkan CSS ke Scene Dashboard ---
                URL cssUrl = getClass().getResource("/guardlog/view/css/style.css");
                if (cssUrl != null) {
                    dashboardScene.getStylesheets().add(cssUrl.toExternalForm());
                } else {
                    System.err.println("ERROR: File CSS tidak ditemukan untuk DashboardView! Jalur: /guardlog/view/css/style.css");
                }
                // ------------------------------------------

                Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
                window.setScene(dashboardScene);
                window.setTitle("GuardLog - Security Dashboard");
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Navigation Error", "Gagal memuat dashboard. Silakan coba lagi atau hubungi dukungan.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Gagal", "Username, password, atau nama petugas tidak valid!");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}