package dk.gruppesex.bodtrd.player;

import dk.gruppesex.bodtrd.common.data.Entity;
import dk.gruppesex.bodtrd.common.data.GameData;
import dk.gruppesex.bodtrd.common.data.entityelements.Position;
import dk.gruppesex.bodtrd.common.data.entityelements.Velocity;
import dk.gruppesex.bodtrd.common.interfaces.IEntityProcessor;
import dk.gruppesex.bodtrd.common.services.GamePluginSPI;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lucas
 */
public class PlayerPlugin implements GamePluginSPI
{
    private Entity _entity;
    private IEntityProcessor _processor;
    private GameData _gameData;
    private Map<Integer, Entity> _world;
    private List<IEntityProcessor> _processors;

    @Override
    public void start(GameData gameData, Map<Integer, Entity> world, List<IEntityProcessor> processors)
    {
        Installer.Plugin = this;
        _gameData = gameData;
        _world = world;
        _processors = processors;

        _entity = new Entity();
        _entity.add(new Position(0, 0));
        _entity.add(new Velocity());
        world.put(_entity.getID(), _entity);

        _processor = new PlayerProcessor(_entity);
        processors.add(_processor);
    }

    @Override
    public void stop()
    {
        _processors.remove(_processor);
        _world.remove(_entity.getID());
    }

}
