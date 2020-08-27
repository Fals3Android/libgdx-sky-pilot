package com.skypilot.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

public class Box2dLevel implements Screen {
    OrthographicCamera camera;
    TiledMap map;
    TiledMapRenderer renderer;
    ShapeRenderer shapeRenderer;
    Sprite player;
    SpriteBatch batch;
    PolygonShape shape;
    World world;
    Body playerBody;
    Body bodyOne;
    Box2DDebugRenderer debugRenderer;

    public Box2dLevel (Sprite player) {
        this.player = player;

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(true, w, h);
        batch = new SpriteBatch();

//        map = new TmxMapLoader().load("tile-maps/level-one/level-one.tmx");
//        renderer = new OrthogonalTiledMapRenderer(map, 1f / 16f);
    }

    public Body createPlayer(World world, float xPos, float yPos, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPos, yPos);
        bodyDef.linearDamping = 6f;

        shape = new PolygonShape();
        shape.setAsBox(width, height);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        return world.createBody(bodyDef).createFixture(fixtureDef).getBody();
    }

    public Body createSquare(World world, float xPos, float yPos, float width, float height) {
        BodyDef bodySquare = new BodyDef();
        bodySquare.type = BodyDef.BodyType.StaticBody;
        bodySquare.position.set(xPos, yPos);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(width, height);

        FixtureDef fixtureDefBox = new FixtureDef();
        fixtureDefBox.shape = boxShape;

        return world.createBody(bodySquare).createFixture(fixtureDefBox).getBody();
    }

    public void lerpToTarget(OrthographicCamera camera, Vector2 target) {
        Vector3 position = camera.position;
        position.x = camera.position.x + (target.x - camera.position.x) * .1f;
        position.y = camera.position.y + (target.y - camera.position.y) * .1f;
        camera.position.set(position);
        camera.update();
    }

    @Override
    public void show() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        world = new World(new Vector2(0, 0), true);
        camera = new OrthographicCamera(w, h);
        debugRenderer = new Box2DDebugRenderer();

        bodyOne = createSquare(world, 0, 0, 10, 10);
        playerBody = createPlayer(world, -100, -100, player.getWidth() + 10, player.getHeight() + 30);
    }

    @Override
    public void render(float delta) {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        world.step(1 / 60f, 6, 2);
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(world, camera.combined);

        Vector2 pos = bodyOne.getWorldCenter();
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        float width = 20, height = 20; // exactly half of my physics body???
        shapeRenderer.rect((w / 2) + pos.x - (width / 2), (h / 2) + pos.y - (height / 2), width, height);
        shapeRenderer.end();

        Vector2 playerPos = playerBody.getWorldCenter();
        batch.begin();
        player.setPosition((w / 2) + playerPos.x - (player.getWidth() / 2), (h / 2) + playerPos.y - (player.getHeight() / 2));
        player.draw(batch);

        batch.end();

        //Control the player
        int x = 0, y = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += 10;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= 10;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= 10;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += 10;
        }

        if(x != 0) {
            Vector2 vel = playerBody.getLinearVelocity();
            playerBody.setLinearVelocity((x * 10), vel.y);
        }

        if(y != 0) {
            Vector2 vel = playerBody.getLinearVelocity();
            playerBody.setLinearVelocity(vel.x, (y * 10));
        }

        player.setPosition(playerBody.getPosition().x, playerBody.getPosition().y);
//        lerpToTarget(camera, playerBody.getPosition());
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
        shape.dispose();
    }
}
