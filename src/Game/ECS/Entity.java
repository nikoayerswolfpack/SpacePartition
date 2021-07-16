package Game.ECS;

import Game.ECS.EntityArchetype;

/**
 * @author Nikolas Ayers
 */

public abstract class Entity {
    public EntityArchetype archetype;

    public Entity() {

    }

    public abstract void create();

    public abstract void onSpawn();

    public abstract void onDestroy();

    public abstract void onUpdate();

}
