package io.github.kamilkime.kcaptcha;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.kamilkime.kcaptcha.bossbar.KBossBar;
import io.github.kamilkime.kcaptcha.bossbar.KBossBarListener;
import io.github.kamilkime.kcaptcha.bossbar.KBossBarUtils;
import io.github.kamilkime.kcaptcha.cmds.MainCmd;
import io.github.kamilkime.kcaptcha.cmds.MainTabCompleter;
import io.github.kamilkime.kcaptcha.data.DataManager;
import io.github.kamilkime.kcaptcha.enums.PreparationResult;
import io.github.kamilkime.kcaptcha.listeners.AsyncPlayerChatListener;
import io.github.kamilkime.kcaptcha.listeners.PlayerCommandPreprocessListener;
import io.github.kamilkime.kcaptcha.listeners.PlayerJoinListener;
import io.github.kamilkime.kcaptcha.title.KTitle;
import io.github.kamilkime.kcaptcha.title.KTitleUtils;
import io.github.kamilkime.kcaptcha.user.UserUtils;
import io.github.kamilkime.kcaptcha.utils.MessagingUtils;
import io.github.kamilkime.kcaptcha.utils.ReflectionUtils;
import io.github.kamilkime.kcaptcha.utils.StringUtils;

public class Main extends JavaPlugin {
	
	private static Main inst;
	private static boolean isModded;

	public Main() {
		inst = this;
		
		try {
			Class.forName("cpw.mods.fml.server.FMLServerHandler");
			isModded = true;
		} catch (Exception e) {}
	}
	
	@Override
	public void onEnable() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch(ClassNotFoundException e) {
			Bukkit.getConsoleSender().sendMessage(StringUtils.color("&7&l[KCaptcha] &cProblem with SQLite driver, disabling plugin!"));
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		
		DataManager.getInst().loadConfig();
		
		if(DataManager.getInst().rememberUUIDs) DataManager.getInst().loadUsers();
		
		if(DataManager.getInst().enableKBossBar && !checkAPI(KBossBar.prepareAPI(), "KBossBar", KBossBarUtils.getInitializedClassName())) return;
		if(DataManager.getInst().enableKTitle && !checkAPI(KTitle.prepareAPI(), "KTitle", KTitleUtils.getInitializedClassName())) return;
		
		DataManager.getInst().loadSensitiveBarData();
		
		Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
		Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new KBossBarListener(), this);
		
		getCommand("kcaptcha").setExecutor(new MainCmd());
		getCommand("kcaptcha").setTabCompleter(new MainTabCompleter());
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(UserUtils.needsVerification(p)) {
				DataManager.getNonVerifiedList().put(p.getUniqueId(), StringUtils.generateCaptcha());
				MessagingUtils.sendMessageComplete(p);
			}
		}
		
		if(DataManager.getInst().rememberUUIDs) startAutoSave();
	}

	@Override
	public void onDisable() {
		KBossBar.closeAPI();
		if(DataManager.getInst().rememberUUIDs) DataManager.getInst().saveUsers();
	}

	private boolean checkAPI(PreparationResult pr, String apiName, String initializedClassName) {
		switch(pr) {
		case ALL_OK:
			Bukkit.getConsoleSender().sendMessage(StringUtils.color("&7&l[KCaptcha] &a" + apiName + " &8(" + initializedClassName + ") &ainitialized correctly!"));
			return true;
		case MISSING_PLIB:
			Bukkit.getConsoleSender().sendMessage(StringUtils.color("&7&l[KCaptcha] &cYou need ProtocolLib for " + apiName + " if you run a modded server!"));
			Bukkit.getPluginManager().disablePlugin(this);
			return false;
		case WRONG_VERSION:
			Bukkit.getConsoleSender().sendMessage(StringUtils.color("&7&l[KCaptcha] &cYour server version &8(" + ReflectionUtils.VERSION + (isModded ? "_plib" : "")
					+ ") &cis not supported by " + apiName + "!"));
			Bukkit.getConsoleSender().sendMessage(StringUtils.color("&7&l[KCaptcha] &cIf you think that is not true - contact the plugin developer!"));
			Bukkit.getPluginManager().disablePlugin(this);
			return false;
		default:
			return false;
		}
	}
	
	private void startAutoSave() {
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			public void run() {
				DataManager.getInst().saveUsers();
				Bukkit.getConsoleSender().sendMessage(StringUtils.color("&7&l[KCaptcha] &aUser data saved!"));
			}
		}, DataManager.getInst().autoSaveInterval*20, DataManager.getInst().autoSaveInterval*20);
	}
	
	public static Main getInst() {
		return inst;
	}
	
	public static boolean isModded() {
		return isModded;
	}
	
	public static boolean checkForPLib() {
		if(!isModded) return true;
		if(Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) return true;
		return false;
	}
}