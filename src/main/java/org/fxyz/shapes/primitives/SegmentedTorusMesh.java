package org.fxyz.shapes.primitives;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.DepthTest;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import org.fxyz.geometry.Face3;
import org.fxyz.geometry.Point3D;
import org.fxyz.tests.MatrixOperations;

/**
 * SegmentedTorusMesh is based in TorusMesh, but allows cutting the torus in two
 * directions, in order to have a banner parallel to an uncut torus. Based on a
 * regular 2D TriangleMesh, mapped to a 3D mesh with the torus parametric
 * equations Crop allows cutting/cropping the 2D mesh on the borders If crop ==0
 * then a regular torus is formed (thought with slight differences from
 * TorusMesh)
 */
public class SegmentedTorusMesh extends TexturedMesh {

    private static final int DEFAULT_MAJOR_DIVISIONS = 1;
    private static final int DEFAULT_MINOR_DIVISIONS = 1;
    private static final int DEFAULT_MAJOR_CROP = 0;
    private static final double DEFAULT_MAJOR_RADIUS = 12.5D;
    private static final double DEFAULT_MINOR_RADIUS = 5.0D;
    private static final double DEFAULT_START_ANGLE = 0.0D;
    private static final double DEFAULT_X_OFFSET = 0.0D;
    private static final double DEFAULT_Y_OFFSET = 0.0D;
    private static final double DEFAULT_Z_OFFSET = 1.0D;
    private Map<Float, List<Point3D>> values;

    public SegmentedTorusMesh() {
        this(DEFAULT_MAJOR_DIVISIONS, DEFAULT_MINOR_DIVISIONS, DEFAULT_MAJOR_CROP, DEFAULT_MAJOR_RADIUS, DEFAULT_MINOR_RADIUS);
    }

    public SegmentedTorusMesh(double majorRadius, double minorRadius) {
        this(DEFAULT_MAJOR_DIVISIONS, DEFAULT_MINOR_DIVISIONS, DEFAULT_MAJOR_CROP, majorRadius, minorRadius);
    }

    public SegmentedTorusMesh(int rDivs, int tDivs, int crop, double majorRadius, double minorRadius) {
        setMajorRadiusDivisions(rDivs);
        setMinorRadiusDivisions(tDivs);
        setMajorRadiusCrop(crop);
        setMajorRadius(majorRadius);
        setMinorRadius(minorRadius);
//        setTextureModeFaces(256 * 256);
        updateMesh();
        setCullFace(CullFace.BACK);
        setDrawMode(DrawMode.FILL);
        setDepthTest(DepthTest.ENABLE);
    }

    @Override
    protected final void updateMesh() {
        setMesh(null);
        mesh = createTorus(
                getMajorRadiusDivisions(),
                getMinorRadiusDivisions(),
                getMajorRadiusCrop(),
                (float) getMajorRadius(),
                (float) getMinorRadius(),
                (float) getTubeStartAngleOffset(),
                (float) getxOffset(),
                (float) getyOffset(),
                (float) getzOffset());
        setMesh(mesh);
    }

    private final IntegerProperty majorRadiusDivisions = new SimpleIntegerProperty(DEFAULT_MAJOR_DIVISIONS) {

        @Override
        protected void invalidated() {
            if (mesh != null) {
                updateMesh();
            }
        }

    };

    public final int getMajorRadiusDivisions() {
        return majorRadiusDivisions.get();
    }

    public final void setMajorRadiusDivisions(int value) {
        majorRadiusDivisions.set(value);
    }

    public IntegerProperty majorRadiusDivisionsProperty() {
        return majorRadiusDivisions;
    }

    private final IntegerProperty minorRadiusDivisions = new SimpleIntegerProperty(DEFAULT_MINOR_DIVISIONS) {

        @Override
        protected void invalidated() {
            if (mesh != null) {
                updateMesh();
            }
        }

    };

    public final int getMinorRadiusDivisions() {
        return minorRadiusDivisions.get();
    }

    public final void setMinorRadiusDivisions(int value) {
        minorRadiusDivisions.set(value);
    }

    public IntegerProperty minorRadiusDivisionsProperty() {
        return minorRadiusDivisions;
    }

    private final IntegerProperty majorRadiusCrop = new SimpleIntegerProperty(DEFAULT_MAJOR_CROP) {

        @Override
        protected void invalidated() {
            if (mesh != null) {
                updateMesh();
            }
        }

    };

    public final int getMajorRadiusCrop() {
        return majorRadiusCrop.get();
    }

    public final void setMajorRadiusCrop(int value) {
        majorRadiusCrop.set(value);
    }

    public IntegerProperty majorRadiusCropProperty() {
        return majorRadiusCrop;
    }

    private final DoubleProperty majorRadius = new SimpleDoubleProperty(DEFAULT_MAJOR_RADIUS) {

        @Override
        protected void invalidated() {
            if (mesh != null) {
                updateMesh();
            }
        }

    };

    public final double getMajorRadius() {
        return majorRadius.get();
    }

    public final void setMajorRadius(double value) {
        majorRadius.set(value);
    }

    public DoubleProperty radiusMajorProperty() {
        return majorRadius;
    }

    private final DoubleProperty minorRadius = new SimpleDoubleProperty(DEFAULT_MINOR_RADIUS) {

        @Override
        protected void invalidated() {
            if (mesh != null) {
                updateMesh();
            }
        }

    };

    public final double getMinorRadius() {
        return minorRadius.get();
    }

    public final void setMinorRadius(double value) {
        minorRadius.set(value);
    }

    public DoubleProperty minorRadiusProperty() {
        return minorRadius;
    }

    private final DoubleProperty tubeStartAngleOffset = new SimpleDoubleProperty(DEFAULT_START_ANGLE) {

        @Override
        protected void invalidated() {
            if (mesh != null) {
                updateMesh();
            }
        }

    };

    public final double getTubeStartAngleOffset() {
        return tubeStartAngleOffset.get();
    }

    public void setTubeStartAngleOffset(double value) {
        tubeStartAngleOffset.set(value);
    }

    public DoubleProperty tubeStartAngleOffsetProperty() {
        return tubeStartAngleOffset;
    }
    private final DoubleProperty xOffset = new SimpleDoubleProperty(DEFAULT_X_OFFSET) {

        @Override
        protected void invalidated() {
            if (mesh != null) {
                updateMesh();
            }
        }

    };

    public final double getxOffset() {
        return xOffset.get();
    }

    public void setxOffset(double value) {
        xOffset.set(value);
    }

    public DoubleProperty xOffsetProperty() {
        return xOffset;
    }
    private final DoubleProperty yOffset = new SimpleDoubleProperty(DEFAULT_Y_OFFSET) {

        @Override
        protected void invalidated() {
            if (mesh != null) {
                updateMesh();
            }
        }

    };

    public final double getyOffset() {
        return yOffset.get();
    }

    public void setyOffset(double value) {
        yOffset.set(value);
    }

    public DoubleProperty yOffsetProperty() {
        return yOffset;
    }
    private final DoubleProperty zOffset = new SimpleDoubleProperty(DEFAULT_Z_OFFSET) {

        @Override
        protected void invalidated() {
            if (mesh != null) {
                updateMesh();
            }
        }

    };

    public final double getzOffset() {
        return zOffset.get();
    }

    public void setzOffset(double value) {
        zOffset.set(value);
    }

    public DoubleProperty zOffsetProperty() {
        return zOffset;
    }
    private Point3D last = new Point3D(0, 0, 0);

    private TriangleMesh createTorus(int subDivX, int subDivY, int crop, float meanRadius,
            float minorRadius, float tubeStartAngle, float xOffset, float yOffset, float zOffset) {

        listVertices.clear();
        listTextures.clear();
        listFaces.clear();

        int numDivX = subDivX + 1 - 2 * crop;
        float pointX, pointY, pointZ;

        areaMesh.setWidth(2d * Math.PI * (meanRadius + minorRadius));
        areaMesh.setHeight(2d * Math.PI * minorRadius);
        List<Integer> arr = new ArrayList<>();
        List<Integer> arr2 = new ArrayList<>();
        // Create points
        for (int y = crop; y <= subDivY - crop; y++) {
            float dy = (float) y / subDivY;
            for (int x = crop; x <= subDivX - crop; x++) {
                float dx = (float) x / subDivX;
                if (crop > 0 || (crop == 0 && x < subDivX && y < subDivY)) {
                    pointX = (float) ((meanRadius + minorRadius * Math.cos((-1d + 2d * dy) * Math.PI)) * (Math.cos((-1d + 2d * dx) * Math.PI) + xOffset));
                    pointZ = (float) ((meanRadius + minorRadius * Math.cos((-1d + 2d * dy) * Math.PI)) * (Math.sin((-1d + 2d * dx) * Math.PI) + yOffset));
                    pointY = (float) (minorRadius * Math.sin((-1d + 2d * dy) * Math.PI) * zOffset);
//                    if (pointX > 0 && pointZ > 0) {
                    listVertices.add(new Point3D(pointX, pointY, pointZ));
                    last = new Point3D(new Point3D(pointX, pointY, pointZ));
//                    } else {
//                        listVertices.add(new Point3D(0,0,0));
//                        arr.add(x);
//                        arr2.add(y);
//                    }
                }
            }
        }
        Point3D zero = new Point3D(0, 0, 0);
//        for(Point3D p : listVertices) {
//            if(p.x == 0 && p.y == 0) {
//                p.x = 1;
//                p.y = 0;
//                p.z = (float) (getMajorRadius());
//            }
//        }
        System.out.println("listVertices " + listVertices.size());
        // Create texture coordinates
        createTexCoords(subDivX - 2 * crop, subDivY - 2 * crop);

        // Create textures indices
        for (int y = crop; y < subDivY - crop; y++) {
            for (int x = crop; x < subDivX - crop; x++) {
                if (arr.contains(x) && arr2.contains(y)) {
                    continue;
                }
                int p00 = (y - crop) * numDivX + (x - crop);
                int p01 = p00 + 1;
                int p10 = p00 + numDivX;
                int p11 = p10 + 1;
                listTextures.add(new Face3(p00, p10, p11));
                listTextures.add(new Face3(p11, p01, p00));
            }
        }
        System.out.println("TEXT " + listTextures.size());
        // Create faces indices
        for (int y = crop; y < subDivY - crop; y++) {
            for (int x = crop; x < subDivX - crop; x++) {
                if (arr.contains(x) && arr2.contains(y)) {
                    System.out.println("ELLSE");
                    listFaces.add(new Face3(0, 0, 0));
                    listFaces.add(new Face3(0, 0, 0));
                    continue;
                }

                int p00 = (y - crop) * ((crop > 0) ? numDivX : numDivX - 1) + (x - crop);
                int p01 = p00 + 1;
                if (crop == 0 && x == subDivX - 1) {
                    p01 -= subDivX;
                }
                int p10 = p00 + ((crop > 0) ? numDivX : numDivX - 1);
                if (crop == 0 && y == subDivY - 1) {
                    p10 -= subDivY * ((crop > 0) ? numDivX : numDivX - 1);
                }
                int p11 = p10 + 1;
                if (crop == 0 && x == subDivX - 1) {
                    p11 -= subDivX;
                }
                listFaces.add(new Face3(p00, p10, p11));
                listFaces.add(new Face3(p11, p01, p00));
            }
        }
        System.out.println("listFaces " + listFaces.size());
        for (Point3D p : listVertices) {
            if (p.x < 0) {
                p.x = -p.x;
            }
            if (p.z < 0) {
                p.z = -p.z;
            }
        }
        Cylinder cc = new Cylinder();
        values = new TreeMap<>();
        angles.clear();
        valPoints.clear();
        for (Point3D p : listVertices) {
            float angle = p.z / p.x;
            angle = roundResult(angle, 7);
//            angle = (float) Math.atan(angle);

            if (!values.containsKey(angle)) {
                values.put(angle, new ArrayList<>());
                values.get(angle).add(p);
                angles.add(angle);
                List<Point3D> var = new ArrayList<>();
                var.add(p);
                valPoints.add(var);

            } else {
                values.get(angle).add(p);
                int index = angles.indexOf(angle);
                valPoints.get(index).add(p);
            }
        }
        int count = angles.size() - 1 - angles.size()*3 /4;
        float step = 1f / count;
        float tmpVal = -1;
        for (int i = angles.size() - 1; i > angles.size() * 3 / 4; i--) {
            increaseRadius(angles.get(i), valPoints.get(i), tmpVal, i);
            tmpVal+=step;
        }
        for (Float val : values.keySet()) {
            System.out.println(val + " " + values.get(val).size());
        }
        for (int i = 0; i < angles.size(); i++) {
            System.out.println(angles.get(i) + " " + valPoints.get(i).size());
        }
        return createMesh();
    }

    float roundResult(float d, int precise) {

        precise = 10 ^ precise;
        d = d * precise;
        int i = (int) Math.round(d);
        return (float) i / precise;
    }
    int currentState = 1;
    private List<Float> incVal;
    private List<Float> incValRad;
    private List<Float> zCoordinate;

    private List<Float> angles = new ArrayList<>();
    private List<List<Point3D>> valPoints = new ArrayList<>();

    int counter = 0;

    public void updateFigure() {
        counter++;
        if (counter == 160) {
            updateMesh();
            setMesh(createMesh());
//            currentState = 3;
            counter = 0;
        }
        if (incVal == null) {
            incVal = new ArrayList<>();
            float tmp = 0f;
            for (int i = 0; i < values.keySet().size(); i++) {
                incVal.add(tmp);
                tmp += 0.002;
            }
            Collections.reverse(incVal);
        }
        if (incValRad == null) {
            incValRad = new ArrayList<>();
            float tmp = 0;
            incValRad.add(tmp);
            tmp = 0.002f;
            for (int i = 1; i < values.keySet().size(); i++) {
                incValRad.add(tmp);
                tmp *= 1.095;
            }
            Collections.reverse(incValRad);
        }
        if (zCoordinate == null) {
            zCoordinate = new ArrayList<>();
            for (int i = 0; i < angles.size(); i++) {
                zCoordinate.add(i * 10f);
            }
            Collections.reverse(zCoordinate);
        }
        switch (currentState) {
            case 1:
                for (int j = 0; j < angles.size(); j++) {

                    rotateByKey(angles.get(j), valPoints.get(j), incVal.get(j), j);
                    increaseRadius(angles.get(j), valPoints.get(j), incValRad.get(j), j);
//                Float val = (Float) values.keySet().toArray()[0];
//                    increaseRadius(val, 0.004f);
//                Float key = (Float) values.keySet().toArray()[7];
//                List<Point3D> points = values.get(key);
//                float y = (float) (Math.sin(key.floatValue()) * (getMajorRadius()));
//                float x = (float) (Math.cos(key.floatValue()) * (getMajorRadius()));
//                List<Point3D> vectors = findVectors(points, new Point3D(x, 0, y));
//                for (int z = 0; z < points.size(); z++) {
//                    points.get(z).substractThis(vectors.get(z));
//                }
//                key = (Float) values.keySet().toArray()[5];
//                points = values.get(key);
//                y = (float) (Math.sin(key.floatValue()) * (getMajorRadius()));
//                x = (float) (Math.cos(key.floatValue()) * (getMajorRadius()));
//                vectors = findVectors(points, new Point3D(x, 0, y));
//                for (int z = 0; z < points.size(); z++) {
//                    points.get(z).substractThis(vectors.get(z));
//                }
                }
                setMesh(createMesh());
//                currentState= 2;
        }
    }

    private List<Point3D> findVectors(List<Point3D> points, Point3D point3D, float val) {
        List<Point3D> vect = new ArrayList<>();
        for (Point3D ss : points) {
            Point3D v = point3D.substract(ss);
            v = v.multiply(val);
            vect.add(v);
        }
        return vect;
    }

    private void increaseRadius(Float key, List<Point3D> points, float val, int index) {
//        List<Point3D> points = values.get(key);
        if (points == null) {
            System.out.println("ERROR for key " + key);
            return;
        }
        Float aa = key;
//        double[][] ar = MatrixOperations.rotatePoint(getMajorRadius(), 0, 0, 0, key);;
//        if(key > Math.PI / 2) {
//            aa = key - (float)Math.PI / 2;
//            
//        }
        float y = (float) (Math.sin(Math.atan(aa.floatValue())) * (getMajorRadius()));
        float x = (float) (Math.cos(Math.atan(aa.floatValue())) * (getMajorRadius()));
//        x = (float) ar[0][0];
//        y = (float) ar[0][1];
        float z = 0;
        if (zCoordinate != null) {
            z = zCoordinate.get(index);
        }
        List<Point3D> vectors = findVectors(points, new Point3D(x, z, y), val);
        for (int i = 0; i < points.size(); i++) {
            points.get(i).substractThis(vectors.get(i));
        }
    }

    private void rotateByKey(Float key, List<Point3D> points3, double angle, int index) {
        if (points3 == null) {
            System.out.println("ERROR FOR KEY " + key);
            return;
        }
        for (Point3D p : points3) {
            MatrixOperations.rotPoint(p, angle);

        }
        Float newKey = key + (float) angle;
        angles.set(index, newKey);
    }

}
