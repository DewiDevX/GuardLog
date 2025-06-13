// GUARDLOG/app/src/main/java/guardlog/App.java
package guardlog;

import guardlog.model.EmergencyContactManager;
import guardlog.model.IncidentManager;
import guardlog.model.PatrolLogManager;
import guardlog.model.PersonalNoteManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // --- Inisialisasi semua Manager di awal aplikasi ---
        // Ini akan memicu constructor manager yang akan memuat data dari file
        IncidentManager.getInstance();
        PatrolLogManager.getInstance();
        PersonalNoteManager.getInstance();
        EmergencyContactManager.getInstance();
        System.out.println("Semua data manager diinisialisasi.");
        // ---------------------------------------------------

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/guardlog/view/fxml/LoginView.fxml")));

        Scene scene = new Scene(root, 900, 650);

        URL cssUrl = getClass().getResource("/guardlog/view/css/style.css");
        if (cssUrl == null) {
            System.err.println("ERROR: File CSS tidak ditemukan! Jalur: /guardlog/view/css/style.css");
        } else {
            scene.getStylesheets().add(cssUrl.toExternalForm());
            System.out.println("CSS dimuat untuk Scene Login dari: " + cssUrl.toExternalForm());
        }

        primaryStage.setTitle("GuardLog - Aplikasi Keamanan");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        // --- Menyimpan semua data saat aplikasi ditutup ---
        System.out.println("Aplikasi ditutup. Menyimpan semua data...");
        IncidentManager.getInstance().saveIncidents();
        PatrolLogManager.getInstance().savePatrolEntries();
        PersonalNoteManager.getInstance().savePersonalNotes();
        EmergencyContactManager.getInstance().saveContacts();
        System.out.println("Semua data berhasil disimpan.");
        // -------------------------------------------------
    }

    public static void main(String[] args) {
        launch(args);
    }
}