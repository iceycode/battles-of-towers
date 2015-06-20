package com.icey.btwrs.ai;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.icey.btwrs.world.entity.UnitEntity;

/** Callback for checking where units are on map by casting rays and seeing if hits something
 *
 * @author Allen
 *         Created on 6/16/15.
 */
public class VisibilityCallback implements RayCastCallback {

    private Actor targetActor;
    private boolean visible;

    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        if (fixture.getBody().getUserData().equals(targetActor)) {
            return fraction;
        }

        if (fixture.getBody().getUserData() instanceof UnitEntity) {
            visible = false;
            return fraction;
        }

        return -1;
    }


    public boolean isVisible() {
        return visible;
    }
}
