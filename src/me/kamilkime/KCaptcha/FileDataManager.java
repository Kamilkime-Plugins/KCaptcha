package me.kamilkime.KCaptcha;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FileDataManager{

	private static File mainDir = Main.plugin.getDataFolder();
	private static File cfgFile = new File(mainDir, "config.yml");
	
	public static void checkMainDir(){
		if(!mainDir.exists()){
			mainDir.mkdir();
}
}
	public static void checkCfgFile(){
		if(!cfgFile.exists()){
			try{
			cfgFile.createNewFile();
}
			catch(Exception e){
				System.out.println("§8§l=-=-=-=-=-=<§4§lERROR§8§l>=-=-=-=-=-=");
				System.out.println("§c§lNie mozna utworzyc configu, wylacznie pluginu...");
				Bukkit.getServer().getPluginManager().disablePlugin(Main.plugin);
				System.out.println("§8§l=-=-=-=-=-=<§4§lERROR§8§l>=-=-=-=-=-=");
}
}
		Main.plugin.getConfig().options().copyDefaults(true);
		Main.plugin.saveConfig();
}
	public static void load(){
		Main.EnableBarAPI = Main.plugin.getConfig().getBoolean("enableBarAPI");
		Main.CaptchaLenght = Main.plugin.getConfig().getInt("captchaLenght");
		Main.EnableChatMessage = Main.plugin.getConfig().getBoolean("enableChatMessage");
}
	public static void reloadCfg(){
		Main.plugin.reloadConfig();
		Main.plugin.saveConfig();
		Main.EnableBarAPI = Main.plugin.getConfig().getBoolean("enableBarAPI");
		Main.CaptchaLenght = Main.plugin.getConfig().getInt("captchaLenght");
		Main.EnableChatMessage = Main.plugin.getConfig().getBoolean("enableChatMessage");
}
	public static void spacer(Player p){
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("");
}
}