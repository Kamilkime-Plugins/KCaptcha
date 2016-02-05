package com.gmail.kamilkime.kcaptcha.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.gmail.kamilkime.kcaptcha.data.Settings;
import com.gmail.kamilkime.kcaptcha.objects.utils.VerificationUtils;

public class PlayerLoginListener implements Listener{
		
	private Settings set = Settings.getInst();
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onLogin(PlayerLoginEvent e){
		Player p = e.getPlayer();
		if(p.hasPermission(set.bypassPermission)) return;
		if(VerificationUtils.isValidated(p) && !VerificationUtils.needsRevalidation(p)) return;
		VerificationUtils.addNonValidated(p.getUniqueId(), VerificationUtils.generateCaptcha());
	}
}