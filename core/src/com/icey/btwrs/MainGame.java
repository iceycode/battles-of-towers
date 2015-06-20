package com.icey.btwrs;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.icey.btwrs.assets.Assets;
import com.icey.btwrs.screens.GameScreen;
import com.icey.btwrs.tests.TestScreen;

/** Main Game class that starts up the game and is in charge of rendering screens. Also contains static
 *  global fields that are responsible for loading assets and saving any game data.
 *
 * @author Allen
 * 		Created on 06/10/2015.
 */
public class MainGame extends Game {

	TestScreen testScreen;
	GameScreen gameScreen;
	Assets assets;
	
	@Override
	public void create () {
		assets = Assets.getInstance(); //initialize Assets
	}

	@Override
	public void render () {
		if (assets.isDoneLoading()){
			Gdx.gl.glClearColor(0, 1, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			super.render(); //this renders the screen if it is not null

			if (testScreen == null){
				log("test screen setup");
				testScreen = new TestScreen();
				setScreen(testScreen); //set test screen
			}
		}
	}


	@Override
	public void dispose() {
		Assets.getInstance().dispose(); //dispose any extra assets
		screen.dispose();
	}

	private void log(String message){
		Gdx.app.log("MainGame LOG", message);
	}
}
