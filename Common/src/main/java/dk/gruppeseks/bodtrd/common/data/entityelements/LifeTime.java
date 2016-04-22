/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.common.data.entityelements;

/**
 *
 * @author Thanusaan
 */
public class LifeTime
{
    private float _lifeTime;

    public LifeTime(float lifeTime)
    {
        _lifeTime = lifeTime;
    }

    public float getLifeTime()
    {
        return _lifeTime;
    }

    public void setLifeTime(float lifeTime)
    {
        this._lifeTime = lifeTime;
    }
}
