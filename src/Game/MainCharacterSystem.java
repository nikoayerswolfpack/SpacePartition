package Game;

/**
 * @author Nikolas Ayers
 */

public interface MainCharacterSystem extends ComponentSystem{
    int getHealth();
    void setHealth(int newHealth);
    int getSanity();
    void setSanity(int newSanity);
    int getBullets();
    void setBullets(int newBullets);
    void takeDamage(int damage);
    void gainHealth(int heal);
    void gainBullets(int cartridge);
    void increaseMaxH(int newMax);
    void increaseMaxB(int newMax);
}
