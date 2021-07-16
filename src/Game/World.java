package Game;

/*
* @author Nikolas Ayers
 */

import engine.Vec3;

import java.time.*;

public class World {
    public static float deltaTime = 0.0f;
    Duration deltaTimeDuration = Duration.ZERO;
    Instant beginTime = Instant.now();
    Level[] levels;
    Entity[] entities;
    Vec3 startPosition = new Vec3(0, 0, 0);

    public static final EntityArchetype player = new EntityArchetype("MainCharacter", "Transform");
    public static final EntityArchetype enemy = new EntityArchetype("Transform");
    public World() {

    }

    public void start() {
        entities[0] = new Player(startPosition);
        //Other code for additional entities


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
            entity.onUpdate();
        }
        deltaTimeDuration = Duration.between(beginTime, Instant.now());
        deltaTime = (float) deltaTimeDuration.toMillis();
        deltaTime /= 1000; //time in seconds
    }

    public Level currentLevel() {
        //TODO: DEFINE LEVEL

    }

}
