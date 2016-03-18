/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.common.data.entityelements;

/**
 *
 * @author frede
 */
public class View
{
    private final String _imageFile;

    public View(String path)
    {
        _imageFile = path;
    }

    public String getImageFilePath()
    {
        return _imageFile;
    }
}
