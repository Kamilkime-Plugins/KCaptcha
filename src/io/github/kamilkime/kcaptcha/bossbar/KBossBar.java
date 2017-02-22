package io.github.kamilkime.kcaptcha.bossbar;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.kamilkime.kcaptcha.Main;
import io.github.kamilkime.kcaptcha.enums.PreparationResult;

public class KBossBar {

	private static Map<UUID, KBoss> activeBars = new HashMap<UUID, KBoss>();
	private static Map<UUID, Integer> activeTimers = new HashMap<UUID, Integer>();
	
	public static PreparationResult prepareAPI() {
		if(!KBossBarUtils.findClass()) return PreparationResult.WRONG_VERSION;
		if(!Main.checkForPLib()) return PreparationResult.MISSING_PLIB;
		
		KBossBarUtils.createConstructor();
		Bukkit.getScheduler().runTaskTimer(Main.getInst(), new Runnable() {
			public void run() {
				for(UUID u : activeBars.keySet()) {
					Player p = Bukkit.getPlayer(u);
					teleportDragon(p, KBossBarUtils.getDragonLocation(p.getLocation()));
				}
			}
		}, 0L, 5L);
		
		return PreparationResult.ALL_OK;
	}
	
	public static void closeAPI() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			clearBar(p);
		}
		
		activeBars.clear();
		activeTimers.clear();
	}
	
	public static void sendBar(final Player p, String name, float startPercent, Object color, Object style, int seconds, boolean cutBar) {
		clearBar(p);
		final KBoss b = KBossBarUtils.newBoss(checkName(name), KBossBarUtils.getDragonLocation(p.getLocation()), startPercent, color, style);
		
		b.sendSpawnPacket(p);
		b.sendMetaPacket(p);
		
		if(cutBar) {
			final float toSubtract = b.hp / seconds / 20;
			activeTimers.put(p.getUniqueId(), Bukkit.getScheduler().runTaskTimer(Main.getInst(), new Runnable() {
				public void run() {
					b.hp -= toSubtract;
					if(b.hp <= 1) {
						clearBar(p);
					} else {
						b.sendMetaPacket(p);
					}
				}
			}, 1L, 1L).getTaskId());
		} else {
			activeTimers.put(p.getUniqueId(), Bukkit.getScheduler().runTaskLater(Main.getInst(), new Runnable() {
				public void run() {
					clearBar(p);
				}
			}, seconds*20L).getTaskId());
		}
		
		activeBars.put(p.getUniqueId(), b);
	}
	
	public static void teleportDragon(final Player p, final Location to) {
		if(!hasBar(p)) return;
		Bukkit.getScheduler().runTaskLater(Main.getInst(), new Runnable() {
			public void run() {
				if(!hasBar(p)) return;
				KBoss b = activeBars.get(p.getUniqueId());
				b.sendTeleportPacket(p, to);
			}
		}, 2L);
	}
	
	public static boolean hasBar(Player p) {
		return activeBars.containsKey(p.getUniqueId());
	}
	
	public static void clearBar(Player p) {
		if(!hasBar(p)) return;
		
		KBoss b = activeBars.remove(p.getUniqueId());
		b.sendDestroyPacket(p);
		
		cancelTimer(p);
	}
	
	public static void cancelTimer(Player p) {
		if(!activeTimers.containsKey(p.getUniqueId())) return;
		Bukkit.getScheduler().cancelTask(activeTimers.remove(p.getUniqueId()));
	}
	
	private static String checkName(String name) {
		if(name.length() <= 64) return name;
		return name.substring(0, 64);
	}
}