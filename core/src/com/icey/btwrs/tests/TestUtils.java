package com.icey.btwrs.tests;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.icey.btwrs.constants.WorldConstants;
import com.icey.btwrs.stage.GameStage;
import com.icey.btwrs.utils.BodyUtils;
import com.icey.btwrs.world.Tower;
import com.icey.btwrs.world.TowerUnit;
import com.kotcrab.vis.ui.widget.PopupMenu;

/** Utils for testing various stage/world setups
 *
 * TODO: create more tests
 *
 * @author Allen
 *         Created on 6/15/15.
 */
public class TestUtils {


    public static void addTestMenuItems(PopupMenu menu){

    }


    /** Setup a tower and unit at bottom of map
     *
     * @param stage : stage to add to
     */
    public static void testUnitTowerBtmSetup(GameStage stage){
        World world = stage.getWorld();

        //---Tower---
        Vector2 pos1 = new Vector2(WorldConstants.Body.TOWER_S_CENTER_BTM[0], WorldConstants.Body.TOWER_S_CENTER_BTM[1]);
        com.badlogic.gdx.physics.box2d.Body tower1 = BodyUtils.towerBody(world, WorldConstants.Body.TOWER_SIZE_S[0], WorldConstants.Body.TOWER_SIZE_S[1], pos1,
                "Cannon"); //create body
        Tower tower = new Tower(tower1, 1); //setup BodyActor
        stage.addActor(tower); //add actor with body to stage

        //----TowerUnit---
        Vector2 unitPos1 = new Vector2(WorldConstants.Body.UNIT_CENTER_BTM[0], WorldConstants.Body.UNIT_CENTER_BTM[1]);
        com.badlogic.gdx.physics.box2d.Body unitBody1 = BodyUtils.unitBody(world, WorldConstants.Body.UNIT_SIZE_S[0], WorldConstants.Body.UNIT_SIZE_S[1],
                unitPos1, "Destroyer");
        TowerUnit unitActor = new TowerUnit(unitBody1, 1);

        stage.addTowerUnit(unitActor);

    }
}
