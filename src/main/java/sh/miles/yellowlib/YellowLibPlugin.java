package sh.miles.yellowlib;

import org.bukkit.plugin.java.JavaPlugin;

public class YellowLibPlugin extends JavaPlugin {
    
    @Override
    public void onEnable(){
        getLogger().info("YellowLib Loaded");
        PluginInstanceHolder.setInstance(this);
    }

    @Override
    public void onDisable(){
        getLogger().info("YellowLib Unloaded");
    }
    
}
