/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.map;

import dk.gruppeseks.bodtrd.common.data.Entity;
import dk.gruppeseks.bodtrd.common.data.EntityType;
import dk.gruppeseks.bodtrd.common.data.Map;
import dk.gruppeseks.bodtrd.common.data.ViewManager;
import dk.gruppeseks.bodtrd.common.data.entityelements.Body;
import dk.gruppeseks.bodtrd.common.data.entityelements.Body.Geometry;
import dk.gruppeseks.bodtrd.common.data.entityelements.Position;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Thanusaan
 */
public class MapGenerator
{
    private static Random _rand = new Random(); // TODO needs to be able to pick seed

    private static int _pathUp = 20;
    private static int _pathRight = 40;
    private static int _pathDown = 60;
    private static int _pathLeft = 80;

    private final static int WALLS_PER_MEGA_PIXEL = 70;
    private final static int MEGA_PIXEL = 1000 * 1000;
    private final static int WALL_SIZE = 50; // Be divisible with MAP_WIDTH and MAP_HEIGHT

    public static Map generateMap(List<Entity> walls, int mapWidth, int mapHeight)
    {
        Map map = new Map(mapWidth, mapHeight, WALL_SIZE, ViewManager.getView(MapPlugin.DIRT_IMAGE_FILE_PATH));

        int wallAmount = WALLS_PER_MEGA_PIXEL * mapHeight * mapWidth / MEGA_PIXEL;
        createBorderWalls(walls, mapWidth, mapHeight);

        int mapGridX = map.getWidth() / map.getGridCellSize();
        int mapGridY = map.getHeight() / map.getGridCellSize();

        Position pos = new Position(0, 0);

        int wallCount = 0;

        while (true)
        {
            while (true)
            {
                pos.setX((_rand.nextInt(mapGridX)) * WALL_SIZE);
                pos.setY((_rand.nextInt(mapGridY)) * WALL_SIZE);
                if (checkValidWall(pos, mapWidth, mapHeight) && createWall(pos, walls, map))
                {
                    ++wallCount;
                    break;
                }
            }
            while (true)
            {
                Position pos2 = new Position(pos.getX(), pos.getY());
                int r = _rand.nextInt(100);

                if (r < _pathUp)
                {
                    pos2.setY(pos.getY() - WALL_SIZE);
                }
                else if (r < _pathRight)
                {
                    pos2.setX(pos.getX() + WALL_SIZE);
                }
                else if (r < _pathDown)
                {
                    pos2.setY(pos.getY() + WALL_SIZE);
                }
                else if (r < _pathLeft)
                {
                    pos2.setX(pos.getX() - WALL_SIZE);
                }
                else
                {
                    break;
                }
                if (checkValidWall(pos2, mapWidth, mapHeight) && createWall(pos2, walls, map))
                {
                    pos = pos2;
                    ++wallCount;
                }
                if (wallCount >= wallAmount)
                {
                    break;
                }
            }
            if (wallCount >= wallAmount)
            {
                break;
            }
        }

        return map;
    }

    private static boolean checkValidWall(Position newWallPos, int mapWidth, int mapHeight)
    {
        return (newWallPos.getX() > WALL_SIZE && newWallPos.getY() > WALL_SIZE && newWallPos.getX() < mapWidth - WALL_SIZE && newWallPos.getY() < mapHeight - WALL_SIZE);
    }

    private static boolean createWall(Position pos, List<Entity> walls, Map map)
    {
        int x = (int)Math.floor(pos.getX() / map.getGridCellSize());
        int y = (int)Math.floor(pos.getY() / map.getGridCellSize());

        System.out.println(x + " " + y);
        // Return if a wall is already present in the grid.
        if (!map.getGrid()[x][y])
        {
            return false;
        }

        // Indicate that a wall is now present in this cell.
        map.getGrid()[x][y] = false;

        Entity wall = new Entity();
        wall.setType(EntityType.WALL);
        wall.add(new Position(pos.getX(), pos.getY()));
        wall.add(new Body(WALL_SIZE, WALL_SIZE, Geometry.RECTANGLE));
        wall.add(ViewManager.getView(MapPlugin.WALL_IMAGE_FILE_PATH));

        walls.add(wall);
        return true;
    }

    private static void createBorderWalls(List<Entity> walls, int mapWidth, int mapHeight)
    {
        Entity wallBot = new Entity();
        wallBot.setType(EntityType.WALL);
        wallBot.add(new Position(0, 0));
        wallBot.add(new Body(WALL_SIZE, mapWidth, Geometry.RECTANGLE));
        wallBot.add(ViewManager.getView(MapPlugin.BORDER_IMAGE_FILE_PATH));

        Entity wallTop = new Entity();
        wallTop.setType(EntityType.WALL);
        wallTop.add(new Position(0, mapHeight - WALL_SIZE));
        wallTop.add(new Body(WALL_SIZE, mapWidth, Geometry.RECTANGLE));
        wallTop.add(ViewManager.getView(MapPlugin.BORDER_IMAGE_FILE_PATH));

        Entity wallLeft = new Entity();
        wallLeft.setType(EntityType.WALL);
        wallLeft.add(new Position(0, WALL_SIZE));
        wallLeft.add(new Body(mapHeight - WALL_SIZE * 2, WALL_SIZE, Geometry.RECTANGLE));
        wallLeft.add(ViewManager.getView(MapPlugin.BORDER_IMAGE_FILE_PATH));

        Entity wallRight = new Entity();
        wallRight.setType(EntityType.WALL);
        wallRight.add(new Position(mapWidth - WALL_SIZE, WALL_SIZE));
        wallRight.add(new Body(mapHeight - WALL_SIZE * 2, WALL_SIZE, Geometry.RECTANGLE));
        wallRight.add(ViewManager.getView(MapPlugin.BORDER_IMAGE_FILE_PATH));

        walls.add(wallBot);
        walls.add(wallTop);
        walls.add(wallLeft);
        walls.add(wallRight);
    }
}
