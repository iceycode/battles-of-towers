package com.icey.btwrs.world.entity;

/**  Abstract class Entity represents UserData for Box2D UserData object.
 *  Other UserData classes contain information that affects extended BodyActor class movements, size, positions, etc.
 *
 * @author Allen
 *         Created on 6/10/15.
 */
public abstract class Entity {

    protected EntityType userDataType;
    protected float width;
    protected float height;

    public Entity(float width, float height, EntityType type) {
        this.width = width;
        this.height = height;
        this.userDataType = type; //type of entity
    }

    //gets this from enum class
    public EntityType getEntityType() {
        return userDataType;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

//    public abstract void update(float width, float height);

    /** Inner enum contains the type of UserData to be used by Box2d Body
     *
     */
    public enum EntityType {

        TOWER_UNIT,
        TOWER,

        GROUND,
        WALL,

    }
}
