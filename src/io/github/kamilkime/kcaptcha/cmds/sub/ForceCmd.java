package io.github.kamilkime.kcaptcha.cmds.sub;

import org.bukkit.command.CommandSender;

import io.github.kamilkime.kcaptcha.cmds.KCommand;
import io.github.kamilkime.kcaptcha.data.DataManager;
import io.github.kamilkime.kcaptcha.user.User;

public class ForceCmd implements KCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!sender.hasPermission("kcaptcha.force")){
			for(String msg : DataManager.getInst().mainMessageNoPermission) {
				sender.sendMessage(msg);
			}
			return;
		}
		
		if(args.length < 2){
			for(String msg : DataManager.getInst().mainMessageWrongSyntax) {
				sender.sendMessage(msg.replace("{COMMAND}", "/kcaptcha force <nick>"));
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
		u.delete();
		for(String msg : DataManager.getInst().mainMessageForceCommand) {
			sender.sendMessage(msg.replace("{NAME}", u.getName()));
		}
	}
}