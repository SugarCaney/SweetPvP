package nl.SugCube.SweetPvP.Main;

import org.bukkit.inventory.ItemStack;

public class StringHandler {

	public static SweetPvP plugin;
	
	public StringHandler(SweetPvP instance) {
		plugin = instance;
	}
	
	public static ItemStack setItemStack(String string) {
		ItemStack is;
		try {
			String values[] = string.split(",");
			if (values.length > 2) {
				is = new ItemStack(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Short.parseShort(values[2]));
			} else {
				is = new ItemStack(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
			}
		} catch (Exception e) {
			is = new ItemStack(0, 1);
			System.out.println("Could not parse ItemStack! nl.SugCube.SweetPvP.Main.StringHandler.java.setItemStack(String string)");
		}
		return is;
	}
	
}