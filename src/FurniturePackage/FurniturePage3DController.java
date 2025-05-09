package FurniturePackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class FurniturePage3DController {

    @FXML
    private Button backButton;

    @FXML
    protected void onViewChair(ActionEvent event) {
        Chair chair3D = new Chair();
        chair3D.start(new Stage()); // Using original start() method
    }

    @FXML
    protected void onViewBed(ActionEvent event) {
        Bed bed3D = new Bed();
        bed3D.start(new Stage());
    }

    @FXML
    protected void onViewCupboard(ActionEvent event) {
        Cupboard cupboard3D = new Cupboard();
        cupboard3D.start(new Stage());
    }

    @FXML
    protected void onViewTable(ActionEvent event) {
        Table table3D = new Table();
        table3D.start(new Stage());
    }

    @FXML
    protected void onRoundTable(ActionEvent event) {
        RoundedTable roundedTable = new RoundedTable();
        roundedTable.start(new Stage());
    }

    @FXML
    protected void onViewDesk(ActionEvent event) {
        Desk desk3D = new Desk();
        desk3D.start(new Stage());
    }

    @FXML
    protected void onViewBookShelf(ActionEvent event) {
        Bookshelf bookshelf3D = new Bookshelf();
        bookshelf3D.start(new Stage());
    }

    @FXML
    protected void onBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/FurniturePackage/MainPage.fxml"));
            Scene scene = new Scene(root, 1550, 800);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            showErrorAlert("Failed to load main page: " + e.getMessage());
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}