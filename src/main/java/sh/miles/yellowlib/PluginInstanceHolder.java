package sh.miles.yellowlib;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Manages the plugin instance and provides access to the plugin instance given the lib is shaded versus placed as a plugin
 * 
 * Multiple instances of plugins can not be held at the same time.
 * If YellowLib is acting as a plugin its own instance will be used for all plugins relying on the lib
 * If YellowLib is shaded its expected that the plugin will employ the use of PluginInstanceHolder to get the plugin instance
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PluginInstanceHolder {
    
    private static PluginInstanceHolder instance;

    @Getter(value = AccessLevel.PROTECTED)
    private JavaPlugin plugin;

    /**
     * Sets the instance to be held by the PluginInstanceHolder
     * @param plugin the plugin to hold
     * @return
     */
    public static PluginInstanceHolder setInstance(JavaPlugin plugin) {
        if(instance == null) {
            instance = new PluginInstanceHolder(plugin);
        }
        return instance;
    }

    /**
     * Gets the instance held by the PluginInstanceHolder
     * @return
     */
    public static JavaPlugin getInstance() {
        return instance.getPlugin();
    }

    public static Logger getPluginLogger() {
        return instance.getPlugin().getLogger();
    }
}
