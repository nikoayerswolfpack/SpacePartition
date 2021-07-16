package Game;

public class Sphere extends CollisionMesh{
    double radius;

    public Sphere(double radius) {
        this.radius = radius;
    }

    @Override
    public boolean collision(CollisionMesh mesh) {
        switch (mesh.getClass().getSimpleName()) {
            case ("Sphere"):
                return (this.radius + ((Sphere) mesh).radius) > distance(mesh.center, this.center);
            default:
                return false;
        }

    }
}
