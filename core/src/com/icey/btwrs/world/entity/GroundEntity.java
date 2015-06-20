package com.icey.btwrs.world.entity;

/** GroundUserData for Box2D Ground Body.
 *
 * @author Allen
 *         Created on 6/10/15.
 */
public class GroundEntity extends Entity {


    public GroundEntity(float width, float height) {
        super(width, height, EntityType.GROUND);
        userDataType = EntityType.GROUND;
    }

}
