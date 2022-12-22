package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;
import java.io.Serializable;

public class HomeScreen1 implements Screen, Serializable {

    private final TankStars game;
    private OrthographicCamera camera;
    private Texture selectedScreen;
    private Rectangle newGameButton;
    private Texture newGameButtonImg;
    private Rectangle savedGameButton;
    private Texture savedGameButtonImg;


    public HomeScreen1(TankStars game) {
        this.game= game;
//        camera= new OrthographicCamera(1920, 1080);
        camera= new OrthographicCamera();
        camera.setToOrtho(false);

        selectedScreen= new Texture(Gdx.files.internal("Main Home Screen.png"));

        newGameButtonImg= new Texture(Gdx.files.internal("New Game Button.png"));
        newGameButton= new Rectangle(100, 200, newGameButtonImg.getWidth(), newGameButtonImg.getHeight());
        newGameButton.x= (int)(camera.viewportWidth*0.8)- newGameButton.width/2;
        newGameButton.y= (int)(camera.viewportHeight*0.55)- newGameButton.height/2;

        savedGameButtonImg= new Texture(Gdx.files.internal("Saved Game Button.png"));
        savedGameButton= new Rectangle(savedGameButtonImg.getWidth(), savedGameButtonImg.getHeight());
        savedGameButton.x= newGameButton.x;
        savedGameButton.y= newGameButton.y-(int)(newGameButton.height*1.2);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(selectedScreen, 0, 0, camera.viewportWidth, camera.viewportHeight);
        game.batch.draw(newGameButtonImg, newGameButton.x, newGameButton.y, newGameButton.width, newGameButton.height);
        game.batch.draw(savedGameButtonImg, savedGameButton.x, savedGameButton.y, savedGameButton.width, savedGameButton.height);
        game.batch.end();

        if (Gdx.input.justTouched()){
            handleTouch();
        }
    }

    private void handleTouch(){
        int mouseX= Gdx.input.getX();
        int mouseY= Gdx.input.getY();

        game.batch.begin();

        if (mouseX>newGameButton.x && mouseX< (newGameButton.x+newGameButton.getWidth()) && (camera.viewportHeight- mouseY)< (newGameButton.y+ newGameButton.height) && (camera.viewportHeight- mouseY)> newGameButton.y){
            game.setScreen(new HomeScreen2(game));
        }
//        else if (mouseX>savedGameButton.x && mouseX<savedGameButton.x+savedGameButton.getWidth() && (camera.viewportHeight- mouseY)< (savedGameButton.y+ savedGameButton.height) && (camera.viewportHeight- mouseY)> savedGameButton.y){
//            game.setScreen(new GameScreen(game, tankImg));
//        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
//        camera.setToOrtho(false, width, height);
//        camera.update();
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
        selectedScreen.dispose();
        newGameButtonImg.dispose();
        savedGameButtonImg.dispose();
    }
}