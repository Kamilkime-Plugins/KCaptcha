package com.gmail.kamilkime.kcaptcha.data;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.kamilkime.kcaptcha.Main;
import com.gmail.kamilkime.kcaptcha.objects.VerifiedUser;
import com.gmail.kamilkime.kcaptcha.objects.utils.VerificationUtils;

public class Settings {

	private static Settings inst;
	private File mainDir = Main.getInst().getDataFolder();
	private File cfgFile = new File(mainDir, "config.yml");
	private File msgFile = new File(mainDir, "messages.yml");
	private File dataFile = new File(mainDir, "data.yml");
	public boolean rememberUUIDs;
	public boolean enableChatMessage;
	public boolean enableBarMessage;
	public boolean enableTitle;
	public boolean enableSubtitle;
	public int forceRevalidationEvery;
	public int captchaLength;
	public int bossBarTime;
	public int titleFadeIn;
	public int titleStay;
	public int titleFadeOut;
	public int subtitleFadeIn;
	public int subtitleStay;
	public int subtitleFadeOut;
	public String bypassPermission;
	public String reloadPermission;
	public String infoPermission;
	public String barYouHaveToCompleteCaptcha;
	public String barCaptchaComplete;
	public String titleYouHaveToCompleteCaptcha;
	public String titleCaptchaComplete;
	public String subtitleYouHaveToCompleteCaptcha;
	public String subtitleCaptchaComplete;
	public List<String> cmdsBeforeCaptcha;
	public List<String> msgYouHaveToCompleteCaptcha;
	public List<String> msgCaptchaComplete;
	public List<String> msgInfoCommand;
	public List<String> msgHelpCommand;
	public List<String> msgReloadCommand;
	public List<String> msgNoPermission;
	public List<String> msgNoSuchPlayer;
	public List<String> msgWrongSyntax;
	public List<String> msgFeatureNotEnabled;
	
	private Settings(){
		inst = this;
	}
	
	public static Settings getInst(){
		if(inst !=null) return inst;
		return new Settings();
	}
	
	public void checkFiles(){
		if(!mainDir.exists()) mainDir.mkdir();
		if(!cfgFile.exists()) Main.getInst().saveResource("config.yml", true);
		if(!msgFile.exists()) Main.getInst().saveResource("messages.yml", true);
		if(!dataFile.exists()){
			try {
				dataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void load(){
		checkFiles();
		FileUtils.checkConfig();
		rememberUUIDs = Main.getInst().getConfig().getBoolean("rememberUUIDs");
		enableChatMessage = Main.getInst().getConfig().getBoolean("enableChatMessage");
		enableBarMessage = Main.getInst().getConfig().getBoolean("enableBarMessage");
		enableTitle = Main.getInst().getConfig().getBoolean("enableTitle");
		enableSubtitle = Main.getInst().getConfig().getBoolean("enableSubtitle");
		forceRevalidationEvery = Main.getInst().getConfig().getInt("forceRevalidationEvery");
		captchaLength = Main.getInst().getConfig().getInt("captchaLength");
		bossBarTime = Main.getInst().getConfig().getInt("bossBarTime");
		titleFadeIn = Main.getInst().getConfig().getInt("titleFadeIn");
		titleStay = Main.getInst().getConfig().getInt("titleStay");
		titleFadeOut = Main.getInst().getConfig().getInt("titleFadeOut");
		subtitleFadeIn = Main.getInst().getConfig().getInt("subtitleFadeIn");
		subtitleStay = Main.getInst().getConfig().getInt("subtitleStay");
		subtitleFadeOut = Main.getInst().getConfig().getInt("subtitleFadeOut");
		bypassPermission = Main.getInst().getConfig().getString("bypassPermission");
		reloadPermission = Main.getInst().getConfig().getString("reloadPermission");
		infoPermission = Main.getInst().getConfig().getString("infoPermission");
		cmdsBeforeCaptcha = StringUtils.toCommands(Main.getInst().getConfig().getStringList("cmdsBeforeCaptcha"));
		
		FileUtils.checkMessages();
		YamlConfiguration mYml = YamlConfiguration.loadConfiguration(msgFile);
		msgYouHaveToCompleteCaptcha = StringUtils.color(mYml.getStringList("msgYouHaveToCompleteCaptcha"));
		msgCaptchaComplete = StringUtils.color(mYml.getStringList("msgCaptchaComplete"));
		msgReloadCommand = StringUtils.color(mYml.getStringList("msgReloadCommand"));
		msgInfoCommand = StringUtils.color(mYml.getStringList("msgInfoCommand"));
		msgHelpCommand = StringUtils.color(mYml.getStringList("msgHelpCommand"));
		msgNoPermission = StringUtils.color(mYml.getStringList("msgNoPermission"));
		msgNoSuchPlayer = StringUtils.color(mYml.getStringList("msgNoSuchPlayer"));
		msgWrongSyntax = StringUtils.color(mYml.getStringList("msgWrongSyntax"));
		msgFeatureNotEnabled = StringUtils.color(mYml.getStringList("msgFeatureNotEnabled"));
		barYouHaveToCompleteCaptcha = StringUtils.color(mYml.getString("barYouHaveToCompleteCaptcha"));
		barCaptchaComplete = StringUtils.color(mYml.getString("barCaptchaComplete"));
		titleYouHaveToCompleteCaptcha = StringUtils.color(mYml.getString("titleYouHaveToCompleteCaptcha"));
		titleCaptchaComplete = StringUtils.color(mYml.getString("titleCaptchaComplete"));
		subtitleYouHaveToCompleteCaptcha = StringUtils.color(mYml.getString("subtitleYouHaveToCompleteCaptcha"));
		subtitleCaptchaComplete = StringUtils.color(mYml.getString("subtitleCaptchaComplete"));
		
		if(rememberUUIDs){
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(dataFile);
			for(String uuid : yml.getKeys(false)){
				String date = yml.getString(uuid + ".date");
				try {
					VerifiedUser.createUser(UUID.fromString(uuid), yml.getString(uuid + ".name"), yml.getString(uuid + ".ip"),
							date.contains(".") ? Main.oldSDF.parse(date) : Main.SDF.parse(date));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void save(){
		checkFiles();
		if(rememberUUIDs){
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(dataFile);
			for(VerifiedUser u : VerificationUtils.getUsers()){
				String uuid = u.getUUID().toString();
				yml.set(uuid + ".name", u.getName());
				yml.set(uuid + ".ip", u.getIp());
				yml.set(uuid + ".date", Main.SDF.format(u.getDate()));
			}
			try {
				yml.save(dataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void reload(){
		Main.getInst().reloadConfig();
		load();
	}
}