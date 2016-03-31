/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.common.data.entityelements.Health;

import java.util.ArrayList;

/**
 *
 * @author Thanusaan
 */
public class Health
{
    private double _maxHp;
    private double _hp;
    private double _hpRegenPrSec;
    private ArrayList<DamageInstance> _damageInstances;

    public Health(double maxHp, double hpRegen)
    {
        this._maxHp = maxHp;
        this._hp = maxHp;
        this._hpRegenPrSec = hpRegen;
        _damageInstances = new ArrayList<>();
    }

    public double getHp()
    {
        return _hp;
    }

    public void setHp(double hp)
    {
        this._hp = hp;
    }

    public double getHpRegen()
    {
        return _hpRegenPrSec;
    }

    public void setHpRegen(double hpRegen)
    {
        this._hpRegenPrSec = hpRegen;
    }

    public double getMaxHp()
    {
        return _maxHp;
    }

    public void setMaxHp(double maxHp)
    {
        this._maxHp = maxHp;
    }

    public ArrayList<DamageInstance> getDamageInstances()
    {
        return _damageInstances;
    }

    public void addDamageInstance(DamageInstance di)
    {
        _damageInstances.add(di);
    }

}
