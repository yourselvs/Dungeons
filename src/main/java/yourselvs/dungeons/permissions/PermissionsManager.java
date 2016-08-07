package yourselvs.dungeons.permissions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

import yourselvs.dungeons.Dungeons;

public class PermissionsManager {
	private Dungeons plugin;
	private PluginManager pluginManager;
	private Set<String> permissions = new HashSet<String>();
	
	public PermissionsManager(Dungeons instance){
		this.plugin = instance;
		this.pluginManager = plugin.getServer().getPluginManager();
	}
	
	public Set<String> getPermissions(){return permissions;}
	
	public void addPermission(String permissionStr){
		Permission permission = new Permission(permissionStr);
		permissions.add(permissionStr);
		pluginManager.addPermission(permission);
	}
	
	public void removePermission(String permission){
		permissions.remove(permission);
		pluginManager.removePermission(permission);
	}
}
