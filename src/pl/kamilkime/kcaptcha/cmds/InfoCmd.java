package pl.kamilkime.kcaptcha.cmds;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import pl.kamilkime.kcaptcha.data.DataManager;
import pl.kamilkime.kcaptcha.objects.VerifiedUser;
import pl.kamilkime.kcaptcha.objects.utils.StringManager;

public class InfoCmd {

	private DataManager d = DataManager.getInst();

	@SuppressWarnings("unchecked")
	public void info(CommandSender sender, Command cmd, String l, String args[]){
		if(!sender.hasPermission((String) d.getConfigContent("infoPermission"))){
			for(String s : StringManager.createCommandMessage((List<String>) d.getPlainMessage("msgNoPermission"))){
				sender.sendMessage(s);
			}
			return;
		}
		
		if(args.length < 2){
			for(String s : StringManager.createWrongSyntaxMessage((List<String>) d.getPlainMessage("msgWrongSyntax"), "/kcaptcha info <nick>")){
				sender.sendMessage(s);
			}
			return;
		}
		
		if(!((boolean) d.getConfigContent("rememberUUIDs"))){
			for(String s : StringManager.createCommandMessage((List<String>) d.getPlainMessage("msgFeatureNotEnabled"))){
				sender.sendMessage(s);
			}
			return;
		}
		
		if(VerifiedUser.get(args[1]) == null){
			for(String s : StringManager.createCommandMessage((List<String>) d.getPlainMessage("msgNoSuchPlayer"))){
				sender.sendMessage(s);
			}
			return;
		}
		
		VerifiedUser user = VerifiedUser.get(args[1]);
		for(String s : StringManager.createInfoMessage((List<String>) d.getPlainMessage("msgInfoCommand"), user)){
			sender.sendMessage(s);
		}
	}
}