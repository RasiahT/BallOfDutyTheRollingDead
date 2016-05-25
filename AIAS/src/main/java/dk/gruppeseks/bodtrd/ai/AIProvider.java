/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.ai;

import dk.gruppeseks.bodtrd.common.data.Entity;
import dk.gruppeseks.bodtrd.common.data.Path;
import dk.gruppeseks.bodtrd.common.data.World;
import dk.gruppeseks.bodtrd.common.data.entityelements.AIData;
import dk.gruppeseks.bodtrd.common.data.entityelements.Body;
import dk.gruppeseks.bodtrd.common.data.entityelements.Position;
import dk.gruppeseks.bodtrd.common.data.util.Vector2;
import dk.gruppeseks.bodtrd.common.services.AISPI;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author frede
 */
@ServiceProvider(service = AISPI.class)
public class AIProvider implements AISPI
{
    public static World _world;
    private final int TIME_BETWEEN_PATH_UPDATE = 200; // How many milliseconds between path update
    private static final int AGGRO_DISTANCE = 420;

    @Override
    public Path getPath(Entity entity, Entity target, World world)
    {
        _world = world;
        if (Installer.uninstalled)
        {
            return null;
        }
        AIData aiDat = entity.get(AIData.class);
        if (aiDat != null)
        {
            long lastUpdateLong = entity.get(AIData.class).getUpdateTime();
            if ((System.currentTimeMillis() - lastUpdateLong) < TIME_BETWEEN_PATH_UPDATE)
            {
                return aiDat.getPath();
            }
        }

        boolean[][] grid = world.getMap().getGrid();
        int cellSize = world.getMap().getGridCellSize();

        Body zombieBod = entity.get(Body.class);
        Position zombiePos = entity.get(Position.class);
        Position zombieCenter = new Position(zombiePos.getX() + zombieBod.getWidth() / 2, zombiePos.getY() + zombieBod.getHeight() / 2);

        int startX = (int)(zombieCenter.getX() / cellSize);
        int startY = (int)(zombieCenter.getY() / cellSize);

        Body targetBod = target.get(Body.class);
        Position targetPos = target.get(Position.class);
        Position targetCenter = new Position(targetPos.getX() + targetBod.getWidth() / 2, targetPos.getY() + targetBod.getHeight() / 2);

        Vector2 zombieToTarget = new Vector2(zombieCenter, targetCenter);

        int goalX = (int)(targetCenter.getX() / cellSize);
        int goalY = (int)(targetCenter.getY() / cellSize);
        if (zombieToTarget.getMagnitude() > AGGRO_DISTANCE)
        {
            return null;
        }
        if (startX >= grid.length || goalX >= grid.length || startY >= grid[0].length || goalY >= grid[0].length)
        {
            return null;
        }

        if (startX < 0 || goalX < 0 || startY < 0 || goalY < 0)
        {
            return null;
        }

        List<Node> path = AStarSearch(startX, startY, goalX, goalY, grid);
        if (path == null)
        {
            return null;
        }

        Position[] pathArray = new Position[path.size() - 1];
        int outputIncrement = 0;
        for (int i = path.size() - 2; i >= 0; i--)
        {
            pathArray[outputIncrement++] = new Position(path.get(i).getCellX() * cellSize + cellSize / 2,
                    path.get(i).getCellY() * cellSize + cellSize / 2);
        }
        Path output = new Path(pathArray);
        if (aiDat == null)
        {
            aiDat = new AIData();
            entity.add(aiDat);
        }
        aiDat.setPath(output);
        aiDat.setUpdateTime(System.currentTimeMillis());
        return output;
    }

    private Node removeBest(List<Node> fringe, int goalX, int goalY)
    {
        int bestNodeIndex = 0;
        double cost = fringe.get(bestNodeIndex).getTotalCost(goalX, goalY);

        for (int i = 1; i < fringe.size(); ++i)
        {
            double current = fringe.get(i).getTotalCost(goalX, goalY);
            if (current < cost)
            {
                cost = current;
                bestNodeIndex = i;
            }
        }
        Node bestNode = fringe.get(bestNodeIndex);
        fringe.remove(bestNodeIndex);
        return bestNode;
    }

    // TODO move to its own file/class along with its submethods.
    private List<Node> AStarSearch(int startX, int startY, int goalX, int goalY, boolean[][] grid)
    {
        boolean[][] explored = new boolean[grid.length][grid[0].length];
        List<Node> fringe = new ArrayList();
        Node root = new Node(startX, startY, null, 0);

        fringe.add(root);
        explored[root.getCellX()][root.getCellY()] = true;
        while (fringe.size() > 0)
        {
            Node bestNode = removeBest(fringe, goalX, goalY);

            if (bestNode.getCellX() == goalX && bestNode.getCellY() == goalY)
            {
                return bestNode.getPath();
            }
            List<Node> expansion = expand(bestNode, explored, grid);

            for (Node n : expansion)
            {
                explored[n.getCellX()][n.getCellY()] = true;
                fringe.add(n);
            }
        }
        return null;
    }

    private List<Node> expand(Node node, boolean[][] explored, boolean[][] grid)
    {
        int x = node.getCellX();
        int y = node.getCellY();

        int downX = x;
        int downY = y - 1;

        int upX = x;
        int upY = y + 1;

        int rightX = x + 1;
        int rightY = y;

        int leftX = x - 1;
        int leftY = y;

        List<Node> expansion = new ArrayList();

        if (downY >= 0 && grid[downX][downY] && !explored[downX][downY])
        {
            expansion.add(new Node(downX, downY, node, node.getDepth() + 1));
        }
        if (upY < grid[0].length && grid[upX][upY] && !explored[upX][upY])
        {
            expansion.add(new Node(upX, upY, node, node.getDepth() + 1));
        }
        if (rightX < grid.length && grid[rightX][rightY] && !explored[rightX][rightY])
        {
            expansion.add(new Node(rightX, rightY, node, node.getDepth() + 1));
        }
        if (leftX >= 0 && grid[leftX][leftY] && !explored[leftX][leftY])
        {
            expansion.add(new Node(leftX, leftY, node, node.getDepth() + 1));
        }

        return expansion;
    }
}
