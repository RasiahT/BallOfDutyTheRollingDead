package dk.gruppeseks.bodtrd.player;

import dk.gruppeseks.bodtrd.common.data.Entity;
import dk.gruppeseks.bodtrd.common.data.EntityType;
import dk.gruppeseks.bodtrd.common.data.GameData;
import dk.gruppeseks.bodtrd.common.data.World;
import dk.gruppeseks.bodtrd.common.data.entityelements.Body;
import dk.gruppeseks.bodtrd.common.data.entityelements.Position;
import dk.gruppeseks.bodtrd.common.data.entityelements.Velocity;
import dk.gruppeseks.bodtrd.common.data.entityelements.View;
import dk.gruppeseks.bodtrd.common.interfaces.IEntityProcessor;
import dk.gruppeseks.bodtrd.common.services.GamePluginSPI;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author lucas
 */
@ServiceProvider(service = GamePluginSPI.class)
public class PlayerPlugin implements GamePluginSPI
{
    private static final String PLAYER_IMAGE_FILE_PATH = "../../../Player/src/main/java/dk/gruppeseks/bodtrd/player/assets/ball_red.png";
    private Entity _entity;
    private IEntityProcessor _processor;
    private GameData _gameData;
    private World _world;

    @Override
    public void start(World world)
    {
        Installer.Plugin = this;

        _world = world;

        _entity = createPlayerEntity();
        world.addEntity(_entity);

        _processor = new PlayerProcessor(_entity);
        _world.addProcessor(1, _processor);

        _world.getGameData().setPlayerPosition(_entity.get(Position.class));
    }

    @Override
    public void stop()
    {
        _world.removeProcessor(_processor);
        _world.removeEntity(_entity);
        _gameData.setPlayerPosition(null);
    }

    private Entity createPlayerEntity()
    {
        Entity entity = new Entity();
        entity.setType(EntityType.PLAYER);

        entity.add(new Position(200, 100));
        entity.add(new Body(50, 50));
        entity.add(new Velocity());
        entity.add(new View(PLAYER_IMAGE_FILE_PATH));

        return entity;
    }
}
