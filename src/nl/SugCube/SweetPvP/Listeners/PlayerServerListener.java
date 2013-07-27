package nl.SugCube.SweetPvP.Listeners;

import nl.SugCube.SweetPvP.Main.Methods;
import nl.SugCube.SweetPvP.Main.Powers;
import nl.SugCube.SweetPvP.Main.SweetPvP;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

public class PlayerServerListener implements Listener {

	public static SweetPvP plugin;
	
	public PlayerServerListener(SweetPvP instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		if (SweetPvP.inGame.contains(event.getPlayer())) {
			if (!(event.getMessage().equalsIgnoreCase("/spvp quit"))) {
				if (event.getPlayer().hasPermission("sweetpvp.admin") || event.getPlayer().hasPermission("sweetpvp.staff")) {
					return;
				}
				event.setCancelled(true);
				event.getPlayer().sendMessage(Methods.setColors(
						plugin.getConfig().getString("tag.error") + plugin.getConfig().getString("messages.no-command")));
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		quit(event.getPlayer());
	}
	
	public static void quit(Player player) {
		if (Powers.powerBlindness.contains(player))
			Powers.powerBlindness.remove(player);
		if (Powers.powerPoison.contains(player))
			Powers.powerPoison.remove(player);
		if (Powers.powerDisorient.contains(player))
			Powers.powerDisorient.remove(player);
		if (Powers.powerFire.contains(player))
			Powers.powerFire.remove(player);
		if (Powers.powerDrain.contains(player))
			Powers.powerDrain.remove(player);
		if (Powers.powerNausea.contains(player))
			Powers.powerNausea.remove(player);
		if (Powers.powerSlowness.contains(player))
			Powers.powerSlowness.remove(player);
		if (Powers.powerStrength.contains(player))
			Powers.powerStrength.remove(player);
		if (Powers.powerInvisibility.contains(player))
			Powers.powerInvisibility.remove(player);
		if (SweetPvP.inGame.contains(player)) {
			SweetPvP.inGame.remove(player);
			Location loc = new Location(player.getWorld(), plugin.getData().getDouble("lobbyspawn.x"),
					plugin.getData().getDouble("lobbyspawn.y"), plugin.getData().getDouble("lobbyspawn.z"));
			loc.setPitch((float) plugin.getData().getDouble("lobbyspawn.pitch"));
			loc.setYaw((float) plugin.getData().getDouble("lobbyspawn.yaw"));
			player.teleport(loc);
			plugin.inv.setPlayerInv(player);
		}
		
		for (int i = 0; i < 1000; i++) {
			if (plugin.arena[i].getPlayers().contains(player)) {
				plugin.arena[i].removeSpawn("" + SweetPvP.playerSpawn.get(player));
				SweetPvP.playerSpawn.remove(player);
				plugin.arena[i].removePlayer(player);
			}
		}
		
		if (SweetPvP.creative.contains(player)) {
			SweetPvP.creative.remove(player);
			player.setGameMode(GameMode.CREATIVE);
		}
		
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		if (SweetPvP.effects.containsKey(player)) {
			for (PotionEffect effect : SweetPvP.effects.get(player)) {
				player.addPotionEffect(effect);
			}
		}
		
		try {
			player.setExp(SweetPvP.exp.get(player));
			SweetPvP.exp.remove(player);
		} catch (Exception e) { }
		try {
			player.setLevel(SweetPvP.level.get(player));
			SweetPvP.level.remove(player);
		} catch (Exception e) { }
		try {
			player.setHealth(SweetPvP.health.get(player));
			SweetPvP.health.remove(player);
		} catch (Exception e) { }
		try {
			player.setSaturation(SweetPvP.saturation.get(player));
			SweetPvP.saturation.remove(player);
		} catch (Exception e) { }
		try {
			player.setFoodLevel(SweetPvP.food.get(player));
			SweetPvP.food.remove(player);
		} catch (Exception e) { }
		
	}
	
}
