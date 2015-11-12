package pl.kamilkime.kcaptcha;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import pl.kamilkime.kcaptcha.cmds.MainCmd;
import pl.kamilkime.kcaptcha.data.DataManager;
import pl.kamilkime.kcaptcha.listeners.AsyncPlayerChatListener;
import pl.kamilkime.kcaptcha.listeners.PlayerCommandPreprocessListener;
import pl.kamilkime.kcaptcha.listeners.PlayerLoginListener;
import pl.kamilkime.kcaptcha.objects.utils.VerificationUtils;
import pl.kamilkime.kcaptcha.title.TitleUtils;

public class Main extends JavaPlugin {

	private static Main inst;
	private DataManager d;
	private BarAPI bar;

	@Override
	public void onEnable() {
		inst = this;
		
		d = DataManager.getInst();
		d.checkFiles();
		d.load();
		
		bar = new BarAPI();
		bar.onEnable();
		
		if(TitleUtils.canUse18()) TitleUtils.load();
		
		Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerLoginListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
		Bukkit.getPluginManager().registerEvents(bar, this);
		
		getCommand("kcaptcha").setExecutor(new MainCmd());
		
		checkPlayers();
	}

	@Override
	public void onDisable() {
		d.save();
		bar.onDisable();
	}

	public static Main getInst() {
		return inst;
	}

	@SuppressWarnings("deprecation")
	private void checkPlayers() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (VerificationUtils.isValidated(p)) continue;
			if (p.hasPermission((String) d.getConfigContent("bypassPermission"))) continue;
			VerificationUtils.addNonValidated(p.getUniqueId(), VerificationUtils.generateCaptcha());
		}
	}
}