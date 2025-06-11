// GUARDLOG/app/src/main/java/guardlog/controller/EmergencyContactController.java
package guardlog.controller;

import guardlog.model.EmergencyContact; // <--- Import ini
import guardlog.model.EmergencyContactManager; // <--- Import ini
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class EmergencyContactController implements Initializable {

    @FXML private TableView<EmergencyContact> contactTable;
    @FXML private TableColumn<EmergencyContact, String> nameColumn;
    @FXML private TableColumn<EmergencyContact, String> roleColumn;
    @FXML private TableColumn<EmergencyContact, String> phoneColumn;

    // Hapus ObservableList<EmergencyContact> contacts = FXCollections.observableArrayList();
    // Gunakan EmergencyContactManager

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        // Set TableView items dari EmergencyContactManager singleton
        contactTable.setItems(EmergencyContactManager.getInstance().getContacts());
        System.out.println("EmergencyContactController: TableView diatur. Jumlah item di TableView: " + contactTable.getItems().size()); // Debug
    }

    @FXML
    private void handleCallContact() {
        EmergencyContact selectedContact = contactTable.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Kontak", "Silakan pilih kontak yang ingin dihubungi.");
            return;
        }

        showAlert(Alert.AlertType.INFORMATION, "Menghubungi...", "Mencoba menghubungi: " + selectedContact.getName() + " (" + selectedContact.getPhoneNumber() + ")");
        // TODO: Log this call attempt with officer's name
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        loadScene("/guardlog/view/fxml/DashboardView.fxml", "GuardLog - Security Dashboard", event);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadScene(String fxmlPath, String title, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            Scene newScene = new Scene(root);

            URL cssUrl = getClass().getResource("/guardlog/view/css/style.css");
            if (cssUrl != null) {
                newScene.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("ERROR: File CSS tidak ditemukan untuk FXML: " + fxmlPath + "! Jalur: /guardlog/view/css/style.css");
            }

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