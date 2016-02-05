package com.gmail.kamilkime.kcaptcha.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.gmail.kamilkime.kcaptcha.data.Settings;
import com.gmail.kamilkime.kcaptcha.data.StringUtils;
import com.gmail.kamilkime.kcaptcha.objects.VerifiedUser;

public class InfoCmd {

	private Settings set = Settings.getInst();

	public void info(CommandSender sender, Command cmd, String l, String args[]){
		if(!sender.hasPermission(set.infoPermission)){
			for(String s : set.msgNoPermission) sender.sendMessage(s);
			return;
		}
		if(args.length < 2){
			for(String s : StringUtils.createWrongSyntaxMessage(set.msgWrongSyntax, "/kcaptcha info <nick>")) sender.sendMessage(s);
			return;
		}
		if(!set.rememberUUIDs){
			for(String s : set.msgFeatureNotEnabled) sender.sendMessage(s);
			return;
		}
		if(VerifiedUser.get(args[1]) == null){
			for(String s : set.msgNoSuchPlayer) sender.sendMessage(s);
			return;
		}
		
		VerifiedUser user = VerifiedUser.get(args[1]);
		for(String s : StringUtils.createInfoMessage(set.msgInfoCommand, user)) sender.sendMessage(s);
	}
}