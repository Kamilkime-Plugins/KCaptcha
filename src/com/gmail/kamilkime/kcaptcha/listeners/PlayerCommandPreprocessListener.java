package com.gmail.kamilkime.kcaptcha.listeners;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.gmail.kamilkime.kcaptcha.data.Settings;
import com.gmail.kamilkime.kcaptcha.data.StringUtils;
import com.gmail.kamilkime.kcaptcha.objects.VerifiedUser;
import com.gmail.kamilkime.kcaptcha.objects.utils.VerificationUtils;
import com.gmail.kamilkime.kcaptcha.title.TitleUtils;

import me.confuser.barapi.BarAPI;

public class PlayerCommandPreprocessListener implements Listener {
	
	private Settings set = Settings.getInst();
	
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
	public void onCommandPreprocess(PlayerCommandPreprocessEvent e) {
		boolean anythingSent = false;
		Player p = e.getPlayer();
		String command = e.getMessage().replaceFirst("/", "");
		if(!VerificationUtils.isWaitingForValidation(p)) return;
		if(command.contains(" ")){
			String[] splitted = command.split(" ");
			if(set.cmdsBeforeCaptcha.contains(splitted[0].toLowerCase())) return;
		} else {
			if(set.cmdsBeforeCaptcha.contains(command.toLowerCase())) return;
		}
		
		String captcha = VerificationUtils.getPlayerCaptcha(p.getUniqueId());
// CAPTCHA correct =================================================================================================================================================
		if(captcha.equals(command)){
			VerificationUtils.removeNonValidated(p.getUniqueId());
			VerifiedUser.createUser(p.getUniqueId(), p.getName(), p.getAddress().getAddress().toString().replace("/", ""), new Date());
			if(set.enableChatMessage){
				for(String m : StringUtils.createChatMessage(set.msgCaptchaComplete, captcha)) p.sendMessage(m);
				anythingSent = true;
			}
			if(set.enableBarMessage){
				BarAPI.removeBar(p);
				BarAPI.setMessage(p, StringUtils.createBarMessage(set.barCaptchaComplete, captcha), set.bossBarTime);
				anythingSent = true;
			}
			if(TitleUtils.canUseTitles()){
				if(set.enableTitle){
					TitleUtils.sendTitlePacket(p, TitleUtils.createTitlePacket(captcha, "TITLE", set.titleCaptchaComplete,
							set.titleFadeIn*20, set.titleStay*20, set.titleFadeOut*20));
					if(set.enableSubtitle){
						TitleUtils.sendTitlePacket(p, TitleUtils.createTitlePacket(captcha, "SUBTITLE", set.subtitleCaptchaComplete,
								set.subtitleFadeIn*20, set.subtitleStay*20, set.subtitleFadeOut));
					}
					anythingSent = true;
				}
			}
			if(!anythingSent) for(String m : StringUtils.createChatMessage(set.msgCaptchaComplete, captcha)) p.sendMessage(m);
		} 
// CAPTCHA incorrect =================================================================================================================================================
		else {
			if(set.enableChatMessage){
				for(String m : StringUtils.createChatMessage(set.msgYouHaveToCompleteCaptcha, captcha)) p.sendMessage(m);
				anythingSent = true;
			}
			if(set.enableBarMessage){
				BarAPI.removeBar(p);
				BarAPI.setMessage(p, StringUtils.createBarMessage(set.barYouHaveToCompleteCaptcha, captcha), set.bossBarTime);
				anythingSent = true;
			}
			if(TitleUtils.canUseTitles()){
				if(set.enableTitle){
					TitleUtils.sendTitlePacket(p, TitleUtils.createTitlePacket(captcha, "TITLE", set.titleYouHaveToCompleteCaptcha,
							set.titleFadeIn*20, set.titleStay*20, set.titleFadeOut*20));
					if(set.enableSubtitle){
						TitleUtils.sendTitlePacket(p, TitleUtils.createTitlePacket(captcha, "SUBTITLE", set.subtitleYouHaveToCompleteCaptcha,
								set.subtitleFadeIn*20, set.subtitleStay*20, set.subtitleFadeOut));
					}
					anythingSent = true;
				}
			}
			if(!anythingSent) for(String m : StringUtils.createChatMessage(set.msgYouHaveToCompleteCaptcha, captcha)) p.sendMessage(m);
		}
		e.setCancelled(true);
	}
}