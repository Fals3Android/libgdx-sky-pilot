package com.skypilot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Map;

public class Boss {
    ShapeRenderer shapeRenderer;
    public Sprite bossSprite;
    SpriteBatch batch;
    public float bossPositionX = Gdx.graphics.getWidth() / 4 * 2;
    public float getBossPositionY = Gdx.graphics.getHeight() / 4 * 3;
    public float bossRadius = 100;
    boolean hitXMin = true;
    float bossSpeed = 6;
    public Color bossColor = Color.BLACK;
    float randomExplosionClock = 0;
    int randomExplosionThreshold = 0;
    boolean setOffExplosion = false;
    float randomExplosionX = 0.0f;
    float randomExplosionY = 0.0f;
    public boolean explosionComplete = false;
    public float health = 10f;

    public Boss() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("packed/game.atlas"));
        bossSprite = atlas.createSprite("BOSS-ONE");
        bossSprite.setScale(2, 3);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
    }

    public void drawBoss(Map<String, Float> playerCoordinates) {
        batch.begin();
        bossSprite.setPosition(moveBossXAxis(), getBossPositionY);
        bossSprite.setRegion(34, 0, 32, 32);
        bossSprite.draw(batch);
        batch.end();
        spawnRandomExplosions(playerCoordinates);
    }

    public void spawnRandomExplosions(Map<String, Float> playerCoordinates) {
        randomExplosionClock += Gdx.graphics.getDeltaTime(); // add the time since the last frame
        if(randomExplosionClock > 0.2 && !setOffExplosion) {
            explosionComplete = false;
        }

        if(randomExplosionClock > 1 && !setOffExplosion) {
            setOffExplosion = true;
            randomExplosionX = playerCoordinates.get("x");
            randomExplosionY = playerCoordinates.get("y");
        }

        if(setOffExplosion) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.circle(randomExplosionX, randomExplosionY, 50);
            shapeRenderer.end();
        }

        if(randomExplosionClock > 1.5 && setOffExplosion) {
            explosionComplete = true;
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.circle(randomExplosionX, randomExplosionY, 50);
            shapeRenderer.end();
            setOffExplosion = false;
            randomExplosionClock = 0;
        }
    }

    public boolean wasPlayerDamaged(Map<String, Float> playerCoordinates) {
        double distance = Math.sqrt(
            Math.pow(randomExplosionX - (playerCoordinates.get("x")), 2) +
            Math.pow(randomExplosionY - (playerCoordinates.get("y")), 2)
        );

        if(distance <= 75) {
            return true;
        }
        return false;
    }

    private void createBossShape() {
        shapeRenderer.setColor(bossColor);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(moveBossXAxis(), getBossPositionY, bossRadius);
        shapeRenderer.end();
    }

    private float moveBossXAxis() {
        if(hitXMin && bossPositionX < Gdx.graphics.getWidth()) {
            return bossPositionX += bossSpeed;
        }
        if(bossPositionX < 0) {
            hitXMin = true;
            return bossPositionX;
        }

        hitXMin = false;
        return bossPositionX -= bossSpeed;
    }
}
