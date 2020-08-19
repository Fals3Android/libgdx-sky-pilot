package com.skypilot.game.input;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.skypilot.game.Boss;
import com.skypilot.game.Player;
import com.skypilot.game.screens.Level;

public class MainMenuProcessor implements InputProcessor {
    Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("Pen_Clicking.mp3"));
    Sound enterSound = Gdx.audio.newSound(Gdx.files.internal("Crash_Metal_Plate_Big_Room.mp3"));
    public int chosenPlaneIndex;
    private Sprite chosenPlane;
    private float windowHeight;
    private Boss boss;
    private Player player;
    private Game game;
    private Sprite[] planes;

    public MainMenuProcessor(int chosenPlaneIndex, Sprite chosenPlane, float windowHeight, Boss boss, Player player, Game game, Sprite[] planes) {
        this.chosenPlaneIndex = chosenPlaneIndex;
        this.chosenPlane = chosenPlane;
        this.windowHeight = windowHeight;
        this.boss = boss;
        this.player = player;
        this.game = game;
        this.planes = planes;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == 21) {
            // left arrow
            clickSound.play();
            chosenPlaneIndex--;
            chosenPlane = (chosenPlaneIndex >= 0) ? planes[chosenPlaneIndex] : planes[++chosenPlaneIndex];
            if(chosenPlane.getY() < (windowHeight / 2.3f) + 20) {
                chosenPlane.setY(chosenPlane.getY() + 20);
            }
            return true;
        }

        if(keycode == 22) {
            // right arrow
            clickSound.play();
            chosenPlaneIndex++;
            chosenPlane = (chosenPlaneIndex <= planes.length - 1) ? planes[chosenPlaneIndex] : planes[--chosenPlaneIndex];
            if(chosenPlane.getY() < (windowHeight / 2.3f) + 20) {
                chosenPlane.setY(chosenPlane.getY() + 20);
            }
            return true;
        }

        if(keycode == 66) {
            // enter
            enterSound.play(0.8f);
            boss = new Boss();
            player = new Player(chosenPlane);
            game.setScreen(new Level(player, boss));
            return true;
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
