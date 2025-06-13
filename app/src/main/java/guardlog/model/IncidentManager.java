// GUARDLOG/app/src/main/java/guardlog/model/IncidentManager.java
package guardlog.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class IncidentManager {
    private static IncidentManager instance;
    private ObservableList<Incident> incidents;
    private static final String FILE_PATH = "data/incidents.txt"; // Jalur file TXT

    private IncidentManager() {
        incidents = FXCollections.observableArrayList();
        System.out.println("IncidentManager: Constructor dipanggil.");
        loadIncidents(); // <--- Muat data saat Manager diinisialisasi
        // Dummy data hanya jika file kosong setelah dimuat
        if (incidents.isEmpty()) {
            System.out.println("IncidentManager: File kosong atau gagal dimuat, menambahkan dummy data.");
            addIncident(new Incident("Keamanan", LocalDateTime.now().minusHours(3), "Gerbang Utama", "Pagar rusak", "Admin"));
            addIncident(new Incident("Kesehatan", LocalDateTime.now().minusHours(1), "Pos Jaga", "Petugas pusing", "Admin"));
            saveIncidents(); // Simpan dummy data ke file setelah ditambahkan
        }
    }

    public static IncidentManager getInstance() {
        if (instance == null) {
            instance = new IncidentManager();
        }
        return instance;
    }

    public ObservableList<Incident> getIncidents() {
        return incidents;
    }

    public void addIncident(Incident incident) {
        this.incidents.add(incident);
        System.out.println("IncidentManager: Menambahkan insiden baru: " + incident.getCategory() + ". Jumlah insiden sekarang: " + incidents.size());
        // saveIncidents(); // Opsional: simpan setiap kali ada penambahan, atau simpan di akhir aplikasi (direkomendasikan)
    }

    public void saveIncidents() {
        File file = new File(FILE_PATH);
        // Pastikan direktori parent ada
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Incident incident : incidents) {
                writer.write(incident.toCsvString());
                writer.newLine();
            }
            System.out.println("IncidentManager: Data insiden berhasil disimpan ke " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("IncidentManager: Gagal menyimpan data insiden: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadIncidents() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("IncidentManager: File insiden tidak ditemukan (" + FILE_PATH + "), membuat baru.");
            return; // Tidak ada yang dimuat karena file tidak ada
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Incident incident = Incident.fromCsvString(line);
                if (incident != null) {
                    incidents.add(incident);
                }
            }
            System.out.println("IncidentManager: Data insiden berhasil dimuat dari " + FILE_PATH + ". Jumlah: " + incidents.size());
        } catch (IOException e) {
            System.err.println("IncidentManager: Gagal memuat data insiden: " + e.getMessage());
            e.printStackTrace();
        }
    }
}