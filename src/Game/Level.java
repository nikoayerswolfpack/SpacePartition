package Game;

/**
 * @author Nikolas Ayers
 */

import importer.Obj;

public class Level {
    Enemy[] enemies;
    EventSpawner[] spawnPoints;
    EventSpawner inPoint;
    EventSpawner outPoint;
    EventSpawner sanityPoint;
    float spawnWait;
    Obj model;


    public Level() {

    }

    public void spawnEnemy() {
      //TODO: make enemy, entity, eventspawner
    }
    //TODO: STORE INITIAL POSITIONS FOR ENTITIES BASED ON LEVEL
}
