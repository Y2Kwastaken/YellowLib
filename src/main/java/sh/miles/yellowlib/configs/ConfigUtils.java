package sh.miles.yellowlib.configs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigUtils {
    
    private final JavaPlugin plugin;
    public ConfigUtils(final JavaPlugin plugin){
        this.plugin = plugin;
    }

    public ConfigFile reloadConfig(final String name) {
        final File file = new File(plugin.getDataFolder(), name);
        if(!file.exists()) {
            return new ConfigFile(null, file.getPath());
        }
        return new ConfigFile(YamlConfiguration.loadConfiguration(file), file.getPath());
    }

    /**
     * Creates a directory
     * @param name the name
     */
    public final void createDir(final String name){
        final File dir = new File(plugin.getDataFolder(), name);
        if(!dir.exists()){
            dir.mkdir();
        }
    }

    /**
     * Creates a file
     * @param name the file to create
     */
    public final void createFile(final String name){
        final File file = new File(plugin.getDataFolder(), name);
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets a file
     * @param name The name of the file
     * @return The File
     */
    public final File getFile(final String name){
        return new File(plugin.getDataFolder(), name);
    }

    /**
     * Creates a config file
     * @param path the path of the config file
     * @return the config file
     */
    public ConfigFile createConfig(final String path) {
        final File file = new File(plugin.getDataFolder(), path);
    
        if (!new File(plugin.getDataFolder(), path).exists()) {
    
            plugin.saveResource(path, false);
        }
    
        final ConfigFile configuration = reloadConfig(path);
        if (!file.exists()) {
            try {
                configuration.fetch().save(file);
            }catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return configuration;
    }

    /**
     * Saves the config file
     * @param config the config file to save
     */
    public final void saveConfig(final ConfigFile config){
        try{
            config.fetch().save(new File(plugin.getDataFolder(), config.getPath()));
        }catch(IOException e){
            e.printStackTrace();
        }
    }    

    /**
     * Updates a pre-existing config file with new values overwrites all vaules that are not ignored
     * @param config the config to update
     * @param ignoredSections sections to not update
     */
    public final void update(final ConfigFile config, List<String> ignoredSections){
        try {
            ConfigUpdater.update(plugin, config.getPath(), new File(plugin.getDataFolder(), config.getPath()), ignoredSections);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void update(final ConfigFile config, String... ignoredSections){
        update(config, Arrays.asList(ignoredSections));
    }

    public final void update(final ConfigFile config){
        update(config, new ArrayList<>());
    }

}
