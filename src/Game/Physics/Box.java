package Game.Physics;


import engine.Triangle;
import engine.Vec3;

public class Box extends CollisionMesh {
    Triangle[] triangles = new Triangle[12];

    public Box(Vec3 center, Vec3[] dimensionVectors) {
        volumePoints = new Vec3[11];

        if (dimensionVectors.length != 3) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format("A box needs three dimension vectors, received %s", dimensionVectors.length)
            );
        }
        // Get dimensions in length, width, height order
        this.center = center;

        for (Vec3 dimension : dimensionVectors) {
            dimension.scale(0.5);
        }

        Vec3[] negDims = new Vec3[]{
                (new Vec3(dimensionVectors[0])),
                (new Vec3(dimensionVectors[1])),
                (new Vec3(dimensionVectors[2]))
        };

        for (Vec3 neg : negDims) {
            neg.scale(-1);
        }

        for (int i = 0; i < 8; i++) {
            volumePoints[i] = new Vec3(center);
            if (i == 0 || i == 1 || i == 4 || i == 5) {
                volumePoints[i].add(negDims[0]);
            } else {
                volumePoints[i].add(dimensionVectors[0]);
            }

            if ((i + 1) % 2 == 0) {
                volumePoints[i].add(dimensionVectors[1]);
            } else {
                volumePoints[i].add(negDims[1]);
            }

            if (i <= 3) {
                volumePoints[i].add(dimensionVectors[2]);
            } else {
                volumePoints[i].add(negDims[2]);
            }
        }

        int[][] triangleSet = new int[][]{
                new int[]{1, 2, 5},
                new int[]{2, 5, 6},
                new int[]{1, 3, 7},
                new int[]{1, 5, 7},
                new int[]{2, 6, 8},
                new int[]{2, 4, 8},
                new int[]{3, 4, 7},
                new int[]{4, 7, 8},
                new int[]{1, 2, 3},
                new int[]{2, 3, 4},
                new int[]{5, 6, 7},
                new int[]{6, 7, 8}
        };

        for (int i = 0; i < 12; i++) {
            Vec3[] vertices = new Vec3[3];
            for (int j = 0; j < 3; j++) {
                vertices[j] = volumePoints[triangleSet[i][j] - 1];
            }
            triangles[i] = new Triangle(vertices[0], vertices[1], vertices[2]);
        }

        volumePoints[8] = dimensionVectors[0];
        volumePoints[9] = dimensionVectors[1];
        volumePoints[10] = dimensionVectors[2];
    }

    @Override
    public boolean collision(CollisionMesh mesh) {
        switch (mesh.getClass().getSimpleName()) {
            case ("Box"):
                Vec3[] myAxes = new Vec3[3];
                boolean[] flags = new boolean[3];
                for (int i = 0; i < 3; i++) {

                    Vec3[] myPoints_projected = new Vec3[8];
                    double[] myPoints_projected_length = new double[8];
                    Vec3[] otherPoints_projected = new Vec3[8];
                    double[] otherPoints_projected_length = new double[8];

                    for (int j = 0; j < 8; j++) {
                        myPoints_projected[j] = new Vec3(volumePoints[i + 8]);
                        myPoints_projected[j].scale(2);
                        double dist = myPoints_projected[j].dot(volumePoints[j]);
                        myPoints_projected[j].normalize();
                        myPoints_projected[j].scale(dist);

                        myPoints_projected_length[j] = myPoints_projected[j].getLength();
                    }
                    for (int j = 0; j < 8; j++) {
                        otherPoints_projected[j] = new Vec3(volumePoints[i + 8]);
                        otherPoints_projected[j].scale(2);
                        double dist = myPoints_projected[j].dot(((Box) mesh).volumePoints[j]);
                        otherPoints_projected[j].normalize();
                        otherPoints_projected[j].scale(dist);

                        otherPoints_projected_length[j] = otherPoints_projected[j].getLength();
                    }

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
            case("Sphere"):
                return ((Sphere) mesh).collision(this);
            case("Capsule"):

                Capsule a_s = ((Capsule) mesh);

                Vec3 aNormal_s = new Vec3(a_s.volumePoints[1]);
                aNormal_s.sub(a_s.volumePoints[0]);
                aNormal_s.normalize();

                Vec3 lineEndOffsetA_s = new Vec3(aNormal_s);
                lineEndOffsetA_s.scale(a_s.radius);

                Vec3 firstCoreA_s = new Vec3(lineEndOffsetA_s);
                firstCoreA_s.add(a_s.volumePoints[0]);
                Vec3 firstCoreB_s = new Vec3(a_s.volumePoints[1]);
                firstCoreB_s.sub(lineEndOffsetA_s);

                Vec3 v0_s = new Vec3(this.center);
                v0_s.sub(firstCoreA_s);
                Vec3 v1_s = new Vec3(this.center);
                v1_s.sub(firstCoreB_s);

                double d0_s = v0_s.dot(v0_s);
                double d1_s = v1_s.dot(v1_s);


                Vec3 bestSphereInA_s;
                if (d0_s > d1_s) {
                    bestSphereInA_s = firstCoreB_s;
                } else {
                    bestSphereInA_s = firstCoreA_s;
                }

                Vec3 bestSphereInB_s = ClosestPointOnLineSegment(this.center, this.center, bestSphereInA_s);

                bestSphereInA_s = ClosestPointOnLineSegment(firstCoreA_s, firstCoreB_s, bestSphereInB_s);
                Sphere bestA_s = new Sphere(a_s.radius, bestSphereInA_s);
                return bestA_s.collision(this);
            default:
                return false;
        }
    }


}
