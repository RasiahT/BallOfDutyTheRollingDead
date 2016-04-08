/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.ai;

import dk.gruppeseks.bodtrd.common.data.World;
import dk.gruppeseks.bodtrd.common.data.entityelements.Position;
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
    private boolean[][] _grid;

    @Override
    public Position[] getPath(Position start, Position goal, World world)
    {
        //The following is dummy-code, untill the map is fixed
        boolean[][] testGrid =
        {
            {
                true, true, true, true
            },
            {
                true, false, true, false
            },
            {
                true, false, true, true
            },
            {
                true, true, false, true
            }
        };
        //End of dummy-code

        _grid = world.getMap().getGrid();
        int cellSize = world.getMap().getGridCellSize();

        int startX = (int)(start.getX() / cellSize);
        int startY = (int)(start.getY() / cellSize);

        int goalX = (int)(goal.getX() / cellSize);
        int goalY = (int)(goal.getY() / cellSize);

        List<Node> path = AStarSearch(startX, startY, goalX, goalY);
        Position[] output = new Position[path.size()];

        for (int i = 0; i < path.size(); i++)
        {
            output[output.length - 1 - i] = new Position(path.get(i).getCellX() * cellSize + cellSize / 2,
                    path.get(i).getCellY() * cellSize + cellSize / 2);
        }

        return output;
    }

    private Node removeBest(List<Node> fringe, int goalX, int goalY)
    {
        int bestNodeIndex = 0;
        double cost = fringe.get(bestNodeIndex).heuristic(goalX, goalY);

        for (int i = 1; i < fringe.size(); ++i)
        {
            double current = fringe.get(i).heuristic(goalX, goalY);
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
    private List<Node> AStarSearch(int startX, int startY, int goalX, int goalY)
    {
        List<Node> fringe = new ArrayList();
        Node root = new Node(startX, startY, null, 0);

        fringe.add(root);
        while (fringe.size() > 0)
        {
            Node bestNode = removeBest(fringe, goalX, goalY);

            if (bestNode.getCellX() == goalX && bestNode.getCellY() == goalY)
            {
                return bestNode.getPath();
            }
            fringe.addAll(expand(bestNode));
        }
        return null; // TODO perhaps throw an exception or something third
    }

    private List<Node> expand(Node node)
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

        if (downY >= 0 && _grid[downX][downY])
        {
            expansion.add(new Node(downX, downY, node, node.getDepth() + 1));
        }
        if (upY < _grid[0].length && _grid[upX][upY])
        {
            expansion.add(new Node(upX, upY, node, node.getDepth() + 1));
        }
        if (rightX < _grid.length && _grid[rightX][rightY])
        {
            expansion.add(new Node(rightX, rightY, node, node.getDepth() + 1));
        }
        if (leftX >= 0 && _grid[leftX][leftY])
        {
            expansion.add(new Node(leftX, leftY, node, node.getDepth() + 1));
        }

        return expansion;
    }
}
