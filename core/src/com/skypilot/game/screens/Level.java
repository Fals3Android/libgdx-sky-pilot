package com.skypilot.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.skypilot.game.Boss;
import com.skypilot.game.Player;
import com.skypilot.game.input.LevelProcessor;

public class Level implements Screen {
    OrthographicCamera camera;
    TiledMap map;
    TiledMapRenderer renderer;
    Player player;
    Boss boss;
    Stage stage;
    SpriteBatch batch;
    float clock = 0;

    public Level(Player player, Boss boss) {
        this.player = player;
        this.boss = boss;
        this.stage = new Stage();
        batch = new SpriteBatch();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 18, 10);
        camera.update();

        map = new TmxMapLoader().load("tile-maps/level-one/level-one.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1f / 32f);
    }

    public void drawLevel() {
        if(player.playerHealth <= 0) {
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            getGameOverScreen();
            if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                stage.clear();
                player.playerHealth = 100;
            }
            return;
        }

        player.drawPlayer();
        boss.drawBoss(player.playerMovement.getPlayerCoordinates());
        if(didPlayerMissleHit()) {
            boss.bossSprite.setColor(0.5f, 0, 0, 1);
            boss.health -= 1.5f;
        }

        if(boss.health < 0) {
            boss.bossColor = Color.BROWN;
        }

        if(boss.wasPlayerDamaged(player.playerMovement.getPlayerCoordinates()) && boss.explosionComplete) {
            player.playerSprite.setColor(Color.RED);
            player.playerHealth = player.playerHealth - 1;
        }

        clock += Gdx.graphics.getDeltaTime(); // add the time since the last frame
        if(clock > 1) {
            player.playerSprite.setColor(Color.WHITE);
            boss.bossSprite.setColor(1,1,1,1);
            boss.bossSprite.setRegion(64, 0, 32, 32);
            clock = 0;
        }
    }

    private void getGameOverScreen() {
        BitmapFont font	= new BitmapFont();
        Label label;
        Label.LabelStyle labelStyle;
        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        label = new Label("Game Over Press Enter to restart"  , labelStyle);
        label.setFontScale(3.0f);
        label.setPosition(Gdx.graphics.getWidth() / 8 * 2, Gdx.graphics.getHeight() / 2);
        stage.addActor(label);
        stage.draw();
    }

    private boolean didPlayerMissleHit() {
        double distanceLaser = Math.sqrt(
                Math.pow(
                    boss.bossPositionX - player.laser.getPlayerMisslePosition()[0], 2
                ) +
                Math.pow(
                    boss.getBossPositionY - player.laser.getPlayerMisslePosition()[1], 2
                )
        );

        double distanceGravityBomb = Math.sqrt(
                Math.pow(
                        boss.bossPositionX - player.gravityBomb.getPlayerMisslePosition()[0], 2
                ) +
                        Math.pow(
                                boss.getBossPositionY - player.gravityBomb.getPlayerMisslePosition()[1], 2
                        )
        );

        if(distanceGravityBomb <= boss.bossRadius || distanceLaser <= boss.bossRadius) {
            return true;
        }

        return false;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new LevelProcessor());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        scrollCameraY();
        camera.update();
        renderer.setView(camera);
        renderer.render();
        drawLevel();
    }

    private void scrollCameraY() {
        if(camera.position.y <= 10) {
            camera.position.y += 0.1f;
        } else {
            camera.position.y = 5;
        }
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
        map.dispose();
    }
}
