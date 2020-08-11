package com.skypilot.game;

import com.badlogic.gdx.Game;
import com.skypilot.game.screens.MainMenu;

public class SkyPilot extends Game {
	@Override
	public void create () {
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () { }
}
