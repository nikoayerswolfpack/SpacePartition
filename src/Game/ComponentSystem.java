package Game;

/*
 * @author Nikolas Ayers
 */

public interface ComponentSystem {

    boolean canMakeAction(String action);

    void start();

    void update();


}
