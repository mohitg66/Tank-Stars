package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.physics.box2d.*;
import org.w3c.dom.Text;
import javax.swing.*;
import java.io.Serializable;


public class GameScreen2 implements Screen, Serializable {

    private final TankStars game;
    private OrthographicCamera camera;
    private Texture bg;
    private Texture terrain;
    private Texture tank1;
    private Texture tank2;
    private float tankScale;
    private Texture pauseButtonImg;
//    private Sprite pauseButton;
    private Texture pauseMenu;
    private boolean paused;


    public GameScreen2(TankStars game, Texture Img1, Texture Img2) {
        this.game= game;

        camera= new OrthographicCamera();
        camera.setToOrtho(false);

        bg= new Texture(Gdx.files.internal("Terrain bg.png"));
        terrain= new Texture(Gdx.files.internal("Terrain 2.png"));
        tank1= Img1;
        tank2= Img2;
        tankScale= 0.3f;
        pauseButtonImg= new Texture(Gdx.files.internal("Pause Button.png"));
//        pauseButton.draw(game.batch);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

//        if (paused){
//            handleTouch();
//            return;
//        }

        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(bg, 0, 0, camera.viewportWidth, camera.viewportHeight);
        game.batch.draw(terrain, 0, 0, camera.viewportWidth, terrain.getHeight());
        game.batch.draw(tank1, 1/6f* camera.viewportWidth, terrain.getHeight(), tank1.getWidth()*tankScale, tank1.getHeight()*tankScale);
        game.batch.draw(tank2, 4.5f/6f* camera.viewportWidth, terrain.getHeight(), tank2.getWidth()*tankScale, tank2.getHeight()*tankScale, 0, 0,  tank2.getWidth(), tank2.getHeight(),  true, false);
        game.batch.draw(pauseButtonImg, 10f, 0.9f* camera.viewportHeight);
        game.batch.end();

    }

    private void handleTouch(){
        if (Gdx.input.justTouched()){
            int mouseX= Gdx.input.getX();
            int mouseY= Gdx.input.getY();

//            mouseX>pauseButton.
        }
    }

    private void handleInput(){

    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();
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
        terrain.dispose();
        tank1.dispose();
        tank2.dispose();
        pauseButtonImg.dispose();
    }
}
