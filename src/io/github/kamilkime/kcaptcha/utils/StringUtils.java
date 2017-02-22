package io.github.kamilkime.kcaptcha.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.PluginManager;

import io.github.kamilkime.kcaptcha.data.DataManager;

public class StringUtils {
	
	private static final char[] CAPTCHA_CHARS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	private static final Random RANDOM = new Random();
	
	@SuppressWarnings("unchecked")
	public static List<String> toCommands(List<String> list){
		Map<String, Command> knownCommands = null;
		try{
			PluginManager pm = Bukkit.getPluginManager();
			Field commandMapField = pm.getClass().getDeclaredField("commandMap");
	        commandMapField.setAccessible(true);
	        Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
	        knownCommandsField.setAccessible(true);
	        knownCommands = (Map<String, Command>) knownCommandsField.get((SimpleCommandMap) commandMapField.get(pm));
		} catch(Exception e){
			e.printStackTrace();
		}
		List<String> toReturn = new ArrayList<String>();
		for(String s : list){
			toReturn.add(s);
			if(knownCommands !=null){
				Command c = knownCommands.get(s);
				if(c !=null && c.getAliases() !=null){
					for(String alias : c.getAliases()) if(!toReturn.contains(alias)) toReturn.add(alias);
				}
			}
		}
		return toReturn;
	}
	
	public static String color(String s){
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static List<String> color(List<String> list){
		for(int i=0; i<list.size(); i++) list.set(i, color(list.get(i)));
		return list;
	}
	
	public static String generateCaptcha() {
		String c = "";
		for(int i=0; i<DataManager.getInst().captchaLength; i++) {
			c += CAPTCHA_CHARS[RANDOM.nextInt(CAPTCHA_CHARS.length)];
		}
		return c;
	}
}