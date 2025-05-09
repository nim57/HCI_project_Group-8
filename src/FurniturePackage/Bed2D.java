package FurniturePackage;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Bed2D extends Application {

    // Window dimensions for centering
    private static final int WINDOW_WIDTH = 1550;
    private static final int WINDOW_HEIGHT = 800;

    @Override
    public void start(Stage primaryStage) {
        Group root = createBedView();

        // Load and set the background image
        Image backgroundImage = new Image(Bed2D.class.getResourceAsStream("/resources/bg10.jpg"));
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(WINDOW_WIDTH);
        backgroundView.setFitHeight(WINDOW_HEIGHT);
        root.getChildren().add(0, backgroundView); // Ensure the background is added first

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        primaryStage.setTitle("2D Bed");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Group createBedView() {
        Image woodImage = new Image(Bed2D.class.getResourceAsStream("/resources/wood1.jpg"));
        ImagePattern woodPattern = new ImagePattern(woodImage);

        Group root = new Group();

        
        Rectangle mattress = new Rectangle(400, 200); // Larger dimension to suggest depth
        mattress.setFill(woodPattern);

        // Center the mattress 
        double centerX = (WINDOW_WIDTH - mattress.getWidth()) / 2;
        double centerY = (WINDOW_HEIGHT - mattress.getHeight()) / 2;

        mattress.setX(centerX);
        mattress.setY(centerY);

        // Bed legs
        Rectangle legFrontLeft = new Rectangle(30, 80);
        legFrontLeft.setFill(woodPattern);
        legFrontLeft.setX(mattress.getX());
        legFrontLeft.setY(mattress.getY() + mattress.getHeight());

        Rectangle legFrontRight = new Rectangle(30, 80);
        legFrontRight.setFill(woodPattern);
        legFrontRight.setX(mattress.getX() + mattress.getWidth() - legFrontRight.getWidth());
        legFrontRight.setY(mattress.getY() + mattress.getHeight());

        // Rear legs 
        Rectangle legRearLeft = new Rectangle(15, 40);
        legRearLeft.setFill(woodPattern);
        legRearLeft.setX(mattress.getX() - 10); // Slightly offset to the left for depth illusion
        legRearLeft.setY(mattress.getY() + mattress.getHeight() - 10); // Slightly higher to suggest depth

        Rectangle legRearRight = new Rectangle(15, 40);
        legRearRight.setFill(woodPattern);
        legRearRight.setX(mattress.getX() + mattress.getWidth() - legRearRight.getWidth() + 10); // Offset to the right
        legRearRight.setY(mattress.getY() + mattress.getHeight() - 20); // Slightly higher

        // Headboard 
        Rectangle headboard = new Rectangle(60, mattress.getHeight());
        headboard.setFill(woodPattern);
        headboard.setX(centerX - headboard.getWidth());
        headboard.setY(centerY);

        // Footboard 
        Rectangle footboard = new Rectangle(50, mattress.getHeight() - 0); 
        footboard.setFill(woodPattern);
        footboard.setX(centerX + mattress.getWidth());
        footboard.setY(centerY + 0); // Lower to suggest perspective

        root.getChildren().addAll(mattress, legFrontLeft, legFrontRight, legRearLeft, legRearRight, headboard, footboard);

        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
