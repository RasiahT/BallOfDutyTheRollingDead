/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.health;

import dk.gruppeseks.bodtrd.common.data.World;
import dk.gruppeseks.bodtrd.common.interfaces.IEntityProcessor;
import dk.gruppeseks.bodtrd.common.services.GamePluginSPI;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Thanusaan
 */
@ServiceProvider(service = GamePluginSPI.class)
public class HealthPlugin implements GamePluginSPI
{
    private IEntityProcessor _processor;
    private World _world;

    @Override
    public void start(World world)
    {
        _world = world;
        Installer.Plugin = this;

        _processor = new HealthProcessor();
        world.addProcessor(6, _processor);
    }

    @Override
    public void stop()
    {
        _world.removeProcessor(_processor);
    }
}
