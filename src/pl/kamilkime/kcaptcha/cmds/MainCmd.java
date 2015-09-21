package pl.kamilkime.kcaptcha.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCmd implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String l, String args[]) {
		if (cmd.getName().equalsIgnoreCase("kcaptcha")) {
			if(args.length == 0){
				new HelpCmd().help(sender, cmd, l, args);
			} else {
				if(args[0].equalsIgnoreCase("info")){
					new InfoCmd().info(sender, cmd, l, args);
				}
				else if(args[0].equalsIgnoreCase("reload")){
					new ReloadCmd().reload(sender, cmd, l, args);
				}
				else if(args[0].equalsIgnoreCase("help")){
					new HelpCmd().help(sender, cmd, l, args);
				} else {
					new HelpCmd().help(sender, cmd, l, args);
				}
			}
		}
		return false;
	}
}