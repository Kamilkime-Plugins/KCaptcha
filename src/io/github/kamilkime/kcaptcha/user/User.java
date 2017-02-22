package io.github.kamilkime.kcaptcha.user;

import java.util.UUID;

import org.bukkit.entity.Player;

import io.github.kamilkime.kcaptcha.data.DatabaseManager;

public class User {

	private UUID uuid;
	private String ip;
	private String name;
	private long verificationDate;
	
	public User(UUID uuid, String name, String ip, long verificationDate) {
		this.uuid = uuid;
		this.name = name;
		this.ip = ip;
		this.verificationDate = verificationDate;
		UserUtils.addUser(this);
	}

	public UUID getUUID() {
		return this.uuid;
	}

	public String getIp() {
		return this.ip;
	}

	public String getName() {
		return this.name;
	}

	public long getVerificationDate() {
		return this.verificationDate;
	}
	
	public void delete() {
		UserUtils.removeUser(this);
		DatabaseManager.openConnection();
		DatabaseManager.executeUpdate("DELETE FROM users WHERE uuid='" + this.uuid.toString() + "'");
		DatabaseManager.closeConnection();
	}
	
	public void update(Player p) {
		if(!this.name.equals(p.getName())) this.name = p.getName();
	}
	
	public static User get(UUID uuid) {
		for(User u : UserUtils.getUsers()) {
			if(u.getUUID().equals(uuid)) return u;
		}
		return null;
	}
	
	public static User get(String name) {
		for(User u : UserUtils.getUsers()) {
			if(u.getName().equalsIgnoreCase(name)) return u;
		}
		return null;
	}
}