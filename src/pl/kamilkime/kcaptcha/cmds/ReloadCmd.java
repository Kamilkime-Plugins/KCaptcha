package pl.kamilkime.kcaptcha.cmds;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import pl.kamilkime.kcaptcha.data.DataManager;
import pl.kamilkime.kcaptcha.objects.utils.StringManager;

public class ReloadCmd {

	private DataManager d = DataManager.getInst();

	@SuppressWarnings("unchecked")
	public void reload(CommandSender sender, Command cmd, String l, String args[]){
		if(!sender.hasPermission((String) d.getConfigContent("reloadPermission"))){
			for(String s : StringManager.createCommandMessage((List<String>) d.getPlainMessage("msgNoPermission"))){
				sender.sendMessage(s);
			}
			return;
		}
		d.reload();
		for(String s : StringManager.createCommandMessage((List<String>) d.getPlainMessage("msgReloadCommand"))){
			sender.sendMessage(s);
		}
	}
}