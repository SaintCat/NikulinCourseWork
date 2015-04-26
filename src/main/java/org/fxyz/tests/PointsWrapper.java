/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fxyz.tests;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import org.fxyz.geometry.Point3D;
import org.fxyz.shapes.primitives.CuboidMesh;

/**
 *
 * @author Admin
 */
public class PointsWrapper {

    private static final double SPHERE_DEFAULT_RADIUS = 0.1;
    private List<Point3D> points;
    private List<Sphere> visiblePoints;
    private List<MyLine> cubeLines;

    public PointsWrapper(List<Point3D> points) {
        this.points = points;
        visiblePoints = tranformPointsToSpheres(points);
        cubeLines = getCubeLines();
    }


    public void rotateAroundOZ(double angleInDegress) {
        for (int i = 0; i < points.size(); i++) {
            Point3D p = points.get(i);
            MatrixOperations.rotPoint(p, Math.toRadians(angleInDegress));
            Sphere s = visiblePoints.get(i);
            s.setTranslateX(p.x);
            s.setTranslateY(p.y);
            s.setTranslateZ(p.z);
        }
    }

    private List<MyLine> getCubeLines() {
        List<MyLine> res = new ArrayList<>();
        res.add(new MyLine(points.get(0), points.get(1)));
        res.add(new MyLine(points.get(1), points.get(2)));
        res.add(new MyLine(points.get(2), points.get(3)));
        res.add(new MyLine(points.get(3), points.get(0)));

        res.add(new MyLine(points.get(4), points.get(5)));
        res.add(new MyLine(points.get(5), points.get(6)));
        res.add(new MyLine(points.get(6), points.get(7)));
        res.add(new MyLine(points.get(7), points.get(4)));

        res.add(new MyLine(points.get(0), points.get(4)));
        res.add(new MyLine(points.get(3), points.get(7)));
        res.add(new MyLine(points.get(2), points.get(6)));
        res.add(new MyLine(points.get(1), points.get(5)));
        return res;
    }

    public List<Sphere> getVisiblePoints() {
        return visiblePoints;
    }

    public class MyLine {

        public Point3D first;
        public Point3D second;

        public MyLine(Point3D first, Point3D second) {
            this.first = first;
            this.second = second;
        }
    }

    public List<Point3D> getIntersectPointsWithFlat(Point3D p, Point3D normal) {
        List<Point3D> res = new ArrayList<>();
        for (MyLine line : cubeLines) {
            Point3D resPoint = CuboidMesh.intersectFlatAndLine(line.first, line.second.substract(line.first).normalize(), p, normal);
            if (resPoint != null) {
                if (((resPoint.x <= line.second.x && resPoint.x >= line.first.x)
                        || (resPoint.x >= line.second.x && resPoint.x <= line.first.x))
                        && ((resPoint.y <= line.second.y && resPoint.y >= line.first.y)
                        || (resPoint.y >= line.second.y && resPoint.y <= line.first.y))
                        && ((resPoint.z <= line.second.z && resPoint.z >= line.first.z)
                        || (resPoint.z >= line.second.z && resPoint.z <= line.first.z))) {
                    res.add(resPoint);
                }
            }
        }
        return res;
    }

    public void rotateAroundAxis(Point3D axis, double angle) {
        double[][] matr = MatrixOperations.createRotateMatrixAroundAxis(axis, angle);
        for (int i = 0; i < points.size(); i++) {
            Point3D p = points.get(i);
            double[][] pointMatrix = new double[][]{{p.x, p.y, p.z}};
            pointMatrix = MatrixOperations.transpose(pointMatrix);
            pointMatrix = MatrixOperations.multiply(matr, pointMatrix);
            p.x = (float) pointMatrix[0][0];
            p.y = (float) pointMatrix[1][0];
            p.z = (float) pointMatrix[2][0];
//            Sphere s = visiblePoints.get(i);
//            s.setTranslateX(p.x);
//            s.setTranslateY(p.y);
//            s.setTranslateZ(p.z);
        }
    }

    public void translateXCoor(double value) {
        for (Point3D p : points) {
            p.x += value;
        }
    }

    public void translateYCoor(double value) {
        for (Point3D p : points) {
            p.y += value;
        }
    }

    public void translateZCoor(double value) {
        for (Point3D p : points) {
            p.z += value;
        }
    }

    public static List<Sphere> tranformPointsToSpheres(List<Point3D> pointsToTranfor, double radius) {
        List<Sphere> res = new ArrayList<>();
        for (Point3D p : pointsToTranfor) {
            Sphere s = new Sphere(radius);
            s.setTranslateX(p.x);
            s.setTranslateY(p.y);
            s.setTranslateZ(p.z);
            s.setMaterial(new PhongMaterial(Color.ROYALBLUE));
            res.add(s);
        }
        return res;
    }

    public static List<Sphere> tranformPointsToSpheres(List<Point3D> pointsToTranfor) {
        return tranformPointsToSpheres(pointsToTranfor, SPHERE_DEFAULT_RADIUS);
    }

    public void updateSphereCoors() {
        for (int i = 0; i < points.size(); i++) {
            Point3D p = points.get(i);
            Sphere s = visiblePoints.get(i);
            s.setTranslateX(p.x);
            s.setTranslateY(p.y);
            s.setTranslateZ(p.z);
        }
    }
}
