package com.icey.btwrs.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.icey.btwrs.constants.Constants;
import com.icey.btwrs.constants.WorldConstants;
import com.icey.btwrs.world.TowerUnit;
import com.kotcrab.vis.ui.widget.Separator;

/** Tower unit custom touchpad controller that controls movement of tower units
 *  Pops up a PopupMenu - a VisUI Table/Dialog - which contains 2 TouchPads
 *  1) Touchpad on right : steering
 *  2) Touchpad on left : acceleration
 *
 *  NOTE: may also end up adding buttons
 *
 *
 *
 *
 * @author Allen
 *         Created on 6/10/15.
 */
public class UnitTouchpad extends Window {

    public final float DEADZONERADIUS = 5f; //deadzone radius for touchpad

    private final float POSITION_X = Constants.SCREEN_WIDTH/2 - Constants.UNIT_CONTROLS_SIZE[0]/2;
    private float positionY = 0; //varies based on which player it is for

    GameStage stage;

    Slider velSlider; //velocity slider
    Touchpad touchPad; //for steering direction

    TowerUnit unit;
    Separator vertSeparator; //vertical separator

    /** Sets up a touchpad that controls an individual unit on each player's side
     *  NOTE: currently, default style is used
     *
     * @param skin : skin used
     * @param stage : gamestage - stage units are controlled on
     */
    public UnitTouchpad(Skin skin, GameStage stage, int player) {
        super("", skin);
        this.stage = stage;

        positionY += player == 1 ? 0 : Constants.SCREEN_HEIGHT - Constants.UNIT_CONTROLS_SIZE[1];

        setBounds(POSITION_X, positionY, Constants.UNIT_CONTROLS_SIZE[0], Constants.UNIT_CONTROLS_SIZE[1]);

        touchPad = new Touchpad(DEADZONERADIUS, skin); //uses default style currently
        touchPad.setSize(Constants.TOUCHPAD_SIZE[0], Constants.TOUCHPAD_SIZE[1]);
//        velSlider = new Slider(.1f, 3f, .3f, true, skin); //slider for velocity

        setTouchpadListener();

        vertSeparator = new Separator(true);
        //TODO: add components here
        add(vertSeparator).fillY().expandY(); //adds separator
        add(touchPad).width(touchPad.getWidth()).height(touchPad.getHeight()).right();
    }


    /** Sets the touchpad listener, a ChangeListener
     *  Whenever listener is touched, a force is applied to unit
     *
     */
    protected void setTouchpadListener(){

        touchPad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (touchPad.isTouched()){
                    applyForceToUnit();
                }
            }
        });

    }


    /** Applies force for movement in x, y direction
     *  Get the forward/backward velocity by getting y percent relative to edge (up = positive)
     *  Get the direction (angle) by taking arctangent of y/x position
     *
     *  TODO: setup large vs small unit max velocity
     */
    protected void applyForceToUnit(){
        if (unit!=null){

            //set based on maximum movement force
            float velocity = (touchPad.getKnobPercentY()/100) * WorldConstants.Body.MAX_FORCE;
            float angle = (float)(Math.atan(touchPad.getY()/touchPad.getKnobX())) * MathUtils.radDeg;

            log("Applied force to unit; vel : " + velocity + "; angle: " + angle);

            unit.applyMoveForce(velocity, angle);
        }
    }


    /** Controls a unit, also becoming visible in the process
     *
     * @param unit : unit to control
     */
    public void controlUnit(TowerUnit unit){
        this.unit = unit;
        setVisible(true);
    }

    private void log(String message){
        Gdx.app.log("UnitTouchpad LOG", message);
    }

//    /** we instantiate this vector and the callback here so we don't irritate the GC **/
//    Vector3 testPoint = new Vector3();
//    QueryCallback callback = new QueryCallback() {
//        @Override
//        public boolean reportFixture (Fixture fixture) {
//            // if the hit point is inside the fixture of the body
//            // we report it
//            if (fixture.testPoint(testPoint.x, testPoint.y)) {
//                hitBody = fixture.getBody();
//                return false;
//            } else
//                return true;
//        }
//    };


//    @Override
//    public boolean keyDown(int keycode) {
//        return false;
//    }
//
//    @Override
//    public boolean keyUp(int keycode) {
//        return false;
//    }
//
//    @Override
//    public boolean keyTyped(char character) {
//        return false;
//    }
//
//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        return false;
//    }
//
//    @Override
//    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        return false;
//    }
//
//    @Override
//    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        return false;
//    }
//
//    @Override
//    public boolean mouseMoved(int screenX, int screenY) {
//        return false;
//    }
//
//    @Override
//    public boolean scrolled(int amount) {
//        return false;
//    }
}
