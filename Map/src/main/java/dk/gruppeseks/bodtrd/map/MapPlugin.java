/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.map;

import dk.gruppeseks.bodtrd.common.data.Entity;
import dk.gruppeseks.bodtrd.common.data.World;
import dk.gruppeseks.bodtrd.common.services.GamePluginSPI;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author lucas
 */
@ServiceProvider(service = GamePluginSPI.class)
public class MapPlugin implements GamePluginSPI
{
    private World _world;
    private List<Entity> _walls = new ArrayList<>();

    @Override
    public void stop()
    {
        for (Entity wall : _walls)
        {
            _world.removeEntity(wall);
        }
    }

    @Override
    public void start(World world)
    {
        Installer.Plugin = this;

        this._world = world;
        _world.getGameData().setMapWidth(4096);
        _world.getGameData().setMapHeight(4096);

        MapGenerator.generateMap(_walls, _world.getGameData());
        for (Entity wall : _walls)
        {
            world.addEntity(wall);
        }
    }
}
