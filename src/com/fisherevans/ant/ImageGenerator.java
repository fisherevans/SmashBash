package com.fisherevans.ant;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Author: Fisher Evans
 * Date: 2/17/14
 */
public class ImageGenerator {
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
                    for(int size:newSizes) {
                        outputFolder = new File(dir + "/" + size);
                        if(!outputFolder.exists())
                            outputFolder.mkdir();
                        outputFile = new File(dir + "/" + size + "/" + baseFileName + "." + format);
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
}
