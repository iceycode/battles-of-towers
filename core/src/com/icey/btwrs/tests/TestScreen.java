package com.icey.btwrs.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.icey.btwrs.constants.Constants;
import com.icey.btwrs.constants.WorldConstants;
import com.icey.btwrs.stage.GameStage;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.dialog.DialogUtils;
import com.kotcrab.vis.ui.widget.MenuItem;
import com.kotcrab.vis.ui.widget.PopupMenu;

/**
 * @author Allen
 *         Created on 6/10/15.
 */
public class TestScreen implements Screen{

    Stage testStage; //test stage containing UI for running tests
    GameStage stage; //main stage for game
    PopupMenu mainMenu; //a menu to display anywhere on stage

    Vector3 touchPoint; //touch down location
    boolean instructShown = false; //if true, popup dialog showed instructions

    final String TEST_START_MSG = "Right click to access test menu";
    OrthographicCamera camera;

    InputMultiplexer multiplexer; //input multiplexer
    float time; //time game is running


    public TestScreen(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        stage = new GameStage(WorldConstants.TEST_1B);
        testStage = new Stage();
        testStage.setViewport(stage.getViewport()); //set same viewport as stage

        //add stages to InputMultiplexer
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(testStage);

        Gdx.input.setInputProcessor(multiplexer); //set the input processor



        touchPoint = new Vector3(0,0,0); //initialize touchPoint

        setTestPopup();
    }


    /** TODO: add more test items here
     *
     */
    public void setTestPopup(){
        mainMenu = new PopupMenu(); //root popup menu
        mainMenu.setSize(100, 50); //size of popup menu

        //on right click, popup menu appears
        mainMenu.addListener(new ClickListener(Input.Buttons.RIGHT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                log("Right clicked screen");

                //change default location of popupmenu if out of bounds when clicked
                if (x + mainMenu.getWidth() > Constants.SCREEN_WIDTH)
                    x -= mainMenu.getWidth();

                if (y - mainMenu.getHeight() < 0)
                    y += mainMenu.getHeight();

                mainMenu.showMenu(testStage, x, y); //shows menu at locations x, y
            }
        });


        //popup menu containing tests
        final PopupMenu testMenu = new PopupMenu();
        final MenuItem item = new MenuItem("Test setup: Bottom 1 Unit 1 Tower");
        item.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (item.isPressed()){
                    TestUtils.testUnitTowerBtmSetup(stage);
                    mainMenu.remove(); //remove from stage
                }
            }
        });
        testMenu.add(item); //add item to popupMenu


        //create item to hold tests submenu
        MenuItem tests = new MenuItem("Tests");
        tests.setSubMenu(testMenu); //test menu item - automatically shows testMenu when mouse is over

        mainMenu.add(tests); //add tests item
        mainMenu.row();

        //exit game item
        final MenuItem exitItem = new MenuItem("Exit");
        exitItem.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); //exit the application
            }
        });
        mainMenu.add(exitItem);
    }


    public void showPopupMenu(){
        float x = touchPoint.x;
        float y = touchPoint.y;

        //change default location of popupmenu if out of bounds when clicked
        if (x + mainMenu.getWidth() > Constants.SCREEN_WIDTH)
            x -= mainMenu.getWidth();

        if (y - mainMenu.getHeight() < 0)
            y += mainMenu.getHeight();

        mainMenu.showMenu(testStage, x, y); //shows menu at locations x, y
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer); //set the input processor

        //draw stages here
        stage.draw();
        testStage.draw();
    }

    @Override
    public void render(float delta) {
        time += delta;

        if (!instructShown){
            DialogUtils.showOKDialog(testStage, "Test Dialog", TEST_START_MSG);
            instructShown = true;
        }


        if (Gdx.input.justTouched() ){
            if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
                log("Right clicked screen");
                camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                showPopupMenu();
            }
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                mainMenu.remove();
            }
        }

        stage.act(delta);
        testStage.act();

        show(); //show method
    }

    @Override
    public void resize(int width, int height) {
        log("resized to : " + width + "x" + height);

        stage.getViewport().update(width, height);
        stage.getViewport().apply();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        testStage.dispose();
        VisUI.dispose(); //dispose visui skin

    }


    private void log(String message){
        Gdx.app.log("TestScreen LOG", message);
    }
}
