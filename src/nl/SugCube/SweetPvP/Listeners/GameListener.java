package nl.SugCube.SweetPvP.Listeners;

import nl.SugCube.SweetPvP.Main.Methods;
import nl.SugCube.SweetPvP.Main.SweetPvP;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class GameListener implements Listener {
	
	public static SweetPvP plugin;
	
	public GameListener(SweetPvP instance) {
		plugin = instance;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = (Player) event.getEntity();
		if (SweetPvP.inGame.contains(player)) {
			int arena = 0;
			for (int i = 0; i < 1000; i++) {
				if (plugin.arena[i].getPlayers().contains(player)) {
					arena = i;
					break;
				}
			}
			event.setDroppedExp(0);
			event.getDrops().clear();
			
			for (Player p : plugin.arena[arena].getPlayers()) {
				try {
					p.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.main") + plugin.getConfig().getString("messages.kill")
							.replaceAll("%player", player.getDisplayName()).replaceAll("%killer",
									player.getKiller().getDisplayName())));
				} catch (Exception e) {
					p.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.main") + plugin.getConfig().getString("messages.died")
							.replaceAll("%player", player.getDisplayName())));
				}
				p.playSound(p.getLocation(), Sound.GHAST_DEATH, 1, 1);
				p.playEffect(event.getEntity().getLocation(), Effect.SMOKE, 1);
				p.playEffect(event.getEntity().getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
			}
			
			event.setDeathMessage(null);
			PlayerServerListener.quit(player);
			
			Location loc = new Location(player.getWorld(), plugin.getData().getDouble("lobbyspawn.x"),
					plugin.getData().getDouble("lobbyspawn.y"), plugin.getData().getDouble("lobbyspawn.z"));
			loc.setPitch((float) plugin.getData().getDouble("lobbyspawn.pitch"));
			loc.setYaw((float) plugin.getData().getDouble("lobbyspawn.yaw"));
			player.teleport(loc);
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		for (int i = 0; i < 1000; i++) {
			if (plugin.arena[i].getPlayers().contains(player)) {
				if (!plugin.arena[i].inGame) {
					player.teleport(player.getLocation());
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		for (int i = 0; i < 1000; i++) {
			if (plugin.arena[i].getPlayers().contains(player)) {
				if (!plugin.arena[i].inGame) {
					event.setCancelled(true);
					player.updateInventory();
				}
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (SweetPvP.inGame.contains(player)) {
			if (event.getBlockPlaced().getType() == Material.TNT) {
				event.getBlockPlaced().setType(Material.AIR);
				Location loc = event.getBlockPlaced().getLocation();
				loc.setY(loc.getY() + 0.4);
				Entity tnt = event.getPlayer().getWorld().spawn(loc, TNTPrimed.class);
		        ((TNTPrimed) tnt).setFuseTicks(80);
		        event.getPlayer().getInventory().removeItem(new ItemStack(Material.TNT, 1));
			}
			if (event.getBlockPlaced().getType() != Material.FIRE) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (SweetPvP.inGame.contains(player)) {
			if (event.getBlock().getType() != Material.FIRE) {
				event.setCancelled(true);
			}
		}
	}

}
