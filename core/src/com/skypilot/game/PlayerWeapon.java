package com.skypilot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PlayerWeapon {
    ShapeRenderer shapeRenderer;
    float playerMissile = 0f;
    float playerPositionX = 0f;
    float playerPositionY = 0f;
    boolean playerFired = false;

    PlayerWeapon () {
        shapeRenderer = new ShapeRenderer();
    }

    public void loadWeapon(float playerX, float playerY, String weaponType) {
        playerPositionX = playerX;
        playerPositionY = playerY;
        if (weaponType == "laser") {
            loadLaser();
        } else {
            loadGravityBomb();
        }
        resetPlayerMissle();
    }

    public float[] getPlayerMisslePosition() {
        float[] coordinates = {playerPositionX, playerMissile};
        return coordinates;
    }

    private void resetPlayerMissle() {
        if(playerMissile > Gdx.graphics.getHeight()) {
            this.playerMissile = playerPositionY + 10;
            playerFired = false;
        }
    }

    private float playerShoot(float weaponSpeed) {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            playerFired = true;
        }

        if (playerFired) {
            return this.playerMissile += weaponSpeed;
        }

        return playerPositionY + 10;
    }

    private void loadLaser() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 1, 1);
        shapeRenderer.rect(playerPositionX, playerShoot(100), 5, playerFired ? 100: 0);
        shapeRenderer.end();
    }

    private void loadGravityBomb() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 1, 1);
        shapeRenderer.circle(playerPositionX, playerShoot(50), playerFired ? 10: 0);
        shapeRenderer.end();
    }
}
