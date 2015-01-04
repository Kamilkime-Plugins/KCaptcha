package me.kamilkime.KCaptcha;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Cmds implements CommandExecutor{
	
	public Cmds(Main plugin){}
	
	public boolean onCommand(CommandSender sender, Command cmd, String l, String args[]){
		if(cmd.getName().equalsIgnoreCase("kcaptcha")){
			if(sender.hasPermission("kcaptcha.reload")){
				FileDataManager.reloadCfg();
				sender.sendMessage("§6§l[§a§lKCaptcha§6§l] §b§lConfig reloaded!");
}
}
		return false;
}
}