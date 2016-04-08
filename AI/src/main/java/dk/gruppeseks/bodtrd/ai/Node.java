/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.ai;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dzenita Hasic
 */
public class Node
{
    private int _gridX;
    private int _gridY;
    private Node _parent;
    private int _depth;

    public int getCellX()
    {
        return _gridX;
    }

    public int getCellY()
    {
        return _gridY;
    }

    public int getDepth()
    {
        return _depth;
    }

    public Node(int gridX, int gridY, Node parent, int depth)
    {
        this._gridX = gridX;
        this._gridY = gridY;
        this._parent = parent;
        this._depth = depth;
    }

    public double heuristic(int goalX, int goalY)
    {
        double a = _gridX - goalX;
        double b = _gridY - goalY;
        double c = Math.sqrt(a * a + b * b);
        return _depth + c;
    }

    public List<Node> getPath()
    {
        List<Node> output = new ArrayList<>();
        output.add(this);

        Node x = this;
        while (x._parent != null)
        {
            output.add(x._parent);
            x = x._parent;
        }

        return output;
    }
}
