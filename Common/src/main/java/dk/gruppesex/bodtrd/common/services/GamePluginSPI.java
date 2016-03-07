package dk.gruppesex.bodtrd.common.services;

/**
 * @description Defines the required methods for any pluggable game component.
 * @inv The states of a game plugin are loaded, initialized, and unloaded.
 * A game plugin is in the loaded state when it has been located at runtime.
 * A game plugin is in the initialized state when the start() method has been invoked.
 * A game plugin is in the unloaded state when the stop() method has been invoked, it will remain in this state until freed by the GC.
 */
public interface GamePluginSPI
{
    /**
     * @description Should be the first method to be invoked when the component is loaded. It is responsible for initializing the component.
     * @pre The implementation of the interface is loaded.
     * @post The component has been initialized according to its implementation of start().
     */
    public void start();

    /**
     * @description Responsible for unloading the component. This method should be invoked as the last in the components lifetime.
     * @pre The start() method has previously been invoked.
     * @post The component has unloaded its functionality and is no longer active.
     */
    public void stop();
}
