package nl.SugCube.SweetPvP.Listeners;

import nl.SugCube.SweetPvP.Main.Methods;
import nl.SugCube.SweetPvP.Main.Powers;
import nl.SugCube.SweetPvP.Main.Signs;
import nl.SugCube.SweetPvP.Main.SweetPvP;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SignListener implements Listener {
	
	public static SweetPvP plugin;
	
	public SignListener(SweetPvP instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		if (event.getBlock().getTypeId() == 63 || event.getBlock().getTypeId() == 68) {
			Sign sign = (Sign) event.getBlock().getState();
			if (Signs.signs.contains(sign)) {
				Signs.unregisterSign(sign);
				Signs.updateSigns();
			}
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = event.getClickedBlock();
			if (b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST) {
				Sign sign = (Sign) b.getState();
					
				if (sign.getLine(0).equalsIgnoreCase("[PvP]")) {
				
					if (player.getItemInHand().getType() == Material.AIR) {
						
						Signs.registerSign(sign);
						
						String[] arenaNo = sign.getLine(1).split(" ");
						if (plugin.arena[Integer.parseInt(arenaNo[2]) - 1].getEnabled() &&
								plugin.arena[Integer.parseInt(arenaNo[2]) - 1].getArenaSpawns() > 
								plugin.arena[Integer.parseInt(arenaNo[2]) - 1].getPlayers().size() &&
								plugin.arena[Integer.parseInt(arenaNo[2]) - 1].getArenaSpawns() != 1) {
							/*
							 * POWER
							 */
							if (sign.getLine(3).contains("Power ")) {
								String power[] = sign.getLine(3).split(" ");
								if (Powers.setPower(player, power[1])) {
									Powers.setPower(player, power[1]);
									Methods.setInGame(player, "bambieknoodles");
									String[] arenaNo1 = sign.getLine(1).split(" ");
									Methods.spawnPlayer(player, Integer.parseInt(arenaNo1[2]) - 1);
									plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].addPlayer(player);
									
									if (plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].getPlayers().size() ==
											plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].getArenaSpawns()) {
										plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].startCount(true);
									}
									
									if (Powers.powerStrength.contains(player)) {
										player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999999, 0));
									}
		
									for (Player pl : plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].getPlayers()) {
										pl.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.main") +
											plugin.getConfig().getString("messages.join-broadcast").replace(
											"%player", event.getPlayer().getDisplayName()).replace("%amount",
											plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].getPlayers().size() + "")
											.replace("%max", "" + plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].getArenaSpawns())));
									}
									
									player.updateInventory();
									player.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.main") +
											plugin.getConfig().getString("messages.join-arena").replaceAll("%arena", getArena(sign))
													.replaceAll("%extra", getExtra(sign))));
								}
							} else if (sign.getLine(3).contains("Kit ")) {
								/*
								 * KIT
								 */
								String kit[] = sign.getLine(3).split(" ");
								if (player.hasPermission("sweetpvp.kit." + kit[1])) {
									Methods.setInGame(player, kit[1]);
									String[] arenaNo1 = sign.getLine(1).split(" ");
									Methods.spawnPlayer(player, Integer.parseInt(arenaNo1[2]) - 1);
									plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].addPlayer(player);
	
									if (plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].getPlayers().size() ==
											plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].getArenaSpawns()) {
										plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].startCount(true);
									}
									
									for (Player pl : plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].getPlayers()) {
										pl.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.main") +
											plugin.getConfig().getString("messages.join-broadcast").replace(
											"%player", event.getPlayer().getDisplayName()).replace("%amount",
										plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].getPlayers().size() + "")
											.replace("%max", "" + plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].getArenaSpawns())));
									}

									player.updateInventory();
									player.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.main") +
											plugin.getConfig().getString("messages.join-arena").replaceAll("%arena", getArena(sign))
													.replaceAll("%extra", getExtra(sign))));
								} else {
									player.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.error") +
											plugin.getConfig().getString("messages.no-permission")));
								}
							} else {
								Methods.setInGame(player, "bambieknoodles");
								/*
								 * FIST
								 */
								String[] arenaNo1 = sign.getLine(1).split(" ");
								Methods.spawnPlayer(player, Integer.parseInt(arenaNo1[2]) - 1);
								plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].addPlayer(player);
	
								if (plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].getPlayers().size() ==
										plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].getArenaSpawns()) {
									plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].startCount(true);
								}
								
								for (Player pl : plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].getPlayers()) {
									pl.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.main") +
										plugin.getConfig().getString("messages.join-broadcast").replace(
										"%player", event.getPlayer().getDisplayName()).replace("%amount",
										plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].getPlayers().size() + "")
										.replace("%max", "" + plugin.arena[Integer.parseInt(arenaNo1[2]) - 1].getArenaSpawns())));
								}
								
								player.updateInventory();
								player.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.main") +
										plugin.getConfig().getString("messages.no-kit-or-power").replaceAll("%arena", getArena(sign))));
							}
						}
					}
				} else {
					player.sendMessage(Methods.setColors(plugin.getConfig().getString("tag.error") +
							plugin.getConfig().getString("messages.empty-hand")));
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onSignCreate(SignChangeEvent event) {
		if (event.getLine(0).equalsIgnoreCase("[PvP]")) {
			if (event.getLine(1).contains("Join Arena ")) {
				if (event.getLine(2).isEmpty()) {
					if (event.getLine(3).contains("Power ") || event.getLine(3).contains("Kit ") || event.getLine(3).isEmpty()) {
						event.getPlayer().sendMessage(Methods.setColors(
								plugin.getConfig().getString("tag.main") +
								plugin.getConfig().getString("messages.success-sign")));
						
						Sign sign = (Sign) event.getBlock().getState();
						sign.setLine(0, event.getLine(0));
						sign.setLine(1, event.getLine(1));
						sign.setLine(2, event.getLine(2));
						sign.setLine(3, event.getLine(3));
						sign.update();
						Signs.registerSign(sign);
						
					} else {
						event.getPlayer().sendMessage(Methods.setColors(
								plugin.getConfig().getString("tag.error") +
								plugin.getConfig().getString("messages.wrong-sign") +
								": Line 4"));
						event.setLine(0, ChatColor.DARK_RED + event.getLine(0));
					}
				} else {
					event.getPlayer().sendMessage(Methods.setColors(
							plugin.getConfig().getString("tag.error") +
							plugin.getConfig().getString("messages.wrong-sign") +
							": Line 3"));
					event.setLine(0, ChatColor.DARK_RED + event.getLine(0));
				}
			} else {
				event.getPlayer().sendMessage(Methods.setColors(
						plugin.getConfig().getString("tag.error") +
						plugin.getConfig().getString("messages.wrong-sign") +
						": Line 2"));
				event.setLine(0, ChatColor.DARK_RED + event.getLine(0));
			}
		}
	}
	
	public String getArena(Sign sign) {
		return sign.getLine(1).replaceAll("Join ", "");
	}
	
	public String getExtra(Sign sign) {
		String[] string = sign.getLine(3).split(" ");
		return string[1] + " " + string[0];
	}
}
