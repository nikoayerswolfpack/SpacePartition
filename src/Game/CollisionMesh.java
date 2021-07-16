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

    public void updateColliderPosition(Vec3 relativeTranslate) {
        for (Vec3 volumePoint : volumePoints) {
            center.add(relativeTranslate);
            volumePoint.add(center);
        }
    }

    public abstract boolean collision(CollisionMesh mesh);


}
