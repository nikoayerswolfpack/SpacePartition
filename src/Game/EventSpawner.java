package Game;

import Game.ECS.Entity;
import Game.ECS.EntityArchetype;
import Game.ECS.World;
import Game.Physics.*;
import engine.Vec3;


public class EventSpawner extends Entity {


    public EventSpawner(Vec3 startPos, Vec3 startRot) {
        super(startPos, startRot);
    }

    @Override
    public void create() { archetype = new EntityArchetype(World.eventSpawner); }

    @Override
    public void onSpawn(Vec3 setPos, Vec3 setRot) {
        Transform eventSpawnerTransform = (Transform) archetype.getComponent("Transform");
        eventSpawnerTransform.setPosition(setPos);
        eventSpawnerTransform.setRotation(setRot);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onUpdate() {
        Collider eventSpawnerCollider = (Collider) archetype.getComponent("Collider");
        eventSpawnerCollider.update();
    }


}
