/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppesex.bodtrd.common.services;

/**
 * @description Defines the methods required for any implementation seeking to process a given set of entities. The process method is
 * invoked as part of the game loop.
 */
public interface EntityProcessorSPI
{

    /**
     * @description Processes a set of entities.
     * @post x amount of entities have been processed.
     */
    public void process();
}
