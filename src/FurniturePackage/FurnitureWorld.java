package FurniturePackage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.transform.*;
import javafx.stage.*;
import java.io.File;

public class FurnitureWorld extends Application {

    private static final float WIDTH = 1550;
    private static final float HEIGHT = 800;
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private Group furnitureGroup = new Group();
    private PhongMaterial backgroundMaterial = new PhongMaterial();
    private SmartGroup currentFurniture;
    private double mousePosX, mousePosY;
    private double mouseOldX, mouseOldY;

    // The method that was being called from DashboardApp
    public void launchFurnitureWorld(Stage primaryStage) {
        BorderPane root = new BorderPane();
        prepareBackground(root);

        VBox controlPanel = createControlPanel(primaryStage);
        root.setRight(controlPanel);

        HBox navigationPanel = createNavigationPanel(primaryStage);
        root.setTop(navigationPanel);

        furnitureGroup = new Group();
        root.setCenter(furnitureGroup);

        Camera camera = new PerspectiveCamera();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setFill(Color.web("#2D3447"));
        scene.setCamera(camera);

        initMouseControl(scene, primaryStage);

        primaryStage.setTitle("3D Furniture World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        launchFurnitureWorld(primaryStage); // Standard JavaFX entry point
    }

    private VBox createControlPanel(Stage primaryStage) {
        VBox controlPanel = new VBox(20);
        controlPanel.setStyle("-fx-background-color: #3A4466; -fx-padding: 20px;");
        controlPanel.setPrefWidth(250);

        Text title = new Text("Controls");
        title.setFont(Font.font("Arial", 20));
        title.setFill(Color.WHITE);

        Text sizeLabel = new Text("Size Adjustment");
        sizeLabel.setFont(Font.font("Arial", 14));
        sizeLabel.setFill(Color.WHITE);

        Slider sizeSlider = new Slider(0.5, 2.0, 1.0);
        sizeSlider.setShowTickLabels(true);
        sizeSlider.setShowTickMarks(true);
        sizeSlider.setMajorTickUnit(0.5);
        sizeSlider.setMinorTickCount(1);
        sizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (currentFurniture != null) {
                currentFurniture.setScaleX(newVal.doubleValue());
                currentFurniture.setScaleY(newVal.doubleValue());
                currentFurniture.setScaleZ(newVal.doubleValue());
            }
        });

        Button changeBackgroundButton = new Button("Change Background");
        changeBackgroundButton.setOnAction(event -> changeBackground(primaryStage));
        styleButton(changeBackgroundButton);

        Text colorLabel = new Text("Furniture Color");
        colorLabel.setFont(Font.font("Arial", 14));
        colorLabel.setFill(Color.WHITE);

        ColorPicker colorPicker = new ColorPicker(Color.WHITE);
        colorPicker.setOnAction(event -> {
            Color newColor = colorPicker.getValue();
            changeColorOfCurrentFurniture(newColor);
        });

        Text instructions = new Text("Instructions:\n" +
                "- Left drag: Rotate\n" +
                "- Right drag: Move\n" +
                "- Scroll: Zoom\n" +
                "- Use slider to resize");
        instructions.setFont(Font.font("Arial", 12));
        instructions.setFill(Color.LIGHTGRAY);

        controlPanel.getChildren().addAll(
                title, sizeLabel, sizeSlider,
                colorLabel, colorPicker,
                changeBackgroundButton, instructions
        );

        return controlPanel;
    }

    private HBox createNavigationPanel(Stage primaryStage) {
        HBox navigationPanel = new HBox(10);
        navigationPanel.setStyle("-fx-background-color: linear-gradient(to right, #3A4466, #4A5A7F); " +
                "-fx-padding: 15px; -fx-alignment: center;");

        String[] furnitureTypes = {"Chair", "Table", "Bed", "Cupboard", "Rounded Table", "Bookshelf", "Stool"};
        for (String type : furnitureTypes) {
            Button button = new Button(type);
            button.setOnAction(event -> {
                switch (type) {
                    case "Chair": displayChair(); break;
                    case "Table": displayTable(); break;
                    case "Bed": displayBed(); break;
                    case "Cupboard": displayCupboard(); break;
                    case "Rounded Table": displayRoundedTable(); break;
                    case "Bookshelf": displayBookshelf(); break;
                    case "Stool": displayDesk(); break;
                }
            });
            styleButton(button);
            navigationPanel.getChildren().add(button);
        }

        return navigationPanel;
    }

    private void changeBackground(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Background Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            backgroundMaterial.setDiffuseMap(new Image(selectedFile.toURI().toString()));
        }
    }

    private void changeColorOfCurrentFurniture(Color color) {
        PhongMaterial newMaterial = new PhongMaterial(color);
        if (currentFurniture != null) {
            currentFurniture.getChildren().forEach(node -> {
                if (node instanceof Box) {
                    ((Box) node).setMaterial(newMaterial);
                } else if (node instanceof Cylinder) {
                    ((Cylinder) node).setMaterial(newMaterial);
                }
            });
        }
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: linear-gradient(to bottom, #5D6B99, #3A4466); " +
                "-fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-padding: 8px 16px; -fx-background-radius: 5px; " +
                "-fx-border-radius: 5px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 1);");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: linear-gradient(to bottom, #6D7BA9, #4A5A7F); " +
                "-fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-padding: 8px 16px; -fx-background-radius: 5px; " +
                "-fx-border-radius: 5px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 8, 0, 0, 2);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: linear-gradient(to bottom, #5D6B99, #3A4466); " +
                "-fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-padding: 8px 16px; -fx-background-radius: 5px; " +
                "-fx-border-radius: 5px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 1);"));
    }

    private void prepareBackground(BorderPane root) {
        backgroundMaterial.setDiffuseColor(Color.web("#4A5A7F"));
        Box background = new Box(WIDTH * 3, HEIGHT * 3, 1);
        background.setMaterial(backgroundMaterial);
        background.setTranslateZ(1500);
        root.getChildren().add(background);
    }

    private void displayChair() {
        furnitureGroup.getChildren().clear();
        currentFurniture = new SmartGroup();
        prepareChair(currentFurniture);
        furnitureGroup.getChildren().add(currentFurniture);
        positionFurniture();
    }

    private void displayTable() {
        furnitureGroup.getChildren().clear();
        currentFurniture = new SmartGroup();
        prepareTable(currentFurniture);
        furnitureGroup.getChildren().add(currentFurniture);
        positionFurniture();
    }

    private void displayBed() {
        furnitureGroup.getChildren().clear();
        currentFurniture = new SmartGroup();
        prepareBed(currentFurniture);
        furnitureGroup.getChildren().add(currentFurniture);
        positionFurniture();
    }

    private void displayCupboard() {
        furnitureGroup.getChildren().clear();
        currentFurniture = new SmartGroup();
        prepareCupboard(currentFurniture);
        furnitureGroup.getChildren().add(currentFurniture);
        positionFurniture();
    }

    private void displayRoundedTable() {
        furnitureGroup.getChildren().clear();
        currentFurniture = new SmartGroup();
        prepareRoundedTable(currentFurniture);
        furnitureGroup.getChildren().add(currentFurniture);
        positionFurniture();
    }

    private void displayBookshelf() {
        furnitureGroup.getChildren().clear();
        currentFurniture = new SmartGroup();
        prepareBookshelf(currentFurniture);
        furnitureGroup.getChildren().add(currentFurniture);
        positionFurniture();
    }

    private void displayDesk() {
        furnitureGroup.getChildren().clear();
        currentFurniture = new SmartGroup();
        prepareDesk(currentFurniture);
        furnitureGroup.getChildren().add(currentFurniture);
        positionFurniture();
    }

    private void positionFurniture() {
        if (currentFurniture != null) {
            currentFurniture.setTranslateX(WIDTH / 2);
            currentFurniture.setTranslateY(HEIGHT / 2);
            currentFurniture.setTranslateZ(-800);
            currentFurniture.setScaleX(1.0);
            currentFurniture.setScaleY(1.0);
            currentFurniture.setScaleZ(1.0);
        }
    }

    private void prepareChair(SmartGroup group) {
        PhongMaterial material = new PhongMaterial(Color.web("#8B4513"));

        Box seat = new Box(100, 10, 80);
        seat.setMaterial(material);
        seat.setTranslateY(-5);

        Box backrest = new Box(100, 120, 5);
        backrest.setMaterial(material);
        backrest.setTranslateY(-60);
        backrest.setTranslateZ(-38);

        Box[] legs = new Box[4];
        for (int i = 0; i < legs.length; i++) {
            legs[i] = new Box(10, 50, 10);
            legs[i].setMaterial(material);
            legs[i].setTranslateY(25);
        }
        legs[0].setTranslateX(-45);
        legs[0].setTranslateZ(30);
        legs[1].setTranslateX(45);
        legs[1].setTranslateZ(30);
        legs[2].setTranslateX(-45);
        legs[2].setTranslateZ(-30);
        legs[3].setTranslateX(45);
        legs[3].setTranslateZ(-30);

        group.getChildren().addAll(seat, backrest);
        group.getChildren().addAll(legs);
    }

    private void prepareTable(SmartGroup group) {
        PhongMaterial material = new PhongMaterial(Color.web("#CD853F"));

        Box tabletop = new Box(200, 10, 100);
        tabletop.setMaterial(material);
        tabletop.setTranslateY(-15);

        Box[] legs = new Box[4];
        for (int i = 0; i < legs.length; i++) {
            legs[i] = new Box(10, 100, 10);
            legs[i].setMaterial(material);
            legs[i].setTranslateY(40);
        }
        legs[0].setTranslateX(-90);
        legs[0].setTranslateZ(40);
        legs[1].setTranslateX(90);
        legs[1].setTranslateZ(40);
        legs[2].setTranslateX(-90);
        legs[2].setTranslateZ(-40);
        legs[3].setTranslateX(90);
        legs[3].setTranslateZ(-40);

        group.getChildren().addAll(tabletop);
        group.getChildren().addAll(legs);
    }

    private void prepareBed(SmartGroup group) {
        PhongMaterial material = new PhongMaterial(Color.web("#4169E1"));

        Box mattress = new Box(150, 15, 250);
        mattress.setMaterial(material);
        mattress.setTranslateY(-15);

        Box mattress2 = new Box(150, 20, 250);
        mattress2.setMaterial(material);
        mattress2.setTranslateY(15);

        Box headboard = new Box(150, 90, 10);
        headboard.setMaterial(material);
        headboard.setTranslateY(5);
        headboard.setTranslateZ(-130);

        Box footboard = new Box(150, 70, 10);
        footboard.setMaterial(material);
        footboard.setTranslateY(10);
        footboard.setTranslateZ(120);

        group.getChildren().addAll(mattress, mattress2, headboard, footboard);
    }

    private void prepareCupboard(SmartGroup group) {
        PhongMaterial material = new PhongMaterial(Color.web("#A0522D"));

        Box body = new Box(200, 300, 50);
        body.setMaterial(material);

        Box[] shelves = new Box[3];
        for (int i = 0; i < shelves.length; i++) {
            shelves[i] = new Box(190, 5, 40);
            shelves[i].setMaterial(material);
        }
        shelves[0].setTranslateY(-75);
        shelves[1].setTranslateY(0);
        shelves[2].setTranslateY(75);

        Box door1 = new Box(95, 290, 2);
        door1.setMaterial(material);
        door1.setTranslateX(-52.5);
        door1.setTranslateZ(-26);

        Box door2 = new Box(95, 290, 2);
        door2.setMaterial(material);
        door2.setTranslateX(52.5);
        door2.setTranslateZ(-26);

        group.getChildren().addAll(body);
        group.getChildren().addAll(shelves);
        group.getChildren().addAll(door1, door2);
    }

    private void prepareRoundedTable(SmartGroup group) {
        PhongMaterial material = new PhongMaterial(Color.web("#DAA520"));

        Cylinder tabletop = new Cylinder(120, 10);
        tabletop.setMaterial(material);
        tabletop.setTranslateY(-20);

        Cylinder pedestal = new Cylinder(15, 90);
        pedestal.setMaterial(material);
        pedestal.setTranslateY(25);

        Cylinder base = new Cylinder(50, 20);
        base.setMaterial(material);
        base.setTranslateY(60);

        group.getChildren().addAll(tabletop, pedestal, base);
    }

    private void prepareBookshelf(SmartGroup group) {
        PhongMaterial material = new PhongMaterial(Color.web("#708090"));

        Box body = new Box(150, 200, 10);
        body.setMaterial(material);
        body.setTranslateY(-15);
        body.setTranslateZ(15);

        Box[] shelves = new Box[3];
        for (int i = 0; i < shelves.length; i++) {
            shelves[i] = new Box(140, 5, 40);
            shelves[i].setMaterial(material);
        }
        shelves[0].setTranslateY(-80);
        shelves[1].setTranslateY(-25);
        shelves[2].setTranslateY(30);

        Box leftPanel = new Box(10, 230, 40);
        leftPanel.setMaterial(material);
        leftPanel.setTranslateX(-70);

        Box rightPanel = new Box(10, 230, 40);
        rightPanel.setMaterial(material);
        rightPanel.setTranslateX(70);

        group.getChildren().addAll(body, leftPanel, rightPanel);
        group.getChildren().addAll(shelves);
    }

    private void prepareDesk(SmartGroup group) {
        PhongMaterial material = new PhongMaterial(Color.web("#696969"));

        Box surface = new Box(200, 5, 100);
        surface.setMaterial(material);
        surface.setTranslateY(-15);

        Box[] legs = new Box[4];
        for (int i = 0; i < legs.length; i++) {
            legs[i] = new Box(10, 30, 10);
            legs[i].setMaterial(material);
        }
        legs[0].setTranslateX(-90);
        legs[0].setTranslateZ(40);
        legs[1].setTranslateX(90);
        legs[1].setTranslateZ(40);
        legs[2].setTranslateX(-90);
        legs[2].setTranslateZ(-40);
        legs[3].setTranslateX(90);
        legs[3].setTranslateZ(-40);

        group.getChildren().addAll(surface);
        group.getChildren().addAll(legs);
    }

    private void initMouseControl(Scene scene, Stage stage) {
        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();

            mouseOldX = event.getSceneX();
            mouseOldY = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            if (event.isPrimaryButtonDown()) {
                angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
                angleY.set(anchorAngleY + anchorX - event.getSceneX());
            } else if (event.isSecondaryButtonDown() && currentFurniture != null) {
                double deltaX = event.getSceneX() - mouseOldX;
                double deltaY = event.getSceneY() - mouseOldY;
                currentFurniture.setTranslateX(currentFurniture.getTranslateX() + deltaX);
                currentFurniture.setTranslateY(currentFurniture.getTranslateY() + deltaY);
                mouseOldX = event.getSceneX();
                mouseOldY = event.getSceneY();
            }
        });

        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY();
            furnitureGroup.translateZProperty().set(furnitureGroup.getTranslateZ() + delta);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    class SmartGroup extends Group {
        SmartGroup() {
            Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
            Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
            xRotate.angleProperty().bind(angleX);
            yRotate.angleProperty().bind(angleY);
            this.getTransforms().addAll(xRotate, yRotate);
        }
    }
}