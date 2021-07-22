package Game;

import Game.ECS.Component;
import Game.ECS.Entity;
import Game.Physics.Collider;
import Game.Physics.CollisionMesh;
import Game.Physics.Transform;

public class EnemyAI extends Component implements EnemyAISystem {
    int health;
    EnemyType enemyType;

    public EnemyAI(EnemyType type) {
        super();
        this.enemyType = type;
    }

    public EnemyAI(Entity parent, EnemyType type) {
        super(parent);
        this.enemyType = type;
    }

    @Override
    public void start() {
        health = enemyType.getHealth();
        EntityAction.put("takeDamage", true);
        EntityAction.put("gainHealth", true);
    }

    @Override
    public void update() {
        EntityAction.put("takeDamage", (health > 0));
        EntityAction.put("gainHealth", (enemyType.getHealth() > health));
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int newHealth) {
        this.health = newHealth;
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public void gainHealth(int heal) {
        health += heal;
    }

    @Override
    public void moveToPlayer() {

    }
}
