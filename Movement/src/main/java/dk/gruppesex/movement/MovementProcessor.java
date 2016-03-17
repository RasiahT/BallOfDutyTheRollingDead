/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppesex.movement;

import dk.gruppesex.bodtrd.common.data.Entity;
import dk.gruppesex.bodtrd.common.data.GameData;
import dk.gruppesex.bodtrd.common.data.entityelements.Position;
import dk.gruppesex.bodtrd.common.data.entityelements.Velocity;
import dk.gruppesex.bodtrd.common.data.util.Vector2;
import dk.gruppesex.bodtrd.common.interfaces.IEntityProcessor;
import java.util.Map;

/**
 *
 * @author Morten
 */
public class MovementProcessor implements IEntityProcessor
{
    @Override
    public void process(GameData gameData, Map<Integer, Entity> world)
    {
        double dt = gameData.getDeltaTime();

        for (Entity e : world.values())
        {
            Position p = e.get(Position.class);
            Velocity velocity = e.get(Velocity.class);

            if (p == null || velocity == null)
            {
                continue;
            }

            Vector2 v = velocity.getVector();

            p.setX(p.getX() + (v.getX() * 250 * dt)); // TODO replace hardcoded 250 with  v.getMagnitude()
            p.setY(p.getY() + (v.getY() * 250 * dt)); // TODO replace hardcoded 250 with  v.getMagnitude()
        }
    }
}
