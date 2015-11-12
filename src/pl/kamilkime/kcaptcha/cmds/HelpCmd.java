package pl.kamilkime.kcaptcha.cmds;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import pl.kamilkime.kcaptcha.data.DataManager;
import pl.kamilkime.kcaptcha.objects.utils.StringManager;

public class HelpCmd {
	
	private DataManager d = DataManager.getInst();

	@SuppressWarnings("unchecked")
	public void help(CommandSender sender, Command cmd, String l, String args[]){
		for(String s : StringManager.createCommandMessage((List<String>) d.getPlainMessage("msgHelpCommand"))){
			sender.sendMessage(s);
		}
	}
}