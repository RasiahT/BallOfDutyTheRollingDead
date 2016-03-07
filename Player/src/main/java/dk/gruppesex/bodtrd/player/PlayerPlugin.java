package dk.gruppesex.bodtrd.player;

import dk.gruppesex.bodtrd.common.data.Entity;
import dk.gruppesex.bodtrd.common.data.GameData;
import dk.gruppesex.bodtrd.common.services.GamePluginSPI;
import java.util.Map;

/**
 *
 * @author lucas
 */
public class PlayerPlugin implements GamePluginSPI
{
    private Entity _entity;

    @Override
    public void start(GameData gameData, Map<Integer, Entity> world)
    {
        _entity = new Entity();
        world.put(_entity.getID(), _entity);

    }

    @Override
    public void stop(GameData gameData, Map<Integer, Entity> world)
    {
        world.remove(_entity.getID());
    }

}
