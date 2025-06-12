// GUARDLOG/app/src/main/java/guardlog/controller/EmergencyContactController.java
package guardlog.controller;

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

// Simple model for emergency contacts (can be a nested class or separate file)
class EmergencyContact {
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
}

public class EmergencyContactController implements Initializable {

    @FXML private TableView<EmergencyContact> contactTable;
    @FXML private TableColumn<EmergencyContact, String> nameColumn;
    @FXML private TableColumn<EmergencyContact, String> roleColumn;
    @FXML private TableColumn<EmergencyContact, String> phoneColumn;

    private ObservableList<EmergencyContact> contacts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        contactTable.setItems(contacts);

        // Populate with dummy data
        contacts.add(new EmergencyContact("Kepala Keamanan", "Supervisor", "0812-3456-7890"));
        contacts.add(new EmergencyContact("Pemadam Kebakaran", "Darurat", "113"));
        contacts.add(new EmergencyContact("Polisi", "Darurat", "110"));
        contacts.add(new EmergencyContact("Ambulans", "Darurat", "118"));
        contacts.add(new EmergencyContact("Teknisi Gedung", "Teknisi", "0876-5432-1098"));
    }

    @FXML
    private void handleCallContact() {
        EmergencyContact selectedContact = contactTable.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Kontak", "Silakan pilih kontak yang ingin dihubungi.");
            return;
        }

        // In a real application, this would trigger a call or log the attempt.
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
            Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
            window.setTitle(title);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML: " + fxmlPath);
        }
    }
}