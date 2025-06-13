// GUARDLOG/app/src/main/java/guardlog/controller/IncidentController.java
package guardlog.controller;

import guardlog.model.Incident;
import guardlog.model.IncidentManager;
import guardlog.model.UserSession;
import javafx.collections.FXCollections; // Masih diperlukan untuk ComboBox
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class IncidentController implements Initializable {

    @FXML private ComboBox<String> categoryComboBox;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;
    @FXML private TextField locationField;
    @FXML private TextArea descriptionArea;
    @FXML private TableView<Incident> incidentTable;
    @FXML private TableColumn<Incident, String> idColumn;
    @FXML private TableColumn<Incident, String> categoryColumn;
    @FXML private TableColumn<Incident, LocalDateTime> dateTimeColumn; // Tipe diubah ke LocalDateTime
    @FXML private TableColumn<Incident, String> locationColumn;
    @FXML private TableColumn<Incident, String> reportedByColumn;
    @FXML private TableColumn<Incident, String> statusColumn;
    @FXML private TextArea detailsViewArea;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryComboBox.setItems(FXCollections.observableArrayList(
                "Keamanan", "Kesehatan", "Infrastruktur", "Lingkungan", "Lain-lain"
        ));
        datePicker.setValue(LocalDate.now());
        timeField.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        reportedByColumn.setCellValueFactory(new PropertyValueFactory<>("reportedByOfficer"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime")); // Ini tetap sama
        dateTimeColumn.setCellFactory(column -> new TableCell<Incident, LocalDateTime>() { // Tipe diubah
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) { // Parameter `item` sekarang adalah LocalDateTime
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(dateTimeFormatter)); // Langsung format `item` (LocalDateTime)
                }
            }
        });

        incidentTable.setItems(IncidentManager.getInstance().getIncidents());
        System.out.println("IncidentController: TableView diatur. Jumlah item di TableView: " + incidentTable.getItems().size());

        incidentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                displayIncidentDetails(newSelection);
            } else {
                detailsViewArea.clear();
            }
        });
    }

    private void displayIncidentDetails(Incident incident) {
        String details = String.format(
                "ID: %s\n" +
                "Kategori: %s\n" +
                "Waktu: %s\n" +
                "Lokasi: %s\n" +
                "Pelapor: %s\n" +
                "Status: %s\n" +
                "Deskripsi:\n%s",
                incident.getId(),
                incident.getCategory(),
                incident.getDateTime().format(dateTimeFormatter),
                incident.getLocation(),
                incident.getReportedByOfficer(),
                incident.getStatus(),
                incident.getDescription()
        );
        detailsViewArea.setText(details);
    }

    @FXML
    private void handleCreateIncident() {
        String category = categoryComboBox.getValue();
        LocalDate date = datePicker.getValue();
        String timeText = timeField.getText().trim();
        String location = locationField.getText().trim();
        String description = descriptionArea.getText().trim();
        String reportedBy = UserSession.getInstance().getActiveOfficerName();

        if (category == null || date == null || timeText.isEmpty() || location.isEmpty() || description.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Tidak Lengkap", "Semua kolom harus diisi.");
            return;
        }
        if (reportedBy == null || reportedBy.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Sesi Petugas Hilang", "Nama petugas tidak ditemukan. Harap login kembali.");
            return;
        }

        try {
            LocalTime time = LocalTime.parse(timeText, DateTimeFormatter.ofPattern("HH:mm"));
            LocalDateTime incidentDateTime = LocalDateTime.of(date, time);

            Incident newIncident = new Incident(category, incidentDateTime, location, description, reportedBy);
            IncidentManager.getInstance().addIncident(newIncident);

            showAlert(Alert.AlertType.INFORMATION, "Laporan Insiden", "Insiden berhasil dicatat dengan ID: " + newIncident.getId());

            categoryComboBox.getSelectionModel().clearSelection();
            datePicker.setValue(LocalDate.now());
            timeField.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
            locationField.clear();
            descriptionArea.clear();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Format Waktu Salah", "Format waktu harus HH:MM (contoh: 14:30).");
        }
    }

    @FXML
    private void handleUpdateIncidentStatus() {
        Incident selectedIncident = incidentTable.getSelectionModel().getSelectedItem();
        if (selectedIncident == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Insiden", "Silakan pilih insiden yang akan diperbarui statusnya.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(selectedIncident.getStatus());
        dialog.setTitle("Perbarui Status Insiden");
        dialog.setHeaderText("Masukkan status baru untuk insiden " + selectedIncident.getId());
        dialog.setContentText("Status:");

        dialog.showAndWait().ifPresent(newStatus -> {
            if (!newStatus.trim().isEmpty()) {
                selectedIncident.setStatus(newStatus.trim());
                incidentTable.refresh();
                displayIncidentDetails(selectedIncident);
                showAlert(Alert.AlertType.INFORMATION, "Status Diperbarui", "Status insiden " + selectedIncident.getId() + " berhasil diperbarui menjadi " + newStatus + ".");
            }
        });
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