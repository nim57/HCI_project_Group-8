package FurniturePackage;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;

public class Gallery extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f4f4f4;");

        // Navigation Bar Buttons
        Button homeButton = createNavButton("Dashboard");
        Button aboutButton = createNavButton("About");
        Button galleryButton = createNavButton("Gallery");
        Button logoutButton = createNavButton("Logout");

        // Linked navigation
        logoutButton.setOnAction(e -> root.setCenter(openLogIN(primaryStage)));
        aboutButton.setOnAction(e -> openAboutUsPage());
        galleryButton.setOnAction(e -> openGalleryPage());
        homeButton.setOnAction(e -> root.setCenter(opendash(primaryStage)));

        // Navigation Bar Layout
        HBox navBar = new HBox(20, homeButton, aboutButton, galleryButton, logoutButton);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(15));
        navBar.setStyle("-fx-background-color: #2c3e50;");

        // Profile Picture
        Image image = new Image("resources/profile.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        Circle clip = new Circle(25, 25, 25);
        imageView.setClip(clip);

        StackPane imageContainer = new StackPane(imageView);
        imageContainer.setPadding(new Insets(0, 10, 0, 0));

        HBox profileContainer = new HBox(imageContainer);
        profileContainer.setAlignment(Pos.CENTER_RIGHT);

        HBox topContainer = new HBox(navBar, profileContainer);
        HBox.setHgrow(navBar, Priority.ALWAYS);

        root.setTop(new VBox(topContainer));

        // Main Container
        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setPadding(new Insets(30));
        mainContainer.setPrefSize(800, 600);

        Label lblTitle = new Label("Gallery");
        lblTitle.setId("dashboard-title");
        lblTitle.setStyle("-fx-font-size: 36px; -fx-text-fill: #2c3e50; -fx-font-weight: bold;");

        GridPane rectanglesGrid = new GridPane();
        rectanglesGrid.setHgap(20);
        rectanglesGrid.setVgap(20);
        rectanglesGrid.setAlignment(Pos.TOP_CENTER);

        String[] images = {
                "/resources/gallery/live1.jpg",
                "/resources/gallery/dine1.jpg",
                "/resources/gallery/kit1.jpg",
                "/resources/gallery/bed1.jpg",
                "/resources/gallery/live2.jpg",
                "/resources/gallery/dine2.jpg",
                "/resources/gallery/kit2.jpg",
                "/resources/gallery/bed2.jpg",
                "/resources/gallery/live3.jpg",
                "/resources/gallery/dine3.jpg",
                "/resources/gallery/kit3.jpg",
                "/resources/gallery/bed3.jpg",
        };

        String[] columnTitles = {"Living Rooms", "Dining Rooms", "Bed Room", "Kitchen"};

        for (int j = 0; j < 4; j++) {
            Label columnTitle = new Label(columnTitles[j]);
            columnTitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #34495e; -fx-font-weight: bold;");
            columnTitle.setAlignment(Pos.CENTER);
            rectanglesGrid.add(columnTitle, j, 0);
        }

        int imageIndex = 0;

        for (int i = 1; i <= 3; i++) {
            for (int j = 0; j < 4; j++) {
                Rectangle rectangle = new Rectangle(300, 200);
                rectangle.setArcWidth(20);
                rectangle.setArcHeight(20);
                rectangle.setStroke(Color.LIGHTGRAY);
                rectangle.setStrokeWidth(2);

                Image backgroundImage = new Image(images[imageIndex]);
                rectangle.setFill(new ImagePattern(backgroundImage));

                imageIndex = (imageIndex + 1) % images.length;

                rectangle.setOnMouseEntered(event -> {
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), rectangle);
                    scaleTransition.setToX(1.05);
                    scaleTransition.setToY(1.05);
                    scaleTransition.play();
                    rectangle.setEffect(new javafx.scene.effect.DropShadow(10, Color.GRAY));
                });

                rectangle.setOnMouseExited(event -> {
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), rectangle);
                    scaleTransition.setToX(1);
                    scaleTransition.setToY(1);
                    scaleTransition.play();
                    rectangle.setEffect(null);
                });

                rectanglesGrid.add(rectangle, j, i);
            }
        }

        mainContainer.getChildren().addAll(lblTitle, rectanglesGrid);

        // Wrap mainContainer in ScrollPane
        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color:transparent;");
        scrollPane.setPadding(new Insets(10));
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1550, 800);
        scene.getStylesheets().add(getClass().getResource("dashboard.css").toExternalForm());

        primaryStage.setTitle("Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createNavButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("nav-button");
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;"));
        return button;
    }

    private void openGalleryPage() {
        Gallery gallery = new Gallery();
        Stage galleryStage = new Stage();
        gallery.start(galleryStage);
    }

    private Node openLogIN(Stage primaryStage) {
        login Login = new login();
        Login.start(primaryStage);
        return null;
    }

    private Node opendash(Stage primaryStage) {
        DashboardApp Dash = new DashboardApp();
        Dash.start(primaryStage);
        return null;
    }

    private void openAboutUsPage() {
        AboutUs aboutUs = new AboutUs();
        Stage aboutUsStage = new Stage();
        aboutUs.start(aboutUsStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
