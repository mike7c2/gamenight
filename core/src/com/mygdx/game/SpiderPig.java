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
import com.badlogic.gdx.physics.box2d.CircleShape;

/**
 * Created by mike on 18/10/15.
 */
public class SpiderPig {

    World world;

    private Sprite sprite;
    private Texture texture;
    private Body body;

    private float width;
    private float height;

    private int x;
    private int y;

    public SpiderPig(World world, float width, float height, float startX, float startY) {
        texture = new Texture(Gdx.files.internal("spiderpig.png"));
        sprite = new Sprite(texture);

        sprite.setSize(width, height);
        sprite.flip(false, true);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startX, startY);

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();

        //shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);
        shape.setRadius(sprite.getWidth()/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);

        this.width = width;
        this.height = height;
        this.world = world;
    }

    public void update() {

        Vector2 velo = body.getLinearVelocity();
        Vector2 dirVec = velo.add(1.5f, 0.0f);

        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        //sprite.setRotation(dirVec.angle());
        sprite.setRotation((float)Math.toDegrees(body.getAngle()));


    }

    public Body getBody() {
        return body;
    }

    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    public void flip() {
        sprite.flip(false, true);

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
