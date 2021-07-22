package Game;

import Game.ECS.Component;
import Game.ECS.Entity;

public class SpawnComponent extends Component implements SpawnComponentSystem {
    public boolean hasInteracted;
    public int weight; // depending on the entity, will determine how much, for example, health will be gained
    public SpawnType spawnType;

    public SpawnComponent() {
        super();
    }

    public SpawnComponent(Entity parent) {
        super(parent);
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {

    }



}
