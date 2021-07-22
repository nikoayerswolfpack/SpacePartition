package Game.Physics;

import Game.ECS.ComponentSystem;
import engine.Vec3;

public interface TransformSystem extends ComponentSystem {

    void setPosition(Vec3 newPos);
    void setRotation(Vec3 newRot);
    void setVelocity(Vec3 newVel);
    void setOmega(Vec3 newOmg);
    void scaleVelocity(double s);
    void scaleOmega(double s);
    void stopVelocity();
    void stopOmega();

}
