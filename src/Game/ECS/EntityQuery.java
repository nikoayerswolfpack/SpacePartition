package Game.ECS;


import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class EntityQuery {

    World world;
    public ArrayList<Entity> results;

    public EntityQuery(World world, Class... S) {
        this.world = world;
        entityIsValid: for (Entity entity : world.entities) {
            for (Class component : S) {
                if (!entity.archetype.hasComponent(component.getClass().getSimpleName())) {
                    break entityIsValid;
                }
            }
            results.add(entity);
        }

    }

}
