package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mike on 18/10/15.
 */
public class Surface{
    private Texture texture;

    private List<SurfaceBlock> blocks = new ArrayList<SurfaceBlock>();

    float bodyStartX;
    float bodyStartY;
    float length;
    float thickness;
    float noOfBlocks;
    float height;
    float blockLength;
    float speed = -10.0f;

    float smoothedValue;
    float smoothing = 2;

    World world;
    boolean inverted;


    Random randomGenerator = new Random();

    private SurfaceBlock nextBlock()
    {

        smoothedValue += ((randomGenerator.nextInt(2000) / 1000.0f) - smoothedValue) / smoothing;

        float blockThickness = this.thickness * (1 + (smoothedValue));
        if (inverted)
        {
            return new SurfaceBlock(world, texture, length + blockLength, bodyStartY - (blockThickness/2), blockLength, blockThickness, speed);
        }
        else {
            return new SurfaceBlock(world, texture, length + blockLength, bodyStartY + (blockThickness/2), blockLength, blockThickness, speed);
        }
    }

    public Surface(World world, float length, float thickness, float noOfBlocks, float height, boolean inverted, float speed)
    {

        texture = new Texture(Gdx.files.internal("brick.jpg"));
        this.speed = speed;
        this.world = world;
        this.bodyStartX = 0;
        this.bodyStartY = height;
        this.length = length;
        this.thickness = thickness;
        this.noOfBlocks = noOfBlocks;
        this.height = height;
        this.inverted = inverted;

        float blockLength = length/noOfBlocks;

        this.blockLength = blockLength;

        for (int i = 0; i < (noOfBlocks + 2); i++) {
            SurfaceBlock b;
            if (inverted)
            {
                b = new SurfaceBlock(world, texture, (blockLength * (i + 1)), bodyStartY - (thickness / 2), blockLength, thickness, speed);
            }
            else {
                b = new SurfaceBlock(world, texture, (blockLength * (i + 1)), bodyStartY + (thickness / 2), blockLength, thickness, speed);
            }
            blocks.add(b);
        }

    }

    public void update()
    {

        List<SurfaceBlock> toRemove = new ArrayList<SurfaceBlock>();

        //Loop over each of the elements making up the surface and update them
        for (SurfaceBlock s : blocks)
        {
            s.update();

            //If the object has left the screen add it to list for removal
            if (s.offScreen())
            {
                toRemove.add(s);
            }
        }

        //Remove any block which is now off the screen
        for (SurfaceBlock s : toRemove)
        {
            blocks.remove(s);

            blocks.add(nextBlock());

        }

    }


    public void draw(Batch batch) {
        //Loop over each of the elements making up the surface and update them
        for (SurfaceBlock s : blocks)
        {
            //System.out.println("Drawing sprinte " + Float.toString(s.getSprite().getX()) + " " + Float.toString(s.getSprite().getY()));
            s.getSprite().draw(batch);

        }
    }

    public class SurfaceBlock
    {
        private World world;
        private Body body;
        private Sprite sprite;
        private float width;

        public SurfaceBlock(World world, Texture texture, float startX, float startY, float width, float height, float speed)
        {

            //System.out.println("Generating surface block : " + Float.toString(startX) + " " + Float.toString(startY) + " " + Float.toString(width) + " " + Float.toString(height));

            sprite = new Sprite(texture);
            sprite.setSize(width, height);

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.KinematicBody;
            bodyDef.position.set(startX, startY);

            body = world.createBody(bodyDef);
            body.setLinearVelocity(speed, 0);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width/2, height/2);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1f;
            Fixture fixture = body.createFixture(fixtureDef);

            sprite.setPosition(body.getPosition().x  - (sprite.getWidth() / 2), body.getPosition().y  - (sprite.getHeight() / 2));

            this.width = width;
            this.world = world;
        }

        public void update()
        {
            sprite.setX(body.getPosition().x - (sprite.getWidth() / 2));
            sprite.setY(body.getPosition().y - (sprite.getHeight() / 2));

            //System.out.println("Setting sprite " + sprite.getX() + " " + sprite.getWidth());
        }

        public boolean offScreen()
        {
            boolean ret = false;

            //Check if this block has gone off screen
            if (body.getPosition().x < -width )
            {
                ret = true;

                //Do any cleaning up here, dispose sprite and body.etc
                world.destroyBody(body);
                //System.out.println("Removed body");
            }

            return ret;
        }

        public Sprite getSprite()
        {
            return sprite;
        }
    }

}
