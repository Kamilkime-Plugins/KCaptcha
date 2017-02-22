package io.github.kamilkime.kcaptcha.data;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.kamilkime.kcaptcha.Main;
import io.github.kamilkime.kcaptcha.enums.ConfigProblem;
import io.github.kamilkime.kcaptcha.enums.ConfigType;
import io.github.kamilkime.kcaptcha.utils.StringUtils;

public class FileManager {

	public static void checkFileExistance() {
		if(!Main.getInst().getDataFolder().exists()) Main.getInst().getDataFolder().mkdir();
		for(ConfigType ct : ConfigType.values()) {
			File f = getConfigFile(ct);
			if(!f.exists()) {
				configProblem(ConfigProblem.MISSING_FILE, f.getName(), "");
				Main.getInst().saveResource(f.getName(), true);
			}
		}
	}
	
	public static void checkFileIntegrity() {
		for(ConfigType ct : ConfigType.values()) {
			File old = new File(Main.getInst().getDataFolder(), ct.toString() + "ConfigOld@" + System.currentTimeMillis() + ".yml");
			File f = getConfigFile(ct);
			YamlConfiguration fYml = YamlConfiguration.loadConfiguration(f);
			
			if(fYml.get("configVersion") == null || fYml.getInt("configVersion") !=ct.getVersion()) {
				configProblem(fYml.get("configVersion") == null ? ConfigProblem.MISSING_VERSION : ConfigProblem.WRONG_VERSION, f.getName(), "");
				f.renameTo(old);
				Main.getInst().saveResource(f.getName(), true);
				continue;
			}

			File toCheck = new File(Main.getInst().getDataFolder(), "toCheck.yml");
			f.renameTo(toCheck);
			Main.getInst().saveResource(f.getName(), true);
			YamlConfiguration cleanYml = YamlConfiguration.loadConfiguration(f);
			
			boolean contentsOk = true;
			for(String key : cleanYml.getKeys(false)) {
				if(fYml.get(key) == null) {
					configProblem(ConfigProblem.MISSING_SECTION, f.getName(), key);
					toCheck.renameTo(old);
					contentsOk = false;
					break;
				}
			}
			
			if(!contentsOk) continue;

			f.delete();
			toCheck.renameTo(f);
		}
	}
	
	public static File getConfigFile(ConfigType type) {
		return new File(Main.getInst().getDataFolder(), type.toString() + "Config.yml");
	}
	
	private static void configProblem(ConfigProblem cp, String fName, String addInfo) {
		switch(cp) {
		case MISSING_FILE:
			Bukkit.getConsoleSender().sendMessage(StringUtils.color("&7&l[KCaptcha] &cMissing file &8" + fName + "&c, recreating!"));
			break;
		case MISSING_SECTION:
			Bukkit.getConsoleSender().sendMessage(StringUtils.color("&7&l[KCaptcha] &cMissing section &8" + addInfo + " &cin file &8" + fName + "&c, recreating!"));
			break;
		case MISSING_VERSION:
			Bukkit.getConsoleSender().sendMessage(StringUtils.color("&7&l[KCaptcha] &cMissing configVersion in file &8" + fName + "&c, recreating!"));
			break;
		case WRONG_VERSION:
			Bukkit.getConsoleSender().sendMessage(StringUtils.color("&7&l[KCaptcha] &cWrong configVersion in file &8" + fName + "&c, recreating!"));
			break;
		default:
			break;
		}
	}
}