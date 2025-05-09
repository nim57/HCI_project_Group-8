package FurniturePackage;
import javafx.application.Application;
import javax.swing.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class DashboardApp extends Application {
    private Stage primaryStage;
    private String username = "User Name"; // Default value

    public void setUsername(String username) {
        if (username != null && !username.isEmpty()) {
            this.username = username;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        BorderPane root = new BorderPane();

        // Create sidebar for navigation
        VBox sidebar = createSidebar(root);

        // Main content area
        StackPane contentArea = new StackPane();

        // Header with welcome message and profile
        HBox header = createHeader();

        // Main dashboard content
        VBox dashboardContent = createDashboardContent();

        // Image slider
        HBox imageSliderRow = createImageSlider();

        // Combine all elements in the main content area
        VBox mainContent = new VBox(20);
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setMaxWidth(1200);
        mainContent.getChildren().addAll(header, dashboardContent, imageSliderRow);
        mainContent.setPadding(new Insets(30));
        contentArea.setAlignment(Pos.CENTER);
        contentArea.getChildren().add(mainContent);

        // Set up the root layout
        root.setLeft(sidebar);
        root.setCenter(contentArea);

        // Create scene and apply styles
        Scene scene = new Scene(root, 1550, 800);
        scene.getStylesheets().add(getClass().getResource("dashboard.css").toExternalForm());

        primaryStage.setTitle("Furniture Hub Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createSidebar(BorderPane root) {
        // App logo/brand
        Label brandLabel = new Label("Furniture Hub");
        brandLabel.getStyleClass().add("brand-label");

        // Navigation buttons with icons
        Button homeButton = createNavButton("Dashboard", "home-icon");
        homeButton.setOnAction(e -> refreshDashboard(root));

        Button aboutButton = createNavButton("About", "about-icon");
        aboutButton.setOnAction(e -> openAboutUsPage());

        Button galleryButton = createNavButton("Gallery", "gallery-icon");
        galleryButton.setOnAction(e -> openGalleryPage());

        Button logoutButton = createNavButton("Logout", "logout-icon");
        logoutButton.setOnAction(e -> root.setCenter(openLogIN(primaryStage)));

        // Add spacing between navigation groups
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Create sidebar container
        VBox sidebar = new VBox(25);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPadding(new Insets(25));
        sidebar.getChildren().addAll(
                brandLabel,
                new Separator(),
                homeButton,
                aboutButton,
                galleryButton,
                spacer,
                logoutButton
        );

        return sidebar;
    }

    private Button createNavButton(String text, String iconClass) {
        Button button = new Button(text);
        button.getStyleClass().addAll("nav-button", iconClass);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);

        return button;
    }

    private HBox createHeader() {
        // Welcome message
        VBox welcomeBox = new VBox(5);
        Label welcomeLabel = new Label("Welcome to Furniture Hub");
        welcomeLabel.getStyleClass().add("welcome-label");

        Text dateText = new Text("Today is " + java.time.LocalDate.now().toString());
        dateText.getStyleClass().add("date-text");

        welcomeBox.getChildren().addAll(welcomeLabel, dateText);

        // Profile section
        HBox profileSection = new HBox(15);
        profileSection.setAlignment(Pos.CENTER_RIGHT);

        // Profile picture with circular clip
        Image profileImage = new Image("resources/profile.png");
        ImageView profileView = new ImageView(profileImage);
        profileView.setFitHeight(50);
        profileView.setFitWidth(50);

        Circle clip = new Circle(25, 25, 25);
        profileView.setClip(clip);

        StackPane profileContainer = new StackPane(profileView);
        profileContainer.getStyleClass().add("profile-container");

        Label usernameLabel = new Label(username);
        usernameLabel.getStyleClass().add("username-label");

        profileSection.getChildren().addAll(usernameLabel, profileContainer);

        // Combine welcome and profile in header
        HBox header = new HBox();
        header.getStyleClass().add("header");
        header.getChildren().addAll(welcomeBox, profileSection);
        HBox.setHgrow(welcomeBox, Priority.ALWAYS);

        return header;
    }

    private VBox createDashboardContent() {
        VBox content = new VBox(30);
        content.setAlignment(Pos.TOP_CENTER);

        // Dashboard title
        Label dashboardTitle = new Label("Your Furniture Design Hub");
        dashboardTitle.setId("dashboard-title");

        // Feature cards in a grid
        GridPane cardsGrid = new GridPane();
        cardsGrid.setHgap(30);
        cardsGrid.setVgap(30);
        cardsGrid.setAlignment(Pos.CENTER);

        VBox furnitureViewCard = createFeatureCard(
                "Furniture View",
                "Explore and customize individual furniture pieces in 3D",
                event -> loadMainPage()
        );

        VBox interiorRoomCard = createFeatureCard(
                "Interior Room",
                "Design and visualize complete interior spaces",
                event -> {
                    FurnitureWorld furnitureWorld = new FurnitureWorld();
                    Stage stage = new Stage();
                    furnitureWorld.launchFurnitureWorld(stage);
                }
        );

        cardsGrid.add(furnitureViewCard, 0, 0);
        cardsGrid.add(interiorRoomCard, 1, 0);

        content.getChildren().addAll(dashboardTitle, cardsGrid);
        return content;
    }

    private VBox createFeatureCard(String title, String description, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        VBox card = new VBox(15);
        card.getStyleClass().add("feature-card");
        card.setPadding(new Insets(25));
        card.setPrefSize(300, 200);

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("card-title");

        Label descLabel = new Label(description);
        descLabel.getStyleClass().add("card-description");
        descLabel.setWrapText(true);

        Button actionButton = new Button("Open " + title);
        actionButton.getStyleClass().add("card-button");
        actionButton.setOnAction(action);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        card.getChildren().addAll(titleLabel, descLabel, spacer, actionButton);

        // Add hover effect
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.4));
        card.setEffect(shadow);

        return card;
    }

    private HBox createImageSlider() {
        HBox sliderContainer = new HBox();
        sliderContainer.getStyleClass().add("slider-container");
        sliderContainer.setAlignment(Pos.CENTER);

        StackPane imageContainer = new StackPane();
        imageContainer.getStyleClass().add("image-slider");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(800);
        imageView.setFitHeight(400);
        imageView.setPreserveRatio(true);

        // List of image paths
        String[] images = new String[]{
                "resources/cs.png",
                "resources/bd.png",
                "resources/bks.png",
        };

        // Using an array to hold the current image index
        final int[] currentIndex = {0};

        // Display the first image initially
        imageView.setImage(new Image(images[currentIndex[0]]));

        // Create a timeline for changing images with fade transition
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            FadeTransition fade = new FadeTransition(Duration.millis(500), imageView);
            fade.setFromValue(1.0);
            fade.setToValue(0.3);
            fade.setOnFinished(e -> {
                currentIndex[0] = (currentIndex[0] + 1) % images.length;
                imageView.setImage(new Image(images[currentIndex[0]]));

                FadeTransition fadeIn = new FadeTransition(Duration.millis(500), imageView);
                fadeIn.setFromValue(0.3);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            fade.play();
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Add navigation dots
        HBox dots = new HBox(10);
        dots.setAlignment(Pos.CENTER);
        dots.setPadding(new Insets(15, 0, 0, 0));

        for (int i = 0; i < images.length; i++) {
            Circle dot = new Circle(5);
            dot.getStyleClass().add("slider-dot");
            if (i == 0) dot.getStyleClass().add("active");
            final int imageIndex = i;
            dot.setOnMouseClicked(e -> {
                currentIndex[0] = imageIndex;
                imageView.setImage(new Image(images[currentIndex[0]]));

                // Update active dot
                dots.getChildren().forEach(node -> node.getStyleClass().remove("active"));
                dot.getStyleClass().add("active");
            });
            dots.getChildren().add(dot);
        }

        imageContainer.getChildren().add(imageView);

        VBox sliderWithDots = new VBox(10);
        sliderWithDots.setAlignment(Pos.CENTER);
        sliderWithDots.getChildren().addAll(imageContainer, dots);

        sliderContainer.getChildren().add(sliderWithDots);
        return sliderContainer;
    }

    private void refreshDashboard(BorderPane root) {
        // Recreate the dashboard content
        VBox dashboardContent = createDashboardContent();
        HBox imageSliderRow = createImageSlider();
        HBox header = createHeader();  // This will now use the current username

        VBox mainContent = new VBox(20);
        mainContent.getChildren().addAll(header, dashboardContent, imageSliderRow);
        mainContent.setPadding(new Insets(30));

        StackPane contentArea = new StackPane(mainContent);
        contentArea.getStyleClass().add("content-area");

        root.setCenter(contentArea);
    }

    private Node openLogIN(Stage primaryStage) {
        login Login = new login();
        Login.start(primaryStage);
        return null;
    }

    private void openGalleryPage() {
        Gallery gallery = new Gallery();
        Stage galleryStage = new Stage();
        gallery.start(galleryStage);
    }

    private void openAboutUsPage() {
        AboutUs aboutUs = new AboutUs();
        Stage aboutUsStage = new Stage();
        aboutUs.start(aboutUsStage);
    }

    private void loadMainPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FurniturePackage/MainPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1550, 800);
            scene.getStylesheets().add(getClass().getResource("/FurniturePackage/styles.css").toExternalForm());
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}