/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.common.data.entityelements.Health;

/**
 *
 * @author Thanusaan
 */
public class DamageInstance
{
    private double _damageAmount;
    private int _ownerId;

    public DamageInstance(double damageAmount, int ownerId)
    {
        this._damageAmount = damageAmount;
        this._ownerId = ownerId;
    }

    public double getDamageAmount()
    {
        return _damageAmount;
    }

    public int getOwnerId()
    {
        return _ownerId;
    }

}
