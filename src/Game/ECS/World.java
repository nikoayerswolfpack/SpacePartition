package Game.ECS;

/**
* @author Nikolas Ayers
 */

import Game.Level;
import Game.Physics.*;
import Game.Player;
import engine.Vec3;

import java.awt.event.*;
import java.time.*;

public class World {
    public static float deltaTime = 0.0f;
    Duration deltaTimeDuration = Duration.ZERO;
    Instant beginTime = Instant.now();
    Level[] levels;
    Entity[] entities;
    Vec3 startPosition = new Vec3(0, 0, 0);
    Vec3 startRotation = new Vec3(0, 0, 0);

    public EntityQuery colliderEntities;

    public static final EntityArchetype player = new EntityArchetype("MainCharacter", "Transform", "Collider");
    public static final EntityArchetype enemy = new EntityArchetype("Transform", "Collider");
    public static final EntityArchetype eventSpawner = new EntityArchetype("Transform", "Collider", "SpawnComponent");
    public World() {

    }



    public void destroy(Entity entity) {
        entities[entity.entityIndex] = null;
    }

    public void start() {
        Player player = new Player(startPosition, startRotation);

        colliderEntities = new EntityQuery<Collider>(this);
    }

    public void end() {
        //Other code for additional entities
        for (Entity entity : entities) {
            entity.onDestroy();
        }
    }

    public void update() {
        beginTime = Instant.now();
        for ( Entity entity : entities) {
            if (entity.state == EntityState.ALIVE) {
                entity.onUpdate();
            }
            else {
                destroy(entity);
            }
        }
        for ( Object entity : colliderEntities.entities) {

        }
        deltaTimeDuration = Duration.between(beginTime, Instant.now());
        deltaTime = (float) deltaTimeDuration.toMillis();
        deltaTime /= 1000; //time in seconds
    }

    public Level currentLevel() {
        //TODO: DEFINE LEVEL
        return null;
    }

}
