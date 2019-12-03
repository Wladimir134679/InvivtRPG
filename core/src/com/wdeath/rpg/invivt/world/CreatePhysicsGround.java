package com.wdeath.rpg.invivt.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.wdeath.rpg.invivt.world.comps.WorldGroundComponent;
import com.wdeath.rpg.invivt.world.comps.WorldMapComponent;
import com.wdeath.rpg.invivt.world.comps.WorldPhysicsComponent;

import java.util.Iterator;


public class CreatePhysicsGround {

    public static void create(Entity world){
        WorldPhysicsComponent wpc = world.getComponent(WorldPhysicsComponent.class);
        WorldMapComponent wmc = world.getComponent(WorldMapComponent.class);
        WorldGroundComponent wgc = world.getComponent(WorldGroundComponent.class);

        createBody(wgc, wpc);
        createFixtures(wpc, wmc, wgc);
    }

    private static void createBody(WorldGroundComponent wgc, WorldPhysicsComponent wpc){
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(0, 0);
        def.fixedRotation = true;

        wgc.body = wpc.world.createBody(def);
    }

    private static void createFixtures(WorldPhysicsComponent wpc, WorldMapComponent wmc, WorldGroundComponent wgc){
        MapLayers mapLayers = wmc.map.getLayers();
        TiledMapTileLayer ground = (TiledMapTileLayer)mapLayers.get("ground");
        int width = (int)wmc.map.getProperties().get("width");
        int height = (int)wmc.map.getProperties().get("height");

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                TiledMapTileLayer.Cell cell = ground.getCell(x, y);
                if(cell == null)
                    continue;
                createFixture(x, y, wpc, wgc);
            }
        }
    }

    private static void createFixture(int x, int y, WorldPhysicsComponent wpc, WorldGroundComponent wgc){
        ChainShape shape = new ChainShape();
        Vector2[] vecs = new Vector2[4];
        vecs[0] = new Vector2(x, y);
        vecs[1] = new Vector2(x + 1, y);
        vecs[2] = new Vector2(x + 1, y + 1);
        vecs[3] = new Vector2(x, y + 1);
        shape.createLoop(vecs);

        wgc.body.createFixture(shape, 0);
    }
}
