package Game;



public class Enemy extends Entity{

    @Override
    public void create() {
        archetype = new EntityArchetype(World.enemy);
    }

    @Override
    public void onSpawn() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onUpdate() {
        Transform myTransform = (Transform) archetype.getComponent("Transform");
        archetype.components.forEach((str, comp) -> {
            comp.update();
        });
    }
}
