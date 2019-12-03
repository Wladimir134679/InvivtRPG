package com.wdeath.rpg.invivt.world.comps;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public class WorldGroundComponent implements Component {

    public Body body;
    public Fixture[] fixtures;
}
