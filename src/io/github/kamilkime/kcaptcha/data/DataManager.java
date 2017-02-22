package io.github.kamilkime.kcaptcha.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;

import io.github.kamilkime.kcaptcha.bossbar.KBossBarUtils;
import io.github.kamilkime.kcaptcha.enums.BlockedAction;
import io.github.kamilkime.kcaptcha.enums.BossType;
import io.github.kamilkime.kcaptcha.enums.ConfigType;
import io.github.kamilkime.kcaptcha.enums.TitleFunction;
import io.github.kamilkime.kcaptcha.user.User;
import io.github.kamilkime.kcaptcha.user.UserUtils;
import io.github.kamilkime.kcaptcha.utils.StringUtils;

public class DataManager {
	
	private static Map<UUID, String> nonVerified;
	private static DataManager inst;
	private YamlConfiguration cfg;
	
	public int captchaLength;
	public int barDisplayTime;
	public int barStartPercent;
	public int autoSaveInterval;
	public int forceRevalidationEvery;
	
	public boolean barShorten;
	public boolean enableKTitle;
	public boolean rememberUUIDs;
	public boolean enableKBossBar;
	public boolean enableChatMessage;
	
	public Object barColor;
	public Object barStyle;
	
	public BossType bossType;
	
	public SimpleDateFormat dateFormat;
	
	public String barMessageCompleteCaptcha;
	public String barMessageCaptchaCompleted;
	public String titleMessageCompleteCaptcha;
	public String titleMessageCaptchaCompleted;
	public String subtitleMessageCompleteCaptcha;
	public String subtitleMessageCaptchaCompleted;
	public String actionbarMessageCompleteCaptcha;
	public String actionbarMessageCaptchaCompleted;
	
	public List<String> cmdsBeforeCaptcha;
	public List<String> mainMessageInfoCommand;
	public List<String> mainMessageHelpCommand;
	public List<String> mainMessageWrongSyntax;
	public List<String> mainMessageNoPermission;
	public List<String> mainMessageNoSuchPlayer;
	public List<String> mainMessageForceCommand;
	public List<String> mainMessageReloadCommand;
	public List<String> chatMessageCompleteCaptcha;
	public List<String> chatMessageCaptchaCompleted;
	public List<String> mainMessageFeatureNotEnabled;
	
	public List<Integer> fadeProperties;
	public List<BlockedAction> blockedActions;
	public List<TitleFunction> enabledFunctions;
	
	private DataManager() {
		inst = this;
	}
	
	public void loadConfig() {
		FileManager.checkFileExistance();
		FileManager.checkFileIntegrity();
		
		// barConfig.yml
		cfg = YamlConfiguration.loadConfiguration(FileManager.getConfigFile(ConfigType.bar));
		this.barDisplayTime = cfg.getInt("barDisplayTime");
		this.barStartPercent = cfg.getInt("barStartPercent");
		this.barShorten = cfg.getBoolean("barShorten");
		this.enableKBossBar = cfg.getBoolean("enableKBossBar");
		this.bossType = BossType.valueOf(cfg.getString("bossType").toUpperCase());
		this.barMessageCompleteCaptcha = StringUtils.color(cfg.getString("barMessageCompleteCaptcha"));
		this.barMessageCaptchaCompleted = StringUtils.color(cfg.getString("barMessageCaptchaCompleted"));
		
		// chatConfig.yml
		cfg = YamlConfiguration.loadConfiguration(FileManager.getConfigFile(ConfigType.chat));
		this.enableChatMessage = cfg.getBoolean("enableChatMessage");
		this.chatMessageCompleteCaptcha = StringUtils.color(cfg.getStringList("chatMessageCompleteCaptcha"));
		this.chatMessageCaptchaCompleted = StringUtils.color(cfg.getStringList("chatMessageCaptchaCompleted"));
		
		// mainConfig.yml
		cfg = YamlConfiguration.loadConfiguration(FileManager.getConfigFile(ConfigType.main));
		this.captchaLength = cfg.getInt("captchaLength");
		this.autoSaveInterval = cfg.getInt("autoSaveInterval");
		this.forceRevalidationEvery = cfg.getInt("forceRevalidationEvery");
		this.rememberUUIDs = cfg.getBoolean("rememberUUIDs");
		this.dateFormat = new SimpleDateFormat(cfg.getString("dateFormat"));
		this.cmdsBeforeCaptcha = StringUtils.toCommands(cfg.getStringList("cmdsBeforeCaptcha"));
		this.mainMessageInfoCommand = StringUtils.color(cfg.getStringList("mainMessageInfoCommand"));
		this.mainMessageHelpCommand = StringUtils.color(cfg.getStringList("mainMessageHelpCommand"));
		this.mainMessageWrongSyntax = StringUtils.color(cfg.getStringList("mainMessageWrongSyntax"));
		this.mainMessageNoPermission = StringUtils.color(cfg.getStringList("mainMessageNoPermission"));
		this.mainMessageNoSuchPlayer = StringUtils.color(cfg.getStringList("mainMessageNoSuchPlayer"));
		this.mainMessageForceCommand = StringUtils.color(cfg.getStringList("mainMessageForceCommand"));
		this.mainMessageReloadCommand = StringUtils.color(cfg.getStringList("mainMessageReloadCommand"));
		this.mainMessageFeatureNotEnabled = StringUtils.color(cfg.getStringList("mainMessageFeatureNotEnabled"));
		this.blockedActions = new ArrayList<BlockedAction>();
		for(String s : cfg.getStringList("blockedActions")) {
			this.blockedActions.add(BlockedAction.valueOf(s.toUpperCase()));
		}
		
		// titleConfig.yml
		cfg = YamlConfiguration.loadConfiguration(FileManager.getConfigFile(ConfigType.title));
		this.enableKTitle = cfg.getBoolean("enableKTitle");
		this.titleMessageCompleteCaptcha = StringUtils.color(cfg.getString("titleMessageCompleteCaptcha"));
		this.titleMessageCaptchaCompleted = StringUtils.color(cfg.getString("titleMessageCaptchaCompleted"));
		this.subtitleMessageCompleteCaptcha = StringUtils.color(cfg.getString("subtitleMessageCompleteCaptcha"));
		this.subtitleMessageCaptchaCompleted = StringUtils.color(cfg.getString("subtitleMessageCaptchaCompleted"));
		this.actionbarMessageCompleteCaptcha = StringUtils.color(cfg.getString("actionbarMessageCompleteCaptcha"));
		this.actionbarMessageCaptchaCompleted = StringUtils.color(cfg.getString("actionbarMessageCaptchaCompleted"));
		this.fadeProperties = cfg.getIntegerList("fadeProperties");
		this.enabledFunctions = new ArrayList<TitleFunction>();
		for(String s : cfg.getStringList("enabledFunctions")) {
			this.enabledFunctions.add(TitleFunction.valueOf(s.toUpperCase()));
		}
	}
	
	public void loadUsers() {
		try {
			DatabaseManager.checkDatabaseFile();
			DatabaseManager.openConnection();
			ResultSet users = DatabaseManager.executeQuery("SELECT * FROM users");
			while(users.next()) {
				new User(UUID.fromString(users.getString("uuid")), users.getString("name"), users.getString("ip"), users.getLong("date"));
			}
			DatabaseManager.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void loadSensitiveBarData() {
		cfg = YamlConfiguration.loadConfiguration(FileManager.getConfigFile(ConfigType.bar));
		this.barColor = KBossBarUtils.getColor(cfg.getString("barColor").toUpperCase());
		this.barStyle = KBossBarUtils.getStyle(cfg.getString("barStyle").toUpperCase());
	}
	
	public void saveUsers() {
		DatabaseManager.checkDatabaseFile();
		DatabaseManager.openConnection();
		for(User u : UserUtils.getUsers()) {
			DatabaseManager.executeUpdate("INSERT OR IGNORE INTO users VALUES ('" + u.getUUID().toString() + "', '" + u.getName() + "', '" + u.getIp() + "', "
					+ u.getVerificationDate() + ");");
			DatabaseManager.executeUpdate("UPDATE users SET name='" + u.getName() + "', ip='" + u.getIp() + "', date=" + u.getVerificationDate() + " WHERE uuid='"
					+ u.getUUID().toString() + "'");
		}
		DatabaseManager.closeConnection();
	}
	
	public static Map<UUID, String> getNonVerifiedList() {
		if(nonVerified == null) nonVerified = new HashMap<UUID, String>();
		return nonVerified;
	}
	
	public static DataManager getInst() {
		if(inst == null) return new DataManager();
		return inst;
	}
}