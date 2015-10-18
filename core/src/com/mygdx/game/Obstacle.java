package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

    private int x;
    private int y;

    private int count;

    public Obstacle(World world)
    {
        texture = new Texture(Gdx.files.internal("brick.jpg"));
        sprite = new Sprite(texture);


        sprite.setSize(Gdx.graphics.getWidth()/12, Gdx.graphics.getHeight()/4);

        sprite.setPosition(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/8);

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
        count += Gdx.graphics.getWidth()/512;
        body.setTransform(Gdx.graphics.getWidth() - (count % Gdx.graphics.getWidth()),0,0);

        System.out.println("Obstacle : " + Float.toString(-(count % Gdx.graphics.getWidth())));
        sprite.setX(body.getPosition().x);

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
