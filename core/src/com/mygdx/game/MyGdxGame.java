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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.rmi.runtime.Log;


public class MyGdxGame extends ApplicationAdapter implements ApplicationListener, InputProcessor {

    // Spider pig sprite
	SpriteBatch batch;
	SpiderPig spiderPig;

    // Physics world
    World world;
    Box2DDebugRenderer renderer;
    Vector2 worldVectorUp;
    Vector2 worldVectorDown;
    // Bounding surfaces
    Surface top;
    Surface bottom;
    // Obstacles
    List<Obstacle> obstacles = new ArrayList<Obstacle>();
    // Physics
    float gravity = 0f;
    float jumpVelocity = 0f;

	@Override
	public void create () {

        gravity = Gdx.graphics.getHeight()/200;
        jumpVelocity = Gdx.graphics.getHeight()/20;

        // Create world with 2 possible gravity vectors
        worldVectorUp = new Vector2(0,gravity);
        worldVectorDown = new Vector2(0,-gravity);
        world = new World(worldVectorUp,true);
        renderer = new Box2DDebugRenderer();

        // Create spiderpig sprite
        batch = new SpriteBatch();
        spiderPig = new SpiderPig(world);

        // create surfaces and obstacles
        top = new Surface(world, true);
        bottom = new Surface(world, false);
        obstacles.add(new Obstacle(world));

        // Initialize touch screen handling
        touchInit();
	}

    // Game step handling
	private double accumulator;
	private double currentTime;
	private float step = 1.0f / 60.0f;
    private int count = 0;

    // Touch handling stuff
    private Map<Integer,TouchInfo> touches = new HashMap<Integer,TouchInfo>();
    private String touchMessage = "Touch something already!";
    private int w,h;
    private int worldVectorCount = 0;                                                               // tracks the current gravity vector: 0=UP; 1=DOWN
    boolean touchedDown = false;                                                                    // touch down flag
    boolean touchedUp = false;                                                                      // touch up flag (touch down must be true for this to be true)
    boolean touchedUp2 = false;                                                                     // double touch up flag (false = 1st touch; true = 2nd touch)
    boolean doubleTouch = false;                                                                    // double touch flag (true if a second touch comes quickly enough)
    long touchTime = 0;                                                                             // time of last touch
    long currentTouchTime = 0;                                                                      // current system time (could be temporary)
    long doubleTouchThreshold = 500;                                                                // threshold time for a double touch


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

            for (Obstacle o : obstacles)
            {
                o.update();
            }

            world.step(step, 1, 1);

            // Handle screen touches
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

        for (Obstacle o : obstacles)
        {
            o.getSprite().draw(batch);
        }

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
            System.out.println("doubleTouch = " + Boolean.toString(doubleTouch) + ";  touchUp2 = " + Boolean.toString(touchedUp2));
            if (doubleTouch){
                // flip gravity
                worldVectorCount = (worldVectorCount + 1) % 2;
                switch (worldVectorCount) {
                    case 0:
                        world.setGravity(worldVectorUp);
                        spiderPig.getBody().setLinearVelocity(0f, jumpVelocity);
                        System.out.println("touchHandler - gravity UP");
                        break;
                    case 1:
                        world.setGravity(worldVectorDown);
                        spiderPig.getBody().setLinearVelocity(0f, -1f*jumpVelocity);
                        System.out.println("touchHandler - gravity DOWN");
                        break;
                }
                // reset double touch flags
                doubleTouch = false;
                touchedUp2 = false;
                // flip spiderpig sprite to match new gravity
                spiderPig.getSprite().flip(false,true);
            }else {
                if (spiderPig != null)
                {
                    switch (worldVectorCount) {
                        case 0:
                            spiderPig.getBody().setLinearVelocity(0f, -1f*jumpVelocity);
                            break;
                        case 1:
                            spiderPig.getBody().setLinearVelocity(0f, jumpVelocity);
                            break;
                    }
                }
                System.out.println("touchHandler - jump");
            }
            // reset flags after touch is handled
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
            currentTouchTime = System.currentTimeMillis();
            touchedUp = true;
            if (touchedUp2){
                if (currentTouchTime - touchTime < doubleTouchThreshold){
                    doubleTouch = true;
                }else {
                    touchTime = currentTouchTime;
                    doubleTouch = false;
                }
            } else {
                touchTime = currentTouchTime;
                System.out.println("touchTime = " + Long.toString(touchTime) + "; currentTime - touchTime = " + Long.toString(currentTouchTime - touchTime));
                touchedUp2 = true;
                doubleTouch = false;
            }
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
