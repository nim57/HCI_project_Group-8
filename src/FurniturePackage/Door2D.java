package FurniturePackage;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Door2D {

    
    private static final int WINDOW_WIDTH = 1550;
    private static final int WINDOW_HEIGHT = 800;

    public static Group createDoorView() {
        // Load images
        Image woodImage = new Image(Door2D.class.getResourceAsStream("/resources/metal.jpg"));
        Image backgroundImage = new Image(Door2D.class.getResourceAsStream("/resources/bg10.jpg"));
        ImagePattern woodPattern = new ImagePattern(woodImage);

        Group root = new Group();

        // Create background
        ImageView backgroundView = new ImageView(backgroundImage);
        // Set the background size to fill the window
        backgroundView.setFitWidth(WINDOW_WIDTH);
        backgroundView.setFitHeight(WINDOW_HEIGHT);

        // Create the door panel with wood texture
        Rectangle doorPanel = new Rectangle(200, 400); // Adjust dimensions as needed
        doorPanel.setFill(woodPattern);

        // Create the door frame slightly larger than the door panel
        Rectangle doorFrame = new Rectangle(210, 410); // Adjust dimensions as needed
        doorFrame.setFill(woodPattern);

        // Calculate center position for the door
        double centerX = (WINDOW_WIDTH - doorPanel.getWidth()) / 2;
        double centerY = (WINDOW_HEIGHT - doorPanel.getHeight()) / 2;

        // Set positions relative to the center
        doorPanel.setX(centerX);
        doorPanel.setY(centerY);

        doorFrame.setX(centerX - 5); 
        doorFrame.setY(centerY - 5); 

        // Add elements to the root group in proper order
        root.getChildren().add(backgroundView); 
        root.getChildren().add(doorFrame); 
        root.getChildren().add(doorPanel); 

        return root;
    }
}
