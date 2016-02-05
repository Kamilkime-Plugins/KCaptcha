package com.gmail.kamilkime.kcaptcha.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.kamilkime.kcaptcha.Main;

@SuppressWarnings("deprecation")
public class FileUtils {

	private final static int cfgVersion = YamlConfiguration.loadConfiguration(Main.getInst().getResource("config.yml")).getInt("configVersion");
	private final static int msgVersion = YamlConfiguration.loadConfiguration(Main.getInst().getResource("messages.yml")).getInt("messagesVersion");
	private static File mainDir = Main.getInst().getDataFolder();
	private static File cfgFile = new File(mainDir, "config.yml");
	private static File msgFile = new File(mainDir, "messages.yml");
	private static File errorLogs = new File(mainDir, "errorLogs");
	private static String oldLang;
	
	public static void checkConfig(){
		File f = new File(mainDir, "configOld@" + StringUtils.createTimestamp() + ".yml");
		if(Main.getInst().getConfig().get("lang") !=null) oldLang = Main.getInst().getConfig().getString("lang");
		if(Main.getInst().getConfig().get("configVersion") == null || Main.getInst().getConfig().getInt("configVersion") !=cfgVersion){
			cfgFile.renameTo(f);
			Main.getInst().saveResource("config.yml", true);
			return;
		}
		File temp = new File(mainDir, "tempCfg.yml");
		cfgFile.renameTo(temp);
		Main.getInst().saveResource("config.yml", true);
		YamlConfiguration cYml = YamlConfiguration.loadConfiguration(cfgFile);
		YamlConfiguration tYml = YamlConfiguration.loadConfiguration(temp);
		for(String s : cYml.getKeys(false)){
			if(tYml.get(s) == null){
				temp.renameTo(f);
				return;
			}
		}
		cfgFile.delete();
		temp.renameTo(cfgFile);
	}
	
	public static void checkMessages(){
		if(oldLang !=null && new File(mainDir, "messages_" + oldLang + ".yml").exists()){
			new File(mainDir, "messages_" + oldLang + ".yml").renameTo(msgFile);
		}
		YamlConfiguration msgYml = YamlConfiguration.loadConfiguration(msgFile);
		File f = new File(mainDir, "messagesOld@" + StringUtils.createTimestamp() + ".yml");
		if(msgYml.get("messagesVersion") == null || msgYml.getInt("messagesVersion") !=msgVersion){
			msgFile.renameTo(f);
			Main.getInst().saveResource("messages.yml", true);
			return;
		}
		File temp = new File(mainDir, "tempMsg.yml");
		msgFile.renameTo(temp);
		Main.getInst().saveResource("messages.yml", true);
		YamlConfiguration cYml = YamlConfiguration.loadConfiguration(msgFile);
		YamlConfiguration tYml = YamlConfiguration.loadConfiguration(temp);
		for(String s : cYml.getKeys(false)){
			if(tYml.get(s) == null){
				temp.renameTo(f);
				return;
			}
		}
		msgFile.delete();
		temp.renameTo(msgFile);
	}
	
	public static String createErrorLog(StackTraceElement[] stacktrace) throws IOException{
		if(!errorLogs.exists()) errorLogs.mkdir();
		File log = new File(errorLogs, StringUtils.createTimestamp() + ".log");
		log.createNewFile();
		FileWriter fw = new FileWriter(log);
		for(StackTraceElement s : stacktrace) fw.write(s.toString() + "\n");
		fw.close();
		return log.getName();
	}
}