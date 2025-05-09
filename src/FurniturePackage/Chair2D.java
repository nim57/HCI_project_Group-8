package FurniturePackage;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Chair2D {

   
    private static final int WINDOW_WIDTH = 1550;
    private static final int WINDOW_HEIGHT = 800;

    public Group createChairView() {
        // Load the images
        Image woodImage = new Image(getClass().getResourceAsStream("/resources/bg2.jpeg"));
        ImagePattern woodPattern = new ImagePattern(woodImage);

        // Load the background image
        Image bgImage = new Image(getClass().getResourceAsStream("/resources/bgb.jpg")); // Adjust path as necessary
        ImagePattern bgPattern = new ImagePattern(bgImage);

        Group root = new Group();

        // Create a rectangle 
        Rectangle background = new Rectangle(WINDOW_WIDTH, WINDOW_HEIGHT);
        background.setFill(bgPattern);

        // Chair components
        Rectangle seat = new Rectangle(200, 40);
        seat.setFill(woodPattern);

        Rectangle backrest = new Rectangle(160, 200);
        backrest.setFill(woodPattern);

        Rectangle leg1 = new Rectangle(40, 100);
        leg1.setFill(woodPattern);

        Rectangle leg2 = new Rectangle(40, 100);
        leg2.setFill(woodPattern);

        // Calculate center position for the chair
        double centerX = (WINDOW_WIDTH - seat.getWidth()) / 2;
        double centerY = (WINDOW_HEIGHT - (seat.getHeight() + backrest.getHeight())) / 2 + backrest.getHeight();

        // Set positions relative to the center
        seat.setX(centerX);
        seat.setY(centerY);

        backrest.setX(centerX + (seat.getWidth() - backrest.getWidth()) / 2);
        backrest.setY(centerY - backrest.getHeight());

        leg1.setX(centerX);
        leg1.setY(centerY + seat.getHeight());

        leg2.setX(centerX + seat.getWidth() - leg2.getWidth());
        leg2.setY(centerY + seat.getHeight());

        
        root.getChildren().add(background);

        root.getChildren().addAll(seat, backrest, leg1, leg2);

        return root;
    }
}
