package Game;

/**
 * @author Nikolas Ayers
 */

import Game.ECS.Component;
import engine.Vec3;

import java.util.ArrayList;

public class Collider extends Component implements ColliderSystem {
    CollisionMesh[] meshes;
    boolean isStatic;
    ArrayList<Collider> currentCollisions;

    public Collider(boolean isStatic, CollisionMesh... meshSet) {
        meshes = new CollisionMesh[meshSet.length];
        for (int i = 0; i < meshSet.length; i++) {
            meshes[i] = meshSet[i];
        }
        this.isStatic = isStatic;
        start();
    }

    @Override
    public void start() {
        currentCollisions = new ArrayList<>();


    }

    @Override
    public void update() {

    }

    @Override
    public void update(Vec3 velocity) {
        if (!isStatic) {
            Vec3 relativeTranslate = new Vec3(velocity);
            relativeTranslate.scale(World.deltaTime);
            for (CollisionMesh mesh : meshes) {
                mesh.updateColliderPosition(relativeTranslate);
            }
        }
    }

    @Override
    public void update(Collider... colliders) {
        for (Collider collider : colliders) {
            currentCollider : for (CollisionMesh mesh : meshes) {
                for (CollisionMesh otherMesh : collider.meshes) {
                    if (mesh.collision(otherMesh)) {
                        currentCollisions.add(collider);
                        break currentCollider;
                    }
                    else {
                        currentCollisions.remove(collider);
                    }
                }
            }
        }
    }
}