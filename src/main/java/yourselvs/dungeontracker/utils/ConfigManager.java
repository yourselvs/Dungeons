package yourselvs.dungeontracker.utils;

//Java Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import yourselvs.dungeontracker.Dungeons;

/**
* A class that handles the configuration file.
* 
* @author iffa
* @author Pandarr
* @author Sammy
* @author kitskub
* @author yourselvs
*/
public class ConfigManager {
 // Variables
 private Map<ConfigFile, YamlConfiguration> config = new EnumMap<ConfigFile, YamlConfiguration>(ConfigFile.class);
 private Map<ConfigFile, File> configFile = new EnumMap<ConfigFile, File>(ConfigFile.class);
 private Map<ConfigFile, Boolean> loaded = new EnumMap<ConfigFile, Boolean>(ConfigFile.class);
 private Dungeons plugin;

 /**
  * Gets the configuration file.
  * 
  * @param configfile ConfigFile to get
  * 
  * @return YamlConfiguration object
  */
 public YamlConfiguration getConfig(ConfigFile configfile) {
     if (loaded.containsKey(configfile) && !loaded.get(configfile)) {
         loadConfig(configfile);
     }
     return config.get(configfile);
 }

 /**
  * Gets the configuration file.
  * 
  * @param configfile ConfigFile to get
  * 
  * @return Configuration file
  */
 public File getConfigFile(ConfigFile configfile) {
     return configFile.get(configfile);
 }

 /**
  * Checks if the configuration file is loaded.
  * 
  * @param configfile ConfigFile to get
  * 
  * @return True if configuraton file is loaded
  */
 public boolean getLoaded(ConfigFile configfile) {
     return loaded.get(configfile);
 }

 /**
  * Loads all configuration files. (can be used to save a total of 2 lines!)
  */
 public void loadConfigs() {
     for (ConfigFile configfile : ConfigFile.values()) {
         loadConfig(configfile);
     }
 }

 /**
  * Loads the configuration file from the .jar.
  * 
  * @param configfile ConfigFile to load
  */
 public void loadConfig(ConfigFile configfile) {
     configFile.put(configfile, new File(plugin.getDataFolder(), configfile.getFile()));
     if (configFile.get(configfile).exists()) {
         config.put(configfile, new YamlConfiguration());
         try {
             config.get(configfile).load(configFile.get(configfile));
         } catch (FileNotFoundException ex) {
             loaded.put(configfile, false);
             return;
         } catch (IOException ex) {
             loaded.put(configfile, false);
             return;
         } catch (InvalidConfigurationException ex) {
             loaded.put(configfile, false);
             return;
         }
         loaded.put(configfile, true);
     } else {
         try {
             plugin.getDataFolder().mkdir();
             InputStream jarURL = ConfigManager.class.getResourceAsStream("/" + configfile.getFile());
             copyFile(jarURL, configFile.get(configfile));
             config.put(configfile, new YamlConfiguration());
             config.get(configfile).load(configFile.get(configfile));
             loaded.put(configfile, true);
         } catch (Exception e) {
         }
     }
 }

 /**
  * Copies a file to a new location.
  * 
  * @param in InputStream
  * @param out File
  * 
  * @throws Exception
  */
 private void copyFile(InputStream in, File out) throws Exception {
     InputStream fis = in;
     FileOutputStream fos = new FileOutputStream(out);
     try {
         byte[] buf = new byte[1024];
         int i = 0;
         while ((i = fis.read(buf)) != -1) {
             fos.write(buf, 0, i);
         }
     } catch (Exception e) {
         throw e;
     } finally {
         if (fis != null) {
             fis.close();
         }
         if (fos != null) {
             fos.close();
         }
     }
 }

 /**
  * Constructor of SpaceConfig.
  */
 public ConfigManager(Dungeons instance) {
	 this.plugin = instance;
 }

 /**
  * All config files.
  */
 public enum ConfigFile {
     // Enums
     CONFIG("config.yml");

     // Variables
     private String file;

     /**
      * Constructor of ConfigFile.
      * @param file 
      */
     ConfigFile(String file) {
         this.file = file;
     }

     /**
      * Gets the file associated with the enum.
      * 
      * @return File associated with the enum
      */
     public String getFile() {
         return this.file;
     }
 }
}