package nl.SugCube.SweetPvP.Main;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Inventories {
	
	public static SweetPvP plugin;
	public Inventory[] playerInv = new Inventory[9999999];
	public Inventory[] arenaInv = new Inventory[9999999];
	
	Inventories(SweetPvP instance) {
		plugin = instance;
	}
	
	public void setArenaInv(Player player) {
		playerInv[player.getEntityId()] = Bukkit.createInventory(player, 36);
		playerInv[player.getEntityId()].setContents(player.getInventory().getContents());
		player.getInventory().setContents(getArenaInv(player).getContents());
	}
	
	public void setPlayerInv(Player player) {
		player.getInventory().setContents(playerInv[player.getEntityId()].getContents());
		playerInv[player.getEntityId()].clear();
	}
	
	public Inventory getArenaInv(Player player) {
		arenaInv[player.getEntityId()] = Bukkit.createInventory(player, 36);
		
		@SuppressWarnings("unchecked")
		List<String> kitlist = (List<String>) plugin.getConfig().getList("kit.basic");
		Iterator<String> iterator = kitlist.iterator();
		while (iterator.hasNext()) {
			arenaInv[player.getEntityId()].addItem(StringHandler.setItemStack(iterator.next()));
		}
		
		return arenaInv[player.getEntityId()];
	}
	
}