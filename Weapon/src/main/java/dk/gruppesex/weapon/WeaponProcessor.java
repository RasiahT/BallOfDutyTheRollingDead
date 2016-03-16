/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppesex.weapon;

import dk.gruppesex.bodtrd.common.data.Entity;
import static dk.gruppesex.bodtrd.common.data.EntityType.PROJECTILE;
import dk.gruppesex.bodtrd.common.data.GameData;
import dk.gruppesex.bodtrd.common.data.entityelements.Position;
import dk.gruppesex.bodtrd.common.data.entityelements.Velocity;
import dk.gruppesex.bodtrd.common.data.entityelements.Weapon;
import dk.gruppesex.bodtrd.common.data.utill.Vector2;
import dk.gruppesex.bodtrd.common.interfaces.IEntityProcessor;
import java.util.Map;

/**
 *
 * @author Dzenita Hasic
 */
public class WeaponProcessor implements IEntityProcessor
{
    @Override
    public void process(GameData gameData, Map<Integer, Entity> world)
    {
        for (Entity e : world.values())
        {
            Weapon wep = e.get(Weapon.class);

            if (wep == null)
            {
                continue;
            }
            if (wep.getReloadTimeLeft() > 0)
            {
                wep.setReloadTimeLeft((float)(wep.getReloadTimeLeft() - gameData.getDeltaTime()));
                continue;
            }

            handleReloading(wep);

            if (!wep.isAttacking())
            {
                continue;
            }
            if (!(wep.getCurrentMagazineSize() > 0))
            {
                if (!(wep.getCurrentAmmunition() > 0))
                {
                    continue;
                }
                wep.setReloadTimeLeft(wep.getReloadSpeed());
                continue;
            }

            wep.setAttackCooldown((float)(wep.getAttackCooldown() - gameData.getDeltaTime()));

            if (!(wep.getAttackCooldown() > 0))
            {
                continue;
            }

            attack(gameData, world, e, wep);
        }
    }

    private void attack(GameData gameData, Map<Integer, Entity> world, Entity e, Weapon wep)
    {
        Position p = e.get(Position.class);
        Vector2 orientation = wep.getOrientation();//.setMagnitude(e.get(Body.class).getWidth()/2);TODO
        Position position = new Position(p.getX() + orientation.getX(), p.getY() + orientation.getY());
        Velocity velocity = new Velocity(wep.getOrientation());//.setMagnitude(bulletSpeed);TODO: bulletSpeed
        Entity bullet = new Entity();
        bullet.setType(PROJECTILE);
        bullet.add(position);
        bullet.add(velocity);
        wep.setCurrentMagazineSize(wep.getCurrentMagazineSize() - 1);
    }

    private void handleReloading(Weapon wep)
    {
        if (wep.isReloading())
        {
            int currentAmmunition = wep.getCurrentAmmunition();
            int removedAmmunition = Math.max(currentAmmunition, currentAmmunition - wep.getMaxMagazineSize());
            wep.setCurrentAmmunition(currentAmmunition - removedAmmunition);
            wep.setCurrentMagazineSize(removedAmmunition);
            wep.setReloading(false);
        }
    }

}
