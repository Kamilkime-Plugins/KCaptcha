package io.github.kamilkime.kcaptcha.cmds.sub;

import org.bukkit.command.CommandSender;

import io.github.kamilkime.kcaptcha.Main;
import io.github.kamilkime.kcaptcha.cmds.KCommand;
import io.github.kamilkime.kcaptcha.utils.StringUtils;

public class VersionCmd implements KCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		sender.sendMessage(StringUtils.color("                       &8&l--------------------"));
		sender.sendMessage(StringUtils.color("                     &7&lKCaptcha v" + Main.getInst().getDescription().getVersion() + " &6&lby Kamilkime"));
		sender.sendMessage(StringUtils.color("                        &a&lhttps://goo.gl/BLqvns"));
		sender.sendMessage(StringUtils.color("             &6&lFor plugin help type &7&l/kcaptcha help"));
		sender.sendMessage(StringUtils.color("                       &8&l--------------------"));
	}
}