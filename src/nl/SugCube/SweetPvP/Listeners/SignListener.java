package nl.SugCube.SweetPvP.Listeners;

import nl.SugCube.SweetPvP.Main.Methods;
import nl.SugCube.SweetPvP.Main.SweetPvP;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener {
	
	public static SweetPvP plugin;
	
	public SignListener(SweetPvP instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onSignCreate(SignChangeEvent event) {
		if (event.getLine(0).equalsIgnoreCase("[PvP]")) {
			if (event.getLine(1).contains("Join Arena ")) {
				if (event.getLine(2).isEmpty()) {
					if (event.getLine(3).contains("Power:") || event.getLine(3).contains("Kit:") || event.getLine(3).isEmpty()) {
						event.getPlayer().sendMessage(Methods.setColors(
								plugin.getConfig().getString("tag.main") +
								plugin.getConfig().getString("messages.success-sign")));
					} else {
						event.getPlayer().sendMessage(Methods.setColors(
								plugin.getConfig().getString("tag.error") +
								plugin.getConfig().getString("messages.wrong-sign") +
								": Line 4"));
					}
				} else {
					event.getPlayer().sendMessage(Methods.setColors(
							plugin.getConfig().getString("tag.error") +
							plugin.getConfig().getString("messages.wrong-sign") +
							": Line 3"));
				}
			} else {
				event.getPlayer().sendMessage(Methods.setColors(
						plugin.getConfig().getString("tag.error") +
						plugin.getConfig().getString("messages.wrong-sign") +
						": Line 2"));
			}
		}
	}

}
