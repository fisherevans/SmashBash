package com.fisherevans.build;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Author: Fisher Evans
 * Date: 2/17/14
 */
public class ImageGenerator {
    public static String GENERATE_IMAGES_PREFIX = "128 16,32,48,64,96,128 ";

    public static void main(String[] args) {
        generateImages((GENERATE_IMAGES_PREFIX + "res/img/sprites/characters png").split(" "));
        generateImages((GENERATE_IMAGES_PREFIX + "res/img/sprites/entities png").split(" "));
        generateImages((GENERATE_IMAGES_PREFIX + "res/img/sprites/gui png").split(" "));
        generateImages((GENERATE_IMAGES_PREFIX + "res/maps png").split(" "));
    }

    public static void generateImages(String[] args) {
        int baseSize = Integer.parseInt(args[0]);

        String[] newSizesSplit = args[1].split(",");
        int[] newSizes = new int[newSizesSplit.length];
        for(int id = 0;id < newSizesSplit.length;id++)
            newSizes[id] = Integer.parseInt(newSizesSplit[id]);

        String dir = args[2];
        String format = args[3];

        ImageGenerator.run(baseSize, newSizes, dir, format);
    }

    public static void run(int baseSize, int[] newSizes, String dir, String format) {
        System.out.println("Generating images for " + format + " images in " + dir);
        try {
            File baseFolder = new File(dir);
            for(File child:baseFolder.listFiles()) {
                String baseFileName;
                if(child.isFile() && child.getName().endsWith(format)) {
                    baseFileName = child.getName().replace("." + format, "");
                    BufferedImage baseImage = ImageIO.read(new File(dir + "/" + baseFileName + "." + format)), temp;
                    float scale;
                    int width, height;
                    File outputFile, outputFolder;
                    makeFolder(dir + "/re-sized");
                    for(int size:newSizes) {
                        makeFolder(dir + "/re-sized/" + size);
                        outputFile = new File(dir + "/re-sized/" + size + "/" + baseFileName + "." + format);
                        scale = ((float)size)/((float)baseSize);
                        width = (int)(baseImage.getWidth()*scale);
                        height = (int)(baseImage.getHeight()*scale);
                        temp = Scalr.resize(baseImage, Scalr.Method.QUALITY, width, height);
                        ImageIO.write(temp, format, outputFile);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(5);
        }
    }

    private static File makeFolder(String ref) {
        File folder = new File(ref);
        if(!folder.exists())
            folder.mkdir();
        return folder;
    }
}
