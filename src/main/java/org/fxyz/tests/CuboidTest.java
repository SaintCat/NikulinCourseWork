package org.fxyz.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.fxyz.cameras.CameraTransformer;
import org.fxyz.geometry.Point3D;
import org.fxyz.shapes.composites.PolyLine3D;
import org.fxyz.shapes.primitives.CuboidMesh;
import org.fxyz.utils.DensityFunction;

/**
 *
 * @author jpereda
 */
public class CuboidTest extends Application {

    private PerspectiveCamera camera;
    private final double sceneWidth = 600;
    private final double sceneHeight = 600;
    private final CameraTransformer cameraTransform = new CameraTransformer();

    private double mousePosX;
    private double mousePosY;
    private double mouseOldX;
    private double mouseOldY;
    private double mouseDeltaX;
    private double mouseDeltaY;
    private CuboidMesh cuboid;
    private CuboidMesh cuboid2;
    private CuboidMesh cuboid3;
    private CuboidMesh flat;
    private CuboidMesh flat2;
    private CuboidMesh flat3;
    private CuboidMesh flat4;
    private Point3D floatNormalVector = new Point3D(0, 0, 1);
    private Rotate rotateY;
    private long lastEffect;

    private DensityFunction<Point3D> dens = p -> (double) p.x;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group sceneRoot = new Group();
        Scene scene = new Scene(sceneRoot, sceneWidth, sceneHeight, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.WHITE);
        camera = new PerspectiveCamera(true);

        //setup camera transform for rotational support
        cameraTransform.setTranslate(0, 0, 0);
        cameraTransform.getChildren().add(camera);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-50);
        cameraTransform.ry.setAngle(-45.0);
        cameraTransform.rx.setAngle(-10.0);
        //add a Point Light for better viewing of the grid coordinate system
        PointLight light = new PointLight(Color.WHITE);
        cameraTransform.getChildren().add(light);
        light.setTranslateX(camera.getTranslateX());
        light.setTranslateY(camera.getTranslateY());
        light.setTranslateZ(10 * camera.getTranslateZ());
        scene.setCamera(camera);

        rotateY = new Rotate(0, Rotate.Y_AXIS);
        Group group = new Group();
        group.getChildren().add(cameraTransform);
        cuboid = new CuboidMesh(5f, 10f, 5f, 1);
        cuboid.setDrawMode(DrawMode.FILL);
        cuboid.setCullFace(CullFace.BACK);
        // NONE
        cuboid.setTextureModeNone(Color.ROYALBLUE);
//        cuboid.translateXCoor(7);
//        cuboid.translateYCoor(-3);
        // IMAGE
//        cuboid.setTextureModeImage(getClass().getResource("res/netCuboid.png").toExternalForm());
        // DENSITY
//        cuboid.setTextureModeVertices3D(256*256,p->(double)p.x*p.y*p.z);
        // FACES
        cuboid.setTextureModeFaces(1530);

        cuboid.getTransforms().addAll(rotateY);
        rotateY = new Rotate(0, Rotate.X_AXIS);
        cuboid.getTransforms().add(rotateY);
        group.getChildren().add(cuboid);
        final Timeline bannerEffect = new Timeline();
        bannerEffect.setCycleCount(Timeline.INDEFINITE);
        final KeyValue kv1 = new KeyValue(rotateY.angleProperty(), 360);
        final KeyFrame kf1 = new KeyFrame(Duration.millis(10000), kv1);
        bannerEffect.getKeyFrames().addAll(kf1);
//        bannerEffect.play();

        cuboid2 = new CuboidMesh(10f, 5f, 5f, 1);
        cuboid2.translateYCoor(-2.5);
        cuboid2.translateXCoor(7.5);
        cuboid2.setDrawMode(DrawMode.FILL);
        cuboid2.setCullFace(CullFace.BACK);
        // NONE
        cuboid2.setTextureModeNone(Color.ROYALBLUE);
        // IMAGE
//        cuboid.setTextureModeImage(getClass().getResource("res/netCuboid.png").toExternalForm());
        // DENSITY
//        cuboid.setTextureModeVertices3D(256*256,p->(double)p.x*p.y*p.z);
        // FACES
        cuboid2.setTextureModeFaces(1530);
        group.getChildren().addAll(cuboid2);
        List<Point3D> list = new ArrayList<>();
        list.add(new Point3D(0, 0, 0));
        list.add(new Point3D(100, 0, 0));
        PolyLine3D line = new PolyLine3D(list, 1, Color.RED);
        group.getChildren().add(line);
        Rotate rotateFirst = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
        Rotate rotateSecond = new Rotate(0, -7, 0, 0, Rotate.Y_AXIS);
        cuboid2.getTransforms().add(rotateFirst);
        cuboid.getTransforms().add(rotateSecond);
        final Timeline bannerEffect2 = new Timeline();
        bannerEffect2.setCycleCount(Timeline.INDEFINITE);
        final KeyValue kv12 = new KeyValue(rotateFirst.angleProperty(), 360);
        final KeyValue kv13 = new KeyValue(rotateSecond.angleProperty(), 360);
        final KeyFrame kf12 = new KeyFrame(Duration.millis(10000), kv12, kv13, kv1);
        bannerEffect2.getKeyFrames().addAll(kf12);
//        bannerEffect2.play();

        cuboid3 = new CuboidMesh(5f, 5f, 10f, 1);
        cuboid3.translateYCoor(-2.5);
        cuboid3.translateXCoor(15);
        cuboid3.translateZCoor(2.5);
        cuboid3.setDrawMode(DrawMode.FILL);
        cuboid3.setCullFace(CullFace.BACK);
        // NONE
        cuboid3.setTextureModeNone(Color.ROYALBLUE);
        cuboid3.setTextureModeFaces(1530);
        group.getChildren().add(cuboid3);
        sceneRoot.getChildren().addAll(group);

        flat = new CuboidMesh(0.2, 0.2, 40);
        flat.setTextureModeNone(Color.RED);
        flat.setTranslateX(20);
//        flat.translateXCoor(20);
        group.getChildren().add(flat);
        flat2 = new CuboidMesh(0.2, 0.2, 40);
        flat2.setTextureModeNone(Color.RED);
        flat2.setTranslateX(-20);
//        flat2.translateXCoor(-20);
        group.getChildren().add(flat2);
        flat3 = new CuboidMesh(40, 0.2, 0.2);
        flat3.setTextureModeNone(Color.RED);
        flat3.setTranslateZ(0);
        flat3.translateZCoor(20);
        group.getChildren().add(flat3);
        flat4 = new CuboidMesh(40, 0.2, 0.2);
        flat4.setTextureModeNone(Color.RED);
        flat4.setTranslateZ(0);
        flat4.translateZCoor(-20);
        group.getChildren().add(flat4);
//
//        flat.updateFigureMesh();
//        flat2.updateFigureMesh();
//        flat3.updateFigureMesh();
//        flat4.updateFigureMesh();
        //First person shooter keyboard movement 
        scene.setOnKeyPressed(event -> {
            double change = 10.0;
            //Add shift modifier to simulate "Running Speed"
            if (event.isShiftDown()) {
                change = 50.0;
            }
            //What key did the user press?
            KeyCode keycode = event.getCode();
            //Step 2c: Add Zoom controls
            if (keycode == KeyCode.W) {
                camera.setTranslateZ(camera.getTranslateZ() + change);
            }
            if (keycode == KeyCode.S) {
                camera.setTranslateZ(camera.getTranslateZ() - change);
            }
            //Step 2d:  Add Strafe controls
            if (keycode == KeyCode.A) {
                camera.setTranslateX(camera.getTranslateX() - change);
            }
            if (keycode == KeyCode.D) {
                camera.setTranslateX(camera.getTranslateX() + change);
            }
        });

        scene.setOnMousePressed((MouseEvent me) -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged((MouseEvent me) -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);

            double modifier = 10.0;
            double modifierFactor = 0.1;

            if (me.isControlDown()) {
                modifier = 0.1;
            }
            if (me.isShiftDown()) {
                modifier = 50.0;
            }
            if (me.isPrimaryButtonDown()) {
                cameraTransform.ry.setAngle(((cameraTransform.ry.getAngle() + mouseDeltaX * modifierFactor * modifier * 2.0) % 360 + 540) % 360 - 180);  // +
                cameraTransform.rx.setAngle(((cameraTransform.rx.getAngle() - mouseDeltaY * modifierFactor * modifier * 2.0) % 360 + 540) % 360 - 180);  // -
            } else if (me.isSecondaryButtonDown()) {
                double z = camera.getTranslateZ();
                double newZ = z + mouseDeltaX * modifierFactor * modifier;
                camera.setTranslateZ(newZ);
            } else if (me.isMiddleButtonDown()) {
                cameraTransform.t.setX(cameraTransform.t.getX() + mouseDeltaX * modifierFactor * modifier * 0.3);  // -
                cameraTransform.t.setY(cameraTransform.t.getY() + mouseDeltaY * modifierFactor * modifier * 0.3);  // -
            }
        });

        cuboid.updateFigureMesh();
        cuboid2.updateFigureMesh();
        cuboid3.updateFigureMesh();
        lastEffect = System.nanoTime();
        AtomicInteger count = new AtomicInteger();
        AnimationTimer timerEffect = new AnimationTimer() {

            @Override
            public void handle(long now) {
                if (now > lastEffect + 1_00l) {
                    cuboid.getTransforms().add(new Rotate(0.3, 0, 0, 0, Rotate.Y_AXIS));
                    cuboid2.getTransforms().add(new Rotate(0.3, 0, 0, 0, Rotate.Y_AXIS));
                    cuboid3.getTransforms().add(new Rotate(0.3, 0, 0, 0, Rotate.Y_AXIS));
                    firstAngle += 0.3;

                    Point3D p = new Point3D(1, 0, 0);
//                    .rotPoint(p, firstAngle);
                    p = p.normalize();
                    cuboid2.translateYCoor(2.5);
                    cuboid2.rotateAroundAxis(p, 0.03);
                    cuboid2.translateYCoor(-2.5);
                    cuboid3.translateYCoor(2.5);
                    cuboid3.rotateAroundAxis(p, 0.03);
                    cuboid3.translateYCoor(-2.5);
                    count.getAndIncrement();

                    cuboid3.translateYCoor(2.5);
                    Point3D z = new Point3D(1, 0, 0);
                    z = z.normalize();
                    cuboid3.rotateAroundAxis(z, 0.03);
                    cuboid3.translateYCoor(-2.5);

                    cuboid2.updateFigureMesh();
                    cuboid3.updateFigureMesh();

                    //rotate plat 
                    Point3D oX = new Point3D(1, 0, 0);
                    flat.rotateAroundAxis(oX, 0.03);
                    flat2.rotateAroundAxis(oX, 0.03);
                    flat3.rotateAroundAxis(oX, 0.03);
                    flat4.rotateAroundAxis(oX, 0.03);
                    MatrixOperations.rotPoint(floatNormalVector, 0.03);
                    flat.updateFigureMesh();
                    flat2.updateFigureMesh();
                    flat3.updateFigureMesh();
                    flat4.updateFigureMesh();
//                    flat.getTransforms().add(new Rotate(0.3, Rotate.X_AXIS));
//                    flat2.getTransforms().add(new Rotate(0.3, Rotate.X_AXIS));
//                    flat3.getTransforms().add(new Rotate(0.3, Rotate.Z_AXIS));
//                    flat4.getTransforms().add(new Rotate(0.3, Rotate.Z_AXIS));
                    lastEffect = now;
                }
            }
        };
        timerEffect.start();
        primaryStage.setTitle("F(X)yz - Cuboid Test");
        primaryStage.setScene(scene);
        primaryStage.show();

//        writer.setMaterialColor(Color.AQUA);
//        writer.setTextureImage(getClass().getResource("res/netCuboid.png").toExternalForm());
    }
    private double firstAngle = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Point3D q = new Point3D(20,20, -20);
        Point3D U = new Point3D(0,0,1);
        Point3D res  = CuboidMesh.intersectFlatAndLine(q, U, new Point3D(0,0,0), new Point3D(0,1,0));
        System.out.println(res==null?"null" : res.toString());
//        launch(args);
    }
}
