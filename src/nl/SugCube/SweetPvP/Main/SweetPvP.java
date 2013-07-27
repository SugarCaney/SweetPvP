package nl.SugCube.SweetPvP.Main;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import nl.SugCube.SweetPvP.Listeners.FightListener;
import nl.SugCube.SweetPvP.Listeners.GameListener;
import nl.SugCube.SweetPvP.Listeners.PlayerServerListener;
import nl.SugCube.SweetPvP.Listeners.SignListener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

public class SweetPvP extends JavaPlugin {
	
	public Logger logger = Logger.getLogger("Minecraft");
	private FileConfiguration data = null;
	private File dataFile = null;
	public Inventories inv = new Inventories(this);
	public Location lobbyspawn;
	public Methods m = new Methods(this);
	public Powers p = new Powers(this);
	public Arena[] arena = new Arena[1000];
	public Signs ss = new Signs(this);
	public Help help = new Help(this);
	
	public PlayerServerListener psl = new PlayerServerListener(this);
	public SignListener sl = new SignListener(this);
	public GameListener gl = new GameListener(this);
	public FightListener fl = new FightListener(this);
	
	public static List<Player> creative = new ArrayList<Player>();
	public static List<Player> inGame = new ArrayList<Player>();
	public static HashMap<Player, String> playerSpawn = new HashMap<Player, String>();
	public static HashMap<Player, Collection<PotionEffect>> effects = new HashMap<Player, Collection<PotionEffect>>();
	public static HashMap<Player, Float> exp = new HashMap<Player, Float>();
	public static HashMap<Player, Integer> level = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> health = new HashMap<Player, Integer>();
	public static HashMap<Player, Float> saturation = new HashMap<Player, Float>();
	public static HashMap<Player, Integer> food = new HashMap<Player, Integer>();
	
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
		pm.registerEvents(fl, this);
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new GameLoop(this), 20, 20);
		
		for (int i = 0; i < 1000; i++) {
			arena[i] = new Arena(this, i + 1);
		}
		
		Signs.loadSigns();
		Signs.updateSigns();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (inGame.contains(p)) {
				PlayerServerListener.quit(p);
				p.updateInventory();
			}
		}
		
		Signs.saveSigns();
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
				} else if (args[0].equalsIgnoreCase("set")) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						if (player.hasPermission("sweetpvp.admin")) {
							if (args.length > 1) {
								try {
									boolean hasNext = true;
									int count = 1;
									while (hasNext) {
										if (getData().isSet("arena." + args[1] + "." + count)) {
											count++;
										} else {
											hasNext = false;
											getData().set("arena." + args[1] + ".enabled", false);
										}
									}
									arena[Integer.parseInt(args[1]) - 1].addSpawn(); 
									getData().set("arena." + args[1] + "." + count + ".x", player.getLocation().getX());
									getData().set("arena." + args[1] + "." + count + ".y", player.getLocation().getY());
									getData().set("arena." + args[1] + "." + count + ".z", player.getLocation().getZ());
									getData().set("arena." + args[1] + "." + count + ".pitch", player.getLocation().getPitch());
									getData().set("arena." + args[1] + "." + count + ".yaw", player.getLocation().getYaw());
									saveData();
									player.sendMessage(Methods.setColors(getConfig().getString("tag.main") +
											getConfig().getString("messages.spawn-set").replaceAll("%no", "" + count)
											.replace("%arena", "Arena " + args[1])));
								} catch (Exception e) {
									player.sendMessage(Methods.setColors(getConfig().getString("tag.error") +
											"Usage: /spvp set <arena #>"));
								}
							} else { 
								player.sendMessage(Methods.setColors(getConfig().getString("tag.error") +
										"Usage: /spvp set <arena #>"));
							}
						} else {
							player.sendMessage(Methods.setColors(getConfig().getString("tag.error") +
									getConfig().getString("messages.no-permission")));
						}
					}
				} else if (args[0].equalsIgnoreCase("delete")) {
					if (sender.hasPermission("sweetpvp.admin")) {
						if (sender instanceof Player) {
							Player player = (Player) sender;
							if (args.length > 1) {
								if (getData().isSet("arena." + args[1])) {
									try {
										getData().set("arena." + args[1], null);
										saveData();
										player.sendMessage(Methods.setColors(getConfig().getString("tag.main") +
												getConfig().getString("messages.spawn-delete").replaceAll("%arena", 
														"Arena " + args[1])));
										arena[Integer.parseInt(args[1]) - 1].resetSpawn();
									} catch (Exception e) {
										player.sendMessage(Methods.setColors(getConfig().getString("tag.error") +
												getConfig().getString("messages.no-spawns-arena").replaceAll("%arena", 
														"Arena " + args[1])));
									}
								} else {
									player.sendMessage(Methods.setColors(getConfig().getString("tag.error") +
											getConfig().getString("messages.no-spawns-arena").replaceAll("%arena", 
													"Arena " + args[1])));
								}
							} else {
								player.sendMessage(Methods.setColors(getConfig().getString("tag.error") +
										"Usage: /spvp delete <arena #>"));
							}
						}
					} else {
						((Player) sender).sendMessage(Methods.setColors(getConfig().getString("tag.error") +
								getConfig().getString("messages.no-permission")));
					}
				} else if (args[0].equalsIgnoreCase("enable")) {
					if (!(sender instanceof Player)) {
						return false;
					}
					Player player = (Player) sender;
					if (sender.hasPermission("sweetpvp.staff") || sender.hasPermission("sweetpvp.admin")) {
						if (args.length > 1) {
							getData().set("arena." + args[1] + ".enabled", true);
							arena[Integer.parseInt(args[1]) - 1].enabled(true);
							saveData();
							if (sender instanceof Player) {
								player.sendMessage(Methods.setColors(getConfig().getString("tag.main") +
										getConfig().getString("messages.arena-enabled").replaceAll("%arena", "Arena " + args[1])));
							} else {
								System.out.println("Arena " + args[1] + " has been enabled!");
							}
						} else {
							if (sender instanceof Player) {
								player.sendMessage(Methods.setColors(getConfig().getString("tag.error") +
										"&cUsage: /spvp enable <arena #>"));
							} else {
								System.out.println("Usage: /spvp enable <arena #>");
							}
						}
					} else {
						((Player) sender).sendMessage(Methods.setColors(getConfig().getString("tag.error") +
								getConfig().getString("messages.no-permission")));
					}
				} else if (args[0].equalsIgnoreCase("disable")) {
					if (!(sender instanceof Player)) {
						return false;
					}
					Player player = (Player) sender;
					if (sender.hasPermission("sweetpvp.staff") || sender.hasPermission("sweetpvp.admin")) {
						if (args.length > 1) {
							getData().set("arena." + args[1] + ".enabled", false);
							arena[Integer.parseInt(args[1]) - 1].enabled(false);
							saveData();
							
							List<Player> pls = new ArrayList<Player>();
							for (Player pl : arena[Integer.parseInt(args[1]) - 1].getPlayers()) {
								pls.add(pl);
							}
							for (Player p : pls) {
								PlayerServerListener.quit(p);
								p.sendMessage(Methods.setColors(getConfig().getString("tag.main") +
										getConfig().getString("messages.disable-kick")));
							}
							pls.clear();
							
							if (sender instanceof Player) {
								player.sendMessage(Methods.setColors(getConfig().getString("tag.main") +
										getConfig().getString("messages.arena-disabled").replaceAll("%arena", "Arena " + args[1])));
							} else {
								System.out.println("Arena " + args[1] + " has been disabled!");
							}
						} else {
							if (sender instanceof Player) {
								player.sendMessage(Methods.setColors(getConfig().getString("tag.error") +
										"&cUsage: /spvp disable <arena #>"));
							} else {
								System.out.println("Usage: /spvp disable <arena #>");
							}
						}
					} else {
						((Player) sender).sendMessage(Methods.setColors(getConfig().getString("tag.error") +
								getConfig().getString("messages.no-permission")));
					}
				} else if (args[0].equals("quit")) {
					int arenaNo = -1;
					if (sender instanceof Player) {
						Player player = (Player) sender;
						for (Arena a : arena) {
							for (Player p : a.getPlayers()) {
								if (p.equals(player)) {
									arenaNo = a.getId();
								}
							}
						}
						if (arenaNo > 0) { 
							player.sendMessage(Methods.setColors(getConfig().getString("tag.main") + getConfig()
									.getString("messages.quit").replace("%arena", "Arena " + arenaNo)));
							PlayerServerListener.quit(player);
						} else {
							player.sendMessage(Methods.setColors(getConfig().getString("tag.error") + getConfig()
									.getString("messages.no-quit")));
						}
					}
				} else if (args[0].equalsIgnoreCase("lobby")) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						Location loc = new Location(player.getWorld(),
								getData().getDouble("lobbyspawn.x"), getData().getDouble("lobbyspawn.y"),
								getData().getDouble("lobbyspawn.z"));
						loc.setPitch((float) getData().getDouble("lobbyspawn.pitch"));
						loc.setYaw((float) getData().getDouble("lobbyspawn.yaw"));
						player.teleport(loc);
					}
				} else if (args[0].equalsIgnoreCase("forcestart")) {
					if (sender.hasPermission("spvp.staff") || sender.hasPermission("sweetpvp.admin")) {
						arena[Integer.parseInt(args[1]) - 1].startCount(true);
					} else {
						((Player) sender).sendMessage(Methods.setColors(getConfig().getString("tag.error") +
								getConfig().getString("messages.no-permission")));
					}
				} else {
					if (sender instanceof Player) {
						Help.show((Player) sender);
					} else {
						System.out.println("Sorry, most of the commands are only in-game available!");
					}
				}
			} else {
				if (sender instanceof Player) {
					Help.show((Player) sender);
				} else {
					System.out.println("Sorry, most of the commands are only in-game available!");
				}
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
