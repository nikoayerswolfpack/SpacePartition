package Game.ECS;

/**
 * @author Nikolas Ayers
 */

import java.util.Hashtable;

public abstract class Component implements ComponentSystem {

    public Hashtable<String, Boolean> EntityAction = new Hashtable<>();

    @Override
    public boolean canMakeAction(String action) {
        return EntityAction.get(action);
    }
    public abstract void start();
    public abstract void update();

}
