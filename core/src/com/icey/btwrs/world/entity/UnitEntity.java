package com.icey.btwrs.world.entity;

/** Data corresponding to TowerUnit BodyActor
 *
 * @author Allen
 *         Created on 6/11/15.
 */
public class UnitEntity extends Entity {

    String unitName;

    public UnitEntity(float width, float height, String name) {
        super(width, height, EntityType.TOWER_UNIT);
        this.unitName = name;
    }


    public String getUnitName(){
        return unitName;
    }
}
