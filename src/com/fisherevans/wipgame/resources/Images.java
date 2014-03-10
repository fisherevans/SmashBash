package com.fisherevans.wipgame.resources;

import com.fisherevans.wipgame.log.Log;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class Images {
    private final static String IMAGE_LOCATIONS = "res/img";

    public final static Log log = new Log(Images.class);

    private static Map<String, Image> _images = null;

    public static void load() throws SlickException, IOException {
        _images = new HashMap<>();
        loadImages(IMAGE_LOCATIONS);
    }

    private static void loadImages(String prefix) {
        File folder = new File(prefix);
        String key;
        Image image;
        if(folder.isDirectory()) {
            for(File child:folder.listFiles()) {
                if(child.isFile() && child.getName().matches((".*\\.(png|jpg|jpeg|gif)"))) {
                    try {
                        key = prefix + "/" + child.getName().replaceAll("\\..*", "");
                        image = getAbsoluteImage(child.getCanonicalPath());
                        _images.put(key, image);
                    } catch (SlickException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    loadImages(prefix + "/" + child.getName());
                }
            }
        }
    }

    public static Image getImage(String key) {
        Image img = _images.get(IMAGE_LOCATIONS + "/" + key);
        if(img == null)
            log.error("Failed to get Image: " + IMAGE_LOCATIONS + "/" + key);
        return img;
    }

    public static Image getAbsoluteImage(String location) throws SlickException {
        return new Image(location, false, Image.FILTER_LINEAR);
    }
}
