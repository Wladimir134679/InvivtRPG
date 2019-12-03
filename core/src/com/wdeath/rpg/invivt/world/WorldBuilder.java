package com.wdeath.rpg.invivt.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.wdeath.rpg.invivt.world.comps.WorldGroundComponent;
import com.wdeath.rpg.invivt.world.comps.WorldMapComponent;
import com.wdeath.rpg.invivt.world.comps.WorldPhysicsComponent;
import com.wdeath.rpg.invivt.world.comps.WorldRenderComponent;
import com.wdeath.rpg.invivt.world.sys.WorldDrawSystem;

public class WorldBuilder {

    public static void init(Engine engine, String pathLoad){
        WorldBuilder worldBuilder = new WorldBuilder();
        worldBuilder
                .createComponents()
                .loadWorld(pathLoad)
                .createPhysics()
                .createRender()
                .builder(engine);
    }

    private Entity entity;

    private WorldBuilder(){
        entity = new Entity();
    }

    public WorldBuilder createComponents(){
        WorldMapComponent wmc = new WorldMapComponent();
        WorldPhysicsComponent wpc = new WorldPhysicsComponent();
        WorldRenderComponent wrc = new WorldRenderComponent();
        WorldGroundComponent wgc = new WorldGroundComponent();

        entity.add(wmc);
        entity.add(wpc);
        entity.add(wrc);
        entity.add(wgc);
        return this;
    }

    public WorldBuilder loadWorld(String path){
        WorldMapComponent wmc = entity.getComponent(WorldMapComponent.class);
        TmxMapLoader tmxMapLoader = new TmxMapLoader();
        wmc.map = tmxMapLoader.load(path);
        return this;
    }

    public WorldBuilder createPhysics(){
        WorldPhysicsComponent wpc = entity.getComponent(WorldPhysicsComponent.class);
        wpc.world = new World(new Vector2(0, -9f), false);

        CreatePhysicsGround.create(entity);
        return this;
    }

    public WorldBuilder createRender(){
        WorldRenderComponent wrc = entity.getComponent(WorldRenderComponent.class);
        WorldMapComponent wmc = entity.getComponent(WorldMapComponent.class);
        wrc.ppm = 32;
        wrc.camera = new OrthographicCamera();
        wrc.camera.setToOrtho(false, Gdx.graphics.getWidth() / wrc.ppm, Gdx.graphics.getHeight() / wrc.ppm);
        wrc.batch = new SpriteBatch();
        wrc.tiledMapRenderer = new OrthogonalTiledMapRenderer(wmc.map, 1/wrc.ppm);
        wrc.debugRenderer = new Box2DDebugRenderer();
        return this;
    }

    public WorldBuilder builder(Engine engine){
        engine.addEntity(entity);

        WorldDrawSystem wds = new WorldDrawSystem(entity);
        engine.addSystem(wds);

        return this;
    }
}
