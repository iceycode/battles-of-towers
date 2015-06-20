package com.icey.btwrs.stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.icey.btwrs.assets.Assets;
import com.icey.btwrs.constants.Constants;
import com.icey.btwrs.constants.WorldConstants;
import com.icey.btwrs.world.Tower;
import com.icey.btwrs.world.TowerUnit;
import com.icey.btwrs.world.WorldRenderer;
import com.icey.btwrs.world.entity.Entity;
import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

/** Main stage for Game. Contains anything related to map and controllable characters in it, as well as
 *  other UI that interacts with it.
 *
 * @author Allen
 *         Created on 6/10/15.
 */
public class GameStage extends Stage{

    private final int TILE_SIZE  = WorldConstants.TILE_SIZE;
    private final float MAP_SCALE = 1/TILE_SIZE; //scale of map
    private final float ASPECT_RATIO = Constants.SCREEN_HEIGHT/Constants.SCREEN_WIDTH;

    //world width & height - based on tile size & number of rows & cols in tiled map
    private float worldWidth;
    private float worldHeight;

    protected Skin skin; //note: currently using visui default skin
    //viewport & camera for viewing
    protected Viewport viewport;
    protected OrthographicCamera camera;

    //World objects used in stage
    protected WorldRenderer renderer; //renders box2D objects in world
    protected World world; //from renderer
    protected Body groundBody; //ground body
    protected MouseJoint mouseJoint; //mouseJoin for moving bodies by mouse movement
    protected Body hitBody; //body that was touched
    protected Entity hitEntity; // entity that was hit

    //TiledMap path, map object and renderer
    protected String mapPath; //path of map
    protected TiledMap tiledMap; //the tiled map
    protected TiledMapRenderer mapRenderer; //tiledmap renderer

    //widgets for controlling units
    protected UnitTouchpad p1Touchpad;
    protected UnitTouchpad p2Touchpad;

    //units and towers on stage
    protected Array<TowerUnit> p1Units = new Array<TowerUnit>();
    protected Array<TowerUnit> p2Units = new Array<TowerUnit>();
    protected TowerUnit unitP1; //player 1 TowerUnit
    protected TowerUnit unitP2; //player 2 TowerUnit

    /** GameStage constructor with mapChoice
     *
     * @param mapPath : String value with path to chosen/set map
     */
    public GameStage(String mapPath){
        this.mapPath = mapPath; //set the mapPath
        this.skin = Assets.getInstance().getSkin(); //set the GameStage skin

        setCameraView();

        setTiledMap();
        setWorld();
        setTouchpads();
    }

    /** Sets up the camera & viewport
     *
     */
    protected void setCameraView(){

        if (mapPath.equals(WorldConstants.TEST_1A)){
            worldWidth = WorldConstants.COLS_A;
            worldHeight = WorldConstants.ROWS_A;
        }
        else{
            worldWidth = WorldConstants.COLS_B;
            worldHeight = WorldConstants.ROWS_A;
        }


        viewport = new ScalingViewport(Scaling.fill, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        camera = new OrthographicCamera(worldWidth, worldHeight * ASPECT_RATIO);
        viewport.setCamera(camera);

        setViewport(viewport);
        viewport.apply(true); //centers the camera so its position is at bottom left
    }


    protected void setTiledMap(){
        tiledMap = new TmxMapLoader().load(mapPath);
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }


    protected void setWorld(){
        renderer = new WorldRenderer(camera, worldWidth, worldHeight);
        bodiesFromMap(renderer.getWorld(), tiledMap); //create bodies from tiledMap
    }


    protected void setTouchpads(){
        p1Touchpad = new UnitTouchpad(skin, this, 1);
        p2Touchpad = new UnitTouchpad(skin, this, 2);

        addActor(p1Touchpad);
        addActor(p2Touchpad);

        //make invisible when starting
        p1Touchpad.setVisible(false);
        p2Touchpad.setVisible(false);
    }


    /** Adds tower unit to stage, also giving touchpad control
     *  Adds to an array of player units
     *
     * @param unit : unit to add
     */
    public void addTowerUnit(TowerUnit unit){
        if (unit.player == 1){
            p1Units.add(unit);
            p1Touchpad.controlUnit(unit); //set controllable unit for touchpad
            unitP1 = unit;
        }
        else{
            p2Units.add(unit);
            p2Touchpad.controlUnit(unit);
            unitP2 = unit;
        }

    }

    /** Adds bodies from TiledMap object layers
     *
     * @param world : world to add it into
     * @param tiledMap : tiledMap object
     */
    public void bodiesFromMap(World world, TiledMap tiledMap){

        Box2DMapObjectParser parser = new Box2DMapObjectParser(); //map parser
        parser.load(world, tiledMap);
    }



    /** Adds all towers to stage, setting player names as well
     *
     * @param towerMap : towers in an objectMap, keys being player names
     */
    public void setTowers(ObjectMap<String, Array<Tower>> towerMap){

        while(towerMap.keys().hasNext()){
            String playerName = towerMap.keys().next();
            Array<Tower> towers = towerMap.get(playerName);

            for (Tower tower : towers){
                addActor(tower);
            }
        }

    }

    protected void renderTiledMap(){


        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
    }


    @Override
    public void act(float delta) {
        super.act(delta);

        renderTiledMap(); //renders tiled map view
        renderer.render(delta); //render the box2d physics world

    }

    /** we instantiate this vector and the callback here so we don't irritate the GC **/
    Vector3 testPoint = new Vector3();
    QueryCallback callback = new QueryCallback() {
        @Override
        public boolean reportFixture (Fixture fixture) {
            // if the hit point is inside the fixture of the body
            // we report it
            if (fixture.testPoint(testPoint.x, testPoint.y)) {
                hitBody = fixture.getBody();
                hitEntity = (Entity) hitBody.getUserData();

                return false;
            } else
                return true;
        }
    };


    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        // translate the mouse coordinates to world coordinates
        camera.unproject(testPoint.set(x, y, 0));
        // ask the world which bodies are within the given
        // bounding box around the mouse pointer
        hitBody = null;

        // Query world for all fixtures that potentially overlap the provided body that was hit
        getWorld().QueryAABB(callback, testPoint.x - 0.0001f, testPoint.y - 0.0001f, testPoint.x + 0.0001f, testPoint.y
                + 0.0001f);

        if (hitBody == groundBody) hitBody = null;

        // ignore kinematic bodies, they don't work with the mouse joint
        if (hitBody != null && hitBody.getType() == BodyDef.BodyType.KinematicBody) return false;

        // if we hit something we create a new mouse joint
        // and attach it to the hit body.
        if (hitBody != null) {
            MouseJointDef def = new MouseJointDef();
            def.bodyA = groundBody;
            def.bodyB = hitBody;
            def.collideConnected = true;
            def.target.set(testPoint.x, testPoint.y);
            def.maxForce = 1000.0f * hitBody.getMass();

            mouseJoint = (MouseJoint)world.createJoint(def);
            hitBody.setAwake(true);
        }

        return super.touchDown(x, y, pointer, button);
    }

    /** another temporary vector **/
    Vector2 target = new Vector2();

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
        // if a mouse joint exists we simply update
        // the target of the joint based on the new
        // mouse coordinates
        if (mouseJoint != null) {
            camera.unproject(testPoint.set(x, y, 0));
            mouseJoint.setTarget(target.set(testPoint.x, testPoint.y));
        }
        return super.touchDragged(x, y, pointer);
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        // if a mouse joint exists we simply destroy it
        if (mouseJoint != null) {
            world.destroyJoint(mouseJoint);
            mouseJoint = null;
        }
        return super.touchUp(x, y, pointer, button);
    }

    @Override
    public boolean mouseMoved (int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled (int amount) {
        return false;
    }


    public World getWorld(){
        return renderer.getWorld();
    }

//  NOTE: alternative controls for unit that rely on keyboard
//    @Override
//    public boolean keyDown(int keyCode) {
//
//        if (keyCode == Input.Keys.S)
//            moveUnitP1(-3f, 0);
//        else if (keyCode == Input.Keys.W)
//            moveUnitP1(3f, 0);
//
//        if (keyCode == Input.Keys.A)
//            moveUnitP1(-90, 0);
//        else if (keyCode == Input.Keys.)
//
//        switch(keyCode){
//            case Input.Keys.A:
//                if (unitP1!= null)
//                    moveUnitP1(3, 0);
//                break;
//            case Input.Keys.S:
//        }
//
//        return true;
//    }
}
