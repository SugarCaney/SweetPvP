package nl.SugCube.SweetPvP.Listeners;

import java.util.Random;

import nl.SugCube.SweetPvP.Main.Powers;
import nl.SugCube.SweetPvP.Main.SweetPvP;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FightListener implements Listener {
	
	public static SweetPvP plugin;
	Random ran = new Random();
	
	public FightListener(SweetPvP instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof LivingEntity) {
			if (e.getDamager() instanceof Player) {
				LivingEntity lentity = (LivingEntity) e.getEntity();
				Player damager = (Player) e.getDamager();
				
				if (Powers.powerBlindness.contains(damager)) {
					if (ran.nextInt(6) == 0) {
						lentity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 5));
					}
				} else if (Powers.powerDisorient.contains(damager)) {
					if (ran.nextInt(6) == 0) {
						Location loc = lentity.getLocation();
						loc.setYaw(loc.getYaw() + ran.nextInt(180) + 90);
						lentity.teleport(loc);
					}
				} else if (Powers.powerDrain.contains(damager)) {
					if (ran.nextInt(6) == 0) {
						damager.setHealth((damager.getHealth() + 1 > 20 ? 20 : damager.getHealth() + 1));
					}
				} else if (Powers.powerFire.contains(damager)) {
					if (ran.nextInt(6) == 0) {
						lentity.setFireTicks(100);
					}
				} else if (Powers.powerNausea.contains(damager)) {
					if (ran.nextInt(6) == 0) {
						lentity.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 5));
					}
				} else if (Powers.powerPoison.contains(damager)) {
					if (ran.nextInt(6) == 0) {
						lentity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 120, 0));
					}
				} else if (Powers.powerSlowness.contains(damager)) {
					if (ran.nextInt(6) == 0) {
						lentity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 0));
					}
				} else if (Powers.powerStrength.contains(damager)) {
					e.setDamage(e.getDamage() - 2);
				}
			}
		}
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (Powers.powerInvisibility.contains(p)) {
				if (ran.nextInt(6) == 0) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 120, 0));
				}
			}
		}
	}

}
