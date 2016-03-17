/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.common.interfaces;

import dk.gruppeseks.bodtrd.common.data.Entity;
import dk.gruppeseks.bodtrd.common.data.GameData;
import java.util.Map;

/**
 * @description Defines the methods required for any implementation seeking to process a given set of entities. The process method is
 * invoked as part of the game loop.
 */
public interface IEntityProcessor
{

    /**
     * @param gameData Data about the game not related to entities.
     * @param world All entities in the game.
     * @description Processes a set of entities.
     * @post x amount of entities have been processed.
     */
    public void process(GameData gameData, Map<Integer, Entity> world);
}
