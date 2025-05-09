package FurniturePackage;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AboutUs extends Application {
    @Override
    public void start(Stage primaryStage) {
        VBox mainContainer = new VBox(20); // Use VBox for vertical layout
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(25));

        Label aboutTitle = new Label("ABOUT US");
        aboutTitle.setId("about-title");

        Label visionTitle = new Label("Our Vision");
        visionTitle.setId("vision-title");
        Label visionText = new Label("Our vision is to redefine the essence of quality living spaces worldwide,\n" +
                "becoming the premier destination for those seeking unparalleled craftsmanship in furniture. \n" +
                "We aim to inspire living spaces with our creations, ensuring each piece resonates with the elegance, \n" +
                "functionality, and sustainability that our brand stands for. As we journey towards this horizon, \n" +
                "we are committed to pushing the boundaries of design, quality, \n" +
                "and innovation to enrich homes and lives across the globe.");
        visionText.setWrapText(true);
        visionText.setId("vision-text");

        Label missionTitle = new Label("Our Mission");
        missionTitle.setId("mission-title");
        Label missionText = new Label("Our mission embodies the heart of craftsmanship, innovation, and sustainability. " +
                "\nAt the core of our work is a deep commitment to creating furniture that stands the test of time, \n" +
                "both in design and durability. We pledge to craft pieces that not only enhance the beauty of your home but \n" +
                "also uphold our responsibility to the environment. Through sustainable practices, meticulous design, and the finest materials, \n" +
                "we strive to bring you furniture that celebrates quality living, comfort, and the joy of home. \n" +
                "It is our mission to ensure that every creation from our workshop contributes to a more sustainable and beautiful world, \n" +
                "making quality furniture accessible and cherished by all.");
        missionText.setWrapText(true);
        missionText.setId("mission-text");

        Label contactTitle = new Label("Contact Us");
        contactTitle.setId("contact-title");
        Label contactInfo = new Label("Name: Furniture Hub\nPhone: +94 71 646 8923\nEmail: FurnitureHub@gmail.com.");
        contactInfo.setId("contact-info");
        contactInfo.setWrapText(true);

        // Adding components to the VBox
        mainContainer.getChildren().addAll(aboutTitle, visionTitle, visionText, missionTitle, missionText, contactTitle, contactInfo);

        // Create a new scene
        Scene scene = new Scene(mainContainer, 1550, 800);
        scene.getStylesheets().add(getClass().getResource("dashboard.css").toExternalForm());

        primaryStage.setTitle("About Us");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
