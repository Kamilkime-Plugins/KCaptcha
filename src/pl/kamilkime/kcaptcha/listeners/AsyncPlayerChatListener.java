package pl.kamilkime.kcaptcha.listeners;

import java.util.Date;

import me.confuser.barapi.BarAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import pl.kamilkime.kcaptcha.data.DataManager;
import pl.kamilkime.kcaptcha.objects.VerifiedUser;
import pl.kamilkime.kcaptcha.objects.utils.VerificationUtils;
import pl.kamilkime.kcaptcha.title.TitleUtils;

public class AsyncPlayerChatListener implements Listener {

	private DataManager d = DataManager.getInst();
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if(e.isCancelled()) return;
		if(!VerificationUtils.isWaitingForValidation(p)) return;
		
		
		String captcha = VerificationUtils.getPlayerCaptcha(p.getUniqueId());
// CAPTCHA correct =================================================================================================================================================
		if(captcha.equals(e.getMessage())){
			VerificationUtils.removeNonValidated(p.getUniqueId());
			VerifiedUser.createUser(p.getUniqueId(), p.getName(), p.getAddress().getAddress().toString().replace("/", ""), new Date());
			if(((boolean) d.getConfigContent("enableChatMessage"))
					|| (!((boolean) d.getConfigContent("enableBarMessage")) && !((boolean) d.getConfigContent("enableChatMessage")))){
				for(String m : d.getChatMessage("msgCaptchaComplete", captcha)){
					p.sendMessage(m);
				}
			}
			
			if(((boolean) d.getConfigContent("enableBarMessage"))){
				BarAPI.removeBar(p);
				BarAPI.setMessage(p, d.getBarMessage("barCaptchaComplete", captcha), (int) d.getConfigContent("bossBarTime"));
			}
			
			if(TitleUtils.canUseTitles()){
				if(((boolean) d.getConfigContent("enableTitle"))){
					TitleUtils.sendTitlePacket(p, TitleUtils.createTitlePacket(captcha, "TITLE", (String) d.getPlainMessage("titleCaptchaComplete"),
							((int) d.getConfigContent("titleFadeIn"))*20, ((int) d.getConfigContent("titleStay"))*20, ((int) d.getConfigContent("titleFadeOut"))*20));
				}
				
				if(((boolean) d.getConfigContent("enableSubtitle"))){
					TitleUtils.sendTitlePacket(p, TitleUtils.createTitlePacket(captcha, "SUBTITLE", (String) d.getPlainMessage("subtitleCaptchaComplete"),
							((int) d.getConfigContent("subtitleFadeIn"))*20, ((int) d.getConfigContent("subtitleStay"))*20, ((int) d.getConfigContent("subtitleFadeOut"))));
				}
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
				BarAPI.removeBar(p);
				BarAPI.setMessage(p, d.getBarMessage("barYouHaveToCompleteCaptcha", captcha), (int) d.getConfigContent("bossBarTime"));
			}
			
			if(TitleUtils.canUseTitles()){
				if(((boolean) d.getConfigContent("enableTitle"))){
					TitleUtils.sendTitlePacket(p, TitleUtils.createTitlePacket(captcha, "TITLE", (String) d.getPlainMessage("titleYouHaveToCompleteCaptcha"),
							((int) d.getConfigContent("titleFadeIn"))*20, ((int) d.getConfigContent("titleStay"))*20, ((int) d.getConfigContent("titleFadeOut"))*20));
				}
				
				if(((boolean) d.getConfigContent("enableSubtitle"))){
					TitleUtils.sendTitlePacket(p, TitleUtils.createTitlePacket(captcha, "SUBTITLE", (String) d.getPlainMessage("subtitleYouHaveToCompleteCaptcha"),
							((int) d.getConfigContent("subtitleFadeIn"))*20, ((int) d.getConfigContent("subtitleStay"))*20, ((int) d.getConfigContent("subtitleFadeOut"))));
				}
			}
		}
		
		e.setCancelled(true);
	}
}