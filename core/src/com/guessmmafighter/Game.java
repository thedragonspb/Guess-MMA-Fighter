package com.guessmmafighter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;

	Vector3 touchPos;

	Render render;
	Event  event;
	Logic  logic;

	AdHandler handler;

	public Game(AdHandler handler){
		this.handler = handler;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 720, 1280);

		touchPos = new Vector3();

		logic  = new Logic();
		render = new Render(batch, logic);
		event  = new Event(logic, touchPos);
	}

	@Override
	public void render () {
		handler.showAds(true);

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		if(logic.state == 0){
			render.drawMenu();
		}else if (logic.state == 1){
			render.drawGame();
		}else if(logic.state == 2){
			render.drawBonus();
		}

		batch.end();

		if(Gdx.input.justTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);

			if(logic.state == 0){
				event.eventMenu();
			}else if (logic.state == 1){
				event.eventGame();
				if(logic.checkAnswer()){
					logic.sound.playSound(GameSound.CORRECT_ANSWER);
					logic.state = 2;
				}
			}else if(logic.state == 2){
				event.eventBonus();
			}
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		render.delete();
		logic.delete();
	}
}

