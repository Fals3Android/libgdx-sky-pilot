package com.skypilot.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.skypilot.game.Boss;
import com.skypilot.game.Player;
import com.skypilot.game.input.MainMenuProcessor;

public class MainMenu implements Screen {
    MainMenuProcessor menuProcessor;
    BitmapFont font	= new BitmapFont();
    Game game;
    Music menuMusic = Gdx.audio.newMusic(Gdx.files.internal("MAIN_THEME.mp3"));
    public Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("Pen_Clicking.mp3"));
    public Sound enterSound = Gdx.audio.newSound(Gdx.files.internal("Crash_Metal_Plate_Big_Room.mp3"));
    Stage start;
    Boss boss;
    Player player;
    TextureAtlas atlas;
    SpriteBatch batch;
    Sprite craftSprite;
    Sprite bomberSprite;
    Sprite fighterSprite;

    Sprite[] planes = {craftSprite, bomberSprite, fighterSprite};
    public Sprite chosenPlane;
    int chosenPlaneIndex = 0;

    public MainMenu(Game game) {
        this.game = game;
        float xCenter = Gdx.graphics.getWidth() / 4;

        start = new Stage();
        Label chooseLabel = this.createLabel("Choose Your Craft", xCenter * 1.6f, 700, 1.5f);
        Label titleLabel = this.createLabel("Sky Pilot", xCenter * 1.6f, 900, 3f);
        Label directionsLabel = this.createLabel("Use the <- and -> arrow keys to choose, press enter to make a selection.", xCenter / 1.6f, 300, 1.5f);
        start.addActor(chooseLabel);
        start.addActor(titleLabel);
        start.addActor(directionsLabel);

        batch = new SpriteBatch();

        atlas = new TextureAtlas(Gdx.files.internal("packed/game.atlas"));
        craftSprite = atlas.createSprite("PLANE");
        craftSprite.setScale(3f, 4f);
        craftSprite.setPosition(xCenter * 1.1f, Gdx.graphics.getHeight() / 2.3f);
        planes[0] = craftSprite;

        bomberSprite = atlas.createSprite("PLANE-TWO");
        bomberSprite.setScale(3f, 4f);
        bomberSprite.setPosition(xCenter * 1.9f, Gdx.graphics.getHeight() / 2.3f);
        planes[1] = bomberSprite;

        fighterSprite = atlas.createSprite("PLANE-THREE");
        fighterSprite.setScale(3f, 4f);
        fighterSprite.setPosition(xCenter * 2.7f, Gdx.graphics.getHeight() / 2.3f);
        planes[2] = fighterSprite;
        chosenPlane = craftSprite;
        chosenPlane.setY(chosenPlane.getY() + 20);
        menuProcessor = new MainMenuProcessor(this);
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.7f);
        menuMusic.play();
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

    public void startGame() {
        boss = new Boss();
        player = new Player(chosenPlane);
        game.setScreen(new Level(player, boss));
    }

    public void setPlaneType(boolean branch) {
        if(branch) {
            chosenPlaneIndex--;
            chosenPlane = (chosenPlaneIndex >= 0) ? planes[chosenPlaneIndex] : planes[++chosenPlaneIndex];
            if(chosenPlane.getY() < (Gdx.graphics.getHeight() / 2.3f) + 20) {
                chosenPlane.setY(chosenPlane.getY() + 20);
            }
            return;
        }

        chosenPlaneIndex++;
        chosenPlane = (chosenPlaneIndex <= planes.length - 1) ? planes[chosenPlaneIndex] : planes[--chosenPlaneIndex];
        if(chosenPlane.getY() < (Gdx.graphics.getHeight() / 2.3f) + 20) {
            chosenPlane.setY(chosenPlane.getY() + 20);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(menuProcessor);
    }

    @Override
    public void render(float delta) {
        startGame();
//        TODO: Temp way to start on level 1
//        Gdx.gl.glClearColor(0f, 0.3f, 0.7f, 1f);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        start.draw();
//        batch.begin();
//        craftSprite.draw(batch);
//        bomberSprite.draw(batch);
//        fighterSprite.draw(batch);
//        batch.end();
//
//        for(int i = 0; i <= planes.length - 1; i++) {
//            if(chosenPlaneIndex != i) {
//                planes[i].setY(Gdx.graphics.getHeight() / 2.3f);
//            }
//        }
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
        start.clear();
        menuMusic.stop();
    }

    @Override
    public void dispose() {
        menuMusic.dispose();
        start.dispose();
        clickSound.dispose();
        enterSound.dispose();
    }
}
