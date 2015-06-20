package com.icey.btwrs.world.enums;

import com.icey.btwrs.world.Tower;
import com.icey.btwrs.world.TowerUnit;
import com.icey.btwrs.world.interfaces.UnitAction;

/**
 * @author Allen
 *         Created on 6/11/15.
 */
public enum UnitState implements UnitAction{


    MOVE_FORWARD{
        @Override
        public void moveUnit(TowerUnit unit) {

        }
    },

    MOVE_BACK{
        @Override
        public void moveUnit(TowerUnit unit) {

        }
    },

    RIGHT{
        @Override
        public void directUnit(TowerUnit unit) {

        }
    },

    LEFT{
        @Override
        public void directUnit(TowerUnit unit) {

        }
    },


    UP{
        @Override
        public void directUnit(TowerUnit unit) {

        }
    },

    DOWN{
        @Override
        public void directUnit(TowerUnit unit) {

        }
    },


    ATTACK_TOWER{
        @Override
        public void attackTower(TowerUnit unit, Tower tower) {

        }
    },


    ATTACK_UNIT{
        @Override
        public void attackUnit(TowerUnit unit, TowerUnit opponent) {

        }
    };

    @Override
    public void directUnit(TowerUnit unit) {

    }

    @Override
    public void moveUnit(TowerUnit unit) {

    }

    @Override
    public void attackUnit(TowerUnit unit, TowerUnit opponent) {

    }

    @Override
    public void attackTower(TowerUnit unit, Tower tower) {

    }

    @Override
    public void deployUnit(Tower tower, int unitID) {

    }
}
