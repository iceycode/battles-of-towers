package com.icey.btwrs.constants;

import com.badlogic.gdx.math.Vector2;

/** Constants related to map - TMX file locations, dimensions, positions, etc.
 *
 * @author Allen
 *         Created on 6/10/15.
 */
public class WorldConstants {

    /* Simple test map. Tiles are 12x12 pixels and map is 28 cols by 48 rows.
     *
     */
    public static final String MAPS_DIR = "maps/";
    public static final String TEST_1A = MAPS_DIR + "test/TestMap1A.tmx";
    public static final String TEST_1B = MAPS_DIR + "test/TestMap1B.tmx";

    //---------MAP CONSTANTS: for screen & world----------------
    public static final int TILE_SIZE = 16;

    //Cols and rows in maps - also Box2D world width of maps
    public static final int COLS_A = 28;
    public static final int ROWS_A = 48;

    //second map size (44 instead of 48 rows)
    public static final int COLS_B = 28; //same as COLS_A
    public static final int ROWS_B = 44; //44 rows, 2nd setup for rows


    //---Map Screen widths---
    public static final float MAP_WIDTH_A = COLS_A * TILE_SIZE;
    public static final float MAP_HEIGHT_A = ROWS_A * TILE_SIZE;

    public static final float MAP_WIDTH_B = COLS_B * TILE_SIZE;
    public static final float MAP_HEIGHT_B = ROWS_B * TILE_SIZE;

    //---MAP POSITIONS (on screen)---
    public static final float MAP_Y_A = Constants.SCREEN_HEIGHT/2 - MAP_HEIGHT_A/2;
    public static final float MAP_X_A = Constants.SCREEN_WIDTH/2 - MAP_WIDTH_A/2;

    public static final float MAP_Y_B = Constants.SCREEN_HEIGHT/2 - MAP_HEIGHT_B/2;
    public static final float MAP_X_B = Constants.SCREEN_WIDTH/2 - MAP_WIDTH_B/2;


    /**----------------WORLD POSITIONS ON MAP---------------------
     * NOTE: world positions are on 2D coordinate plane and units are meare 1 unit per 1 meter.
     */

    public static final float[] WORLD_CENTER = {0, 0}; //0,0, just like on coordinate plane
    public static final float WORLD_TOP = COLS_A /2; //top is positive
    public static final float WORLD_BTM = -COLS_A /2;

    public static final float BTM_EDGE = 2f; //pads bottom a bit,

    //---------------VARIOUS SCREEN COORDINATES---------------
    //center coordinates of map ON SCREEN
    //size of map on the screen
    public static final float[] SIZE_A = {COLS_A , ROWS_A}; // * TILE_SIZE[1]
    public static final float[] CENTER_MAP_SCREEN_A = {Constants.SCREEN_WIDTH /2 - SIZE_A[0]/2, Constants.SCREEN_HEIGHT
            /2 - SIZE_A[0]/2};


    /** Constants for Box2D bodies in game
     *
     * @author Allen
     *         Created on 6/15/15.
     */
    public static class Body {

        public static final Vector2 GRAVITY = new Vector2(0,0);//gravity
        public static final float GROUND_DENSITY = 0.0f;

        public static final float GROUND_HEIGHT_PIXELS = SIZE_A[1];
        public static final float GROUND_HEIGHT = GROUND_HEIGHT_PIXELS/ Constants.PIX_PER_METER ; //~1.49
        //ground is 800 × 178 pixels
        public static final float GROUND_WIDTH_PIXELS = SIZE_A[0];
        public static final float GROUND_WIDTH = GROUND_WIDTH_PIXELS/ Constants.PIX_PER_METER ; //~6.75

        public static final float GROUND_Y = 40;
        public static final float GROUND_X = 0;

        //--------------- UNIT CONSTANTS----------------
        public static final float MAX_FORCE = 5f; //max force on small object = 5 m/s
        public static final float UNIT_MASS_S = 1000f; //weight in kilograms
        public static final float[] UNIT_SIZE_S = {3, 4};
        public static final float UNIT_S_DENSITY = UNIT_MASS_S/(UNIT_SIZE_S[0] * UNIT_SIZE_S[1]); //density of body

        //----UNIT WORLD POSITIONS
        public static final float UNIT_BTM = WORLD_BTM - UNIT_SIZE_S[1];
        public static final float UNIT_TOP = WORLD_TOP - UNIT_SIZE_S[1];
        public static final float[] UNIT_CENTER_BTM = {WORLD_CENTER[0] - UNIT_SIZE_S[0]/2, UNIT_BTM};
        public static final float[] UNIT_CENTER_TOP = {WORLD_CENTER[0] - UNIT_SIZE_S[0]/2, UNIT_TOP};

        //-----------------TOWER CONSTANTS--------------
        public static final float[] TOWER_SIZE_S = {10f, 15f};



        //------TOWER POSITIONS---------
        public static float TOWER_BTM_S = WORLD_BTM;
        public static float TOWER_TOP_S = WORLD_TOP - TOWER_SIZE_S[1];
        public static final float[] TOWER_S_CENTER_BTM = {WORLD_CENTER[0] + TOWER_SIZE_S[0], TOWER_BTM_S};
        public static final float[] TOWER_S_CENTER_TOP = {WORLD_CENTER[0] - TOWER_SIZE_S[0]/2, TOWER_TOP_S};

        public static final float[] TOWER_SIZE_L = {15f, 20f};

    }
}
