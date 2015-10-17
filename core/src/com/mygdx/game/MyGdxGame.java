package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

public class MyGdxGame extends ApplicationAdapter {

	SpriteBatch batch;
	Texture spiderPig;
    Sprite spriteSpiderPig;

    int countX = 0;
    int countY = 0;

	@Override
	public void create () {
        batch = new SpriteBatch();
        spiderPig = new Texture(Gdx.files.internal("spiderpig.png"));
        spriteSpiderPig = new Sprite(spiderPig);
	}

    @Override
    public void dispose() {
        batch.dispose();
        spiderPig.dispose();
    }

	@Override
	public void render () {
        countX ++;
        countY ++;

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(spiderPig, countX, countY, 100, 50);
		batch.end();
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
}
