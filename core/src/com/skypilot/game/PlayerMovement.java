package com.skypilot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;
import java.util.Map;

public class PlayerMovement {
    Sprite playerSprite;
    float playerPositionX = 0;
    float playerPositionY = 0;
    boolean isBoosting = false;
    float basePlayerSpeed = 6.5f;
    float boostPlayerSpeed = 10;

    Map<String, Runnable> applyDirection = new HashMap<String, Runnable>() {{
        put("W", () -> {
            if(!(playerPositionY + playerSprite.getHeight() > Gdx.graphics.getHeight())) playerPositionY += playerBoost();
        });
        put("A", () -> {
            if(!(playerPositionX < 0)) playerPositionX -= playerBoost();
        });
        put("S", () -> {
            if(!(playerPositionY < 0)) playerPositionY -= playerBoost();
        });
        put("D", () -> {
            if(!(playerPositionX + playerSprite.getWidth() > Gdx.graphics.getWidth())) playerPositionX += playerBoost();
        });
    }};

    Map<String, Boolean> getKey = new HashMap<String, Boolean>();

    PlayerMovement(Sprite sprite, float initialX, float initialY, float basePlayerSpeed, float boostPlayerSpeed) {
        this.playerSprite = sprite;
        this.playerPositionX = initialX;
        this.playerPositionY = initialY;
        this.basePlayerSpeed = basePlayerSpeed;
        this.boostPlayerSpeed = boostPlayerSpeed;
    }
    
    public void keyboardMovement() {
        getKey.put("W", Gdx.input.isKeyPressed(Input.Keys.W));
        getKey.put("A", Gdx.input.isKeyPressed(Input.Keys.A));
        getKey.put("S", Gdx.input.isKeyPressed(Input.Keys.S));
        getKey.put("D", Gdx.input.isKeyPressed(Input.Keys.D));

        for(String key : getKey.keySet()) {
            if(getKey.get(key)) {
                applyDirection.get(key).run();
                playerSprite.setPosition(playerPositionX, playerPositionY);
            }
        }
    }

    private float playerBoost() {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            isBoosting = true;
            return boostPlayerSpeed;
        }
        isBoosting = false;
        return basePlayerSpeed;
    }

    public Map<String, Float> getPlayerCoordinates() {
        Map<String, Float> playerCoordinates = new HashMap<String, Float>() {{
            put("x", playerPositionX + playerSprite.getWidth() / 2);
            put("y", playerPositionY + playerSprite.getWidth() / 2);
        }};
        return playerCoordinates;
    }
}


