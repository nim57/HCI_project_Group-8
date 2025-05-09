package FurniturePackage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class MainPageController {

    @FXML
    protected void onView2D(ActionEvent event) {
        try {
            // Load the FXML for the 2D objects page
            Parent root = FXMLLoader.load(
                    getClass().getResource("/FurniturePackage/FurniturePage.fxml")
            );
            Scene scene = new Scene(root, 1550, 800);

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("2D Furniture Objects");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onView3D(ActionEvent event) {
        try {
            // Load the FXML for the 3D objects page
            Parent root = FXMLLoader.load(
                    getClass().getResource("/FurniturePackage/FurniturePage3D.fxml")
            );
            Scene scene = new Scene(root, 1550, 800);

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("3D Furniture Objects");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onBack(ActionEvent event) {
        try {
            // Create a new instance of DashboardApp
            DashboardApp dashboard = new DashboardApp();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Start the dashboard application in the current stage
            dashboard.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}