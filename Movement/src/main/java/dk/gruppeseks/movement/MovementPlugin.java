/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.movement;

import dk.gruppeseks.bodtrd.common.data.Entity;
import dk.gruppeseks.bodtrd.common.data.GameData;
import dk.gruppeseks.bodtrd.common.interfaces.IEntityProcessor;
import dk.gruppeseks.bodtrd.common.services.GamePluginSPI;
import java.util.List;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Morten
 */
@ServiceProvider(service = GamePluginSPI.class)
public class MovementPlugin implements GamePluginSPI
{
    private List<IEntityProcessor> _processors;
    private IEntityProcessor _processor;

    @Override
    public void start(GameData gameData, Map<Integer, Entity> world, List<IEntityProcessor> processors)
    {
        Installer.Plugin = this;

        _processors = processors;
        _processor = new MovementProcessor();
        _processors.add(_processor);
    }

    @Override
    public void stop()
    {
        _processors.remove(_processor);
    }
}
