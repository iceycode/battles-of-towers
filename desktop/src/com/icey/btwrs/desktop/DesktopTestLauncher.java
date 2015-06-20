package com.icey.btwrs.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.icey.btwrs.constants.Constants;
import com.icey.btwrs.tests.TestGame;

/** Launches test game
 *
 * @author Allen
 *         Created on 6/16/15.
 */
public class DesktopTestLauncher {

    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = Constants.GAME_TITLE;
        config.height = (int)Constants.SCREEN_HEIGHT;
        config.width = (int)Constants.SCREEN_WIDTH;

        new LwjglApplication(new TestGame(), config);
    }

}
