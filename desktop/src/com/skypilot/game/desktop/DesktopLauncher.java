package com.skypilot.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.skypilot.game.SkyPilot;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1000;
		config.height = 1000;
//		config.fullscreen = true;
		config.pauseWhenMinimized = true;
		new LwjglApplication(new SkyPilot(), config);
	}
}
