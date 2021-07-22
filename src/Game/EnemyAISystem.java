package Game;

import Game.ECS.ComponentSystem;

public interface EnemyAISystem extends ComponentSystem {

    int getHealth();
    void setHealth(int newHealth);
    void takeDamage(int damage);
    void gainHealth(int heal);
    void moveToPlayer();

}
