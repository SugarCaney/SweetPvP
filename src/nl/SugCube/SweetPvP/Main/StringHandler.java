package nl.SugCube.SweetPvP.Main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class StringHandler {

	public static SweetPvP plugin;
	
	public StringHandler(SweetPvP instance) {
		plugin = instance;
	}
	
	public static String locationToString(Location loc) {
		return loc.getWorld().getName() + "%" + loc.getX() + "%" + loc.getY() + "%" + loc.getZ();
	}
	
	public static Location setLocation(String string) {
		String[] s = string.split("%");
		Location loc = new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]),
				Double.parseDouble(s[2]), Double.parseDouble(s[3]));
		return loc;
	}
	
	public static String getFriendlyItemName(String string) {
		String first = string.substring(0, 1).toUpperCase();
		String last = string.substring(1, string.length()).toLowerCase();
		string = first + last;
		return string.replaceAll("_", " ");
	}
	
	public static ItemStack setItemStack(String string) {
		ItemStack is;
		try {
			String values[] = string.split(",");
			if (values.length > 2) {
				if (values.length > 8) {
					is = new ItemStack(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Short.parseShort(values[2]));
					is = addEnchantment(is, values[3], values[4]);
					is = addEnchantment(is, values[5], values[6]);
					is = addEnchantment(is, values[7], values[8]);
				} else if (values.length > 6) {
					is = new ItemStack(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Short.parseShort(values[2]));
					is = addEnchantment(is, values[3], values[4]);
					is = addEnchantment(is, values[5], values[6]);
				} else if (values.length > 4) {
					is = new ItemStack(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Short.parseShort(values[2]));
					is = addEnchantment(is, values[3], values[4]);
				} else {
					is = new ItemStack(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Short.parseShort(values[2]));
				}
			} else {
				is = new ItemStack(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
			}
		} catch (Exception e) {
			is = new ItemStack(0, 1);
			System.out.println("Could not parse ItemStack! nl.SugCube.SweetPvP.Main.StringHandler.java.setItemStack(String string)");
		}
		return is;
	}

	public static ItemStack addEnchantment(ItemStack is, String name, String level) {
		switch (name.toLowerCase()) {
		case "power":
			is.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, Integer.parseInt(level)); break;
		case "flame":
			is.addUnsafeEnchantment(Enchantment.ARROW_FIRE, Integer.parseInt(level)); break;
		case "infinity":
			is.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, Integer.parseInt(level)); break;
		case "punch":
			is.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, Integer.parseInt(level)); break;
		case "sharpness":
			is.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, Integer.parseInt(level)); break;
		case "arthropods":
			is.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, Integer.parseInt(level)); break;
		case "smite":
			is.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, Integer.parseInt(level)); break;
		case "efficiency":
			is.addUnsafeEnchantment(Enchantment.DIG_SPEED, Integer.parseInt(level)); break;
		case "unbreaking":
			is.addUnsafeEnchantment(Enchantment.DURABILITY, Integer.parseInt(level)); break;
		case "fireaspect":
			is.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, Integer.parseInt(level)); break;
		case "knockback":
			is.addUnsafeEnchantment(Enchantment.KNOCKBACK, Integer.parseInt(level)); break;
		case "fortune":
			is.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, Integer.parseInt(level)); break;
		case "looting":
			is.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, Integer.parseInt(level)); break;
		case "respiration":
			is.addUnsafeEnchantment(Enchantment.OXYGEN, Integer.parseInt(level)); break;
		case "protection":
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, Integer.parseInt(level)); break;
		case "blastresistance":
			is.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, Integer.parseInt(level)); break;
		case "featherfalling":
			is.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, Integer.parseInt(level)); break;
		case "fireprotection":
			is.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, Integer.parseInt(level)); break;
		case "projectileprotection":
			is.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, Integer.parseInt(level)); break;
		case "silktouch":
			is.addUnsafeEnchantment(Enchantment.SILK_TOUCH, Integer.parseInt(level)); break;
		case "thorns":
			is.addUnsafeEnchantment(Enchantment.THORNS, Integer.parseInt(level)); break;
		case "aquaaffinity":
			is.addUnsafeEnchantment(Enchantment.WATER_WORKER, Integer.parseInt(level)); break;
		}
		
		return is;
	}
	
}