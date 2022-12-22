package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.demo.demo1;

import javax.naming.directory.SearchResult;
import java.awt.*;
import java.io.Serializable;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher2 implements Serializable {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Tank Stars");
		config.setWindowedMode(1260, 660);
        config.setWindowSizeLimits(1260, 660, 1260, 660);
//		config.setMaximized(true);
        new Lwjgl3Application(new TankStars(), config);
    }
}

