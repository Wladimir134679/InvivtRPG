package com.wdeath.rpg.invivt.world.comps;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class WorldRenderComponent implements Component {

    public float ppm;

    public Box2DDebugRenderer debugRenderer;

    public OrthographicCamera camera;
    public SpriteBatch batch;

    public OrthogonalTiledMapRenderer tiledMapRenderer;
}
