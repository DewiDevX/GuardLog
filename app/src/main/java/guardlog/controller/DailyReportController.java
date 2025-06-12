// GUARDLOG/app/src/main/java/guardlog/controller/DailyReportController.java
package guardlog.controller;

import guardlog.model.Incident;
import guardlog.model.PatrolLogEntry;
import guardlog.model.UserSession;
import guardlog.model.Visitor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;         
import java.time.LocalDateTime;     
import java.time.LocalTime;         
import java.time.format.DateTimeFormatter; 
import java.util.Objects;
import java.util.ResourceBundle;

public class DailyReportController implements Initializable {

    @FXML private TextArea reportTextArea;

    // Dummy data for demonstration. In a real app, these would come from a database/service.
    private ObservableList<Visitor> dummyVisitors;
    private ObservableList<Incident> dummyIncidents;
    private ObservableList<PatrolLogEntry> dummyPatrolLogs;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize dummy data (replace with actual data loading)
        dummyVisitors = FXCollections.observableArrayList(
            new Visitor("Budi Santoso", "Meeting", "Admin", LocalDateTime.now().minusHours(2)),
            new Visitor("Siti Aminah", "Kunjungan", "Admin", LocalDateTime.now().minusHours(1))
        );
        dummyVisitors.get(0).setTimeOut(LocalDateTime.now().minusHours(1).minusMinutes(30)); // Checkout Budi

        dummyIncidents = FXCollections.observableArrayList(
            new Incident("Keamanan", LocalDateTime.now().minusHours(3), "Gerbang Utama", "Pagar rusak", "Admin"),
            new Incident("Kesehatan", LocalDateTime.now().minusHours(1), "Pos Jaga", "Petugas pusing", "Admin")
        );
        dummyIncidents.get(0).setStatus("Resolved"); // Update status

        dummyPatrolLogs = FXCollections.observableArrayList(
            new PatrolLogEntry(LocalDateTime.now().minusHours(4), "Gerbang Utama", "Semua aman.", "Admin"),
            new PatrolLogEntry(LocalDateTime.now().minusHours(2), "Perimeter Timur", "Anjing menggonggong.", "Admin")
        );
    }

    @FXML
    private void handleGenerateReport() {
        String officerName = UserSession.getInstance().getActiveOfficerName();
        if (officerName == null || officerName.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Sesi Petugas Hilang", "Nama petugas tidak ditemukan. Harap login kembali.");
            return;
        }

        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("========================================\n");
        reportBuilder.append("       LAPORAN HARIAN KEAMANAN\n");
        reportBuilder.append("========================================\n");
        reportBuilder.append("Tanggal: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))).append("\n");
        reportBuilder.append("Petugas Pelapor: ").append(officerName).append("\n");
        reportBuilder.append("----------------------------------------\n\n");

        // 1. Visitor Activity
        reportBuilder.append("--- Aktivitas Pengunjung ---\n");
        if (dummyVisitors.isEmpty()) {
            reportBuilder.append("Tidak ada pengunjung yang dicatat hari ini.\n");
        } else {
            for (Visitor visitor : dummyVisitors) {
                reportBuilder.append(String.format("  - %s (%s)\n    Masuk: %s\n    Keluar: %s\n    Dicatat oleh: %s\n",
                        visitor.getName(),
                        visitor.getPurpose(),
                        visitor.getTimeIn().format(DateTimeFormatter.ofPattern("HH:mm")),
                        visitor.getTimeOut() != null ? visitor.getTimeOut().format(DateTimeFormatter.ofPattern("HH:mm")) : "Belum Checkout",
                        visitor.getOfficerName()
                ));
            }
        }
        reportBuilder.append("\n");

        // 2. Incident Reports
        reportBuilder.append("--- Laporan Insiden ---\n");
        if (dummyIncidents.isEmpty()) {
            reportBuilder.append("Tidak ada insiden yang dicatat hari ini.\n");
        } else {
            for (Incident incident : dummyIncidents) {
                reportBuilder.append(String.format("  - ID: %s, Kategori: %s\n    Waktu: %s, Lokasi: %s\n    Deskripsi: %s\n    Pelapor: %s, Status: %s\n",
                        incident.getId(),
                        incident.getCategory(),
                        incident.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                        incident.getLocation(),
                        incident.getDescription().length() > 50 ? incident.getDescription().substring(0, 50) + "..." : incident.getDescription(), // Truncate long descriptions
                        incident.getReportedByOfficer(),
                        incident.getStatus()
                ));
            }
        }
        reportBuilder.append("\n");

        // 3. Patrol Log Summary
        reportBuilder.append("--- Ringkasan Log Patroli ---\n");
        if (dummyPatrolLogs.isEmpty()) {
            reportBuilder.append("Tidak ada catatan patroli hari ini.\n");
        } else {
            for (PatrolLogEntry entry : dummyPatrolLogs) {
                reportBuilder.append(String.format("  - Waktu: %s, Area: %s\n    Catatan: %s\n    Dicatat oleh: %s\n",
                        entry.getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm")),
                        entry.getArea(),
                        entry.getNotes().length() > 50 ? entry.getNotes().substring(0, 50) + "..." : entry.getNotes(),
                        entry.getRecordedByOfficer()
                ));
            }
        }
        reportBuilder.append("\n");

        reportBuilder.append("========================================\n");
        reportBuilder.append("Laporan selesai pada: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))).append("\n");
        reportBuilder.append("========================================\n");

        reportTextArea.setText(reportBuilder.toString());
        showAlert(Alert.AlertType.INFORMATION, "Laporan Harian", "Laporan harian berhasil dibuat.");
    }

    @FXML
    private void handleExportReport() {
        if (reportTextArea.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Tidak Ada Laporan", "Buat laporan terlebih dahulu sebelum mengekspor.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Simpan Laporan Harian");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setInitialFileName("Laporan_Harian_Keamanan_" + LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + ".txt");

        File file = fileChooser.showSaveDialog(reportTextArea.getScene().getWindow());
        if (file != null) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(reportTextArea.getText());
                showAlert(Alert.AlertType.INFORMATION, "Ekspor Berhasil", "Laporan berhasil disimpan ke:\n" + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Ekspor Gagal", "Terjadi kesalahan saat menyimpan laporan: " + e.getMessage());
            }
        }
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