/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.common.data;

/**
 *
 * @author lucas
 */
public class Audio
{
    private final String _filePath;
    private final AudioType _audioType;
    private final String _originalFilePath;

    public Audio(String originalPath, String path, AudioType type)
    {
        _filePath = path;
        _audioType = type;
        _originalFilePath = originalPath;
    }

    public String getFilePath()
    {
        return _filePath;
    }

    public AudioType getType()
    {
        return _audioType;
    }

    public String getOriginalFilePath()
    {
        return _originalFilePath;
    }

}
