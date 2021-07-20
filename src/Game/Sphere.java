package Game;

/**
 * @author Nikolas Ayers
 */

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
            case ("Box"):
                Vec3[] myAxes = new Vec3[3];
                boolean[] flags = new boolean[3];
                for (int i = 0; i < 3; i++) {

                    Vec3[] myPoints_projected = new Vec3[8];
                    double[] myPoints_projected_length = new double[8];
                    Vec3[] otherPoints_projected = new Vec3[2];
                    double[] otherPoints_projected_length = new double[2];

                    for (int j = 0; i < 8; i++) {
                        myPoints_projected[j] = new Vec3(volumePoints[i + 8]);
                        myPoints_projected[j].scale(2);
                        double dist = myPoints_projected[j].dot(volumePoints[j]);
                        myPoints_projected[j].normalize();
                        myPoints_projected[j].scale(dist);

                        myPoints_projected_length[j] = myPoints_projected[j].getLength();
                    }

                    otherPoints_projected[0] = new Vec3(volumePoints[i+8]);
                    otherPoints_projected[0].scale(2);
                    otherPoints_projected[0].normalize();
                    otherPoints_projected[0].scale(radius);

                    otherPoints_projected[1] = new Vec3(otherPoints_projected[0]);
                    otherPoints_projected[1].scale(-1);

                    Vec3 projectedCenter = new Vec3(volumePoints[i+8]);
                    double distance = projectedCenter.dot(center);
                    projectedCenter.normalize();
                    projectedCenter.scale(distance);

                    otherPoints_projected[0].add(projectedCenter);
                    otherPoints_projected[1].add(projectedCenter);


                    Vec3 myClosest = new Vec3(myPoints_projected[indexOfSmallest(myPoints_projected_length)]);
                    Vec3 myFurthest = new Vec3(myPoints_projected[indexOfLargest(myPoints_projected_length)]);
                    Vec3 otherClosest = new Vec3(otherPoints_projected[indexOfSmallest(otherPoints_projected_length)]);
                    Vec3 otherFurthest = new Vec3(otherPoints_projected[indexOfSmallest(otherPoints_projected_length)]);

                    Vec3 dist0 = new Vec3(otherFurthest);
                    dist0.sub(myClosest);

                    Vec3 dist1 = new Vec3(myClosest);
                    dist1.sub(myFurthest);

                    Vec3 dist2 = new Vec3(otherClosest);
                    dist2.sub(otherFurthest);

                    dist1.add(dist2);

                    flags[i] = dist0.getLength() < dist1.getLength();

                }
                return flags[0] && flags[1] && flags[2];
            default:
                return false;

        }

    }
}
