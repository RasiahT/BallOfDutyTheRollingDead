/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.weapon;

import dk.gruppeseks.bodtrd.common.data.World;
import dk.gruppeseks.bodtrd.common.interfaces.IEntityProcessor;
import dk.gruppeseks.bodtrd.common.services.GamePluginSPI;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Dzenita Hasic
 */
@ServiceProvider(service = GamePluginSPI.class)
public class WeaponPlugin implements GamePluginSPI
{
    private IEntityProcessor _processor;
    private World _world;

    @Override
    public void start(World world)
    {
        Installer.Plugin = this;

        _world = world;

        _processor = new WeaponProcessor();
        _world.addProcessor(3, _processor);
    }

    @Override
    public void stop()
    {
        _world.removeProcessor(_processor);
    }

}
