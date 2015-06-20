package com.icey.btwrs.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.io.File;

/** Creates assets using Pixmap
 *
 * @author Allen
 *         Created on 6/11/15.
 */
public class AssetHelper {

    //NOTE: FreeType will not work in HTML5
    public static BitmapFont fontFNTGenerator(String fontPath, int size, Color color) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;

        //1st retro font
        BitmapFont font = generator.generateFont(parameter); // font size 12 pixels
        font.setColor(color);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        return font;
    }

    public static Pixmap createPixmap(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        // pixmap.drawRectangle(200, 200, width, height);
        pixmap.setColor(color);
        pixmap.fill(); // fill with the color

        return pixmap;
    }


    /** Creates a PNG file using Pixmap
     *
     * @param pixmap : pixmap that will be turned into a png
     * @param dir: directory of new file - needs to be ABSOLUTE
     * @param fileName : name of the new file
     */
    public static void pixmapToFile(Pixmap pixmap, String dir, String fileName){

        //first check if file exists
        try{
            FileHandle fhCheck = Gdx.files.absolute(dir + fileName);
            if (!fhCheck.exists()){
                File file = new File(dir, fileName);
                FileHandle fileHandle = new FileHandle(file);

                PixmapIO.writePNG(fileHandle, pixmap);
            }
        }
        catch(Exception e){
            System.out.println("File already exists!");
            e.printStackTrace();
        }
    }


    /**
     * Gets the map by name
     *
     * //TODO: CREATE MORE TILED MAPS for testing!
     * @param name : name of tiled map
     * @return new tiledMap object from Tmx file
     */
    public static TiledMap getTiledMap(String name) {
        return new TmxMapLoader().load(name);
    }
}
