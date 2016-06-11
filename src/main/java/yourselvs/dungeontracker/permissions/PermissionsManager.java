package yourselvs.dungeontracker.permissions;

import java.util.List;

import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

import yourselvs.dungeontracker.Dungeons;

public class PermissionsManager {
	private Dungeons plugin;
	private PluginManager pluginManager;
	private List<Permission> permissions;
	private boolean loaded = false;
	
	public PermissionsManager(Dungeons instance){
		this.plugin = instance;
		this.pluginManager = plugin.getServer().getPluginManager();
		
		permissions.add(new Permission("dungeon.info")); // view info about the plugin and each dungeon
		permissions.add(new Permission("dungeon.join")); // join/leave a dungeon
		permissions.add(new Permission("dungeon.forcejoin"));; // force a player to join/leave a dungeon
		permissions.add(new Permission("dungeon.leaders")); // view the leaderboards of a dungeon
		permissions.add(new Permission("dungeon.history")); // view the completion history of a dungeon or a specific player in a dungeon
		permissions.add(new Permission("dungeon.record")); // view dungeon world record, personal record, and other players records
		permissions.add(new Permission("dungeon.create")); // create a dungeon
		permissions.add(new Permission("dungeon.param")); // view/change a parameter of a dungeon
		permissions.add(new Permission("dungeon.command")); // view/change a commmand value of a dungeon
	}
	
	public void loadPermissions(){
		if(!loaded){			
			for(Permission permission : permissions)
				pluginManager.addPermission(permission);
			
			loaded = true;
		}
	}
	
	public List<Permission> getPermissions(){return permissions;}
	
	public void addPermission(Permission permission){
		permissions.add(permission);
		pluginManager.addPermission(permission);
	}
	
	public void addPermission(String permissionStr){
		Permission permission = new Permission(permissionStr);
		permissions.add(permission);
		pluginManager.addPermission(permission);
	}
}
