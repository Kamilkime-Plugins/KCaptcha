package io.github.kamilkime.kcaptcha.cmds;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.kamilkime.kcaptcha.cmds.sub.ConvertCmd;
import io.github.kamilkime.kcaptcha.cmds.sub.ForceCmd;
import io.github.kamilkime.kcaptcha.cmds.sub.HelpCmd;
import io.github.kamilkime.kcaptcha.cmds.sub.InfoCmd;
import io.github.kamilkime.kcaptcha.cmds.sub.ReloadCmd;
import io.github.kamilkime.kcaptcha.cmds.sub.VersionCmd;

public class MainCmd implements CommandExecutor {
	
	private static Map<String, KCommand> subCommands = new HashMap<String, KCommand>();
	
	public MainCmd() {
		subCommands.put("convert", new ConvertCmd());
		subCommands.put("force", new ForceCmd());
		subCommands.put("help", new HelpCmd());
		subCommands.put("info", new InfoCmd());
		subCommands.put("reload",new ReloadCmd());
		subCommands.put("rl", subCommands.get("reload"));
		subCommands.put("version", new VersionCmd());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("kcaptcha")) {
			if(args.length == 0) {
				subCommands.get("version").execute(sender, args);
			} else {
				if(subCommands.containsKey(args[0].toLowerCase())) {
					subCommands.get(args[0].toLowerCase()).execute(sender, args);
				} else {
					subCommands.get("help").execute(sender, args);
				}
			}
		}
		return false;
	}
}