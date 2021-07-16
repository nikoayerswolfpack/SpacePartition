package engine;

import java.awt.*;
import java.io.Serializable;
import bsp3d.*;

/**
 *
 * @author Leo
 */
public class Triangle implements Serializable, Comparable<Triangle> {

    public Vec3 a;
    public Vec3 b;
    public Vec3 c;
    public Vec3 normal = new Vec3();

    private final Vec3 p1Tmp = new Vec3();

    private static Stroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    private static Color[] colors = new Color[256];

    static {
        for (int c = 0; c < 256; c++) {
            colors[c] = new Color(c, c, c, 255);
        }
    }

    public Triangle(Vec3 a, Vec3 b, Vec3 c, Vec3 n) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.normal = n;

    }

    public Triangle(Vec3 a, Vec3 b, Vec3 c) {
        this.a = a;
        this.b = b;
        this.c = c;

        p1Tmp.set(a);
        p1Tmp.sub(b);

        normal.set(c);
        normal.sub(b);
        normal.cross(p1Tmp);
    }

    private static Polygon polygon = new Polygon();

    public void draw(Graphics2D g) {
        polygon.reset();
        polygon.addPoint((int) (a.x), (int) (a.y));
        polygon.addPoint((int) (b.x), (int) (b.y));
        polygon.addPoint((int) (c.x), (int) (c.y)); // if not work go hur
        g.draw(polygon);
    }

    Vec3 wa = new Vec3();
    Vec3 wb = new Vec3();
    Vec3 wc = new Vec3();

    private static Plane plane = new Plane (new Vec3(0, 0, 1.0), new Vec3 (0, 0, 0.01));
    Observer observerTmp = new Observer();

    public void draw3D(Graphics2D g, Observer observer) {
        wa.set(a);
        wb.set(b);
        wc.set(c);

        wa.sub(observer.position);
        wb.sub(observer.position);
        wc.sub(observer.position);

        wa.rotateY(-observer.angleY);
        wb.rotateY(-observer.angleY);
        wc.rotateY(-observer.angleY);

        wa.rotateX(-observer.angleX);
        wb.rotateX(-observer.angleX);
        wc.rotateX(-observer.angleX);

        if (wa.z <= 0 && wb.z <= 0 && wc.z <= 0) return;
        if (wa.z <=0 || wb.z <=0 || wc.z <= 0) {
            Triangle cameraTriangle = new Triangle(wa, wb, wc);

            observerTmp.position.set(0, 0, 0);
            observerTmp.direction.set(0, 0, 1.0);
            for (Triangle triangle: plane.clipBack(cameraTriangle)) {
                triangle.draw3D(g, observerTmp);
            }

            return;
        }

        int px1 = (int) (300 * wa.x / wa.z);
        int py1 = (int) (300 * wa.y / wa.z);

        int px2 = (int) (300 * wa.x / wa.z);
        int py2 = (int) (300 * wa.y / wa.z);

        int px3 = (int) (300 * wa.x / wa.z);
        int py3 = (int) (300 * wa.y / wa.z);

        double z = (wa.z + wb.z + wc.z) / 3.0;

        int c = (int) (255 * (1.0 - (z / 5)));
        if (c < 0) c = 0;
        if (c > 255) c = 255;

        if (px1 < -400 && px2 < -400 && px3 < -400) return;
        if (px1 > 400 && px2 > 400 && px3 > 400) return;
        if (px1 < -300 && px2 < -300 && px3 < -300) return;
        if (px1 > 300 && px2 > 300 && px3 > 300) return;

        polygon.reset();
        polygon.addPoint(px1, py1);
        polygon.addPoint(px2, py2);
        polygon.addPoint(px3, py3);
        g.setStroke(stroke);
        g.setColor(colors[c]);
        g.fill(polygon);
        g.setColor(Color.BLACK);
        g.draw(polygon);
    }

    @Override
    public String toString() {
        return "Triangle { " + "a = " + a + ", b = " + b + ", c = " + c + ", normal = " + normal + " }";
    }

    @Override
    public int compareTo(Triangle o) {
        return (int) Math.signum((o.a.z + o.b.z + o.c.z) / 3.0 - (a.z + b.z + c.z) / 3.0);
    }
}
