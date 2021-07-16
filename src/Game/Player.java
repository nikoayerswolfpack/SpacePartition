package Game;

import Game.ECS.Entity;
import Game.ECS.EntityArchetype;
import engine.Vec3;

public class Player extends Entity {

    public Player(Vec3 startPos) {
        create();
        Transform myTransform = (Transform) archetype.getComponent("Transform");
        myTransform.setPosition(startPos);
        archetype.components.forEach((str, comp) -> {
            comp.update();
        });
    }


    @Override
    public void create() {
        archetype = new EntityArchetype(World.player);
    }

    @Override
    public void onSpawn() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onUpdate() {

        Transform myTransform = (Transform) archetype.getComponent("Transform");
        archetype.components.forEach((str, comp) -> {
            comp.update();
        });

    }
}
