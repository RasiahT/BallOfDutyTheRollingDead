/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.weapon;

import dk.gruppeseks.bodtrd.common.data.Entity;
import static dk.gruppeseks.bodtrd.common.data.EntityType.PROJECTILE;
import dk.gruppeseks.bodtrd.common.data.World;
import dk.gruppeseks.bodtrd.common.data.entityelements.Body;
import dk.gruppeseks.bodtrd.common.data.entityelements.Position;
import dk.gruppeseks.bodtrd.common.data.entityelements.Velocity;
import dk.gruppeseks.bodtrd.common.data.entityelements.View;
import dk.gruppeseks.bodtrd.common.data.entityelements.Weapon;
import dk.gruppeseks.bodtrd.common.data.util.Vector2;
import dk.gruppeseks.bodtrd.common.interfaces.IEntityProcessor;

/**
 *
 * @author Dzenita Hasic
 */
public class WeaponProcessor implements IEntityProcessor
{
    private static final String BULLET_IMAGE_FILE_PATH = "../../../Player/src/main/java/dk/gruppeseks/bodtrd/weapon/assets/blackball.png";
    private static View _view = new View(BULLET_IMAGE_FILE_PATH);

    @Override
    public void process(World world)
    {
        for (Entity e : world.entities())
        {
            Weapon wep = e.get(Weapon.class);

            if (wep == null)
            {
                continue;
            }
            if (wep.getReloadTimeLeft() > 0)
            {
                wep.setReloadTimeLeft((float)(wep.getReloadTimeLeft() - world.getGameData().getDeltaTime()));
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

            wep.setAttackCooldown((float)(wep.getAttackCooldown() - world.getGameData().getDeltaTime()));

            if (!(wep.getAttackCooldown() > 0))
            {
                continue;
            }

            attack(world, e, wep);
        }
    }

    private void attack(World world, Entity e, Weapon wep)
    {
        Position p = e.get(Position.class);
        Vector2 orientation = wep.getOrientation();//.setMagnitude(e.get(Body.class).getWidth()/2);TODO
        Position position = new Position(p.getX() + orientation.getX(), p.getY() + orientation.getY());
        Velocity velocity = new Velocity(wep.getOrientation());//.setMagnitude(bulletSpeed);TODO: bulletSpeed

        Entity bullet = new Entity();
        bullet.setType(PROJECTILE);
        bullet.add(position);
        bullet.add(velocity);
        bullet.add(new Body(20, 20));
        bullet.add(_view);
        world.addEntity(bullet);

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

    @Override
    public void notifyEntitiesAdded(Entity entity)
    {
        Weapon wep = new Weapon();
        wep.setAttackSpeed(400);
        wep.setMaxAmmunition(300);
        wep.setCurrentAmmunition(wep.getMaxAmmunition());
        wep.setMaxMagazineSize(30);
        wep.setCurrentMagazineSize(wep.getMaxMagazineSize());
        wep.setReloadSpeed(1000);

        entity.add(wep);
    }

    @Override
    public void notifyEntitiesRemoved(Entity entity)
    {
    }

}
