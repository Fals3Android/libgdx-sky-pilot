package com.skypilot.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.skypilot.game.SkyPilot;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1000;
		config.height = 1000;
//		config.fullscreen = true;
		config.pauseWhenMinimized = true;

		//TODO: ths texture packer should really be for dev builds only
		Settings settings = new Settings();
		settings.maxWidth = 512;
		settings.maxHeight = 512;
		TexturePacker.process(settings, "./core/assets", "./core/assets/packed", "game");

		new LwjglApplication(new SkyPilot(), config);
	}
}
