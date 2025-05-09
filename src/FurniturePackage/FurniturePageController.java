package FurniturePackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FurniturePageController {

    /**
     * Displays furniture with proper scaling and centering
     */
    private void displayFurnitureView(Group viewGroup, String title) {
        try {
            // First layout pass to calculate bounds
            viewGroup.layout();

            // Get the visual bounds of the furniture
            javafx.geometry.Bounds bounds = viewGroup.getBoundsInLocal();
            System.out.println("Displaying " + title + " - Width: " + bounds.getWidth() +
                    ", Height: " + bounds.getHeight());

            // Add padding and calculate window size
            double padding = 40;
            double width = bounds.getWidth() + padding;
            double height = bounds.getHeight() + padding;

            // Ensure minimum size
            width = Math.max(width, 400);
            height = Math.max(height, 400);

            // Center the furniture in the window
            viewGroup.setTranslateX((width - bounds.getWidth()) / 2 - bounds.getMinX());
            viewGroup.setTranslateY((height - bounds.getHeight()) / 2 - bounds.getMinY());

            // Create and show the window
            Scene scene = new Scene(viewGroup, width, height);
            Stage stage = new Stage();
            stage.setTitle("2D View: " + title);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();
        } catch (Exception e) {
            showError("Failed to display furniture", e.getMessage());
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Furniture view methods
    @FXML private void onViewChair(ActionEvent event) {
        displayFurnitureView(new Chair2D().createChairView(), "Chair");
    }

    @FXML private void onViewBed(ActionEvent event) {
        displayFurnitureView(Bed2D.createBedView(), "Bed");
    }

    @FXML private void onViewCupboard(ActionEvent event) {
        displayFurnitureView(Cupboard2D.createCupboardView(), "Cupboard");
    }

    @FXML private void onViewTable(ActionEvent event) {
        displayFurnitureView(Table2D.createTableView(), "Table");
    }

    @FXML private void onViewRack(ActionEvent event) {
        displayFurnitureView(Rack2D.createRackView(), "Rack");
    }

    @FXML private void onViewBench(ActionEvent event) {
        displayFurnitureView(Bench2D.createBenchView(), "Bench");
    }

    @FXML private void onViewDoor(ActionEvent event) {
        displayFurnitureView(Door2D.createDoorView(), "Door");
    }

    @FXML private void onViewBookshelf(ActionEvent event) {
        displayFurnitureView(Bookshelf2D.createBookshelfView(), "Bookshelf");
    }

    @FXML private void onBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/FurniturePackage/MainPage.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (Exception e) {
            showError("Navigation Error", "Could not return to main menu: " + e.getMessage());
        }
    }
}