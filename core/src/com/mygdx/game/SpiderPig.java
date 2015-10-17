package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by mike on 18/10/15.
 */
public class SpiderPig {

    private Sprite sprite;
    private Texture texture;

    private int x;
    private int y;

    public SpiderPig()
    {
        texture = new Texture(Gdx.files.internal("spiderpig.png"));
        sprite = new Sprite(texture);



    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }



}
