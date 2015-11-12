package pl.kamilkime.kcaptcha.objects;

import java.util.Date;
import java.util.UUID;

import org.bukkit.entity.Player;

import pl.kamilkime.kcaptcha.objects.utils.VerificationUtils;

/*
 * This class is used ONLY if uuid remembering is enabled
 */

public class VerifiedUser {

	private UUID uuid;
	private String name;
	private String ip;
	private Date date;

	private VerifiedUser(UUID uuid, String name, String ip, Date date) {
		this.uuid = uuid;
		this.name = name;
		this.ip = ip;
		this.date = date;
		VerificationUtils.addUser(this);
	}

	public UUID getUUID() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public String getIp() {
		return ip;
	}

	public Date getDate() {
		return date;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public static void createUser(UUID uuid, String name, String ip, Date date){
		for(VerifiedUser u : VerificationUtils.getUsers()){
			if(u.getName().equalsIgnoreCase(name)) return;
			if(u.getUUID().equals(uuid)) return;
		}
		new VerifiedUser(uuid, name, ip, date);
	}
	
	public static VerifiedUser get(String name){
		for(VerifiedUser u : VerificationUtils.getUsers()){
			if(u.getName().equalsIgnoreCase(name)) return u;
		}
		return null;
	}
	
	public static VerifiedUser get(UUID uuid){
		for(VerifiedUser u : VerificationUtils.getUsers()){
			if(u.getUUID().equals(uuid)) return u;
		}
		return null;
	}
	
	public static VerifiedUser get(Player p){
		for(VerifiedUser u : VerificationUtils.getUsers()){
			if(u.getName().equalsIgnoreCase(p.getName())) return u;
			if(u.getUUID().equals(p.getUniqueId())){
				if(!u.getName().equalsIgnoreCase(p.getName())) u.setName(p.getName());
				if(!u.getIp().equals(p.getAddress().getAddress().toString().replace("/", ""))) u.setIp(p.getAddress().getAddress().toString().replace("/", ""));
				return u;
			}
		}
		return null;
	}
}