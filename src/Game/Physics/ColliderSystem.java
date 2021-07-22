package Game.Physics;

/**
 * @author Nikolas Ayers
 */

import Game.ECS.ComponentSystem;
import engine.Vec3;

public interface ColliderSystem extends ComponentSystem {

    void update(Vec3 velocity);
    void update(Collider... colliders);
}
