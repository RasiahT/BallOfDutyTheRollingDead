/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.common.data;

import dk.gruppeseks.bodtrd.common.interfaces.IEntityProcessor;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author lucas
 */
public class World
{
    private final Map<Integer, Entity> _entities = new ConcurrentHashMap<>();
    private final Map<Integer, Set<IEntityProcessor>> _processors = new ConcurrentHashMap<>();
    private final Map<EntityType, Set<IEntityProcessor>> _enthusiasts = new ConcurrentHashMap<>();
    private final GameData _gameData;

    public World(GameData gameData)
    {
        _gameData = gameData;

        for (EntityType e : EntityType.values())
        {
            Set<IEntityProcessor> enthusiasts = ConcurrentHashMap.newKeySet();
            _enthusiasts.put(e, enthusiasts);
        }
    }

    public GameData getGameData()
    {
        return _gameData;
    }

    public Collection<Entity> entities()
    {
        return _entities.values();
    }

    public void update()
    {
        for (Integer priority : _processors.keySet())
        {
            _processors.get(priority).parallelStream().forEach((processor) ->
                    {
                        processor.process(this);
                    });
            /*for (IEntityProcessor processor : _processors.get(priority))
            {
                processor.process(this);
            }*/
        }
    }

    public void addProcessor(int priority, IEntityProcessor processor)
    {
        Set<IEntityProcessor> processorSet = _processors.get(priority);
        if (processorSet == null)
        {
            processorSet = ConcurrentHashMap.newKeySet();
            _processors.put(priority, processorSet);
        }

        processorSet.add(processor);
    }

    public void removeProcessor(IEntityProcessor processor)
    {
        for (Set<IEntityProcessor> processorSet : _processors.values())
        {
            processorSet.remove(processor);
        }
    }

    public void addEnthusiast(EntityType entityType, IEntityProcessor processor)
    {
        _enthusiasts.get(entityType).add(processor);
    }

    public void removeEnthusiast(EntityType entityType, IEntityProcessor processor)
    {
        _enthusiasts.get(entityType).remove(processor);
    }

    public void addEntity(Entity e)
    {
        _entities.put(e.getID(), e);

        for (IEntityProcessor processor : _enthusiasts.get(e.getType()))
        {
            processor.notifyEntitiesAdded(e);
        }
    }

    public void removeEntity(Entity e)
    {
        _entities.remove(e.getID());

        for (IEntityProcessor processor : _enthusiasts.get(e.getType()))
        {
            processor.notifyEntitiesRemoved(e);
        }
    }
}
