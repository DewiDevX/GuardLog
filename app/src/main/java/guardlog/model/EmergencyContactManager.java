// GUARDLOG/app/src/main/java/guardlog/model/EmergencyContactManager.java
package guardlog.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmergencyContactManager {
    private static EmergencyContactManager instance;
    private ObservableList<EmergencyContact> contacts;

    private EmergencyContactManager() {
        contacts = FXCollections.observableArrayList();
        System.out.println("EmergencyContactManager: Constructor dipanggil. Memuat data dummy...");
        // Dummy Data
        addContact(new EmergencyContact("Kepala Keamanan", "Supervisor", "0812-3456-7890"));
        System.out.println("EmergencyContactManager: Kontak 1 ditambahkan. Jumlah kontak: " + contacts.size());
        addContact(new EmergencyContact("Pemadam Kebakaran", "Darurat", "113"));
        System.out.println("EmergencyContactManager: Kontak 2 ditambahkan. Jumlah kontak: " + contacts.size());
        addContact(new EmergencyContact("Polisi", "Darurat", "110"));
        System.out.println("EmergencyContactManager: Kontak 3 ditambahkan. Jumlah kontak: " + contacts.size());
        addContact(new EmergencyContact("Ambulans", "Darurat", "118"));
        System.out.println("EmergencyContactManager: Kontak 4 ditambahkan. Jumlah kontak: " + contacts.size());
        addContact(new EmergencyContact("Teknisi Gedung", "Teknisi", "0876-5432-1098"));
        System.out.println("EmergencyContactManager: Kontak 5 ditambahkan. Jumlah kontak: " + contacts.size());
    }

    public static EmergencyContactManager getInstance() {
        if (instance == null) {
            instance = new EmergencyContactManager();
        }
        return instance;
    }

    public ObservableList<EmergencyContact> getContacts() {
        System.out.println("EmergencyContactManager: getContacts() dipanggil. Mengembalikan list dengan ukuran: " + contacts.size());
        return contacts;
    }

    public void addContact(EmergencyContact contact) {
        this.contacts.add(contact);
        System.out.println("EmergencyContactManager: Menambahkan kontak baru: " + contact.getName() + ". Jumlah kontak sekarang: " + contacts.size());
        // TODO: Simpan ke database
    }

    // Metode lain
}