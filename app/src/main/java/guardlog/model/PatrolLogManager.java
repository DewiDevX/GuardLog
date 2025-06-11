// GUARDLOG/app/src/main/java/guardlog/model/PatrolLogManager.java
package guardlog.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;

public class PatrolLogManager {
    private static PatrolLogManager instance;
    private ObservableList<PatrolLogEntry> patrolEntries;

    private PatrolLogManager() {
        patrolEntries = FXCollections.observableArrayList();
        System.out.println("PatrolLogManager: Constructor dipanggil. Memuat data dummy...");
        // Dummy Data
        addPatrolEntry(new PatrolLogEntry(LocalDateTime.now().minusHours(2), "Gerbang Utama", "Semua aman, tidak ada kejadian.", "Admin"));
        System.out.println("PatrolLogManager: Patroli 1 ditambahkan. Jumlah entri: " + patrolEntries.size());
        addPatrolEntry(new PatrolLogEntry(LocalDateTime.now().minusHours(1), "Perimeter Timur", "Terdapat suara anjing menggonggong.", "Admin"));
        System.out.println("PatrolLogManager: Patroli 2 ditambahkan. Jumlah entri: " + patrolEntries.size());
    }

    public static PatrolLogManager getInstance() {
        if (instance == null) {
            instance = new PatrolLogManager();
        }
        return instance;
    }

    public ObservableList<PatrolLogEntry> getPatrolEntries() {
        System.out.println("PatrolLogManager: getPatrolEntries() dipanggil. Mengembalikan list dengan ukuran: " + patrolEntries.size());
        return patrolEntries;
    }

    public void addPatrolEntry(PatrolLogEntry entry) {
        this.patrolEntries.add(entry);
        System.out.println("PatrolLogManager: Menambahkan entri patroli baru di area: " + entry.getArea() + ". Jumlah entri sekarang: " + patrolEntries.size());
        // TODO: Simpan ke database
    }

    // Metode lain
}