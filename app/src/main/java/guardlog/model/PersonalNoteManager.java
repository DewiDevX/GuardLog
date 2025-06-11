// GUARDLOG/app/src/main/java/guardlog/model/PersonalNoteManager.java
package guardlog.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;

public class PersonalNoteManager {
    private static PersonalNoteManager instance;
    private ObservableList<PersonalNote> personalNotes;

    private PersonalNoteManager() {
        personalNotes = FXCollections.observableArrayList();
        System.out.println("PersonalNoteManager: Constructor dipanggil. Memuat data dummy...");
        // Dummy Data
        addPersonalNote(new PersonalNote("Periksa Lampu", "Lampu di koridor barat berkedip, perlu diperbaiki.", "To-Do", "Admin"));
        System.out.println("PersonalNoteManager: Catatan 1 ditambahkan. Jumlah catatan: " + personalNotes.size());
        addPersonalNote(new PersonalNote("Pesan untuk Shift Malam", "Pastikan semua pintu terkunci sebelum shift berakhir.", "Serah Terima", "Admin"));
        System.out.println("PersonalNoteManager: Catatan 2 ditambahkan. Jumlah catatan: " + personalNotes.size());
    }

    public static PersonalNoteManager getInstance() {
        if (instance == null) {
            instance = new PersonalNoteManager();
        }
        return instance;
    }

    public ObservableList<PersonalNote> getPersonalNotes() {
        System.out.println("PersonalNoteManager: getPersonalNotes() dipanggil. Mengembalikan list dengan ukuran: " + personalNotes.size());
        return personalNotes;
    }

    public void addPersonalNote(PersonalNote note) {
        this.personalNotes.add(note);
        System.out.println("PersonalNoteManager: Menambahkan catatan baru: " + note.getTitle() + ". Jumlah catatan sekarang: " + personalNotes.size());
        // TODO: Simpan ke database
    }

    // Metode lain
}