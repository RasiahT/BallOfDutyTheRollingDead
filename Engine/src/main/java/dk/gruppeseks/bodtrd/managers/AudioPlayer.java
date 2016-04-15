/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import dk.gruppeseks.bodtrd.common.data.AudioAction;
import dk.gruppeseks.bodtrd.common.data.AudioManager;
import dk.gruppeseks.bodtrd.common.data.AudioTask;
import dk.gruppeseks.bodtrd.common.data.AudioType;

/**
 *
 * @author S
 */
public class AudioPlayer
{
    public void handleAudioTasks(AssetManager am)
    {
        while (AudioManager.hasSoundTasks())
        {
            handleAudioTask(am, AudioManager.pollAudioTask());
        }
    }

    private void handleAudioTask(AssetManager am, AudioTask audioTask)
    {
        AudioAction audioAction = audioTask.getAudioAction();
        if (audioTask.getAudioType() == AudioType.SOUND)
        {

            Sound sound = am.get(audioTask.getFileName(), Sound.class);
            switch (audioAction)
            {
                case PLAY:
                    sound.play();
                    break;
                case RESUME:
                    sound.resume();
                    break;
                case PAUSE:
                    sound.pause();
                    break;
                case STOP:
                    sound.stop();
                    break;
                case LOOP:
                    sound.loop();
                    break;
                case DISPOSE:
                    sound.dispose();
                    break;
                default:
                    break;
            }
        }
        else
        {
            Music music = am.get(audioTask.getFileName(), Music.class);
            switch (audioAction)
            {
                case PLAY:
                    music.play();
                    break;
                case PAUSE:
                    music.pause();
                    break;
                case STOP:
                    music.stop();
                    break;
                default:
                    break;
            }
        }
    }
}
