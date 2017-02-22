package io.github.kamilkime.kcaptcha.cmds.sub;

import org.bukkit.command.CommandSender;

import io.github.kamilkime.kcaptcha.cmds.KCommand;
import io.github.kamilkime.kcaptcha.data.DataManager;

public class HelpCmd implements KCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		for(String msg : DataManager.getInst().mainMessageHelpCommand) {
			sender.sendMessage(msg);
		}
	}
}