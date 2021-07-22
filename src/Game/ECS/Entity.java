package Game.ECS;

import Game.ECS.EntityArchetype;
import engine.Vec3;

import java.awt.event.ActionListener;

/**
 * @author Nikolas Ayers
 */

public abstract class Entity {
    public EntityArchetype archetype;
    public EntityState state;

    public Entity(Vec3 startPos, Vec3 startRot) {
        state = EntityState.ALIVE;
        create();
        onSpawn(startPos, startRot);

    }

    public Entity(Vec3 startPos) {
        this(startPos, new Vec3(0, 0, 0));

    }

    public abstract void create();

    public abstract void onSpawn(Vec3 startPos, Vec3 startRot);

    public abstract void onDestroy();

    public abstract void onUpdate();



}
