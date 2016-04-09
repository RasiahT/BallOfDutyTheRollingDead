/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.common.data;

import dk.gruppeseks.bodtrd.common.data.entityelements.View;

/**
 *
 * @author lucas
 */
public class Map
{
    private final int _width;
    private final int _height;
    private final boolean[][] _grid; // currently 0 is no wall 1 is wall. Could have been boolean but it's not as open for change.
    private final int _gridCellSize;
    private final View _mapTextureView;

    public Map(int width, int height, int cellSize, View mapTextureView)
    {
        _width = width;
        _height = height;
        _gridCellSize = cellSize;
        _mapTextureView = mapTextureView;
        _grid = new boolean[width / cellSize][height / cellSize];

        for (int i = 0; i < width / cellSize; ++i)
        {
            for (int j = 0; j < height / cellSize; ++j)
            {
                _grid[i][j] = true;
            }
        }
    }

    public int getWidth()
    {
        return _width;
    }

    public int getHeight()
    {
        return _height;
    }

    public boolean[][] getGrid()
    {
        return _grid;
    }

    public int getGridCellSize()
    {
        return _gridCellSize;
    }

    public View getMapTextureView()
    {
        return _mapTextureView;
    }
}
