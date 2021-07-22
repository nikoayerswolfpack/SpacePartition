package Game;

public enum EnemyType {
    GRUNT (25),
    MID (60),
    BOSS (150)
    ;

    private final int health;

    private EnemyType(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
}
