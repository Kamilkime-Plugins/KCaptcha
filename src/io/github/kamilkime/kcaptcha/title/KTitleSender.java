package io.github.kamilkime.kcaptcha.title;

import org.bukkit.entity.Player;

public interface KTitleSender {

	public void sendActionBar(Player p, String text, int fadeIn, int stay, int fadeOut);
	public void sendTitle(Player p, String text, String textLocation, int fadeIn, int stay, int fadeOut);
}