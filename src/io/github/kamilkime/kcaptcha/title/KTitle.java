package io.github.kamilkime.kcaptcha.title;

import org.bukkit.entity.Player;

import io.github.kamilkime.kcaptcha.Main;
import io.github.kamilkime.kcaptcha.enums.PreparationResult;

public class KTitle {

	public static PreparationResult prepareAPI() {
		if(!KTitleUtils.findClass()) return PreparationResult.WRONG_VERSION;
		if(!Main.checkForPLib()) return PreparationResult.MISSING_PLIB;
		return PreparationResult.ALL_OK;
	}
	
	public static void sendTitle(Player p, String text, String textLocation, int fadeIn, int stay, int fadeOut) {
		KTitleUtils.getTitleSender().sendTitle(p, text, textLocation, fadeIn, stay, fadeOut);
	}
	
	public static void sendActionBar(Player p, String text, int fadeIn, int stay, int fadeOut) {
		KTitleUtils.getTitleSender().sendActionBar(p, text, fadeIn, stay, fadeOut);
	}
}