package Game.ECS;

/**
 * @author Nikolas Ayers
 */

import java.util.Hashtable;

public abstract class Component implements ComponentSystem {

    public Hashtable<String, Boolean> EntityAction = new Hashtable<>();
    private Entity parent;

    public Component() {

    }

    public Component(Entity parent) {
        this.parent = parent;
    }

    public Entity getEntity() {
        return parent;
    }

    public void setEntity(Entity parent) {
        this.parent = parent;
    }

    @Override
    public boolean canMakeAction(String action) {
        return EntityAction.get(action);
    }

    public abstract void start();

    public abstract void update();

}
