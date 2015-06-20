package com.icey.btwrs.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kotcrab.vis.ui.VisUI;

/** Contains and manages assets
 *
 *  TODO: create custom skin, for now just using VisUI default skin
 *
 * @author Allen
 *         Created on 6/10/15.
 */
public class Assets {

    private static Assets instance;

    Skin skin;
    AssetManager assetManager;

    public Assets(){


        initAssets();
    }


    public static Assets getInstance() {
        if (instance == null)
            instance = new Assets();

        return instance;
    }


    /** Initializes assets
     *
     * TODO: finish this up
     */
    public void initAssets(){
        assetManager = new AssetManager();
        VisUI.load(); //load VisUI

        skin = VisUI.getSkin(); //set skin to VisUI skin for now
    }


    public boolean isDoneLoading(){


        return assetManager.update() || skin != null; //FIXME: change after initial tests
    }


    //switches b/w different skins for user
    public void switchSkin(){

    }

    public Skin getSkin(){
        return skin;
    }


    /** Disposes all assets
     *
     */
    public void dispose(){
        VisUI.dispose(); //dispose VisUI
        skin.dispose(); //dispose other skins
    }


    private void log(String message){
        System.out.println("Assets LOG: " + message);
    }
}
