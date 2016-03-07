/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppesex.bodtrd.common.data.entityelements;

import java.io.Serializable;

/**
 *
 * @author Morten
 */
public class Position implements Serializable
{
    public double x;
    public double y;

    public Position(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

}
