package Game;

import engine.Vec3;

public class SpawnPoint {
    Vec3 spawnPosition;
    Vec3 spawnRotation;
    EnemyType type;

    public SpawnPoint(Vec3 position, Vec3 rotation, EnemyType type) {
        this.spawnPosition = position;
        this.spawnRotation = rotation;
        this.type = type;
    }
}
