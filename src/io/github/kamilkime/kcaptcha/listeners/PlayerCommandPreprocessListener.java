package io.github.kamilkime.kcaptcha.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import io.github.kamilkime.kcaptcha.data.DataManager;
import io.github.kamilkime.kcaptcha.enums.BlockedAction;
import io.github.kamilkime.kcaptcha.user.User;
import io.github.kamilkime.kcaptcha.utils.MessagingUtils;

public class PlayerCommandPreprocessListener implements Listener {

	@EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
	public void onEvent(PlayerCommandPreprocessEvent e) {
		if(!DataManager.getNonVerifiedList().containsKey(e.getPlayer().getUniqueId())) return;
		
		String command = e.getMessage().replace("/", "");
		if(command.contains(" ")) {
			if(DataManager.getInst().cmdsBeforeCaptcha.contains(command.split(" ")[0].toLowerCase())) return;
		} else {
			if(DataManager.getInst().cmdsBeforeCaptcha.contains(command.toLowerCase())) return;
		}
		
		if(DataManager.getInst().blockedActions.contains(BlockedAction.COMMAND)) e.setCancelled(true);
		
		if(command.equals(DataManager.getNonVerifiedList().get(e.getPlayer().getUniqueId()))) {
			new User(e.getPlayer().getUniqueId(), e.getPlayer().getName(), e.getPlayer().getAddress().getHostString(), System.currentTimeMillis());
			DataManager.getNonVerifiedList().remove(e.getPlayer().getUniqueId());
			MessagingUtils.sendMessageCompleted(e.getPlayer());
		} else {
			MessagingUtils.sendMessageComplete(e.getPlayer());
		}
	}
}