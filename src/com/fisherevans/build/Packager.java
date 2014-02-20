package com.fisherevans.build;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fisher Evans
 * Date: 2/19/14
 */
public class Packager {
    private static String[] include = new String[] {
            "res",
            "dll",
            "WIPGame.jar",
            "Start.bat"
    };
    private static String output = "out/WIPGame";

    public static void pack(String buildNumber) {
        String outputFilename = output + "-" + buildNumber + ".zip";
        System.out.println("Packaging game into: " + outputFilename);
        try {
            ArrayList<File> files = new ArrayList<>();
            File tempFile;
            for(String fileName:include) {
                tempFile = new File(fileName);
                if(!tempFile.exists()) {
                    System.out.println(fileName + " does not exist! Exiting...");
                    System.exit(1);
                } else {
                    files.add(tempFile);
                }
            }
            File zipFile = new File(outputFilename);
            if(zipFile.exists())
                zipFile.delete();

            ZipFile zipArchive = new ZipFile(outputFilename);
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters.setIncludeRootFolder(true);

            for(File file:files) {
                if(file.isDirectory())
                    zipArchive.addFolder(file, parameters);
                else
                    zipArchive.addFile(file, parameters);
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
