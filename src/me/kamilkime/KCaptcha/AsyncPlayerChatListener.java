package me.kamilkime.KCaptcha;

import me.confuser.barapi.BarAPI;

import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener{
	
	public AsyncPlayerChatListener(Main plugin){}
	private int i;
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		String pName = p.getName();
		//First message ==========================================================================================================================
		if(Main.joinMap.get(pName) !=null){
			if(!e.isCancelled()){
				Main.joinMap.remove(pName);
				String randomString = RandomStringUtils.randomAlphanumeric(Main.CaptchaLenght);
				Main.chatMap.put(pName, randomString);
				if(Main.EnableChatMessage || (!Main.EnableChatMessage && !Main.EnableBarAPI)){
					i = 0;
					while(i < 51){
						p.sendMessage("");
						i++;
}
					p.sendMessage("§8§l=-=-=-=<§a§lOchrona botowa§8§l>=-=-=-=");
					p.sendMessage("§6§lWpisz na czacie kod: §b§l" + randomString + "\n" + "§6§lAby moc korzystac z czatu");
					p.sendMessage("§8§l=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					FileDataManager.spacer(p);
}
				if(Bukkit.getPluginManager().getPlugin("BarAPI") !=null){
					if(Main.EnableBarAPI){
						BarAPI.setMessage(e.getPlayer(), "§6§lKod do wpisania na czacie: §b§l" + randomString, 15);
}
}
				e.setCancelled(true);
				return;
}
}
		//Next messages ==========================================================================================================================
		if(Main.chatMap.containsKey(pName)){
			String captcha = Main.chatMap.get(pName);
			//Access granted ==========================================================================================================================
			if(e.getMessage().equals(captcha)){
				Main.chatMap.remove(pName);
				if(Main.EnableChatMessage || (!Main.EnableChatMessage && !Main.EnableBarAPI)){
					FileDataManager.spacer(p);
					p.sendMessage("§8§l=-=-=-=<§a§lOchrona botowa§8§l>=-=-=-=");
					p.sendMessage("§6§lWeryfikacja pomyslna\nMozesz juz uzywac czatu");
					p.sendMessage("§8§l=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					
					YamlConfiguration ips = FileDataManager.getIpFile();
					ips.set(pName, e.getPlayer().getAddress().getAddress().toString().replaceAll("/", ""));
					try{
						ips.save(FileDataManager.ipFile);
}
					catch(Exception ex){
						ex.printStackTrace();
}

					FileDataManager.spacer(p);
}
				if(Bukkit.getPluginManager().getPlugin("BarAPI") !=null){
					if(Main.EnableBarAPI){
						BarAPI.setMessage(e.getPlayer(), "§6§lWeryfikacja pomyslna, mozesz juz uzywac czatu", 15);
}
}
				e.setCancelled(true);
				return;
}
			//Access denied ==========================================================================================================================
			if(Main.EnableChatMessage || (!Main.EnableChatMessage && !Main.EnableBarAPI)){
				FileDataManager.spacer(p);
				p.sendMessage("§8§l=-=-=-=<§a§lOchrona botowa§8§l>=-=-=-=");
				p.sendMessage("§6§lWpisz na czacie kod: §b§l" + captcha + "\n" + "§6§lAby moc korzystac z czatu");
				p.sendMessage("§8§l=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
				FileDataManager.spacer(p);
}
			if(Bukkit.getPluginManager().getPlugin("BarAPI") !=null){
				if(Main.EnableBarAPI){
					BarAPI.setMessage(e.getPlayer(), "§6§lKod do wpisania na czacie: §b§l" + captcha, 15);
}
}
			e.setCancelled(true);
}
}
}