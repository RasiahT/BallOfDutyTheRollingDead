/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.collision;

/**
 *
 * @author lucas
 */
public class CollisionData
{
    private int _collisionFlag = CollisionFlags.NONE;

    public int getCollisionFlag()
    {
        return _collisionFlag;
    }

    public void setCollisionFlag(int collisionFlag)
    {
        this._collisionFlag = collisionFlag;
    }
}
