/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppesex.bodtrd.common.data;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lucas
 */
public class Entity
{
    private Map<Class<?>, Object> data = new HashMap<>();

    public void add(Object data)
    {
        this.data.put(data.getClass(), data);
    }

    public void remove(Class<?> type)
    {
        this.data.remove(type);
    }

    public Object get(Class<?> type)
    {
        return data.get(type);
    }
}
