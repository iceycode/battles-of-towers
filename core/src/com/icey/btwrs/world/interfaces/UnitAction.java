package com.icey.btwrs.world.interfaces;

import com.icey.btwrs.world.Tower;
import com.icey.btwrs.world.TowerUnit;

/** UnitAction interface
 *
 * @author Allen
 *         Created on 6/11/15.
 */
public interface UnitAction {

    /** Changes unit directions
     *
     * @param unit : unit being controlled
     */
    public void directUnit(TowerUnit unit);

    /** Changes movement speed and takes care of reversing.
     *
     * @param unit : unit being controlled
     */
    public void moveUnit(TowerUnit unit);

    /** Unit attacks opponent unit
     *
     * @param unit : unit doing attacking
     * @param opponent : opponent unit that is attacked
     */
    public void attackUnit(TowerUnit unit, TowerUnit opponent);

    /** Unit attacks a tower
     *
     * @param unit : Unit that is doing the attacking
     * @param tower : tower that is being attacked
     */
    public void attackTower(TowerUnit unit, Tower tower);

    /** Deploys a unit from the tower using the ID to specify
     *
     * @param tower : tower from which unit comes from
     * @param unitID : unit id
     */
    public void deployUnit(Tower tower, int unitID);
}
