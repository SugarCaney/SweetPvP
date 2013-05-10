package nl.SugCube.SweetPvP.Main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Powers {

	public static SweetPvP plugin;
	
	public Powers(SweetPvP instance) {
		plugin = instance;
	}
	
	public static List<Player> powerPoison = new ArrayList<Player>();
	public static List<Player> powerDisorient = new ArrayList<Player>();
	public static List<Player> powerFire = new ArrayList<Player>();
	public static List<Player> powerNausea = new ArrayList<Player>();
	public static List<Player> powerBlindness = new ArrayList<Player>();
	public static List<Player> powerDrain = new ArrayList<Player>();
	public static List<Player> powerSlowness = new ArrayList<Player>();
	public static List<Player> powerStrength = new ArrayList<Player>();
	
}
