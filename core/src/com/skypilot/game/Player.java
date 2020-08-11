package com.skypilot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Player {
    public SpriteBatch batch;
    public Texture grayfighter;
    public Sprite fighterSprite;
    Sound flightSound = Gdx.audio.newSound(Gdx.files.internal("Airplane_In_Flight.mp3"));
    ShapeRenderer shapeRenderer;
    public int playerHealth = 100;
    Stage stage;
    Label label;
    public PlayerMovement playerMovement;
    public PlayerWeapon laser;
    public PlayerWeapon gravityBomb;

    public Player(String planeType) {
        batch = new SpriteBatch();
        grayfighter = new Texture(planeType + ".png");
        fighterSprite = new Sprite(grayfighter);
        fighterSprite.setPosition(100, 100);
        shapeRenderer = new ShapeRenderer();
        stage = new Stage();
        label = UILabel.createLabel("Health " + playerHealth, 20, Gdx.graphics.getHeight() - 40, 3.0f);
        playerMovement = new PlayerMovement(fighterSprite, 100, 100, 6.5f, 10.5f);
        laser = new PlayerWeapon();
        gravityBomb = new PlayerWeapon();
    }

    public void drawPlayer() {
        batch.begin();
        fighterSprite.draw(batch);
        batch.end();
        playerMovement.keyboardMovement();
        laser.loadWeapon(playerMovement.getPlayerCoordinates().get("x"), playerMovement.getPlayerCoordinates().get("y"), "laser");
        gravityBomb.loadWeapon(playerMovement.getPlayerCoordinates().get("x"), playerMovement.getPlayerCoordinates().get("y"), "gravityBomb");
        flightSound.loop(0.2f);
        label.setText("Health " + playerHealth);
        stage.addActor(label);
        stage.draw();
    }
}
