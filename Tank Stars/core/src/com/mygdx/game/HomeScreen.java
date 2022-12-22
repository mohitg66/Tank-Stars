//package com.mygdx.game;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.utils.ScreenUtils;
//
//import java.awt.*;
//import java.io.Serializable;
//
//public class HomeScreen implements Screen, Serializable {
//
//    private final TankStars game;
//    private OrthographicCamera camera;
//    private Texture coalitionScreen1;
//    private Texture coalitionScreen2;
//    private Texture frostScreen1;
//    private Texture frostScreen2;
//    private Texture abramsScreen1;
//    private Texture abramsScreen2;
//    private Texture selectedScreen1;
//    private Texture selectedScreen2;
//    private Texture tankImg;
//    private Rectangle newGameButton;
//    private Texture newGameButtonImg;
//    private Rectangle savedGameButton;
//    private Texture savedGameButtonImg;
//    private Rectangle chooseButton;
//    private Texture chooseButtonImg;
//
//
//    public HomeScreen(TankStars game) {
//        this.game= game;
////        camera= new OrthographicCamera(1920, 1080);
//        camera= new OrthographicCamera();
//        camera.setToOrtho(false);
//
//        coalitionScreen1= new Texture(Gdx.files.internal("Coalition Screen P1.png"));
//        frostScreen1= new Texture(Gdx.files.internal("Frost Screen P1.png"));
//        abramsScreen1= new Texture(Gdx.files.internal("Abrams Screen P1.png"));
//
//        coalitionScreen2= new Texture(Gdx.files.internal("Coalition Screen P2.png"));
//        frostScreen2= new Texture(Gdx.files.internal("Frost Screen P2.png"));
//        abramsScreen2= new Texture(Gdx.files.internal("Abrams Screen P2.png"));
//
//        selectedScreen1= coalitionScreen1;
//        selectedScreen2= coalitionScreen2;
//
//        newGameButtonImg= new Texture(Gdx.files.internal("New Game Button.png"));
//        newGameButton= new Rectangle(100, 200, newGameButtonImg.getWidth(), newGameButtonImg.getHeight());
//        newGameButton.x= (int)(camera.viewportWidth*0.8)- newGameButton.width/2;
//        newGameButton.y= (int)(camera.viewportHeight*0.306)- newGameButton.height/2;
//
//        savedGameButtonImg= new Texture(Gdx.files.internal("Saved Game Button.png"));
//        savedGameButton= new Rectangle(savedGameButtonImg.getWidth(), savedGameButtonImg.getHeight());
//        savedGameButton.x= newGameButton.x;
//        savedGameButton.y= newGameButton.y-(int)(newGameButton.height*1.2);
//
//        tankImg= new Texture(Gdx.files.internal("Coalition1.png"));
//
//    }
//
//    @Override
//    public void show() {
//
//    }
//
//    @Override
//    public void render(float delta) {
//        ScreenUtils.clear(0, 0, 0, 1);
//        camera.update();
//        game.batch.setProjectionMatrix(camera.combined);
//        game.batch.begin();
//        game.batch.draw(selectedScreen1, 0, 0, camera.viewportWidth, camera.viewportHeight);
//        game.batch.draw(newGameButtonImg, newGameButton.x, newGameButton.y, newGameButton.width, newGameButton.height);
//        game.batch.draw(savedGameButtonImg, savedGameButton.x, savedGameButton.y, savedGameButton.width, savedGameButton.height);
//        game.batch.end();
////        game.batch.draw(Abrams, 0, 0);
//
//        if (Gdx.input.justTouched()){
//            handleTouch();
//        }
//    }
//
//    private void handleTouch(){
//        int mouseX= Gdx.input.getX();
//        int mouseY= Gdx.input.getY();
//
//        float y= 0.422f* camera.viewportHeight;
//        float x1= 0.67f* camera.viewportWidth;
//        float x2= 0.795f* camera.viewportWidth;
//        float x3= 0.919f* camera.viewportWidth;
//        float width= 0.108f* camera.viewportWidth;
//        float height= 0.108f* camera.viewportWidth;
//
//        game.batch.begin();
//
//        if (mouseX>(x1-width/2) && mouseX<(x1+width/2) && mouseY>(y-height/2) && mouseY<(y+height/2)){
//            tankImg= new Texture(Gdx.files.internal("Coalition1.png"));
//            selectedScreen1= coalitionScreen1;
//            game.batch.draw(coalitionScreen1, 0, 0, camera.viewportWidth, camera.viewportHeight);
//            game.batch.draw(newGameButtonImg, newGameButton.x, newGameButton.y, newGameButton.width, newGameButton.height);
//            game.batch.draw(savedGameButtonImg, savedGameButton.x, savedGameButton.y, savedGameButton.width, savedGameButton.height);
////            game.setScreen(new GameScreen(game, new Tank(tankImg)));
//        }
//        else if (mouseX>(x2-width/2) && mouseX<(x2+width/2) && mouseY>(y-height/2) && mouseY<(y+height/2)){
//            tankImg= new Texture(Gdx.files.internal("Frost1.png"));
//            selectedScreen1= frostScreen1;
//            game.batch.draw(frostScreen1, 0, 0, camera.viewportWidth, camera.viewportHeight);
//            game.batch.draw(newGameButtonImg, newGameButton.x, newGameButton.y, newGameButton.width, newGameButton.height);
//            game.batch.draw(savedGameButtonImg, savedGameButton.x, savedGameButton.y, savedGameButton.width, savedGameButton.height);
////            game.setScreen(new GameScreen(game, new Tank(tankImg)));
//        }
//        else if (mouseX>(x3-width/2) && mouseX<(x3+width/2) && mouseY>(y-height/2) && mouseY<(y+height/2)) {
//            tankImg = new Texture(Gdx.files.internal("Abrams1.png"));
//            selectedScreen1= abramsScreen1;
//            game.batch.draw(abramsScreen1, 0, 0, camera.viewportWidth, camera.viewportHeight);
//            game.batch.draw(newGameButtonImg, newGameButton.x, newGameButton.y, newGameButton.width, newGameButton.height);
//            game.batch.draw(savedGameButtonImg, savedGameButton.x, savedGameButton.y, savedGameButton.width, savedGameButton.height);
////            game.setScreen(new GameScreen(game, new Tank(tankImg)));
//        }
//        else if (mouseX>newGameButton.x && mouseX< (newGameButton.x+newGameButton.getWidth()) && (camera.viewportHeight- mouseY)< (newGameButton.y+ newGameButton.height) && (camera.viewportHeight- mouseY)> newGameButton.y){
//            game.setScreen(new GameScreen(game, tankImg));
//        }
////        else if (mouseX>savedGameButton.x && mouseX<savedGameButton.x+savedGameButton.getWidth() && (camera.viewportHeight- mouseY)< (savedGameButton.y+ savedGameButton.height) && (camera.viewportHeight- mouseY)> savedGameButton.y){
////            game.setScreen(new GameScreen(game, tankImg));
////        }
//
//        game.batch.end();
//    }
//
//    @Override
//    public void resize(int width, int height) {
////        camera.setToOrtho(false, width, height);
////        camera.update();
//    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void hide() {
//
//    }
//
//    @Override
//    public void dispose() {
//        coalitionScreen1.dispose();
//        frostScreen1.dispose();
//        abramsScreen1.dispose();
//        selectedScreen1.dispose();
//    }
//}