package nl.SugCube.SweetPvP.Main;

import nl.SugCube.SweetPvP.Listeners.PlayerServerListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Methods {
	
	public static SweetPvP plugin;
	public static ItemStack is;
	
	public Methods(SweetPvP instance) {
		plugin = instance;
	}
	
	public static boolean spawnPlayer(Player player, int arena) {
		if (plugin.arena[arena].getPlayers().size() == plugin.arena[arena].getArenaSpawns()) {
			return false;
		} else {
			int count = 1;
			while (plugin.arena[arena].getOccpiedSpawns().contains("" + count)) {
				count++;
			}
			plugin.arena[arena].addSpawn("" + count);
			SweetPvP.playerSpawn.put(player, "" + count);
			Location teleportLocation = new Location(player.getWorld(),
					plugin.getData().getDouble("arena." + (arena + 1) + "." + count + ".x"),
					plugin.getData().getDouble("arena." + (arena + 1) + "." + count + ".y"),
					plugin.getData().getDouble("arena." + (arena + 1) + "." + count + ".z"));
			teleportLocation.setYaw((float) plugin.getData().getDouble("arena." + (arena + 1) + "." + count + ".yaw"));
			teleportLocation.setPitch((float) plugin.getData().getDouble("arena." + (arena + 1) + "." + count + ".pitch"));
			player.teleport(teleportLocation);
			player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999999, 4));
			return true;
		}
	}
	
	public static void setInGame(Player player, String kit) {
		if (SweetPvP.inGame.contains(player)) {
			SweetPvP.inGame.remove(player);
			plugin.inv.setPlayerInv(player);
		} else {
			SweetPvP.inGame.add(player);
			plugin.inv.setArenaInv(player, kit);
			
			if (player.getGameMode().getValue() == 1) {
				SweetPvP.creative.add(player);
			}
			player.setGameMode(GameMode.SURVIVAL);
			
			SweetPvP.effects.put(player, player.getActivePotionEffects());
			for (PotionEffect effect : player.getActivePotionEffects()) {
				player.removePotionEffect(effect.getType());
			}
			
			SweetPvP.exp.put(player, player.getExp());
			player.setExp(0);
			SweetPvP.level.put(player, player.getLevel());
			player.setLevel(0);
			SweetPvP.health.put(player, player.getHealth());
			player.setHealth(20.0);
			SweetPvP.food.put(player, player.getFoodLevel());
			player.setFoodLevel(20);
			SweetPvP.saturation.put(player, player.getSaturation());
			player.setSaturation(20F);
		}
	}

	public static void onWin(Player player) {
		int arena = 0;
		
		if (SweetPvP.inGame.contains(player)) {
			for (int i = 0; i < 1000; i++) {
				if (plugin.arena[i].getPlayers().contains(player)) {
					arena = i;
					break;
				}
			}
			
			PlayerServerListener.quit(player);
			is = StringHandler.setItemStack(plugin.getConfig().getString("game.prize"));
			player.getInventory().addItem(is);
			player.sendMessage(setColors(plugin.getConfig().getString("tag.main") +
					plugin.getConfig().getString("messages.get-prize")
					.replaceAll("%prize", StringHandler.getFriendlyItemName(is.getType().name()))));
			Bukkit.broadcastMessage(setColors(plugin.getConfig().getString("tag.main") +
					plugin.getConfig().getString("messages.on-win").replaceAll("%player", player.getDisplayName())
					.replaceAll("%arena", "Arena " + (arena + 1))));
			
			plugin.arena[arena].inGame = false;
			plugin.arena[arena].resetCount();
		}
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
