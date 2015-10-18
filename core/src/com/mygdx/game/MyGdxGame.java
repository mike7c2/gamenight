package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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

import java.util.HashMap;
import java.util.Map;

import sun.rmi.runtime.Log;


public class MyGdxGame extends ApplicationAdapter implements ApplicationListener, InputProcessor {
    
	SpriteBatch batch;
	SpiderPig spiderPig;

    World world;
    Box2DDebugRenderer renderer;
    Vector2 worldVectorUp;
    Vector2 worldVectorDown;

    Surface top;
    Surface bottom;

    Obstacle obstacle;

	@Override
	public void create () {
        batch = new SpriteBatch();

        // create up and down world vectors for gravity switching
        worldVectorUp = new Vector2(0,1);
        worldVectorDown = new Vector2(0,-1);
        world = new World(worldVectorUp, true);
        renderer = new Box2DDebugRenderer();

        spiderPig = new SpiderPig(world);

        top = new Surface(world, true);
        bottom = new Surface(world, false);

        obstacle = new Obstacle(world);

        // Initialize touch message text
        touchInit();
	}

	private double accumulator;
	private double currentTime;
	private float step = 1.0f / 60.0f;
    private int count = 0;

    // Touchscreen input stuff
    private Map<Integer,TouchInfo> touches = new HashMap<Integer,TouchInfo>();
    private String touchMessage = "Touch something already!";
    private int w,h;
    private int worldVectorCount = 0;
    boolean touchedDown = false;
    boolean touchedUp = false;

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
            top.update();
            bottom.update();
            obstacle.update();
            world.step(step, 1, 1);

            // Get screen touches and display messages
            touchHandler();

        }

        //Do the drawing here

        //Clear the screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Redraw all the guff
		batch.begin();

        spiderPig.getSprite().draw(batch);
        top.getSprite().draw(batch);
        bottom.getSprite().draw(batch);
        obstacle.getSprite().draw(batch);

        batch.end();

//        System.out.println("Woo loop! " + Integer.toString(count));

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


    class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }



    public void touchInit() {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        Gdx.input.setInputProcessor(this);
        for(int i = 0; i < 5; i++){
            touches.put(i, new TouchInfo());
        }
    }
    public void touchHandler(){
        if(touchedUp) {
            // flip gravity
            worldVectorCount = (worldVectorCount + 1) % 2;
            switch (worldVectorCount) {
            case 0:
//                worldVectorUp.set(0,1);
                world.setGravity(worldVectorUp);
                System.out.println("touchHandler - gravity UP");
                break;
            case 1:
//                worldVectorUp.set(0,-1);
                world.setGravity(worldVectorDown);
                System.out.println("touchHandler - gravity DOWN");
                break;
            }
            touchedDown = false;
            touchedUp = false;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchedDown = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(touchedDown){
            touchedUp = true;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
