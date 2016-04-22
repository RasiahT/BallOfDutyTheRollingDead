/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.common.data;

import dk.gruppeseks.bodtrd.common.data.entityelements.Position;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author S
 */
public class AudioManager
{
    private static final Queue<AudioTask> _audioTasks = new ConcurrentLinkedQueue<>();
    private static java.util.Map<String, Audio> _sounds = new ConcurrentHashMap<>();

    public static boolean hasSoundTasks()
    {
        return !_audioTasks.isEmpty();
    }

    public static Collection<AudioTask> audioTasks()
    {
        return _audioTasks;
    }

    public static Collection<Audio> audios()
    {
        return _sounds.values();
    }

    public static AudioTask pollAudioTask()
    {
        return _audioTasks.poll();
    }

    public static void playSound(String path, AudioAction audioAction, Position position, int pulseRadius)
    {
        playSound(path, audioAction, 0, position, pulseRadius);
    }

    public static void playSound(String path, AudioAction audioAction)
    {
        playSound(path, audioAction, 0, null, 0);
    }

    public static void playSound(String path, AudioAction audioAction, float duration)
    {
        playSound(path, audioAction, duration, null, 0);
    }

    public static void playSound(String path, AudioAction audioAction, float duration, Position position, int pulseRadius)
    {
        Audio a = _sounds.get(path);
        _audioTasks.add(new AudioTask(a.getFilePath(), audioAction, a.getType(), duration, position, pulseRadius));
    }

    public static void createSound(String path, AudioType type)
    {
        try
        {
            _sounds.put(path, new Audio(path, new File(path).getCanonicalPath().replace("\\", "/"), type));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    public static Audio getAudio(String path)
    {
        return _sounds.get(path);
    }
}
