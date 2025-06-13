// GUARDLOG/app/src/main/java/guardlog/controller/DailyReportController.java
package guardlog.controller;

import guardlog.model.IncidentManager;
import guardlog.model.PatrolLogManager;
import guardlog.model.UserSession;
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
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class DailyReportController implements Initializable {

    @FXML private TextArea reportTextArea;

    // Tambahkan formatter sebagai field kelas untuk penggunaan yang konsisten
    private final DateTimeFormatter timeOnlyFormatter = DateTimeFormatter.ofPattern("HH:mm"); // <--- PERUBAHAN DI SINI

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Data akan diambil saat tombol generate report diklik
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
        reportBuilder.append("Tanggal: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM YY"))).append("\n");
        reportBuilder.append("Petugas Pelapor: ").append(officerName).append("\n");
        reportBuilder.append("----------------------------------------\n\n");
        reportBuilder.append("--- Laporan Insiden ---\n");
        if (IncidentManager.getInstance().getIncidents().isEmpty()) {
            reportBuilder.append("Tidak ada insiden yang dicatat hari ini.\n");
        } else {
            IncidentManager.getInstance().getIncidents().forEach(incident -> {
                reportBuilder.append(String.format("  - ID: %s, Kategori: %s\n    Waktu: %s, Lokasi: %s\n    Deskripsi: %s\n    Pelapor: %s, Status: %s\n",
                        incident.getId(),
                        incident.getCategory(),
                        incident.getDateTime().format(timeOnlyFormatter), // <--- GUNAKAN timeOnlyFormatter DI SINI
                        incident.getLocation(),
                        incident.getDescription().length() > 50 ? incident.getDescription().substring(0, 50) + "..." : incident.getDescription(),
                        incident.getReportedByOfficer(),
                        incident.getStatus()
                ));
            });
        }
        reportBuilder.append("\n");

        reportBuilder.append("--- Ringkasan Log Patroli ---\n");
        if (PatrolLogManager.getInstance().getPatrolEntries().isEmpty()) {
            reportBuilder.append("Tidak ada catatan patroli hari ini.\n");
        } else {
            PatrolLogManager.getInstance().getPatrolEntries().forEach(entry -> {
                // --- PERUBAHAN DI SINI ---
                reportBuilder.append(String.format("  - Waktu: %s, Area: %s\n    Catatan: %s\n    Dicatat oleh: %s\n",
                        entry.getTimestamp().format(timeOnlyFormatter), // <--- GUNAKAN timeOnlyFormatter DI SINI (LINE 85)
                        entry.getArea(),
                        entry.getNotes().length() > 50 ? entry.getNotes().substring(0, 50) + "..." : entry.getNotes(),
                        entry.getRecordedByOfficer()
                ));
                // --- AKHIR PERUBAHAN ---
            });
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