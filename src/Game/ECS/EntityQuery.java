package Game.ECS;


import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

public class EntityQuery<T extends Component> {

    ArrayList<Entity> entities;
    World world;
    private Class<T> type;

    public EntityQuery(World world) {
        this.world = world;
        type = (Class<T>)( (ParameterizedType) getClass().getGenericSuperclass() ).getActualTypeArguments()[0]; // TODO: MAKE SURE THIS WORKS
        for (Entity entity : world.entities) {
            if (entity.archetype.hasComponent(type.getSimpleName())) {
                entities.add(entity);
            }
        }
    }

}
