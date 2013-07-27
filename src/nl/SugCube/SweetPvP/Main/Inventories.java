package nl.SugCube.SweetPvP.Main;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Inventories {
	
	public static SweetPvP plugin;
	public Inventory[] playerInv = new Inventory[9999999];
	public Inventory[] arenaInv = new Inventory[9999999];
	public ItemStack[][] playerArmor = new ItemStack[9999999][];
	
	Inventories(SweetPvP instance) {
		plugin = instance;
	}
	
	public void setArenaInv(Player player, String kit) {
		playerInv[player.getEntityId()] = Bukkit.createInventory(player, 36);
		playerInv[player.getEntityId()].setContents(player.getInventory().getContents());
		playerArmor[player.getEntityId()] = player.getInventory().getArmorContents();
		if (kit == "bambieknoodles") {
			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
		} else {
			player.getInventory().setContents(getArenaInv(player, kit).getContents());
		}
	}
	
	public void setPlayerInv(Player player) {
		player.getInventory().setContents(playerInv[player.getEntityId()].getContents());
		player.getInventory().setArmorContents(playerArmor[player.getEntityId()]);
		playerArmor[player.getEntityId()] = null;
		playerInv[player.getEntityId()].clear();
	}
	
	public Inventory getArenaInv(Player player, String kit) {
		arenaInv[player.getEntityId()] = Bukkit.createInventory(player, 36);
		String configNode = "kit." + kit;
		
		@SuppressWarnings("unchecked")
		List<String> kitlist = (List<String>) plugin.getConfig().getList(configNode);
		Iterator<String> iterator = kitlist.iterator();
		player.getInventory().setArmorContents(null);
		while (iterator.hasNext()) {
			ItemStack is = StringHandler.setItemStack(iterator.next());
			int id = is.getTypeId();
			if (id >= 298 && id <= 317) {
				if ((id + 2) % 4 == 0) {
					player.getInventory().setHelmet(is);
				} else if ((id + 1) % 4 == 0) {
					player.getInventory().setChestplate(is);
				} else if (id % 4 == 0) {
					player.getInventory().setLeggings(is);
				} else if ((id + 3) % 4 == 0) {
					player.getInventory().setBoots(is);
				}
			} else {
				arenaInv[player.getEntityId()].addItem(is);
			}
		}
		
		return arenaInv[player.getEntityId()];
	}
	
}