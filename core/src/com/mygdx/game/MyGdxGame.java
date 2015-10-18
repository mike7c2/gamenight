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
	Texture img;

	SpiderPig spiderPig;
    Sprite spriteSpiderPig;
    int countX = 0;
    int countY = 0;

	@Override
	public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        spiderPig = new SpiderPig();

        initBox2d();

        // Initialize touch message text
        touchInit();
	}
    private BitmapFont font;
	private double accumulator;
	private double currentTime;
	private float step = 1.0f / 60.0f;

    private int count = 0;

    private World world;
    private Box2DDebugRenderer renderer;

    // Touchscreen input stuff
    private Map<Integer,TouchInfo> touches = new HashMap<Integer,TouchInfo>();
    private String touchMessage = "Touch something already!";
    private int w,h;


    private void initBox2d()
    {
        world = new World(new Vector2(0, -10), true);
        renderer = new Box2DDebugRenderer();


    }

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

            // spiderpig moving junk
            countX ++;
            countY ++;

            spiderPig.update();
            world.step(step, 1, 1);

        }

        //Do the drawing here

        //Clear the screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Redraw all the guff
		batch.begin();

        batch.draw(spiderPig.getSprite(), spiderPig.getX(), spiderPig.getY());

        // Get screen touches and display messages
        touchHandler();

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
        touchMessage = "";
        for(int i = 0; i < 5; i++){
            if(touches.get(i).touched)
                touchMessage += "Finger:" + Integer.toString(i) + "; touch at:" +
                        Float.toString(touches.get(i).touchX) +
                        "," +
                        Float.toString(touches.get(i).touchY) +
                        "\n";

        }
        System.out.println(touchMessage);
//        TextBounds tb = font.getBounds(touchMessage);
//        float x = w/2 - tb.width/2;
//        float y = h/2 + tb.height/2;
//        font.drawMultiLine(batch, touchMessage, x, y);
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
        if(pointer < 5){
            touches.get(pointer).touchX = screenX;
            touches.get(pointer).touchY = screenY;
            touches.get(pointer).touched = true;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer < 5){
            touches.get(pointer).touchX = 0;
            touches.get(pointer).touchY = 0;
            touches.get(pointer).touched = false;
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
