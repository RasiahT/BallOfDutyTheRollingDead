/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.movement;

import dk.gruppeseks.bodtrd.common.data.Entity;
import dk.gruppeseks.bodtrd.common.data.World;
import dk.gruppeseks.bodtrd.common.data.entityelements.Position;
import dk.gruppeseks.bodtrd.common.data.entityelements.Velocity;
import dk.gruppeseks.bodtrd.common.data.util.Vector2;
import dk.gruppeseks.bodtrd.common.interfaces.IEntityProcessor;

/**
 *
 * @author Morten
 */
public class MovementProcessor implements IEntityProcessor
{
    @Override
    public void process(World world)
    {
        double dt = world.getGameData().getDeltaTime();

        for (Entity e : world.entities())
        {
            Position p = e.get(Position.class);
            Velocity velocity = e.get(Velocity.class);

            if (p == null || velocity == null)
            {
                continue;
            }

            Vector2 v = velocity.getVector();

            p.setX(p.getX() + (v.getX() * dt));
            p.setY(p.getY() + (v.getY() * dt));
        }
    }

    @Override
    public void notifyEntitiesAdded(Entity entity)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyEntitiesRemoved(Entity entity)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
