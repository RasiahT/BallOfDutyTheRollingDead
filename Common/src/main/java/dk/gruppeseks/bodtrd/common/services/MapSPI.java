/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.common.services;

import dk.gruppeseks.bodtrd.common.data.World;

/**
 *
 * @author lucas
 */
public interface MapSPI
{
    public void generateMap(World world);

    public void unloadMap();
}
