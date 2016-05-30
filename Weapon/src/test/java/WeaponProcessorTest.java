/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dk.gruppeseks.bodtrd.common.data.AudioManager;
import dk.gruppeseks.bodtrd.common.data.AudioType;
import dk.gruppeseks.bodtrd.common.data.Entity;
import dk.gruppeseks.bodtrd.common.data.EntityType;
import dk.gruppeseks.bodtrd.common.data.GameData;
import dk.gruppeseks.bodtrd.common.data.ViewManager;
import dk.gruppeseks.bodtrd.common.data.World;
import dk.gruppeseks.bodtrd.common.data.entityelements.Body;
import dk.gruppeseks.bodtrd.common.data.entityelements.Damage;
import dk.gruppeseks.bodtrd.common.data.entityelements.Position;
import dk.gruppeseks.bodtrd.common.data.entityelements.Weapon;
import dk.gruppeseks.bodtrd.common.data.util.Vector2;
import dk.gruppeseks.bodtrd.common.interfaces.IEntityProcessor;
import dk.gruppeseks.bodtrd.weapon.WeaponPlugin;
import dk.gruppeseks.bodtrd.weapon.WeaponProcessor;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Morten
 */
public class WeaponProcessorTest
{
    private World _world;
    private GameData _gameData;
    private IEntityProcessor _processor;
    private Entity _player;

    public WeaponProcessorTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
        _processor = new WeaponProcessor();
        _gameData = new GameData();
        _gameData.setDeltaTime(0.03f);

        ViewManager.createView(WeaponPlugin.BULLET_IMAGE_TOTAL_FILE_PATH, false);
        AudioManager.createSound(WeaponPlugin.NINE_MM_SOUND_TOTAL_FILE_PATH, AudioType.SOUND);
        AudioManager.createSound(WeaponPlugin.RELOAD_SOUND_TOTAL_FILE_PATH, AudioType.SOUND);

        _player = new Entity();
        _player.setType(EntityType.PLAYER);

        _world = new World(_gameData);
        _world.addEntity(_player);

        Weapon wep = new Weapon();
        wep.setReloading(false);
        wep.setAttackDamage(new Damage(20));
        wep.setMaxMagazineSize(30);
        wep.setMaxAmmunition(300);
        wep.setCurrentMagazineSize(20);
        wep.setAttackSpeed(20);
        wep.setReloadTimeLeft(0);
        wep.setAttackCooldown(0);
        wep.setAttacking(true);
        wep.setReloadSpeed(2);
        wep.setCurrentAmmunition(wep.getMaxAmmunition());
        wep.setReloadSound(AudioManager.getAudio(WeaponPlugin.RELOAD_SOUND_TOTAL_FILE_PATH));

        _player.add(wep);

    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test that a bullet is present in the world after shooting
     */
    @Test
    public void testPlayerShoot()
    {
        _gameData.setMousePosition(20, 20);

        Position pos = new Position(300, 300);
        Body bod = new Body(50, 50, Body.Geometry.CIRCLE);
        _player.add(pos);
        _player.add(bod);

        _processor.process(_world);

        boolean containsBullet = false;
        for (Entity e : _world.entities())
        {
            if (e.getType() == EntityType.PROJECTILE)
            {
                containsBullet = true;
            }
        }
        assertTrue(containsBullet);
    }

    /**
     * Test that vector between player and bullet is the same as between player
     * and mouse position
     */
    @Test
    public void testAim()
    {
        //Setup
        _gameData.setMousePosition(100, 100);
        Position playerPos = new Position(50, 50);
        Body bod = new Body(50, 50, Body.Geometry.CIRCLE);
        _player.add(playerPos);
        _player.add(bod);

        //Calculate expected vector
        Position mousePos = _gameData.getMousePosition();
        Position playerCenter = new Position(playerPos.getX() + bod.getWidth() / 2, playerPos.getY() + bod.getHeight() / 2);
        Vector2 expectedVec = new Vector2(playerCenter, mousePos).normalize();

        bod.setOrientation(new Vector2(mousePos.getX() - playerCenter.getX(), mousePos.getY() - playerCenter.getY()).setMagnitude(bod.getWidth() / 2));

        _processor.process(_world);
        Entity bullet = null;
        //Find bullet
        for (Entity e : _world.entities())
        {
            if (e.getType() == EntityType.PROJECTILE)
            {
                bullet = e;
            }
        }
        //Calculating actual vector
        Position bulletPos = bullet.get(Position.class);
        Vector2 actualVec = new Vector2(playerCenter, bulletPos).normalize();

        assertTrue(expectedVec.equals(actualVec));
    }
}
