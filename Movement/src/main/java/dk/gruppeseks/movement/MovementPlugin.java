/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.movement;

import dk.gruppeseks.bodtrd.common.data.World;
import dk.gruppeseks.bodtrd.common.interfaces.IEntityProcessor;
import dk.gruppeseks.bodtrd.common.services.GamePluginSPI;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Morten
 */
@ServiceProvider(service = GamePluginSPI.class)
public class MovementPlugin implements GamePluginSPI
{
    private World _world;
    private IEntityProcessor _processor;

    @Override
    public void start(World world)
    {
        Installer.Plugin = this;

        _world = world;
        _processor = new MovementProcessor();
        _world.addProcessor(2, _processor);
    }

    @Override
    public void stop()
    {
        _world.removeProcessor(_processor);
    }
}
