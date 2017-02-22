package io.github.kamilkime.kcaptcha.cmds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import io.github.kamilkime.kcaptcha.user.User;
import io.github.kamilkime.kcaptcha.user.UserUtils;

public class MainTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> subs = Arrays.asList("force", "help", "info", "reload", "version");
		List<String> comp = new ArrayList<String>();
		
		if(args.length == 1) {
			for(String s : subs) {
				if(s.startsWith(args[0].toLowerCase())) comp.add(s);
			}
		}
		
		if(args.length == 2) {
			if((args[0].equalsIgnoreCase("info") && sender.hasPermission("kcaptcha.info")) || (args[0].equalsIgnoreCase("force") && sender.hasPermission("kcaptcha.force"))) {
				for(User u : UserUtils.getUsers()) {
					if(u.getName().toLowerCase().startsWith(args[1])) comp.add(u.getName());
				}
			}
		}
		return comp;
	}
}