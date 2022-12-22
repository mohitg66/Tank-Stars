package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.Serializable;

public class LoadingScreen implements Screen, Serializable {

	private final TankStars game;
	private OrthographicCamera camera;
	private Viewport viewport;
	private Texture loadingScreenImg;

	public LoadingScreen(TankStars game) {
		this.game = game;
		camera= new OrthographicCamera();
		camera.setToOrtho(false);
		viewport= new FillViewport(camera.viewportWidth, camera.viewportHeight);

		loadingScreenImg= new Texture(Gdx.files.internal("Loading Screen 1.jpg"));

	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(loadingScreenImg, 0,0, camera.viewportWidth, camera.viewportHeight);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new HomeScreen1(game));
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
		camera.update();
		viewport.update(width, height);
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

	}
}