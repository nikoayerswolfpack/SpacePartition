package Game;

import Game.ECS.Entity;
import Game.ECS.EntityArchetype;
import Game.ECS.EntityState;
import Game.ECS.World;
import Game.Physics.*;
import engine.Vec3;


public class EventSpawner extends Entity {
    private SpawnComponent thisSpawnComponent;

    public EventSpawner(Vec3 startPos, Vec3 startRot) {
        super(World.eventSpawner, startPos, startRot);
    }

    @Override
    public void onSpawn(Vec3 setPos, Vec3 setRot) {
        Transform eventSpawnerTransform = (Transform) archetype.getComponent("Transform");
        eventSpawnerTransform.setPosition(setPos);
        eventSpawnerTransform.setRotation(setRot);
        thisSpawnComponent = (SpawnComponent) archetype.getComponent("SpawnComponent");
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onUpdate() {
        Collider eventSpawnerCollider = (Collider) archetype.getComponent("Collider");
        for (Collider other : eventSpawnerCollider.currentCollisions) { //going through what eventSpawner is colliding w/
            if (other.colliderType == ColliderType.BEING) { //checking if any of those are beings
                if (other.getEntity().archetype.hasComponent("MainCharacter")) { //checking if it is the player
                    MainCharacter mc = (MainCharacter) other.getEntity().archetype.getComponent("MainCharacter");
                    switch (thisSpawnComponent.spawnType){ //seeing what effect on the player based on eventSpawn type
                        case HEALTH:
                            mc.gainHealth(thisSpawnComponent.weight);
                            break;
                        case SANITY:
                            mc.setSanity(mc.FULL_SANITY);
                            break;
                        case BULLETS:
                            mc.gainBullets(thisSpawnComponent.weight);
                            break;
                        default:
                            //do nothing
                    }

                }
                else if (other.getEntity().archetype.hasComponent("")) {

                }
                this.state = EntityState.DEAD;
            }

        }
    }


}
