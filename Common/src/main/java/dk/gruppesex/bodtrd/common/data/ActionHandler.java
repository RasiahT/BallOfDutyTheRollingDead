/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppesex.bodtrd.common.data;

import dk.gruppesex.bodtrd.common.data.entityelements.Position;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Morten
 */
public class ActionHandler
{
    private static Map<Action, Boolean> _actions = new ConcurrentHashMap();

    public static void setActive(Action action, boolean b)
    {
        _actions.put(action, b);
    }

    public static boolean isActive(Action action)
    {
        Boolean val = _actions.get(action);
        return val != null && val;
    }

    public static Position getMousePosition()
    {
        Point point = MouseInfo.getPointerInfo().getLocation();
        return new Position(point.getX(), point.getY());
    }
}
