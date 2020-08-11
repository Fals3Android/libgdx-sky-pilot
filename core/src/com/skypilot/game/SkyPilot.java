package com.skypilot.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;


public class SkyPilot extends Game {
	MainMenu menu;
	Level level;
	Player player;
	Boss boss;
	float fadeOutScreenTiming = 0.5f;
	float fadeInScreenTiming = 0.0f;
	boolean levelLoaded = false;

	@Override
	public void create () {
		int windowWidth = Gdx.graphics.getWidth();
		int windowHeight = Gdx.graphics.getHeight();
		menu = new MainMenu(windowWidth, windowHeight);
	}

	@Override
	public void render () {
//		if(!menu.hasPlayerMadeChoice) {
			Gdx.gl.glClearColor(0, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			menu.drawMenu();
//			return;
//		}

		//TODO: need an easy way to bypass screen fade
//		if(fadeOutScreenTiming <= 0.0f) {
//			fadeOutScreenTiming -= 0.01f;
//			Gdx.gl.glClearColor(0, fadeOutScreenTiming, fadeOutScreenTiming, 1);
//			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//			return;
//		}
//
//		if(fadeInScreenTiming < 1.0f) {
//			fadeInScreenTiming += 0.005f;
//			Gdx.gl.glClearColor(0, fadeInScreenTiming, fadeInScreenTiming, 1);
//			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//			return;
//		}

//		if(!levelLoaded) {
//			player = new Player(menu.chosenPlane);
//			boss = new Boss();
//			level = new Level(player, boss);
//			levelLoaded = true;
//			return;
//		}
//
//		level.drawLevel();
	}
	
//	@Override
//	public void dispose () { }
}
