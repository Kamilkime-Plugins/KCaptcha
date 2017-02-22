package io.github.kamilkime.kcaptcha.bossbar;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class KBossBarListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEvent(PlayerQuitEvent e) {
		KBossBar.clearBar(e.getPlayer());
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEvent(PlayerKickEvent e) {
		KBossBar.clearBar(e.getPlayer());
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEvent(PlayerTeleportEvent e) {
		KBossBar.teleportDragon(e.getPlayer(), KBossBarUtils.getDragonLocation(e.getTo()));
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEvent(PlayerRespawnEvent e) {
		KBossBar.teleportDragon(e.getPlayer(), KBossBarUtils.getDragonLocation(e.getRespawnLocation()));
	}
}