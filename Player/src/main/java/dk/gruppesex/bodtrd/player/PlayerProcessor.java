package dk.gruppesex.bodtrd.player;

import dk.gruppesex.bodtrd.common.data.Entity;
import static dk.gruppesex.bodtrd.common.data.EntityType.PLAYER;
import dk.gruppesex.bodtrd.common.data.GameData;
import dk.gruppesex.bodtrd.common.data.entityelements.Position;
import dk.gruppesex.bodtrd.common.data.entityelements.Velocity;
import dk.gruppesex.bodtrd.common.services.EntityProcessorSPI;
import java.util.Map;

/**
 *
 * @author lucas
 */
public class PlayerProcessor implements EntityProcessorSPI
{
    @Override
    public void process(GameData gameData, Map<Integer, Entity> world, Entity entity)
    {
        if (entity.getType() == PLAYER)
        {
            Position pos = (Position)entity.get(Position.class);
            Velocity vel = (Velocity)entity.get(Velocity.class);

        }
    }

}
