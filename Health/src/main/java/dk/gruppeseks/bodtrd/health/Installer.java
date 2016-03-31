package dk.gruppeseks.bodtrd.health;

import dk.gruppeseks.bodtrd.common.services.GamePluginSPI;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall
{
    public static GamePluginSPI Plugin = null;

    @Override
    public void restored()
    {
        // TODO
    }

    @Override
    public void uninstalled()
    {
        if (Plugin != null)
        {
            Plugin.stop();
        }
    }

}
