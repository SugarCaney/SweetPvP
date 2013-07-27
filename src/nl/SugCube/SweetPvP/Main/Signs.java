package nl.SugCube.SweetPvP.Main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class Signs {

	public static SweetPvP p;
	public static List<Sign> signs = new ArrayList<Sign>();
	public static List<String> signsString = new ArrayList<String>();
	
	public Signs(SweetPvP instance) {
		p = instance;
	}
	
	public static void updateSigns() {
		
		if (signs.size() == 0)
			return;
		
		List<Sign> signs2 = new ArrayList<Sign>();
		for (Sign si : signs) {
			signs2.add(si);
		}
		
		for (Sign s : signs2) {
			
			if (s.getLocation().getBlock().getTypeId() == 63 || s.getLocation().getBlock().getTypeId() == 68) {
				s.update();
				
				String line = s.getLine(1);
				String[] words = line.split(" ");
				int arena = Integer.parseInt(words[2]) - 1;
				
				if (p.arena[arena].inGame) {
					s.setLine(2, ChatColor.DARK_RED + "In Game");
				} else if (!p.arena[arena].getEnabled()) {
					s.setLine(2, ChatColor.DARK_RED + "Disabled");
				} else {
					String[] lines = { p.arena[arena].getPlayers().size() + "" , p.arena[arena].getArenaSpawns() + "" };
					s.setLine(2, ChatColor.GREEN + lines[0] + "/" + lines[1]);
				}
				
				s.update();
			} else {
				signs.remove(s);
			}
			
		}
		
		signs2.clear();
		
	}
	
	public static void unregisterSign(Sign sign) {
		
		if (signs.contains(sign))
			signs.remove(sign);
		
	}
	
	public static void registerSign(Sign sign) {
		
		if (signs.contains(sign))
			return;
		
		signs.add(sign);
		
	}
	
	public static void saveSigns() {
		
		signsString.clear();
		
		for (Sign sign : signs) {
			String string = StringHandler.locationToString(sign.getLocation());
			signsString.add(string);
		}
		
		p.getData().set("signs", signsString);
		p.saveData();
		
	}
	
	public static void loadSigns() {
		
		signsString = p.getData().getStringList("signs");
		
		if (signsString == null) 
			return;
		
		for (String s : signsString) {
			Location loc = StringHandler.setLocation(s);
			Block block = loc.getBlock();
			
			if (block.getTypeId() == 63 || block.getTypeId() == 68) {
				Sign sign = (Sign) block.getState();
				signs.add(sign);
			}
		}
		
	}
	
}
