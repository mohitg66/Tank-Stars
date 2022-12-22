package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.physics.box2d.*;

public class GameScreen implements Screen {

    private final TankStars game;
    private OrthographicCamera camera;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;
    private Box2DDebugRenderer debugRenderer;
    private Texture bg;
    private Sprite terrain;
    private Body bodyTerrain;
    private Tank tank1;
    private Body tank1Body;
    private Tank tank2;
    private Body tank2Body;
    private Sprite pauseButton;
    private Sprite pauseMenu;
    private Sprite resumeButton;
    private Sprite mainMenuButton;
    private World world;
    private boolean paused;
    private float tankScale;
    private Tank curTank;
    private Body curTankBody;
    private Tank otherTank;
    private Body otherTankBody;
    private int angle;
    private int force;
    private Missile missile1;
    private Body missile1Body;
    private Missile missile2;
    private Body missile2Body;
    private Missile curMissile;
    private Body curMissileBody;
    private Missile selectedMissile;
    private Body selectedMissileBody;

    public GameScreen(TankStars game, Texture tankA, Texture tankB) {
        this.game= game;
        this.camera= new OrthographicCamera();
        this.camera.setToOrtho(false);
        this.font= new BitmapFont();
        this.shapeRenderer= new ShapeRenderer();
        this.debugRenderer= new Box2DDebugRenderer();
        this.bg= new Texture(Gdx.files.internal("Terrain bg.png"));
        this.terrain= new Sprite(new Texture(Gdx.files.internal("Terrain 2.png")));
        this.tank1= new Tank(tankA);
        this.tank2= new Tank(tankB);
        this.pauseButton= new Sprite(new Texture(Gdx.files.internal("Pause Button.png")));
        this.pauseMenu= new Sprite(new Texture(Gdx.files.internal("Pause Menu.png")));
        this.resumeButton= new Sprite(new Texture(Gdx.files.internal("Resume Button.png")));
        this.mainMenuButton= new Sprite(new Texture(Gdx.files.internal("Main Menu Button.png")));
        this.world= new World(new Vector2(0, -50f), true);
//        this.world= new World(new Vector2(0, 0), true);
        this.tankScale= 1f;
        this.angle= 45;
        this.force= 50;
//        this.missiles= new ArrayList<Missile>();

        terrain.setBounds(0, 0, camera.viewportWidth, terrain.getHeight());
        pauseButton.setPosition(0, 9.8f/10f * camera.viewportHeight- pauseButton.getHeight());
        pauseMenu.setPosition(camera.viewportWidth/2- pauseMenu.getWidth()/2, camera.viewportHeight/2- pauseMenu.getHeight()/2);
        resumeButton.setPosition(camera.viewportWidth/2- resumeButton.getWidth()/2, camera.viewportHeight/2+ resumeButton.getHeight()/2);
        mainMenuButton.setPosition(camera.viewportWidth/2- mainMenuButton.getWidth()/2, camera.viewportHeight/2- mainMenuButton.getHeight()/2- 20);


//         terrain body
        BodyDef bodyDefTerrain= new BodyDef();
        bodyDefTerrain.type= BodyDef.BodyType.StaticBody;
        bodyDefTerrain.position.set(terrain.getX(), terrain.getY());
        bodyTerrain= world.createBody(bodyDefTerrain);

        PolygonShape shapeTerrain= new PolygonShape();
        shapeTerrain.setAsBox(terrain.getWidth()/2, terrain.getHeight()/2);
        FixtureDef fixtureDef= new FixtureDef();
        fixtureDef.shape= shapeTerrain;
        bodyTerrain.createFixture(fixtureDef);
        shapeTerrain.dispose();


//         tank1 body
        tank1.setBounds(1/6f * camera.viewportWidth, terrain.getY()+ terrain.getHeight(), tank1.getWidth()* tankScale, tank1.getHeight()* tankScale);

        BodyDef bodyDefTank1= new BodyDef();
        bodyDefTank1.type= BodyDef.BodyType.DynamicBody;
        bodyDefTank1.position.set(tank1.getX(), tank1.getY());
        tank1Body= world.createBody(bodyDefTank1);

        CircleShape shapeTank1= new CircleShape();
        shapeTank1.setRadius(tank1.getHeight()/2);
        FixtureDef fixtureDefTank1= new FixtureDef();
        fixtureDefTank1.shape= shapeTank1;
//        fixtureDefTank1.density= 1f;
        tank1Body.createFixture(fixtureDefTank1);
        shapeTank1.dispose();


//         tank2 body
        tank2.setBounds((camera.viewportWidth)- tank1.getX()- (tank2.getWidth()* tankScale), terrain.getY()+ terrain.getHeight(), tank2.getWidth()* tankScale, tank2.getHeight()* tankScale);
        tank2.flip(true, false);

        BodyDef bodyDefTank2= new BodyDef();
        bodyDefTank2.type= BodyDef.BodyType.DynamicBody;
        bodyDefTank2.position.set(tank2.getX(), tank2.getY());
        tank2Body= world.createBody(bodyDefTank2);

        CircleShape shapeTank2= new CircleShape();
        shapeTank2.setRadius(tank2.getHeight()/2);
        FixtureDef fixtureDefTank2= new FixtureDef();
        fixtureDefTank2.shape= shapeTank2;
//        fixtureDefTank2.density= 1f;
        tank2Body.createFixture(fixtureDefTank2);
        shapeTank2.dispose();

        curTankBody= tank1Body;
        curTank= tank1;
        otherTankBody= tank2Body;
        otherTank= tank2;


        // missile1 body
        missile1= new Missile(new Texture(Gdx.files.internal("Regular.png")), 1000);
        BodyDef bodyDefMissile1= new BodyDef();
        bodyDefMissile1.type= BodyDef.BodyType.DynamicBody;
//        bodyDefMissile1.position.set(curTankBody.getPosition().x, curTankBody.getPosition().y);
        bodyDefMissile1.position.set(camera.viewportWidth/2, camera.viewportHeight/2);
        missile1Body= world.createBody(bodyDefMissile1);

        PolygonShape shapeMissile1= new PolygonShape();
        shapeMissile1.setAsBox(missile1.getWidth()/2, missile1.getHeight()/2);
        FixtureDef fixtureDefMissile1= new FixtureDef();
        fixtureDefMissile1.shape= shapeMissile1;
        missile1Body.createFixture(fixtureDefMissile1);
        shapeMissile1.dispose();

        // rotate missile1 by -90 degrees
        missile1Body.setTransform(missile1Body.getPosition(), (float) Math.toRadians(-90));
        missile1.rotate(90);


        // missile2 body
        Missile missile2= new Missile(new Texture(Gdx.files.internal("Big One.png")), 1500);
        BodyDef bodyDefMissile2= new BodyDef();
        bodyDefMissile2.type= BodyDef.BodyType.DynamicBody;
//        bodyDefMissile2.position.set(curTankBody.getPosition().x, curTankBody.getPosition().y);
        bodyDefMissile2.position.set(camera.viewportWidth/2, camera.viewportHeight/2);
        Body missile2Body= world.createBody(bodyDefMissile2);

        PolygonShape shapeMissile2= new PolygonShape();
        shapeMissile2.setAsBox(missile2.getWidth()/2, missile2.getHeight()/2);
        FixtureDef fixtureDefMissile2= new FixtureDef();
        fixtureDefMissile2.shape= shapeMissile2;
        missile2Body.createFixture(fixtureDefMissile2);
        shapeMissile2.dispose();

        // rotate missile2 by -90 degrees
        missile2Body.setTransform(missile2Body.getPosition(), (float) Math.toRadians(-90));
        missile2.rotate(90);

        curMissile= null;
        curMissileBody= null;

    }

    @Override
    public void render(float delta) {
        camera.update();

        tank1.setPosition(tank1Body.getPosition().x, tank1Body.getPosition().y);
        tank2.setPosition(tank2Body.getPosition().x, tank2Body.getPosition().y);
        if (curMissile!= null) {
            curMissile.setPosition(curMissileBody.getPosition().x, curMissileBody.getPosition().y);
        }

        // if pauseButton is clicked or escape key is pressed, pause the game
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && Gdx.input.getX() < pauseButton.getWidth() && Gdx.input.getY() < pauseButton.getY() || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (paused) resume();
            else pause();
        }

        // when tank1 or tank2 collides with terrain, they are set to height of terrain
        if (tank1Body.getPosition().y < terrain.getY()+ terrain.getHeight()) {
            tank1Body.setTransform(tank1Body.getPosition().x, terrain.getY()+ terrain.getHeight(), 0);
        }
        if (tank2Body.getPosition().y < terrain.getY()+ terrain.getHeight()) {
            tank2Body.setTransform(tank2Body.getPosition().x, terrain.getY()+ terrain.getHeight(), 0);
        }

//         if tank1 or tank2 or terrain collides with missile, their health is decreased which is proportional to the missile's distance from the tank after collision
        if (curMissileBody!=null) {
            if (curMissileBody.getPosition().y < terrain.getY() + terrain.getHeight() || (curMissileBody.getPosition().x > tank1Body.getPosition().x && curMissileBody.getPosition().x < tank1Body.getPosition().x + tank1.getWidth() && curMissileBody.getPosition().y < tank1Body.getPosition().y + tank1.getHeight())) {
                curTank.setHealth((int)(curTank.getHealth() - (int) (curTank.getWidth() * Math.abs(curMissileBody.getPosition().x+ curMissile.getWidth()/2 - curTankBody.getPosition().x+ curTank.getWidth()/2)) / curTank.getWidth()));
                if (curTank.getHealth()<=0) {
                    curTank.setHealth(0);
                    game.setScreen(new HomeScreen1(game));
                }
                curTank.setFuel(100);
                curMissileBody = null;
                curMissile = null;
            }
        }

        game.batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        ScreenUtils.clear(0, 0, 0.2f, 1);

        if (paused)
            handlePauseMenuInput();
        else
            handleInput();

        game.batch.begin();
        if (paused){
            game.batch.draw(bg, 0, 0, camera.viewportWidth, camera.viewportHeight);
            terrain.draw(game.batch);
            tank1.draw(game.batch);
            tank2.draw(game.batch);
            pauseButton.draw(game.batch);
            pauseMenu.draw(game.batch);
            resumeButton.draw(game.batch);
            mainMenuButton.draw(game.batch);

        } else {
            world.step(Gdx.graphics.getDeltaTime(), 6, 2);
            game.batch.draw(bg, 0, 0, camera.viewportWidth, camera.viewportHeight);
            terrain.draw(game.batch);
            if (curMissile != null) curMissile.draw(game.batch);
            tank1.draw(game.batch);
            tank2.draw(game.batch);
            pauseButton.draw(game.batch);
        }

            // display the fonts
            float tank1TopX = 1 / 6f * camera.viewportWidth;
            float tank2TopX = 4.5f / 6f * camera.viewportWidth;

            // display health of tanks on screen and change color of health bar according to health of tank (green if health is full, red if health is low) using bitmap font
            font.setColor(Color.WHITE);
            font.draw(game.batch, "Health: " + tank1.getHealth(), tank1TopX, camera.viewportHeight - 10);
            font.draw(game.batch, "Health: " + tank2.getHealth(), tank2TopX, camera.viewportHeight - 10);

            // display fuel of tanks on screen and change color of fuel bar according to fuel of tank (green if fuel is full, red if fuel is low) using bitmap font
            font.draw(game.batch, "Fuel: " + tank1.getFuel(), tank1TopX, camera.viewportHeight - 30);
            font.draw(game.batch, "Fuel: " + tank2.getFuel(), tank2TopX, camera.viewportHeight - 30);

            // display selected missile on screen using bitmap font
            if (selectedMissile==missile1) {
                font.draw(game.batch, "Selected Missile: " + "Regular", tank1TopX, camera.viewportHeight - 50);
                font.draw(game.batch, "Selected Missile: " + "Regular", tank2TopX, camera.viewportHeight - 50);
            }
            else if (selectedMissile==missile2) {
                font.draw(game.batch, "Selected Missile: " + "Big One", tank1TopX, camera.viewportHeight - 50);
                font.draw(game.batch, "Selected Missile: " + "Big One", tank2TopX, camera.viewportHeight - 50);
            }

            // display power and angle over the top of tanks using bitmap font
            font.draw(game.batch, "Power: " + force, tank1TopX, camera.viewportHeight - 90);
            font.draw(game.batch, "Angle: " + angle, tank1TopX, camera.viewportHeight - 110);

            font.draw(game.batch, "Power: " + force, tank2TopX, camera.viewportHeight - 90);
            font.draw(game.batch, "Angle: " + angle, tank2TopX, camera.viewportHeight - 110);


        game.batch.end();
    }


    void handlePauseMenuInput(){
        // if resume button is pressed, resume game
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && (Gdx.input.getX() > resumeButton.getX() && Gdx.input.getX() < resumeButton.getX() + resumeButton.getWidth() && Gdx.input.getY() > resumeButton.getY() && Gdx.input.getY() < resumeButton.getY() + resumeButton.getHeight())) {
            resume();
        }

        // if main menu button is pressed, set screen to homeScreen1
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && (Gdx.input.getX() > mainMenuButton.getX() && Gdx.input.getX() < mainMenuButton.getX() + mainMenuButton.getWidth() && Gdx.input.getY() > mainMenuButton.getY() && Gdx.input.getY() < mainMenuButton.getY() + mainMenuButton.getHeight())) {
            game.setScreen(new HomeScreen1(game));
        }

    }

    void handleInput(){
        // press 1 to select missile1
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            selectedMissile= missile1;
            selectedMissileBody= missile1Body;
        }
        // press 2 to select missile2
        else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            selectedMissile= missile2;
            selectedMissileBody= missile2Body;
        }

        // press space to change turn and then fire missile
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {

            if (selectedMissile==null){
                curMissile= missile1;
                curMissileBody= missile1Body;
            } else {
                curMissile= selectedMissile;
                curMissileBody= selectedMissileBody;
            }


            // set missile position to tank position and rotate missile by -90 degrees if it is tank1's turn and +90 degrees if it is tank2's turn
            if (curTank == tank1) {
                curMissileBody.setTransform(curTankBody.getPosition().x+ tank1.getWidth(), curTankBody.getPosition().y+ tank1.getHeight(), (float) Math.toRadians(180));
            } else {
                curMissileBody.setTransform(curTankBody.getPosition().x, curTankBody.getPosition().y+ tank2.getHeight(), (float) Math.toRadians(180));
            }
            curMissile.rotate(180);

            if (curTank == tank1) {
                curMissileBody.setLinearVelocity((float) (force* 50 * Math.cos(Math.toRadians(angle))), (float) (force* 50 * Math.sin(Math.toRadians(angle))));
            } else{
                curMissileBody.setLinearVelocity((-1)* (float) (force* 50 * Math.cos(Math.toRadians(angle))), (float) (force* 50 * Math.sin(Math.toRadians(angle))));
            }


            if (curTankBody == tank1Body) {
                curTankBody = tank2Body;
                curTank = tank2;
            } else {
                curTankBody = tank1Body;
                curTank = tank1;
            }
        }


        if (Gdx.input.isKeyPressed(Input.Keys.A) && curTank.getFuel() > 0) {
            curTankBody.applyLinearImpulse(-1f, 0, curTankBody.getPosition().x, curTankBody.getPosition().y, true);
            curTank.setFuel(curTank.getFuel()-1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && curTank.getFuel() > 0) {
            curTankBody.applyLinearImpulse(1f, 0, curTankBody.getPosition().x, curTankBody.getPosition().y, true);
            curTank.setFuel(curTank.getFuel()-1);
        } else {
            // stop tank
            tank1Body.setLinearVelocity(0, tank1Body.getLinearVelocity().y);
            tank2Body.setLinearVelocity(0, tank2Body.getLinearVelocity().y);
        }

//       if right is pressed force is increased and if left button is pressed force is decreased
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (force < 100) force+= 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (force > 0) force-= 1;
        }

//         if up is pressed angle is increased and if down is pressed angle is decreased
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (angle < 90) angle+= 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (angle > 0) angle-= 1;
        }

    }

    void drawLine(){
        // if enter is pressed, draw a projectile motion dotted line from curTank to other tank


    }


    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
//        camera.viewportWidth= width;
//        camera.viewportHeight= height;
//        camera.update();
    }

    @Override
    public void pause() {
        paused= true;
    }

    @Override
    public void resume() {
        paused= false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        bg.dispose();
        terrain.getTexture().dispose();
        tank1.getTexture().dispose();
        tank2.getTexture().dispose();
        pauseButton.getTexture().dispose();
        pauseMenu.getTexture().dispose();
        world.dispose();
        shapeRenderer.dispose();
        debugRenderer.dispose();
        missile1.getTexture().dispose();
        missile2.getTexture().dispose();
        font.dispose();
    }
}





