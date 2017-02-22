package io.github.kamilkime.kcaptcha.user;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import io.github.kamilkime.kcaptcha.data.DataManager;

public class UserUtils {

	private static List<User> users = new ArrayList<User>();
	
	public static List<User> getUsers() {
		return new ArrayList<User>(users);
	}
	
	public static void addUser(User u) {
		if(!users.contains(u)) users.add(u);
	}
	
	public static void removeUser(User u) {
		if(users.contains(u)) users.remove(u);
	}
	
	public static boolean needsVerification(Player p) {
		if(p.hasPermission("kcaptcha.bypass")) return false;
		User user = User.get(p.getUniqueId());
		if(user == null) return true;
		if(user.getVerificationDate() + (DataManager.getInst().forceRevalidationEvery*24*3600*1000) < System.currentTimeMillis()) {
			user.delete();
			return true;
		}
		return false;
	}
}