package com.skypilot.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.skypilot.game.Boss;
import com.skypilot.game.Player;

public class MainMenu implements Screen {
    BitmapFont font	= new BitmapFont();
    Game game;
    Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("Pen_Clicking.mp3"));
    Sound enterSound = Gdx.audio.newSound(Gdx.files.internal("Crash_Metal_Plate_Big_Room.mp3"));
    Music menuMusic = Gdx.audio.newMusic(Gdx.files.internal("MAIN_THEME.mp3"));
    int windowWidth;
    int windowHeight;
    Stage start;
    Boss boss;
    Player player;

    SpriteBatch batch;
    Texture spacecraft;
    Texture grayfighter;
    Sprite craftSprite;
    Sprite fighterSprite;
    float clock = 0;

    String[] planes = {"spacecraft", "grayfighter"};
    public String chosenPlane = planes[0];

    public MainMenu(Game game) {
        this.game = game;
        this.windowWidth = Gdx.graphics.getWidth();
        this.windowHeight = Gdx.graphics.getHeight();
        float xCenter = this.windowWidth / 4;

        start = new Stage();
        Label chooseLabel = this.createLabel("Choose Your Craft", xCenter * 1.6f, 700, 1.5f);
        Label titleLabel = this.createLabel("Sky Pilot", xCenter * 1.6f, 900, 3f);
        Label directionsLabel = this.createLabel("Use the <- and -> arrow keys to choose, press enter to make a selection.", xCenter / 1.6f, 300, 1.5f);
        start.addActor(chooseLabel);
        start.addActor(titleLabel);
        start.addActor(directionsLabel);

        batch = new SpriteBatch();

        spacecraft = new Texture("spacecraft.png");
        craftSprite = new Sprite(spacecraft);
        craftSprite.setPosition(xCenter, windowHeight / 2.3f);

        grayfighter = new Texture("grayfighter.png");
        fighterSprite = new Sprite(grayfighter);
        fighterSprite.setPosition(xCenter * 2.4f, windowHeight / 2.3f);
        craftSprite.setColor(Color.GOLD);
    }

    public Label createLabel(String text, float xPosition, float yPosition, float scale) {
        Label label;
        Label.LabelStyle chooseLabelStyle;
        chooseLabelStyle = new Label.LabelStyle();
        chooseLabelStyle.font = font;
        label = new Label(text  , chooseLabelStyle);
        label.setScale(scale);
        label.setPosition(xPosition, yPosition);
        label.setFontScale(scale, scale);
        return label;
    }

    public void drawMenu() {
//        menuMusic.setLooping(true);
//        menuMusic.play();
        start.draw();
        batch.begin();
        if(craftSprite != null && fighterSprite != null) {
            craftSprite.draw(batch);
            fighterSprite.draw(batch);
        }
        batch.end();

        clock += Gdx.graphics.getDeltaTime(); // add the time since the last frame

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && clock > 0.3) {
            craftSprite.setColor(Color.GOLD);
            fighterSprite.setColor(Color.WHITE);
            clickSound.play();
            chosenPlane = planes[0];
            clock = 0;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && clock > 0.3) {
            fighterSprite.setColor(Color.GOLD);
            craftSprite.setColor(Color.WHITE);
            clickSound.play();
            chosenPlane = planes[1];
            clock = 0;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ENTER) && clock > 0.3) {
            menuMusic.pause();
            start.clear();
            craftSprite = null;
            fighterSprite = null;
            enterSound.play(0.8f);
			boss = new Boss();
			player = new Player(chosenPlane);
            game.setScreen(new Level(player, boss));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        drawMenu();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
