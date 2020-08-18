package com.skypilot.game.input;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.skypilot.game.Boss;
import com.skypilot.game.Player;
import com.skypilot.game.screens.Level;

public class MainMenuProcessor implements InputProcessor {

    private Sound clickSound;
    public int chosenPlaneIndex;
    private Sprite chosenPlane;
    private float windowHeight;
    private Music menuMusic;
    private Stage start;
    private Sound enterSound;
    private Boss boss;
    private Player player;
    private Game game;
    private Sprite[] planes;

    public MainMenuProcessor(Sound clickSound, int chosenPlaneIndex, Sprite chosenPlane, float windowHeight, Music menuMusic, Stage start, Sound enterSound, Boss boss, Player player, Game game, Sprite[] planes) {
        this.clickSound = clickSound;
        this.chosenPlaneIndex = chosenPlaneIndex;
        this.chosenPlane = chosenPlane;
        this.windowHeight = windowHeight;
        this.menuMusic = menuMusic;
        this.start = start;
        this.enterSound = enterSound;
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
            menuMusic.pause();
            start.clear();
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
