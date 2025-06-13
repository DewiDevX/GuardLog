// GUARDLOG/app/src/main/java/guardlog/model/PatrolLogManager.java
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

public class PatrolLogManager {
    private static PatrolLogManager instance;
    private ObservableList<PatrolLogEntry> patrolEntries;
    private static final String FILE_PATH = "data/patrol_logs.txt";

    private PatrolLogManager() {
        patrolEntries = FXCollections.observableArrayList();
        System.out.println("PatrolLogManager: Constructor dipanggil.");
        loadPatrolEntries();
        if (patrolEntries.isEmpty()) {
            System.out.println("PatrolLogManager: File kosong atau gagal dimuat, menambahkan dummy data.");
            addPatrolEntry(new PatrolLogEntry(LocalDateTime.now().minusHours(2), "Gerbang Utama", "Semua aman, tidak ada kejadian.", "Admin"));
            addPatrolEntry(new PatrolLogEntry(LocalDateTime.now().minusHours(1), "Perimeter Timur", "Terdapat suara anjing menggonggong.", "Admin"));
            savePatrolEntries();
        }
    }

    public static PatrolLogManager getInstance() {
        if (instance == null) {
            instance = new PatrolLogManager();
        }
        return instance;
    }

    public ObservableList<PatrolLogEntry> getPatrolEntries() {
        return patrolEntries;
    }

    public void addPatrolEntry(PatrolLogEntry entry) {
        this.patrolEntries.add(entry);
        System.out.println("PatrolLogManager: Menambahkan entri patroli baru di area: " + entry.getArea() + ". Jumlah entri sekarang: " + patrolEntries.size());
    }

    public void savePatrolEntries() {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (PatrolLogEntry entry : patrolEntries) {
                writer.write(entry.toCsvString());
                writer.newLine();
            }
            System.out.println("PatrolLogManager: Data log patroli berhasil disimpan ke " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("PatrolLogManager: Gagal menyimpan data log patroli: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadPatrolEntries() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("PatrolLogManager: File log patroli tidak ditemukan (" + FILE_PATH + "), membuat baru.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                PatrolLogEntry entry = PatrolLogEntry.fromCsvString(line);
                if (entry != null) {
                    patrolEntries.add(entry);
                }
            }
            System.out.println("PatrolLogManager: Data log patroli berhasil dimuat dari " + FILE_PATH + ". Jumlah: " + patrolEntries.size());
        } catch (IOException e) {
            System.err.println("PatrolLogManager: Gagal memuat data log patroli: " + e.getMessage());
            e.printStackTrace();
        }
    }
}