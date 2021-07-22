package Game.Physics;

/**
 * @author Nikolas Ayers
 */

import Game.ECS.Component;
import Game.ECS.Entity;
import Game.ECS.World;
import engine.Vec3;

import java.util.ArrayList;

public class Collider extends Component implements ColliderSystem {
    CollisionMesh[] meshes;
    boolean isStatic;
    public ArrayList<Collider> currentCollisions;
    public ColliderType colliderType;

    public Collider() {
        super();
    }

    public Collider(Entity parent) {
        super(parent);
    }

    public Collider(Entity parent, boolean isStatic, CollisionMesh... meshSet) {
        super(parent);
        meshes = new CollisionMesh[meshSet.length];
        meshes = meshSet;
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