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

    public Entity(EntityArchetype archetype, Vec3 startPos, Vec3 startRot) {
        state = EntityState.ALIVE;
        create(archetype);
        onSpawn(startPos, startRot);

    }

    public Entity(EntityArchetype archetype, Vec3 startPos) {
        this(archetype, startPos, new Vec3(0, 0, 0));

    }

    public void create( EntityArchetype archetype ) {
        this.archetype = archetype;
        archetype.components.forEach(((s, component) -> {
            component.setEntity(this);
        }));
    }

    public abstract void onSpawn(Vec3 startPos, Vec3 startRot);

    public abstract void onDestroy();

    public abstract void onUpdate();



}
