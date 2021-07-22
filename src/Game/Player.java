package Game;

import Game.ECS.Entity;
import Game.ECS.EntityArchetype;
import Game.ECS.World;
import Game.Physics.Collider;
import Game.Physics.Transform;
import engine.Vec3;

public class Player extends Entity {


    public Player(Vec3 startPos, Vec3 startRot) {
        super(startPos, startRot);
    }

    @Override
    public void create() {
        archetype = new EntityArchetype(World.player);
    }

    @Override
    public void onSpawn(Vec3 startPos, Vec3 startRot) {
        Transform myTransform = (Transform) archetype.getComponent("Transform");
        myTransform.setPosition(startPos);
        myTransform.setRotation(startRot);
        archetype.components.forEach((str, comp) -> {
            comp.update();
        });
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onUpdate() {

        Transform myTransform = (Transform) archetype.getComponent("Transform");
        Collider collider = (Collider) archetype.getComponent("Collider");
        collider.update(myTransform.velocity);
        archetype.components.forEach((str, comp) -> {
            comp.update();
        });

    }


}
