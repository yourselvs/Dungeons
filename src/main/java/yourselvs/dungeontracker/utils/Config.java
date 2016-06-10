package yourselvs.dungeontracker.utils;

//Java Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

//Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

/**
* A class that handles the configuration file.
* 
* @author iffa
* @author Pandarr
* @author Sammy
* @author kitskub
*/
public class Config {
 // Variables
 private static YamlConfiguration myConfig;
 private static File configFile;
 private static boolean loaded = false;

 /**
  * Gets the configuration file.
  * 
  * @return the myConfig
  */
 public static YamlConfiguration getConfig() {
     if (!loaded) {
         loadConfig();
     }
     return myConfig;
 }

 /**
  * Gets the configuration file.
  * 
  * @return Configuration file
  */
 public static File getConfigFile() {
     return configFile;
 }

 /**
  * Loads the configuration file from the .jar.
  */
 public static void loadConfig() {
     configFile = new File(Bukkit.getServer().getPluginManager().getPlugin("YourPlugin").getDataFolder(), "config.yml");
     if (configFile.exists()) {
         myConfig = new YamlConfiguration();
         try {
             myConfig.load(configFile);
         } catch (FileNotFoundException ex) {
             // TODO: Log exception
         } catch (IOException ex) {
             // TODO: Log exception
         } catch (InvalidConfigurationException ex) {
             // TODO: Log exception
         }
         loaded = true;
     } else {
         try {
             Bukkit.getServer().getPluginManager().getPlugin("BananaSpace").getDataFolder().mkdir();
             InputStream jarURL = Config.class.getResourceAsStream("/config.yml");
             copyFile(jarURL, configFile);
             myConfig = new YamlConfiguration();
             myConfig.load(configFile);
             loaded = true;
             // TODO: Log that config has been loaded
         } catch (Exception e) {
             // TODO: Log exception
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
 static private void copyFile(InputStream in, File out) throws Exception {
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
 private Config() {
 }
}