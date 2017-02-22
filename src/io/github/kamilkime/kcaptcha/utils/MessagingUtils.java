package io.github.kamilkime.kcaptcha.utils;

import org.bukkit.entity.Player;

import io.github.kamilkime.kcaptcha.bossbar.KBossBar;
import io.github.kamilkime.kcaptcha.data.DataManager;
import io.github.kamilkime.kcaptcha.enums.TitleFunction;
import io.github.kamilkime.kcaptcha.title.KTitle;

public class MessagingUtils {

	private static DataManager d = DataManager.getInst();
	
	public static void sendMessageComplete(Player p) {
		String c = DataManager.getNonVerifiedList().get(p.getUniqueId());
		if(d.enableChatMessage) {
			for(String msg : d.chatMessageCompleteCaptcha) {
				p.sendMessage(msg.replace("{CAPTCHA}", c).replace("{CODE}", c));
			}
		}
		
		if(d.enableKBossBar) {
			KBossBar.sendBar(p, d.barMessageCompleteCaptcha.replace("{CAPTCHA}", c).replace("{CODE}", c), d.barStartPercent, d.barColor, d.barStyle, d.barDisplayTime, d.barShorten);
		}
		
		if(d.enableKTitle) {
			if(d.enabledFunctions.contains(TitleFunction.TITLE)) {
				KTitle.sendTitle(p, d.titleMessageCompleteCaptcha.replace("{CAPTCHA}", c).replace("{CODE}", c), "TITLE", d.fadeProperties.get(0)*20, d.fadeProperties.get(1)*20, d.fadeProperties.get(2)*20);
			}
			if(d.enabledFunctions.contains(TitleFunction.SUBTITLE)) {
				KTitle.sendTitle(p, d.subtitleMessageCompleteCaptcha.replace("{CAPTCHA}", c).replace("{CODE}", c), "SUBTITLE", d.fadeProperties.get(0)*20, d.fadeProperties.get(1)*20, d.fadeProperties.get(2)*20);
			}
			if(d.enabledFunctions.contains(TitleFunction.ACTIONBAR)) {
				KTitle.sendActionBar(p, d.actionbarMessageCompleteCaptcha.replace("{CAPTCHA}", c).replace("{CODE}", c), d.fadeProperties.get(0)*20, d.fadeProperties.get(1)*20, d.fadeProperties.get(2)*20);
			}
		}
	}
	
	public static void sendMessageCompleted(Player p) {
		if(d.enableChatMessage) {
			for(String msg : d.chatMessageCaptchaCompleted) {
				p.sendMessage(msg);
			}
		}
		
		if(d.enableKBossBar) {
			KBossBar.sendBar(p, d.barMessageCaptchaCompleted, d.barStartPercent, d.barColor, d.barStyle, d.barDisplayTime, d.barShorten);
		}
		
		if(d.enableKTitle) {
			if(d.enabledFunctions.contains(TitleFunction.TITLE)) {
				KTitle.sendTitle(p, d.titleMessageCaptchaCompleted, "TITLE", d.fadeProperties.get(0)*20, d.fadeProperties.get(1)*20, d.fadeProperties.get(2)*20);
			}
			if(d.enabledFunctions.contains(TitleFunction.SUBTITLE)) {
				KTitle.sendTitle(p, d.subtitleMessageCaptchaCompleted, "SUBTITLE", d.fadeProperties.get(0)*20, d.fadeProperties.get(1)*20, d.fadeProperties.get(2)*20);
			}
			if(d.enabledFunctions.contains(TitleFunction.ACTIONBAR)) {
				KTitle.sendActionBar(p, d.actionbarMessageCaptchaCompleted, d.fadeProperties.get(0)*20, d.fadeProperties.get(1)*20, d.fadeProperties.get(2)*20);
			}
		}
	}
}