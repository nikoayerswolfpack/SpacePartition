package bsp3d;

import engine.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Node implements Serializable {

    private int level;
    private final List<Triangle> triangles = new ArrayList<Triangle>();
    private Plane plane;
    private Node front;
    private Node back;

    private static final double planeThickness = 0.01;

    public void preProcess(int level, List<Triangle> ts) {
        this.level = level;

        if (ts.isEmpty()) return;

        int bestIndex = 0;
        int bestScore = Integer.MAX_VALUE;

        int leftCount = 0;
        int rightCount = 0;
        double bestBalanceScore = Double.MAX_VALUE;
        for (int i = 0; i < ts.size(); i++) {
            int fragmentationCount = 0;
            Triangle t = ts.remove(0);
            plane = new Plane(t);
            for (Triangle t2 : ts) {
                if (plane.isOnSamePlane(t2, planeThickness)) {
                    continue;
                }
                List<Triangle> cb = plane.clipBack(t2);
                leftCount += cb.size();
                fragmentationCount += cb.size() > 1 ? 1 : 0;
                List<Triangle> cf = plane.clipFront(t2);
                rightCount += cf.size();
                fragmentationCount += cf.size() > 1 ? 1 : 0;
            }

            if (fragmentationCount < bestScore) {
                bestScore = fragmentationCount;
                bestIndex = i;
            }
            ts.add(t);
        }

        Triangle triangle = ts.remove(bestIndex);
        triangles.add(triangle);

        plane = new Plane(triangle);

        List<Triangle> frontTriangles = new ArrayList<>();
        List<Triangle> backTriangles = new ArrayList<>();

        for (Triangle t : ts) {
            if (plane.isOnSamePlane(t, planeThickness)) {
                triangles.add(t);
                continue;
            }
            List<Triangle> cb = plane.clipBack(t);
            if (!cb.isEmpty()) {
                frontTriangles.addAll(cb);
            }
            List<Triangle> cf = plane.clipFront(t);
            if (!cf.isEmpty()) {
                backTriangles.addAll(cf);
            }
        }

        if (!frontTriangles.isEmpty()) {
            front = new Node();
            front.preProcess(level + 1, frontTriangles);
        }
        if (!backTriangles.isEmpty()) {
            back = new Node();
            back.preProcess(level + 1, backTriangles);
        }

    }

    public static int maxCount = 10000;

    private static int count;

    public void transverse(Observer observer, Graphics2D g) {
        if (level == 0) count = 0;

        boolean isFront = plane.isFront(observer.position);
        if (!isFront) {
            if (front != null) {
                front.transverse(observer, g);
            }
            for (Triangle t : triangles) {
                t.draw3D(g, observer);
                count++;
            }
            if (back != null) {
                back.transverse(observer, g);
            }
        }
        else {
            if (back != null) {
                back.transverse(observer, g);
            }
            for (Triangle t : triangles) {
                t.draw3D(g, observer);
                count++;
            }
            if (front != null) {
                front.transverse(observer, g);
            }
        }



    }

    public static void save(String name, Node nodeObj) throws Exception {
        FileOutputStream fos = new FileOutputStream(name);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(nodeObj);
        oos.close();
    }

    public static Node load(String name) throws Exception {
        FileInputStream fis = new FileInputStream(name);
        return load(fis);
    }

    public static Node load(InputStream is) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(is);
        Node node = (Node) ois.readObject();
        ois.close();
        return node;
    }
}
