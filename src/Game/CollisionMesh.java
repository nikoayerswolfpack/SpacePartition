package Game;

import engine.Vec3;

public abstract class CollisionMesh {
    Vec3[] volumePoints;
    Vec3 center;

    public static double distance(Vec3 a, Vec3 b) {
        Vec3 difference = new Vec3(a);
        difference.sub(b);
        return difference.getLength();
    }



    public static Vec3 ClosestPointOnLineSegment(Vec3 A, Vec3 B, Vec3 Point)
    {
        Vec3 returnA = new Vec3(A);
        Vec3 AB = new Vec3(B);
        AB.sub(A);
        Vec3 PA = new Vec3(Point);
        PA.sub(A);
        double t = PA.dot(A) / AB.dot(AB);
        double saturate = Math.min((Math.max(t,0)), 1.0);
        AB.scale(saturate);
        returnA.add(AB);
        return returnA;
    }

    public static boolean SphereCollision(Sphere s1, Sphere s2) {
        Vec3 penetrationNormal = new Vec3(s1.center);
        penetrationNormal.sub(s2.center);
        penetrationNormal.normalize();
        double penetrationDepth = s1.radius + s2.radius - penetrationNormal.getLength();
        return penetrationDepth > 0;
        }


    public void updateColliderPosition(Vec3 relativeTranslate) {
        for (Vec3 volumePoint : volumePoints) {
            center.add(relativeTranslate);
            volumePoint.add(center);
        }
    }



    public abstract boolean collision(CollisionMesh mesh);




}
