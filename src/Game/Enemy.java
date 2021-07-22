package Game;


import Game.ECS.Entity;
import Game.ECS.EntityArchetype;
import Game.ECS.World;
import Game.Physics.Transform;
import engine.Vec3;

public class Enemy extends Entity {

    public Enemy(Vec3 startPos, Vec3 startRot) {
        super(World.enemy, startPos, startRot);
    }

    @Override
    public void onSpawn(Vec3 startPos, Vec3 startRot) {
        Transform enemyTransform = (Transform) archetype.getComponent("Transform");
        enemyTransform.setPosition(startPos);
        enemyTransform.setRotation(startRot);
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
