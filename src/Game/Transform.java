package Game;

/**
 * @author Nikolas Ayers
 */

import Game.ECS.Component;
import engine.*;

public class Transform extends Component implements TransformSystem {
    public Vec3 pos;
    public Vec3 rot;
    public Vec3 velocity;
    public Vec3 omega;


    @Override
    public void start() {

    }

    @Override
    public void update() {
        Vec3 velMoment = new Vec3(velocity);
        Vec3 omgMoment = new Vec3(omega);
        velMoment.scale(World.deltaTime);
        omgMoment.scale(World.deltaTime);
        pos.add(velMoment);
        rot.add(omgMoment);
    }

    @Override
    public void setPosition(Vec3 newPos) {
        pos.set(newPos);
    }

    @Override
    public void setRotation(Vec3 newRot) {
        rot.set(newRot);
    }

    @Override
    public void setVelocity(Vec3 newVel) {
        velocity.set(newVel);
    }

    @Override
    public void setOmega(Vec3 newOmg) {
        omega.set(newOmg);
    }

    @Override
    public void scaleVelocity(double s) {
        velocity.scale(s);
    }

    @Override
    public void scaleOmega(double s) {
        omega.scale(s);
    }

    @Override
    public void stopVelocity() {
        velocity.scale(0);
    }

    @Override
    public void stopOmega() {
        omega.scale(0);
    }
}
