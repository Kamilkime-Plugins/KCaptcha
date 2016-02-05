package com.gmail.kamilkime.kcaptcha;

import me.confuser.barapi.BarAPI;

import java.text.SimpleDateFormat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.kamilkime.kcaptcha.cmds.MainCmd;
import com.gmail.kamilkime.kcaptcha.data.Settings;
import com.gmail.kamilkime.kcaptcha.listeners.AsyncPlayerChatListener;
import com.gmail.kamilkime.kcaptcha.listeners.PlayerCommandPreprocessListener;
import com.gmail.kamilkime.kcaptcha.listeners.PlayerLoginListener;
import com.gmail.kamilkime.kcaptcha.objects.utils.VerificationUtils;
import com.gmail.kamilkime.kcaptcha.title.TitleUtils;

public class Main extends JavaPlugin {

	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss z");
	public static final SimpleDateFormat oldSDF = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");
	private static Main inst;
	private Settings d;
	private BarAPI bar;

	@Override
	public void onEnable() {
		inst = this;
		
		d = Settings.getInst();
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
			if (p.hasPermission(Settings.getInst().bypassPermission)) continue;
			VerificationUtils.addNonValidated(p.getUniqueId(), VerificationUtils.generateCaptcha());
		}
	}
}