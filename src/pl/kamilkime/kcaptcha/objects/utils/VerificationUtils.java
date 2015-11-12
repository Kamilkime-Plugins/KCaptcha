package pl.kamilkime.kcaptcha.objects.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.entity.Player;

import pl.kamilkime.kcaptcha.data.DataManager;
import pl.kamilkime.kcaptcha.objects.VerifiedUser;

public class VerificationUtils {

	private static Collection<VerifiedUser> validated = new ArrayList<VerifiedUser>();
	private static Map<UUID, String> nonValidated = new HashMap<UUID, String>();
	private static DataManager d = DataManager.getInst();
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
		if(!(boolean) d.getConfigContent("rememberUUIDs")) return false;
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
		int length = (int) d.getConfigContent("captchaLength");
		String captcha = "";
		for(int i=0; i<length; i++){
			int pos = r.nextInt(alphanumbers.length);
			int big = r.nextInt(2);
			captcha += (big == 1 ? String.valueOf(alphanumbers[pos]).toUpperCase() : alphanumbers[pos]);
		}
		return captcha;
	}
}