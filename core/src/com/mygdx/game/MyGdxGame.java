package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;



public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	SpiderPig spiderPig;

    World world;
    Box2DDebugRenderer renderer;

	@Override
	public void create () {
        batch = new SpriteBatch();

        world = new World(new Vector2(0, 10), true);
        renderer = new Box2DDebugRenderer();

        spiderPig = new SpiderPig(world);
	}

	private double accumulator;
	private double currentTime;
	private float step = 1.0f / 60.0f;
    private int count = 0;

	@Override
	public void render() {


		double newTime = TimeUtils.millis() / 1000.0;
		double frameTime = Math.min(newTime - currentTime, 0.25);
		float deltaTime = (float) frameTime;

        accumulator += deltaTime;

		while ( accumulator >= step){
            //Step the game here
			accumulator -= step;
            count += 1;

            spiderPig.update();
            world.step(step, 1, 1);

        }

        //Do the drawing here

        //Clear the screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Redraw all the guff
		batch.begin();

        spiderPig.getSprite().draw(batch);

        batch.end();

        System.out.println("Woo loop! " + Integer.toString(count));

	}

    @Override
    public void dispose() {
        batch.dispose();
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
