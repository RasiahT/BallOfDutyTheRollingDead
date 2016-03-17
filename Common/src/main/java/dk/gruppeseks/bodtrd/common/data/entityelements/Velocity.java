/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.common.data.entityelements;

import dk.gruppeseks.bodtrd.common.data.util.Vector2;

/**
 *
 * @author Morten
 */
public class Velocity
{
    private Vector2 _vector;

    public Velocity(double x, double y)
    {
        _vector = new Vector2(x, y);
    }

    public Velocity(Vector2 vec)
    {
        _vector = vec;
    }

    public Velocity()
    {
        _vector = new Vector2(0, 0);
    }

    public Vector2 getVector()
    {
        return _vector;
    }

    public void setVector(Vector2 vector)
    {
        this._vector = vector;
    }

}
