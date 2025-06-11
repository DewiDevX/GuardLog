// GUARDLOG/app/src/main/java/guardlog/App.java
package guardlog;

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
        // Load the LoginView.fxml
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/guardlog/view/fxml/LoginView.fxml")));

        Scene scene = new Scene(root, 900, 650); // Sintaks sudah benar

        // --- Menerapkan CSS ke Scene Login (Scene awal) ---
        URL cssUrl = getClass().getResource("/guardlog/view/css/style.css");
        if (cssUrl == null) {
            System.err.println("ERROR: File CSS tidak ditemukan! Jalur: /guardlog/view/css/style.css");
        } else {
            scene.getStylesheets().add(cssUrl.toExternalForm());
            System.out.println("CSS dimuat untuk Scene Login dari: " + cssUrl.toExternalForm());
        }
        // ---------------------------------------------------

        primaryStage.setTitle("GuardLog - Aplikasi Keamanan");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}