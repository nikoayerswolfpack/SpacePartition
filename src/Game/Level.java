package Game;

/**
 * @author Nikolas Ayers
 */

import Game.ECS.EntityQuery;
import Game.ECS.World;
import engine.Vec3;
import importer.Obj;
import jdk.jfr.Event;

import java.lang.reflect.InvocationTargetException;

public class Level {
    World world;
    SpawnPoint[] enemySpawnPoints;
    EventSpawner inPoint;
    EventSpawner outPoint;
    EventSpawner sanityPoint;
    float spawnWait;
    Obj model;

    public Level(SpawnPoint[] levelEnemies, EventSpawner entrance, EventSpawner sanityPoint, EventSpawner exit, Obj levelModel) {
        this.enemySpawnPoints = levelEnemies;
        this.inPoint = entrance;
        this.sanityPoint = sanityPoint;
        this.outPoint = exit;
        this.model = levelModel;
    }

    public void spawnEnemy(int enemyIndex) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Enemy enemy = (Enemy) world.create("Enemy", enemySpawnPoints[enemyIndex].spawnPosition, enemySpawnPoints[enemyIndex].spawnRotation);

        world.enemies = new EntityQuery(world, EnemyAI.class);

    }
    //TODO: STORE INITIAL POSITIONS FOR ENTITIES BASED ON LEVEL
}
