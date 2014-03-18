package com.fisherevans.build;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Author: Fisher Evans
 * Date: 2/19/14
 */
public class Packager {
    private static String[] include = new String[] {
            "res",
            "dll",
            "SmashBash.jar",
            "Start.bat"
    };

    private static String output = "out/SmashBash";

    public static void main(String[] args) {
        pack(incrementBuildNumber("res/build.txt"));
    }

    public static void pack(String buildNumber) {
        String outputFilename = output + "-" + buildNumber + ".zip";
        System.out.println("Packaging game into: " + outputFilename);
        try {
            ArrayList<File> files = new ArrayList<File>();
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
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
            parameters.setRootFolderInZip("SmashBash-" + buildNumber);

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

    private static String incrementBuildNumber(String buildNumberFile) {
        System.out.println("Finding build number...");
        try {
            File buildFile = new File(buildNumberFile);

            Scanner in = new Scanner(buildFile);
            Integer buildNumber = Integer.parseInt(in.nextLine()) + 1;
            in.close();


            FileWriter out = new FileWriter(buildFile);
            out.append(buildNumber.toString() + "\n");
            out.append(new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date()));
            out.close();

            return buildNumber.toString();
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
