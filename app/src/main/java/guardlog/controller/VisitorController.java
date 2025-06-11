// GUARDLOG/app/src/main/java/guardlog/controller/VisitorController.java
package guardlog.controller;

import guardlog.model.UserSession;
import guardlog.model.Visitor;
import guardlog.model.VisitorManager; // <--- Import ini
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class VisitorController implements Initializable {

    @FXML private TextField visitorNameField;
    @FXML private TextField visitorPurposeField;
    @FXML private TableView<Visitor> visitorTable;
    @FXML private TableColumn<Visitor, String> nameColumn;
    @FXML private TableColumn<Visitor, String> purposeColumn;
    @FXML private TableColumn<Visitor, String> officerColumn;
    @FXML private TableColumn<Visitor, String> timeInColumn;
    @FXML private TableColumn<Visitor, String> timeOutColumn;

    // Hapus ObservableList<Visitor> visitors = FXCollections.observableArrayList();
    // Gunakan VisitorManager

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        purposeColumn.setCellValueFactory(new PropertyValueFactory<>("purpose"));
        officerColumn.setCellValueFactory(new PropertyValueFactory<>("officerName"));
        timeInColumn.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
        timeOutColumn.setCellValueFactory(new PropertyValueFactory<>("timeOut"));

        timeInColumn.setCellFactory(column -> new TableCell<Visitor, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    LocalDateTime time = ((Visitor) getTableRow().getItem()).getTimeIn();
                    setText(time != null ? time.format(formatter) : "");
                }
            }
        });
        timeOutColumn.setCellFactory(column -> new TableCell<Visitor, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    LocalDateTime time = ((Visitor) getTableRow().getItem()).getTimeOut();
                    setText(time != null ? time.format(formatter) : "Belum Checkout");
                }
            }
        });

        // Set TableView items dari VisitorManager singleton
        visitorTable.setItems(VisitorManager.getInstance().getVisitors());
        System.out.println("VisitorController: TableView diatur. Jumlah item di TableView: " + visitorTable.getItems().size()); // Debug
    }

    @FXML
    private void handleRegisterVisitor() {
        String name = visitorNameField.getText().trim();
        String purpose = visitorPurposeField.getText().trim();
        String officer = UserSession.getInstance().getActiveOfficerName();

        if (name.isEmpty() || purpose.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Tidak Lengkap", "Nama pengunjung dan tujuan kunjungan tidak boleh kosong.");
            return;
        }
        if (officer == null || officer.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Sesi Petugas Hilang", "Nama petugas tidak ditemukan. Harap login kembali.");
            return;
        }

        Visitor newVisitor = new Visitor(name, purpose, officer, LocalDateTime.now());
        VisitorManager.getInstance().addVisitor(newVisitor); // Tambahkan melalui Manager

        visitorNameField.clear();
        visitorPurposeField.clear();
        showAlert(Alert.AlertType.INFORMATION, "Registrasi Berhasil", "Pengunjung " + name + " berhasil dicatat.");
    }

    @FXML
    private void handleCheckoutVisitor() {
        Visitor selectedVisitor = visitorTable.getSelectionModel().getSelectedItem();

        if (selectedVisitor == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Pengunjung", "Silakan pilih pengunjung yang akan di-checkout.");
            return;
        }

        if (selectedVisitor.getTimeOut() != null) {
            showAlert(Alert.AlertType.INFORMATION, "Sudah Checkout", "Pengunjung ini sudah di-checkout.");
            return;
        }

        selectedVisitor.setTimeOut(LocalDateTime.now());
        visitorTable.refresh();
        showAlert(Alert.AlertType.INFORMATION, "Checkout Berhasil", "Pengunjung " + selectedVisitor.getName() + " berhasil di-checkout.");
        // TODO: Update in database
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