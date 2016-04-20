/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.common.data;

import dk.gruppeseks.bodtrd.common.data.entityelements.Position;

/**
 *
 * @author frede
 */
public class Path
{

    private Position[] _path;
    private int _nextPosition;
    private long _creationTime;

    public Path(Position[] path)
    {
        _path = path;
        _creationTime = System.currentTimeMillis();
    }

    public Position[] getTotalPath()
    {
        return _path;
    }

    public long getCreationTime()
    {
        return _creationTime;
    }

    public Position peekPosition()
    {
        return _path[_nextPosition];
    }

    public int size()
    {
        return _path.length - _nextPosition;
    }

    public Position popPosition()
    {
        return _path[_nextPosition++];
    }

}
