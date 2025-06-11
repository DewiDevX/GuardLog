// GUARDLOG/app/src/main/java/guardlog/model/IncidentManager.java
package guardlog.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class IncidentManager {
    private static IncidentManager instance;
    private ObservableList<Incident> incidents;

    private IncidentManager() {
        incidents = FXCollections.observableArrayList();
        System.out.println("IncidentManager: Constructor dipanggil. Memuat data dummy...");
        // Dummy Data for demonstration
        addIncident(new Incident("Keamanan", LocalDateTime.now().minusHours(3), "Gerbang Utama", "Pagar rusak", "Admin"));
        System.out.println("IncidentManager: Insiden 'Pagar rusak' ditambahkan. Jumlah insiden saat ini: " + incidents.size());
        addIncident(new Incident("Kesehatan", LocalDateTime.now().minusHours(1), "Pos Jaga", "Petugas pusing", "Admin"));
        System.out.println("IncidentManager: Insiden 'Petugas pusing' ditambahkan. Jumlah insiden saat ini: " + incidents.size());
    }

    public static IncidentManager getInstance() {
        if (instance == null) {
            instance = new IncidentManager();
        }
        return instance;
    }

    public ObservableList<Incident> getIncidents() {
        System.out.println("IncidentManager: getIncidents() dipanggil. Mengembalikan list dengan ukuran: " + incidents.size());
        return incidents;
    }

    public void addIncident(Incident incident) {
        this.incidents.add(incident);
        System.out.println("IncidentManager: Menambahkan insiden baru: " + incident.getCategory() + ". Jumlah insiden sekarang: " + incidents.size());
        // TODO: Simpan ke database jika aplikasi nyata
    }

    // Anda bisa menambahkan metode lain di sini untuk update, delete, get by ID, dll.
}