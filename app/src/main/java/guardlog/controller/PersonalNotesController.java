// GUARDLOG/app/src/main/java/guardlog/controller/PersonalNotesController.java
package guardlog.controller;

import guardlog.model.PersonalNote;
import guardlog.model.PersonalNoteManager; // <--- Import ini
import guardlog.model.UserSession;
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

public class PersonalNotesController implements Initializable {

    @FXML private TextField noteTitleField;
    @FXML private TextArea noteContentArea;
    @FXML private ComboBox<String> noteCategoryComboBox;
    @FXML private TableView<PersonalNote> notesTable;
    @FXML private TableColumn<PersonalNote, String> titleColumn;
    @FXML private TableColumn<PersonalNote, String> categoryColumn;
    @FXML private TableColumn<PersonalNote, String> timestampColumn;
    @FXML private TableColumn<PersonalNote, String> createdByColumn;
    @FXML private TextArea noteDetailView;

    // Hapus ObservableList<PersonalNote> personalNotes = FXCollections.observableArrayList();
    // Gunakan PersonalNoteManager

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        noteCategoryComboBox.setItems(FXCollections.observableArrayList(
                "Umum", "To-Do", "Pengingat", "Serah Terima"
        ));

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("createdByOfficer"));

        timestampColumn.setCellFactory(column -> new TableCell<PersonalNote, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    LocalDateTime time = ((PersonalNote) getTableRow().getItem()).getTimestamp();
                    setText(time != null ? time.format(formatter) : "");
                }
            }
        });

        // Set TableView items dari PersonalNoteManager singleton
        notesTable.setItems(PersonalNoteManager.getInstance().getPersonalNotes());
        System.out.println("PersonalNotesController: TableView diatur. Jumlah item di TableView: " + notesTable.getItems().size()); // Debug
    }

    @FXML
    private void handleCreateNote() {
        String title = noteTitleField.getText().trim();
        String content = noteContentArea.getText().trim();
        String category = noteCategoryComboBox.getValue();
        String createdBy = UserSession.getInstance().getActiveOfficerName();

        if (title.isEmpty() || content.isEmpty() || category == null) {
            showAlert(Alert.AlertType.WARNING, "Input Tidak Lengkap", "Judul, isi, dan kategori catatan tidak boleh kosong.");
            return;
        }
        if (createdBy == null || createdBy.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Sesi Petugas Hilang", "Nama petugas tidak ditemukan. Harap login kembali.");
            return;
        }

        PersonalNote newNote = new PersonalNote(title, content, category, createdBy);
        PersonalNoteManager.getInstance().addPersonalNote(newNote); // Tambahkan melalui Manager

        noteTitleField.clear();
        noteContentArea.clear();
        noteCategoryComboBox.getSelectionModel().clearSelection();
        showAlert(Alert.AlertType.INFORMATION, "Catatan Berhasil", "Catatan pribadi berhasil ditambahkan.");
    }

    @FXML
    private void handleDeleteNote() {
        PersonalNote selectedNote = notesTable.getSelectionModel().getSelectedItem();
        if (selectedNote == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Catatan", "Silakan pilih catatan yang ingin dihapus.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Yakin ingin menghapus catatan ini?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                PersonalNoteManager.getInstance().getPersonalNotes().remove(selectedNote); // Hapus dari Manager
                showAlert(Alert.AlertType.INFORMATION, "Catatan Dihapus", "Catatan berhasil dihapus.");
                noteDetailView.clear(); // Clear detail view after deletion
                // TODO: Delete from database
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