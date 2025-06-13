// GUARDLOG/app/src/main/java/guardlog/model/EmergencyContactManager.java
package guardlog.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EmergencyContactManager {
    private static EmergencyContactManager instance;
    private ObservableList<EmergencyContact> contacts;
    private static final String FILE_PATH = "data/emergency_contacts.txt";

    private EmergencyContactManager() {
        contacts = FXCollections.observableArrayList();
        System.out.println("EmergencyContactManager: Constructor dipanggil.");
        loadContacts();
        if (contacts.isEmpty()) {
            System.out.println("EmergencyContactManager: File kosong atau gagal dimuat, menambahkan dummy data.");
            addContact(new EmergencyContact("Kepala Keamanan", "Supervisor", "0812-3456-7890"));
            addContact(new EmergencyContact("Pemadam Kebakaran", "Darurat", "113"));
            addContact(new EmergencyContact("Polisi", "Darurat", "110"));
            addContact(new EmergencyContact("Ambulans", "Darurat", "118"));
            addContact(new EmergencyContact("Teknisi Gedung", "Teknisi", "0876-5432-1098"));
            saveContacts();
        }
    }

    public static EmergencyContactManager getInstance() {
        if (instance == null) {
            instance = new EmergencyContactManager();
        }
        return instance;
    }

    public ObservableList<EmergencyContact> getContacts() {
        return contacts;
    }

    public void addContact(EmergencyContact contact) {
        this.contacts.add(contact);
        System.out.println("EmergencyContactManager: Menambahkan kontak baru: " + contact.getName() + ". Jumlah kontak sekarang: " + contacts.size());
    }

    public void saveContacts() {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (EmergencyContact contact : contacts) {
                writer.write(contact.toCsvString());
                writer.newLine();
            }
            System.out.println("EmergencyContactManager: Data kontak darurat berhasil disimpan ke " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("EmergencyContactManager: Gagal menyimpan data kontak darurat: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadContacts() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("EmergencyContactManager: File kontak darurat tidak ditemukan (" + FILE_PATH + "), membuat baru.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                EmergencyContact contact = EmergencyContact.fromCsvString(line);
                if (contact != null) {
                    contacts.add(contact);
                }
            }
            System.out.println("EmergencyContactManager: Data kontak darurat berhasil dimuat dari " + FILE_PATH + ". Jumlah: " + contacts.size());
        } catch (IOException e) {
            System.err.println("EmergencyContactManager: Gagal memuat data kontak darurat: " + e.getMessage());
            e.printStackTrace();
        }
    }
}