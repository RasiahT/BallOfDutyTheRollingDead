/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.collision;

import dk.gruppeseks.bodtrd.common.data.Entity;
import dk.gruppeseks.bodtrd.common.data.World;
import dk.gruppeseks.bodtrd.common.data.entityelements.Body;
import dk.gruppeseks.bodtrd.common.data.entityelements.Position;
import dk.gruppeseks.bodtrd.common.data.entityelements.Velocity;
import dk.gruppeseks.bodtrd.common.interfaces.IEntityProcessor;
import java.util.Collection;

/**
 *
 * @author Chris
 */
public class CollisionProcessor implements IEntityProcessor
{
    @Override
    public void process(World world)
    {
        Collection<Entity> entities = world.entities(); // todo move to World. we should only make one immutable collection per process call.

        for (Entity handled : entities)
        {
            Position handledPos = handled.get(Position.class);
            Velocity handledVel = handled.get(Velocity.class);
            Body handledBody = handled.get(Body.class);
            CollisionData handledData = handled.get(CollisionData.class);

            if (handledPos == null || handledVel == null || handledBody == null || handledData == null)
            {
                continue;
            }
            double startX = handledPos.getX();
            double startY = handledPos.getY();

            boolean collision = false;

            for (Entity ent : entities)
            {

                CollisionData entData = ent.get(CollisionData.class);

                if (ent.getID() == handled.getID() || entData == null)
                {
                    continue;
                }
                if (CollisionHandler.isColliding(handled, ent))
                {
                    Position firstPos = CollisionHandler.collisionResponse(handled, ent);
                    handledPos.setX(firstPos.getX());
                    handledPos.setY(firstPos.getY());

                    for (Entity ent2 : entities) // Checks if it collides with anything.
                    {
                        CollisionData ent2Data = ent.get(CollisionData.class);

                        if (ent2.getID() == handled.getID() || ent2.getID() == ent.getID() || ent2Data == null)
                        {
                            continue;
                        }
                        if (CollisionHandler.isColliding(handled, ent2))
                        {
                            for (Entity ent3 : entities)
                            {
                                CollisionData ent3Data = ent.get(CollisionData.class);

                                if (ent3.getID() == handled.getID() || ent3.getID() == ent.getID()
                                        || ent3.getID() == ent2.getID() || ent3Data == null)
                                {
                                    continue;
                                }
                                if (CollisionHandler.isColliding(handled, ent3))
                                {
                                    handledPos.setX(startX);
                                    handledPos.setY(startY); // If it even collides after a third collision correction, then dont do any corrections at all.
                                    collision = true;
                                    break;
                                }

                            }
                            if (!collision)
                            {
                                // if it doesnt collide with a third object after the second collision correction, put it to the secondcalculated position.
                                Position response = CollisionHandler.collisionResponse(handled, ent2);
                                handledPos.setX(response.getX());
                                handledPos.setY(response.getY());
                            }
                            collision = true;
                            break;
                        }

                    }
                    if (!collision) // if it doesnt collide with a second object, put it to the first calculated position.
                    {
                        handledPos.setX(firstPos.getX());
                        handledPos.setY(firstPos.getY());
                    }
                    collision = true; // with the collided object anymore.
                    break;
                }
            }
        }
    }

    @Override
    public void notifyEntitiesAdded(Entity entity)
    {
        CollisionHandler.addCollisionData(entity);
    }

    @Override
    public void notifyEntitiesRemoved(Entity entity)
    {
        entity.remove(CollisionData.class);
    }

}
