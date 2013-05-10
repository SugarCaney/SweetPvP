package nl.SugCube.SweetPvP.Main;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import nl.SugCube.SweetPvP.Listeners.GameListener;
import nl.SugCube.SweetPvP.Listeners.PlayerServerListener;
import nl.SugCube.SweetPvP.Listeners.SignListener;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SweetPvP extends JavaPlugin {
	
	public Logger logger = Logger.getLogger("Minecraft");
	private FileConfiguration data = null;
	private File dataFile = null;
	public Inventories inv = new Inventories(this);
	public Location lobbyspawn;
	
	public PlayerServerListener psl = new PlayerServerListener(this);
	public SignListener sl = new SignListener(this);
	public GameListener gl = new GameListener(this);
	
	public static List<Player> inGame = new ArrayList<Player>();
	
	@Override
	public void onEnable() {
		PluginDescriptionFile f = this.getDescription();
		this.logger.info("[" + f.getName() + "]" + " v" + f.getVersion() + " Has Been Enabled!");
		
		File file = new File(getDataFolder() + File.separator + "config.yml");
		if (!file.exists()) {
			getConfig().options().copyDefaults(true);
			saveConfig();
		}
		
		File df = new File(getDataFolder() + File.separator + "data.yml");
		if (!df.exists()) {
			reloadData();
			saveData();
		}
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(psl, this);
		pm.registerEvents(sl, this);
		pm.registerEvents(gl, this);
	}
	
	@Override
	public void onDisable() {
		saveData();
		PluginDescriptionFile f = this.getDescription();
		this.logger.info("[" + f.getName() + "] " + f.getVersion() + " Has Been Disabled!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("spvp")) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("setlobby")) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						if (player.hasPermission("sweetpvp.admin")) {
							getData().set("lobbyspawn.x", player.getLocation().getX());
							getData().set("lobbyspawn.y", player.getLocation().getY());
							getData().set("lobbyspawn.z", player.getLocation().getZ());
							getData().set("lobbyspawn.pitch", player.getLocation().getPitch());
							getData().set("lobbyspawn.yaw", player.getLocation().getYaw());
							saveData();
							player.sendMessage(Methods.setColors(getConfig().getString("tag.main") +
									getConfig().getString("messages.lobby-set")));
						} else {
							player.sendMessage(Methods.setColors(getConfig().getString("tag.error") +
									getConfig().getString("messages.no-permission")));
						}
					}
				} else if (args[0].equalsIgnoreCase("test")) {
					if (inGame.contains((Player) sender)) {
						inGame.remove((Player) sender);
						inv.setPlayerInv((Player) sender);
					} else {
						inGame.add((Player) sender);
						inv.setArenaInv((Player) sender);
					}
				} else if (args[0].equalsIgnoreCase("win")) {
					Player player = (Player) sender;
					Methods.onWin(player);
				}
			} else {
				
			}
		}
		return false;
	}
	
	public void reloadData() {
	    if (dataFile == null) {
	    	dataFile = new File(getDataFolder(), "data.yml");
	    }
	    data = YamlConfiguration.loadConfiguration(dataFile);
	 
	    InputStream defStream = this.getResource("data.yml");
	    if (defStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defStream);
	        data.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getData() {
	    if (data == null) {
	        this.reloadData();
	    }
	    return data;
	}
	
	public void saveData() {
	    if (data == null || dataFile == null) {
	    	return;
	    }
	    try {
	        getData().save(dataFile);
	    } catch (Exception ex) {
	        this.getLogger().log(Level.SEVERE, "Could not save config to " + dataFile, ex);
	    }
	}

}
