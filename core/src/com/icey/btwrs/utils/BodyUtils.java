package com.icey.btwrs.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.icey.btwrs.constants.Constants;
import com.icey.btwrs.constants.WorldConstants;
import com.icey.btwrs.world.entity.TowerEntity;
import com.icey.btwrs.world.entity.UnitEntity;

/** Creates Box2D Body instances for World
 *
 * @author Allen
 *         Created on 6/10/15.
 */
public class BodyUtils {



    /** Gets position of Body in center of the screen
     *
     * @param width : width of body
     * @param height : height of body
     * @return : position as a Vector2 object
     */
    public static Vector2 screenCenterPosition(float width, float height){
        Vector2 pos = new Vector2();

        pos.x = Constants.SCREEN_WIDTH/2 - width/2;
        pos.y = Constants.SCREEN_HEIGHT/2 - height/2;

        return pos;
    }


    /** Creates a ground body for the world with an EdgeShape object
     *
     * @param world : world rendered in tiles
     * @param width : width of map in tiles
     * @param height : height of map
     * @return : a ground body in which towers exist & units move in
     */
    public static Body createGround(World world, float width, float height){
        float k_restitution = 0.4f;
        Body ground;

        //coordinates for edges
        float[] topLeft = {- width/2, height/2};
        float[] topRight ={width/2, height/2};
        float[] bottomLeft = {-width/2, - height/2};
        float[] bottomRight = {width/2, -height/2};


        BodyDef bd = new BodyDef();
        bd.position.set(screenCenterPosition(width, height));
        ground = world.createBody(bd);

        EdgeShape shape = new EdgeShape();

        FixtureDef sd = new FixtureDef();
        sd.shape = shape;
        sd.density = WorldConstants.Body.GROUND_DENSITY;
        sd.restitution = k_restitution;

        shape.set(new Vector2(bottomLeft[0], bottomLeft[1]), new Vector2(topLeft[0], topLeft[1]));
        ground.createFixture(sd);

        shape.set(new Vector2(bottomRight[0], bottomRight[1]), new Vector2(topRight[0], topRight[1]));
        ground.createFixture(sd);

        shape.set(new Vector2(topLeft[0], topLeft[1]), new Vector2(topRight[0], topRight[1]));
        ground.createFixture(sd);

        shape.set(new Vector2(bottomLeft[0], bottomLeft[1]), new Vector2(bottomRight[0], bottomRight[1]));
        ground.createFixture(sd);

        shape.dispose();

        return ground;
    }


    public static Body unitBody(World world, float width, float height, Vector2 position, String name){

        float sizeModX = WorldConstants.TILE_SIZE;
        float sizeModY = WorldConstants.TILE_SIZE;

        Body unitBody; //moving body (will be a unit)

        PolygonShape poly3 = new PolygonShape();
        poly3.setAsBox(width, height);

        FixtureDef fd = new FixtureDef();
        fd.shape = poly3;
        fd.density = WorldConstants.Body.UNIT_S_DENSITY; //density is kg/m^2 -
        fd.friction = 0.3f;

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody; //set as static
        bd.position.set(position); //set position
        bd.allowSleep = true; //allows body to sleep

        unitBody = world.createBody(bd); //create the body
        unitBody.createFixture(fd);

        UnitEntity unitEntity = new UnitEntity(width * sizeModX, height * sizeModY, name);
        unitBody.setUserData(unitEntity);

        return unitBody;
    }



    public static Body towerBody(World world, float width, float height, Vector2 position, String name){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1.0f;
        fd.friction = 0.3f;

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody; //set as static
        bd.position.set(position); //set position


        Body body = world.createBody(bd); //create the body
        body.createFixture(fd); //set the fixture

        //entities are set with SCREEN actor positions
        TowerEntity entity = new TowerEntity(WorldConstants.Body.TOWER_SIZE_S[0] * WorldConstants.TILE_SIZE,
                WorldConstants.Body.TOWER_SIZE_S[1] * WorldConstants.TILE_SIZE, name);
        body.setUserData(entity);

        shape.dispose();

        return body;
    }


    /** See {@link World#createJoint(JointDef)} for more info. Basically creates a joint b/w two bodies
     *  TODO: look more into this
     *
     *
     * @param world : world where bodies & joint will exist
     * @param body1 : body 1 - the body that is moving relative to other body
     * @param body2 : body 2 - a static body to which body1 is joined (eg, the ground)
     */
    public static void frictionJoint(World world, Body body1, Body body2){
        float gravity = 10.0f;
        float I = body1.getInertia();
        float mass = body1.getMass();

        float radius = (float)Math.sqrt(2 * I / mass);

        FrictionJointDef jd = new FrictionJointDef();
        jd.localAnchorA.set(0, 0);
        jd.localAnchorB.set(0, 0);
        jd.bodyA = body2;
        jd.bodyB = body1;
        jd.collideConnected = true;
        jd.maxForce = mass * gravity;
        jd.maxTorque = mass * radius * gravity;

        world.createJoint(jd);
    }


    public static void unitBodyTriangle(World world, Vector2 position){
        Body unitBody;

        Transform xf1 = new Transform(new Vector2(), 0.3524f * (float)Math.PI);
        xf1.setPosition(xf1.mul(new Vector2(1, 0)));

        Vector2[] vertices = new Vector2[3];
        vertices[0] = xf1.mul(new Vector2(-1, 0));
        vertices[1] = xf1.mul(new Vector2(1, 0));
        vertices[2] = xf1.mul(new Vector2(0, 0.5f));

        PolygonShape poly1 = new PolygonShape();
        poly1.set(vertices);

        FixtureDef sd1 = new FixtureDef();
        sd1.shape = poly1;
        sd1.density = 4.0f;

        Transform xf2 = new Transform(new Vector2(), -0.3524f * (float)Math.PI);
        xf2.setPosition(xf2.mul(new Vector2(-1, 0)));

        vertices[0] = xf2.mul(new Vector2(-1, 0));
        vertices[1] = xf2.mul(new Vector2(1, 0));
        vertices[2] = xf2.mul(new Vector2(0, 0.5f));

        PolygonShape poly2 = new PolygonShape();
        poly2.set(vertices);

        FixtureDef sd2 = new FixtureDef();
        sd2.shape = poly2;
        sd2.density = 2.0f;

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.angularDamping = 5.0f;
        bd.linearDamping = 0.1f;

        bd.position.set(position);
        bd.angle = (float)Math.PI;
        bd.allowSleep = false;
        unitBody = world.createBody(bd);
        unitBody.createFixture(sd1);
        unitBody.createFixture(sd2);

        poly1.dispose();
        poly2.dispose();
    }


    /** Creates the body for a TowerUnit Actor
     *
     * @param world
     * @return
     */
    public static Body createUnitBody(World world) {
	/* #some doc
	 * A body definition holds all the data needed to construct a rigid body.
	 * You can safely re-use body definitions. Shapes are added to a body after construction.
	 */
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
//        bodyDef.position.set(new Vector2(Constants.RUNNER_X, Constants.RUNNER_Y + Constants.RUNNER_WIDTH_PIXELS));

        //defines a new polygon shape for enclosing runnerc
        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(Constants.RUNNER_WIDTH /2, Constants.RUNNER_HEIGHT *10/2);
//        shape.setAsBox(Constants.RUNNER_WIDTH_PIXELS/2, Constants.RUNNER_HEIGHT_PIXELS/2);

        //   shape.setAsBox(Constants.RUNNER_WIDTH, Constants.RUNNER_HEIGHT);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.6f;

        //   fixtureDef.restitution = 0.1f; // Make it bounce a little bit

        Body body = world.createBody(bodyDef);

//        body.createFixture(shape, Constants.RUNNER_DENSITY);
//        body.setGravityScale(Constants.RUNNER_GRAVITY_SCALE);


        //adjusts the mass of this body
        //MassData massData =  new MassData();
        //massData.mass = 0.0f; //mass is 1 kilogram
        //body.setMassData(massData);
        body.resetMassData();

//        body.setUserData(new RunnerUserData(Constants.RUNNER_WIDTH , Constants.RUNNER_HEIGHT ));
        //body.setUserData(new RunnerUserData(Constants.RUNNER_WIDTH , Constants.RUNNER_HEIGHT ));


        shape.dispose();

        return body;
    }


    /** creates an obstacle based on parameter dimension & position
     *
     * @param world
     * @param width
     * @param height
     * @param posX
     * @param posY
     * @return
     */
    public static Body createObstacleBody(World world, float width, float height, float posX, float posY){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        //sets position  either high or low
        bodyDef.position.set(new Vector2(posX, posY));

        //defines a new polygon shape for enclosing runner
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        Body body = world.createBody(bodyDef);
//        body.createFixture(shape, Constants.OBSTACLE_DENSITY);
        body.resetMassData();

//        body.setUserData(new ObstacleUserData(width, height));

        shape.dispose();

        return body;

    }


    /** Creates a wall BodyActor at certain position, so that a TowerUnit cannot move past it.
     *
     * @param world : world this body will be put in
     * @param x : x position in world
     * @param y : y position in world
     * @return
     */
    public static Body createWallBody(World world, float x, float y){
        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(x, y);
        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(32f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

        // Create our body in the world using our body definition
        Body body = world.createBody(bodyDef);
//        body.setUserData(new ObstacleUserData(circle.getRadius()*2, circle.getRadius()*2));
        body.createFixture(fixtureDef);

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();

        return body;
    }


}
