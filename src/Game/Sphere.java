package Game;

import engine.Vec3;

public class Sphere extends CollisionMesh {
    double radius;

    public Sphere(double radius, Vec3 center) {
        this.radius = radius;
        this.center = center;
    }

    @Override
    public boolean collision(CollisionMesh mesh) {
        switch (mesh.getClass().getSimpleName()) {
            case ("Sphere"):
                return SphereCollision(this, (Sphere) mesh);
            case ("Capsule"):
                return ((Capsule) mesh).collision(this);
            //TODO: BOX
            default:
                return false;

        }

    }
}
