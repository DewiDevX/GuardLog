// GUARDLOG/app/src/main/java/guardlog/model/PersonalNoteManager.java
package guardlog.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
// import java.time.LocalDateTime;

public class PersonalNoteManager {
    private static PersonalNoteManager instance;
    private ObservableList<PersonalNote> personalNotes;
    private static final String FILE_PATH = "data/personal_notes.txt";

    private PersonalNoteManager() {
        personalNotes = FXCollections.observableArrayList();
        System.out.println("PersonalNoteManager: Constructor dipanggil.");
        loadPersonalNotes();
        if (personalNotes.isEmpty()) {
            System.out.println("PersonalNoteManager: File kosong atau gagal dimuat, menambahkan dummy data.");
            addPersonalNote(new PersonalNote("Periksa Lampu", "Lampu di koridor barat berkedip, perlu diperbaiki.", "To-Do", "Admin"));
            addPersonalNote(new PersonalNote("Pesan untuk Shift Malam", "Pastikan semua pintu terkunci sebelum shift berakhir.", "Serah Terima", "Admin"));
            savePersonalNotes();
        }
    }

    public static PersonalNoteManager getInstance() {
        if (instance == null) {
            instance = new PersonalNoteManager();
        }
        return instance;
    }

    public ObservableList<PersonalNote> getPersonalNotes() {
        return personalNotes;
    }

    public void addPersonalNote(PersonalNote note) {
        this.personalNotes.add(note);
        System.out.println("PersonalNoteManager: Menambahkan catatan baru: " + note.getTitle() + ". Jumlah catatan sekarang: " + personalNotes.size());
    }

    public void savePersonalNotes() {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (PersonalNote note : personalNotes) {
                writer.write(note.toCsvString());
                writer.newLine();
            }
            System.out.println("PersonalNoteManager: Data catatan pribadi berhasil disimpan ke " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("PersonalNoteManager: Gagal menyimpan data catatan pribadi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadPersonalNotes() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("PersonalNoteManager: File catatan pribadi tidak ditemukan (" + FILE_PATH + "), membuat baru.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                PersonalNote note = PersonalNote.fromCsvString(line);
                if (note != null) {
                    personalNotes.add(note);
                }
            }
            System.out.println("PersonalNoteManager: Data catatan pribadi berhasil dimuat dari " + FILE_PATH + ". Jumlah: " + personalNotes.size());
        } catch (IOException e) {
            System.err.println("PersonalNoteManager: Gagal memuat data catatan pribadi: " + e.getMessage());
            e.printStackTrace();
        }
    }
}