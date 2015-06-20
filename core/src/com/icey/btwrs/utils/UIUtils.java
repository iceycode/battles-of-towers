package com.icey.btwrs.utils;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.util.dialog.DialogUtils;

/** Contains methods for creating various widgets. Unlike my previous projects, I am making things a lot simpler and
 *  using VisUI libgdx plugin for a lot of widget creation unless I need to create my own custom widget.
 *
 * @author Allen
 *         Created on 6/10/15.
 */
public class UIUtils {

    /** See {@link com.kotcrab.vis.ui.util.dialog.DialogUtils.ErrorDialog#showErrorDialog(Stage, String, Exception)}
     *  Shows text and then if details expanded, the exception stack trace.
     *
     * @param errMessage : error message
     * @param exception : exception that occured
     * @param stage : stage exception occured on
     */
    public static void showErrorAny(String errMessage, Exception exception, Stage stage){
        DialogUtils.showErrorDialog(stage, errMessage, exception);
    }



}
