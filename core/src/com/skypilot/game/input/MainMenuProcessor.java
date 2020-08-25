package com.skypilot.game.input;

import com.badlogic.gdx.InputProcessor;
import com.skypilot.game.screens.MainMenu;

public class MainMenuProcessor implements InputProcessor {
    private MainMenu menu;

    public MainMenuProcessor(MainMenu menu) {
        this.menu = menu;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == 21) {
            menu.clickSound.play();
            menu.setPlaneType(true);
        }

        if(keycode == 22) {
            menu.clickSound.play();
            menu.setPlaneType(false);
        }

        if(keycode == 66) {
            menu.enterSound.play(0.8f);
            menu.startGame();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
