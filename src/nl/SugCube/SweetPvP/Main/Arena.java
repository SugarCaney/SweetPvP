package nl.SugCube.SweetPvP.Main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Arena {
	
	public SweetPvP plugin;
	private int id;
	private int spawns = 0;
	private List<Player> players = new ArrayList<Player>();
	private List<String> spawnOccupied = new ArrayList<String>();
	private boolean canCountdown = false;
	private boolean enabled = true;
	public int countdown;
	public boolean inGame = false;
			
	public Arena(SweetPvP instance, int arenaID) {
		plugin = instance;
		this.id = arenaID;
		countdown = plugin.getConfig().getInt("game.countdown");
		
		while (plugin.getData().isSet("arena." + this.id + "." + (spawns + 1))) {
			spawns++;
		}
		
		if (plugin.getData().isSet("arena." + "id")) {
			if (plugin.getData().getBoolean("arena." + "id." + "enabled")) {
				this.enabled = true;
			} else {
				this.enabled = false;
			}
		}
	}
	
	public void removeSpawn(String spawn) {
		this.spawnOccupied.remove(spawn);
	}
	
	public void addSpawn(String spawn) {
		this.spawnOccupied.add(spawn);
	}
	
	public List<String> getOccpiedSpawns() {
		return this.spawnOccupied;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getArenaSpawns() {
		return this.spawns;
	}
	
	public List<Player> getPlayers() {
		return this.players;
	}
	
	public boolean canCountdown() {
		Signs.updateSigns();
		return canCountdown;
	}
	
	public boolean getEnabled() {
		return this.enabled;
	}
	
	public void addPlayer(Player player) {
		this.players.add(player);
		Signs.updateSigns();
	}
	
	public void removePlayer(Player player) {
		this.players.remove(player);
		Signs.updateSigns();
	}
	
	public void resetCount() {
		this.canCountdown = false;
		countdown = plugin.getConfig().getInt("game.countdown") + 1;
		Signs.updateSigns();
	}
	
	public void addSpawn() {
		this.spawns++;
		Signs.updateSigns();
	}
	
	public void resetSpawn() {
		this.spawns = 0;
		Signs.updateSigns();
	}
	
	public void enabled(boolean x) {
		this.enabled = x;
		Signs.updateSigns();
	}
	
	public void startCount(boolean x) {
		this.canCountdown = x;
	}
	
}