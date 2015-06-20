package com.icey.btwrs.world;

import com.badlogic.gdx.math.Vector2;
import com.icey.btwrs.constants.WorldConstants;
import com.icey.btwrs.world.entity.UnitEntity;
import com.badlogic.gdx.physics.box2d.Body;

/** TowerUnit - a controllable body within an actor
 *  Gets deployed from a tower and is controlled by player once deployed.
 *
 *
 * TODO: figure out physics - how fast unit should move/rotate
 *
 * @author Allen
 *         Created on 6/10/15.
 */
public class TowerUnit extends BodyActor {

    float maxVelocity = WorldConstants.Body.MAX_FORCE; //max is 5 m/s (5 pixels per second)

    UnitEntity unitEntity; //the userData for the body
    public String name; //name of unit
    public int player; //player that owns unit



    public TowerUnit(Body body, int player){
        super(body);
        this.player = player;
        this.unitEntity = (UnitEntity)body.getUserData(); //carries detailed info about unit's body
        this.name = unitEntity.getUnitName();

        setX(body.getPosition().x);
        setY(body.getPosition().y);
    }


    /** Changes the velocity and heading (direction) of unit
     *
     * @param velocity : velocity in up/down direction (is always less then maxVelocity)
     * @param turnAngle : the angle at which unit should be turning
     */
    public void applyMoveForce(float velocity, float turnAngle){
        Vector2 pos = body.getPosition(); //position of body

        body.setLinearVelocity(0, velocity); //instantly sets the velocity
        body.setTransform(pos, turnAngle); //rotate to angle at which touchpad is (NOTE: done instantly)


        //NOTE: alternate method - acceleration with frame-by-frame turning
//        Vector2 currentVelocity = body.getLinearVelocity();
//        //check to see if velocity is at max velocity, if not accelerate
//        if (Math.abs(currentVelocity.y) <= maxVelocity)
//            body.applyForce(0, velocity, pos.x, pos.y, true); //applies force in y direction
        //next, rotate the body - NOTE: this rotates frame-by-frame
//        float angle = body.getAngle() * MathUtils.radDeg; //angle body currently facing, translated to degrees
//        float rotation = (turnAngle - angle) % 360; //get the rotation by taking different b/w current & desired angle
//
//        if (Math.abs(rotation) > 1){
//            if (rotation < -180) rotation += 360;
//            if (rotation > 180) rotation -= 360;
//            float impulse = body.getInertia() * rotation; //get impulse required
//            body.applyTorque(impulse , true);
//        }
    }


    @Override
    public UnitEntity getEntity() {
        return (UnitEntity)entity;
    }

}
