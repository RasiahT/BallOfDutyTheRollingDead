import dk.gruppeseks.bodtrd.common.exceptions.NoPathException;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Frederik
 */
public class AIStarPerfTest
{
    private static boolean[][] _grid;
    private static int count = 0;
    private static int successes = 0;
    public static double WEIGHT = 1;

    public static void main(String[] args)
    {
        MapGenerator gen = new MapGenerator();
        for (WEIGHT = 0.0; WEIGHT < 30; WEIGHT += 0.5)
        {
            successes = 0;
            count = 0;
            for (int i = 0; i < 1000; i++)
            {
                _grid = MapGenerator.generateMap(12800, 7200).getGrid();
                int startX = (int)(Math.random() * _grid.length);
                int startY = (int)(Math.random() * _grid[0].length);
                int goalX = (int)(Math.random() * _grid.length);
                int goalY = (int)(Math.random() * _grid[0].length);
                try
                {
                    AStarSearch(startX, startY, goalX, goalY, new boolean[_grid.length][_grid[0].length]);
                    successes++;
                }
                catch (NoPathException e)
                {

                }
            }
            System.out.println("Average steps with a weight of " + WEIGHT + ": " + count / successes
                    + " grid: " + _grid.length + ", " + _grid[0].length);
        }

    }

    private static List<Node> AStarSearch(int startX, int startY, int goalX, int goalY, boolean[][] explored) throws NoPathException
    {
        List<Node> fringe = new ArrayList();
        Node root = new Node(startX, startY, null, 0);

        fringe.add(root);
        explored[root.getCellX()][root.getCellY()] = true;
        while (fringe.size() > 0)
        {
            count++;
            Node bestNode = removeBest(fringe, goalX, goalY);

            if (bestNode.getCellX() == goalX && bestNode.getCellY() == goalY)
            {
                return bestNode.getPath();
            }
            List<Node> expansion = expand(bestNode, explored);

            for (Node n : expansion)
            {
                explored[n.getCellX()][n.getCellY()] = true;
                fringe.add(n);
            }
        }
        throw new NoPathException("No path towards the given point");
    }

    private static Node removeBest(List<Node> fringe, int goalX, int goalY)
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

    private static List<Node> expand(Node node, boolean[][] explored)
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

        if (downY >= 0 && _grid[downX][downY] && !explored[downX][downY])
        {
            expansion.add(new Node(downX, downY, node, node.getDepth() + 1));
        }
        if (upY < _grid[0].length && _grid[upX][upY] && !explored[upX][upY])
        {
            expansion.add(new Node(upX, upY, node, node.getDepth() + 1));
        }
        if (rightX < _grid.length && _grid[rightX][rightY] && !explored[rightX][rightY])
        {
            expansion.add(new Node(rightX, rightY, node, node.getDepth() + 1));
        }
        if (leftX >= 0 && _grid[leftX][leftY] && !explored[leftX][leftY])
        {
            expansion.add(new Node(leftX, leftY, node, node.getDepth() + 1));
        }

        return expansion;
    }
}
