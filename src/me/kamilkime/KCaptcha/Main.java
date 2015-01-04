package me.kamilkime.KCaptcha;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public static Main plugin;
	public static int CaptchaLenght;
	public static boolean EnableBarAPI;
	public static boolean EnableChatMessage;
	public static Map<String, Boolean> joinMap = new HashMap<String, Boolean>();
	public static Map<String, String> chatMap = new HashMap<String, String>();
	
	public void onEnable(){
		plugin = this;
		FileDataManager.checkMainDir();
		FileDataManager.checkCfgFile();
		FileDataManager.load();
		
		if(Bukkit.getOnlinePlayers().length > 0){
			for(Player p : Bukkit.getOnlinePlayers()){
				if(!p.hasPermission("kchapta.bypass")){
					String pName = p.getName();
					joinMap.put(pName, true);
					if(chatMap.containsKey(pName)){
						chatMap.remove(pName);
}
}
}
}
		Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerLoginListener(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(this), this);
		
		getCommand("kcaptcha").setExecutor(new Cmds(this));
}
}