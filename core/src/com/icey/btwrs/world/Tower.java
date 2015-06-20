package com.icey.btwrs.world;

import com.badlogic.gdx.physics.box2d.Body;
import com.icey.btwrs.world.entity.TowerEntity;

/** Tower, when clicked on shows a popup window for what to do.
 *  Contains a static body.
 *
 * @author Allen
 *         Created on 6/10/15.
 */
public class Tower extends BodyActor{

    int player;

    public Tower(Body body, int player){
        super(body);
        this.entity = (TowerEntity)body.getUserData();
        this.player = player;
    }


    @Override
    public TowerEntity getEntity() {
        return (TowerEntity)entity;
    }
}
