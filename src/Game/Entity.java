package Game;

/**
 * @author Nikolas Ayers
 */

public abstract class Entity {
    EntityArchetype archetype;

    public Entity() {

    }

    public abstract void create();

    public abstract void onSpawn();

    public abstract void onDestroy();

    public abstract void onUpdate();

}
