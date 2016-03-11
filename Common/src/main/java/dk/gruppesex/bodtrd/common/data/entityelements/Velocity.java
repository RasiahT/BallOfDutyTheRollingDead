/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppesex.bodtrd.common.data.entityelements;

/**
 *
 * @author Morten
 */
public class Velocity
{
    private double _dx;
    private double _dy;

    public Velocity(double dx, double dy)
    {
        this._dx = dx;
        this._dy = dy;
    }

    public Velocity()
    {
    }

    public double getDx()
    {
        return _dx;
    }

    public void setDx(double dx)
    {
        this._dx = dx;
    }

    public double getDy()
    {
        return _dy;
    }

    public void setDy(double dy)
    {
        this._dy = dy;
    }

}
