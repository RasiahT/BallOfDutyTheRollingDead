/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.common.data.entityelements;

import dk.gruppeseks.bodtrd.common.data.Path;

/**
 *
 * @author Frederik
 */
public class AIData
{
    private float[] _shape;
    private Path _path;
    private long _lastUpdate;
    private Position _lastKnownPosition;

    private double _rotateSpeed;

    public void setRotateSpeed(double rotateSpeed)
    {
        this._rotateSpeed = rotateSpeed;
    }

    public double getRotateSpeed()
    {
        return this._rotateSpeed;
    }

    public void setFoVShape(float[] shape)
    {
        _shape = shape;
    }

    public float[] getFoVShape()
    {
        return _shape;
    }

    /**
     *
     * Latest known position of the target the AI is tracking.
     */
    public Position getLastKnownPosition()
    {
        return this._lastKnownPosition;
    }

    /**
     *
     *
     * @param pos Latest known position of the target the AI is tracking.
     */
    public void setLastKnownPosition(Position pos)
    {
        this._lastKnownPosition = pos;
    }

    public void setUpdateTime(long time)
    {
        _lastUpdate = time;
    }

    public long getUpdateTime()
    {
        return _lastUpdate;
    }

    public Path getPath()
    {
        return _path;
    }

    public void setPath(Path path)
    {
        _path = path;
    }

}
