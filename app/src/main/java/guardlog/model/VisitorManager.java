// GUARDLOG/app/src/main/java/guardlog/model/VisitorManager.java
package guardlog.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;

public class VisitorManager {
    private static VisitorManager instance;
    private ObservableList<Visitor> visitors;

    private VisitorManager() {
        visitors = FXCollections.observableArrayList();
        System.out.println("VisitorManager: Constructor dipanggil. Memuat data dummy...");
        // Dummy Data
        addVisitor(new Visitor("Budi Santoso", "Meeting dengan Manager", "Admin", LocalDateTime.now().minusHours(2)));
        visitors.get(0).setTimeOut(LocalDateTime.now().minusHours(1).minusMinutes(30)); // Set checkout for Budi
        System.out.println("VisitorManager: Pengunjung Budi ditambahkan. Jumlah pengunjung: " + visitors.size());
        addVisitor(new Visitor("Siti Aminah", "Kunjungan Keluarga", "Admin", LocalDateTime.now().minusHours(1)));
        System.out.println("VisitorManager: Pengunjung Siti ditambahkan. Jumlah pengunjung: " + visitors.size());
        addVisitor(new Visitor("Joko Susilo", "Antar Dokumen", "Admin", LocalDateTime.now().minusMinutes(30)));
        System.out.println("VisitorManager: Pengunjung Joko ditambahkan. Jumlah pengunjung: " + visitors.size());
    }

    public static VisitorManager getInstance() {
        if (instance == null) {
            instance = new VisitorManager();
        }
        return instance;
    }

    public ObservableList<Visitor> getVisitors() {
        System.out.println("VisitorManager: getVisitors() dipanggil. Mengembalikan list dengan ukuran: " + visitors.size());
        return visitors;
    }

    public void addVisitor(Visitor visitor) {
        this.visitors.add(visitor);
        System.out.println("VisitorManager: Menambahkan pengunjung baru: " + visitor.getName() + ". Jumlah pengunjung sekarang: " + visitors.size());
        // TODO: Simpan ke database
    }

    // Metode lain seperti updateVisitor, deleteVisitor jika diperlukan
}