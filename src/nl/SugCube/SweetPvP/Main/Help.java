package nl.SugCube.SweetPvP.Main;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class Help {
	
	public static SweetPvP plugin;
	
	public Help(SweetPvP instance) {
		plugin = instance;
	}

	public static void show(Player p) {
		
		PluginDescriptionFile f = plugin.getDescription();
		p.sendMessage(Methods.setColors("&b* &eSweetPvP Help &aPlugin made by MrSugarCaney v" + f.getVersion()));
		p.sendMessage(Methods.setColors("&b* &6Commands can be executed like so: /spvp <command listed>"));
		if (p.hasPermission("sweetpvp.admin")) {
			p.sendMessage(Methods.setColors("&b* &asetlobby &eSets the spawn of the lobby, player's respawn"));
			p.sendMessage(Methods.setColors("&b* &aset <arena#> &eSets the next spawnpoint of the arena"));
			p.sendMessage(Methods.setColors("&b* &adelete <arena#> &eDeletes all spawns of a certain arena"));
		}
		if (p.hasPermission("sweetpvp.admin") || p.hasPermission("sweetpvp.staff") || p.hasPermission("sweetpvp.enable")) {
			p.sendMessage(Methods.setColors("&b* &aenable <arena#> &eEnables a certain arena"));
		}
		if (p.hasPermission("sweetpvp.admin") || p.hasPermission("sweetpvp.staff") || p.hasPermission("sweetpvp.disable")) {
			p.sendMessage(Methods.setColors("&b* &adisable <arena#> &eDisables a certain arena"));
		}
		if (p.hasPermission("sweetpvp.admin") || p.hasPermission("sweetpvp.staff") || p.hasPermission("sweetpvp.forcestart")) {
			p.sendMessage(Methods.setColors("&b* &aforcestart <arena#> &eForces a countdown of a certain arena"));
		}
		p.sendMessage(Methods.setColors("&b* &alobby &eTeleports you to the lobby"));
		p.sendMessage(Methods.setColors("&b* &aquit &eLeave a certain arena once joined"));
		
	}
	
}
