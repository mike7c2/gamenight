package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by mike on 18/10/15.
 */
public class Obstacle{

    private Sprite sprite;
    private Texture texture;
    private Body body;


    private int count = 0;

    private boolean isGone = false;

    public Obstacle(World world, float width, float height, float x, float y)
    {
        texture = new Texture(Gdx.files.internal("dog.jpg"));
        sprite = new Sprite(texture);


        sprite.setSize(width, height);

        sprite.setPosition(x,y);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        Fixture fixture = body.createFixture(fixtureDef);

    }

    public void update()
    {
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setRotation((float)Math.toDegrees(body.getAngle()));


        if (body.getPosition().x < 0)
        {
            isGone = true;
        }
    }

    public boolean isGone()
    {
        return isGone;
    }

    public void draw(Batch batch) {

        sprite.draw(batch);
    }


    public float getX()
    {
        return sprite.getX();
    }

    public float getY()
    {
        return sprite.getY();
    }

}
