package io.github.kamilkime.kcaptcha.cmds.sub;

import org.bukkit.command.CommandSender;

import io.github.kamilkime.kcaptcha.cmds.KCommand;
import io.github.kamilkime.kcaptcha.data.DataManager;

public class ReloadCmd implements KCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!sender.hasPermission("kcaptcha.reload")) {
			for(String msg : DataManager.getInst().mainMessageNoPermission) {
				sender.sendMessage(msg);
			}
			return;
		}
		
		DataManager.getInst().loadConfig();
		for(String msg : DataManager.getInst().mainMessageReloadCommand) {
			sender.sendMessage(msg);
		}
	}
}