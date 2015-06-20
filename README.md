#README#

A cross platform simple tower defense-attack game for head-to-head play and multiplayer as well.

##Setup##

Using Libgdx with several 3rd party tools:



###VisUI###

An extension for libgdx UI which enables easy creation of custom scene2d.ui widgets. It is very easy to use and 
contains a lot of tools that I have been programming myself in the past (such as DialogUtils, which shows a popup 
dialog with buttons). You can load your own skins and use your own bundles for VisUI. 

It also comes with a level editor, but is NOT a UI editor. 

To use own bundle, copy default bundles to project and change what is needed, then load new bundle and set it for VisUI:

    VisUI.setFileChooserBundle(I18NBundle) //sets file chooser bundle
    VisUI.setDialogUtilsBundle(I18NBundle) //sets dialog utils bundle
    VisUI.setTabbedPaneBundle(I18NBundle) //sets tabbed pane bundle
    VisUI.setColorPickerBundle(I18NBundle) //sets color picker bundle (since 0.7.7)

See [wiki](https://github.com/kotcrab/VisEditor/wiki) for more info.