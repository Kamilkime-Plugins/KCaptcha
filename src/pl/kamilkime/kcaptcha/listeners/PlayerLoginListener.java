package pl.kamilkime.kcaptcha.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import pl.kamilkime.kcaptcha.data.DataManager;
import pl.kamilkime.kcaptcha.objects.utils.VerificationUtils;

public class PlayerLoginListener implements Listener{
		
	private DataManager d = DataManager.getInst();
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onLogin(PlayerLoginEvent e){
		Player p = e.getPlayer();
		
		if(p.hasPermission((String) d.getConfigContent("bypassPermission"))) return;
		if(VerificationUtils.isValidated(p)) return;
		VerificationUtils.addNonValidated(p.getUniqueId(), VerificationUtils.generateCaptcha());
	}
}