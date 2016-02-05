package com.gmail.kamilkime.kcaptcha.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.gmail.kamilkime.kcaptcha.data.Settings;

public class ReloadCmd {

	private Settings set = Settings.getInst();

	public void reload(CommandSender sender, Command cmd, String l, String args[]){
		if(!sender.hasPermission(set.reloadPermission)){
			for(String s : set.msgNoPermission) sender.sendMessage(s);
			return;
		}
		set.reload();
		for(String s : set.msgReloadCommand) sender.sendMessage(s);
	}
}