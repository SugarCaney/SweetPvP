package nl.SugCube.SweetPvP.Main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Powers {

	public static SweetPvP plugin;
	
	public static List<Player> powerPoison = new ArrayList<Player>();
	public static List<Player> powerDisorient = new ArrayList<Player>();
	public static List<Player> powerFire = new ArrayList<Player>();
	public static List<Player> powerNausea = new ArrayList<Player>();
	public static List<Player> powerBlindness = new ArrayList<Player>();
	public static List<Player> powerDrain = new ArrayList<Player>();
	public static List<Player> powerSlowness = new ArrayList<Player>();
	public static List<Player> powerStrength = new ArrayList<Player>();
	public static List<Player> powerInvisibility = new ArrayList<Player>();
	
	public Powers(SweetPvP instance) {
		plugin = instance;
	}
	
	public static boolean setPower(Player player, String type) {
		switch (type) {
		case "Poison":
			if (player.hasPermission("sweetpvp.power.poison") || player.hasPermission("sweetpvp.admin")
					|| player.hasPermission("sweetpvp.staff")) {
				if (!(powerPoison.contains(player)))
					powerPoison.add(player);
			} else {
				player.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.error") +
						plugin.getConfig().getString("messages.no-permission")));
				return false;
			}
			return true;
		case "Disorient":
			if (player.hasPermission("sweetpvp.power.disorient") || player.hasPermission("sweetpvp.admin")
					|| player.hasPermission("sweetpvp.staff")) {
				if (!(powerDisorient.contains(player)))
					powerDisorient.add(player);
			} else {
				player.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.error") +
						plugin.getConfig().getString("messages.no-permission")));
				return false;
			}
			return true;
		case "Fire":
			if (player.hasPermission("sweetpvp.power.fire") || player.hasPermission("sweetpvp.admin")
					|| player.hasPermission("sweetpvp.staff")) {
				if (!(powerFire.contains(player)))
					powerFire.add(player);
			} else {
				player.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.error") +
						plugin.getConfig().getString("messages.no-permission")));
				return false;
			}
			return true;
		case "Nausea":
			if (player.hasPermission("sweetpvp.power.nausea") || player.hasPermission("sweetpvp.admin")
					|| player.hasPermission("sweetpvp.staff")) {
				if (!(powerNausea.contains(player)))
					powerNausea.add(player);
			} else {
				player.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.error") +
						plugin.getConfig().getString("messages.no-permission")));
				return false;
			}
			return true;
		case "Blindness":
			if (player.hasPermission("sweetpvp.power.blindness") || player.hasPermission("sweetpvp.admin")
					|| player.hasPermission("sweetpvp.staff")) {
				if (!(powerBlindness.contains(player)))
					powerBlindness.add(player);
			} else {
				player.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.error") +
						plugin.getConfig().getString("messages.no-permission")));
				return false;
			}
			return true;
		case "Drain":
			if (player.hasPermission("sweetpvp.power.drain") || player.hasPermission("sweetpvp.admin")
					|| player.hasPermission("sweetpvp.staff")) {
				if (!(powerDrain.contains(player)))
					powerDrain.add(player);
			} else {
				player.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.error") +
						plugin.getConfig().getString("messages.no-permission")));
				return false;
			}
			return true;
		case "Slowness":
			if (player.hasPermission("sweetpvp.power.slowness") || player.hasPermission("sweetpvp.admin")
					|| player.hasPermission("sweetpvp.staff")) {
				if (!(powerSlowness.contains(player)))
					powerSlowness.add(player);
			} else {
				player.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.error") +
						plugin.getConfig().getString("messages.no-permission")));
				return false;
			}
			return true;
		case "Strength":
			if (player.hasPermission("sweetpvp.power.strength") || player.hasPermission("sweetpvp.admin")
					|| player.hasPermission("sweetpvp.staff")) {
				if (!(powerStrength.contains(player)))
					powerStrength.add(player);
			} else {
				player.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.error") +
						plugin.getConfig().getString("messages.no-permission")));
				return false;
			}
			return true;
		case "Invisible":
			if (player.hasPermission("sweetpvp.power.invisible") || player.hasPermission("sweetpvp.admin")
					|| player.hasPermission("sweetpvp.staff")) {
				if (!(powerInvisibility.contains(player)))
					powerInvisibility.add(player);
			} else {
				player.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.error") +
						plugin.getConfig().getString("messages.no-permission")));
				return false;
			}
			return true;
		default:
			return false;	
		}
	}
	
}
