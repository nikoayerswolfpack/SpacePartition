package Game;

import Game.ECS.Component;

public class SpawnComponent extends Component implements SpawnComponentSystem {
    public boolean hasInteracted;
    public int weight; // depending on the entity, will determine how much, for example, health will be gained
    public SpawnType spawnType;

    @Override
    public void start() {

    }

    @Override
    public void update() {

    }



}
