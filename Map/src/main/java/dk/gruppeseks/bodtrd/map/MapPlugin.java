/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.map;

import dk.gruppeseks.bodtrd.common.data.Entity;
import dk.gruppeseks.bodtrd.common.data.Map;
import dk.gruppeseks.bodtrd.common.data.ViewManager;
import dk.gruppeseks.bodtrd.common.data.World;
import dk.gruppeseks.bodtrd.common.services.MapSPI;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author lucas
 */
@ServiceProvider(service = MapSPI.class)
public class MapPlugin implements MapSPI
{
    public final static String WALL_IMAGE_FILE_PATH = "../../../Map/src/main/java/dk/gruppeseks/bodtrd/map/assets/wall_box.png";
    public final static String BORDER_IMAGE_FILE_PATH = "../../../Map/src/main/java/dk/gruppeseks/bodtrd/map/assets/texture_border.png";
    public final static String DIRT_IMAGE_FILE_PATH = "../../../Map/src/main/java/dk/gruppeseks/bodtrd/map/assets/texture_dirt.png";

    private final static int MAP_WIDTH = 2000; // Be divisible by WALL_SIZE
    private final static int MAP_HEIGHT = 1000; // Be divisible by WALL_SIZE

    private World _world;
    private List<Entity> _walls = new ArrayList<>();

    @Override
    public void unloadMap()
    {
        ViewManager.removeView(WALL_IMAGE_FILE_PATH);
        ViewManager.removeView(BORDER_IMAGE_FILE_PATH);
        ViewManager.removeView(DIRT_IMAGE_FILE_PATH);

        for (Entity wall : _walls)
        {
            _world.removeEntity(wall);
        }
    }

    @Override
    public void generateMap(World world)
    {
        this._world = world;

        Installer.Plugin = this;

        ViewManager.createView(WALL_IMAGE_FILE_PATH, false);
        ViewManager.createView(BORDER_IMAGE_FILE_PATH, true);
        ViewManager.createView(DIRT_IMAGE_FILE_PATH, true);

        Map map = MapGenerator.generateMap(_walls, MAP_WIDTH, MAP_HEIGHT);
        for (Entity wall : _walls)
        {
            world.addEntity(wall);
        }

        world.setMap(map);
    }
}
