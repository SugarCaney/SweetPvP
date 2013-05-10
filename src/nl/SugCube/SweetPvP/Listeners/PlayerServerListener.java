package nl.SugCube.SweetPvP.Listeners;

import nl.SugCube.SweetPvP.Main.Powers;
import nl.SugCube.SweetPvP.Main.SweetPvP;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerServerListener implements Listener {

	public static SweetPvP plugin;
	
	public PlayerServerListener(SweetPvP instance) {
		plugin = instance;
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
		if (SweetPvP.inGame.contains(player)) {
			SweetPvP.inGame.remove(player);
			Location loc = new Location(player.getWorld(), plugin.getData().getDouble("lobbyspawn.x"),
					plugin.getData().getDouble("lobbyspawn.y"), plugin.getData().getDouble("lobbyspawn.z"));
			loc.setPitch((float) plugin.getData().getDouble("lobbyspawn.pitch"));
			loc.setYaw((float) plugin.getData().getDouble("lobbyspawn.yaw"));
			player.teleport(loc);
			plugin.inv.setPlayerInv(player);
		}
	}
	
}
