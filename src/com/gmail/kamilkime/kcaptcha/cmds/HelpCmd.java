package com.gmail.kamilkime.kcaptcha.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.gmail.kamilkime.kcaptcha.data.Settings;

public class HelpCmd {
	
	private Settings set = Settings.getInst();

	public void help(CommandSender sender, Command cmd, String l, String args[]){
		for(String s : set.msgHelpCommand){
			sender.sendMessage(s);
		}
	}
}