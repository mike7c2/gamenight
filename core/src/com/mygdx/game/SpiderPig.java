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
public class SpiderPig {

    private Sprite sprite;
    private Texture texture;
    private Body body;

    private int x;
    private int y;

    public SpiderPig(World world)
    {
        texture = new Texture(Gdx.files.internal("orangetest.bmp"));
        sprite = new Sprite(texture);
        sprite.setSize(Gdx.graphics.getWidth()/8, Gdx.graphics.getHeight()/8);
        sprite.flip(false,true);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Gdx.graphics.getWidth() / 2, sprite.getY());

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
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
    }

    public Body getBody()
    {
        return body;
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
