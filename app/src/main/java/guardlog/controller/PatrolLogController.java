// GUARDLOG/app/src/main/java/guardlog/controller/PatrolLogController.java
package guardlog.controller;

import guardlog.model.PatrolLogEntry;
import guardlog.model.PatrolLogManager;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class PatrolLogController implements Initializable {

    @FXML private ComboBox<String> areaComboBox;
    @FXML private TextArea notesArea;
    @FXML private TableView<PatrolLogEntry> patrolLogTable;
    @FXML private TableColumn<PatrolLogEntry, LocalDateTime> timestampColumn; // Tipe diubah ke LocalDateTime
    @FXML private TableColumn<PatrolLogEntry, String> areaColumn;
    @FXML private TableColumn<PatrolLogEntry, String> notesColumn;
    @FXML private TableColumn<PatrolLogEntry, String> officerColumn;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        areaComboBox.setItems(FXCollections.observableArrayList(
                "Gerbang Utama", "Area Parkir", "Perimeter Timur", "Perimeter Barat", "CCTV Room", "Pos Jaga"
        ));

        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
        officerColumn.setCellValueFactory(new PropertyValueFactory<>("recordedByOfficer"));

        timestampColumn.setCellFactory(column -> new TableCell<PatrolLogEntry, LocalDateTime>() { // Tipe diubah
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) { // Parameter `item` sekarang adalah LocalDateTime
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(formatter)); // Langsung format `item` (LocalDateTime)
                }
            }
        });

        patrolLogTable.setItems(PatrolLogManager.getInstance().getPatrolEntries());
        System.out.println("PatrolLogController: TableView diatur. Jumlah item di TableView: " + patrolLogTable.getItems().size());
    }

    @FXML
    private void handleRecordPatrol() {
        String area = areaComboBox.getValue();
        String notes = notesArea.getText().trim();
        String officer = UserSession.getInstance().getActiveOfficerName();

        if (area == null || notes.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Tidak Lengkap", "Area dan catatan patroli tidak boleh kosong.");
            return;
        }
        if (officer == null || officer.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Sesi Petugas Hilang", "Nama petugas tidak ditemukan. Harap login kembali.");
            return;
        }

        PatrolLogEntry newEntry = new PatrolLogEntry(LocalDateTime.now(), area, notes, officer);
        PatrolLogManager.getInstance().addPatrolEntry(newEntry);

        areaComboBox.getSelectionModel().clearSelection();
        notesArea.clear();
        showAlert(Alert.AlertType.INFORMATION, "Patroli Dicatat", "Catatan patroli di " + area + " berhasil ditambahkan.");
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