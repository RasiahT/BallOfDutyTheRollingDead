/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.weapon;

import dk.gruppeseks.bodtrd.common.data.AudioManager;
import dk.gruppeseks.bodtrd.common.data.AudioType;
import dk.gruppeseks.bodtrd.common.data.Entity;
import static dk.gruppeseks.bodtrd.common.data.EntityType.PLAYER;
import dk.gruppeseks.bodtrd.common.data.ViewManager;
import dk.gruppeseks.bodtrd.common.data.World;
import dk.gruppeseks.bodtrd.common.data.entityelements.Damage;
import dk.gruppeseks.bodtrd.common.data.entityelements.Weapon;
import dk.gruppeseks.bodtrd.common.interfaces.IEntityProcessor;
import dk.gruppeseks.bodtrd.common.services.GamePluginSPI;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Dzenita Hasic
 */
@ServiceProvider(service = GamePluginSPI.class)
public class WeaponPlugin implements GamePluginSPI
{
    private static final String BULLET_IMAGE_FILE_PATH = "assets/ballblack.png";
    private static final String NINE_MM_SOUND_FILE_PATH = "assets/9mm.mp3";
    public static final String RELOAD_SOUND_FILE_PATH = "assets/reload.mp3";
    public static String BULLET_IMAGE_TOTAL_FILE_PATH = "";
    public static String NINE_MM_SOUND_TOTAL_FILE_PATH = "";
    public static String RELOAD_SOUND_TOTAL_FILE_PATH = "";
    private IEntityProcessor _processor;
    private World _world;

    @Override
    public void start(World world)
    {
        Installer.Plugin = this;

        BULLET_IMAGE_TOTAL_FILE_PATH = WeaponPlugin.class.getResource(BULLET_IMAGE_FILE_PATH).getPath().replace("file:", "");
        NINE_MM_SOUND_TOTAL_FILE_PATH = WeaponPlugin.class.getResource(NINE_MM_SOUND_FILE_PATH).getPath().replace("file:", "");
        RELOAD_SOUND_TOTAL_FILE_PATH = WeaponPlugin.class.getResource(RELOAD_SOUND_FILE_PATH).getPath().replace("file:", "");
        ViewManager.createView(BULLET_IMAGE_TOTAL_FILE_PATH, false);
        AudioManager.createSound(NINE_MM_SOUND_TOTAL_FILE_PATH, AudioType.SOUND);
        AudioManager.createSound(RELOAD_SOUND_TOTAL_FILE_PATH, AudioType.SOUND);

        _world = world;

        _processor = new WeaponProcessor();
        _world.addProcessor(2, _processor);
        _world.addEnthusiast(PLAYER, _processor);

        for (Entity e : _world.entities())
        {
            if (e.getType() == PLAYER) // If player gets loaded after weapon, this is bad.
            {
                addBaseWeapon(e);
            }
        }
    }

    public static void addBaseWeapon(Entity e)
    {
        Weapon wep = new Weapon();
        wep.setAttackSpeed(0.4f);
        wep.setAttackDamage(new Damage(10));
        wep.setMaxAmmunition(300);
        wep.setCurrentAmmunition(wep.getMaxAmmunition());
        wep.setMaxMagazineSize(30);
        wep.setCurrentMagazineSize(wep.getMaxMagazineSize());
        wep.setReloadSpeed(2);
        wep.setReloadSound(AudioManager.getAudio(RELOAD_SOUND_TOTAL_FILE_PATH));

        e.add(wep);
    }

    @Override
    public void stop()
    {
        ViewManager.removeView(BULLET_IMAGE_TOTAL_FILE_PATH);

        _world.removeProcessor(_processor);
        _world.removeEnthusiast(PLAYER, _processor);

        for (Entity e : _world.entities())
        {
            if (e.getType() == PLAYER)
            {
                e.remove(Weapon.class);
            }
        }
    }
}
