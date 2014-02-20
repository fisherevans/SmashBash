package com.fisherevans.build;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Author: Fisher Evans
 * Date: 2/19/14
 */
public class Builder {
    public static void main(String[] args) {
        ImageGenerator.generateImages("128 16,32,48,64,96,128 res/img/sprites/characters png".split(" "));
        ImageGenerator.generateImages("128 16,32,48,64,96,128 res/maps png".split(" "));
        String buildNumber = incrementBuildNumber();
        Packager.pack(buildNumber);
    }

    private static String buildNumberFile = "res/build.txt";

    private static String incrementBuildNumber() {
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
