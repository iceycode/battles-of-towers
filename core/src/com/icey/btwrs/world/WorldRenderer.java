package com.icey.btwrs.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.icey.btwrs.utils.BodyUtils;
import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

/** Renders the world that contains all bodies that interact in game
 *
 * NOTE: no gravity, since this game has a top-down view of units that are controlled
 *
 * @author Allen
 *         Created on 6/15/15.
 */
public class WorldRenderer {

    private final float TIME_STEP = 1/100f;
    private final int VEL_ITERS = 6;
    private final int POS_ITERS = 2;


    private float worldWidth;
    private float worldHeight;


    World world;
    Body ground; //ground body
    Box2DDebugRenderer debugRenderer;
    Box2DMapObjectParser mapParser;
    OrthographicCamera camera;

    private float accumulator = 0; //for fixing time step


    public WorldRenderer(OrthographicCamera camera, float width, float height){
        this.worldWidth = width;
        this.worldHeight = height;
        this.camera = camera;

        Vector2 gravity = new Vector2(0, 0); //NO GRAVITY
        this.world = new World(gravity, true);

        this.debugRenderer = new Box2DDebugRenderer();

        setWorldGround(width, height);
    }


    /** Sets up the ground
     *
     * @param width : width of ground
     * @param height : height of ground
     */
    public void setWorldGround(float width, float height){
        this.worldWidth = width;
        this.worldHeight = height;

        ground = BodyUtils.createGround(world, worldWidth, worldHeight);
    }



    /** Main render method
     *
     * @param deltaTime : time from screen
     */
    public void render(float deltaTime){
        debugRenderer.render(world, camera.combined);

        doPhysicsStep(deltaTime);
    }



    public void doPhysicsStep(float deltaTime){
        // a fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;

        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VEL_ITERS, POS_ITERS);
            accumulator -= TIME_STEP;
        }

    }


    /** Returns a world for adding bodies to
     *
     * @return : world that is being rendered
     */
    public World getWorld(){
        return world;
    }


    /** Returns ground for attaching mouse joints to when moving a TowerUnit body
     *
     * @return : groudn body
     */
    public Body getGround(){
        return ground;
    }


    /** World data representing what object in game
     *
     */
    public static class WorldData{

        public static Array<Tower> p1Towers;
        public static Array<Tower> p2Towers;

        public static ObjectMap<String, TowerUnit> p1Units;
        public static ObjectMap<String, TowerUnit> p2Units;


    }
}
