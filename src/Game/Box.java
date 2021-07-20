package Game;


import engine.Triangle;
import engine.Vec3;

import java.util.List;

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

                    for (int j = 0; i < 8; i++) {
                        myPoints_projected[j] = new Vec3(volumePoints[i + 8]);
                        myPoints_projected[j].scale(2);
                        double dist = myPoints_projected[j].dot(volumePoints[j]);
                        myPoints_projected[j].normalize();
                        myPoints_projected[j].scale(dist);

                        myPoints_projected_length[j] = myPoints_projected[j].getLength();
                    }
                    for (int j = 0; i < 8; i++) {
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
            default:
                return false;
        }
    }


}
