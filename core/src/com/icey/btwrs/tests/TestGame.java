package com.icey.btwrs.tests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.icey.btwrs.assets.Assets;

/** Test Game. This will contain the same essential functionality as Maingame, except that it will
 *  go directly to doing whatever it has to do on TestScreen.
 *
 *  If running any tests here, need to launch DesktopTestLauncher, instead of DesktopLauncher.
 *
 *
 * @author Allen
 *         Created on 6/10/15.
 */
public class TestGame extends Game{

    TestScreen testScreen;
    Assets assets;

    @Override
    public void create() {
        assets = Assets.getInstance(); //initialize Assets

    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render(); //this renders the screen if it is not null

        if (assets.isDoneLoading()){
            if (testScreen == null){
                testScreen = new TestScreen();
                setScreen(testScreen); //set the screen
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose(); //will hide the screen
        getScreen().dispose(); //disposes the screen
    }
}
