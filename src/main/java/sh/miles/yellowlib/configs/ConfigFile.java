package sh.miles.yellowlib.configs;

import org.bukkit.configuration.file.FileConfiguration;

import lombok.Getter;

/**
 * A wrapper class for the FileConfiguration that also contains name and path
 */
public class ConfigFile {
    
    private FileConfiguration config;
    @Getter
    private String path;

    public ConfigFile(FileConfiguration config, String path) {
        this.config = config;
        this.path = path;
    }

    public FileConfiguration fetch(){
        return config;
    }

}
