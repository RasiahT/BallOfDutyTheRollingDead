/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.engine;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.gruppeseks.bodtrd.common.data.ActionHandler;
import dk.gruppeseks.bodtrd.common.data.Entity;
import dk.gruppeseks.bodtrd.common.data.GameData;
import dk.gruppeseks.bodtrd.common.data.ViewManager;
import dk.gruppeseks.bodtrd.common.data.World;
import dk.gruppeseks.bodtrd.common.data.entityelements.Body;
import dk.gruppeseks.bodtrd.common.data.entityelements.Position;
import dk.gruppeseks.bodtrd.common.data.entityelements.View;
import dk.gruppeseks.bodtrd.common.services.GamePluginSPI;
import dk.gruppeseks.bodtrd.managers.GameInputManager;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author lucas
 */
public class Game implements ApplicationListener
{
    private OrthographicCamera _camera;
    private final Lookup _lookup = Lookup.getDefault();
    private World _world;
    private Set<GamePluginSPI> _gamePlugins;
    private SpriteBatch _batch;
    private AssetManager _assetManager;

    @Override
    public void create()
    {
        _batch = new SpriteBatch();
        _assetManager = new AssetManager();

        GameData gameData = new GameData();
        _world = new World(gameData);

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());
        _camera = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());

        Gdx.input.setInputProcessor(new GameInputManager());

        Lookup.Result<GamePluginSPI> result = _lookup.lookupResult(GamePluginSPI.class);
        result.addLookupListener(lookupListener);
        _gamePlugins = ConcurrentHashMap.newKeySet();
        _gamePlugins.addAll(result.allInstances());
        result.allItems();

        for (GamePluginSPI plugin : _gamePlugins)
        {
            plugin.start(_world);
        }

        loadViews();
    }

    private final LookupListener lookupListener = new LookupListener()
    {
        @Override
        public void resultChanged(LookupEvent le)
        {
            Collection<GamePluginSPI> updatedPlugins = (Collection<GamePluginSPI>)_lookup.lookupAll(GamePluginSPI.class);
            for (GamePluginSPI updatedPlugin : updatedPlugins)
            {
                if (!_gamePlugins.contains(updatedPlugin))
                {
                    updatedPlugin.start(_world);
                    _gamePlugins.add(updatedPlugin);
                }
            }

            for (GamePluginSPI oldPlugin : _gamePlugins)
            {
                if (!updatedPlugins.contains(oldPlugin))
                {
                    _gamePlugins.remove(oldPlugin);
                }
            }
            loadViews();
        }
    };

    @Override
    public void resize(int i, int i1)
    {
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _world.getGameData().setDeltaTime(Gdx.graphics.getDeltaTime());

        update();
        draw();
    }

    private void loadViews()
    {
        for (View view : ViewManager.views())
        {
            String imagePath = view.getImageFilePath();

            if (!_assetManager.isLoaded(imagePath, Texture.class))
            {
                _assetManager.load(imagePath, Texture.class);
            }
        }
    }

    private void update()
    {
        ActionHandler.setMousePosition(Gdx.input.getX(), Gdx.input.getY());
        _world.update();
        _assetManager.update();
    }

    private void draw()
    {
        Body pBody = _world.getGameData().getPlayerBody(); // todo preferably get rid of this in someway.
        Position pPosition = _world.getGameData().getPlayerPosition();
        _camera.position.x = (float)(pPosition.getX() + pBody.getWidth() / 2);
        _camera.position.y = (float)(pPosition.getY()) + pBody.getHeight() / 2;

        _camera.update();
        _batch.setProjectionMatrix(_camera.combined);

        _batch.begin();
        for (Entity e : _world.entities())
        {
            View view = e.get(View.class);
            Body body = e.get(Body.class);
            Position pos = e.get(Position.class);

            if (body == null || pos == null || view == null)
            {
                continue;
            }

            if (_assetManager.isLoaded(view.getImageFilePath()))
            {
                _batch.draw(_assetManager.get(view.getImageFilePath(), Texture.class), (float)pos.getX(), (float)pos.getY());
            }
        }
        _batch.end();

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {
    }

    @Override
    public void dispose()
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
