package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	Texture spiderPig;
    Sprite spriteSpiderPig;
    int countX = 0;
    int countY = 0;

	@Override
	public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

		// spiderpig texture
        spiderPig = new Texture(Gdx.files.internal("spiderpig.png"));
        spriteSpiderPig = new Sprite(spiderPig);
	}
    private BitmapFont font;
	private double accumulator;
	private double currentTime;
	private float step = 1.0f / 60.0f;

    private int count = 0;


	@Override
	public void render() {
		// spiderpig moving junk
        countX ++;
        countY ++;
		batch.begin();
		batch.draw(spiderPig, countX, countY, 100, 50);
		batch.end();

		double newTime = TimeUtils.millis() / 1000.0;
		double frameTime = Math.min(newTime - currentTime, 0.25);
		float deltaTime = (float) frameTime;

        accumulator += deltaTime;

		while ( accumulator >= step){
			accumulator -= step;
            count += 1;

			//Step the game here
        }

        //Do the drawing here

        //Clear the screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Redraw all the guff
		batch.begin();

        font.draw(batch, "Hello World " + Integer.toString(count), 200 + (count % 200), 200 + (count % 200));

        batch.end();

        System.out.println("Woo loop! " + Integer.toString(count));

	}

    @Override
    public void dispose() {
        batch.dispose();
        spiderPig.dispose();
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
