// GUARDLOG/app/src/main/java/guardlog/util/SceneSwitcher.java
package guardlog.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * A utility class for switching between different FXML scenes.
 * This class assumes that FXML files are located within the resources/guardlog/view/fxml/ directory
 * relative to the classpath root.
 */
public class SceneSwitcher {

    /**
     * Loads an FXML scene and sets it on the current stage.
     *
     * @param fxmlFileName The name of the FXML file (e.g., "DashboardView.fxml").
     * It should be located in /guardlog/view/fxml/.
     * @param title The title for the new window/scene.
     * @param event The ActionEvent that triggered this navigation (used to get the current Stage).
     */
    public static void loadScene(String fxmlFileName, String title, ActionEvent event) {
        try {
            // The path must be absolute from the classpath root (src/main/resources or src/main/java)
            // Given your structure, it starts with /guardlog/view/fxml/
            String fullPath = "/guardlog/view/fxml/" + fxmlFileName;
            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getResource(fullPath)));
            Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
            window.setTitle(title);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML: " + fxmlFileName);
            // Optionally, show an alert to the user
            // Alert alert = new Alert(Alert.AlertType.ERROR, "Error Loading Page", "Could not load " + fxmlFileName);
            // alert.showAndWait();
        }
    }
}