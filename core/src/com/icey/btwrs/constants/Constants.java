package com.icey.btwrs.constants;

import com.badlogic.gdx.math.Vector2;

/** Main game constants
 *
 * @author Allen
 *         Created on 6/10/15.
 */
public class Constants {

    public static final String GAME_TITLE = "Battles of Towers";

    public static final float SCREEN_WIDTH = 480;
    public static final float SCREEN_HEIGHT = 800;


    //Skin & texture paths
    public static final String SKIN_VISUI_DEFAULT = "visui/skins/uiskin.json";

    public static final float[] UNIT_CONTROLS_SIZE = {300f, 200f};
    public static final float[] TOUCHPAD_SIZE = {50f, 50f};

    /* Box2D Constants
     */
    public static final float WORLD_TO_SCREEN = 150000f; //converter from meters to pixels
    public static final float PIX_PER_METER = 118.5f; //pixels per meter scaled by actor

    public static final float MOVE_TIME = 5;

    public static final Vector2 IMPULSE_L = new Vector2(.04f, 0);

    //max velocity in pixels per second
    public static final float MAX_VELOCITY_X = 20f * 40; //the max velocity in m/s2 (Newtons) is 20


    public static final Vector2 GRAVITY = new Vector2(0, -10f); //gravity of the box2d world

}
