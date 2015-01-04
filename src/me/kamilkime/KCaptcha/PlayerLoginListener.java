package me.kamilkime.KCaptcha;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener{
	
	public PlayerLoginListener(Main plugin){}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e){
		String pName = e.getPlayer().getName();
		
		if(!e.getPlayer().hasPermission("kchapta.bypass")){
			Main.joinMap.put(pName, true);
}
		if(Main.chatMap.containsKey(pName)){
			Main.chatMap.remove(pName);
}
}
}