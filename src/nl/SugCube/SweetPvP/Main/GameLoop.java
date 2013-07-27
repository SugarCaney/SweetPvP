package nl.SugCube.SweetPvP.Main;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GameLoop implements Runnable {

	public static SweetPvP plugin;
	
	public GameLoop(SweetPvP instance) {
		plugin = instance;
	}

	public void run() {
		
		Signs.updateSigns();
		
		for (int i = 0; i < 1000; i++) {
			
			if (plugin.arena[i].inGame) {
				
				if (plugin.arena[i].getPlayers().size() == 1) {
					Methods.onWin(plugin.arena[i].getPlayers().get(0));
				}
				
			}
			
			if (plugin.arena[i].canCountdown()) {
				
				if (plugin.arena[i].countdown == 60) {
					for (Player p : plugin.arena[i].getPlayers()) {
						p.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.main") +
								plugin.getConfig().getString("messages.arena-countdown").replace("%time", "60")));
					}
				} else if (plugin.arena[i].countdown == 50) {
					for (Player p : plugin.arena[i].getPlayers()) {
						p.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.main") +
								plugin.getConfig().getString("messages.arena-countdown").replace("%time", "50")));
					}
				} else if (plugin.arena[i].countdown == 40) {
					for (Player p : plugin.arena[i].getPlayers()) {
						p.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.main") +
								plugin.getConfig().getString("messages.arena-countdown").replace("%time", "40")));
					}
				} else if (plugin.arena[i].countdown == 30) {
					for (Player p : plugin.arena[i].getPlayers()) {
						p.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.main") +
								plugin.getConfig().getString("messages.arena-countdown").replace("%time", "30")));
					}
				} else if (plugin.arena[i].countdown == 20) {
					for (Player p : plugin.arena[i].getPlayers()) {
						p.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.main") +
								plugin.getConfig().getString("messages.arena-countdown").replace("%time", "20")));
					}
				} else if (plugin.arena[i].countdown == 10) {
					for (Player p : plugin.arena[i].getPlayers()) {
						p.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.main") +
								plugin.getConfig().getString("messages.arena-countdown").replace("%time", "10")));
					}
				} else if (plugin.arena[i].countdown == 2) {
					for (Player p : plugin.arena[i].getPlayers()) {
						p.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.main") +
								plugin.getConfig().getString("messages.arena-countdown-ready")));
					}
				} else if (plugin.arena[i].countdown == 1) {
					for (Player p : plugin.arena[i].getPlayers()) {
						p.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.main") +
								plugin.getConfig().getString("messages.arena-countdown-steady")));
					}
				} else if (plugin.arena[i].countdown == 0) {
					for (Player p : plugin.arena[i].getPlayers()) {
						p.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.main") +
								plugin.getConfig().getString("messages.arena-countdown-fight")));
					}
					plugin.arena[i].countdown = 121;
					plugin.arena[i].resetCount();
					if (plugin.arena[i].getPlayers().size() > 1) {
						plugin.arena[i].inGame = true;
						for (Player p : plugin.arena[i].getPlayers()) {
							p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
							p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 4));
						}
					} else {
						for (Player p : plugin.arena[i].getPlayers()) {
							p.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.error") +
								plugin.getConfig().getString("messages.not-enough-players")));
						}
					}
				}
				
				plugin.arena[i].countdown--;
				
			}
			
		}
	}
	
}
