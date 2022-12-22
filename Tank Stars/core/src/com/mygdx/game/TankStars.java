package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.Serializable;

public class TankStars extends Game implements Serializable {
    public SpriteBatch batch;

    @Override
    public void create() {
        batch= new SpriteBatch();
        this.setScreen(new LoadingScreen(this));
//        this.setScreen(new GameScreen(this, new Texture(Gdx.files.internal("Abrams1.png")), new Texture(Gdx.files.internal("Frost1.png"))));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
