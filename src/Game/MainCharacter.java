package Game;

import Game.ECS.Component;
import Game.ECS.Entity;

/**
 * @author Nikolas Ayers
 */

public class MainCharacter extends Component implements MainCharacterSystem {
    int health;
    int sanity;
    int bullets;
    int MAX_HEALTH = 100;
    int MAX_BULLETS = 200;
    int FULL_SANITY = 100;

    public MainCharacter() {
        super();
    }

    public MainCharacter(Entity parent) {
        super(parent);
    }


    @Override
    public void start() {
        EntityAction.put("takeDamage", true);
        EntityAction.put("gainHealth", true);
        EntityAction.put("gainBullets", true);
    }

    @Override
    public void update() {
        EntityAction.put("takeDamage", (health > 0));
        EntityAction.put("gainHealth", (health < MAX_HEALTH));
        EntityAction.put("gainBullets", (bullets < MAX_BULLETS));
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
    public int getSanity() {
        return sanity;
    }

    @Override
    public void setSanity(int newSanity) {
        this.sanity = newSanity;
    }

    @Override
    public int getBullets() {
        return bullets;
    }

    @Override
    public void setBullets(int newBullets) {
        this.bullets = newBullets;
    }

    @Override
    public void takeDamage(int damage) {
        if (canMakeAction("takeDamage")) health -= damage;
    }

    @Override
    public void gainHealth(int heal) {
        if (canMakeAction("gainHealth")) health += heal;
    }

    @Override
    public void gainBullets(int cartridge) {
        if (canMakeAction("gainBullets")) bullets += cartridge;
    }

    @Override
    public void increaseMaxH(int newMax) {
        MAX_HEALTH += newMax;
        health = MAX_HEALTH;
    }

    @Override
    public void increaseMaxB(int newMax) {
        MAX_BULLETS += newMax;
    }


}
