package com.icey.btwrs.world.entity;

import com.icey.btwrs.constants.WorldConstants;

/**
 * @author Allen
 *         Created on 6/11/15.
 */
public class TowerEntity extends Entity {

    String name; //name of tower

    public TowerEntity(float width, float height, String name) {
        super(width, height, EntityType.TOWER);
        this.userDataType = EntityType.TOWER;
        this.name = name;
    }


    public boolean isLargeTower(){
        return width > WorldConstants.Body.TOWER_SIZE_S[0];
    }


}
