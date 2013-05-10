package nl.SugCube.SweetPvP.Listeners;

import nl.SugCube.SweetPvP.Main.SweetPvP;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class GameListener implements Listener {
	
	public static SweetPvP plugin;
	
	public GameListener(SweetPvP instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (SweetPvP.inGame.contains(player)) {
			event.setCancelled(true);
		}
	}

}
