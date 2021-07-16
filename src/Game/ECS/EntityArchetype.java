package Game.ECS;

/**
 * @author Nikolas Ayers
 */

import Game.MainCharacter;
import Game.Transform;

import java.util.Hashtable;

public class EntityArchetype {

    public Hashtable<String, Component> components = new Hashtable<String, Component>();

    public EntityArchetype(String... compToAdd) {
        assemble(compToAdd);
    }

    public EntityArchetype(EntityArchetype archetype) {
        archetype.components.forEach((str, comp) -> {
            assemble(str);
        });
    }


    public void assemble(String... compToAdd) {
        for (String compName : compToAdd) {
            switch (compName) {
                case ("Transform"):
                    components.put("Transform", new Transform());
                    break;
                case ("MainCharacter"):
                    components.put("MainCharacter", new MainCharacter());
                //TODO: ADD COMPONENTS
                default:
                    //Do nothing.
            }
        }
    }

    public void start() {

    }

    public void update() {
      components.forEach((str, comp) -> {
            comp.update();
        });
    }

    public void update(String compName) {
        getComponent(compName).update();
    }

    public Component getComponent(String componentName) {
        return components.get(componentName);
    }
}
