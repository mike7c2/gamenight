package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by mike on 18/10/15.
 */
public class Surface{

    private Sprite sprite;
    private Texture texture;
    private Body body;

    private int x;
    private int y;

    public Surface(World world, boolean top)
    {
        texture = new Texture(Gdx.files.internal("brick.jpg"));
        sprite = new Sprite(texture);


        Gdx.graphics.getHeight();

        sprite.setSize(Gdx.graphics.getWidth() * 2, Gdx.graphics.getHeight() / 8);

        if(top)
        {
            sprite.setPosition(0,0);
        }
        else {
            sprite.setPosition(0,Gdx.graphics.getHeight() - (Gdx.graphics.getHeight()/8));
        }

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(sprite.getX(), sprite.getY());
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        Fixture fixture = body.createFixture(fixtureDef);

    }

    public void update()
    {
        sprite.setX(getX() - Gdx.graphics.getWidth() / 1024);
        if(sprite.getX() < (-Gdx.graphics.getWidth())) {
            sprite.setX(0);
        }
    }

    public Sprite getSprite()
    {
        return sprite;
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
