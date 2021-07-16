package importer;

import engine.Triangle;
import engine.Vec2;
import engine.Vec3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WavefrontParser {

    public static List<Vec3> vertices = new ArrayList<>();
    public static List<Vec3> normals = new ArrayList<>();

    public static Obj obj = new Obj();
    public static List<Obj> objs = new ArrayList<>();

    public static void load(String resource, double scaleFactor) throws Exception {
        objs.clear();
        vertices.clear();
        normals.clear();

        BufferedReader br = new BufferedReader(new InputStreamReader(WavefrontParser.class.getResourceAsStream(resource)));
        String line = null;

        while((line = br.readLine()) != null) {
            if (line.startsWith("v ")) {
                extractVertex(line, vertices, scaleFactor);
            }
            else if (line.startsWith("vn ")) {
                extractVertexNormal(line, normals);
            }
            else if (line.startsWith("f ")) {
                extractFace(line, vertices, normals, obj);
            }
        }
        br.close();


    }
    private static void extractVertex(String line, List<Vec3> vertices, double scaleFactor) {
        line = line.substring(2).trim();
        String[] v = line.split("\\ ");
        Vec3 normal = new Vec3(
                Double.parseDouble(v[0]),
                Double.parseDouble(v[1]),
                Double.parseDouble(v[2])

        );
        normals.add(normal);
    }
    private static void extractVertexNormal(String line, List<Vec3> normals) {
        line = line.substring(3).trim();
        String[] v = line.split("\\ ");
        Vec3 normal = new Vec3(
                Double.parseDouble(v[0]),
                Double.parseDouble(v[1]),
                Double.parseDouble(v[2])

        );
        normals.add(normal);
    }
    private static void extractFace(String line, List<Vec3> vertices, List<Vec3 normals, Obj obj) {
        List<Triangle> faces = obj.faces;
        line = line.substring(2).trim();
        String[] v = line.split("\\ ");
        String[] i1 = v[0].split("/");
        String[] i2 = v[1].split("/");
        String[] i3 = v[2].split("/");
        String[] i4 = null;

        Vec3 p1 = vertices.get(Integer.parseInt(i1[0]) - 1);
        Vec3 p2 = vertices.get(Integer.parseInt(i2[0]) - 1);
        Vec3 p3 = vertices.get(Integer.parseInt(i3[0]) - 1);
        Vec3 p4 = null;

        Vec3 n1 = new Vec3();
        Vec3 n2 = new Vec3();
        Vec3 n3 = new Vec3();
        Vec3 n4 = new Vec3();

        if (v.length > 3) {
            i4 = v[3].split("/");
            p4 = vertices.get(Integer.parseInt(i4[0]) - 1);
        }

        if (i1.length > 2) {
            n1 = normals.get(Integer.parseInt(i1[2]) - 1);
            n2 = normals.get(Integer.parseInt(i2[2]) - 1);
            n3 = normals.get(Integer.parseInt(i3[2]) - 1);
        }

        if (i1.length > 2 && v.length > 3) {
            n4 = normals.get(Integer.parseInt(i4[2]) - 1);
        }

        Vec2 t1 = new Vec2();
        Vec2 t2 = new Vec2();
        Vec2 t3 = new Vec2();
        Vec2 t4 = new Vec2();

        n1.add(n2);
        n1.add(n3);
        n1.normalize();
        Triangle face = new Triangle(p1, p2, p3, n1);
        faces.add(face);

        if (v.length > 3) {
            face = new Triangle(p1, p3, p4, n1);
            faces.add(face);
            throw new RuntimeException("Wavefront with faces with more than 3 edges !");
        }

    }
    public static void main(String[] args) throws Exception {
        load("/src/test.obj", 1);
        System.out.println(WavefrontParser.obj.faces.size());
    }
}
