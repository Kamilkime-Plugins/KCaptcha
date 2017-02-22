package io.github.kamilkime.kcaptcha.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.kamilkime.kcaptcha.data.DataManager;
import io.github.kamilkime.kcaptcha.user.User;
import io.github.kamilkime.kcaptcha.user.UserUtils;
import io.github.kamilkime.kcaptcha.utils.MessagingUtils;
import io.github.kamilkime.kcaptcha.utils.StringUtils;

public class PlayerJoinListener implements Listener {

	@EventHandler(priority=EventPriority.MONITOR)
	public void onEvent(PlayerJoinEvent e) {
		if(UserUtils.needsVerification(e.getPlayer())) {
			DataManager.getNonVerifiedList().put(e.getPlayer().getUniqueId(), StringUtils.generateCaptcha());
			MessagingUtils.sendMessageComplete(e.getPlayer());
		} else {
			User u = User.get(e.getPlayer().getUniqueId());
			if(u !=null) u.update(e.getPlayer());
		}
	}
}