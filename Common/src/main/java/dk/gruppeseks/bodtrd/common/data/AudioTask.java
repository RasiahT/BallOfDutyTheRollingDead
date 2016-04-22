/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.common.data;

import dk.gruppeseks.bodtrd.common.data.entityelements.Position;

/**
 *
 * @author S
 */
public class AudioTask
{
    private final String _audioFileName;
    private final AudioAction _audioAction;
    private final float _duration;
    private final AudioType _audioType;
    private final Position _position;
    private final int _pulseRadius;

    public AudioTask(String audioFileName, AudioAction audioAction, AudioType audioType, float duration, Position position, int pulseRadius)
    {
        _audioFileName = audioFileName;
        _audioAction = audioAction;
        _audioType = audioType;
        _duration = duration;
        _position = position;
        _pulseRadius = pulseRadius;
    }

    public int getPulseRadius()
    {
        return _pulseRadius;
    }

    public Position getPosition()
    {
        return _position;
    }

    public String getFileName()
    {
        return _audioFileName;
    }

    public AudioAction getAudioAction()
    {
        return _audioAction;
    }

    public float getDuration()
    {
        return _duration;
    }

    public AudioType getAudioType()
    {
        return _audioType;
    }
}
