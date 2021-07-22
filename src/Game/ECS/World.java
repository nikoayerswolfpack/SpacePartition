package Game.ECS;

/**
* @author Nikolas Ayers
 */

import Game.*;
import Game.Physics.*;
import engine.Vec3;

import java.awt.event.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class World {
    public static float deltaTime = 0.0f;
    Duration deltaTimeDuration = Duration.ZERO;
    Instant beginTime = Instant.now();
    Level[] levels;
    int SAVED_LEVEL = 1;
    ArrayList<Entity> entities;
    Vec3 startPosition = new Vec3(0, 0, 0);
    Vec3 startRotation = new Vec3(0, 0, 0);


    public EntityQuery colliderEntities;
    public EntityQuery enemies;

    public static final EntityArchetype player = new EntityArchetype("MainCharacter", "Transform", "Collider");
    public static final EntityArchetype enemy = new EntityArchetype("Transform", "Collider");
    public static final EntityArchetype eventSpawner = new EntityArchetype("Transform", "Collider", "SpawnComponent");

    public World() {
    }

    public Entity create(String entityType, Vec3 spawnPos, Vec3 spawnRot) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> entityClass = Class.forName(entityType);
        Constructor<?> entityConstructor = entityClass.getConstructor(Vec3.class, Vec3.class);
        Object genericEntity = entityConstructor.newInstance(spawnPos, spawnRot);
        entities.add((Entity) genericEntity);
        return (Entity) genericEntity;
    }

    public void destroy(Entity entity) {
        entities.remove(entity);
    }

    public void start() {
        Player player = new Player(startPosition, startRotation);

        colliderEntities = new EntityQuery(this, Collider.class);
    }

    //TODO: Make loadLevel()

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
            } else {
                destroy(entity);
            }
        }
        for ( Entity entity : colliderEntities.results) {
            colliderEntities.results.forEach((other) -> {
                if (entity != other) {
                    if (CollisionMesh.distance(
                            ((Transform) entity.archetype.getComponent("Transform")).pos,
                            (((Transform) other.archetype.getComponent("Transform")).pos)) < 5.0) {
                        ((Collider) entity.archetype.getComponent("Collider")).update(
                                ((Collider) other.archetype.getComponent("Collider"))
                        );
                    }
                }
            });

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
