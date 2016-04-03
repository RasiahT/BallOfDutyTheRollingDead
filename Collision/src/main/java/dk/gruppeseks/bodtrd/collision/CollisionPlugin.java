/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.collision;

import dk.gruppeseks.bodtrd.common.data.Entity;
import dk.gruppeseks.bodtrd.common.data.EntityType;
import dk.gruppeseks.bodtrd.common.data.World;
import dk.gruppeseks.bodtrd.common.interfaces.IEntityProcessor;
import dk.gruppeseks.bodtrd.common.services.GamePluginSPI;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Chris
 */
@ServiceProvider(service = GamePluginSPI.class)
public class CollisionPlugin implements GamePluginSPI
{
    private IEntityProcessor _processor;
    private World _world;

    @Override
    public void start(World world)
    {
        _world = world;
        Installer.Plugin = this;

        _processor = new CollisionProcessor();
        world.addProcessor(5, _processor);
        world.addEnthusiast(EntityType.PROJECTILE, _processor);
        world.addEnthusiast(EntityType.PLAYER, _processor);
        world.addEnthusiast(EntityType.WALL, _processor);
        world.addEnthusiast(EntityType.ENEMY, _processor);

        for (Entity e : _world.entities())
        {
            CollisionHandler.addCollisionData(e);
        }
    }

    @Override
    public void stop()
    {
        _world.removeProcessor(_processor);
        _world.removeEnthusiast(EntityType.PROJECTILE, _processor);
        _world.removeEnthusiast(EntityType.PLAYER, _processor);
        _world.removeEnthusiast(EntityType.WALL, _processor);
        _world.removeEnthusiast(EntityType.ENEMY, _processor);
        for (Entity e : _world.entities())
        {
            e.remove(CollisionData.class);
        }
    }
}
