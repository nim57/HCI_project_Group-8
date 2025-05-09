package FurniturePackage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class login extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Main container with modern styling
        BorderPane mainContainer = new BorderPane();
        mainContainer.setStyle("-fx-background-color: #f8f9fa;");

        // Left side with brand image/logo
        StackPane leftPanel = new StackPane();
        leftPanel.setPrefWidth(450);
        leftPanel.setStyle("-fx-background-color: linear-gradient(to bottom right, #4b6cb7, #182848);");
        leftPanel.setAlignment(Pos.CENTER);

        // Brand content with logo
        VBox brandContent = new VBox(20);
        brandContent.setAlignment(Pos.CENTER);
        brandContent.setPadding(new Insets(40));
        brandContent.setMaxWidth(350);

        // Placeholder for logo (using text icon if image not available)
        Label logoPlaceholder = new Label("🛋️"); // Furniture emoji as fallback
        logoPlaceholder.setStyle("-fx-font-size: 80px;");

        Label brandName = new Label("FurniHome");
        brandName.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        brandName.setEffect(new DropShadow(10, Color.BLACK));

        Text tagline = new Text("Design Your Perfect Space");
        tagline.setFill(Color.WHITE);
        tagline.setFont(Font.font("Arial", 16));
        tagline.setTextAlignment(TextAlignment.CENTER);

        brandContent.getChildren().addAll(logoPlaceholder, brandName, tagline);
        leftPanel.getChildren().add(brandContent);

        // Right side with login form
        VBox rightPanel = new VBox(30);
        rightPanel.setPadding(new Insets(60, 80, 60, 80));
        rightPanel.setAlignment(Pos.CENTER);
        rightPanel.setPrefWidth(550);
        rightPanel.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 20, 0, 0, 0);");

        // Form header
        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);

        Label lblTitle = new Label("Welcome Back");
        lblTitle.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label lblSubtitle = new Label("Login to continue to Furniture Hub");
        lblSubtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        headerBox.getChildren().addAll(lblTitle, lblSubtitle);

        // Form fields
        VBox formBox = new VBox(20);
        formBox.setAlignment(Pos.CENTER);
        formBox.setMaxWidth(350);

        // Username field
        VBox usernameBox = new VBox(5);
        Label usernameLabel = new Label("Username");
        usernameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50;");

        TextField txtInput = new TextField();
        txtInput.setPromptText("Enter your username");
        txtInput.setStyle("-fx-font-size: 14px; -fx-pref-height: 45px; -fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #bdc3c7; -fx-border-width: 1px; -fx-padding: 0 15px;");
        usernameBox.getChildren().addAll(usernameLabel, txtInput);

        // Password field
        VBox passwordBox = new VBox(5);
        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50;");

        PasswordField txtpwd = new PasswordField();
        txtpwd.setPromptText("Enter your password");
        txtpwd.setStyle("-fx-font-size: 14px; -fx-pref-height: 45px; -fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #bdc3c7; -fx-border-width: 1px; -fx-padding: 0 15px;");
        passwordBox.getChildren().addAll(passwordLabel, txtpwd);

        // Remember me and forgot password
        HBox optionsBox = new HBox();
        optionsBox.setAlignment(Pos.CENTER_LEFT);

        CheckBox rememberMe = new CheckBox("Remember me");
        rememberMe.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 13px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Hyperlink forgotPassword = new Hyperlink("Forgot password?");
        forgotPassword.setStyle("-fx-text-fill: #3498db; -fx-font-size: 13px; -fx-border-color: transparent;");

        optionsBox.getChildren().addAll(rememberMe, spacer, forgotPassword);

        // Feedback label
        Label lblFeedback = new Label("");
        lblFeedback.setStyle("-fx-font-size: 14px; -fx-text-fill: #e74c3c; -fx-wrap-text: true; -fx-max-width: 350px;");

        // Login button
        Button btnLogin = new Button("Login");
        btnLogin.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: #4b6cb7; -fx-background-radius: 5px; -fx-pref-height: 50px; -fx-pref-width: 350px; -fx-cursor: hand;");
        btnLogin.setOnMouseEntered(e -> btnLogin.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: #3a56a0; -fx-background-radius: 5px; -fx-pref-height: 50px; -fx-pref-width: 350px; -fx-cursor: hand;"));
        btnLogin.setOnMouseExited(e -> btnLogin.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: #4b6cb7; -fx-background-radius: 5px; -fx-pref-height: 50px; -fx-pref-width: 350px; -fx-cursor: hand;"));

        // Sign up link
        HBox linkBox = new HBox(5);
        linkBox.setAlignment(Pos.CENTER);

        Label noAccountLabel = new Label("Don't have an account?");
        noAccountLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        Hyperlink signInLink = new Hyperlink("Sign Up");
        signInLink.setStyle("-fx-font-size: 14px; -fx-text-fill: #3498db; -fx-font-weight: bold; -fx-border-color: transparent;");

        linkBox.getChildren().addAll(noAccountLabel, signInLink);

        // Add all components to form
        formBox.getChildren().addAll(usernameBox, passwordBox, optionsBox, lblFeedback, btnLogin, linkBox);
        rightPanel.getChildren().addAll(headerBox, formBox);

        // Add panels to main container
        mainContainer.setLeft(leftPanel);
        mainContainer.setCenter(rightPanel);

        // Maintain existing functionality
        txtInput.textProperty().addListener((observable, oldValue, newValue) -> {
            lblFeedback.setText("Input: " + newValue);
        });

        btnLogin.setOnAction(e -> {
            System.out.println("Login button clicked...");
            String username = txtInput.getText();
            String password = txtpwd.getText();

            // Validate input
            if (username.isEmpty() || password.isEmpty()) {
                lblFeedback.setText("Please enter both username and password");
                lblFeedback.setTextFill(Color.RED);
                return;
            }

            // Authenticate user
            DatabaseConnection dbConnection = new DatabaseConnection();
            boolean isAuthenticated = dbConnection.authenticateUser(username, password);
            dbConnection.closeConnection();

            if (isAuthenticated) {
                lblFeedback.setText("Login successful!");
                lblFeedback.setTextFill(Color.GREEN);
                opendash(primaryStage, username);
            } else {
                lblFeedback.setText("Invalid username or password");
                lblFeedback.setTextFill(Color.RED);
            }
        });

        signInLink.setOnAction(e -> {
            System.out.println("Sign In link clicked...");
            openSignInPage(primaryStage);
        });

        forgotPassword.setOnAction(e -> {
            System.out.println("Forgot password link clicked...");
            // Add your forgot password functionality here
        });

        Scene scene = new Scene(mainContainer, 1000, 600);
        primaryStage.setTitle("Login to Furniture Hub");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(550);
        primaryStage.show();
    }

    private void openSignInPage(Stage primaryStage) {
        SignIn signIn = new SignIn();
        signIn.start(primaryStage);
    }

    private void opendash(Stage primaryStage, String username) {
        DashboardApp Dash = new DashboardApp();
        Dash.setUsername(username);
        Dash.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}