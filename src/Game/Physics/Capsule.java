package Game.Physics;

/**
* @author Nikolas Ayers
 * @see https://wickedengine.net/2020/04/26/capsule-collision-detection/
 * @author turanszkij
 */


import engine.Vec3;

public class Capsule extends CollisionMesh {
    double radius;

    public Capsule(double radius, Vec3 base, Vec3 tip) {
        this.radius = radius;
        volumePoints = new Vec3[]{base, tip};
        Vec3 axis = base;
        axis.sub(tip);
        axis.scale(0.5);
        axis.add(base);
        this.center = axis;
    }


    @Override
    public boolean collision(CollisionMesh mesh) {
        switch (mesh.getClass().getSimpleName()) {
            case ("Sphere"):

                Capsule a_s = this;
                Sphere sphere = (Sphere) mesh;

                Vec3 aNormal_s = new Vec3(a_s.volumePoints[1]);
                aNormal_s.sub(a_s.volumePoints[0]);
                aNormal_s.normalize();

                Vec3 lineEndOffsetA_s = new Vec3(aNormal_s);
                lineEndOffsetA_s.scale(a_s.radius);

                Vec3 firstCoreA_s = new Vec3(lineEndOffsetA_s);
                firstCoreA_s.add(a_s.volumePoints[0]);
                Vec3 firstCoreB_s = new Vec3(a_s.volumePoints[1]);
                firstCoreB_s.sub(lineEndOffsetA_s);

                Vec3 v0_s = new Vec3(sphere.center);
                v0_s.sub(firstCoreA_s);
                Vec3 v1_s = new Vec3(sphere.center);
                v1_s.sub(firstCoreB_s);

                double d0_s = v0_s.dot(v0_s);
                double d1_s = v1_s.dot(v1_s);


                Vec3 bestSphereInA_s;
                if (d0_s > d1_s) {
                    bestSphereInA_s = firstCoreB_s;
                } else {
                    bestSphereInA_s = firstCoreA_s;
                }

                Vec3 bestSphereInB_s = ClosestPointOnLineSegment(sphere.center, sphere.center, bestSphereInA_s);

                bestSphereInA_s = ClosestPointOnLineSegment(firstCoreA_s, firstCoreB_s, bestSphereInB_s);
                Sphere bestA_s = new Sphere(a_s.radius, bestSphereInA_s);
                Sphere bestB_s = new Sphere(sphere.radius, bestSphereInB_s);
                return SphereCollision(bestA_s, bestB_s);

            case ("Capsule"):

                Capsule a = this;
                Capsule b = (Capsule) mesh;

                Vec3 aNormal = new Vec3(a.volumePoints[1]);
                aNormal.sub(a.volumePoints[0]);
                aNormal.normalize();

                Vec3 lineEndOffsetA = new Vec3(aNormal);
                lineEndOffsetA.scale(a.radius);

                Vec3 firstCoreA = new Vec3(lineEndOffsetA);
                firstCoreA.add(a.volumePoints[0]);
                Vec3 firstCoreB = new Vec3(a.volumePoints[1]);
                firstCoreB.sub(lineEndOffsetA);


                //FOR CAPSULE B
                Vec3 bNormal = new Vec3(b.volumePoints[1]);
                bNormal.sub(b.volumePoints[0]);
                bNormal.normalize();

                Vec3 lineEndOffsetB = new Vec3(bNormal);
                lineEndOffsetB.scale(b.radius);

                Vec3 secondCoreA = new Vec3(lineEndOffsetB);
                secondCoreA.add(b.volumePoints[0]);
                Vec3 secondCoreB = new Vec3(b.volumePoints[1]);
                secondCoreB.sub(lineEndOffsetB);


                // vectors between line endpoints:
                Vec3 v0 = new Vec3(secondCoreA);
                v0.sub(firstCoreA);
                Vec3 v1 = new Vec3(secondCoreB);
                v1.sub(firstCoreA);
                Vec3 v2 = new Vec3(secondCoreA);
                v2.sub(firstCoreB);
                Vec3 v3 = new Vec3(secondCoreB);
                v3.sub(firstCoreB);

                // squared distances:
                double d0 = v0.dot(v0);
                double d1 = v1.dot(v1);
                double d2 = v2.dot(v2);
                double d3 = v3.dot(v3);

                Vec3 bestSphereInA;
                if (d2 < d0 || d2 < d1 || d3 < d0 || d3 < d1) {
                    bestSphereInA = firstCoreB;
                } else {
                    bestSphereInA = firstCoreA;
                }

                Vec3 bestSphereInB = ClosestPointOnLineSegment(secondCoreA, secondCoreB, bestSphereInA);

                bestSphereInA = ClosestPointOnLineSegment(firstCoreA, firstCoreB, bestSphereInB);
                Sphere bestA = new Sphere(a.radius, bestSphereInA);
                Sphere bestB = new Sphere(b.radius, bestSphereInB);
                return SphereCollision(bestA, bestB);
            case ("Box"):
                return ((Box) mesh).collision(this);
            default:
                return false;
        }

    }


}