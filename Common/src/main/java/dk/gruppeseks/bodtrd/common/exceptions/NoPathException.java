/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.common.exceptions;

/**
 *
 * @author Frederik
 */
public class NoPathException extends Exception
{

    public NoPathException()
    {
        super();
    }

    public NoPathException(String message)
    {
        super(message);
    }

    public NoPathException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public NoPathException(Throwable cause)
    {
        super(cause);
    }
}
