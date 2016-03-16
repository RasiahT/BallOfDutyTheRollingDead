/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppesex.weapon;

import dk.gruppesex.bodtrd.common.data.Entity;
import dk.gruppesex.bodtrd.common.data.GameData;
import dk.gruppesex.bodtrd.common.interfaces.IEntityProcessor;
import dk.gruppesex.bodtrd.common.services.GamePluginSPI;
import java.util.List;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Dzenita Hasic
 */
@ServiceProvider(service = GamePluginSPI.class)
public class WeaponPlugin implements GamePluginSPI
{
    private IEntityProcessor _processor;
    private GameData _gameData;
    private Map<Integer, Entity> _world;
    private List<IEntityProcessor> _processors;

    @Override
    public void start(GameData gameData, Map<Integer, Entity> world, List<IEntityProcessor> processors)
    {
        Installer.Plugin = this;

        _world = world;

        _processors = processors;
        _processor = new WeaponProcessor();
        processors.add(_processor);

        _gameData = gameData;
    }

    @Override
    public void stop()
    {
        _processors.remove(_processor);
    }

}
