package com.gmail.kamilkime.kcaptcha.data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.PluginManager;

import com.gmail.kamilkime.kcaptcha.Main;
import com.gmail.kamilkime.kcaptcha.objects.VerifiedUser;

public class StringUtils {

	public static List<String> createChatMessage(List<String> message, String captcha){
		List<String> output = new ArrayList<String>();
		for(String s : message) output.add(s.replace("{CAPTCHA}", captcha).replace("{CODE}", captcha));
		return output;
	}
	
	public static String createBarMessage(String message, String captcha){
		String output = message;
		if(message.length() > 64) output = message.substring(0, 64);
		return output.replace("{CAPTCHA}", captcha).replace("{CODE}", captcha);
	}
	
	public static List<String> createInfoMessage(List<String> message, VerifiedUser user){
		List<String> output = new ArrayList<String>();
		for(String s : message){
			output.add(s.replace("{UUID}", user.getUUID().toString()).replace("{NAME}", user.getName())
					.replace("{IP}", user.getIp()).replace("{DATE}", Main.SDF.format(user.getDate())));
		}
		return output;
	}
	
	public static List<String> createWrongSyntaxMessage(List<String> message, String commandName){
		List<String> output = new ArrayList<String>();
		for(String s : message) output.add(s.replace("{COMMAND}", commandName));
		return output;
	}
	
	public static String createTimestamp(){
		Calendar c = Calendar.getInstance();
		String time = "" + c.get(Calendar.YEAR) + "" + (c.get(Calendar.MONTH) + 1) + "" + c.get(Calendar.DAY_OF_MONTH) +
				"" + c.get(Calendar.HOUR_OF_DAY) + "" + c.get(Calendar.MINUTE) + "" + c.get(Calendar.SECOND);
		return time;
	}
	
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
}