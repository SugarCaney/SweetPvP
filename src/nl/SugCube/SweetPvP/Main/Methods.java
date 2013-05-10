package nl.SugCube.SweetPvP.Main;

import nl.SugCube.SweetPvP.Listeners.PlayerServerListener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Methods {
	
	public static SweetPvP plugin;
	public static ItemStack is;
	
	public Methods(SweetPvP instance) {
		plugin = instance;
	}

	public static void onWin(Player player) {
		PlayerServerListener.quit(player);
		is = StringHandler.setItemStack(plugin.getConfig().getString("game.prize"));
		player.getInventory().addItem(is);
		player.sendMessage(setColors(plugin.getConfig().getString("tag.main") +
				plugin.getConfig().getString("messages.get-prize")));
	}
	
	public static String setColors(String s) {
		return s.
			replace("&0", ChatColor.BLACK + "").
			replace("&1", ChatColor.DARK_BLUE + "").
			replace("&2", ChatColor.DARK_GREEN + "").
			replace("&3", ChatColor.DARK_AQUA + "").
			replace("&4", ChatColor.DARK_RED + "").
			replace("&5", ChatColor.DARK_PURPLE + "").
			replace("&6", ChatColor.GOLD + "").
			replace("&7", ChatColor.GRAY + "").
			replace("&8", ChatColor.DARK_GRAY + "").
			replace("&9", ChatColor.BLUE + "").
			replace("&a", ChatColor.GREEN + "").
			replace("&b", ChatColor.AQUA + "").
			replace("&c", ChatColor.RED + "").
			replace("&d", ChatColor.LIGHT_PURPLE + "").
			replace("&e", ChatColor.YELLOW + "").
			replace("&f", ChatColor.WHITE + "").
			replace("&k", ChatColor.MAGIC + "").
			replace("&l", ChatColor.BOLD + "").
			replace("&m", ChatColor.STRIKETHROUGH + "").
			replace("&n", ChatColor.UNDERLINE + "").
			replace("&o", ChatColor.ITALIC + "").
			replace("&r", ChatColor.RESET + "");
	}
	
}
