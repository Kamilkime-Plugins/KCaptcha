package com.gmail.kamilkime.kcaptcha.objects.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.gmail.kamilkime.kcaptcha.data.Settings;
import com.gmail.kamilkime.kcaptcha.objects.VerifiedUser;

public class VerificationUtils {

	private static Collection<VerifiedUser> validated = new HashSet<VerifiedUser>();
	private static Map<UUID, String> nonValidated = new HashMap<UUID, String>();
	private static Settings set = Settings.getInst();
	private static Random r = new Random();
	private static char[] alphanumbers= "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
	
	public static Collection<VerifiedUser> getUsers(){
		return new ArrayList<VerifiedUser>(validated);
	}
	
	public static void addUser(VerifiedUser u){
		if(!validated.contains(u)) validated.add(u);
	}
	
	public static void removeUser(VerifiedUser u){
		if(validated.contains(u)) validated.remove(u);
	}
	
	public static void addNonValidated(UUID uuid, String captcha){
		nonValidated.put(uuid, captcha);
	}
	
	public static void removeNonValidated(UUID uuid){
		if(nonValidated.containsKey(uuid)) nonValidated.remove(uuid);
	}
	
	public static boolean isValidated(Player p){
		if(set.rememberUUIDs) return false;
		if(VerifiedUser.get(p) == null) return false;
		return true;
	}
	
	public static boolean isWaitingForValidation(Player p){
		if(isValidated(p)) return false;
		if(!nonValidated.containsKey(p.getUniqueId())) return false;
		return true;
	}
	
	public static String getPlayerCaptcha(UUID uuid){
		return nonValidated.get(uuid);
	}
	
	public static String generateCaptcha(){
		String captcha = "";
		for(int i=0; i<set.captchaLength; i++){
			int pos = r.nextInt(alphanumbers.length);
			captcha += (r.nextInt(2) == 1 ? String.valueOf(alphanumbers[pos]).toUpperCase() : alphanumbers[pos]);
		}
		return captcha;
	}
}