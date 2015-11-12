package pl.kamilkime.kcaptcha.objects.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import pl.kamilkime.kcaptcha.objects.VerifiedUser;

public class StringManager {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");

	public static List<String> createChatMessage(List<String> message, String captcha){
		List<String> output = new ArrayList<String>();
		for(String s : message){
			s = ChatColor.translateAlternateColorCodes('&', s);
			s = s.replace("{CAPTCHA}", captcha);
			s = s.replace("{CODE}", captcha);
			output.add(s);
		}
		return output;
	}
	
	public static String createBarMessage(String message, String captcha){
		String output = message;
		if(message.length() > 64) output = message.substring(0, 64);
		output = ChatColor.translateAlternateColorCodes('&', output);
		output = output.replace("{CAPTCHA}", captcha);
		output = output.replace("{CODE}", captcha);
		return output;
	}
	
	public static List<String> createCommandMessage(List<String> message){
		List<String> output = new ArrayList<String>();
		for(String s : message){
			s = ChatColor.translateAlternateColorCodes('&', s);
			output.add(s);
		}
		return output;
	}
	
	public static List<String> createInfoMessage(List<String> message, VerifiedUser user){
		List<String> output = new ArrayList<String>();
		for(String s : message){
			s = ChatColor.translateAlternateColorCodes('&', s);
			s = s.replace("{UUID}", user.getUUID().toString());
			s = s.replace("{NAME}", user.getName());
			s = s.replace("{IP}", user.getIp());
			s = s.replace("{DATE}", sdf.format(user.getDate()));
			output.add(s);
		}
		return output;
	}
	
	public static List<String> createWrongSyntaxMessage(List<String> message, String commandName){
		List<String> output = new ArrayList<String>();
		for(String s : message){
			s = ChatColor.translateAlternateColorCodes('&', s);
			s = s.replace("{COMMAND}", commandName);
			output.add(s);
		}
		return output;
	}
}