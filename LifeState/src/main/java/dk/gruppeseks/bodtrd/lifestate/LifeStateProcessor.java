/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.lifestate;

import dk.gruppeseks.bodtrd.common.data.Entity;
import dk.gruppeseks.bodtrd.common.data.EntityState;
import dk.gruppeseks.bodtrd.common.data.World;
import dk.gruppeseks.bodtrd.common.interfaces.IEntityProcessor;

/**
 *
 * @author Thanusaan
 */
public class LifeStateProcessor implements IEntityProcessor
{
    @Override
    public void process(World world)
    {
        for (Entity e : world.entities())
        {
            EntityState state = e.getState();
            
            switch (state)
            {
                case DESTROYED:
                    world.removeEntity(e);
                    break;
                case JUST_CREATED:
                    e.setState(EntityState.ALIVE);
                    break;
                default:
                    break;
            }
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
