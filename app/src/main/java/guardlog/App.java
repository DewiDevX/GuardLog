// GUARDLOG/app/src/main/java/guardlog/App.java
package guardlog;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects; // Added for Objects.requireNonNull()

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the LoginView.fxml directly
        // The path should be relative to the classpath root, which is 'guardlog' in this case.
        // So, it starts with /guardlog/...
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/guardlog/view/fxml/LoginView.fxml")));

        primaryStage.setTitle("GuardLog - Aplikasi Keamanan");
        primaryStage.setScene(new Scene(root, 900, 650)); // Adjusted size for better layout
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}