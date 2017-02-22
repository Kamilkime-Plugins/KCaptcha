package io.github.kamilkime.kcaptcha.cmds.sub;

import org.bukkit.command.CommandSender;

import io.github.kamilkime.kcaptcha.cmds.KCommand;
import io.github.kamilkime.kcaptcha.data.DataManager;
import io.github.kamilkime.kcaptcha.user.User;

public class InfoCmd implements KCommand {
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!sender.hasPermission("kcaptcha.info")){
			for(String msg : DataManager.getInst().mainMessageNoPermission) {
				sender.sendMessage(msg);
			}
			return;
		}
		
		if(args.length < 2){
			for(String msg : DataManager.getInst().mainMessageWrongSyntax) {
				sender.sendMessage(msg.replace("{COMMAND}", "/kcaptcha info <nick>"));
			}
			return;
		}
		
		if(!DataManager.getInst().rememberUUIDs){
			for(String msg : DataManager.getInst().mainMessageFeatureNotEnabled) {
				sender.sendMessage(msg);
			}
			return;
		}
		
		if(User.get(args[1]) == null){
			for(String msg : DataManager.getInst().mainMessageNoSuchPlayer) {
				sender.sendMessage(msg);
			}
			return;
		}
		
		User u = User.get(args[1]);
		for(String msg : DataManager.getInst().mainMessageInfoCommand) {
			sender.sendMessage(msg.replace("{UUID}", u.getUUID().toString()).replace("{NAME}", u.getName()).replace("{IP}", u.getIp())
					.replace("{DATE}", DataManager.getInst().dateFormat.format(u.getVerificationDate()))
					.replace("{EXPIRATION}", DataManager.getInst().dateFormat.format(u.getVerificationDate() + DataManager.getInst().forceRevalidationEvery*24*3600*1000)));
		}
	}
}