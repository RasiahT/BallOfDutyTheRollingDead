/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.common.data.entityelements;

import dk.gruppeseks.bodtrd.common.data.Path;

/**
 *
 * @author Frederik
 */
public class AIData
{
    private float[] _shape;
    private Path _path;

    public void setFoVShape(float[] shape)
    {
        _shape = shape;
    }

    public float[] getFoVShape()
    {
        return _shape;
    }

    public Path getPath()
    {
        return _path;
    }

    public void setPath(Position[] path)
    {
        _path = new Path(path);
    }

}
