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
import com.badlogic.gdx.math.Matrix4;
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
    Body worldBodyLeft;
    Body worldBodyRight;
    Body worldBodyTop;
    Body worldBodyBottom;
    Body playerBody;
    Box2DDebugRenderer debugRenderer;
    float viewPortWidth = Gdx.graphics.getWidth() / 9;
    float viewPortHeight = Gdx.graphics.getHeight() / 9;

    public Box2dLevel (Sprite player) {
        this.player = player;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewPortWidth, viewPortHeight);
        batch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer();

//        map = new TmxMapLoader().load("tile-maps/level-one/level-one.tmx");
//        renderer = new OrthogonalTiledMapRenderer(map, 1f / 16f);
    }

    public Body createPlayer(World world, float xPos, float yPos, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPos, yPos);
        bodyDef.linearDamping = 10f;

        shape = new PolygonShape();
        shape.setAsBox(width, height);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0f;
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
        Body body = world.createBody(bodySquare).createFixture(fixtureDefBox).getBody();
        Vector2 pos = body.getWorldCenter();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(pos.x - width, pos.y - height, width * 2, height * 2);
        shapeRenderer.end();

        return body;
    }

    public Body createWorldBounds(World world, float xPos, float yPos, float width, float height) {
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

    public void moveCameraAlongXAxis(OrthographicCamera camera, Vector2 target, World world) {
        Vector3 position = camera.position;
        position.x = 0;
        position.y = camera.position.y + (target.y - camera.position.y) * .1f;
        camera.position.set(position);
        camera.update();
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, 0), true);
        createWorldBounds(viewPortWidth, 900);
        //TODO: need to figure out some maths to scale the player with the world
        player.setScale(0.2f,0.2f);
        playerBody = createPlayer(world, 0, -50, player.getWidth() / 10, player.getHeight() / 10);
    }

    private void createWorldBounds(float w, float h) {
        float worldHeight = h * 2;
        int distanceFromCenterWorld = 1700;
        worldBodyLeft = createWorldBounds(world, 0 - (w / 2), distanceFromCenterWorld, 1, worldHeight);
        worldBodyRight = createWorldBounds(world, 0 + (w / 2), distanceFromCenterWorld, 1, worldHeight);
        worldBodyBottom = createWorldBounds(world, 0, distanceFromCenterWorld - worldHeight, w / 2, 1);
        worldBodyTop = createWorldBounds(world, 0, distanceFromCenterWorld + worldHeight, w / 2, 1);
    }

    @Override
    public void render(float delta) {
        world.step(1 / 30f, 8, 3);
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Matrix4 cameraSetting = camera.combined; // set this to various sprites and bodies to line them up;
        shapeRenderer.setProjectionMatrix(cameraSetting);
        debugRenderer.render(world, cameraSetting);
        batch.setProjectionMatrix(cameraSetting);

        createSquare(world, 0, 0, 10, 10);
        createSquare(world, -60, 30, 10, 10);
        createSquare(world, -30, 20, 10, 10);
        createSquare(world, 60, 10, 10, 10);
        createSquare(world, 30, 20, 10, 10);


        Vector2 playerPos = playerBody.getWorldCenter();
        batch.begin();
        player.setPosition(playerPos.x - (player.getWidth() / 2), playerPos.y - (player.getHeight() / 2));
        player.draw(batch);
        batch.end();
        float vel = 20.0f;
        float velX = 0, velY = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            velY = vel ;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            velX = vel;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            velY = -vel;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            velX = -vel;
        }
        // impulse = speed - linearVelocity * mass
        playerBody.applyLinearImpulse(velX - (playerBody.getLinearVelocity().x * playerBody.getMass()), velY - (playerBody.getLinearVelocity().y * playerBody.getMass()), playerBody.getPosition().x, playerBody.getPosition().y, true);
        moveCameraAlongXAxis(camera, new Vector2(playerBody.getPosition().x, playerBody.getPosition().y), world);
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
