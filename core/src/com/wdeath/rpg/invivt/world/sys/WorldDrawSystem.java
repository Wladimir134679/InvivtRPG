package com.wdeath.rpg.invivt.world.sys;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.wdeath.rpg.invivt.world.comps.WorldPhysicsComponent;
import com.wdeath.rpg.invivt.world.comps.WorldRenderComponent;

public class WorldDrawSystem extends EntitySystem {

    private Entity entity;
    private WorldRenderComponent wrc;

    public WorldDrawSystem(Entity entity) {
        this.entity = entity;
        wrc = entity.getComponent(WorldRenderComponent.class);
    }

    @Override
    public void update(float deltaTime) {
        wrc.camera.update();
        wrc.tiledMapRenderer.setView(wrc.camera);
        wrc.tiledMapRenderer.render();
        wrc.debugRenderer.render(entity.getComponent(WorldPhysicsComponent.class).world, wrc.camera.combined);
    }
}
