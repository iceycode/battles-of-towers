package com.icey.btwrs.ui;

import com.kotcrab.vis.ui.VisUI;

/** Manages various widgets on Screen for VisUI, a third-party extension for libgdx
 *
 * @author Allen
 *         Created on 6/10/15.
 */
public class VisWidgetManager {


    private static VisWidgetManager instance;


    public VisWidgetManager(){
        VisUI.load(); //to load skin
    }

    /** Returns VisWidgetManager
     *
     * @return : the manager
     */
    public VisWidgetManager getInstance(){
        if (instance==null)
            instance = new VisWidgetManager();

        return instance;
    }

    /** Disposes the VisUI skin
     *
     */
    public void disposeVisUI(){
        VisUI.dispose();
    }

}
