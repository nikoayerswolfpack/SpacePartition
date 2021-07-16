package Game;

import Game.ECS.Component;

public class Collider extends Component implements ColliderSystem{
    CollisionMesh[] meshes;

    @Override
    public void start() {

    }

    @Override
    public void update() {
        //nested for loop go thru meshes and compare each mesh within each collision mesh to every other one
    }
}
