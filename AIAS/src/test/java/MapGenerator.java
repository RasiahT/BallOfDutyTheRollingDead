/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dk.gruppeseks.bodtrd.common.data.Map;
import dk.gruppeseks.bodtrd.common.data.entityelements.Position;
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

    public static Map generateMap(int mapWidth, int mapHeight)
    {
        Map map = new Map(mapWidth, mapHeight, WALL_SIZE, null);

        int wallAmount = WALLS_PER_MEGA_PIXEL * mapHeight * mapWidth / MEGA_PIXEL;

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
                if (checkValidWall(pos, mapWidth, mapHeight) && createWall(pos, map))
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
                if (checkValidWall(pos2, mapWidth, mapHeight) && createWall(pos2, map))
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

    private static boolean createWall(Position pos, Map map)
    {
        int x = (int)(pos.getX() / map.getGridCellSize());
        int y = (int)(pos.getY() / map.getGridCellSize());
        // Return if a wall is already present in the grid.
        if (!map.getGrid()[x][y])
        {
            return false;
        }

        map.getGrid()[x][y] = false;
        return true;
    }

}
