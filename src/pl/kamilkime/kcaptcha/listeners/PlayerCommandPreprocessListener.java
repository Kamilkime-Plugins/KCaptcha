package pl.kamilkime.kcaptcha.listeners;

import java.util.Date;
import java.util.List;

import me.confuser.barapi.BarAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import pl.kamilkime.kcaptcha.data.DataManager;
import pl.kamilkime.kcaptcha.objects.VerifiedUser;
import pl.kamilkime.kcaptcha.objects.utils.VerificationUtils;

public class PlayerCommandPreprocessListener implements Listener {
	
	private DataManager d = DataManager.getInst();
	
	@SuppressWarnings("unchecked")
	@EventHandler(priority=EventPriority.MONITOR)
	public void onCommandPreprocess(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		String command = e.getMessage().replaceFirst("/", "");
		List<String> cmds = (List<String>) d.getConfigContent("cmdsBeforeCaptcha");
		
		if(e.isCancelled()) return;
		if(!VerificationUtils.isWaitingForValidation(p)) return;
		
		if(command.contains(" ")){
			String[] splitted = command.split(" ");
			if(cmds.contains(splitted[0].toLowerCase())) return;
		} else {
			if(cmds.contains(command.toLowerCase())) return;
		}
		
		String captcha = VerificationUtils.getPlayerCaptcha(p.getUniqueId());
// CAPTCHA correct =================================================================================================================================================
		if(captcha.equals(command)){
			VerificationUtils.removeNonValidated(p.getUniqueId());
			VerifiedUser.createUser(p.getUniqueId(), p.getName(), p.getAddress().getAddress().toString().replace("/", ""), new Date());
			if(((boolean) d.getConfigContent("enableChatMessage"))
					|| (!((boolean) d.getConfigContent("enableBarMessage")) && !((boolean) d.getConfigContent("enableChatMessage")))){
				for(String m : d.getChatMessage("msgCaptchaComplete", captcha)){
					p.sendMessage(m);
				}
			}
			
			if(((boolean) d.getConfigContent("enableBarMessage"))){
				BarAPI.setMessage(p, d.getBarMessage("barCaptchaComplete", captcha), (int) d.getConfigContent("bossBarTime"));
			}
		} 
// CAPTCHA incorrect =================================================================================================================================================
		else {
			if(((boolean) d.getConfigContent("enableChatMessage"))
					|| (!((boolean) d.getConfigContent("enableBarMessage")) && !((boolean) d.getConfigContent("enableChatMessage")))){
				for(String m : d.getChatMessage("msgYouHaveToCompleteCaptcha", captcha)){
					p.sendMessage(m);
				}
			}
			
			if(((boolean) d.getConfigContent("enableBarMessage"))){
				BarAPI.setMessage(p, d.getBarMessage("barYouHaveToCompleteCaptcha", captcha), (int) d.getConfigContent("bossBarTime"));
			}
		}
		
		e.setCancelled(true);
	}
}