package com.icey.btwrs.world;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.icey.btwrs.world.entity.Entity;

/** Abstract class BodyActor extends Actor & holds a Body object
 *  For using an {@link com.badlogic.gdx.scenes.scene2d.Actor} with {@link com.badlogic.gdx.physics.box2d.Body}.
 *  Allows for use of Animation class with a Body physics properties in a World.
 *  Entity contains data related to body and actor shape/textures.
 *
 * @author Allen
 *         Created on 6/10/15.
 */
public abstract class BodyActor extends Actor {

    public Body body;
    protected Entity entity;
    protected Rectangle rect;


    /** creates a GameActor with body
     *
     * @param body
     */
    public BodyActor(Body body) {
        this.body = body;
        entity = (Entity) body.getUserData();
        rect = new Rectangle();
    }


    public abstract Entity getEntity();

}
