package me.kamilkime.KCaptcha;

import me.confuser.barapi.BarAPI;

import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessListener implements Listener{

	public PlayerCommandPreprocessListener(Main plugin){}
	
	private int i;
	
	@EventHandler
	public void onChat(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		String pName = p.getName();
		//First command ==========================================================================================================================
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
		//Next command ==========================================================================================================================
		if(Main.chatMap.containsKey(pName)){
			String captcha = Main.chatMap.get(pName);
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