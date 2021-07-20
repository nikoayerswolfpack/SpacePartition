package Game;


import engine.Triangle;
import engine.Vec3;

import java.util.List;

public class Box extends CollisionMesh {
    Triangle[] triangles = new Triangle[12];

    public Box(Vec3 center, Vec3[] dimensionVectors) {
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

        Vec3[] negDims = new Vec3[] {
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

            if ((i+1) % 2 == 0) {
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

        int[][] triangleSet = new int[][] {
                new int[] { 1, 2, 5 },
                new int[] { 2, 5, 6 },
                new int[] { 1, 3, 7 },
                new int[] { 1, 5, 7 },
                new int[] { 2, 6, 8 },
                new int[] { 2, 4, 8 },
                new int[] { 3, 4, 7 },
                new int[] { 4, 7, 8 },
                new int[] { 1, 2, 3 },
                new int[] { 2, 3, 4 },
                new int[] { 5, 6, 7 },
                new int[] { 6, 7, 8 }
        };

        for(int i = 0; i < 12; i++) {
            Vec3[] vertices = new Vec3[3];
            for(int j = 0; j < 3; j++) {
                vertices[j] = volumePoints[triangleSet[i][j]-1];
            }
            triangles[i] = new Triangle(vertices[0], vertices[1], vertices[2]);
        }
    }

    @Override
    public boolean collision(CollisionMesh mesh) {
        return false;
    }
}
